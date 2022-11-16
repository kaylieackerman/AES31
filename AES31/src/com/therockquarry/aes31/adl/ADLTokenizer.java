/*
	-------------------------------------------------------------------------------
	ADLTokenizer.java
	AES31-3

	Created by David Ackerman on 7/6/05.

	Copyright 2005 David Ackerman.
	-------------------------------------------------------------------------------
	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; version 2
	of the License.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
	-------------------------------------------------------------------------------
*/

package com.therockquarry.aes31.adl;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class ADLTokenizer {
	
	public	final static int TT_EOF				= -1;
	public  final static int TT_SECTION_START	= -2;
	public  final static int TT_SECTION_END		= -3;
	public  final static int TT_KEYWORD			= -4;
	public	final static int TT_DATA			= -5;
	public	final static int TT_COMMENT			= -6;
	
	private File adlFile;	/* File to be parsed */
	private FileInputStream fis;
	private BufferedReader reader;
	private StreamTokenizer st;
	
	protected int tokenType;
	protected String tokenValue;
	protected long tokenPos;
	
	public ADLTokenizer (String fn) throws FileNotFoundException, IOException
	{
		//try 
		//{
			adlFile = new File(fn);
			_initParser();
		//}
		//catch (Exception e) 
		//{
		//	e.printStackTrace();
		//}
	}
	
	public ADLTokenizer (File f) throws FileNotFoundException, IOException
	{
		adlFile = f;
		_initParser();
	}
	
	private boolean _initParser () throws FileNotFoundException, IOException
	{
		 //try 
		 //{
			fis = new FileInputStream (adlFile);
			reader = new BufferedReader(new InputStreamReader (fis, Charset.forName("US-ASCII")));
			String line;
			String estring;
			StringReader sr;
			StringBuffer sbuffer;
			sbuffer = new StringBuffer();
			while ((line = reader.readLine()) != null)
			{
				sbuffer.append(line);
				sbuffer.append("\n");
			}
			//since EDML does not use \ to escape characters, we have to pre-emptively escape the backslash itself!
			estring = sbuffer.toString();
			estring = estring.replace("\\", "\\\\");
			//EDML used 0x1B as its escape character for the double qoute within strings
			estring = estring.replace((char)0x1B, '\\');
			
			sr = new StringReader(estring);
			st = new StreamTokenizer(sr);
			//st.ordinaryChar(0x5C);
			tokenPos = 0;
			
			/* Wipeout Stream Tokenizer defaults and setup, divide the document into words and whitespace. */
			st.resetSyntax();
			
			st.wordChars(0x21, 0x7E);
			//st.wordChars(0x21, 0x5B);
			//st.wordChars(0x5D, 0x7E);
			st.whitespaceChars(0x20, 0x20);
			st.whitespaceChars('\t', '\n');
			st.whitespaceChars('\f', '\r');
			st.quoteChar('"');
			return true;
		// } 
		// catch (Exception e) 
		// {
		//	e.printStackTrace();
		//	return false;
		 // }
	}
	
	public int nextToken ()
	{
		try {
			tokenType = st.nextToken();
			//System.out.println("FOUND: " + st.sval + " of TYPE: " + tokenType);
			if (tokenType != st.TT_EOF) 
			{
				/* Check for ADL SECTION tag. */
				if (st.sval.startsWith("<") && st.sval.endsWith(">") && tokenType != '"')
				{
					/* Check for tag close */
					if (st.sval.charAt(1) == 0x2F) 
					{
						tokenType = TT_SECTION_END;
						tokenValue = st.sval.substring(2, st.sval.length() - 1);
					}
					else
					{
						tokenType = TT_SECTION_START;
						tokenValue = st.sval.substring(1, st.sval.length() - 1);
					}
				}
			
				/* Check for ADL KEYWORD tag. */
				else if (st.sval.startsWith("(") && st.sval.endsWith(")") && tokenType != '"')
				{
					tokenType = TT_KEYWORD;
					tokenValue = st.sval.substring(1, st.sval.length() - 1);
				}
			
				/* Strip comments. */
				else if (st.sval.startsWith("/*"))
				{
					tokenValue = st.sval;
					if (!tokenValue.endsWith("*/"))
					{
						while (st.nextToken() != StreamTokenizer.TT_EOF && (!st.sval.endsWith("*/")))
						{
							tokenValue = tokenValue.concat(" " + st.sval);
							++tokenPos;
							//System.out.println("2 At POS: " + tokenPos + "\tTOKEN: " + tokenValue);
						}
						tokenValue = tokenValue.concat(" " + st.sval);
					}
					//st.nextToken();
					//++tokenPos;
					tokenType = TT_COMMENT;
					//tokenValue = "COMMENT";
					
					//System.out.println("*****\t\"" + tokenValue + "\"");
				}
				/* must be data. */
				else
				{
					tokenType = TT_DATA;
					tokenValue = st.sval;
					//tokenValue = tokenValue.replace("\\", "\\\\");
					//System.out.println("*****\t\"" + tokenValue + "\"");
				}
			}
			else 
			{
				tokenType = TT_EOF;
				close();
			} 
			
			//if (tokenValue != null)
			{
				++tokenPos;
				//System.out.println(" 1 At POS: " + tokenPos + "\tTOKEN: " + tokenValue + " TYPE: " + tokenType);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return tokenType;
			
	}
	
	public void pushBack ()
	{
		
		st.pushBack();
		--tokenPos;
	}
	
	public String nextTokenValue () throws ADLTokenizerException
	{
		if (this.nextToken() != TT_DATA)
		{
			this.pushBack();
			throw new ADLTokenizerException("Token is not of type TT_DATA as expected. Found " + this.tokenValue + " of type " + this.tokenType);
		}
		
		return tokenValue;
	}
	
	public String getTokenValue ()
	
	{
		return tokenValue;
	}
	
	public Vector<String> allDataForCurrentKeyword ()
	{
		Vector<String> rval = new Vector<String>();
		
		while (this.nextToken() == TT_DATA)
		{
			rval.add(tokenValue);
			//System.out.println("ADDING TO VECT: " + tokenValue);
		}
		//System.out.println("TOKEN TYPE IS " + this.tokenType + " before push back");
		
		if (this.tokenType != TT_COMMENT)
		{
			this.pushBack();
		}
		
		return rval;
	}
	
	public int getLineNumber ()
	{
		return st.lineno();
	}
	
	public long getTokenPos ()
	{
		//System.out.println(" 3 At POS: " + tokenPos + "\tTOKEN: " + tokenValue);
		return tokenPos;
	}
	
	public void restartParseFrom (long pos) throws FileNotFoundException, IOException
	{
		//System.out.println("tokenizer pos target: " + pos);
		this._initParser();
		while (this.tokenPos < pos)
		{
			this.nextToken();
			//System.out.println("tokenizer reset to: " + tokenPos);
		}
	}
	
	public void close() throws IOException
	{
		reader.close();
		fis.close();
	}
}

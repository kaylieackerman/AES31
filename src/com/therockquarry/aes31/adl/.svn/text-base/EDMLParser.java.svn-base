/*
	-------------------------------------------------------------------------------
	EDMLParser.java
	AES31-3

	Created by David Ackerman on 7/7/05.

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

import java.io.*;
import java.util.*;

public class EDMLParser {
	private File _fileToParse;
	private ADLTokenizer tokenizer;
	private ADLSection adl;
	
	public EDMLParser () 
	{
			_fileToParse = null;
	}
	
	public EDMLParser (String fn) throws FileNotFoundException
	{
		try {
			_fileToParse = new File(fn);
			_initParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EDMLParser (File f) throws FileNotFoundException, IOException
	{
		_fileToParse = f;
		_initParser();
	}
	
	private void _initParser () throws FileNotFoundException, IOException
	{
		tokenizer = new ADLTokenizer (_fileToParse);
		adl = new ADLSection();
	}
	
	public ADLSection parse () throws EDMLParserException, FileNotFoundException, IOException
	{
		BaseSection currentSection = null;
		
		while (tokenizer.nextToken() != ADLTokenizer.TT_EOF)
		{	
			//System.out.println("(EDML) At POS: " + tokenizer.getTokenPos() + "\tTOKEN: " + tokenizer.tokenValue);
			if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				if (tokenizer.tokenValue.equals("ADL"))
				{
					adl.sectionOpened();
					boolean b = adl.readHeader(tokenizer);
					
				}
				// add checks for unrecognized sections and copy/skip according to instrumentation
				
			} /* unexpected keyword or data between sections. Illegal according to specification */
			else if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
				adl.addValidationError("UNEXPECTED KEYWORD FOUND INBETWEEN DOCUMENT SECTIONS. Token value: \"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
					
				//throw new EDMLParserException("Illegal keyword between document sections. Found " + tokenizer.tokenValue + " of type " + tokenizer.tokenType);
			}
			else if (tokenizer.tokenType == ADLTokenizer.TT_DATA)
			{
				adl.addValidationError("UNEXPECTED DATA FOUND INBETWEEN DOCUMENT SECTIONS. Token value: \"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
					
				//throw new EDMLParserException("Illegal data between document sections. Found " + tokenizer.tokenValue + " of type " + tokenizer.tokenType);

			} // end of if for TT_SECTION_START
			
		}	// end of while loop
		
		return adl;
	}
	
	public static boolean validateADLString (String aString)
	{
		boolean rval = false;
		
		if (aString.length() <= 255)
		{
			rval = true;
		}
		
		return rval;
	}
	
	public static String sanitizeEDMLQuotes (String aString)
	{
		return aString.replace("\"", (char)0x1B + "\"");
	}
	
	public static String sanitizeEDMLQuotesForXml (String aString)
	{
		return aString.replace("\"", "'");
	}
	
	/**
	*	Validates a String to confirm that it contains valid data for a boolean in an AES-31-3 document.
	*
	*	@param seqClean The String to validate for boolean formatting.
	*	@return True if the data is valid, false otherwise.
	*/
	public static  boolean validateADLBoolean (String seqClean)
	{
		boolean rval = false;
		
		if (seqClean.equalsIgnoreCase("TRUE") || seqClean.equalsIgnoreCase("FALSE"))
		{
			rval = true;
		}
		
		return rval;
	}
	
	public void close() throws IOException {
	}
	
}

/*
	-------------------------------------------------------------------------------
	BaseSection.java
	AES31-3

	Created by Kaylie Ackerman on 7/8/05.

	Copyright 2005 Kaylie Ackerman.
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

public abstract class BaseSection implements Cloneable, AES31Validation {
	public static final int TAG_NONE	= -1;
	public static final int TAG_OPEN	= -2;
	public static final int TAG_CLOSED	= -3;
	public String sectionName;
	private BaseSection _sectionParent;
	protected long rewindPoint;

	private	Properties  _extraData;			/* Capture all unexpected data for validation purposes */
	private Vector<String> _validationErrors;
	private	int	_tagState;
	protected boolean wellFormed;
	protected boolean valid;
	protected boolean throwExceptions = false;
	protected boolean stopOnExceptions = false;
		
	public BaseSection (String name)
	{
		_init(name);
	}
	
	private void _init (String name)
	{
		sectionName = name;
		_extraData = new Properties();
		_validationErrors = new Vector<String>();
		_tagState = TAG_NONE;
		rewindPoint = 0;
		_sectionParent = null;
	}
	
	public void setParent (BaseSection parent)
	{
		_sectionParent = parent;
	}
	
	public BaseSection getParent ()
	{
		return _sectionParent;
	}
	
	protected static boolean hasData (String keyword)
	{
		return false;
	}
	
	protected void sectionOpened ()
	{
		_tagState = TAG_OPEN;
	}
	
	protected void sectionClosed ()
	{
		if (_tagState != TAG_OPEN)
		{
			this.addValidationError("Close of tag encountered before open tag");
			
		}
		_tagState = TAG_CLOSED;
	}
	
	protected boolean isSectionOpen()
	{
		return (_tagState == TAG_OPEN);
	} 
	
	public void addValidationError (String s)
	{
		_validationErrors.addElement(s);
	}
	
	public int getNumValidationErrors ()
	{
		return _validationErrors.size();
	}
	
	/*
	* Here we catch overlapping tags. Either an (extra) misplaced close tag in a section
	* or a stray open tag in a section. If a section has 2 of its own close tag and one occurs
	* before all data has been read, then data acquisition will stop with the first occurance
	* of the tag.
	*/
	protected boolean readHeader (ADLTokenizer tokenizer) throws EDMLParserException, FileNotFoundException, IOException
	{	
		Vector data = null;
		String keyword = null;
		boolean rval = true;

		//   while the next token is not the end of THIS section and is not the end of the file.
		while ((tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END || !(tokenizer.tokenValue.equalsIgnoreCase(this.sectionName)))
																		&& tokenizer.tokenType != ADLTokenizer.TT_EOF)
		{
			if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
				keyword = tokenizer.tokenValue;
				data = tokenizer.allDataForCurrentKeyword();
				this.addData(keyword, data, tokenizer);
				
			}
			
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				if (throwExceptions)
				{
					throw new EDMLParserException("Found overlapping SECTION Tags. " + tokenizer.tokenValue +
																		" inside of " +	this.sectionName);
				}
				
				this.addValidationError("Found overlapping SECTION Tags. " + tokenizer.tokenValue +
																		" inside of " +	this.sectionName);
				this.wellFormed = false;
				rval = false;
				
				if (stopOnExceptions)
				{
					return rval;
				}
				
				if (rewindPoint == 0)
				{
					tokenizer.pushBack();
					rewindPoint = tokenizer.getTokenPos();
				}
			}
			
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
			{
				if (!tokenizer.tokenValue.equalsIgnoreCase(this.sectionName))
				{
					if (throwExceptions)
					{
						throw new EDMLParserException(tokenizer.tokenValue + " close tag found within " + this.sectionName + " SECTION");
					}
					
					this.addValidationError(tokenizer.tokenValue + " close tag found within " + this.sectionName + " SECTION");
					this.wellFormed = false;
					rval = false;
				
					if (stopOnExceptions)
					{
						return rval;
					}
				
				}
			}
			
			// catch raw data at the start of a SECTION that is not preceeded by a keyword
			else if (tokenizer.tokenType == ADLTokenizer.TT_DATA)
			{
				
				if (throwExceptions)
				{
					throw new EDMLParserException("Unexpected data found in " + this.sectionName + " section.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				}
				
				this.addValidationError("Unexpected data found in " + this.sectionName + " section.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				
				this.wellFormed = false;
				rval = false;
				
				if (stopOnExceptions)
				{
					return rval;
				}
				
			
			}
			
		} //end of while loop
		
		if (tokenizer.tokenValue.equalsIgnoreCase(this.sectionName))
		{
			this.sectionClosed();
		}
		
		if (this.isSectionOpen())
		{
			if (throwExceptions)
			{
				throw new EDMLParserException(this.sectionName + " close tag missing");
			}
			
			this.addValidationError(this.sectionName + " close tag missing");
			this.wellFormed = false;
			rval = false;
			
			if (stopOnExceptions)
			{
				return rval;
			}
			
		}
			
		if (rewindPoint > 0)
		{
			tokenizer.restartParseFrom(rewindPoint);
			rewindPoint = 0;
		}
		
		return rval;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
	
	}
	
	/**
	*	Prints to STD OUT all errors encountered with this document section while parsing an ADL file.
	*/
	public void printErrors ()
	{
		Enumeration e = _validationErrors.elements();
		while (e.hasMoreElements()) {
			String es = (String)e.nextElement();
			
			//debug
			System.out.println(es);
		}
	}
	
	public StringBuffer getErrors ()
	{
		StringBuffer rval = new StringBuffer();
		Enumeration e = _validationErrors.elements();
		while (e.hasMoreElements()) {
			String es = (String)e.nextElement();
			rval.append(es + "\n");
		}
		
		return rval;
	}

	protected void addExtraData (String key, String val)
	{
		_extraData.setProperty(key, val);
		
		Enumeration e = _extraData.propertyNames();
		while (e.hasMoreElements()) {
			String es = (String)e.nextElement();
			
			//debug
			//System.out.println("Anomolies:\n------------------\nKEY: \"" + es + "\" VALUE: \"" + _extraData.getProperty(es) + "\"\n------------------\n");
		}
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		BaseSection rval = (BaseSection)super.clone();
		return rval;
	}

}

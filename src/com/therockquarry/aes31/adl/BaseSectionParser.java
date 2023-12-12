//
//  BaseSectionParser.java
//  AES31
//
//  Created by Kaylie Ackerman on 9/25/06.
//  Copyright 2006 Kaylie Ackerman. All rights reserved.
//

package com.therockquarry.aes31.adl;

import java.io.*;
import java.util.*;

public class BaseSectionParser 
{
	public String sectionName;
	
	public enum TagState
	{
		TAG_NONE, TAG_OPEN, TAG_CLOSED
	}
	
	private Vector<String> _validationErrors;
	protected boolean _stopOnExceptions;
	protected boolean _throwExceptions;
	protected boolean _wellFormed;
	protected long _rewindPoint;
	protected TagState _tagState;
	
	public BaseSectionParser (String name)
	{
		_init(name);
	}
	
	private void _init(String name)
	{
		sectionName = name;
		_validationErrors = new Vector<String>();
		_stopOnExceptions = false;
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
				if (_throwExceptions)
				{
					throw new EDMLParserException("Found overlapping SECTION Tags. " + tokenizer.tokenValue +
																		" inside of " +	this.sectionName);
				}
				
				this.addValidationError("Found overlapping SECTION Tags. " + tokenizer.tokenValue +
																		" inside of " +	this.sectionName);
				this._wellFormed = false;
				rval = false;
				
				if (_stopOnExceptions)
				{
					return rval;
				}
				
				if (_rewindPoint == 0)
				{
					tokenizer.pushBack();
					_rewindPoint = tokenizer.getTokenPos();
				}
			}
			
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
			{
				if (!tokenizer.tokenValue.equalsIgnoreCase(this.sectionName))
				{
					if (_throwExceptions)
					{
						throw new EDMLParserException(tokenizer.tokenValue + " close tag found within " + this.sectionName + " SECTION");
					}
					
					this.addValidationError(tokenizer.tokenValue + " close tag found within " + this.sectionName + " SECTION");
					this._wellFormed = false;
					rval = false;
				
					if (_stopOnExceptions)
					{
						return rval;
					}
				
				}
			}
			
			// catch raw data at the start of a SECTION that is not preceeded by a keyword
			else if (tokenizer.tokenType == ADLTokenizer.TT_DATA)
			{
				
				if (_throwExceptions)
				{
					throw new EDMLParserException("Unexpected data found in " + this.sectionName + " section.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				}
				
				this.addValidationError("Unexpected data found in " + this.sectionName + " section.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				
				this._wellFormed = false;
				rval = false;
				
				if (_stopOnExceptions)
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
			if (_throwExceptions)
			{
				throw new EDMLParserException(this.sectionName + " close tag missing");
			}
			
			this.addValidationError(this.sectionName + " close tag missing");
			this._wellFormed = false;
			rval = false;
			
			if (_stopOnExceptions)
			{
				return rval;
			}
			
		}
			
		if (_rewindPoint > 0)
		{
			tokenizer.restartParseFrom(_rewindPoint);
			_rewindPoint = 0;
		}
		
		return rval;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
	
	}
	
	public void addValidationError (String s)
	{
		_validationErrors.addElement(s);
	}
	
	protected void sectionOpened ()
	{
		_tagState = TagState.TAG_OPEN;
	}
	
	protected void sectionClosed ()
	{
		if (_tagState != TagState.TAG_OPEN)
		{
			this.addValidationError("Close of tag encountered before open tag");
			
		}
		_tagState = TagState.TAG_CLOSED;
	}
	
	protected boolean isSectionOpen()
	{
		return (_tagState == TagState.TAG_OPEN);
	} 
}

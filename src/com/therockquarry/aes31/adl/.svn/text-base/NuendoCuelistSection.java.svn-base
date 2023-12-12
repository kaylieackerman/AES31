/*
	-------------------------------------------------------------------------------
	NuendoCuelistSection.java
	AES31-3

	Created on 1/9/07.

	Copyright 2007 David Ackerman.
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

public class NuendoCuelistSection extends BaseSection implements Cloneable
{
	private ArrayList<NuendoMarkerPoint> _markers;
	
	public NuendoCuelistSection ()
	{
		super ("NUENDO_CUELIST");
		_init ();
	}
	
	
	
	public void _init ()
	{
		_markers = new ArrayList<NuendoMarkerPoint> ();
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			_markers.add(new NuendoMarkerPoint((String)data.elementAt(0), (String)data.elementAt(1), this));
		}
		catch(InvalidDataException e)
		{
			e.printStackTrace();
		}
	}
	
	protected boolean readHeader (ADLTokenizer tokenizer) throws EDMLParserException, FileNotFoundException, IOException
	{
		Vector data = null;
		boolean rval = true;
		
		while ((tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END || !(tokenizer.tokenValue.equalsIgnoreCase(this.sectionName)))
																		&& tokenizer.tokenType != ADLTokenizer.TT_EOF)
		
		{
			if (tokenizer.tokenType == ADLTokenizer.TT_DATA)
			{
				try
				{
					data = new Vector();
					data.add(tokenizer.getTokenValue());
					data.add(tokenizer.nextTokenValue());
					this.addData(null, data, tokenizer);
				}
				catch (ADLTokenizerException e)
				{
					e.printStackTrace();
				}
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
		}
		
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
	
	public void addMarker (NuendoMarkerPoint nmp)
	{
		_markers.add(nmp);
	}
	
	public ArrayList<NuendoMarkerPoint> getMarkers ()
	{
		return _markers;
	}
	
	public void resample (double sr)
	{
		for (NuendoMarkerPoint nmp : _markers)
		{
			nmp.resample(sr);
		}
	}
	
	public String toString ()
	{
		String rval = "\n<NUENDO_CUELIST>";
		for (NuendoMarkerPoint nmp : _markers)
		{
			rval = rval.concat(nmp.toString());
		}
		rval = rval.concat("\n</NUENDO_CUELIST>");
		
		return rval;
	}
	
	
}

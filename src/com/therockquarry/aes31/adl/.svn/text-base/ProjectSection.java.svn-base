/*
	-------------------------------------------------------------------------------
	ProjectSection.java
	AES31-3

	Created on 7/7/05.

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

import java.net.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>ProjectSection</code> object contains all of the project level information for this Audio Decision List.
*	The ADL specification is available from the Audio Engineering Society, 
*	<a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class ProjectSection extends BaseSection implements Cloneable {
	private String		_proj_title;		/* Project Title */
	private String		_proj_originator;	/* Originating Facility */
	private SimpleDate	_proj_create_date;	/* Creation date and time */
	private String		_proj_notes;		/* Generic comment string */
	private String		_proj_client_data;	/* URL for external file */
	

	/**
	*	Constructs a new empty <code>ProjectSection</code> object. 
	*/
	public ProjectSection ()
	{
		super ("PROJECT");
		_init ();
	}
	
	private void _init ()
	{
		_proj_title = null;
		_proj_originator = null;
		_proj_create_date = new SimpleDate ();
		_proj_notes = null;
		_proj_client_data = null;
	}
	
	/**
	*	Sets the title of the project contained in this ADL.
	*
	*	@param projTitle The title of this project.
	*/
	public void setProjTitle (String projTitle) throws InvalidDataException
	{
		if (projTitle.equals("_"))
		{
			_proj_title = null;
			return;
		}
		
		if (EDMLParser.validateADLString(projTitle))
		{
			_proj_title = projTitle;
		}
		else
		{
			throw new InvalidDataException("\"" + projTitle + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the title of the project contained in this ADL.
	*
	*	@return  The title of this project.
	*/
	public String getProjTitle ()
	{
		return _proj_title;
	}
	
	/**
	*	Sets the originator of the project contained in this ADL.
	*
	*	@param originator The originator facility of this project.
	*/
	public void setProjOriginator (String originator) throws InvalidDataException
	{
		if (originator.equals("_"))
		{
			_proj_originator = null;
			return;
		}
		
		if (EDMLParser.validateADLString(originator))
		{
			_proj_originator = originator;
		}
		else
		{
			throw new InvalidDataException("\"" + originator + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the originator of the project contained in this ADL.
	*
	*	@return  The originator facility of this project.
	*/
	public String getProjOriginator ()
	{
		return _proj_originator;
	}
	
	/**
	*	Sets the Create Date of the project contained in this ADL. Note that this is a convenience method for excepting the time and date
	*	as a String object.
	*
	*	@param timeDate The time and date that this ADL was created.
	*/
	public void setCreateDate (String timeDate) throws InvalidDataException
	{
		_proj_create_date.setTimeDate(timeDate);
		
	}
	
	/**
	*	Sets the Create Date of the project contained in this ADL. Note that this is a convenience method for excepting the time and date
	*	as a Date object.
	*
	*	@param timeDate The time and date that this ADL was created.
	*/
	public void setCreateDate (Date timeDate)
	{
		_proj_create_date.setTimeDate(timeDate);
	}
	
	/**
	*	Sets the Create Date of the project contained in this ADL.
	*
	*	@param timeDate A SimpleDate object contianing the time and date that this ADL was created.
	*/
	public void setCreateDate (SimpleDate timeDate)
	{
		_proj_create_date = timeDate;
	}
	
	/**
	*	Returns the Create Date of the project contained in this ADL.
	*
	*	@return  A SimpleDate object contianing the time and date that this ADL was created.
	*/
	public SimpleDate getCreateDate ()
	{
		return _proj_create_date;
	}
	
	/**
	*	Sets the project notes for the project contained in this ADL.
	*
	*	@param projectNotes A String representing the project Notes associated with this ADL project.
	*/
	public void setProjNotes (String projectNotes) throws InvalidDataException
	{
		if (projectNotes.equals("_"))
		{
			_proj_notes = null;
			return;
		}
		
		if (EDMLParser.validateADLString(projectNotes))
		{
			_proj_notes = projectNotes;
		}
		else
		{
			throw new InvalidDataException("\"" + projectNotes + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the Project Notes from the project contained in this ADL.
	*
	*	@return  A String object containing the project note field from this ADL document.
	*/
	public String getProjNotes ()
	{
		return _proj_notes;
	}
	
	/**
	*	Sets the project client data for the project contained in this ADL.
	*
	*	@param projClientData A String representing the project client data associated with this ADL project.
	*/
	public void setProjClientData (String projClientData) throws InvalidDataException
	{
		if (projClientData.equals("_"))
		{
			_proj_client_data = null;
			return;
		}
		
		if (EDMLParser.validateADLString(projClientData))
		{
			_proj_client_data = projClientData;
		}
		else
		{
			throw new InvalidDataException("\"" + projClientData + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the project client data from the project contained in this ADL.
	*
	*	@return  A String object containing the project client data field from this ADL document.
	*/
	public String getProjClientData ()
	{
		return _proj_client_data;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("PROJ_TITLE"))
			{
				this.setProjTitle((String)data.elementAt(0));
			} 
			else if (keyword.equalsIgnoreCase("PROJ_ORIGINATOR"))
			{
				this.setProjOriginator((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("PROJ_CREATE_DATE"))
			{
				this.setCreateDate((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("PROJ_NOTES"))
			{
				this.setProjNotes((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("PROJ_CLIENT_DATA"))
			{
				this.setProjClientData((String)data.elementAt(0));
			}
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	/*protected void readHeader (ADLTokenizer tokenizer) throws EDMLParserException
	{
		while ((tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END || !(tokenizer.tokenValue.equals(this.sectionName)))
			&& tokenizer.tokenType != ADLTokenizer.TT_EOF)
		{
			if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
				
			}
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				throw new EDMLParserException("Found overlapping SECTION Tags for " + this.sectionName + " and " + tokenizer.tokenValue);
			}
			else if (tokenizer.tokenType != ADLTokenizer.TT_COMMENT)
			{
				this.addValidationError("UNEXPECTED DATA FOUND IN PROJECT HEADER. Token value: \"" + 
							tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				this.addExtraData("Line: " + String.valueOf(tokenizer.getLineNumber()) + " " + tokenizer.tokenValue, tokenizer.tokenValue);
			}
		}*/
		/* capture close of section tage for validation purposes */
		/*if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
		{
			if (tokenizer.tokenValue.equals(this.sectionName))
			{
				this.sectionClosed();
			} 
			else
			{
				//throw new EDMLParserException(this.sectionName + " close tag not found");
			}
			//debug
			//System.out.println(rval);
		}
	}*/
	
	public String toString ()
	{
		String rval = "\n<PROJECT>\n\t(PROJ_TITLE)\t\t" + (_proj_title == null ? "\"\"" : "\"" +  EDMLParser.sanitizeEDMLQuotes(_proj_title) + "\"") +
					"\n\t(PROJ_ORIGINATOR)\t" + (_proj_originator == null ? "\"\"" : "\"" +  EDMLParser.sanitizeEDMLQuotes(_proj_originator) + "\"") +
					"\n\t(PROJ_CREATE_DATE)\t" + _proj_create_date +
					"\n\t(PROJ_NOTES)\t\t" + (_proj_notes == null ? "\"\"" : "\"" + EDMLParser.sanitizeEDMLQuotes(_proj_notes) + "\"") +
					"\n\t(PROJ_CLIENT_DATA)\t" + (_proj_client_data == null ? "\"\"" : "\"" +  EDMLParser.sanitizeEDMLQuotes(_proj_client_data) + "\"") +
					"\n</PROJECT>";
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("project");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("projTitle");
		e.setNamespace(ADLSection.aes);
		e.setText(_proj_title == null ? "" : _proj_title);
		vs.addContent(e);
		
		e = new Element("projOriginator");
		e.setNamespace(ADLSection.aes);
		e.setText(_proj_originator == null ? "" : _proj_originator);
		vs.addContent(e);
		
		e = new Element("projCreateDate");
		e.setNamespace(ADLSection.aes);
		e.setText(_proj_create_date.toString());
		vs.addContent(e);
		
		e = new Element("projNotes");
		e.setNamespace(ADLSection.aes);
		e.setText(_proj_notes == null ? "" : _proj_notes);
		vs.addContent(e);
		
		e = new Element("projClientData");
		e.setNamespace(ADLSection.aes);
		e.setText(_proj_client_data == null ? "" : _proj_client_data);
		vs.addContent(e);
		
		return vs;
	}
	
	
	public Object clone () throws CloneNotSupportedException
	{
		ProjectSection rval = (ProjectSection)super.clone();
		if (_proj_create_date != null)
		{
			rval._proj_create_date = (SimpleDate)this._proj_create_date.clone();
			
		}
		return rval;
	}
	
	
}

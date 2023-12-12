/*
	-------------------------------------------------------------------------------
	VersionSection.java
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

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>VersionSection</code> object contains all of the version information for this Audio Decision List. It consists of five
*	identifiers/version numbers. The ADL specification is available from the Audio Engineering Society, 
*	<a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class VersionSection extends BaseSection implements Cloneable {
	
	private String _adl_id;					/* Universal ID of ADL format */
	private UUID _adl_uid;					/* Unique identifier for this list */
	private EDMLVersion _ver_adl_version;	/* ADL format version */
	private String _ver_creator;			/* Creator application */
	private EDMLVersion _ver_crtr;			/* Creator version */
	private final static String[] _dataFields = {"ADL_ID", "ADL_UID", "VER_ADL_VERSION", "VER_CREATOR", "VER_CRTR"};
	
	/**
	*	Universal ID of ADL format, "06,64,43,52,01,01,01,04,01,02,03,04,"
	*/
	public static final String ADL_ID_SMPTE_LABEL = "06,64,43,52,01,01,01,04,01,02,03,04,";
	
	/**
	*	Constructs a new default <code>VersionSection</code> object. 
	*	By default:<ul>
	*	<li>the ADL ID is set to the properly defined SMPTE LABEL</li>
	*	<li>the ADL UID is set to a randomly generated UUID</li>
	*	<li>the VER ADL VERSION is set to "01.01"</li>
	*	<li>the VER CREATOR is set to "AES31-3"</li>
	*	<li>the VER CRTR is set to "01.01"</li></ul>
	*/
	public VersionSection ()
	{
		super ("VERSION");
		_init ();
		
		//debug
		//System.out.println ("VERSION CREATED");
	}
	
	private void _init ()
	{
		_adl_id = ADL_ID_SMPTE_LABEL;
		_adl_uid = UUID.randomUUID();
		_ver_adl_version = new EDMLVersion(1, 1);
		_ver_creator = "AES31-3";
		_ver_crtr = new EDMLVersion(1, 1);
	}
	
	protected static boolean hasData (String keyword)
	{
		for (int i = 0; i < _dataFields.length; i++)
		{
			if (keyword.equalsIgnoreCase(_dataFields[i]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private void setAdlId (String adlId) throws InvalidDataException
	{
		if (adlId.equals(ADL_ID_SMPTE_LABEL) )	//validate ID
		{
			_adl_id = adlId;
		}
		else
		{
			throw new InvalidDataException (adlId + " is not the correct Universal ID for the ADL format");
		}
		
		//debug
		//System.out.println("ADL_ID: " + _adl_id);
	}
	
	/**
	*	Returns the ADL ID for this document, as defined by the Society of Motion Pictures and Television Engineers (SMPTE). This value should be
	*	constant for all ADL documents. It is read from ADL files for validation purposes, and provided by default when creating a new instance
	*	of a <code>VersionSection</code>. The value is also available as {@link #ADL_ID_SMPTE_LABEL}.
	*/
	public String getAdlId ()
	{
		return _adl_id;
	}
	
	/**
	*	Sets a unique identifier for this ADL document using an immutable universally unique identifier (UUID).
	*
	*	@param uuid The unique identifier for this ADL document.
	*/
	public void setAdlUid (UUID uuid)
	{
		_adl_uid = uuid;
	}
	
	/**
	*	Sets a unique identifier for this ADL document using an immutable universally unique identifier (UUID).
	*
	*	@param uuid A String representing a unique identifier for this ADL document. The String must conform to the 128-bit UUID specification
	*	as defined by ISO/IEC 11578:1996.
	*/
	public void setAdlUid (String uuid) throws InvalidDataException
	{
		try
		{
			_adl_uid = UUID.fromString(uuid);
		}
		catch (IllegalArgumentException e)
		{
			throw new InvalidDataException("\""+e.getMessage()+"\" encountered while creating UUID");
		}
	}
	
	/**
	*	Returns the unique identifier for this ADL document.
	*
	*	@return The unique identifier for this ADL document.
	*/
	public UUID getAdlUid ()
	{
		return _adl_uid;
	}
	
	/**
	*	Sets the version string for the EDML markup used in this ADL.
	*
	*	@param versionString A string representing the version of the EDML markup language used in this ADL. The string may be up to 14 
	*	characters including point separators having the format MJ.MI(.BG)(.ST)(.RV), where components in () are optional. For additional 
	*	information, consult the AES-31-3 Standard.
	*/
	public void setVerAdlVersion (String versionString) throws InvalidDataException
	{
		_ver_adl_version = new EDMLVersion(versionString);
	}
	
	/**
	*	Sets the version string for the EDML markup used in this ADL.
	*
	*	@param verAdlVersion A EDMLVersion object representing the version of the EDML markup language used in this ADL. 
	*/
	public void setVerAdlVersion (EDMLVersion verAdlVersion)
	{
		_ver_adl_version = verAdlVersion;
	}
	
	/**
	*	Returns the version of the Edit Decision Markup Language (EDML) used in this ADL document.
	*
	*	@return The version identifier for the EDML used in this ADL document.
	*/
	public EDMLVersion getVerAdlVersion ()
	{
		return _ver_adl_version;
	}
	
	/**
	*	Sets the name of the application used to create this ADL Document.
	*
	*	@param versionCreator The name of the application that created this ADL document.
	*/
	public void setVerCreator (String versionCreator) throws InvalidDataException
	{
		if (EDMLParser.validateADLString(versionCreator))
		{
			_ver_creator = versionCreator;
		}
		else
		{
			throw new InvalidDataException("\"" + versionCreator + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Gets the name of the application used to create this ADL Document.
	*
	*	@return The name of the application that created this ADL document.
	*/
	public String getVerCreator ()
	{
		return _ver_creator;
	}
	
	/**
	*	Sets the version string for the Creator Application that produced this ADL.
	*
	*	@param versionString A string representing the version of the Creator Application that produced this ADL. The string may be up to 14 
	*	characters including point separators having the format MJ.MI(.BG)(.ST)(.RV), where components in () are optional. For additional 
	*	information, consult the AES-31-3 Standard.
	*/
	public void setVerCrtr (String versionString) throws InvalidDataException
	{
		_ver_crtr = new EDMLVersion(versionString);
	}
	
	/**
	*	Sets the version for the Creator Application that produced this ADL.
	*
	*	@param ver_crtr A <code>EDMLVersion</code> object representing the version of the Creator Application that produced this ADL.
	*/
	public void setVerCrtr (EDMLVersion ver_crtr)
	{
		_ver_crtr = ver_crtr;
	}
	
	/**
	*	Gets the version for the Creator Application that produced this ADL.
	*
	*	@return The version of the application that created this ADL document.
	*/
	public EDMLVersion getVerCrtr ()
	{
		return _ver_crtr;
	}
	
	
	/**
	*	Returns a String representation of this <code>VersionSection</code>.
	*
	*	@return A String representation of this <code>VersionSection</code>.
	*/
	public String toString ()
	{
		String rval = "\n<VERSION>\n\t(ADL_ID)\t\t\"" + _adl_id + "\"\n\t(ADL_UID)\t\t" + _adl_uid.toString() + "\n\t(VER_ADL_VERSION)\t" + _ver_adl_version.toString() +
			"\n\t(VER_CREATOR)\t\t\"" + EDMLParser.sanitizeEDMLQuotes(_ver_creator) + "\"" + "\n\t(VER_CRTR)\t\t" + _ver_crtr.toString() + "\n</VERSION>";
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("version");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("adlId");
		e.setNamespace(ADLSection.aes);
		e.setText(_adl_id);
		vs.addContent(e);
		
		e = new Element("adlUid");
		e.setNamespace(ADLSection.aes);
		e.setText(_adl_uid.toString());
		vs.addContent(e);
		
		e = new Element("verAdlVersion");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_ver_adl_version.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("verCreator");
		e.setNamespace(ADLSection.aes);
		e.setText(_ver_creator);
		vs.addContent(e);
		
		e = new Element("verCrtr");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_ver_crtr.getXmlAttributes());
		vs.addContent(e);
		
		return vs;
	}
	
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("ADL_ID"))
			{
				this.setAdlId((String)data.elementAt(0));
			} 
			else if (keyword.equalsIgnoreCase("ADL_UID"))
			{
				this.setAdlUid((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("VER_ADL_VERSION"))
			{
				this.setVerAdlVersion((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("VER_CREATOR"))
			{
				this.setVerCreator((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("VER_CRTR"))
			{
				this.setVerCrtr((String)data.elementAt(0));
			}
			
			// add checks for unrecognized keywords and data (by checking size of vector)
			
			// also check for duplicate (non-repeatable) keywords and pass error and
			// store keyword and data so user can choose which value to output.
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
			//e.printStackTrace();
		}
	}
	
	/**
	*	Creates and returns a deep clone of this <code>VersionSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		VersionSection rval = (VersionSection)super.clone();
		
		if (_ver_adl_version != null)
		{
			rval._ver_adl_version = (EDMLVersion)this._ver_adl_version.clone();
		}
		
		if (_ver_crtr != null)
		{
			rval._ver_crtr = (EDMLVersion)this._ver_crtr.clone();
		}
		
		if (_adl_uid != null)
		{
			rval._adl_uid = UUID.fromString(_adl_uid.toString());
		}
		
		return rval;
	}
	
	/*protected void readHeader (ADLTokenizer tokenizer) throws EDMLParserException
	{
		
		
	}*/
	
}

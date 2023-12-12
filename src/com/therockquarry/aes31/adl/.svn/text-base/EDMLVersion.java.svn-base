/*
	-------------------------------------------------------------------------------
	EDMLVersion.java
	AES31-3

	Created by David Ackerman on 6/14/06.

	Copyright 2006 David Ackerman.
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

import java.text.*;
import java.util.*;
import java.util.regex.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;


/**
*	The <code>EDMLVersion</code> class encapsulates the version information for an EDML implementation. It is divided into
*	2 required and 3 optional fields as defined in ADL specification. The ADL specification
*	is available from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class EDMLVersion implements Cloneable {
	private int _majorRev;
	private int _minorRel;
	private int _bugRev;
	private int _stageRev;
	private int _rev;
	
	private final static int REV = 4;
	private final static int STAGE = 3;
	private final static int BUG = 2;
	private final static int MINOR = 1;
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object with the required major version number, minor release number,
	*	and the optional bug revision (point release) number, the stage number and the revision of non-release code.
	*/
	public EDMLVersion (int majorRev, int minorRel, int bugRev, int stageRev, int rev)
	{
		_majorRev = majorRev;
		_minorRel = minorRel;
		_bugRev = bugRev;
		_stageRev = stageRev;
		_rev = rev;
	}
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object with the required major version number, minor release number,
	*	and the optional bug revision (point release) number and the stage number.
	*/
	public EDMLVersion (int majorRev, int minorRel, int bugRev, int stageRev)
	{
		this (majorRev, minorRel, bugRev, stageRev, 0);
	}
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object with the required major version number, minor release number,
	*	and the optional bug revision (point release) number.
	*/
	public EDMLVersion (int majorRev, int minorRel, int bugRev)
	{
		this (majorRev, minorRel, bugRev, 0, 0);
	}
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object with the required major version number and minor release number.
	*/
	public EDMLVersion (int majorRev, int minorRel)
	{
		this (majorRev, minorRel, 0, 0, 0);
	}
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object with the required major version number and minor release number each set to a default value of 01.
	*/
	public EDMLVersion ()
	{
		this (1, 1, 0, 0, 0);
	}
	
	/**
	*	Initializes a new <code>EDMLVersion</code> object from a String	object representing the EDML version. <code>versionString</code>
	*	must conform to the syntax specification defined in the AES31-3 Standard.
	*/
	public EDMLVersion (String versionString) throws InvalidDataException
	{
		if (validateEDMLVersionString(versionString))
		{
			setAllComponentsFromString (versionString);		
		}
		else
		{
			throw new InvalidDataException("The String \""+versionString+"\" does not conform to the formatting rules defined in the AES-31-3 Specification for version information.");
		}
	}
	
	/**
	*	Validates a String object to confirm that it adhears to the formatting rules for a version field for an AES-31-3 document.
	*
	*	@param versionString The String to validate for adhearance to AES-31-3 Version Field formatting rules.
	*	@return True if the String has a valid format, false otherwise.
	*/
	public boolean validateEDMLVersionString (String versionString)
	{
		boolean rval = false;
		String myPattern = "\\d{1,2}\\.\\d{1,2}(\\.\\d{1,2})?(\\.\\d{1,2})?(\\.\\d{1,2})?";
		Pattern pattern = Pattern.compile(myPattern);
		Matcher matcher = pattern.matcher(versionString);
		if (matcher.matches())
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Sets all values for version components from <code>versionString</code>. Any omitted trailing values are reset to 0 by this operation.
	*
	*	@param versionString A String object conforming to the AES-31-3 specification for EDML Version information.
	*/
	public void setAllComponentsFromString (String versionString) throws InvalidDataException
	{
		versionString = versionString.trim();
		String[] strComponents = versionString.split("\\.");
		
		/* validate string components*/
		if (strComponents.length < 2)
		{
			throw new InvalidDataException("EDML Version string must have a Major Revision and a Minor Release number separated by a \".\". For example \"01.01\"");
		}
		
		if (strComponents.length > 5)
		{
			throw new InvalidDataException("EDML Version string must have no more than 5 components separated by \".\" characters.");
		}
		
		int test;
		int[] tmp = {0, 0, 0, 0, 0};
		
		for (int i = 0; i < strComponents.length; i++)
		{
			try
			{
				test = Integer.parseInt(strComponents[i]);
				tmp[i] = test;
			}
			catch (NumberFormatException e)
			{
				throw new InvalidDataException("EDML Version string must be comprised of integer components separated by \".\" characters.");
			}
		}
		
		setMajorRevision(tmp[0]);
		setMinorRelease(tmp[1]);
		setBugRevision(tmp[2]);
		setStageRevision(tmp[3]);
		setRevision(tmp[4]);
	}
	
	/**
	*	Sets the value for the Major Revision field.
	*
	*	@param majorRev An int that represents the major revision number in this <code>EDMLVersion</code> object.
	*/
	public void setMajorRevision (int majorRev)
	{
		_majorRev = majorRev;
	}
	
	/**
	*	Sets the value for the Minor Release field.
	*
	*	@param minorRel An int that represents the minor release number in this <code>EDMLVersion</code> object.
	*/
	public void setMinorRelease (int minorRel)
	{
		_minorRel = minorRel;
	}
	
	/**
	*	Sets the value for the Bug Revision (point release) field.
	*
	*	@param bugRev An int that represents the bug revision number in this <code>EDMLVersion</code> object.
	*/
	public void setBugRevision (int bugRev)
	{
		_bugRev = bugRev;
	}
	
	/**
	*	Sets the value for the Stage Revision field.
	*
	*	@param stage An int that represents the stage number in this <code>EDMLVersion</code> object.
	*/
	public void setStageRevision (int stage)
	{
		_stageRev = stage;
	}
	
	/**
	*	Sets the value for the  Revision for non-released code field.
	*
	*	@param rev An int that represents the revision for non-released code number in this <code>EDMLVersion</code> object.
	*/
	public void setRevision (int rev)
	{
		_rev = rev;
	}
	
	/**
	*	Gets the value for the Major Revision field.
	*
	*	@return The major revision number in this <code>EDMLVersion</code> object.
	*/
	public int getMajorRevision ()
	{
		return _majorRev;
	}
	
	/**
	*	Gets the value for the Minor Release field.
	*
	*	@return The minor release number in this <code>EDMLVersion</code> object.
	*/
	public int getMinorRelease ()
	{
		return _minorRel;
	}
	
	/**
	*	Gets the value for the Bug Revision (point release) field.
	*
	*	@return The bug revision number in this <code>EDMLVersion</code> object.
	*/
	public int getBugRevision ()
	{
		return _bugRev;
	}
	
	/**
	*	Gets the value for the Stage Revision field.
	*
	*	@return The stage number in this <code>EDMLVersion</code> object.
	*/
	public int getStageRevision ()
	{
		return _stageRev;
	}
	
	/**
	*	Gets the value for the  Revision for non-released code field.
	*
	*	@return The revision for non-released code number in this <code>EDMLVersion</code> object.
	*/
	public int getRevision ()
	{
		return _rev;
	}
										
										
	public ArrayList<org.jdom.Attribute> getXmlAttributes ()
	{
		ArrayList<org.jdom.Attribute> rval = new ArrayList<org.jdom.Attribute>();
		Attribute a;
		a = new Attribute("majorVersionNumber", Integer.toString(this.getMajorRevision()));
		rval.add(a);
		a = new Attribute("minorReleaseNumber", Integer.toString(this.getMinorRelease()));
		rval.add(a);
		a = new Attribute("bugRevisionNumber", Integer.toString(this.getBugRevision()));
		rval.add(a);
		a = new Attribute("stageNumber", Integer.toString(this.getStageRevision()));
		rval.add(a);
		a = new Attribute("revisionNumber", Integer.toString(this.getRevision()));
		rval.add(a);
		
		return rval;
	}
	
	/**
	*	Returns a String representation of this <code>EDMLVersion</code> object. The length of the String
	*	is set to exclude all unused optional fields from right to left.
	*
	*	@return A String representation of this <code>EDMLVersion</code> object.
	*/
	public String toString ()
	{
		int lastField = MINOR;
		// determine last field used in version string
		if (_rev > 0)
		{
			lastField = REV;
		}
		else if (_stageRev > 0)
		{
			lastField = STAGE;
		}
		else if (_bugRev > 0)
		{
			lastField = BUG;
		}
		
		DecimalFormat form2 = new DecimalFormat("00");
		String rval;
		
		rval = form2.format(_majorRev);
		rval = rval.concat(".");
		rval = rval.concat(form2.format(_minorRel));
		if (lastField >= BUG)
		{
			rval = rval.concat(".");
			rval = rval.concat(form2.format(_bugRev));
		}
		if (lastField >= STAGE)
		{
			rval = rval.concat(".");
			rval = rval.concat(form2.format(_stageRev));
		}
		if (lastField == REV)
		{
			rval = rval.concat(".");
			rval = rval.concat(form2.format(_rev));
		}
		
		return rval;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		EDMLVersion rval = (EDMLVersion)super.clone();
		
		return rval;
	}
}

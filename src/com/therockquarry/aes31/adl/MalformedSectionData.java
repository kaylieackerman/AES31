/*
	-------------------------------------------------------------------------------
	MalformedSectionData.java
	AES31-3

	Created on 9/25/05.

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

public class MalformedSectionData implements Cloneable{

	private Hashtable<String, Vector> _looseData;
	private String _mostLikelySection;
	private boolean _closed;
	
	public MalformedSectionData ()
	{
		_init ();
	}

	private void _init ()
	{
		_looseData = new Hashtable<String, Vector>();
		_mostLikelySection = null;
		_closed = false;
	}
	
	public boolean add (String keyword, Vector data)
	{
		boolean rval = false;
		
		if (keyword != null && data != null)
		{
			_looseData.put (keyword, data);
			rval = true;
		}
		
		System.out.println("ADDING DATA FOR KEYWORD:  " + keyword);
		
		return rval;
	}
	
	public void setLikelySection (String name)
	{
		_mostLikelySection = name;
		_closed = true;
		System.out.println("SETTING LIKELY SECTION TO " + _mostLikelySection);
		System.out.println("MALFORMED SECTION CLOSED");
	}
	
	public boolean isClosed ()
	{
		return _closed;
	}
	
	
	//change this to return a profile for this object that can be matched again known profiles
	public void guessSectionFromData ()
	{
		BaseSection target = null;
		
		if (_mostLikelySection.equalsIgnoreCase("VERSION"))
		{
			target = new VersionSection ();
		}
		
		Enumeration e = _looseData.keys();
		while (e.hasMoreElements())
		{
			if (target.hasData((String)e.nextElement()))
			{
			
			}
		}
		
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		MalformedSectionData rval = (MalformedSectionData)super.clone();
		rval._looseData = (Hashtable)_looseData.clone();
		return rval;
	}

}

/*
	-------------------------------------------------------------------------------
	NuendoMarkerPoint.java
	AES31-3

	Created on 2/15/07.

	Copyright 2007 Kaylie Ackerman.
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

public class NuendoMarkerPoint 
{
	protected TcfToken _dest_in;
	protected String _marker_name;
	protected NuendoCuelistSection _parent;
	
	public NuendoMarkerPoint ()
	{
		_init();
	}
	
	public NuendoMarkerPoint (String d_i, String n, NuendoCuelistSection p) throws InvalidDataException
	{
		_init();
		setDestIn(d_i);
		setMarkerName(n);
		setParent(p);
	}
	
	public NuendoMarkerPoint (TcfToken d_i, String n, NuendoCuelistSection p) throws InvalidDataException
	{
		_init();
		setDestIn(d_i);
		setMarkerName(n);
		setParent(p);
	}
	
	protected void _init ()
	{
		_dest_in = null;
		_marker_name = null;
		_parent = null;
	}
	
	public void setParent (NuendoCuelistSection p)
	{
		_parent = p;
	}
	
	public NuendoCuelistSection getParent ()
	{
		return _parent;
	}
	
	public void setDestIn (TcfToken di)
	{
		_dest_in = di;
	}
	
	public void setDestIn (String di) throws InvalidDataException
	{
		_dest_in = new TcfToken(di);
	}
	
	public TcfToken getDestIn ()
	{
		return _dest_in;
	}
	
	public void setMarkerName (String n) throws InvalidDataException
	{
		if (EDMLParser.validateADLString(n))
		{
			if (!n.equals("_"))
			{
				_marker_name = n;
			}
			else
			{
				_marker_name = null;
			}
		}
		else
		{
			throw new InvalidDataException("Marker name \"" + n + "\" exceeds the maximum allowable length for an ADL String.");
		}
	}
	
	public String getMarkerName ()
	{
		return _marker_name;
	}
	
	public String toString ()
	{
		String rval = "\n\t" + _dest_in + "\t" + (_marker_name != null ? ("\"" +  EDMLParser.sanitizeEDMLQuotes(_marker_name) + "\"") : "_");
		return rval;
	}
	
	public int compareTo (NuendoMarkerPoint o)
	{
		
		int rval = 0;
		
		if (this.equals(o))
		{
			return rval;
		}
		else if ((rval = _dest_in.compareTo(o._dest_in)) != 0)
		{
			return rval;
		}
		else
		{
			rval = _marker_name.compareTo(o._marker_name);
		}
		
		
		return rval;
	}
	
	public void resample (double sr)
	{
		if (_dest_in != null)
		{
			_dest_in.resample(sr);
		}
	}
	
	
}

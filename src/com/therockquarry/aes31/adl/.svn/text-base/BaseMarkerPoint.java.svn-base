/*
	-------------------------------------------------------------------------------
	BaseMarkerPoint.java
	AES31-3

	Created by David Ackerman on 12/12/06.

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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public abstract class BaseMarkerPoint implements Cloneable, Comparable<BaseMarkerPoint>
{
	protected Range _dest_channel_range;
	protected TcfToken _dest_in;
	protected TcfToken _dest_out;
	protected String _marker_name;
	protected MarkerListSection _parent;
	
	public BaseMarkerPoint ()
	{
		_init();
	}
	
	protected void _init ()
	{
		_dest_channel_range = null;
		_dest_in = null;
		_dest_out = null;
		_marker_name = null;
	}
	
	public void setParent (MarkerListSection p)
	{
		_parent = p;
	}
	
	public MarkerListSection getParent ()
	{
		return _parent;
	}
	
	public void setDestChannelRange (Range dcr)
	{
		_dest_channel_range = dcr;
	}
	
	public void setDestChannelRange (String dcr) throws InvalidDataException
	{
		try
		{
			if (dcr.matches("[0-9]+[-][0-9]+"))
			{
				_parent.addValidationError("In " + _parent.sectionName + " Marker Dest Channel range uses '-'. Should use '~'");
				dcr = dcr.replaceAll("-", "~");
			}
			String[] tmp = dcr.split("[~]");
			if (tmp.length > 2)
			{
				_parent.addValidationError("In " + _parent.sectionName + " Marker Dest Channel range is malformed");
			} 
			else if (tmp.length == 2)
			{

				_dest_channel_range = new Range(dcr);
			} 
			else 
			{
				_dest_channel_range = new Range(Integer.parseInt(dcr), Integer.parseInt(dcr));
			
			}
			
			
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Marker dest channel attribute malformed.", e);
		}
	}
	
	public Range getDestChannelRange ()
	{
		return _dest_channel_range;
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
	
	public void setDestOut (TcfToken d)
	{
		_dest_out = d;
	}
	
	public void setDestOut (String d) throws InvalidDataException
	{
		if (!d.equals("_"))
		{
			_dest_out = new TcfToken(d);
		}
		else
		{
			_dest_out = null;
		}
	}
	
	public TcfToken getDestOut ()
	{
		return _dest_out;
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
		String rval = _dest_channel_range + " " + _dest_in + " " + (_dest_out != null ? _dest_out : "_") + " " + 
					(_marker_name != null ? ("\"" +  EDMLParser.sanitizeEDMLQuotes(_marker_name) + "\"") : "_");
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		return null;
	}
	
	public int compareTo (BaseMarkerPoint o)
	{
		
		int rval = 0;
		
		if (this.equals(o))
		{
			return rval;
		}
		else if ((rval = _dest_channel_range.compareTo(o._dest_channel_range)) != 0)
		{
			return rval;
		}
		else if ((rval = _dest_in.compareTo(o._dest_in)) != 0)
		{
			return rval;
		}
		else if (_dest_out == null && o._dest_out != null)
		{
			return -1;
		}
		else if (_dest_out != null && o._dest_out == null)
		{
			return 1;
		}
		else if ((_dest_out != null && o._dest_out != null) && ((rval = _dest_out.compareTo(o._dest_out)) != 0))
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
		
		if (_dest_out != null)
		{
			_dest_out.resample(sr);
		}
	}
}

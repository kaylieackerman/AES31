/*
	-------------------------------------------------------------------------------
	BaseAutomationPoint.java
	AES31-3

	Created by David Ackerman on 12/14/06.

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

public abstract class BaseAutomationPoint implements Cloneable, Comparable<BaseAutomationPoint>
{
	protected int _dest_track;
	protected TcfToken _dest_in;
	protected AutomationList _parent;
	
	public BaseAutomationPoint ()
	{
		_init();
	}
	
	public BaseAutomationPoint (int dt, TcfToken di, AutomationList p) throws InvalidDataException
	{
		this ();
		setDestTrack(dt);
		setDestIn(di);
		setParent(p);
	}
	
	public BaseAutomationPoint (String dt, String di, AutomationList p) throws InvalidDataException
	{
		this ();
		setDestTrack(dt);
		setDestIn(di);
		setParent(p);
	}
	
	protected void _init ()
	{
		_dest_track = -1;
		_dest_in = null;
		_parent = null;
	}
	
	public void setParent (AutomationList p)
	{
		_parent = p;
	}
	
	public AutomationList getParent ()
	{
		return _parent;
	}
	
	public void setDestTrack (int dt) throws InvalidDataException
	{
		if (dt > 0)
		{
			_dest_track = dt;
		}
		else
		{
			throw new InvalidDataException ("Dest track must be > 0.");
		}
	}
	
	public void setDestTrack (String dt) throws InvalidDataException
	{		
		try
		{
			setDestTrack(Integer.parseInt(dt));
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Unable to convert Dest Track String.", e);
		}
	}
	
	public int getDestTrack ()
	{
		return _dest_track;
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
	
	public int compareTo (BaseAutomationPoint o)
	{
		return 0;
	}
	
	public Element toXmlElement ()
	{
		return null;
	}
	
	public void resample (double sr)
	{
		_dest_in.resample(sr);
	}
	
	
}

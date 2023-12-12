/*
	-------------------------------------------------------------------------------
	MuteAutomationPoint.java
	AES31-3

	Created on 3/17/07.

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

import java.text.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class MuteAutomationPoint extends BaseAutomationPoint implements Cloneable, Comparable<BaseAutomationPoint>
{
	
	public enum MuteType {
		M, U
	}
	
	protected MuteAutomationPoint.MuteType _muteValue;
	
	public MuteAutomationPoint ()
	{
		super();
	}
	
	public MuteAutomationPoint (int dt, TcfToken di, MuteListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public MuteAutomationPoint (String dt, String di, MuteListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public MuteAutomationPoint (int dt, TcfToken di, MuteAutomationPoint.MuteType mv, MuteListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setMuteValue(mv);
	}
	
	public MuteAutomationPoint (String dt, String di, String mv, MuteListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setMuteValue(mv);
	}
	
	public void setMuteValue (MuteAutomationPoint.MuteType mv)
	{
		_muteValue = mv;
	}
	
	public void setMuteValue (String mv) throws InvalidDataException
	{
		_muteValue = MuteAutomationPoint.MuteType.valueOf(mv);
	}
	
	public MuteAutomationPoint.MuteType getMuteValue ()
	{
		return _muteValue;
	}
	
	public String toString ()
	{
		String rval = new String ("\n\t(MP) " + _dest_track + " " + _dest_in + " " + _muteValue);
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("mutePointEntry");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("destinationTrack");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_dest_track));
		vs.addContent(e);
		
		e = new Element("destIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_in.toString());
		vs.addContent(e);
		
		e = new Element("muteValue");
		e.setNamespace(ADLSection.aes);
		e.setText(_muteValue.toString());
		vs.addContent(e);
		
		return vs;
	}
	
	
}

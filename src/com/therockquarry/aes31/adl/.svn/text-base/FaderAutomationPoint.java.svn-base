/*
	-------------------------------------------------------------------------------
	FaderAutomationPoint.java
	AES31-3

	Created by David Ackerman on 12/20/06.

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

public class FaderAutomationPoint extends BaseAutomationPoint implements Cloneable, Comparable<BaseAutomationPoint>
{
	
	protected double _faderValue;
	
	public FaderAutomationPoint ()
	{
		super();
	}
	
	public FaderAutomationPoint (int dt, TcfToken di, FaderListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public FaderAutomationPoint (String dt, String di, FaderListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public FaderAutomationPoint (int dt, TcfToken di, double fv, FaderListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setFaderValue(fv);
	}
	
	public FaderAutomationPoint (String dt, String di, String fv, FaderListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setFaderValue(fv);
	}
	
	public void setFaderValue (double fv)
	{
		_faderValue = fv;
	}
	
	public void setFaderValue (String fv) throws InvalidDataException
	{
		double tmp = 0;
		try
		{
			tmp = Double.parseDouble(fv);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Fader point value must be a double.", e);
		}
		
		_faderValue = tmp;
	}
	
	public double getFaderValue ()
	{
		return _faderValue;
	}
	
	public String toString ()
	{
		String rval = new String ("\n\t(FP) " + _dest_track + " " + _dest_in + " " + _faderValue);
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("faderPointEntry");
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
		
		e = new Element("faderValue");
		e.setNamespace(ADLSection.aes);
		e.setText(Double.toString(_faderValue));
		vs.addContent(e);
		
		return vs;
	}
	
	
	
}

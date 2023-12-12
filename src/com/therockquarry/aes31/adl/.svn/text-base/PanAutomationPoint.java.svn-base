/*
	-------------------------------------------------------------------------------
	PanAutomationPoint.java
	AES31-3

	Created on 1/11/07.

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

public class PanAutomationPoint extends BaseAutomationPoint implements Cloneable, Comparable<BaseAutomationPoint>
{
	protected double _leftRightPosition;
	protected double _frontRearPosition;
	
	public PanAutomationPoint ()
	{
		super ();
	}
	
	public PanAutomationPoint (int dt, TcfToken di, PanListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public PanAutomationPoint (String dt, String di, PanListSection p) throws InvalidDataException
	{
		super(dt, di, p);
	}
	
	public PanAutomationPoint (int dt, TcfToken di, double lrp, PanListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setLeftRightPosition(lrp);
	}
	
	public PanAutomationPoint (String dt, String di, String lrp, PanListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setLeftRightPosition(lrp);
	}
	
	public PanAutomationPoint (int dt, TcfToken di, double lrp, double frp, PanListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setLeftRightPosition(lrp);
		setFrontRearPosition(frp);
	}
	
	public PanAutomationPoint (String dt, String di, String lrp, String frp, PanListSection p) throws InvalidDataException
	{
		this(dt, di, p);
		setLeftRightPosition(lrp);
		setFrontRearPosition(frp);
	}
	
	protected void _init ()
	{
		super._init();
		_leftRightPosition = 0.0;
		_frontRearPosition = 0.0;
	}
	
	public void setLeftRightPosition (double lrp) throws InvalidDataException
	{
		if (lrp >= -100.0 && lrp <= 100)
		{
			_leftRightPosition = lrp;
		}
		else
		{
			throw new InvalidDataException("Left-Right Pan Point value must be between -100.0 and 100.0. Found: " + lrp);
		}
	}
	
	public void setLeftRightPosition (String lrp) throws InvalidDataException
	{
		double tmp = 0;
		try
		{
			tmp = Double.parseDouble(lrp);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Pan point value must be a double.", e);
		}
		
		setLeftRightPosition(tmp);
		
	}
	
	public double getLeftRightPosition ()
	{
		return _leftRightPosition;
	}
	
	public void setFrontRearPosition (double frp) throws InvalidDataException
	{
		if (frp >= 0.0 && frp <= 200)
		{
			_frontRearPosition = frp;
		}
		else
		{
			throw new InvalidDataException("Front-Rear Pan Point value must be between 0.0 and 200.0. Found: " + frp);
		}
	}
	
	public void setFrontRearPosition (String lrp) throws InvalidDataException
	{
		double tmp = 0;
		try
		{
			tmp = Double.parseDouble(lrp);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Pan point value must be a double.", e);
		}
		
		setFrontRearPosition(tmp);
	}
	
	public double getFrontRearPosition ()
	{
		return _frontRearPosition;
	}
	
	public String toString ()
	{
		String rval = new String ("\n\t(PP) " + _dest_track + " " + _dest_in + " " + _leftRightPosition + " " + _frontRearPosition);
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("panPointEntry");
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
		
		e = new Element("leftRightPosition");
		e.setNamespace(ADLSection.aes);
		e.setText(Double.toString(_leftRightPosition));
		vs.addContent(e);
		
		e = new Element("frontRearPosition");
		e.setNamespace(ADLSection.aes);
		e.setText(Double.toString(_frontRearPosition));
		vs.addContent(e);
		
		return vs;
	}
	
}

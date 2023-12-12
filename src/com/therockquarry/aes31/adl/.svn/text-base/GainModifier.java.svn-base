/*
	-------------------------------------------------------------------------------
	GainModifier.java
	AES31-3

	Created on 12/13/06

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

import java.math.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class GainModifier extends BaseModifier implements Cloneable
{
	protected Range dest_channel_range;
	protected double _gain;
	
	public GainModifier ()
	{
		super ();
		_init ();
	}
	
	public GainModifier (Range dc, double g)
	{
		this ();
		setDestChannels(dc);
		setGain(g);
	}
	
	protected GainModifier (Vector d, BaseEditEntry p) throws InvalidDataException
	{
		this ();
		setDestChannels((String)d.get(0));
		setGain((String)d.get(1));
		_parent = p;
	}
	
	protected void _init ()
	{
		dest_channel_range = null;
		_gain = 0.0;
	}
	
	public void setDestChannels (Range c)
	{
		dest_channel_range = c;
	}
	
	public void setDestChannels (String c) throws InvalidDataException
	{
		if (c.equals("_"))
		{
			dest_channel_range = null;
			return;
		}
		
		try
		{
			if (c.matches("[0-9]+[-][0-9]+"))
			{

				c = c.replaceAll("-", "~");
			}
			String[] tmp = c.split("[~]");
			if (tmp.length > 2)
			{
				addValidationError("Gain Modifier dest channel range is malformed.");
			} 
			else if (tmp.length == 2)
			{

				dest_channel_range = new Range(c);
			} 
			else 
			{
				dest_channel_range = new Range(Integer.parseInt(c), Integer.parseInt(c));
			
			}
			
			
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Gain Modifier dest channel range is malformed.", e);
		}
	}
	
	public Range getDestChannels ()
	{
		return dest_channel_range;
	}
	
	public void setGain (double g)
	{
		_gain = g;
	}
	
	public void setGain (String g) throws InvalidDataException
	{
		double tmp = 0;
		try
		{
			tmp = Double.parseDouble(g);
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Gain value must be a double.", e);
		}
		
		_gain = tmp;
	}
	
	public double getGain ()
	{
		return _gain;
	}
	
	public String toString ()
	{
		String rval = new String("(Gain) " + (dest_channel_range != null ? dest_channel_range : "_") + " " + _gain);
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("gain");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		if (dest_channel_range != null)
		{
			e = new Element("channel");
			e.setNamespace(ADLSection.aes);
			e.setAttributes(dest_channel_range.getXmlAttributes());
			vs.addContent(e);
		}
		
		e = new Element("gainValue");
		e.setNamespace(ADLSection.aes);
		e.setText(Double.toString(_gain));
		vs.addContent(e);
		
		return vs;
	}
	
}

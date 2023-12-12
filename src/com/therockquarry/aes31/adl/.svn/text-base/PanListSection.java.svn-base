/*
	-------------------------------------------------------------------------------
	PanListSection.java
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

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class PanListSection extends AutomationList
{
	public PanListSection ()
	{
		super("PAN_LIST");
	}
	
	public void addAutomationPoint (PanAutomationPoint pp)
	{
		_points.add(pp);
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("PP"))
			{
				int size = data.size();
				if (size == 4)
				{
					addAutomationPoint (new PanAutomationPoint ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
						(String)data.elementAt(3), this));
				}
				else
				{
					addAutomationPoint (new PanAutomationPoint ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
						 this));
				}
			}

		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	public ArrayList<PanAutomationPoint> getPanPointsForDestChannel(int dc)
	{
		ArrayList<PanAutomationPoint> rval = new ArrayList<PanAutomationPoint>();
		for (BaseAutomationPoint pap : _points)
		{
			if (pap.getDestTrack() == dc)
			{
				rval.add((PanAutomationPoint)pap);
			}
		}
		
		
		return rval;
	}
	
	public String toString ()
	{
		String rval = new String ("\n<PAN_LIST>");
		
		for (BaseAutomationPoint pp: _points)
		{
			rval = rval.concat(pp.toString());
		}
		
		rval = rval.concat("\n</PAN_LIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("panList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		for (BaseAutomationPoint pp: _points)
		{
			vs.addContent(pp.toXmlElement());
		}
		
		return vs;
	}
	
	
	public void resample (double sr)
	{
		for (BaseAutomationPoint pp: _points)
		{
			pp.resample(sr);
		}
	}
}

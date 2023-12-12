/*
	-------------------------------------------------------------------------------
	MuteListSection.java
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

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class MuteListSection extends AutomationList
{
	
	public MuteListSection ()
	{
		super("MUTE_LIST");
	}
	
	public void addAutomationPoint (MuteAutomationPoint mp)
	{
		_points.add(mp);
	}
	
	
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		
		try
		{
			if (keyword.equalsIgnoreCase("MP"))
			{
				addAutomationPoint (new MuteAutomationPoint ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
						this));
			}

		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	public String toString ()
	{
		String rval = new String ("\n<MUTE_LIST>");
		
		for (BaseAutomationPoint mp: _points)
		{
			rval = rval.concat(mp.toString());
		}
		
		rval = rval.concat("\n</MUTE_LIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("muteList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		for (BaseAutomationPoint mp: _points)
		{
			vs.addContent(mp.toXmlElement());
		}
		
		return vs;
	}
	
	
	public void resample (double sr)
	{
		for (BaseAutomationPoint mp: _points)
		{
			mp.resample(sr);
		}
	}
}

/*
	-------------------------------------------------------------------------------
	FaderListSection.java
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

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class FaderListSection extends AutomationList
{
	
	public FaderListSection ()
	{
		super("FADER_LIST");
	}
	
	public void addAutomationPoint (FaderAutomationPoint fp)
	{
		_points.add(fp);
	}
	
	
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		
		try
		{
			if (keyword.equalsIgnoreCase("FP"))
			{
				addAutomationPoint (new FaderAutomationPoint ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
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
		String rval = new String ("\n<FADER_LIST>");
		
		for (BaseAutomationPoint fp: _points)
		{
			rval = rval.concat(fp.toString());
		}
		
		rval = rval.concat("\n</FADER_LIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("faderList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		for (BaseAutomationPoint fp: _points)
		{
			vs.addContent(fp.toXmlElement());
		}
		
		return vs;
	}
	
	public void resample (double sr)
	{
		for (BaseAutomationPoint fp: _points)
		{
			fp.resample(sr);
		}
	}
}

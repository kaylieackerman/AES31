/*
	-------------------------------------------------------------------------------
	SilenceEditEntry.java
	AES31-3

	Created on 12/12/06.

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

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class SilenceEditEntry extends BaseEditEntry implements Cloneable
{
	
	public SilenceEditEntry ()
	{
		super ();
	}
	
	public SilenceEditEntry (long en, Range dcr, TcfToken di, TcfToken d, BaseEditEntry.StatusType s, EventListSection p) throws InvalidDataException
	{
		this();
		setEntryNumber(en);
		setDestChannels(dcr);
		setDestIn(di);
		setDestOut(d);
		setStatus(s);
		setParent(p);
	}
	
	public SilenceEditEntry (String en, String dcr, String di, String d, String s, EventListSection p) throws InvalidDataException
	{
		this();
		setEntryNumber(en);
		setDestChannels(dcr);
		setDestIn(di);
		setDestOut(d);
		setStatusFromCodeStr(s);
		setParent(p);
	}
	
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		String rval = form4.format(_entry_no);
		rval = rval.concat(" (Silence) "  +  _dest_channel_range + " " + 
							_dest_in + " " + _dest_out + " " + ((_status == null) ? "_" : _status.getStatusCode()));
		
		rval = rval.concat(super.toString());
		
		return rval;
	}
	
	public ArrayList<Element> getXmlElements ()
	{
		ArrayList<Element> rval = new ArrayList<Element>();
		
		DecimalFormat form4 = new DecimalFormat("0000");
		
		Element vs;
		Element e;
		vs = new Element("silence");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("destChannel");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_dest_channel_range.getXmlAttributes());
		vs.addContent(e);
				
		e = new Element("destIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_in.toString());
		vs.addContent(e);
		
		e = new Element("destOut");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_out.toString());
		vs.addContent(e);
	
		if (_status != null)
		{
			e = new Element("statusCode");
			e.setNamespace(ADLSection.aes);
			e.setText(_status.getStatusCode());
			vs.addContent(e);
		}
		
		rval.add(vs);
		
		rval.addAll(super.getXmlElements());
		
		return rval;
	}
	
	
}

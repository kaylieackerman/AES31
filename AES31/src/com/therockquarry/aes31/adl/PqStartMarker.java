/*
	-------------------------------------------------------------------------------
	PqStartMarker.java
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

import java.util.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class PqStartMarker extends BaseMarkerPoint implements Cloneable
{
	public PqStartMarker ()
	{
		super();
	}
	
	public PqStartMarker (String d_c_r, String d_i, String m_n, MarkerListSection p) throws InvalidDataException
	{
		_init();
		setDestChannelRange(d_c_r);
		setDestIn(d_i);
		setMarkerName(m_n);
		setParent(p);
	}
	
	public PqStartMarker (NuendoMarkerPoint m, MarkerListSection p) throws InvalidDataException
	{
		_init();
		setDestChannelRange(new Range(0, 0));
		setDestIn(m.getDestIn());
		setDestOut("_");
		setMarkerName(m.getMarkerName());
		setParent(p);
	}
	
	
	public void setDestOut (String d)
	{
		;
	}
	
	public void setDestOut (TcfToken d)
	{
		;
	}
	
	protected void _init ()
	{
		super._init();
		
	}
	
	public String toString ()
	{
		String rval = "\n(MK-PQ-START) " + super.toString();
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("markPointEntry");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("markerKeyword");
		e.setNamespace(ADLSection.aes);
		e.setText("MK-PQ-START");
		vs.addContent(e);
		
		e = new Element("destinationTrack");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_dest_channel_range.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("destIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_in.toString());
		vs.addContent(e);
		
		if (_dest_out != null)
		{
			e = new Element("destOut");
			e.setNamespace(ADLSection.aes);
			e.setText(_dest_out.toString());
			vs.addContent(e);
		}
		
		e = new Element("markerName");
		e.setNamespace(ADLSection.aes);
		e.setText(_marker_name);
		vs.addContent(e);
		
		return vs;
	}
	
}

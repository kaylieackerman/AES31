/*
	-------------------------------------------------------------------------------
	MarkerListSection.java
	AES31-3

	Created on 12/12/06.

	Copyright 2006 Kaylie Ackerman.
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

public class MarkerListSection extends BaseSection implements Cloneable
{
	private ArrayList<BaseMarkerPoint> _markers;
	
	public MarkerListSection ()
	{
		super ("MARKER_LIST");
		_init ();
	}
	
	private void _init ()
	{
		_markers = new ArrayList<BaseMarkerPoint>();
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("MK"))
			{
				_markers.add(new SimpleMarker((String)data.elementAt(0), (String)data.elementAt(1), 
					(String)data.elementAt(2), (String)data.elementAt(3), this));
			}
			else if (keyword.equalsIgnoreCase("MK-PQ-START"))
			{
				_markers.add(new PqStartMarker((String)data.elementAt(0), (String)data.elementAt(1), 
					(String)data.elementAt(3), this));
			}
			else if (keyword.equalsIgnoreCase("MK-PQ-END"))
			{
				_markers.add(new PqEndMarker((String)data.elementAt(0), (String)data.elementAt(1), 
					(String)data.elementAt(3), this));
			}
			else if (keyword.equalsIgnoreCase("MK-PQ-INDEX"))
			{
				_markers.add(new PqIndexMarker((String)data.elementAt(0), (String)data.elementAt(1), 
					(String)data.elementAt(3), this));
			}
			
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
			e.printStackTrace();
		}
	}
	
	public void addMarker (BaseMarkerPoint bmp)
	{
		_markers.add(bmp);
	}
	
	public ArrayList<BaseMarkerPoint> getMarkers ()
	{
		return _markers;
	}
	
	public void addNuendoMarkers (NuendoCuelistSection ncl) throws InvalidDataException
	{
		ArrayList<NuendoMarkerPoint> nMarkers = ncl.getMarkers();
		for (NuendoMarkerPoint nmp: nMarkers)
		{
			String mName = nmp.getMarkerName();
			if (mName != null && mName.contains("[t-start]"))
			{
				nmp.setMarkerName(mName.replace("[t-start]", ""));
				_markers.add(new PqStartMarker(nmp, this));
			}
			else if (mName != null && mName.contains("[t-end]"))
			{
				nmp.setMarkerName(mName.replace("[t-end]", ""));
				_markers.add(new PqEndMarker(nmp, this));
			}
			else if (mName != null && mName.contains("[t-index]"))
			{
				nmp.setMarkerName(mName.replace("[t-index]", ""));
				_markers.add(new PqIndexMarker(nmp, this));
			}
			else
			{
				_markers.add(new SimpleMarker(nmp, this));
			}
		}
	}
	
	
	public void resample (double sr)
	{
		for (BaseMarkerPoint bmp : _markers)
		{
			bmp.resample(sr);
		}
	}
	
	public String toString ()
	{
		String rval = "\n<MARKER_LIST>";
		for (BaseMarkerPoint bmp : _markers)
		{
			rval = rval.concat(bmp.toString());
		}
		rval = rval.concat("\n</MARKER_LIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("markerList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		for (BaseMarkerPoint bmp : _markers)
		{
			vs.addContent(bmp.toXmlElement());
		}
		
		return vs;
	}

}

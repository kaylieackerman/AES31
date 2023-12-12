/*
	-------------------------------------------------------------------------------
	VideoEditEntry.java
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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class VideoEditEntry extends BaseEditEntry implements Cloneable
{
	private BaseEditEntry.SrcType _src_type;		/* key letter */
	private int _src_index;
	private TcfToken _src_in;
	
	public VideoEditEntry ()
	{
		super ();
	}
	
	public VideoEditEntry (long en, BaseEditEntry.SrcType st, int si, TcfToken s,  TcfToken di, TcfToken d,
							BaseEditEntry.StatusType stat, EventListSection p) throws IllegalArgumentException
	{
		this ();
		setEntryNumber(en);
		setSrcType(st);
		setSrcIndex(si);
		setSrcIn(s);
		setDestIn(di);
		setDestOut(d);
		setStatus(stat);
		setParent(p);
	}
	
	public VideoEditEntry (String en, String st, String si, String s,  String di, String d,
							String stat, EventListSection p) throws InvalidDataException
	{
		this ();
		setEntryNumber(en);
		setSrcType(st);
		setSrcIndex(si);
		setSrcIn(s);
		setDestIn(di);
		setDestOut(d);
		setStatusFromCodeStr(stat);
		setParent(p);
	}
	
	protected void _init ()
	{
		super._init();
		setDestChannels(new Range(-100, -100));
		setSrcType("I");
		_src_index = -1;
		_src_in = null;
	}
	
	public void setSrcType (String s) throws IllegalArgumentException
	{
		try
		{
			_src_type = BaseEditEntry.SrcType.valueOf(s);
		}
		catch (IllegalArgumentException e)
		{
			_src_type = BaseEditEntry.SrcType.valueOf("I");
			throw e;
		}
	}
	
	public void setSrcType (BaseEditEntry.SrcType s)
	{
		_src_type = s;
	}
	
	public int getSrcIndex ()
	{
		return _src_index;
	}
	
	public void setSrcIndex (int i)
	{
		_src_index = i;
	}
	
	public void setSrcIndex (String s)
	{
		_src_index = Integer.parseInt(s);
	}
	
	public TcfToken getSrcIn ()
	{
		return _src_in;
	}
	
	public void setSrcIn (TcfToken s)
	{
		_src_in = s;
	}
	
	public void setSrcIn (String s) throws InvalidDataException
	{
		_src_in = new TcfToken(s);
	}
	
	public void resample (double sr)
	{
		super.resample(sr);
		_src_in.resample(sr);
	}
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		String rval = form4.format(_entry_no);
		rval = rval.concat(" (Vid) "  + _src_type + " " + _src_index + " " +  _src_in + " " + 
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
		vs = new Element("vid");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("srcType");
		e.setNamespace(ADLSection.aes);
		e.setText(_src_type.toString());
		vs.addContent(e);
		
		e = new Element("srcIndex");
		e.setNamespace(ADLSection.aes);
		e.setText(form4.format(_src_index));
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

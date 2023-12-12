/*
	-------------------------------------------------------------------------------
	CutEditEntry.java
	AES31-3

	Created by David Ackerman on 7/10/05.

	Copyright 2005 David Ackerman.
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

public class CutEditEntry extends BaseEditEntry implements Cloneable {
	private BaseEditEntry.SrcType _src_type;		/* key letter */
	private int _src_index;
	private Range _src_channel_range;
	//private Range _dest_channel_range;
	private TcfToken _src_in;
	
	
	public CutEditEntry ()
	{
		super ();
		_init ();
	}
	
	public CutEditEntry (String entry, String st, String sindex, String sc, String dc, String si, String di, String dout, String s, 
																									EventListSection p) throws InvalidDataException
	{
		super ();
		_init ();
		
		_parent = p;
		_entry_no = Long.parseLong(entry);
		setSrcType(st);
		_src_index = Integer.parseInt(sindex);
		
		if (sc.matches("[0-9]+[-][0-9]+"))
		{
			_parent.addValidationError("In " + _parent.sectionName + " entry (" + _entry_no + ") Source Channel range uses '-'. Should use '~'");
			//System.out.println("ERROR detected: Source Channel range uses '-'. Should use '~' in entry " + _entry_no);
			sc = sc.replaceAll("-", "~");
		}
		
		if (dc.matches("[0-9]+[-][0-9]+"))
		{
			_parent.addValidationError("In " + _parent.sectionName + " entry (" + _entry_no + ") Destination Channel range uses '-'. Should use '~'");
			//System.out.println("ERROR detected: Destination Channel range uses '-'. Should use '~' in entry " + _entry_no);
			dc = dc.replaceAll("-", "~");
		}
		
		String[] tmp = sc.split("[~]");
		if (tmp.length > 2)
		{
			System.out.println("ERROR encountered in interpreting Source Channel for entry " + _entry_no);
		} 
		else if (tmp.length == 2)
		{
			//_src_channel_low = Integer.parseInt(tmp[0]);
			//_src_channel_high = Integer.parseInt(tmp[1]);
			_src_channel_range = new Range(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
		} 
		else 
		{
			//_src_channel_low = _src_channel_high = Integer.parseInt(sc);
			_src_channel_range = new Range(Integer.parseInt(sc), Integer.parseInt(sc));
			
		}

		tmp = dc.split("[~]");
		if (tmp.length > 2)
		{
			System.out.println("ERROR encountered in interpreting Destination Channel for entry " + _entry_no);
		} 
		else if (tmp.length == 2)
		{
			//_dest_channel_low = Integer.parseInt(tmp[0]);
			//_dest_channel_high = Integer.parseInt(tmp[1]);
			_dest_channel_range = new Range(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
		}
		else 
		{
			//_dest_channel_low = _dest_channel_high = Integer.parseInt(dc);
			_dest_channel_range = new Range(Integer.parseInt(dc), Integer.parseInt(dc));
		}
		
		_src_in = new TcfToken(si);
		_dest_in = new TcfToken(di);
		_dest_out = new TcfToken(dout);
		setStatusFromCodeStr(s);
	}
	
	
	protected void _init ()
	{
		super._init();
		setSrcType("I");
		_src_index = -1;
		_src_channel_range = null;
		_dest_channel_range = null;
		_src_in = null;
		_dest_in = null;
		_dest_out = null;
		_status = null;
	}
	
	
	
	public void setSrcType (String s)
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
	
	public BaseEditEntry.SrcType getSrcType ()
	{
		return _src_type;
	}
	
	public int getSrcIndex ()
	{
		return _src_index;
	}
	
	public void setSrcIndex (int i)
	{
		_src_index = i;
	}
	
	public TcfToken getSrcIn ()
	{
		return _src_in;
	}
	
	public void setSrcIn (TcfToken tcfToken)
	{
		_src_in = tcfToken;
	}
	
	
	
	
	
	/*public int[] getSrcChannels ()
	{
		int[] rval = new int[2];
		if (_src_channel_low > -1)
		{
			rval[0] = _src_channel_low;
		}
		if (_src_channel_high > -1)
		{
			rval[1] = _src_channel_high;
		}
		
		return rval;
	}*/
	
	public Range getSrcChannels ()
	{
		return _src_channel_range;
	}
	
	public int getSrcChannelForDestChannel (int dc) throws IndexOutOfBoundsException
	{
		int offset = 0; //0 based offset
		if (_dest_channel_range.size() != _src_channel_range.size())
		{
			throw new IndexOutOfBoundsException("Dest channel range and Source channel range must be of same size.");
		}
		if (_dest_channel_range.isWithinRange(dc))
		{
			offset = dc - _dest_channel_range.getBegin();
		}
		return _src_channel_range.getBegin() + offset;
	}
	
	public void setSrcChannels(Range r) {
		_src_channel_range = r;
	}
	
	/*public int[] getDestChannels ()
	{
		int[] rval = new int[2];
		if (_dest_channel_low > -1)
		{
			rval[0] = _dest_channel_low;
		}
		if (_dest_channel_high > -1)
		{
			rval[1] = _dest_channel_high;
		}
		
		return rval;
	}*/
	
	/*public Range getDestChannels ()
	{
		return _dest_channel_range;
	}
	
	public void setDestChannels(Range r) {
		_dest_channel_range = r;
	}*/
	
	/**
	*	Returns the <code>BaseIndexEntry</code> object that represents the Audio Source for this CutEditEntry. 
	*
	*	@return The <code>BaseIndexEntry</code> object that represents the Audio Source for this CutEditEntry. 
	*/
	public SourceEntry getSourceIndexEntry ()
	{
		ADLSection p = (ADLSection)_parent.getParent();
		SourceIndexSection sis = p.getSourceIndexSection();
		return sis.getSourceEntryAtIndex(_src_index);
	}
	
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");


		String rval = form4.format(_entry_no);
		rval = rval.concat(" (Cut) " + _src_type + " " + form4.format(_src_index) +  " " + _src_channel_range + " " + _dest_channel_range + " " + _src_in + " " + 
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
		vs = new Element("cut");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
				
		e = new Element("srcType");
		e.setNamespace(ADLSection.aes);
		e.addContent(_src_type.toString());
		vs.addContent(e);
		
		e = new Element("srcIndex");
		e.setNamespace(ADLSection.aes);
		e.addContent(form4.format(_src_index));
		vs.addContent(e);
		
		e = new Element("srcChannel");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_src_channel_range.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("destChannel");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_dest_channel_range.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("srcIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_src_in.toString());
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
	
	
	public void resample (double sr)
	{
		super.resample(sr);
		_src_in.resample(sr);
	}
	
	public FileSourceIndexEntry getFileSourceIndexEntry() throws InvalidDataException, IndexOutOfBoundsException
	{
		FileSourceIndexEntry fsie = null;
		SourceEntry se;
		
		se = getSourceIndexEntry();
		fsie = (FileSourceIndexEntry)se.getIndexEntries(SourceEntry.SourceType.FILE_SRC).get(0);
		
		return fsie;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		CutEditEntry rval = (CutEditEntry)super.clone();
		
		if (_src_channel_range != null)
		{
			rval._src_channel_range = (Range)this._src_channel_range.clone();
		}
		
		if (_dest_channel_range != null)
		{
			rval._dest_channel_range = (Range)this._dest_channel_range.clone();
		}
		
		if (_src_in != null)
		{
			rval._src_in = (TcfToken)this._src_in.clone();
		}
		
		if (_dest_in != null)
		{
			rval._dest_in = (TcfToken)this._dest_in.clone();
		}
		
		if (_dest_out != null)
		{
			rval._dest_out = (TcfToken)this._dest_out.clone();
		}
		
		return rval;
	}
}

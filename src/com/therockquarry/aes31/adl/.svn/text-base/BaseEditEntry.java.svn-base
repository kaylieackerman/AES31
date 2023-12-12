/*
	-------------------------------------------------------------------------------
	BaseEditEntry.java
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

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class BaseEditEntry implements Cloneable, Comparable<BaseEditEntry> 
{
	protected boolean sortByChannel;
	protected long _entry_no;
	protected Vector<BaseModifier> _modifiers;
	protected ArrayList<AlternateSourceModifier> _altSrc;
	protected InfadeModifier _inFade;
	protected OutfadeModifier _outFade;
	protected XfadeModifier _crossFade;
	protected ArrayList<GainModifier> _gain;
	protected ArrayList<RemarkType> _remarks;
	protected EventListSection _parent;
	protected Range _dest_channel_range;
	protected TcfToken _dest_in;
	protected TcfToken _dest_out;
	protected BaseEditEntry.StatusType _status;		/* key letter */
	
	public enum StatusType 
	{
		PERFORMED ("R"), 
		ERROR ("E"), 
		DISABLED ("D"), 
		MARKED ("X"), 
		MUTE ("M");
		
		private final String _code;
		
		StatusType (String code)
		{
			_code = code;
		}
		
		public String getStatusCode ()
		{
			return _code;
		}
		
		public static StatusType getStatusTypeForStatusCode (String code)
		{
			for (StatusType st : EnumSet.range(StatusType.PERFORMED, StatusType.MUTE))
			{
				if (st.getStatusCode().equalsIgnoreCase(code))
				{
					return st;
				}
			}
			
			return null;
		}
	}
	
	public enum SrcType
	{
		I;
	}
	
	public enum EntryType
	{
		CUT, SILENCE, AUX, VID;
	}
		
	public BaseEditEntry ()
	{
		_init ();
	}
	
	protected void _init ()
	{
		_entry_no = -1;
		_modifiers = new Vector<BaseModifier> ();
		_altSrc = new ArrayList<AlternateSourceModifier> ();
		_inFade = null;
		_outFade = null;
		_crossFade = null;
		_gain = new ArrayList<GainModifier> ();
		_remarks = new ArrayList<RemarkType> ();
		setDestChannels(new Range(1, 1));
		_dest_in = null;
		_dest_out = null;
		_status = null;
		_parent = null;
		sortByChannel = true;

	}
	
	public void setEntryNumber(long l) {
		_entry_no = l;
	}
	
	public void setEntryNumber (String s) throws InvalidDataException
	{
		_entry_no = Long.parseLong(s);
	}
	
	public long getEntryNumber() {
		return _entry_no;
	}
	
	public void addAltSrc (AlternateSourceModifier m)
	{
		_altSrc.add(m);
	}
	
	public ArrayList<AlternateSourceModifier> getAltSrc ()
	{
		return _altSrc;
	}
	
	public void setInFade (InfadeModifier m)
	{
		_inFade = m;
	}
	
	public InfadeModifier getInFade ()
	{
		return _inFade;
	}
	
	public void setOutFade (OutfadeModifier m)
	{
		_outFade = m;
	}
	
	public OutfadeModifier getOutFade ()
	{
		return _outFade;
	}
	
	public void setCrossFade (XfadeModifier m)
	{
		_crossFade = m;
	}
	
	public XfadeModifier getCrossFade ()
	{
		return _crossFade;
	}
	
	public void addGain (GainModifier m)
	{
		_gain.add(m);
	}
	
	public ArrayList<GainModifier> getGain ()
	{
		return _gain;
	}
	
	public void addModifier (BaseModifier m)
	{
		_modifiers.add(m);
		
		//debug
		//System.out.println(m);
	}
	
	public Enumeration getModifiers ()
	{	
		Vector<BaseModifier> rval = new Vector<BaseModifier> ();
		for (Enumeration e = _modifiers.elements(); e.hasMoreElements() ;) {
			rval.add((BaseModifier)e.nextElement());
		}
		
		return rval.elements();
	}
	
	public void addRemark (RemarkType r)
	{
		_remarks.add(r);
	}
	
	public ArrayList<RemarkType> getRemarks ()
	{
		return _remarks;
	}
	
	public ArrayList<RemarkType> getRemarks (RemarkType.RemType t)
	{
		ArrayList<RemarkType> rval = new ArrayList<RemarkType>();
		for (RemarkType rem : _remarks)
		{
			if (rem.getRemType() == t)
			{
				rval.add(rem);
			}
		}
		return rval;
	}
	
	public String toString ()
	{
		//print modifiers
		String rval = new String();
		
		for (AlternateSourceModifier m: _altSrc)
		{
			rval = rval.concat("\n\t" + m);
		}
		
		if (_inFade != null)
		{
			rval = rval.concat("\n\t" + _inFade);
		}
		
		if (_outFade != null)
		{
			rval = rval.concat("\n\t" + _outFade);
		}
		
		if (_crossFade != null)
		{
			rval = rval.concat("\n\t" + _crossFade);
		}
		
		for (GainModifier m: _gain)
		{
			rval = rval.concat("\n\t" + m);
		}
		
		
		/*BaseModifier m;
		Enumeration e = _modifiers.elements();
		while (e.hasMoreElements())
		{
			m = (BaseModifier)e.nextElement();
			rval = rval.concat("\n\t" + m);
		}*/
		
		//print remarks
		//RemarkType rt;
		
		for (RemarkType rt : _remarks)
		{
			rval = rval.concat("\n\t" + rt);
		}
		
		/*
		e = _remarks.elements();
		while (e.hasMoreElements())
		{
			rt = (RemarkType)e.nextElement();
			rval = rval.concat("\n\t" + rt);
		}
		*/
		
		return rval;
	}
	
	public ArrayList<Element> getXmlElements ()
	{
		ArrayList<Element> rval = new ArrayList<Element>();
				
		Element vs;
		Element e;
		
		for (AlternateSourceModifier m: _altSrc)
		{
			rval.add(m.toXmlElement());
		}
		
		if (_inFade != null)
		{
			rval.add(_inFade.toXmlElement());
		}
		
		if (_outFade != null)
		{
			rval.add(_outFade.toXmlElement());
		}
		
		if (_crossFade != null)
		{
			rval.add(_crossFade.toXmlElement());
		}
		
		for (GainModifier m: _gain)
		{
			rval.add(m.toXmlElement());
		}
		
		for (RemarkType rt : _remarks)
		{
			rval.add(rt.toXmlElement());
		}
				
		return rval;
	}
	
	
	public void setParent (EventListSection p)
	{
		_parent = p;
	}
	
	public EventListSection getParent ()
	{
		return _parent;
	}
	
	
	public TcfToken getDestIn ()
	{
		return _dest_in;
	}
	
	public void setDestIn (TcfToken di)
	{
		_dest_in = di;
	}
	
	public void setDestIn (String di) throws InvalidDataException
	{
		_dest_in = new TcfToken(di);
	}
	
	public TcfToken getDestOut ()
	{
		return _dest_out;
	}
	
	public void setDestOut (TcfToken d)
	{
		_dest_out = d;
	}
	
	public void setDestOut (String d) throws InvalidDataException
	{
		_dest_out = new TcfToken(d);
	}
	
	public void setStatusFromCodeStr (String s)
	{
		
		if (!s.equals("_"))
		{
			try
			{
				_status = BaseEditEntry.StatusType.getStatusTypeForStatusCode(s);
			}
			catch (IllegalArgumentException e)
			{
				_status = null;
				throw e;
			}
		}
	}
	
	public void setStatus (BaseEditEntry.StatusType s)
	{
		_status = s;
	}
	
	public BaseEditEntry.StatusType getStatus ()
	{
		return _status;
	}
	
	public void setDestChannels(Range r) {
		_dest_channel_range = r;
	}
	
	public void setDestChannels (String dcr) throws InvalidDataException
	{
		try
		{
			if (dcr.matches("[0-9]+[-][0-9]+"))
			{
				_parent.addValidationError("In " + _parent.sectionName + " Silence Edit Entry (" + this.getEntryNumber() + ") Dest Channel range uses '-'. Should use '~'");
				dcr = dcr.replaceAll("-", "~");
			}
			String[] tmp = dcr.split("[~]");
			if (tmp.length > 2)
			{
				_parent.addValidationError("In " + _parent.sectionName + " Silence Edit Entry (" + this.getEntryNumber() + ") Dest Channel range is malformed");
			} 
			else if (tmp.length == 2)
			{

				_dest_channel_range = new Range(dcr);
			} 
			else 
			{
				_dest_channel_range = new Range(Integer.parseInt(dcr), Integer.parseInt(dcr));
			
			}
			
			
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Silence Edit Entry (" + this.getEntryNumber() + ") dest channel attribute malformed.", e);
		}
	}
	
	public Range getDestChannels ()
	{
		return _dest_channel_range;
	}
	
	public void resample (double sr)
	{
		_dest_in.resample(sr);
		_dest_out.resample(sr);
		
		Enumeration e = getModifiers();
		
		while (e.hasMoreElements())
		{
			((BaseModifier)e.nextElement()).resample(sr);
		}
	}
	
	public void setSortByChannel (boolean b)
	{
		sortByChannel = b;
	}
	
	public boolean getSortByChannel ()
	{
		return sortByChannel;
	}
	
	public int compareTo (BaseEditEntry o)
	{
		int rval = 0;
		
		if (this.equals(o))
		{
			return rval;
		}
		else if (sortByChannel && (rval = _dest_channel_range.compareTo(o._dest_channel_range)) != 0)
		{
			return rval;
		}
		else if ((rval = _dest_in.compareTo(o._dest_in)) != 0)
		{
			return rval;
		}
		else if (_entry_no < o._entry_no)
		{
			rval = -1;
		}
		else  if (_entry_no > o._entry_no)
		{
			rval = 1;
		}
		return rval;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		BaseEditEntry rval = (BaseEditEntry)super.clone();
		
		if (_modifiers != null)
		{
			rval._modifiers = (Vector)this._modifiers.clone();
		}
		
		if (_remarks != null)
		{
			rval._remarks = (ArrayList)this._remarks.clone();
		}
		
		return rval;
	}
	
	
	
}

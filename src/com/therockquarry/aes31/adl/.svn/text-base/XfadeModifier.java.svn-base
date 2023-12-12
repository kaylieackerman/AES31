/*
	-------------------------------------------------------------------------------
	XfadeModifier.java
	AES31-3

	Created on 7/10/05.

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
import java.text.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class XfadeModifier extends BaseModifier implements Cloneable {

	private int _previous_clip;
	private TcfToken _dest_in;
	private TcfToken _dest_out;
	private BaseEditEntry.SrcType _src_type;
	private int	_src_index;
	private TcfToken _src_in;

	public XfadeModifier ()
	{
		super ();
		_init ();
	}
	
	public XfadeModifier (Vector m, BaseEditEntry p) throws InvalidDataException
	{
		super ();
		_init ();
		
		_previous_clip = Integer.parseInt((String)m.get(0));
		_dest_in = new TcfToken((String)m.get(1));
		_dest_out = new TcfToken((String)m.get(2));
		setSrcType((String)m.get(3));
		_src_index = Integer.parseInt((String)m.get(4));
		_src_in = new TcfToken((String)m.get(5));
		_parent = p;
	}
	
	private void _init ()
	{
		_previous_clip = -1;
		_dest_in = null;
		_dest_out = null;
		_src_type = null;
		_src_index = -1;
		_src_in = null;
	}
	
	public void setSrcType (String s)
	{
		try
		{
			_src_type = BaseEditEntry.SrcType.valueOf(s);
		}
		catch (IllegalArgumentException e)
		{
			_src_type = null;
			throw e;
		}
	}
	
	public void setSrcType (BaseEditEntry.SrcType s)
	{
		_src_type = s;
	}
	
	public int getPreviousClip ()
	{
		return _previous_clip;
	}
	
	public void setPreviousClip (int clipNum) throws InvalidDataException
	{
		if (clipNum > 0)
		{
			_previous_clip = clipNum;
		}
		else
		{
			throw new InvalidDataException("\"" + _previous_clip + "\" is not valid. Clip number must be > 0");
		}
		
	}
	
	public int getSrcIndex ()
	{
		return _src_index;
	}
	
	public void setSrcIndex (int srcIndex) throws InvalidDataException
	{
		if (srcIndex > 0)
		{
			_src_index = srcIndex;
		}
		else
		{
			throw new InvalidDataException("\"" + _src_index + "\" is not valid. Src Index must be > 0");
		}
	}
	
	public TcfToken getSrcIn ()
	{
		return _src_in;
	}
	
	public TcfToken getDestIn ()
	{
		return _dest_in;
	}
	
	public TcfToken getDestOut ()
	{
		return _dest_out;
	}
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		
		String rval = new String("(Xfade) " + _previous_clip + " " + _dest_in + " "  + _dest_out + " " + (_src_type != null ? _src_type : "_") + " " + form4.format(_src_index) + 
			" " + _src_in);
		
		
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		
		Element vs;
		Element e;
		vs = new Element("crossfade");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("previousClip");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_previous_clip));
		vs.addContent(e);
		
		e = new Element("destIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_in.toString());
		vs.addContent(e);
		
		e = new Element("destOut");
		e.setNamespace(ADLSection.aes);
		e.setText(_dest_out.toString());
		vs.addContent(e);		

		e = new Element("srcType");
		e.setNamespace(ADLSection.aes);
		e.setText(_src_type.toString());
		vs.addContent(e);
	
		e = new Element("srcIndex");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_src_index));
		vs.addContent(e);
	
		e = new Element("srcIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_src_in.toString());
		vs.addContent(e);
	
		
		return vs;
	}
	
	
	public void resample (double sr)
	{
		if (_src_in != null)
		{
			_src_in.resample(sr);
		}
		if (_dest_in != null)
		{
			_dest_in.resample(sr);
		}
		if (_dest_out != null)
		{
			_dest_out.resample(sr);
		}
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		XfadeModifier rval = (XfadeModifier)super.clone();
		
		if (_dest_in != null)
		{
			rval._dest_in = (TcfToken)this._dest_in.clone();
		}
		
		if (_dest_out != null)
		{
			rval._dest_out = (TcfToken)this._dest_out.clone();
		}
		
		if (_src_in != null)
		{
			rval._src_in = (TcfToken)this._src_in.clone();
		}
		
		return rval;
	}
	
	
	public SourceEntry getSourceIndexEntry ()
	{
		ADLSection p = (ADLSection)_parent.getParent().getParent();
		SourceIndexSection sis = p.getSourceIndexSection();
		return sis.getSourceEntryAtIndex(_src_index);
	}

}

/*
	-------------------------------------------------------------------------------
	OutfadeModifier.java
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

public class OutfadeModifier extends BaseFadeModifier implements Cloneable {

	public OutfadeModifier ()
	{
		super ();
	}
	
	public OutfadeModifier (Vector v, BaseEditEntry p) throws InvalidDataException
	{
		super ();
		_init ();
		
		_duration = new TcfToken((String)v.get(0));
		setShape((String)v.get(1));
		setCurveA((String)v.get(2));
		setCurveB((String)v.get(3));
		setCurveC((String)v.get(4));
		if (v.size() == 8)
		{
			if (!( ((String)v.get(5)).equals("_") && ((String)v.get(6)).equals("_") && ((String)v.get(7)).equals("_") ) )
			{
				setSrcType((String)v.get(5));
				_src_index = Integer.parseInt((String)v.get(6));
				_src_in = new TcfToken((String)v.get(7));
			}
		}
		_parent = p;
	}
	
	public OutfadeModifier (String d, String s, String ca, String cb, String cc, String st, String si, String sin) throws InvalidDataException
	{
		super ();
		_init();
		_duration = new TcfToken(d);
		setShape(s);
		setCurveA(ca);
		setCurveB(cb);
		setCurveC(cc);
		setSrcType(st);
		_src_index = Integer.parseInt(si);
		_src_in = new TcfToken(sin);
	}
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		
		String rval = new String("(Outfade) " + _duration + " " + _shape + " "  + (_curve_a != null ? _curve_a : "_") + " " 
						+ (_curve_b != null ? _curve_b : "_") + " " + (_curve_c != null ? _curve_c : "_"));
		
		if (_src_type != null && _src_index != -1 && _src_in != null)
		{
			rval = rval.concat(" " + (_src_type != null ? _src_type : "_") + " " + form4.format(_src_index) + " " + _src_in);
		}
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		
		Element vs;
		Element e;
		vs = new Element("fadeOut");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("duration");
		e.setNamespace(ADLSection.aes);
		e.setText(_duration.toString());
		vs.addContent(e);
		
		e = new Element("shape");
		e.setNamespace(ADLSection.aes);
		e.setText(_shape.toString());
		vs.addContent(e);
		
		if (_curve_a != null)
		{
			e = new Element("curveA");
			e.setNamespace(ADLSection.aes);
			e.setText(Double.toString(_curve_a));
			vs.addContent(e);
		}
		
		if (_curve_b != null)
		{
			e = new Element("curveB");
			e.setNamespace(ADLSection.aes);
			e.setText(Double.toString(_curve_b));
			vs.addContent(e);
		}
		
		if (_curve_c != null)
		{
			e = new Element("curveC");
			e.setNamespace(ADLSection.aes);
			e.setText(Double.toString(_curve_c));
			vs.addContent(e);
		}
		
		if (_src_type != null)
		{
			e = new Element("srcType");
			e.setNamespace(ADLSection.aes);
			e.setText(_src_type.toString());
			vs.addContent(e);
		}
		
		if (_src_index != -1)
		{
			e = new Element("srcIndex");
			e.setNamespace(ADLSection.aes);
			e.setText(Integer.toString(_src_index));
			vs.addContent(e);
		}
		
		if (_src_in != null)
		{
			e = new Element("srcIn");
			e.setNamespace(ADLSection.aes);
			e.setText(_src_in.toString());
			vs.addContent(e);
		}
		
		return vs;
	}
	
	
	
	public Object clone () throws CloneNotSupportedException
	{
		OutfadeModifier rval = (OutfadeModifier)super.clone();
		
		if (_duration != null)
		{
			rval._duration = (TcfToken)this._duration.clone();
		}
		
		if (_src_in != null)
		{
			rval._src_in = (TcfToken)this._src_in.clone();
		}
		
		return rval;
	}
	
}

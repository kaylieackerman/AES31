/*
	-------------------------------------------------------------------------------
	RemarkModifier.java
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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class RemarkType implements Cloneable {

	private RemarkType.RemType _rem_type;
	private String _remark;
	
	public enum RemType
	{
		NAME, SOURCE, DESC, USER;
	}
	
	public RemarkType ()
	{
		_init ();
	}
	
	public RemarkType (String rt, String r)
	{
		_init ();
		
		setRemType(rt);
		setRemarkText(r);
	}
	
	public RemarkType (RemarkType.RemType rt, String r)
	{
		_init ();
		setRemType(rt);
		setRemarkText(r);
	}
	
	private void _init ()
	{
		_rem_type = null;
		_remark = null;
	}
	
	public RemarkType.RemType getRemType ()
	{
		return _rem_type;
	}
	
	public void setRemType (RemarkType.RemType rt)
	{
		_rem_type = rt;
	}
	
	public void setRemType (String rt)
	{
		_rem_type = RemarkType.RemType.valueOf(rt);
	}
	
	public String getRemarkText ()
	{
		return _remark;
	}
	
	public void setRemarkText (String txt)
	{
		if (EDMLParser.validateADLString(txt))
		{
			_remark = txt;
		}
	}
	
	public String toString ()
	{
		String rval = new String("(Rem) " + _rem_type + " \"" +  EDMLParser.sanitizeEDMLQuotes(_remark) + "\"");
		
		 return rval; 
	}
	
	public Element toXmlElement ()
	{		
		Element vs;
		Element e;
		vs = new Element("remark");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		e = new Element("remType");
		e.setNamespace(ADLSection.aes);
		e.setText(_rem_type.toString());
		vs.addContent(e);
		
		e = new Element("remValue");
		e.setNamespace(ADLSection.aes);
		e.setText(_remark);
		vs.addContent(e);
		
		return vs;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		RemarkType rval = (RemarkType)super.clone();
		
		return rval;
	}

}

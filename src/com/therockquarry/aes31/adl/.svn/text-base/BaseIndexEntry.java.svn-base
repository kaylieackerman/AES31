/*
	-------------------------------------------------------------------------------
	BaseIndexEntry.java
	AES31-3

	Created by David Ackerman on 7/8/05.

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

public class BaseIndexEntry implements Cloneable, AES31Validation {
	
	private SourceEntry _parent;
	private Vector<String> _validationErrors;

	public BaseIndexEntry ()
	{
		_init ();
	}
	
	private void _init ()
	{
		_validationErrors =  new Vector<String>();
	}
	
	public void setParent(SourceEntry parent)
	{
		_parent = parent;
	}
	
	public SourceEntry getParent()
	{
		return _parent;
	}
	
	public void addValidationError (String s)
	{
		_validationErrors.addElement(s);
	}
	
	public StringBuffer getErrors ()
	{
		StringBuffer rval = new StringBuffer();
		Enumeration e = _validationErrors.elements();
		while (e.hasMoreElements()) {
			String es = (String)e.nextElement();
			rval.append(es + "\n");
		}
		
		return rval;
	}
	
	public void resample (double sr)
	{
	
	}
	
	public Element toXmlElement ()
	{
		return null;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		BaseIndexEntry rval = (BaseIndexEntry)super.clone();
		
		return rval;
	}
	
	public String getFilePath ()
	{
		return null;
	}
}

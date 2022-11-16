/*
	-------------------------------------------------------------------------------
	BaseModifier.java
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

public abstract class BaseModifier implements Cloneable
{
	protected BaseEditEntry _parent;
	protected ArrayList<String> _validationErrors;

	public BaseModifier ()
	{
	
	}
	
	public void setParent (BaseEditEntry p)
	{
		_parent = p;
	}
	
	public BaseEditEntry getParent ()
	{
		return _parent;
	}
	
	public void resample (double sr)
	{
		;
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		BaseModifier rval = (BaseModifier)super.clone();
		
		return rval;
	}
	
	public void addValidationError (String s)
	{
		_validationErrors.add(s);
	}
	
	

}

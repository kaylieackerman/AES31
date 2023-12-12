/*
	-------------------------------------------------------------------------------
	Range.java
	AES31-3

	Created on 4/3/06.

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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>Range</code> class provides easy access to an d manipulation of the integer range
*	types that occur in an AES31-3 ADL document. A <code>Range</code> consists of an int begin point
*	and an int end point. It is understood that all ints that fall between the begin point and the
*	end point are implicitly included within the range.
*/
public class Range implements Cloneable, Comparable<Range>
{
	private int _begin;
	private int _end;
	
	/**
	*	Create and return a new <code>Range</code> object without initializing the begin and end points.
	*/
	public Range ()
	{
		_init();
	}
	
	/**
	*	Create and return a new <code>Range</code> object, initializing it to the value given in the <code>range</code>
	*	parameter.
	*
	*	@param range A string in the form of #~#, 2 integers seperated by a '~' character, where the first integer 
	*	is the begin point of the range and the second integer is the end point of the range, or alternatiely a single
	*	integer which is understood to be both the begin and end point of the range.
	*	@throws InvalidDataException If the <code>range</code> parameter is misformed.
	*/
	public Range (String range) throws InvalidDataException
	{
		if (!validateRangeString(range))
		{
			throw new InvalidDataException("\"" + range + "\" is not a valid Range String. Mut be in the form of \"1~2\"");
		}
		
		String[] tmp = range.split("[~]");
		
		if (tmp.length > 2)
		{
			throw new InvalidDataException("Malformed range String passed, unable to create new Range(). Range String must be in the form of \"1~2\"");
		}
		else if (tmp.length == 2)
		{
			this.setBegin(new Integer(tmp[0]));
			this.setEnd(new Integer(tmp[1]));
		} 
		else 
		{
			this.setBegin(new Integer(tmp[0]));
			this.setEnd(new Integer(tmp[0]));
		}
		
	}
	
	/**
	*	Create and return a new <code>Range</code> object, initializing the begin and end points to the parameters passed in.
	*
	*	@param begin The begin point for this <code>Range</code>.
	*	@param end The end point for this <code>Range</code>/
	*/
	public Range (int begin, int end)
	{
		_begin = begin;
		_end = end;
	}
	
	private void _init()
	{
		_begin = -1;
		_end = -1;
	}
	
	/**
	*	Sets the begin point or this <code>Range</code>.
	*
	*	@param begin An int to which to set the begin point of this <code>Range</code>.
	*/
	public void setBegin (int begin)
	{
		_begin = begin;
	}
	
	/**
	*	Sets the end point or this <code>Range</code>.
	*
	*	@param end An int to which to set the end point of this <code>Range</code>.
	*/
	public void setEnd (int end)
	{
		_end = end;
	}
	
	/**
	*	Returns the begin point of this <code>Range</code>.
	*
	*	@return The begin point of this <code>Range</code>.
	*/
	public int getBegin ()
	{
		return _begin;
	}

	/**
	*	Returns the end point of this <code>Range</code>.
	*
	*	@return The end point of this <code>Range</code>.
	*/
	public int getEnd ()
	{
		return _end;
	}
	
	/**
	*	Tests <code>i</code> to determine if it falls within this <code>Range</code>.
	*
	*	@param i An int to test to determine if it falls within this <code>Range</code>.
	*	@return A boolean value, True if <code>i</code> is within this <code>Range</code>,
	*	False otherwise.
	*/
	public boolean isWithinRange (int i)
	{
		boolean rval = false;
		
		if (i >= _begin && i <= _end)
		{
			rval = true;
		}
		
		return rval;
	}
	
	public int size ()
	{
		return (_end - _begin);
	}
	
	private boolean validateRangeString (String str)
	{
		boolean rval = false;
		
		if (str.matches("[0-9]+([~][0-9]+)?"))
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Returns a String representation of this <code>Range</code>.
	*
	*	@return A String representation of this <code>Range</code>.
	*/
	public String toString ()
	{
		String rval;
		
		if (_begin == _end)
		{
			rval =  Integer.toString(_begin);
		}
		else
		{
			rval = new String(Integer.toString(_begin) + "~" + Integer.toString(_end));
		}
		
		return rval;
	}
	
	public ArrayList<org.jdom.Attribute> getXmlAttributes ()
	{
		ArrayList<org.jdom.Attribute> rval = new ArrayList<org.jdom.Attribute>();
		Attribute a;
		
		a = new Attribute("start", Integer.toString(_begin));
		rval.add(a);
		
		if (_begin != _end)
		{
			a = new Attribute("end", Integer.toString(_end));
			rval.add(a);
		}
		
		return rval;
	}
	
	public int compareTo (Range o)
	{
		if (this.equals(o))
		{
			return 0;
		}
		if (_begin < o._begin)
		{
			return -1;
		}
		else if (_begin > o._begin)
		{
			return 1;
		}
		else if (_end < o._end)
		{
			return -1;
		}
		else if (_end > o._end)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	*	Creates and returns a deep clone of this <code>Range</code> object.
	*
	*	@return A deep clone of this <code>Range</code> object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		Range rval = (Range)super.clone();
		
		return rval;
	}

}

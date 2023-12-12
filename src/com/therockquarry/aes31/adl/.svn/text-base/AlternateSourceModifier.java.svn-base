/*
	-------------------------------------------------------------------------------
	AlternateSourceModifier.java
	AES31-3

	Created by David Ackerman on 12/13/06.

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
import java.text.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class AlternateSourceModifier extends BaseModifier implements Cloneable
{
	private BaseEditEntry.SrcType _src_type;
	private Range	_src_channel_range;
	private int	_src_index;
	private TcfToken _src_in;
	
	public AlternateSourceModifier ()
	{
		super();
		_init();
	}
	
	public AlternateSourceModifier (Vector d, BaseEditEntry p) throws InvalidDataException
	{
		this ();
		setSrcType ((String)d.get(0));
		setSrcIndex ((String)d.get(1));
		setSrcChannels ((String)d.get(2));
		setSrcIn ((String)d.get(3));
		_parent = p;
	}
	
	protected void _init ()
	{
		_src_type = null;
		setSrcChannels(new Range(1, 1));
		_src_index = -1;
		_src_in = null;
	}
	
	public void setSrcType (BaseEditEntry.SrcType s)
	{
		_src_type = s;
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
	
	public BaseEditEntry.SrcType getSrcType ()
	{
		return _src_type;
	}
	
	public void setSrcChannels (Range c)
	{
		_src_channel_range = c;
	}
	
	public void setSrcChannels (String c) throws InvalidDataException
	{
		try
		{
			if (c.matches("[0-9]+[-][0-9]+"))
			{

				c = c.replaceAll("-", "~");
			}
			String[] tmp = c.split("[~]");
			if (tmp.length > 2)
			{
				addValidationError("In Alternate Source Modifier SRC channels range is malformed.");
			} 
			else if (tmp.length == 2)
			{

				_src_channel_range = new Range(c);
			} 
			else 
			{
				_src_channel_range = new Range(Integer.parseInt(c), Integer.parseInt(c));
			
			}
			
			
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Alternate Source Modifier attribute malformed.", e);
		}
	}
	
	public Range getSrcChannels ()
	{
		return _src_channel_range;
	}
	
	public void setSrcIndex (int i) throws InvalidDataException
	{
		if (i > 0)
		{
			_src_index = i;
		}
		else
		{
			throw new InvalidDataException ("Source index must be > 0.");
		}
	}
	
	public void setSrcIndex (String i) throws InvalidDataException
	{
		try
		{
			int tmp = Integer.parseInt(i);
			if (tmp > 0)
			{
				_src_index = tmp;
			}
			else
			{
				throw new InvalidDataException ("Source index must be > 0.");
			}
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Source index is malformed.", e);
		}
	}
	
	public int getSrcIndex ()
	{
		return _src_index;
	}
	
	public void setSrcIn (TcfToken si)
	{
		_src_in = si;
	}
	
	public void setSrcIn (String si) throws InvalidDataException
	{
		_src_in = new TcfToken(si);
	}
	
	public TcfToken getSrcIn ()
	{
		return _src_in;
	}
	
	public int getSrcChannelForDestChannel (int dc, Range dest_channel_range) throws IndexOutOfBoundsException
	{
		int offset = 0; //0 based offset
		if (dest_channel_range.size() != _src_channel_range.size())
		{
			throw new IndexOutOfBoundsException("Dest channel range and Source channel range must be of same size.");
		}
		if (dest_channel_range.isWithinRange(dc))
		{
			offset = dc - dest_channel_range.getBegin();
		}
		return _src_channel_range.getBegin() + offset;
	}
	
	public SourceEntry getSourceIndexEntry ()
	{
		ADLSection p = (ADLSection)_parent.getParent().getParent();
		SourceIndexSection sis = p.getSourceIndexSection();
		return sis.getSourceEntryAtIndex(_src_index);
	}
	
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");

		String rval = new String("(Alt) " + _src_type + " " + form4.format(_src_index) + " " +  _src_channel_range + " " + _src_in);
		return rval;
	}
	
	public Element toXmlElement ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		
		Element vs;
		Element e;
		vs = new Element("altSource");
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
		
		e = new Element("srcChannel");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_src_channel_range.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("srcIn");
		e.setNamespace(ADLSection.aes);
		e.setText(_src_in.toString());
		vs.addContent(e);
	
		
		return vs;
	}
	
}

/*
	-------------------------------------------------------------------------------
	BaseFadeModifier.java
	AES31-3

	Created by Kaylie Ackerman on 12/11/06.

	Copyright 2006 Kaylie Ackerman.
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

public class BaseFadeModifier extends BaseModifier implements Cloneable
{
	protected BaseFadeModifier.FadeShape _shape;
	protected Double _curve_a;
	protected Double _curve_b;
	protected Double _curve_c;
	protected TcfToken _duration;
	protected BaseEditEntry.SrcType _src_type;
	protected int	 _src_index;
	protected TcfToken _src_in;
	
	public BaseFadeModifier()
	{
		super();
		_init ();
	}
	
	public enum FadeShape
	{
		LIN, CURVE;
	}
	
	protected void _init ()
	{
		_duration = null;
		_shape = null;
		_curve_a = null;
		_curve_b = null;
		_curve_c = null;
		_src_type = null;
		_src_index = -1;
		_src_in = null;
	}
	
	public void setShape (String s)
	{
		try
		{
			_shape = BaseFadeModifier.FadeShape.valueOf(s);
		}
		catch (IllegalArgumentException e)
		{
			_shape = null;
			throw e;
		}
	}
	
	public void setShape (BaseFadeModifier.FadeShape s)
	{
		_shape = s;
	}
	
	public void setCurveA (Double a)
	{
		_curve_a = a;
	}
	
	public void setCurveA (String a) throws InvalidDataException
	{
		Double tmp = null;
		if (!a.equals("_"))
		{
			try
			{
				tmp = new Double(a);
			}
			catch (NumberFormatException e)
			{
				throw new InvalidDataException("Curve A must be a double.", e);
			}
		}
		_curve_a = tmp;
	}
	
	public void setCurveB (Double b)
	{
		_curve_b = b;
	}
	
	public void setCurveB (String b) throws InvalidDataException
	{
		Double tmp = null;
		if (!b.equals("_"))
		{
			try
			{
				tmp = new Double(b);
			}
			catch (NumberFormatException e)
			{
				throw new InvalidDataException("Curve B must be a double.", e);
			}
		}
		_curve_b = tmp;
	}
	
	public void setCurveC (Double c)
	{
		_curve_c = c;
	}
	
	public void setCurveC (String c) throws InvalidDataException
	{
		Double tmp = null;
		if (!c.equals("_"))
		{
			try
			{
				tmp = new Double(c);
			}
			catch (NumberFormatException e)
			{
				throw new InvalidDataException("Curve C must be a double.", e);
			}
		}
		_curve_c = tmp;
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
	
	public TcfToken getDuration ()
	{
		return _duration;
	}
	
	public int getSrcIndex ()
	{
		return _src_index;
	}
	
	public TcfToken getSrcIn ()
	{
		return _src_in;
	}
	
	public SourceEntry getSourceIndexEntry ()
	{
		ADLSection p = (ADLSection)_parent.getParent().getParent();
		SourceIndexSection sis = p.getSourceIndexSection();
		return sis.getSourceEntryAtIndex(_src_index);
	}
	
	public void resample (double sr)
	{
		if (_src_in != null)
		{
			_src_in.resample(sr);
		}
		
		if (_duration != null)
		{
			_duration.resample(sr);
		}
	}
}

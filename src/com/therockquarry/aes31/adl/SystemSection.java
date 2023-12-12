/*
	-------------------------------------------------------------------------------
	SystemSection.java
	AES31-3

	Created on 7/8/05.

	Copyright 2005 Kaylie Ackerman.
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

import java.math.BigDecimal;
import java.util.regex.*;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
*	The <code>SystemSection</code> object contains optional information about system setup parameters of the originating system. The ADL specification is available from the Audio Engineering Society, 
*	<a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author Kaylie Ackerman
*/
public class SystemSection extends BaseSection implements Cloneable {

	private TcfToken	_sys_src_offset;				/* offset all sources, default 0 */
	private int			_sys_bit_depth;					/* system audio bit depth, default 16 */
	private String		_sys_aud_codec;					/* system audio file format, default BWF */
	private int			_sys_xfade_length;				/* system cross fade length in samples */
	private BigDecimal	_sys_gain;						/* system master gain in decibels */
	
	/**
	*	Initializes a new empty <code>SystemSection</code> object which can contain optional information about system setup 
	*	parameters of the originating system that created the ADL. 
	*
	*/
	public SystemSection ()
	{
		super ("SYSTEM");
		_init ();
	}
	
	private void _init ()
	{
		_sys_src_offset = null;
		_sys_bit_depth = -1;
		_sys_aud_codec = "BWF";
		_sys_xfade_length = -1;
		_sys_gain = null;
	}
	
	/**
	*	Sets the value of the System Source Offset field in this System Section.
	*
	*	@param offsetToken A String representation of the TCF value of this documents  System Source Offset. This is a global TCF offset
	*	for all audio sources.
	*/
	public void setSysSrcOffset (String offsetToken) throws InvalidDataException
	{
		if (!offsetToken.equals("_"))
		{
			_sys_src_offset = new TcfToken(offsetToken);
			
		}
	}
	
	/**
	*	Returns a TcfToken that contains the System Source Offset value for this ADL.
	*
	*	@return A TcfToken that contains the System Source Offset value for this ADL. 
	*/
	public TcfToken getSysSrcOffset ()
	{
		return _sys_src_offset;
	}
	
		
	/**
	*	Sets the value of the System Bit Depth field in this System Section.
	*
	*	@param sysBitDepth A String representation of the bit depth value for this document.
	*/
	public void setSysBitDepth (String sysBitDepth) throws InvalidDataException
	{
		try
		{
			_sys_bit_depth = Integer.parseInt(sysBitDepth);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("System Bit Depth must be an integer.", e);
		}
	}
	
	/**
	*	Sets the value of the System Bit Depth field in this System Section.
	*
	*	@param sysBitDepth A int representation of the bit depth value for this document.
	*/
	public void setSysBitDepth (int sysBitDepth)
	{
		_sys_bit_depth = sysBitDepth;
	}
	
	/**
	*	Returns an int that contains System Bit Depth value for this ADL.
	*
	*	@return An int that contains System Bit Depth value for this ADL.
	*
	*/
	public int getSysBitDepth ()
	{
		return _sys_bit_depth;
	}
	
	/**
	*	Sets the System Audio Codec field for this ADL Document. 
	*
	*	@param sysCodec A String containing the name of the System Audio Codec, For example BWF.
	*/
	public void setSysAudCodec (String sysCodec) throws InvalidDataException
	{
		if (EDMLParser.validateADLString(sysCodec))
		{
			_sys_aud_codec = sysCodec;
		}
		else
		{
			throw new InvalidDataException("\"" + sysCodec + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns a String that contains System Audio Codec value for this ADL.
	*
	*	@return A String that contains System Audio Codec value for this ADL.
	*/
	public String getSysAudCodec ()
	{
		return _sys_aud_codec;
	}
	
	/**
	*	Sets the System Crossfade Length field for this ADL Document. 
	*
	*	@param xfadeLength A String containing the global duration for crossfades for this ADL. String should be parsable as an int. A valid TCF
	*	value will be handled to support some legacy applications that incorrectly set this using the TCF data type, however, the value will be
	*	converted to an int representation for output.
	*/
	public void setSysXfadeLength (String xfadeLength) throws InvalidDataException
	{
		
		if (xfadeLength.matches("[0-9]{2}[^0-9][0-9]{2}[^0-9][0-9]{2}[^0-9][0-9]{2}([^0-9][0-9]{4})?"))
		{
			try
			{
				TcfToken temp = new TcfToken(xfadeLength);
				long tmp = temp.valueOf();
				_sys_xfade_length = (int)tmp;
			}
			catch (Exception e)
			{
				throw new InvalidDataException ("Error occured while trying to convert TCF to int for System Crossfade Length.", e);
			}
			
			this.addValidationError("SYS_XFADE_LEN given as tcf value, should be integer sample count");
			//throw new InvalidDataException ("System Crossfade Length given as a TCF value, Should be given as an int.");
			
		}
		else
		{
			try
			{
				_sys_xfade_length = Integer.parseInt(xfadeLength);
			}
			catch (NumberFormatException e)
			{
				throw new InvalidDataException("System Crossfade Length must be an integer.", e);
			}
		}

	}
	
	/**
	*	Sets the System Crossfade Length field for this ADL Document. 
	*
	*	@param xfadeLength An int containing the global duration for crossfades for this ADL. 
	*/
	public void setSysXfadeLength (int xfadeLength)
	{
		_sys_xfade_length = xfadeLength;
	}
	
	/**
	*	Returns an int that contains the System Crossfade Length field for this ADL Document. 
	*
	*	@return An int that contains the System Crossfade Length field for this ADL.
	*/
	public int getSysXfadeLength ()
	{
		return _sys_xfade_length;
	}
	
	/**
	*	Sets the System Gain  field for this ADL Document. 
	*
	*	@param sysGain A String containing the global System Gain for this ADL. String must be given in decimal format.
	*/
	public void setSysGain (String sysGain) throws InvalidDataException
	{
		try
		{
			_sys_gain = new BigDecimal(sysGain);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("System Gain must be given in decimal format.", e);
		}
	}
	
	/**
	*	Sets the System Gain  field for this ADL Document. 
	*
	*	@param sysGain A Big Decimal Object containing the global System Gain for this ADL.
	*/
	public void setSysGain (BigDecimal sysGain) 
	{
		_sys_gain = sysGain;
	}
	
	/**
	*	Returns a <code>BigDecimal</code> that contains the System Gain field for this ADL Document. 
	*
	*	@return A <code>BigDecimaL</code> that contains the System Gain field for this ADL.
	*/
	public BigDecimal getSysGain ()
	{
		return _sys_gain;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("SYS_SRC_OFFSET"))
			{
				this.setSysSrcOffset((String)data.elementAt(0));
			} 
			else if (keyword.equalsIgnoreCase("SYS_BIT_DEPTH"))
			{
				this.setSysBitDepth((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SYS_AUD_CODEC"))
			{
				this.setSysAudCodec((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SYS_XFADE_LEN"))
			{
				this.setSysXfadeLength((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SYS_GAIN"))
			{
				this.setSysGain((String)data.elementAt(0));
			}
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	/*protected void readHeader (ADLTokenizer tokenizer) throws EDMLParserException
	{
		while (tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END)
		{
			if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
				
				
				else if (tokenizer.tokenType != ADLTokenizer.TT_COMMENT)
				{
					this.addValidationError("UNEXPECTED KEYWORD FOUND IN SYSTEM HEADER. Token value: \"" + 
								tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
					this.addExtraData("Line: " + String.valueOf(tokenizer.getLineNumber()) + " " + tokenizer.tokenValue, tokenizer.tokenValue);
				}
				
				
			}
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				throw new EDMLParserException("Found overlapping SECTION Tags for " + this.sectionName + " and " + tokenizer.tokenValue);
			}
			else if (tokenizer.tokenType != ADLTokenizer.TT_COMMENT)
			{
				this.addValidationError("UNEXPECTED DATA FOUND IN SYSTEM HEADER. Token value: \"" + 
							tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				this.addExtraData("Line: " + String.valueOf(tokenizer.getLineNumber()) + " " + tokenizer.tokenValue, tokenizer.tokenValue);
			}
		}*/
		/* capture close of section tage for validation purposes */
		/*if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
		{
			if (tokenizer.tokenValue.equals(this.sectionName))
			{
				this.sectionClosed();
			} 
			else
			{
				throw new EDMLParserException(this.sectionName + " close tag not found");
			}
			//debug
			//System.out.println(rval);
		}
	}*/
	
	/**
	*	Returns a String representation of this <code>SystemSection</code>.
	*
	*	@return A String representation of this <code>SystemSection</code>.
	*/
	public String toString ()
	{
		String rval = "\n<SYSTEM>";
		
		if (_sys_src_offset != null)
		{
			rval = rval.concat("\n\t(SYS_SRC_OFFSET)\t" + _sys_src_offset + "");
		}
		
		if (_sys_bit_depth > -1)
		{
			rval = rval.concat("\n\t(SYS_BIT_DEPTH)\t\t" + _sys_bit_depth + "");
		}
		
		if (_sys_aud_codec != null)
		{
			rval = rval.concat("\n\t(SYS_AUD_CODEC)\t\t\"" +  EDMLParser.sanitizeEDMLQuotes(_sys_aud_codec) + "\"");
		}
		
		if (_sys_xfade_length > -1)
		{
			rval = rval.concat("\n\t(SYS_XFADE_LEN)\t\t" + _sys_xfade_length);
		}
		
		if (_sys_gain != null)
		{
			rval = rval.concat("\n\t(SYS_GAIN)\t\t" + _sys_gain);
		}
		
		rval = rval.concat("\n</SYSTEM>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("system");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		if (_sys_src_offset != null)
		{
			e = new Element("sysSrcOffset");
			e.setNamespace(ADLSection.aes);
			e.setText(_sys_src_offset.toString());
			vs.addContent(e);
		}
		
		e = new Element("sysBitDepth");
		e.setNamespace(ADLSection.aes);
		if (_sys_bit_depth > -1)
		{
			e.setText(Integer.toString(_sys_bit_depth));
		}
		vs.addContent(e);
		
		e = new Element("sysAudCodec");
		e.setNamespace(ADLSection.aes);
		e.setText(_sys_aud_codec);
		vs.addContent(e);
		
		if (_sys_xfade_length > -1)
		{
			e = new Element("sysXfadeLen");
			e.setNamespace(ADLSection.aes);
			e.setText(Integer.toString(_sys_xfade_length));
			vs.addContent(e);
		}
		
		if (_sys_gain != null)
		{
			e = new Element("sysGain");
			e.setNamespace(ADLSection.aes);
			e.setText(_sys_gain.toString());
			vs.addContent(e);
		}
		
				
		return vs;
	}
	
	
	/**
	*	Converts all TcfToken's contained in this <code>SystemSection</code> to a new sample-rate representation. The sample remainder 
	*	of the TcfToken is adjusted by the ratio of the new sample-rate and the frame count.
	*
	*	@param sampleRate The new target sample-rate that all of this object's TcfToken's will be converted to. 
	*	@see TcfToken
	*/
	public void resample (double sampleRate)
	{
		if (_sys_src_offset != null)
		{
			_sys_src_offset.resample(sampleRate);
		}
	}
	
	/**
	*	Creates and returns a deep clone of this <code>SystemSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		SystemSection rval = (SystemSection)super.clone();
		if (_sys_src_offset != null)
		{
			rval._sys_src_offset = (TcfToken)this._sys_src_offset.clone();
		}
		return rval;
	}
	
	

}

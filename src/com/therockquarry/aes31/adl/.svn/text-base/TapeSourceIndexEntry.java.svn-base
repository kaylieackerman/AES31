/*
	-------------------------------------------------------------------------------
	TapeSourceIndexEntry.java
	AES31-3

	Created on 7/8/05.

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

	/**
	*	The <code>TapeSourceIndexEntry</code> object wraps an aes-31 Tape Source element, a tape reference to 
	*	indicate the original source of the associated file where appropriate.  For further details see the ADL specification, available 
	*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
	*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
	*	Part 3: Simple project interchange</a>.
	*
	*	@author David Ackerman
	*/
public class TapeSourceIndexEntry extends BaseIndexEntry implements Cloneable {

	private final static String	_KEY_LETTER = "T";
	private String		_tapename;
	private TcfToken	_tape_orig;
	private TcfToken	_file_orig;
	private Range		_t_chan;
	private Range		_f_chan;
	
	/**
	* Initializes a new empty <code>TapeSourceIndexEntry</code> object which represents a single Tape Source entry in the Source Index.
	*/
	public TapeSourceIndexEntry ()
	{
		super ();
		_init ();
	}
	
	/**
	*	Initializes a new <code>TapeSourceIndexEntry</code> object from the String objects read during the parsing of an ADL document.
	*
	*	@param tapename Text description that identifies the tape tp th operator.
	*	@param tape_orig A TCF String representing the first frame of tape material transferred to the original file.
	*	@param file_orig A TCF String representing the corresponding file time stamp, equivalent to the BWF timestamp found in the source files BEXT chunk.
	*	@param tape_chan_range The tape channels range that map to the range given by the <code>file_chan_range</code> parameter.
	*	@param file_chan_range The file channels range that map to the range given by the <code>tape_chan_range</code> parameter.
	*/
	public TapeSourceIndexEntry (String tapename, String tape_orig, String file_orig, String tape_chan_range, String file_chan_range) throws InvalidDataException
	{
		super ();
		_init ();
		
		this.setTapename(tapename);
		this.setTapeOrigin(new TcfToken(tape_orig));
		this.setFileOrigin(new TcfToken(file_orig));
		
		if (tape_chan_range.matches("[0-9]+[-][0-9]+"))
		{
			this.addValidationError("Tape Index for  (" + _tapename + ") Tape Channel range uses '-'. Should use '~'");
			tape_chan_range = tape_chan_range.replaceAll("-", "~");
		}
		
		if (file_chan_range.matches("[0-9]+[-][0-9]+"))
		{
			this.addValidationError("Tape Index for  (" + _tapename + ") File Channel range uses '-'. Should use '~'");
			file_chan_range = file_chan_range.replaceAll("-", "~");
		}
		
		this.setTapeChannel(new Range(tape_chan_range));
		this.setFileChannel(new Range(file_chan_range));
	}
	
	private void _init ()
	{
		_tapename = null;
		_tape_orig = null;
		_file_orig = null;
		try
		{
			_t_chan = new Range("1");
			_f_chan = new Range("1");
		}
		catch (InvalidDataException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	*	Sets the user defined name of the audio tape associated with this <code>TapeSourceIndexEntry</code>.
	*
	*	@param tapename The user defined name for the audio tape assiciated with this <code>TapeSourceIndexEntry</code>.
	*	*throws InvalidDataException If the tapename parameter exceeds the 255 character limit of the AES31-3 Specification.
	*/
	public void setTapename (String tapename) throws InvalidDataException
	{
		if (!EDMLParser.validateADLString(tapename))
		{
			throw new InvalidDataException("tapename must be a valid EDML String, <= 255 characters in length.");
		}
					
		_tapename = tapename;
	}
	
	/**
	*	Returns the user defined  name of the audio tape associated with this <code>TapeSourceIndexEntry</code>.
	*
	*	@return  The user defined  name of the audio tape associated with this <code>TapeSourceIndexEntry</code>.
	*/	
	public String getTapename ()
	{
		return _tapename;
	}
	
	/**
	*	Sets the tape origin timecode representing the first frame of tape material transferred to the original file.
	*
	*	@param tapeOrig The timecode representing the first frame of tape material transferred to the original file.
	*/
	public void setTapeOrigin (TcfToken tapeOrig)
	{
		_tape_orig = tapeOrig;
	}
	
	/**
	*	Returns the tape origin timecode representing the first frame of tape material transferred to the original file.
	*
	*	@return The tape origin timecode representing the first frame of tape material transferred to the original file.
	*/
	public TcfToken getTapeOrigin ()
	{
		return _tape_orig;
	}
	
	/**
	*	Sets the file origin timcode representing the file time stamp, 
	*	equivalent to the BWF timestamp found in the source files BEXT chunk.
	*
	*	@param fileOrig The  file origin timcode representing the file time stamp, 
	*	equivalent to the BWF timestamp found in the source files BEXT chunk.
	*/
	public void setFileOrigin (TcfToken fileOrig)
	{
		_file_orig = fileOrig;
	}

	public TcfToken getFileOrigin ()
	{
		return _file_orig;
	}
	
	public void setTapeChannel (Range tapeChan)
	{
		_t_chan = tapeChan;
	}
	
	public Range getTapeChannel ()
	{
		return _t_chan;
	}
	
	public void setFileChannel (Range fileChan)
	{
		_f_chan = fileChan;
	}

	public Range getFileChannel ()
	{
		return _f_chan;
	}
	
	public String toString()
	{
		//String rval = Integer.toString(_index_number);
		String rval = "";
		rval = rval.concat(" (" + _KEY_LETTER + ") \"" +  EDMLParser.sanitizeEDMLQuotes(_tapename) + "\"  " + _tape_orig + " " + _file_orig + 
			" " + _t_chan + " " + _f_chan);
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("tapeSource");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("keyLetter", _KEY_LETTER);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());

		e = new Element("tapeName");
		e.setNamespace(ADLSection.aes);
		e.setText(_tapename);
		vs.addContent(e);
		
		e = new Element("tapeOrig");
		e.setNamespace(ADLSection.aes);
		e.setText(_tape_orig.toString());
		vs.addContent(e);
		
		e = new Element("fileOrig");
		e.setNamespace(ADLSection.aes);
		e.setText(_file_orig.toString());
		vs.addContent(e);
		
		e = new Element("tchan");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_t_chan.getXmlAttributes());
		vs.addContent(e);
		
		e = new Element("fchan");
		e.setNamespace(ADLSection.aes);
		e.setAttributes(_f_chan.getXmlAttributes());
		vs.addContent(e);
		
		return vs;
	}
	
	
	public Object clone () throws CloneNotSupportedException
	{
		TapeSourceIndexEntry rval = (TapeSourceIndexEntry)super.clone();
		rval._tape_orig = (TcfToken)_tape_orig.clone();
		rval._file_orig = (TcfToken)_file_orig.clone();
		rval._t_chan = (Range)_t_chan.clone();
		rval._f_chan = (Range)_f_chan.clone();
		return rval;
	}
	
	public void resample (double sr)
	{
		if (this.getFileOrigin() != null)
		{
			this.getFileOrigin().resample(sr);
		}
		if (this.getTapeOrigin() != null)
		{
			this.getTapeOrigin().resample(sr);
		}
	}
	
}

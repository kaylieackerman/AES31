/*
	-------------------------------------------------------------------------------
	FileSourceIndexEntry.java
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

import java.net.*;
import java.text.*;
import java.util.*;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>FileSourceIndexEntry</code> object wraps an aes-31 File_Path element. File_Path is a customised ASCII-only 
*	locator indicating the absolute path to a file.  For further details see the ADL specification, available 
*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class FileSourceIndexEntry extends BaseIndexEntry implements Cloneable{
	private final static String	_KEY_LETTER = "F";
	private String	_file_path;				/* URL */
	private String	_uid;
	private TcfToken	_file_in;			/* Equivalent to BWF timestamp of current file */
	private TcfToken	_file_len;			/* Sample length of current file */
	private String	_descr;				/* Same as BWF description field */
	private String	_code;				/* Indicates purpose of file,	N is normal file
											X is crossfade render file
											A is alternate audio file */
	
	private final static String FILE_PATH_PREFIX = "URL:file://localhost";
	
	/**
	* Initializes a new empty <code>FileSourceIndexEntry</code> object which represents a single File_Path entry in the Source Index.
	*/
	public FileSourceIndexEntry ()
	{
		super ();
		_init ();
	}
	
	/**
	*	Initializes a new <code>FileSourceIndexEntry</code> object from the String objects read during the parsing of an ADL document.
	*
	*	@param fullpath The entire path for the audio file that is associated with this <code>FileSourceIndexEntry</code> including the
	*	"URL:file://localhost/" prefix.
	*	@param uid The unique identifier for the file referenced by the fullpath parameter, as defined in the broadcast wave BEXT chunk's
	*	OriginatorReference field.
	*	@param startTc The BWF timestamp of the file referenced by the fullpath parameter, expressed as a TCF string.
	*	@param durationTc The sample length in the file referenced by the fullpath parameter, expressed as a TCF string.
	*	@param desc The text from the BWF BEXT chunk's description field.
	*	@param code The usage code for this object, "N" for Normal audio file, "X" for cross-fade render file, "A" for alternate audio file or "_".
	*/
	public FileSourceIndexEntry (String fullpath, String uid, String startTc, String durationTc, String desc, String code) throws InvalidDataException, MalformedURLException
	{
		super ();
		_init ();
		
		this.setAbsolutePath(fullpath);
		
		this.setUid(uid);
		
		if (!startTc.equals("_"))
		{
			this.setFileIn(new TcfToken(startTc));
		}
		
		if (!durationTc.equals("_"))
		{
			this.setFileLength(new TcfToken(durationTc));
		}
		
		if (!desc.equals("_"))
		{
			this.setDescription(desc);
		}
		
		if (!code.equals("_"))
		{
			this.setUsageCode(code);			
		}
	}
	
	private void _init ()
	{
		_file_path = null;
		_uid = null;
		_file_in = null;
		_file_len = null;
		_descr = null;
		_code = null;
	}
	
	/**
	*	Sets the unique identifier for this <code>FileSurceIndexEntry</code>
	*	to the value of the <code>uid</code> String, which should contain the
	*	value of the source audio files Originator Reference field. Validates 
	*	the passed parameter to ensure it is <= 32 characters in length.
	*
	*	@param uid The unique Identifier for the source audio file as found in
	*	the BEXT chunk's Originator Reference field.
	*	@throws InvalidDataException If the <code>uid</code> parameter exceeds
	*	the 32 character limit specified in the AES-31 specification.
	*/
	public void setUid (String uid) throws InvalidDataException
	{
		if (validateUid(uid))
		{
			_uid = uid;
		}
		else
		{
			throw new InvalidDataException (uid + " is not a valid Uid.");
		}
	}
	
	/**
	*	Returns the Unique Identifier for the audio file associated with this 
	*	</code>FileSourceIndexEntry</code>.
	*
	*	@return A String containing the unique identifier for the audio file 
	*	associated with this <code>FileSourceIndexEntry</code>.
	*/
	public String getUid ()
	{
		return _uid;
	}
	
	/**
	*	Sets the path to the audio source file associated with this 
	*	<code>FileSourceIndexEntry</code>, using the full path representation
	*	including the "URL:file://localhost/" prefix. See setFilePath() if
	*	you just want to set the file path component.
	*
	*	@param fullPath The entire absolute path to the audio source file
	*	associated with this <code>FileSourceIndexEntry</code>.
	*	@throws MalformedURLException If there is a problem parsing the fullPath
	*	parameter as a URL.
	*	
	*/
	public void setAbsolutePath (String fullPath) throws MalformedURLException
	{
		URL url = new URL(fullPath);
		_file_path = url.getPath();
		int slashCount = 0;
		for (slashCount = 0; _file_path.charAt(slashCount) == '/'; slashCount++)
		{
			;
		}
		
		_file_path = _file_path.substring(slashCount - 1, _file_path.length());

	}
	
	/**
	*	Returns the absolute path for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>.
	*
	*	@return  The absolute path for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>.
	*/
	public String  getAbsolutePath ()
	{
		return FILE_PATH_PREFIX + _file_path;
	}
	
	public String  getCanonicalAbsolutePath () throws IOException
	{
		return new File(FILE_PATH_PREFIX + _file_path).getCanonicalPath();
	}
	
	/**
	*	Returns the file path for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>, excluding the "URL:file://localhost/" prefix.
	*
	*	@return  The file path for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>, excluding the "URL:file://localhost/" prefix.
	*/
	public String getFilePath ()
	{
		return _file_path;
	}
	
	public File getFile ()
	{
		return new File (getFilePath());
	}
	
	public String getCanonicalFilePath () throws IOException
	{
		return new File(_file_path).getCanonicalPath();
	}
	
	/**
	*	Sets the path to the audio source file associated with this 
	*	<code>FileSourceIndexEntry</code>, using the file path components that
	*	follow the "URL:file://localhost/" prefix. See setAbsolutePath() if
	*	you just want to set the file path using the entire path including the prefix.
	*
	*	@param path The file path to the audio source file
	*	associated with this <code>FileSourceIndexEntry</code>, excluding the "URL:file://localhost/" prefix.
	*	
	*/
	public void setFilePath (String path)
	{
		int slashCount = 0;
		for (slashCount = 0; path.charAt(slashCount) == '/'; slashCount++)
		{
			;
		}
		
		if (slashCount > 0)
		{
			path = path.substring(slashCount - 1, path.length());
		}
		
		_file_path = path;
	}

	/**
	*	Sets the File_in field of this <code>FileSourceIndexEntry</code> which is equivalent to the BWF timestamp
	*	of the source audio file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@param startTc A <code>TcfToken</code> object containing the BWF timestamp for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>.
	*/	
	public void setFileIn (TcfToken startTc)
	{
		_file_in = startTc;
	}
	
	/**
	*	Returns the value of the File_in field of this <code>FileSourceIndexEntry</code> which should be equivalent to the BWF
	*	timestamp of the audio source file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@return A <code>TcfToken</code> representing the File_in field of this <code>FileSourceIndexEntry</code> if the field is
	*	defined, otherwise NULL.
	*/
	public TcfToken getFileIn ()
	{
		return _file_in;
	}
	
	/**
	*	Sets the File_len field of this <code>FileSourceIndexEntry</code> which is equivalent to the duration
	*	of the source audio file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@param length A <code>TcfToken</code> object containing the duration for the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>.
	*/	
	public void setFileLength (TcfToken length)
	{
		_file_len = length;
	}
	
	/**
	*	Returns the value of the File_len field of this <code>FileSourceIndexEntry</code> which should be equivalent to the 
	*	duration of the audio source file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@return A <code>TcfToken</code> representing the File_len field of this <code>FileSourceIndexEntry</code> if the field is
	*	defined, otherwise NULL.
	*/
	public TcfToken getFileLength ()
	{
		return _file_len;
	}
	
	/**
	*	Sets the descr field of this <code>FileSourceIndexEntry</code> which is equivalent to the BWF description field
	*	in the BEXT chunk of the source audio file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@param descr A String containing the description of the audio source file associated with this
	*	<code>FileSourceIndexEntry</code>, as found in the files BEXT chunk.
	*	@throws InvalidDataException if the <code>descr</code> parameter is > 255 characters in length.
	*/	
	public void setDescription (String descr) throws InvalidDataException
	{
		if (EDMLParser.validateADLString(descr))
		{
			_descr = descr;
		}
		else
		{
			throw new InvalidDataException (descr + " is not a valid Description.");
		}
	}
	
	/**
	*	Returns the value of the descr field of this <code>FileSourceIndexEntry</code> which should be equivalent to the 
	*	BWF description field in the BEXT chunk of the audio source file associated with this <code>FileSourceIndexEntry</code>.
	*
	*	@return A String representing the descr field of this <code>FileSourceIndexEntry</code> if the field is
	*	defined, otherwise NULL.
	*/
	public String getDescription ()
	{
		return _descr;
	}
	/**
	*	Sets the usage code for the audio source file associated with this <code>FileSourceIndexEntry</code>, where "N" means
	*	Normal Audio, "X" means Rendered Crossfade and "A" means Alternate Audio. 
	*
	*	@param code a Single character String of either "N", "X" or "A".
	*	@throws InvalidDataException If the <code>code</code> is any value other than "N", "X" or "A".
	*/
	public void setUsageCode (String code) throws InvalidDataException
	{
		if (validateUsageCode(code))
		{
			_code = code.toUpperCase();
		}
		else
		{
			throw new InvalidDataException (code + " is not a valid Usage Code.");
		}
	}
	
	/**
	*	Returns the usage code for the audio source file associated with this <code>FileSourceIndexEntry</code> if the code field 
	*	is defined, otherwise NULL.
	*/
	public String getUsageCode ()
	{
		return _code;
	}
	
	private boolean validateUsageCode (String code)
	{
		boolean rval = false;
		
		if (code.equalsIgnoreCase("N") || code.equalsIgnoreCase("X") || code.equalsIgnoreCase("A"))
		{
			rval = true;
		}
		
		return rval;
	}
	
	private boolean validateUid (String u)
	{
		boolean rval = false;
		
		if (u.length() <= 32)
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Returns a String representation of this <code>FileSourceIndexEntry</code>.
	*
	*	@return A String representation of this <code>FileSourceIndexEntry</code>.
	*/
	public String toString()
	{
		String rval = "";
		
		rval = rval.concat(" (" + _KEY_LETTER + ") \"" + FILE_PATH_PREFIX +  EDMLParser.sanitizeEDMLQuotes(_file_path) + "\"  " +  ((_uid == null) ? "_" : EDMLParser.sanitizeEDMLQuotes(_uid)) + " " + ((_file_in == null) ? "_" : _file_in) + 
						   " " + ((_file_len == null) ? "_" : _file_len) + " " + ((_descr == null) ? "_" : "\"" +  EDMLParser.sanitizeEDMLQuotes(_descr) + "\"") + " " + ((_code == null) ? "_" : _code));
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("filePathSource");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("keyLetter", _KEY_LETTER);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());

		e = new Element("filePath");
		e.setNamespace(ADLSection.aes);
		e.setText(FILE_PATH_PREFIX + _file_path);
		vs.addContent(e);
		
		e = new Element("uid");
		e.setNamespace(ADLSection.aes);
		e.setText(_uid);
		vs.addContent(e);
		
		if (_file_in != null)
		{
			e = new Element("fileIn");
			e.setNamespace(ADLSection.aes);
			e.setText(_file_in.toString());
			vs.addContent(e);
		}
		
		if (_file_len != null)
		{
			e = new Element("fileLen");
			e.setNamespace(ADLSection.aes);
			e.setText(_file_len.toString());
			vs.addContent(e);
		}
		
		if (_descr != null)
		{
			e = new Element("descr");
			e.setNamespace(ADLSection.aes);
			e.setText(_descr);
			vs.addContent(e);
		}
		
		if (_code != null)
		{
			e = new Element("code");
			e.setNamespace(ADLSection.aes);
			e.setText(_code);
			vs.addContent(e);
		}
		
		
		return vs;
	}
	
	
	/**
	*	Creates and returns a semi-deep clone of this <code>FileSourceIndexEntry</code> object.
	*
	 *	@return A semi-deep-cloned copy of this object. Note that the new parent is not known by this object when cloning and is
	 *	explicitly set by the <code>SourceEntry</code> when cloning is a result of a clone of the <code>
	 *	SourceEntry</code>. If the clone is initiated from some other vector then it is the responsibility of that 
	 *	code to set the parent as appropriate. If nothing is done to explicitly set the parent of the clone, it will continue to 
	 *	point to the parent of the object from which it was cloned.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		FileSourceIndexEntry rval = (FileSourceIndexEntry)super.clone();
		
		if (_file_in != null)
		{
			rval._file_in = (TcfToken)this._file_in.clone();
		}
		
		if (_file_len != null)
		{
			rval._file_len = (TcfToken)this._file_len.clone();
		}
		
		
		return rval;
	}
	
	public void resample (double sr)
	{
		if (this.getFileIn() != null)
		{
			this.getFileIn().resample(sr);
		}
		if (this.getFileLength() != null)
		{
			this.getFileLength().resample(sr);
		}
	}
																	
}

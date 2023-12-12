/*
	-------------------------------------------------------------------------------
	TracklistSection.java
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
	to do: moveTrackWithinList() moves track to new position in model
		   moveTrackToList() moves track only to other TracklistSection
		   moveTrackAndEditsWithinList() moves track to new position in model along with all edit events 
		   moveTrackAndEditsToList() moves track and all edit events to other TracklistSection/EventListSection	+ source is added to list
*/

package com.therockquarry.aes31.adl;

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>TracklistSection</code> object contains a track list; 
*	destination tracks are named for operational identification. Track numbers must be unique and correspond 
*	to the destination channels in the event list. For further details see the ADL specification, available 
*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class TracklistSection extends BaseSection implements Cloneable {
	
	private FlexibleTreeMap<Integer,TrackType> _tracks;

	/**
	*	Constructs a new empty <code>TracklistSection</code> object. 
	*/
	public TracklistSection ()
	{
		super ("TRACKLIST");
		_init ();
	}
	
	private void _init ()
	{
		_tracks = new FlexibleTreeMap<Integer, TrackType>();
	}
	
	/**
	*	Constructs a <code>TrackType</code> object from the passed parameters and adds the resulting object to this Tracklist at the  index specified
	*	by the trackNumber parameter.
	*
	*	@param trackNumber A String that represents the integer based track number for the TrackType to be created and added to this Tracklist.
	*	The value corresponds to the destination channel values used in the EventListSection. Value may be 1 - n. Note that track numbers must be 
	*	unique within a given <code>TracklistSection</code>.
	*	@param trackName The name for the specified Destination Channel.
	*	@throws InvalidDataException If the <code>
	*	trackNumber</code> parameter cannot be converted into a valid Integer (<code>trackNumber</code> >= 1).
	*/
	public void addTrackAtIndex (String trackNumber, String trackName) throws InvalidDataException
	{
		int trackNo;
		
		try
		{
			trackNo = new Integer(trackNumber).intValue();
			TrackType tt = new TrackType(trackName, this);
			this.addTrackAtIndex(trackNo, tt);
		}
		catch (Exception e)
		{
			throw new InvalidDataException("Unable to add Track to TracklistSection", e);
		}
	}
	
	/**
	*	Adds a <code>TrackType</code> object to this <code>Tracklist</code> at the specified <code>trackNumber</code>.
	*
	*	@param trackNumber The track number where the <code>track</code> parameter will be placed in this <code>TracklistSection</code>. 
	*	@param track The <code>TrackType</code> object to add to this <code>Tracklist</code>. 
	*	@throws InvalidDataException If this <code>TracklistSection</code> already contains an instance of <code>track</code>, or if the <code>
	*	trackNumber</code> parameter is out of range (<code>trackNumber</code> < 1) or if this <coded>TracklistSection</code> already contains
	*	an entry at the specified <code>trackNumber</code>.
	*/
	public void addTrackAtIndex (int trackNumber, TrackType track) throws InvalidDataException
	{
		if (!validateTrackNumber(trackNumber))
		{
			throw new InvalidDataException("\"" + trackNumber + "\" is not valid. Number must be greater than or equal to 1");
		}
		
		Integer key = new Integer(trackNumber);
		
		if (_tracks.containsKey(key))
		{
			throw new InvalidDataException("Tracklist destination channels must be unique. \"" + trackNumber + "\" already exists in this Tracklist.");
		}
		
		if (_tracks.containsValue(track))
		{
			throw new InvalidDataException("Track \"" + track + "\" already exists in the Track List at index \"" + this.getTrackNumberForEntry(track) + "\".");
		}
		
		_tracks.put(new Integer(trackNumber), track);
		track.setParent(this);
	}
	
	/**
	*	Appends the passed <code>TrackType</code> object to the end of this <code>TracklistSection</code> list. Note that this method sets
	*	the indexNumber of the passed <code>TrackType</code> object.
	*
	*	@param track The <code>TrackType</code> object to add to this <code>TracklistSection</code>.
	*	@throws InvalidDataException If this <code>TracklistSection</code> already contains an instance of <code>track</code>.
	*/
	public void addTrack (TrackType track) throws InvalidDataException
	{
		int lastKey;
		
		try
		{
			lastKey = ((Integer)_tracks.lastKey()).intValue();
			lastKey++;
		}
		catch (NoSuchElementException e)
		{
			lastKey = 1;
		}
		
		if (!_tracks.containsValue(track))
		{
			_tracks.put(new Integer(lastKey), track);
			track.setParent(this);
		}
		else
		{
			throw new InvalidDataException("Track \"" + track + "\" already exists in the Track list at index \"" + this.getTrackNumberForEntry(track) + "\".");
		}
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("TRACK"))
			{
				this.addTrackAtIndex((String)data.elementAt(0), (String)data.elementAt(1));
			} 
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	*	Sets/replaces the <code>TrackType</code> object to this Tracklist for the track number specified by the <code>trackNumber</code> parameter.
	*
	*	@param trackNumber The track number position in this <code>TracklistSection</code> to set the <code>track</code> parameter to.
	*	@param track The <code>TrackType</code> object to add to this <code>Tracklist</code>. 
	*	@throws InvalidDataException If this <code>TracklistSection</code> already contains an instance of <code>track</code>, or if the <code>
	*	trackNumber</code> parameter is out of range (<code>trackNumber</code> < 1).
	*/
	public void setTrackAtIndex (int trackNumber, TrackType track) throws InvalidDataException
	{
		if (_tracks.containsValue(track))
		{
			throw new InvalidDataException("Track \"" + track + "\" already exists in the Track List at index \"" + this.getTrackNumberForEntry(track) + "\".");
		}
		
		if (validateTrackNumber(trackNumber))
		{
			Integer key = new Integer(trackNumber);
			_tracks.put(key, track);
			track.setParent(this);
		}
		else
		{
			throw new InvalidDataException("\"" + trackNumber + "\" is not valid. Number must be greater than or equal to 1");	
		}
		
	}
	
	/**
	*	Deletes the <ocde>TrackType</code> specified by the <code>trackNumber</code> parameter from this <code>TracklistSection</code>.
	*	If the track does not exist, this call has no effect. 
	*
	*	@param trackNumber The track number of the <code>TrackType</code> to be deleted from this <code>TracklistSection</code>.
	*/
	public void deleteTrackAtIndex (int trackNumber)
	{
		Integer key = new Integer(trackNumber);
		if (_tracks.containsKey(key))
		{
			_tracks.remove(key);
		}
	}
	
	/**
	*	Deletes all instances of the specified <code>track</code> object from this <code>TracklistSection</code>. If the track does not exist, this call has no effect. 
	*
	*	@param track The track to be deleted from this <code>TracklistSection</code>.
	*/
	public void deleteTrack (TrackType track)
	{
		_tracks.removeKeysForValue(track);
	}
	
	/**
	*	Returns the <code>TrackType</code> object specified by the <code>trackNumber</code> parameter.
	*
	*	@param trackNumber The track number for the <code>TrackType</code> object to be retrieved.
	*	@return The <code>TrackType</code> object for the <code>trackNumber</code> parameter or null if the track does not exist.
	*/
	public TrackType getTrackAtIndex (int trackNumber)
	{
		TrackType rval = null;
		
		rval = (TrackType)_tracks.get(new Integer(trackNumber));
		
		return rval;

	}
	
	/**
	*	Returns an ArrayList containing all of the <code>TrackType</code> objects contained in this <code>TracklistSection</code>.
	*
	*	@return ArrayList containing all of the <code>TrackType</code> objects contained in this <code>TracklistSection</code>.
	*/
	public ArrayList<TrackType> getTracks ()
	{
		ArrayList<TrackType> rval = new ArrayList<TrackType>(_tracks.values());
				
		return rval;
	}
	
	
	/**
	*	Returns the index number for the first instance of <code>track</code> in this <code>TracklistSection</code>.
	*
	*	@param track The <code>trackType</code> object to search for in this <code>TracklistSection</code>.
	*	@return An int representing the index number in this <code>TracklistSection</code> where the first instance of
	*	the <code>track</code> object is found.
	*/
	public int getTrackNumberForEntry (TrackType track)
	{
		int rval = -1;
		
		ArrayList al = _tracks.keysForValue(track);
		
		if (al.size() > 0)
		{
			rval = ((Integer)al.get(0)).intValue();
		}
		
		return rval;
	}
	
	/**
	*	Validates an int to ensure that it conforms to the restictions for a track number as defined in the AES-31-3 specification.
	*
	*	@param trackNumber The int to validate for compliance with the AES-31-3 specification. The value must be greater than or equal to 1.
	*	@return True if the int is valid, false otherwise.
	*/
	private boolean validateTrackNumber (int trackNumber)
	{
		boolean rval = false;
		
		if (trackNumber >= 1)
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Determines if the <code>TrackType</code> specified by the <code>track</code> parameter exists within this <code>TracklistSection</code>.
	*
	*	@param track The <code>TrackType</code> object to search for within this <code>TracklistSection</code>.
	*	@return Returns a boolean value, <code>true</code> if <code>track</code> is present in this <code>TracklistSection</code>, 
	*	<code>false</code> otherwise.
	*/
	public boolean containsTrack (TrackType track)
	{
		return _tracks.containsValue(track);
	}
	
	
	/**
	*	Returns a String representation of this <code>TracklistSection</code>.
	*
	*	@return A String representation of this <code>TracklistSection</code>.
	*/
	public String toString ()
	{
		String entry;
		String rval = "\n<TRACKLIST>";
		
		Set<Integer> set = _tracks.keySet();
		Iterator<Integer> it = set.iterator();
		
		while (it.hasNext())
		{
			Integer key = it.next();
			
			rval = rval.concat("\n\t(Track)\t" + key + "\t\"" +  _tracks.get(key).toString() + "\""); 
		}
		
		rval = rval.concat("\n</TRACKLIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("trackList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		Set<Integer> set = _tracks.keySet();
		Iterator<Integer> it = set.iterator();
		
		while (it.hasNext())
		{
			Integer key = it.next();
			
			e = new Element("track");
			e.setNamespace(ADLSection.aes);
			e.setAttribute("id", "_" + UUID.randomUUID().toString());
			e.setAttribute("trackNumber", Integer.toString(key));
			e.setAttribute("trackName", _tracks.get(key).getTrackName());
			vs.addContent(e);
		}
		
		return vs;
	}
	
	
	/**
	*	Creates and returns a deep clone of this <code>TracklistSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		TracklistSection rval = (TracklistSection)super.clone();
		rval._tracks = (FlexibleTreeMap)this._tracks.clone();
		
		Iterator<Integer> it = _tracks.keySet().iterator();
		TrackType toCopy = null;
		TrackType theClone = null;
		Integer key;
		
		while (it.hasNext())
		{
			key = it.next();
			toCopy = _tracks.get(key);
			theClone = (TrackType)toCopy.clone();
			rval._tracks.put(key, theClone);
			theClone.setParent(rval);
		}
		
		return rval;
	}
	
}

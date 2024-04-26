/*
	-------------------------------------------------------------------------------
	TrackType.java
	AES31-3

	Created on 4/4/06.

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

/**
*	The <code>TrackType</code> object is a wrapper for the track level information stored in the <code>TracklistSection</code>
*	of an ADL document. For further details see the ADL specification, available 
*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class TrackType implements Cloneable 
{
	
	private TracklistSection _parent;
	private String track_name;
	
	
	
	/**
	*	Constructs a new <code>TrackType</code> object. 
	*
	*	@param trackName The name of this track, a String between 1 and 255 characters long that describes/names the track.
	*	@param parent A reference to the <code>TracklistSection</code> which contains this <code>TrackType</code> object.
	*/
	public TrackType (String trackName, TracklistSection parent) throws InvalidDataException
	{
		_init();
		
		this.setTrackName(trackName);
		this.setParent(parent);
	}
	
	
	/**
	*	Sets the <code>TracklistSection</code> object to which this <code>TrackType</code> instance belongs.
	*
	*	@param parent The <code>TracklistSection</code> object that contains this <code>TrackType</code> object.
	*/
	public void setParent (TracklistSection parent)
	{
		_parent = parent;
	}
	
	
	
	/**
	*	Sets the track name for this <code>TrackType</code> instance.
	*
	*	@param trackName The name of this track, a String between 1 and 255 characters long that describes/names the track.
	*/
	public void setTrackName (String trackName) throws InvalidDataException
	{
		if (EDMLParser.validateADLString(trackName))
		{
			track_name = trackName;
		}
		else
		{
			throw new InvalidDataException("\"" + trackName + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the <code>TracklistSection</code> object to which this <code>TrackType</code> instance belongs.
	*
	*	@return  The <code>TracklistSection</code> object that contains this <code>TrackType</code> object.
	*/
	public TracklistSection getParent ()
	{
		if (_parent != null)
		{
			if (!_parent.containsTrack(this))
			{
				_parent = null;
			}
		}

		return _parent;
	}
	
	/**
	*	Convenience method that returns the track number for this <code>TrackType</code> instance by means of querying the parent object.
	*
	*	@return This tracks track number. The track number corresponds to the destination channels used by events in the <code>EventListSection</code>.
	*/
	public int getTrackNumber ()
	{
		int rval = -1;
		
		TracklistSection p = this.getParent();
		if (p != null)
		{
			rval = p.getTrackNumberForEntry(this);
		}
		
		return rval;
	}
	
	/**
	*	Sets the track name for this <code>TrackType</code> instance.
	*
	*	@return The name of this track.
	*/
	public String getTrackName ()
	{
		return track_name;
	}
	
	private void _init ()
	{
		track_name = null;
		_parent = null;
	}
	
	/**
	*	Creates and returns a semi deep clone of this <code>TrackType</code> object.
	*
	*	@return A deep-cloned copy of this object. Note that the new parent is not known by this object when cloning and is
	*	explicitly set by the <code>TracklistSection</code> when cloning is a result of a clone of the <code>
	*	TracklistSection</code>. If the clone is initiated from some other vector then it is the responsibility of that 
	*	code to set the parent as appropriate. If nothing is done to explicitly set the parent of the clone, it will continue to 
	*	point to the parent of the object from which it was cloned.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		TrackType rval = (TrackType)super.clone();
		
		return rval;
	}
	
	
	
	/**
	*	Returns a String representation of this TrackType object
	*
	*	@return A String representation of this TrackType object
	*/
	public String toString ()
	{
		String rval = (EDMLParser.sanitizeEDMLQuotes(this.getTrackName()));
		
		return rval;
	}
	
	
}

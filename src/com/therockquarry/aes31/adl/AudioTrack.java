/*
	-------------------------------------------------------------------------------
	AudioTrack.java
	AES31-3

	Created by Kaylie Ackerman on 12/10/06.

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

import java.util.*;

public class AudioTrack implements Cloneable
{
	/**
	*	The destination track in the adl that this object represents.
	*/
	protected int destTrackChanelNum;
	
	/**
	*	An ArrayList of layers representing the z axis of the adl dest track, each containing an 
	*	ordered (ascending in dest time) arraylist of <code>PlaybackRegions</code> representing the audio 
	*	of this track.
	*/
	protected ArrayList<ArrayList<PlaybackRegion>> audioLayers;
	
	public AudioTrack (int destChannel)
	{
		setDestTrackChanelNum(destChannel);
	}
	
	public void readDocument (ADL adlDoc)
	{
		adlDoc.load();
		EventListSection els = adlDoc.getADLSection().getEventListSection();
		if (els != null)
		{
			ArrayList<BaseEditEntry> allEvents = els.getEventEntriesForDestChannel(destTrackChanelNum);
		}
	}
	
	public void setDestTrackChanelNum (int chanNum)
	{
		if (chanNum > 0)
		{
			destTrackChanelNum = chanNum;
		}
	}
	
	public int getDestTrackChanelNum ()
	{
		return destTrackChanelNum;
	}
	
}

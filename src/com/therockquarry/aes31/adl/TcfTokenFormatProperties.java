/*
	-------------------------------------------------------------------------------
	TcfTokenFormatProperties.java
	AES31-3

	Created on 7/22/06.

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


public class TcfTokenFormatProperties {

	private float _time_base;
	private char _film_framing;
	private int _frame_count;
	private int _video_field;
	private boolean _dropframe;
	
	public TcfTokenFormatProperties (float timeBase, int frameCount, char filmFraming, int videoField, boolean dropFrame)
	{
		_time_base = timeBase;
		_frame_count = frameCount;
		_film_framing = filmFraming;
		_video_field = videoField;
		_dropframe = dropFrame;
	}
	
	public float getTimeBase()
	{
		return _time_base;
	}
	
	public int getFrameCount()
	{
		return _frame_count;
	}
	
	public char getFilmFraming()
	{
		return _film_framing;
	}
	
	public int getVideoField()
	{
		return _video_field;
	}
	
	public boolean getDropFrame()
	{
		return _dropframe;
	}
}

//
//  EditEventSearchContext.java
//  AES31
//
//  Created by David Ackerman on 4/3/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

package com.therockquarry.aes31.adl;

public class EditEntrySearchContext {
	
	protected int src_index;
	protected Range src_channel_range;
	protected Range dest_channel_range;
	protected TcfToken _src_in;
	protected TcfToken _dest_in;
	protected TcfToken _dest_out;
	
	public EditEntrySearchContext ()
	{
		_init();
	}
	
	public EditEntrySearchContext (int i, Range src, Range dest, TcfToken s, TcfToken d_i, TcfToken d_o)
	{
		src_index = i;
		src_channel_range = src;
		dest_channel_range = dest;
		_src_in = s;
		_dest_in = d_i;
		_dest_out = d_o;
	}
	
	private void _init ()
	{
		src_index = -1;
		src_channel_range = null;
		dest_channel_range = null;
		_src_in = null;
		_dest_in = null;
		_dest_out = null;
	}
	
	public void setSrcIndexTarget (int s)
	{
		src_index = s;
	}
	
	public void setSrcChannelRangeTarget (Range s)
	{
		src_channel_range = s;
	}
	
	public void setDestChannelRangeTarget (Range d)
	{
		dest_channel_range = d;
	}
	
	public void setSrcInTarget (TcfToken s)
	{
		_src_in = s;
	}
	
	public void setDestInTarget (TcfToken s)
	{
		_dest_in = s;
	}
	
	public void setDestOutTarget (TcfToken s)
	{
		_dest_out = s;
	}
	
}

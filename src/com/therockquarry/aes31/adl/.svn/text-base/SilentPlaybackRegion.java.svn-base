//
//  SilentPlaybackRegion.java
//  AES31
//
//  Created by David Ackerman on 12/10/06.
//  Copyright 2006 David Ackerman. All rights reserved.
//

package com.therockquarry.aes31.adl;

public class SilentPlaybackRegion extends PlaybackRegion
{
	protected BaseEditEntry.StatusType status;
	
	public SilentPlaybackRegion () throws InvalidDataException
	{
		super();
		setStatus(null);
	}
	
	public SilentPlaybackRegion (long di, long d) throws InvalidDataException
	{
		super(di, d);
		setStatus(null);
	}
	
	public SilentPlaybackRegion (long di, long d, BaseEditEntry.StatusType s) throws InvalidDataException
	{
		super(di, d);
		setStatus(s);
	}
	
	public void setStatus (BaseEditEntry.StatusType s)
	{
		status = s;
	}
	
	public BaseEditEntry.StatusType getStatus ()
	{
		return status;
	}
}

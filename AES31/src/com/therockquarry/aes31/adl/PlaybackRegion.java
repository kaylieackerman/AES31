//
//  AudioRegion.java
//  AES31
//
//  Created by David Ackerman on 12/10/06.
//  Copyright 2006 David Ackerman. All rights reserved.
//

package com.therockquarry.aes31.adl;

public abstract class PlaybackRegion
{
	protected long destIn;
	protected long destOut;
	
	public PlaybackRegion() throws InvalidDataException
	{
		setDestIn(0);
		setDestIn(0);
	}
	
	public PlaybackRegion(long di, long d) throws InvalidDataException
	{
		setDestIn(di);
		setDestIn(d);
	}
	
	public void setDestIn (long di) throws InvalidDataException
	{
		if (di > 0)
		{
			destIn = di;
		}
		else
		{
			throw new InvalidDataException("Dest in must be >= 0.");
		}
	}
	
	public long getDestIn ()
	{
		return destIn;
	}
	
	public void setDestOut (long d) throws InvalidDataException
	{
		if (d > 0)
		{
			destOut = d;
		}
		else
		{
			throw new InvalidDataException("Dest out must be >= 0.");
		}
	}
	
	public long getDestOut ()
	{
		return destOut;
	}

	
	public boolean validate () throws InvalidDataException
	{
		boolean rval = false;
		if (destIn < destOut)
		{
			rval = true;
		}
		
		return rval;
	}
	
}

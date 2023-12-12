/*
	-------------------------------------------------------------------------------
	EDMLParserException.java
	AES31-3

	Created by Kaylie Ackerman on 7/8/05.

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

public class EDMLParserException extends Exception
{
    public EDMLParserException ()
    {
	super ();
    }
	
    public EDMLParserException (String message)
    {
	super (message);
    }

}

/*
	-------------------------------------------------------------------------------
	SimpleDate.java
	AES31-3

	Created on 7/7/05.

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

import java.text.*;
import java.util.*;
import java.util.regex.*;

/**
*	The <code>SimpleDate</code> class provides a simple interface for handling ISO 8601 formatted date and time.
*	This class handles the parsing of a string representation of such a date, and the setting of the time-date through
*	an instance of the Date class. The many intricacies of the Calendar class are sacrificed here in return for a
*	straightforward and lightweight implementation.
*
*	@author David Ackerman
*/
public class SimpleDate implements Cloneable {
	private int _year;
	private int	_month;
	private int	_day;
	private int _hours;
	private int _minutes;
	private int _seconds;
	private int _utc_offset_hours;
	private int _utc_offset_minutes;
	private char _utc;
	
	/**
	*	Constructs a new empty <code>SimpleDate</code> object. Defaults to the current time and date. 
	*/
	public SimpleDate ()
	{
		_init();
		Date now = new Date();
		this.setTimeDate(now);
	}
	
	/**
	*	Constructs a new <code>SimpleDate</code> object from a String representation of an ISO 8601 formatted date and time.
	*
	*	@param timeDate A String representation of an ISO 8601 formatted date and time.
	*/
	public SimpleDate (String timeDate) throws InvalidDataException
	{
		this ();
		
		if (validateTimeDateString(timeDate))
		{
			_init ();
			_parse (timeDate);
		}
		else
		{
			throw new InvalidDataException("The String \"" + timeDate + "\" does not conform to the ISO 8601 format, or it contains out of range data.");
		}
		
	}
	
	/**
	*	Constructs a new <code>SimpleDate</code> object from a <code>Date</code> object.
	*
	*	@param timeDate A Date object containing the time and date to which this object should be set.
	*/
	public SimpleDate (Date timeDate)
	{
		_init ();
		setTimeDate(timeDate);
	}
	
	private void _init ()
	{
		_year = -1;
		_month = -1;
		_day = -1;
		_hours = -1;
		_minutes = -1;
		_seconds = -1;
		_utc_offset_hours = 0;
		_utc_offset_minutes = 0;
		_utc = ' ';
	}
	
	private void _parse (String s)
	{

		_year = Integer.parseInt(s.substring(0, 4));
		
		_month = Integer.parseInt(s.substring(5, 7));
		_day = Integer.parseInt(s.substring(8, 10));
		
		if (s.length() >= 19)
		{
			_hours = Integer.parseInt(s.substring(11, 13));
			_minutes = Integer.parseInt(s.substring(14, 16));
			_seconds = Integer.parseInt(s.substring(17, 19));
		}
		
		if (s.length() >= 20)
		{
			_utc = s.charAt(19);
			if (_utc == '+' || _utc == '-') {
				_utc_offset_hours = Integer.parseInt(s.substring(20, 22));
				_utc_offset_minutes = Integer.parseInt(s.substring(23, 25));
			}
		}
	}
	
	/**
	*	Sets this object to the time and date values of the <code>timeDate</code> Date object.
	*
	*	@param timeDate A Date object containing the time and date to which this object should be set.
	*/
	public void setTimeDate (Date timeDate)
	{
		_init();
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String result = sdf.format(timeDate);
		_parse(result);
	}
	
	/**
	*	Sets this object to the time and date values of the <code>timeDate</code> String.
	*
	*	@param timeDate A String representation of an ISO 8601 formatted date and time.
	*/
	public void setTimeDate (String timeDate) throws InvalidDataException
	{
		if (validateTimeDateString(timeDate))
		{
			_init ();
			_parse (timeDate);
		}
		else
		{
			throw new InvalidDataException("The String \"" + timeDate + "\" does not conform to the ISO 8601 format, or it contains out of range data.");
		}

	}
	
	/**
	*	Returns this object as an ISO 8601 formatted string.
	*
	*	@return A String representing this object in the ISO 8601 format.
	*/
	public String toString ()
	{
		DecimalFormat form4 = new DecimalFormat("0000");
		DecimalFormat form2 = new DecimalFormat("00");
		
		String returnVal = form4.format(_year);
		returnVal = returnVal.concat("-" + form2.format(_month));
		returnVal = returnVal.concat("-" + form2.format(_day));
		if (_hours != -1) {
			returnVal = returnVal.concat("T" + form2.format(_hours));
			returnVal = returnVal.concat(":" + form2.format(_minutes));
			returnVal = returnVal.concat(":" + form2.format(_seconds));
			if (_utc != ' ') {
				returnVal = returnVal.concat(String.valueOf(_utc));
				if (_utc != 'Z') {
					returnVal = returnVal.concat(form2.format(_utc_offset_hours));
					returnVal = returnVal.concat(":" + form2.format(_utc_offset_minutes));
				}
			}
		}
		
		return returnVal;
	}
	
	/**
	*	Validates a String object to confirm that it adhears to the formatting rules for a time and date for an AES-31-3 document (ISO 8601).
	*
	*	@param timeDate The String to validate for adhearance to ISO 8601 formatting rules.
	*	@return True if the String has a valid format and valid data, false otherwise.
	*/
	public boolean validateTimeDateString (String timeDate)
	{
		boolean rval = false;
		String myPattern = "(\\d{4})-([0][1-9]|[1][0-2])-([0][1-9]|[1-2][0-9]|[3][0-1])(T([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])([Z]|[+-]([0-1][0-9]|[2][0-3]):([0-5][0-9]))?)?";
		Pattern pattern = Pattern.compile(myPattern);
		Matcher matcher = pattern.matcher(timeDate);
		if (matcher.matches())
		{
			rval = true;
			
			int year = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int day = Integer.parseInt(matcher.group(3));

			switch (month)
			{
				case 4:
				case 6:
				case 9:
				case 11: if (day > 30) {
					rval = false;
				}
				break;
				case 2: GregorianCalendar cal = new GregorianCalendar();
					boolean leapYear = cal.isLeapYear(year);
					if ((leapYear)? day > 29 : day > 28) {
					rval = false;
					break;
				}
				default: if (day > 31) {
					rval = false;
					break;
				}
			}
		}
		
		
		return rval;
	}
	
	/**
	*	Returns the time and date of this object as a Java Date object.
	*
	*	@return The time and date contained in this SimpleDate object as a Java Date Object.
	*/
	public Date getAsDate ()
	{
		return new Date (_year - 1900, _month - 1, _day, _hours, _minutes, _seconds);
	}
	
	/*public GregorianCalendar getAsGregorianCalendar ()
	{
		GregorianCalendar rval = new GregorianCalendar(_year, (_month - 1), _day, (_hours == -1) ? 0 : _hours,
																			(_minutes == -1) ? 0 : _minutes,
																			(_seconds == -1) ? 0 : _seconds);

		int ms = (_utc_offset_hours * 3600000) + (_utc_offset_minutes * 60000);
		if (_utc == '-')
		{
			ms *= -1;
		}
		
		SimpleTimeZone tz = new SimpleTimeZone(ms, "here");
		rval.setTimeZone(tz);
		
		return rval;
	}*/
	
	public Object clone () throws CloneNotSupportedException
	{
		SimpleDate rval = (SimpleDate)super.clone();
		
		return rval;
	}
	
	
}

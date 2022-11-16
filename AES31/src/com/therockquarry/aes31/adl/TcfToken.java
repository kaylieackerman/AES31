/*
	-------------------------------------------------------------------------------
	TcfToken.java
	AES31-3

	Created on 7/13/05.

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
import java.math.*;
import java.util.*;
import java.util.regex.*;

public class TcfToken implements Cloneable, Comparable<TcfToken>
{
	
	private final static int FRAME_ERROR_PER_HOUR		= 108;
	private final static int MINUTES_PER_HOUR			=  60;
	private final static int SECONDS_PER_MINUTE			=  60;
	private final static int DECIMAL_SCALE				=  64;
	
	private int _hours;
	private int _minutes;
	private int _seconds;
	private int _frames;
	private int _sample_remainder;
	private int _frame_count;
	private float _time_base;
	private char _film_framing;
	private int _video_field;
	private double _sample_rate;
	private boolean _dropframe;
	private static Properties _sampleRateCharMap;
	private static Properties _sampleRateNameMap;
	private static Properties _frameCountMap;
	private static Properties _timeBaseMap;
	
	
	
	public TcfToken ()
	{
		_init ();
	}
	
	public TcfToken (long sampleCount, double sampleRate) 
	{
		this (sampleCount, sampleRate, new TcfTokenFormatProperties(1.0f, 30, ((char)0x00), 1, false));
	}
	
	public TcfToken (long sampleCount, BigDecimal sampleRate) 
	{
		this (sampleCount, sampleRate.doubleValue(), new TcfTokenFormatProperties(1.0f, 30, ((char)0x00), 1, false));
	}
	
	public TcfToken (String tokenString) throws InvalidDataException
	{
		_init ();
		
		if (validateTcfTokenString(tokenString))
		{
			_parse (tokenString);
		}
		else
		{
			throw new InvalidDataException("String \""+tokenString+"\" does not conform to Timecode Character Format specification");
		}
	}
	
	public TcfToken (long sampleCount, double sampleRate, TcfTokenFormatProperties prop)
	{
		this (sampleCount, prop.getFrameCount(), prop.getDropFrame(), prop.getTimeBase(), prop.getVideoField(), sampleRate);
	}
	
	public TcfToken (long sampleCount, BigDecimal sampleRate, TcfTokenFormatProperties prop)
	{
		this (sampleCount, prop.getFrameCount(), prop.getDropFrame(), prop.getTimeBase(), prop.getVideoField(), sampleRate.doubleValue());
	}
	
	public TcfToken (long sampleCount, int frameCount, boolean dropframe, float timeBase, int videoField, BigDecimal sampleRate)
	{
		this (sampleCount, frameCount, dropframe, timeBase, videoField, sampleRate.doubleValue());
	}
	
	public TcfToken (long sampleCount, int frameCount, boolean dropframe, float timeBase, int videoField, double sampleRate)
	{
		
		/*System.out.println("SAMPLE COUNT: " + sampleCount);
		System.out.println("FRAME COUNT: " + frameCount);
		System.out.println("DROP FRAME: " + dropframe);
		System.out.println("TIME BASE: " + timeBase);
		System.out.println("VIDEO FIELD: " + videoField);
		System.out.println("SAMPLE RATE: " + sampleRate);
		*/
		
		_init ();
		_hours = 0;
		_minutes = 0;
		_seconds = 0;
		_frames = 0;
		_sample_remainder = 0;
		_sample_rate = sampleRate;
		
		if (frameCount == 30 || frameCount == 25 || frameCount == 24)
		{
			_frame_count = frameCount;
		}
		else
		{
			//throw invalid data exception
		}
		
		_dropframe = dropframe;
		
		if (timeBase == 1 || timeBase == 1.001)
		{
			_time_base = timeBase;
		}
		else
		{
			//throw invalid data exception
		}
		
		if (videoField == 1 || videoField == 2)
		{
			_video_field = videoField;
		}
		else
		{
			//throw invalid data exception
		}
		
		if (_sampleRateToChar() == '0')
		{
			//throw invalid data exception
		}
		
		_film_framing = _frameCountAndTimebaseToChar();
		
		adjustTime (sampleCount);
		
	}
	
	private void _init ()
	{
		_hours = -1;
		_minutes = -1;
		_seconds = -1;
		_frames = -1;
		_sample_remainder = -1;
		_frame_count = -1;
		_time_base = -1.0f;
		_film_framing = 0x00;
		_video_field = -1;
		_sample_rate = -1;
		_dropframe = false;
		
		/* Setup table data */
		_sampleRateCharMap = new Properties ();
		_sampleRateCharMap.setProperty("/", "48000.0");
		_sampleRateCharMap.setProperty("-", "47952.047952047952048");
		_sampleRateCharMap.setProperty("+", "48048.0");
		_sampleRateCharMap.setProperty("<", "46080");
		_sampleRateCharMap.setProperty(">", "50000.0");
		_sampleRateCharMap.setProperty("|", "44100.0");
		_sampleRateCharMap.setProperty("~", "44055.944055944055944");
		_sampleRateCharMap.setProperty("^", "44144.1");
		_sampleRateCharMap.setProperty("[", "42336");
		_sampleRateCharMap.setProperty("]", "45937.5");
		_sampleRateCharMap.setProperty("=", "32000.0");
		_sampleRateCharMap.setProperty("'", "31968.031968031968032");
		_sampleRateCharMap.setProperty("\"", "32032.0");
		_sampleRateCharMap.setProperty("(", "30720.0");
		_sampleRateCharMap.setProperty(")", "33333.333333333333333");
		_sampleRateCharMap.setProperty("*", "96000");
		_sampleRateCharMap.setProperty("&", "95904.095904095904096");
		_sampleRateCharMap.setProperty("#", "96096.0");
		_sampleRateCharMap.setProperty("{", "92160.0");
		_sampleRateCharMap.setProperty("}", "100000.0");
		_sampleRateCharMap.setProperty("@", "88200.0");
		_sampleRateCharMap.setProperty("?", "88111.888111888111888");
		_sampleRateCharMap.setProperty("$", "88288.2");
		_sampleRateCharMap.setProperty("`", "84672.0");
		_sampleRateCharMap.setProperty("!", "91875.0");
		
		_sampleRateNameMap = new Properties ();
		_sampleRateNameMap.setProperty("S48000", "48000.0");
		_sampleRateNameMap.setProperty("S47952", "47952.047952047952048");
		_sampleRateNameMap.setProperty("S48048", "48048.0");
		_sampleRateNameMap.setProperty("S46080", "46080");
		_sampleRateNameMap.setProperty("S50000", "50000.0");
		_sampleRateNameMap.setProperty("S44100", "44100.0");
		_sampleRateNameMap.setProperty("S44055", "44055.944055944055944");
		_sampleRateNameMap.setProperty("S44144", "44144.1");
		_sampleRateNameMap.setProperty("S42336", "42336");
		_sampleRateNameMap.setProperty("S45937", "45937.5");
		_sampleRateNameMap.setProperty("S32000", "32000.0");
		_sampleRateNameMap.setProperty("S31968", "31968.031968031968032");
		_sampleRateNameMap.setProperty("S32032", "32032.0");
		_sampleRateNameMap.setProperty("S30720", "30720.0");
		_sampleRateNameMap.setProperty("S33333", "33333.333333333333333");
		_sampleRateNameMap.setProperty("S96000", "96000.0");
		_sampleRateNameMap.setProperty("S95904", "95904.095904095904096");
		_sampleRateNameMap.setProperty("S96096", "96096.0");
		_sampleRateNameMap.setProperty("S92160", "92160.0");
		_sampleRateNameMap.setProperty("S100000", "100000.0");
		_sampleRateNameMap.setProperty("S88200", "88200.0");
		_sampleRateNameMap.setProperty("S88111", "88111.888111888111888");
		_sampleRateNameMap.setProperty("S88288", "88288.2");
		_sampleRateNameMap.setProperty("S84672", "84672.0");
		_sampleRateNameMap.setProperty("S91875", "91875.0");
		
		_frameCountMap = new Properties ();
		_frameCountMap.setProperty("?", "30");
		_frameCountMap.setProperty("|", "30");
		_frameCountMap.setProperty(":", "30");
		_frameCountMap.setProperty("!", "25");
		_frameCountMap.setProperty(".", "25");
		_frameCountMap.setProperty("/", "25");
		_frameCountMap.setProperty("#", "24");
		_frameCountMap.setProperty("=", "24");
		_frameCountMap.setProperty("-", "24");
		
		_timeBaseMap = new Properties ();
		_timeBaseMap.setProperty("?", "0.0");
		_timeBaseMap.setProperty("|", "1.0");
		_timeBaseMap.setProperty(":", "1.001");
		_timeBaseMap.setProperty("!", "0.0");
		_timeBaseMap.setProperty(".", "1.0");
		_timeBaseMap.setProperty("/", "1.001");
		_timeBaseMap.setProperty("#", "0.0");
		_timeBaseMap.setProperty("=", "1.0");
		_timeBaseMap.setProperty("-", "1.001");
	}
	
	private void _parse (String s)
	{
		if (s.length() > 1)
		{
			_hours = Integer.parseInt(s.substring(0, 2));
			if (_validate_hours() != 0)
			{
				System.out.println("INVALID HOUR VALUE: " + _hours);
			}
			_frameCountIndicatorToInt(s.charAt(2));
			_timebaseIndicatorToFloat(s.charAt(2));

			_minutes = Integer.parseInt(s.substring(3, 5));
			if (_validate_minutes() != 0)
			{
				System.out.println("INVALID MINUTES VALUE: " + _minutes);
			}
			_film_framing = s.charAt(5);
			_seconds = Integer.parseInt(s.substring(6, 8));
			if (_validate_seconds() != 0)
			{
				System.out.println("INVALID SECONDS VALUE: " + _seconds);
			}
			_videoFieldIndicatorToInt(s.charAt(8));
			_frames = Integer.parseInt(s.substring(9, 11));
			if (_validate_frames() != 0)
			{
				System.out.println("INVALID FRAMES VALUE: " + _frames);
			}
			if (s.length() > 11)
			{
				_sampleRateIndicatorToBigDouble(s.charAt(11));
				_sample_remainder = Integer.parseInt(s.substring(12, 16));
				if (_validate_sample_remainder() != 0)
				{
					System.out.println("INVALID SAMPLE REMAINDER VALUE: " + _sample_remainder);
				}
			}
			
		}
	}
	
	//proof of concept
	public void adjustTime (long samples)
	{
		long _sample_count = 0;
		_sample_count += _sample_remainder;
		_sample_count += ((_sample_rate / (_frame_count)) * _frames);
		_sample_count += (_seconds * _frame_count * (_sample_rate/_frame_count));
		_sample_count += (_minutes * 60 * _frame_count * (_sample_rate/_frame_count));
		_sample_count += (_hours * 60 * 60 * _frame_count * (_sample_rate/_frame_count));
		_sample_count += samples;
		
		/*double tmp = ((_sample_count/_sample_rate)/60)/60;
		_hours = (int)tmp;
		tmp -= _hours;
		tmp *= 60;
		_minutes = (int)tmp;
		tmp -= _minutes;
		tmp *= 60;
		_seconds = (int)tmp;
		tmp -= _seconds;
		tmp *= _frame_count;
		_frames = (int)tmp;
		tmp -= _frames;
		tmp *= (_sample_rate/_frame_count);
		_sample_remainder = (int)tmp;
		tmp -= _sample_remainder;
		if (tmp >= 0.5)
		{
			_sample_remainder++;
		}*/
		
		//System.out.println("SAMPLE COUNT: " + _sample_count);
		
		long sample_in_1_frame = (long)(_sample_rate/_frame_count);
		long sample_in_1_second = sample_in_1_frame * _frame_count;
		long sample_in_1_minute = sample_in_1_frame * _frame_count * 60;
		long sample_in_1_hour = sample_in_1_frame * _frame_count * 60 * 60;
		
		/*System.out.println("SAMPLE RATE: " + _sample_rate);
		System.out.println("FRAME COUNT: " + _frame_count);
		System.out.println("SAMPLE in ! FR: " + sample_in_1_frame);
		System.out.println("SAMPLE in ! HR: " + sample_in_1_hour);
		*/
		
		_hours = (int)(_sample_count / sample_in_1_hour);
		_sample_count -= (_hours * sample_in_1_hour);
		_minutes = (int)(_sample_count / sample_in_1_minute);
		_sample_count -= (_minutes * sample_in_1_minute);
		_seconds = (int)(_sample_count / sample_in_1_second);
		_sample_count -= (_seconds * sample_in_1_second);
		_frames = (int)(_sample_count / sample_in_1_frame);
		_sample_count -= (_frames * sample_in_1_frame);
		_sample_remainder = (int)_sample_count;
		
		_hours = _hours % 24;	//roll over midnight

		
		
	}
	
	public String toString ()
	{
		DecimalFormat form2 = new DecimalFormat("00");
		DecimalFormat form4 = new DecimalFormat("0000");
		String rval;
		
		if (_hours != -1 && _minutes != -1 &&  _seconds != -1 && _frames != -1)
			{
			rval = form2.format(_hours);
			rval = rval.concat(String.valueOf(_frameCountAndTimebaseToChar()));
			rval = rval.concat(form2.format(_minutes));
			if (_film_framing == 0x00)
			{
				_film_framing = _frameCountAndTimebaseToChar();
			}
			rval = rval.concat(String.valueOf(_film_framing));
			rval = rval.concat(form2.format(_seconds));
			rval = rval.concat(String.valueOf(_videoFieldToChar()));
			rval = rval.concat(form2.format(_frames));
			if (_sample_rate != -1)
			{
				rval = rval.concat(String.valueOf(_sampleRateToChar()));
				rval = rval.concat(form4.format(_sample_remainder));
			}
		}
		else
		{
			rval = "_";
		}
		
		return rval;
	}
	
	
	
	private void _frameCountIndicatorToInt (char c)
	{
		_frame_count = Integer.parseInt(_frameCountMap.getProperty(String.valueOf(c)));
		
	}
	
	private char _frameCountAndTimebaseToChar ()
	{
		char rval = '0';
		
		if (_frame_count == 30)
		{
			if (_time_base == 0)
			{
				rval = '?';
			}
			else if (_time_base == 1.0)
			{
				rval = '|';
			}
			else if (_time_base == 1.001f)
			{
				rval = ':';
			}
		} else if (_frame_count == 25)
		{
			if (_time_base == 0)
			{
				rval = '!';
			}
			else if (_time_base == 1.0)
			{
				rval = '.';
			}
			else if (_time_base == 1.001f)
			{
				rval = '/';
			}
		}
		else if (_frame_count == 24)
		{
			if (_time_base == 0)
			{
				rval = '#';
			}
			else if (_time_base == 1.0)
			{
				rval = '=';
			}
			else if (_time_base == 1.001f)
			{
				rval = '-';
			}
		}

		return rval;
	}
	
	private void _timebaseIndicatorToFloat (char c)
	{		
		_time_base = Float.parseFloat(_timeBaseMap.getProperty(String.valueOf(c)));
		
	}
	
	private void _videoFieldIndicatorToInt (char c)
	{
		_dropframe = false;
		
		if (c == '.' || c == ',')
		{
			_video_field = 1;
		}
		else if (c == ':' || c == ';')
		{
			_video_field = 2;
		}
		
		if (c == ',' || c == ';')
		{
			_dropframe = true;
		}
		
	}
	
	private char _videoFieldToChar ()
	{
		char rval = '0';
		
		if (_video_field == 1)
		{
			rval = (_dropframe) ? ',' : '.';
		}
		else if (_video_field == 2)
		{
			rval = (_dropframe) ? ';' : ':';
		}
		
		return rval;
	}
	
	private void _sampleRateIndicatorToBigDouble (char c)
	{
		_sample_rate = Double.parseDouble(_sampleRateCharMap.getProperty(String.valueOf(c)));
	}
	
	private char _sampleRateToChar ()
	{
		char rval = '0';
		String s = null;
		
		Enumeration e = _sampleRateCharMap.propertyNames ();
		while (e.hasMoreElements())
		{
			s = (String)e.nextElement();
			if (Double.parseDouble(_sampleRateCharMap.getProperty(s)) == _sample_rate)
			{
				rval = (char)s.charAt(0);
				break;
			}
		}
		return rval;
	}
	
	public int getHours ()
	{
		return _hours;
	}
	
	public int getMinutes ()
	{
		return _minutes;
	}
	
	public int getSeconds ()
	{
		return _seconds;
	}
	
	public int getFrames ()
	{
		return _frames;
	}
	
	public int getSampleRemainder ()
	{
		return _sample_remainder;
	}
	
	public int getFrameCount ()
	{
		return _frame_count;
	}

	public float getTimeBase ()
	{
		return _time_base;
	}

	public double getSampleRate ()
	{
		return _sample_rate;
	}
	
	public boolean getDropFrame ()
	{
		return _dropframe;
	}
	
	public int getVideoField ()
	{
		return _video_field;
	}
	
	public char getFilmFraming ()
	{
		return _film_framing;
	}
	
	public long valueOf ()
	{
		long _sample_count = 0;
		_sample_count += _sample_remainder;
		_sample_count += ((_sample_rate / (_frame_count)) * _frames);
		_sample_count += (_seconds * _frame_count * (_sample_rate/_frame_count));
		_sample_count += (_minutes * 60 * _frame_count * (_sample_rate/_frame_count));
		_sample_count += (_hours * 60 * 60 * _frame_count * (_sample_rate/_frame_count));
		return _sample_count;
	}
	
	/*public BigDecimal valueOf ()
	{
		BigDecimal val = new BigDecimal(0.0);
		val.setScale(DECIMAL_SCALE, BigDecimal.ROUND_UNNECESSARY);
		
		val = val.add(new BigDecimal(_hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE));
		val = val.add(new BigDecimal(_minutes * SECONDS_PER_MINUTE));
		val = val.add(new BigDecimal(_seconds));
		val = val.add(new BigDecimal(_frames * (1.0 / _frame_count)));
		if (_sample_remainder > 0) {
			val = val.add(new BigDecimal(_sample_remainder * 1.0 / _sample_rate));
		}*/
		
		/* counted in dropframe but not ntsc time */
		/*if (_dropframe && _time_base < 1.001) {
			int minutesToDrop = ((_hours * MINUTES_PER_HOUR) + _minutes);
			//int framesToDrop = ((minutesToDrop - (minutesToDrop / 10)) * 2);
			double framesDropped = minutesToDrop * 1.8; // dropframe causes 1.8 frames to not be counted per minute
			val = val.add(new BigDecimal(framesDropped * 1.0 / _frame_count));
		} */
		/* ntsc time but dropframe not applied */
		/*else if (_time_base == 1.001 && !_dropframe)
		{
			int minutesToDrop = ((_hours * MINUTES_PER_HOUR) + _minutes);
			int framesToDrop = ((minutesToDrop - (minutesToDrop / 10)) * 2);
			val = val.subtract(new BigDecimal(framesToDrop * 1.0 / _frame_count));
		}
		
		val = val.multiply(new BigDecimal(_time_base));
		
		return val;
	}*/
	
	private int _validate_hours ()
	{
		int rval;
		
		if (_hours < 0) {
			rval = -1;
		}
		else if (_hours > 24)
		{
			rval = 1;
		}
		else
		{
			rval = 0;
		}
		return rval;
	}
	
	private int _validate_minutes ()
	{
		int rval;
		
		if (_minutes < 0) {
			rval = -1;
		}
		else if (_minutes > 59)
		{
			rval = 1;
		}
		else
		{
			rval = 0;
		}
		
		return rval;
	}
	
	private int _validate_seconds ()
	{
		int rval;
		
		if (_seconds < 0) {
			rval = -1;
		}
		else if (_seconds > 59)
		{
			rval = 1;
		}
		else
		{
			rval = 0;
		}
		
		return rval;
	}
	
	private int _validate_frames ()
	{
		int rval;
		
		if (_frames < 0) {
			rval = -1;
		}
		else if (_frames >= _frame_count)
		{
			rval = 1;
		}
		else
		{
			rval = 0;
		}
		
		return rval;
	}
	
	private int _validate_sample_remainder ()
	{
		int rval;
		
		if (_sample_remainder < 0) {
			rval = -1;
		}
		else if (_sample_remainder > (_sample_rate/_frame_count))
		{
			rval = 1;
		}
		else
		{
			rval = 0; /* in range */
		}
		return rval;
	}
	
	
	
	/**
	*	Validates a String object to confirm that it adhears to the formatting rules for a Timecode character Format(TCF) field for an AES-31-3 document.
	*
	*	@param token The String to validate for adhearance to AES-31-3 TCF Field formatting rules.
	*	@return True if the String has a valid format, false otherwise.
	*/
	public static boolean validateTcfTokenString (String token)
	{
		boolean rval = false;
		String myPattern = "\\d{2}(.)\\d{2}(.)\\d{2}(.)\\d{2}((.)\\d{4})?";
		Pattern pattern = Pattern.compile(myPattern);
		Pattern pattern2 = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(token);
		if (matcher.matches())
		{
			rval = true;
			if (_frameCountMap.getProperty(matcher.group(1)) == null)
			{
				rval = false;
			}
			
			Matcher matcher2 = pattern2.matcher(matcher.group(2));
			if (matcher2.matches())
			{
				rval = true;
			}
			else if (_frameCountMap.getProperty(matcher.group(2)) == null)
			{
				rval = false;
			}
			
			if (!(matcher.group(3).equals(".") || matcher.group(3).equals(",") || matcher.group(3).equals(":") || matcher.group(3).equals(";")))
			{
				rval = false;
			}
			
			if (_sampleRateCharMap.getProperty(matcher.group(5)) == null)
			{
				rval = false;
			}
			
		}
		
		//System.out.println("VALIDATING: "+token+" == "+rval);
		
		return rval;
	}
	
	/**
	*	Validates a String object to confirm that it appears in column 1 of table 6 Audio sampling frequencies, 
	*	indicator coding, and derivations of the AES-31-3 specification.
	*
	*	@param sampleRateName The String to confirm that it appears in column 1 of table 6 Audio sampling frequencies, 
	*	indicator coding, and derivations of the AES-31-3 specification.
	*	@return True if the String has a valid format, false otherwise.
	*/
	public static boolean ValidateSampleRateName (String sampleRateName)
	{
		boolean rval = true;
		
		if (_sampleRateNameMap.getProperty(sampleRateName) == null)
		{
			rval = false;
		}
		
		return rval;
	}
	
	public static boolean ValidateSampleRateValue (double sampleRate)
	{
		boolean rval = false;
		double test = 0;
		
		Enumeration keys = _sampleRateNameMap.propertyNames();
		
		while (keys.hasMoreElements())
		{
			String key = (String)keys.nextElement();
			test = Double.parseDouble(_sampleRateNameMap.getProperty(key));
			if (test == sampleRate)
			{
				rval = true;
				break;
			}
		}
		
		return rval;
	}
	
	
	
	/**
	*	Returns a BigDecimal containing the sample rate value for the <code>sampleRateName</code> parameter. 
	*
	*	@param sampleRateName The name of s Sample Rate as it appears in column 1 of table 6 - Audio sampling frequencies, 
	*	indicator coding, and derivations of the AES-31-3 specification.
	*	@return A BigDecimal containing the sample rate value for the <code>sampleRateName</code> parameter. 
	*/
	public static BigDecimal getSampleRateValueForName (String sampleRateName)
	{
		BigDecimal rval = new BigDecimal(_sampleRateNameMap.getProperty(sampleRateName));
		
		return rval;
	}
	
	public TcfTokenFormatProperties getTcfTokenFormatProperties()
	{
		TcfTokenFormatProperties rval = new TcfTokenFormatProperties(_time_base, _frame_count, _film_framing, _video_field, _dropframe);
		
		return rval;
	}
	
	public void resample (double newSampleRate)
	{
		double fact = (double)(_sample_remainder/(_sample_rate/_frame_count));
		_sample_remainder = (int)(fact * (newSampleRate/_frame_count));
		_sample_rate = newSampleRate;
	}
	
	public void resample (BigDecimal newSampleRate)
	{
		resample (newSampleRate.doubleValue());
	}
	
	public static String getSampleRateRepresentation (double sr)
	{
		String rval = null;
		//String testVal = new Double(sr).toString();
		
		Enumeration keys = _sampleRateNameMap.propertyNames();
		String tst = null;
		
		while (keys.hasMoreElements())
		{
			tst = (String)keys.nextElement();
			double testVal = new Double(_sampleRateNameMap.getProperty(tst)).doubleValue();
			
			if (testVal == sr)
			{
				rval = tst;
				break;
			}
		}
		
		return rval;
	}
	
	public int compareTo (TcfToken o)
	{
		if (this.equals(o))
		{
			return 0;
		}
		
		long me = this.valueOf();
		long other = o.valueOf();
		
		if (me < other)
		{
			return -1;
		}
		else if (me > other)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		TcfToken rval = (TcfToken)super.clone();
		
		return rval;
	}
	
	
}

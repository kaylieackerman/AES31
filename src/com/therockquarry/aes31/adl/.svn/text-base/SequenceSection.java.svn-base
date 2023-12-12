/*
	-------------------------------------------------------------------------------
	SequenceSection.java
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
*/

package com.therockquarry.aes31.adl;

import java.math.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>SequenceSection</code> object contains global information about the ADL contents and status. The ADL specification is available from the Audio Engineering Society, 
*	<a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class SequenceSection extends BaseSection implements Cloneable {
	
	private String		_seq_title;					/* sequence title */
	private String		_seq_descript;				/* sequence description */
	private String		_seq_sample_rate;			/* time-line audio sampling frequency name from Table 6 column 1 */
	private int			_seq_sample_rate_factor;	/* time-line audio sampling frequency multiplier, version 2 only (Valid values are 2, 4 or 1(default) */
	private int			_seq_frame_rate;			/* Time-line video sampling frequency name (30, 25 or 24) */
	private int			_seq_adl_level;				/* 1 = ADL level 1 (default) */
	private boolean		_seq_clean;					/* FALSE = ADL may have overlapping events */
	private int			_seq_sort;					/* sort condition: 0 = ascending entry number (default) */
	private boolean		_seq_multichan;				/* FALSE = all events single channel (default)
														TRUE = one or more events contain multiple audio channels */
	private TcfToken	_seq_dest_start;			/* Originating system time-line destination start, a TCF that also 
														encodes the originating system resolve speed, frame and sampling 
														rates, and time-code type */
	
	/**
	*	Initializes a new empty <code>SequenceSection</code> object which contains global information about the ADL contents and status. 
	*
	*/
	public SequenceSection ()
	{
		super ("SEQUENCE");
		_init ();
	}
	
	private void _init ()
	{
		_seq_title = null;
		_seq_descript = null;
		_seq_sample_rate = null;
		_seq_sample_rate_factor = -1;
		_seq_frame_rate = -1;
		_seq_adl_level = 1;
		_seq_clean = false; //default value
		_seq_sort = 0;
		_seq_multichan = false;	//spec's default value
		_seq_dest_start = null;
	}
	
	/**
	*	Sets the optional Sequence Title field in this <code>SequenceSection</code> object.
	*
	*	@param	seqTitle A String containing the Sequence Title for this ADL.
	*/
	public void setSeqTitle (String seqTitle) throws InvalidDataException
	{
		if (seqTitle.equals("_"))
		{
			_seq_title = null;
			return;
		}
		if (EDMLParser.validateADLString(seqTitle))
		{
			_seq_title = seqTitle;
		}
		else
		{
			throw new InvalidDataException("\"" + seqTitle + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the Sequence Title for this <code>SequenceSection</code> object.
	*
	*	@return A String containing the Sequence Title for this <code>SequenceSection</code> object.
	*/
	public String getSeqTitle ()
	{
		return _seq_title;
	}
	
	/**
	*	Sets the optional Sequence Description field in this <code>SequenceSection</code> object.
	*
	*	@param	seqDescription A String containing the Sequence Description for this ADL.
	*/
	public void setSeqDescript (String seqDescription) throws InvalidDataException
	{
		if (seqDescription.equals("_"))
		{
			_seq_descript = null;
			return;
		}
		if (EDMLParser.validateADLString(seqDescription))
		{
			_seq_descript = seqDescription;
		}
		else
		{
			throw new InvalidDataException("\"" + seqDescription + "\" is not valid. Within an ADL Strings must be 255 or less characters in length.");
		}
	}
	
	/**
	*	Returns the Sequence Description for this <code>SequenceSection</code> object.
	*
	*	@return A String containing the Sequence Description for this <code>SequenceSection</code> object.
	*/
	public String getSeqDescript ()
	{
		return _seq_descript;
	}
	
	/**
	*	Sets the required Sequence Sample Rate field in this <code>SequenceSection</code> object.
	*
	*	@param	sampleRate A String containing the Sequence Sample Rate value for this ADL. The String must appear in 
	*	Column 1 of Table 6 - Audio sampling frequencies, indicator coding, and derivations of the AES-31-3 standard to be valid.
	*	Note that the Letter "S" is prepended to the integer representation of the sample rate, for example "S96000".
	*/
	public void setSeqSampleRate (String sampleRate) throws InvalidDataException
	{
		if (TcfToken.ValidateSampleRateName(sampleRate))
		{
			_seq_sample_rate = sampleRate;
		}
		else
		{
			throw new InvalidDataException("\"" + sampleRate + "\" is not valid. The value must be present in Column 1 of Table 6 - Audio sampling frequencies, indicator coding, and derivations of the AES-31-3 standard."); 
		}
	}
	
	/**
	*	Sets the required Sequence Sample Rate field in this <code>SequenceSection</code> object.
	*
	*	@param	sampleRate An double containing the Sequence Sample Rate value for this ADL. The value must appear in 
	*	Column 1 of Table 6 - Audio sampling frequencies, indicator coding, and derivations of the AES-31-3 standard to be valid.
	*	Note that in the table, the Letter "S" is prepended to the integer representation of the sample rate, for example "S96000".
	*	This method will handle the prepending of the value "S" to the int parameter passed in.
	*/
	public void setSeqSampleRate (double sampleRate) throws InvalidDataException
	{
		String rate = "S"+Double.toString(sampleRate);
		if (TcfToken.ValidateSampleRateName(rate))
		{
			_seq_sample_rate = rate;
		}
		else
		{
			throw new InvalidDataException("\"" + rate + "\" is not valid. The value must be present in Column 1 of Table 6 - Audio sampling frequencies, indicator coding, and derivations of the AES-31-3 standard."); 
		}
	}
	
	/**
	*	Returns the Sequence Sample Rate field for this <code>SequenceSection</code> object.
	*
	*	@return A String containing the Sequence Sample Rate for this <code>SequenceSection</code> object.
	*/
	public String getSeqSampleRate ()
	{
		return _seq_sample_rate;
	}
	
	/**
	*	Returns the Sequence Sample Rate field for this <code>SequenceSection</code> object.
	*
	*	@return An int containing the Sequence Sample Rate for this <code>SequenceSection</code> object.
	*/
	public double getSeqSampleRateAsDouble ()
	{
		BigDecimal rval = TcfToken.getSampleRateValueForName(_seq_sample_rate);
		return rval.doubleValue();
	}
	
	/**
	*	Returns the Sequence Sample Rate field for this <code>SequenceSection</code> object.
	*
	*	@return A BigDecimal containing the Sequence Sample Rate for this <code>SequenceSection</code> object. Although the 
	*	Sequence Sample Rate field can only except the value in integer precision, this method will return the full precision
	*	specified in column 5  of Table 6 - Audio sampling frequencies, indicator coding, and derivations of the AES-31-3 standard.
	*/
	public BigDecimal getSeqSampleRateAsDecimal ()
	{
		BigDecimal rval = TcfToken.getSampleRateValueForName(_seq_sample_rate);
		return rval;
	}
	
	/**
	*	Sets the optional Sequence Sample Rate Factor field in this <code>SequenceSection</code> object. Note that this field was 
	*	added for EDML version 2 and should not be present in an ADL marked as major version 1. Therefore an exception will be 
	*	raised if the EDML version is not 2 or greater when adding this field to the document.
	*
	*	@param	seqSampleRateMultiplier A String containing the Sequence Sample Rate Factor value for this ADL. Must resolve to a value of 4, 2, or 1.
	*/
	public void setSeqSampleRateFactor (String seqSampleRateMultiplier) throws DocumentVersionMismatchException, InvalidDataException
	{
		ADLSection adl = (ADLSection)this.getParent();
		VersionSection vs = adl.getVersionSection();
		EDMLVersion docVersion = vs.getVerAdlVersion();
		
		int tmp = -1;
		
		if (docVersion.getMinorRelease() < 2)
		{
			throw new DocumentVersionMismatchException("Seq Sample Rate Factor added to an EDML document that does not support this field! EDML version 2 or greater required.");
		}
		
		try
		{
			tmp = Integer.parseInt(seqSampleRateMultiplier);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("System Sample Rate Factor must be an integer.", e);
		}
		
		if (validateSampleRateFactor(tmp))
		{
			_seq_sample_rate_factor = tmp;
		}
		else
		{
			throw new InvalidDataException("System Sample Rate Factor must be an integer with a value of 1, 2 or 4.");
		}
		
	}
	
	/**
	*	Sets the optional Sequence Sample Rate Factor field in this <code>SequenceSection</code> object. Note that this field was 
	*	added for EDML version 2 and should not be present in an ADL marked as major version 1. Therefore an exception will be 
	*	raised if the EDML version is not 2 or greater when adding this field to the document.
	*
	*	@param	seqSampleRateMultiplier An int containing the Sequence Sample Rate Factor value for this ADL. Must resolve to a value of 4, 2, or 1.
	*/
	public void setSeqSampleRateFactor (int seqSampleRateMultiplier) throws DocumentVersionMismatchException, InvalidDataException
	{
		ADLSection adl = (ADLSection)this.getParent();
		VersionSection vs = adl.getVersionSection();
		EDMLVersion docVersion = vs.getVerAdlVersion();
		
		int tmp;
		
		if (docVersion.getMinorRelease() < 2)
		{
			throw new DocumentVersionMismatchException("Seq Sample Rate Factor added to an EDML document that does not support this field! EDML version 2 or greater required.");
		}
		
		tmp = seqSampleRateMultiplier;
		
		if (validateSampleRateFactor(tmp))
		{
			_seq_sample_rate_factor = tmp;
		}
		else
		{
			throw new InvalidDataException("System Sample Rate Factor must be an integer with a value of 1, 2 or 4.");
		}
		
	}
	
	/**
	*	Returns the Sequence Sample Rate Factor field for this <code>SequenceSection</code> object. Note this optional field is
	*	only supported by EDML version 2 and above. If the field does not exist in the ADL either due to versioning or because it is optional,
	*	the default value of 1 will be returned.
	*
	*	@return An int containing the Sequence Sample Rate Factor for this <code>SequenceSection</code> object. 
	*/
	public int getSeqSampleRateFactor ()
	{
		int rval = _seq_sample_rate_factor;
		
		ADLSection adl = (ADLSection)this.getParent();
		VersionSection vs = adl.getVersionSection();
		EDMLVersion docVersion = vs.getVerAdlVersion();
		
		if (docVersion.getMinorRelease() < 2)
		{
			rval = 1;
		}
		else if (rval == -1)
		{
			rval = 1;	//use default multiplier if field is unset. probably means that the document version does not support this field and assumes a value of 1
		}
		
		return rval;
	}
	
	/**
	*	Sets the required Sequence Frame Rate field in this <code>SequenceSection</code> object.
	*
	*	@param	seqFrameRate A String containing the Sequence Frame Rate value for this ADL. Must resolve to a value of 30, 25, or 24.
	*/
	public void setSeqFrameRate (String seqFrameRate) throws InvalidDataException
	{
		int tmp = -1;
		
		try
		{
			tmp = Integer.parseInt(seqFrameRate);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Sequence Frame Rate must be an integer.", e);
		}
		
		if (validateFrameRate(tmp))
		{
			_seq_frame_rate = tmp;
		}
		else
		{
			throw new InvalidDataException("Seq Frame Rate must be an integer with a value of 30, 25 or 24.");
		}
	}
	
	/**
	*	Sets the required Sequence Frame Rate field in this <code>SequenceSection</code> object.
	*
	*	@param	seqFrameRate An int containing the Sequence Frame Rate value for this ADL. Must resolve to a value of 30, 25, or 24.
	*/
	public void setSeqFrameRate (int seqFrameRate) throws InvalidDataException
	{
		int tmp = seqFrameRate;
		
		if (validateFrameRate(tmp))
		{
			_seq_frame_rate = tmp;
		}
		else
		{
			throw new InvalidDataException("Seq Frame Rate must be an integer with a value of 30, 25 or 24.");
		}
	}
	
	/**
	*	Returns the Sequence Frame Rate field in this <code>SequenceSection</code> object.
	*
	*	@return	 An int containing the Sequence Frame Rate value for this ADL.
	*/
	public int getSeqFrameRate ()
	{
		return _seq_frame_rate;
	}
	
	/**
	*	Sets the required Sequence ADL Level field in this <code>SequenceSection</code> object.
	*
	*	@param	seqAdlLevel A String containing the Sequence ADL Level value for this ADL.
	*/
	public void setSeqAdlLevel (String seqAdlLevel) throws InvalidDataException
	{	
		int tmp = -1;
		
		try
		{
			tmp = Integer.parseInt(seqAdlLevel);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Sequence ADL Level must be an integer.", e);
		}
		
		if (validateAdlLevel(tmp))
		{
			_seq_adl_level = tmp;
		}
		else
		{
			throw new InvalidDataException("Sequence ADL Level must be an integer greater than or equal to 1.");
		}
	}
	
	/**
	*	Sets the required Sequence ADL Level field in this <code>SequenceSection</code> object.
	*
	*	@param	seqAdlLevel An int containing the Sequence ADL Level value for this ADL.
	*/
	public void setSeqAdlLevel (int seqAdlLevel) throws InvalidDataException
	{			
		if (validateAdlLevel(seqAdlLevel))
		{
			_seq_adl_level = seqAdlLevel;
		}
		else
		{
			throw new InvalidDataException("Sequence ADL Level must be an integer greater than or equal to 1.");
		}
	}
	
	/**
	*	Returns the Sequence ADL Level field in this <code>SequenceSection</code> object.
	*
	*	@return	 An int containing the Sequence ADL Level value for this ADL.
	*/
	public int getSeqAdlLevel ()
	{
		return _seq_adl_level;
	}
	
	/**
	*	Sets the required Sequence Clean field in this <code>SequenceSection</code> object.
	*
	*	@param	seqClean A String containing the Sequence Clean value for this ADL. False indicates that the ADL may have overlapping events.
	*/
	public void setSeqClean (String seqClean) throws InvalidDataException
	{
		if (EDMLParser.validateADLBoolean(seqClean))
		{
			_seq_clean = seqClean.equalsIgnoreCase("TRUE");
		}
		else
		{
			throw new InvalidDataException("Sequence Clean must have a boolean value of TRUE or FALSE.");
		}
	}
	
	/**
	*	Sets the required Sequence Clean field in this <code>SequenceSection</code> object.
	*
	*	@param	seqClean A boolean containing the Sequence Clean value for this ADL. False indicates that the ADL may have overlapping events.
	*/
	public void setSeqClean (boolean seqClean)
	{
		_seq_clean = seqClean;
	}
	
	/**
	*	Returns the Sequence Clean field in this <code>SequenceSection</code> object.
	*
	*	@return	 A boolean containing the Sequence ADL Level value for this ADL.
	*/
	public boolean getSeqClean ()
	{
		return _seq_clean;
	}
	
	/**
	*	Sets the required Sequence Sort field in this <code>SequenceSection</code> object.
	*
	*	@param	seqSort A String containing the Sequence Sort value for this ADL.
	*/
	public void setSeqSort (String seqSort) throws InvalidDataException
	{
		int tmp = 0;
		
		try
		{
			tmp = Integer.parseInt(seqSort);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidDataException("Sequence Sort must be an integer.", e);
		}
		
		_seq_sort = tmp;
	}
	
	/**
	*	Sets the required Sequence Sort field in this <code>SequenceSection</code> object.
	*
	*	@param	seqSort An int containing the Sequence Sort value for this ADL.
	*/
	public void setSeqSort (int seqSort)
	{
		_seq_sort = seqSort;
	}
	
	/**
	*	Returns the Sequence Sort field in this <code>SequenceSection</code> object.
	*
	*	@return	 An int containing the Sequence Sort value for this ADL.
	*/
	public int getSeqSort ()
	{
		return _seq_sort;
	}
	
	/**
	*	Sets the optional Sequence Multichan field in this <code>SequenceSection</code> object.
	*
	*	@param	seqMultichan A String containing the Sequence Multichan value for this ADL. FALSE = all events single-channel (default) 
	*	TRUE = one or more events contain multiple audio channels 
	*/
	public void setSeqMultichan (String seqMultichan) throws InvalidDataException
	{
		if (EDMLParser.validateADLBoolean(seqMultichan))
		{
			_seq_multichan = seqMultichan.equalsIgnoreCase("TRUE");
		}
		else
		{
			throw new InvalidDataException("Sequence Multichan must have a boolean value of TRUE or FALSE.");
		}
	}
	
	/**
	*	Sets the optional Sequence Multichan field in this <code>SequenceSection</code> object.
	*
	*	@param	seqMultichan A boolean containing the Sequence Multichan value for this ADL. FALSE = all events single-channel (default) 
	*	TRUE = one or more events contain multiple audio channels 
	*/
	public void setSeqMultichan (boolean seqMultichan)
	{
		_seq_multichan = seqMultichan;
	}
	
	/**
	*	Returns the Sequence Multichan field in this <code>SequenceSection</code> object.
	*
	*	@return	 A boolean containing the Sequence Multichan value for this ADL.  FALSE = all events single-channel (default) 
	*	TRUE = one or more events contain multiple audio channels 
	*/
	public boolean getSeqMultichan ()
	{
		return _seq_multichan;
	}
	
	/**
	*	Sets the required Sequence Dest Start field in this <code>SequenceSection</code> object. This field gives the originating system 
	*	time-line destination start, a TCF that also encodes the originating system resolve speed, frame and sampling rates, and 
	*	time-code type.
	*
	*	@param	seqDestStart A String containing the Sequence Dest Start value for this ADL. 
	*/
	public void setSeqDestStart (String seqDestStart) throws InvalidDataException
	{
		_seq_dest_start = new TcfToken(seqDestStart);
	}
	
	/**
	*	Sets the required Sequence Dest Start field in this <code>SequenceSection</code> object. This field gives the originating system 
	*	time-line destination start, a TCF that also encodes the originating system resolve speed, frame and sampling rates, and 
	*	time-code type.
	*
	*	@param	seqDestStart A <code>TcfToken</code> containing the Sequence Dest Start value for this ADL. 
	*/
	public void setSeqDestStart (TcfToken seqDestStart) throws InvalidDataException
	{
		_seq_dest_start = seqDestStart;
	}
	
	/**
	*	Returns the Sequence Dest Start field in this <code>SequenceSection</code> object.
	*
	*	@return	 A <code>TcfToken</code> containing the Sequence Dest Start value for this ADL.  
	*/
	public TcfToken getSeqDestStart ()
	{
		return _seq_dest_start;
	}
	
	/**
	*	Validates an int to confirm that it contains valid data for the Seq Sample Rate Factor field for an AES-31-3 document.
	*
	*	@param seqSampleRateMultiplier The int to validate for the sample rate factor field.
	*	@return True if the data is valid, false otherwise.
	*/
	public boolean validateSampleRateFactor (int seqSampleRateMultiplier)
	{
		boolean rval = false;
		
		if (seqSampleRateMultiplier == 1 || seqSampleRateMultiplier == 2 || seqSampleRateMultiplier == 4)
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Validates an int to confirm that it contains valid data for the Seq Frame Rate field for an AES-31-3 document.
	*
	*	@param seqFrameRate The int to validate for the frame rate field.
	*	@return True if the data is valid, false otherwise.
	*/
	public boolean validateFrameRate (int seqFrameRate)
	{
		boolean rval = false;
		
		if (seqFrameRate == 30 || seqFrameRate == 25 || seqFrameRate == 24)
		{
			rval = true;
		}
		
		return rval;
	}
	
	/**
	*	Validates an int to confirm that it contains valid data for the Seq ADL Level field for an AES-31-3 document.
	*
	*	@param seqAdlLevel The int to validate for the ADL Level field.
	*	@return True if the data is valid, false otherwise.
	*/
	public boolean validateAdlLevel (int seqAdlLevel)
	{
		boolean rval = false;
		
		if (seqAdlLevel >= 1)
		{
			rval = true;
		}
		
		return rval;
	}
	
	
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		try
		{
			if (keyword.equalsIgnoreCase("SEQ_TITLE"))
			{
				this.setSeqTitle((String)data.elementAt(0));
			} 
			else if (keyword.equalsIgnoreCase("SEQ_DESCRIPT"))
			{
				this.setSeqDescript((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_SAMPLE_RATE"))
			{
				this.setSeqSampleRate((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_SAMP_RATE_FACTOR"))
			{
				this.setSeqSampleRateFactor((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_FRAME_RATE"))
			{
				this.setSeqFrameRate((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_ADL_LEVEL"))
			{
				this.setSeqAdlLevel((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_CLEAN"))
			{
				this.setSeqClean((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_SORT"))
			{
				this.setSeqSort((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_MULTICHAN"))
			{
				this.setSeqMultichan((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("SEQ_DEST_START"))
			{
				this.setSeqDestStart((String)data.elementAt(0));
			}
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	/*protected void readHeader (ADLTokenizer tokenizer) throws EDMLParserException
	{
		while (tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END)
		{
			if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
				if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_TITLE"))
				{	
					try {
						this.setSeqTitle(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				} 
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_DESCRIPT"))
				{	
					try {
						this.setSeqDescript(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_SAMPLE_RATE"))
				{	
					try {
						this.setSeqSampleRate(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_FRAME_RATE"))
				{	
					try {
						this.setSeqFrameRate(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_ADL_LEVEL"))
				{	
					try {
						this.setSeqAdlLevel(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_CLEAN"))
				{	
					try {
						this.setSeqClean(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_SORT"))
				{	
					try {
						this.setSeqSort(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_MULTICHAN"))
				{	
					try {
						this.setSeqMultichan(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQ_DEST_START"))
				{	
					try {
						this.setSeqDestStart(tokenizer.nextTokenValue());
					} catch (Exception e) {
						this.addValidationError(e.toString());
						e.printStackTrace();
					}
				}
				else if (tokenizer.tokenType != ADLTokenizer.TT_COMMENT)
				{
					this.addValidationError("UNEXPECTED KEYWORD FOUND IN SEQUENCE HEADER. Token value: \"" + 
								tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
					this.addExtraData("Line: " + String.valueOf(tokenizer.getLineNumber()) + " " + tokenizer.tokenValue, tokenizer.tokenValue);
				}
				
				
			}
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				throw new EDMLParserException("Found overlapping SECTION Tags for " + this.sectionName + " and " + tokenizer.tokenValue);
			}
			else if (tokenizer.tokenType != ADLTokenizer.TT_COMMENT)
			{
				this.addValidationError("UNEXPECTED DATA FOUND IN SEQUENCE HEADER. Token value: \"" + 
							tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				this.addExtraData("Line: " + String.valueOf(tokenizer.getLineNumber()) + " " + tokenizer.tokenValue, tokenizer.tokenValue);
			}
			}*/
		/* capture close of section tage for validation purposes */
		/*if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
		{
			if (tokenizer.tokenValue.equals(this.sectionName))
			{
				this.sectionClosed();
			} 
			else
			{
				throw new EDMLParserException(this.sectionName + " close tag not found");
			}
			//debug
			//System.out.println(rval);
		}
	}*/
	
	/**
	*	Returns a String representation of this <code>SequenceSection</code>.
	*
	*	@return A String representation of this <code>SequenceSection</code>.
	*/
	public String toString ()
	{
		String rval = "\n<SEQUENCE>";
		
		if (_seq_title != null)
		{
			rval = rval.concat("\n\t(SEQ_TITLE)\t\"" +  EDMLParser.sanitizeEDMLQuotes(_seq_title) + "\"");
		}
		
		if (_seq_descript != null)
		{
			rval = rval.concat("\n\t(SEQ_DESCRIPT)\t\"" +  EDMLParser.sanitizeEDMLQuotes(_seq_descript) + "\"");
		}
		
		rval = rval.concat("\n\t(SEQ_SAMPLE_RATE)\t" + _seq_sample_rate);
		
		ADLSection adl = (ADLSection)this.getParent();
		VersionSection vs = adl.getVersionSection();
		EDMLVersion docVersion = vs.getVerAdlVersion();
		
		if (docVersion.getMinorRelease() >= 2)
		{
			if (validateSampleRateFactor(_seq_sample_rate_factor))
			{
				rval = rval.concat("\n\t(SEQ_SAMP_RATE_FACTOR)\t" + _seq_sample_rate_factor);
			}
		}
		
		 
		rval = rval.concat("\n\t(SEQ_FRAME_RATE)\t" + _seq_frame_rate + 
			"\n\t(SEQ_ADL_LEVEL)\t\t" + _seq_adl_level + 
			"\n\t(SEQ_CLEAN)\t\t" + String.valueOf(_seq_clean).toUpperCase());
		
		if (_seq_sort > -1)
		{
			rval = rval.concat("\n\t(SEQ_SORT)\t\t" + _seq_sort);
		}
		
		rval = rval.concat("\n\t(SEQ_MULTICHAN)\t\t" + String.valueOf(_seq_multichan).toUpperCase() + 
			"\n\t(SEQ_DEST_START)\t" + _seq_dest_start + "\n</SEQUENCE>");
		
		return rval;
		
	}
	
	public Element toXmlElement ()
	{
		Element vs;
		Element e;
		vs = new Element("sequence");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		if (_seq_title != null)
		{
			e = new Element("seqTitle");
			e.setNamespace(ADLSection.aes);
			e.setText(_seq_title);
			vs.addContent(e);
		}
		
		if (_seq_descript != null)
		{
			e = new Element("seqDescript");
			e.setNamespace(ADLSection.aes);
			e.setText(_seq_descript);
			vs.addContent(e);
		}
		
		e = new Element("seqSampleRate");
		e.setNamespace(ADLSection.aes);
		e.setText(_seq_sample_rate);
		vs.addContent(e);
		
		ADLSection adl = (ADLSection)this.getParent();
		VersionSection vers = adl.getVersionSection();
		EDMLVersion docVersion = vers.getVerAdlVersion();
		
		if (docVersion.getMinorRelease() >= 2)
		{
			if (validateSampleRateFactor(_seq_sample_rate_factor))
			{
				e = new Element("seqSampleRateFactor");
				e.setNamespace(ADLSection.aes);
				e.setText(Integer.toString(_seq_sample_rate_factor));
				vs.addContent(e);
			}
		}
		
		e = new Element("seqFrameRate");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_seq_frame_rate));
		vs.addContent(e);
		
		e = new Element("seqAdlLevel");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_seq_adl_level));
		vs.addContent(e);
		
		e = new Element("seqClean");
		e.setNamespace(ADLSection.aes);
		e.setText(Boolean.toString(_seq_clean));
		vs.addContent(e);
		
		e = new Element("seqSort");
		e.setNamespace(ADLSection.aes);
		e.setText(Integer.toString(_seq_sort));
		vs.addContent(e);
		
		e = new Element("seqMultichan");
		e.setNamespace(ADLSection.aes);
		e.setText(Boolean.toString(_seq_multichan));
		vs.addContent(e);
		
		e = new Element("seqDestStart");
		e.setNamespace(ADLSection.aes);
		e.setText(_seq_dest_start.toString());
		vs.addContent(e);
		
		return vs;
	}
	
	
	/**
	*	Converts all TcfToken's contained in this <code>SequenceSection</code> to a new sample-rate representation. The sample remainder 
	*	of the TcfToken is adjusted by the ratio of the new sample-rate and the frame count.
	*
	*	@param sampleRate The new target sample-rate that all of this object's TcfToken's will be converted to. 
	*	@see TcfToken
	*/
	public void resample (double sampleRate)
	{
		if (_seq_dest_start != null)
		{
			_seq_dest_start.resample(sampleRate);
		}
		_seq_sample_rate = TcfToken.getSampleRateRepresentation(sampleRate);
	}
	
	/**
	*	Creates and returns a deep clone of this <code>SequenceSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		SequenceSection rval = (SequenceSection)super.clone();
		if (_seq_dest_start != null)
		{
			rval._seq_dest_start = (TcfToken)this._seq_dest_start.clone();
		}
		return rval;
	}
	
}

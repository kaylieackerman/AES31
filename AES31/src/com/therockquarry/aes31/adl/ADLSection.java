/*
	-------------------------------------------------------------------------------
	ADLSection.java
	AES31-3

	Created by David Ackerman on 7/6/05.

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

import java.util.*;
import java.io.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
*	The <code>ADLSection</code> object is the root of an Audio Decision List Document. It consists of up to
*	eight document subsections which organize the actual data for the project. The ADL specification
*	is available from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class ADLSection extends BaseSection implements Cloneable {
	
	private VersionSection					_versionSection;
	private ProjectSection					_projectSection;
	private SystemSection					_systemSection;
	private SequenceSection					_sequenceSection;
	private TracklistSection				_tracklistSection;
	private SourceIndexSection				_sourceIndexSection;
	private EventListSection				_eventListSection;
	private MarkerListSection				_markerListSection;
	private NuendoCuelistSection			_nuendoCuelistSection;
	private FaderListSection				_faderListSection;
	private MuteListSection					_muteListSection;
	private PanListSection					_panListSection;
	/*
	private RefListSection					_refListSection;
	*/
	private Vector<MalformedSectionData>	_malformedSections;
	
	protected MalformedSectionData msd = null;
	
	//public static Namespace aes = Namespace.getNamespace("aes31", "http://www.aes.org/aes31");
    public static Namespace aes = Namespace.getNamespace("http://www.aes.org/aes31");
	
	/**
	* Initializes a new empty <code>ADLSection</code> object which represents the root of an Audio Decision List document.
	*/
	public ADLSection() 
	{
		super ("ADL");
		_init ();
		//System.out.println("ADL CREATED");
	}
	
	private void _init ()
	{
		_malformedSections = new Vector<MalformedSectionData> ();
	}
	
	/**
	*	Sets the Version Section for this ADL document. An ADL document may have one and only one Version Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Version Section without warning.
	*
	*	@param versionSection A new/different version section for this ADL document.
	*	@see VersionSection
	*/
	public void setVersionSection (VersionSection versionSection)
	{
		_versionSection = versionSection;
		_versionSection.setParent(this);
	}
	
	/**
	*	Returns the Version Section for this ADL.
	*
	*	@return the <code>VersionSection</code> object for this ADL.
	*/
	public VersionSection getVersionSection ()
	{
		return _versionSection;
	}
	
	/**
	*	Sets the Project Section for this ADL document. An ADL document may have one and only one Project Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Project Section without warning.
	*
	*	@param projectSection A new/different project section for this ADL document.
	*	@see ProjectSection
	*/
	public void setProjectSection (ProjectSection projectSection)
	{
		_projectSection = projectSection;
		_projectSection.setParent(this);
	}
	
	/**
	*	Returns the Project Section for this ADL.
	*
	*	@return the <code>ProjectSection</code> object for this ADL.
	*/
	public ProjectSection getProjectSection ()
	{
		return _projectSection;
	}
	
	/**
	*	Sets the System Section for this ADL document. An ADL document may have one and only one System Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing System Section without warning.
	*
	*	@param systemSection A new/different system section for this ADL document.
	*	@see SystemSection
	*/
	public void setSystemSection (SystemSection systemSection)
	{
		_systemSection = systemSection;
		_systemSection.setParent(this);
	}
	
	/**
	*	Returns the System Section for this ADL.
	*
	*	@return the <code>SystemSection</code> object for this ADL.
	*/
	public SystemSection getSystemSection ()
	{
		return _systemSection;
	}
	
	/**
	*	Sets the Sequence Section for this ADL document. An ADL document may have one and only one Sequence Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Sequence Section without warning.
	*
	*	@param sequenceSection A new/different sequence section for this ADL document.
	*	@see SequenceSection
	*/
	public void setSequenceSection (SequenceSection sequenceSection)
	{
		_sequenceSection = sequenceSection;
		_sequenceSection.setParent(this);
	}
	
	/**
	*	Returns the Sequence Section for this ADL.
	*
	*	@return the <code>SequenceSection</code> object for this ADL.
	*/
	public SequenceSection getSequenceSection ()
	{
		return _sequenceSection;
	}
	
	/**
	*	Sets the Tracklist Section for this ADL document. An ADL document may have one and only one Tracklist Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Tracklist Section without warning.
	*
	*	@param tracklistSection A new/different tracklist section for this ADL document.
	*	@see TracklistSection
	*/
	public void setTracklistSection (TracklistSection tracklistSection)
	{
		_tracklistSection = tracklistSection;
		_tracklistSection.setParent(this);
	}
	
	/**
	*	Returns the Tracklist Section Section for this ADL.
	*
	*	@return the <code>TracklistSectionSection</code> object for this ADL.
	*/
	public TracklistSection getTracklistSection ()
	{
		return _tracklistSection;
	}
	
	/**
	*	Sets the Source Index Section for this ADL document. An ADL document may have one and only one Source Index Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Source Index Section without warning.
	*
	*	@param sourceIndexSection A new/different source index section for this ADL document.
	*	@see SourceIndexSection
	*/
	public void setSourceIndexSection (SourceIndexSection sourceIndexSection)
	{
		_sourceIndexSection = sourceIndexSection;
		_sourceIndexSection.setParent(this);
	}
	
	/**
	*	Returns the Source Index Section Section for this ADL.
	*
	*	@return the <code>SourceIndexSection</code> object for this ADL.
	*/
	public SourceIndexSection getSourceIndexSection ()
	{
		return _sourceIndexSection;
	}
	
	/**
	*	Sets the Event List Index Section for this ADL document. An ADL document may have one and only one Event List Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Event List Section without warning.
	*
	*	@param eventListSection A new/different event list section for this ADL document.
	*	@see EventListSection
	*/
	public void setEventListSection (EventListSection eventListSection)
	{
		_eventListSection = eventListSection;
		_eventListSection.setParent(this);
	}
	
	/**
	*	Returns the Event List Section Section for this ADL.
	*
	*	@return the <code>EventListSection</code> object for this ADL.
	*/
	public EventListSection getEventListSection ()
	{
		return _eventListSection;
	}
	
	/**
	*	Sets the Marker Section for this ADL document. An ADL document may have one and only one Marker Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Marker Section without warning.
	*
	*	@param markerListSection A new/different marker section for this ADL document.
	*	@see MarkerListSection
	*/
	public void setMarkerListSection (MarkerListSection markerListSection)
	{
		_markerListSection = markerListSection;
		_markerListSection.setParent(this);
	}
	
	
	public void setNuendoCuelistSection (NuendoCuelistSection nuendoCuelistSection)
	{
		_nuendoCuelistSection = nuendoCuelistSection;
		_nuendoCuelistSection.setParent(this);
	}
	
	/**
	*	Sets the Fader List Section for this ADL document. An ADL document may have one and only one Fader List Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Fader List Section without warning.
	*
	*	@param faderListSection A new/different fader list section for this ADL document.
	*	@see FaderListSection
	*/
	public void setFaderListSection (FaderListSection faderListSection)
	{
		_faderListSection = faderListSection;
		_faderListSection.setParent(this);
	}
	
	/**
	*	Sets the Mute List Section for this ADL document. An ADL document may have one and only one Mute List Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Mute List Section without warning.
	*
	*	@param muteListSection A new/different mute list section for this ADL document.
	*	@see MuteListSection
	*/
	public void setMuteListSection (MuteListSection muteListSection)
	{
		_muteListSection = muteListSection;
		_muteListSection.setParent(this);
	}
	
	/**
	*	Sets the Pan List Section for this ADL document. An ADL document may have one and only one Pan List Section. Setting this 
	*	value may overwrite the ADL documents reference to a current existing Pan List Section without warning.
	*
	*	@param panListSection A new/different pan list section for this ADL document.
	*	@see PanListSection
	*/
	public void setPanListSection (PanListSection panListSection)
	{
		_panListSection = panListSection;
		_panListSection.setParent(this);
	}
	
	/**
	*	Returns the Marker Section Section for this ADL.
	*
	*	@return the <code>MarkerListSection</code> object for this ADL.
	*/
	public MarkerListSection getMarkerListSection ()
	{
		return _markerListSection;
	}
	
	/**
	*	Returns the Marker Section Section for this ADL.
	*
	*	@return the <code>MarkerListSection</code> object for this ADL.
	*/
	public NuendoCuelistSection getNuendoCuelistSection ()
	{
		return _nuendoCuelistSection;
	}
	
	/**
	*	Returns the Fader List Section Section for this ADL.
	*
	*	@return the <code>FaderListSection</code> object for this ADL.
	*/
	public FaderListSection getFaderListSection ()
	{
		return _faderListSection;
	}
	
	/**
	*	Returns the Mute List Section Section for this ADL.
	*
	*	@return the <code>MuteListSection</code> object for this ADL.
	*/
	public MuteListSection getMuteListSection ()
	{
		return _muteListSection;
	}
	
	/**
	*	Returns the Pan List Section Section for this ADL.
	*
	*	@return the <code>PanListSection</code> object for this ADL.
	*/
	public PanListSection getPanListSection ()
	{
		return _panListSection;
	}
	
	
	
	protected boolean readHeader (ADLTokenizer tokenizer) throws EDMLParserException, FileNotFoundException, IOException
	{
		boolean rval = false;
		BaseSection currentSection = null;
		
		while ((tokenizer.nextToken() != ADLTokenizer.TT_SECTION_END || !(tokenizer.tokenValue.equals(this.sectionName)))
			&& tokenizer.tokenType != ADLTokenizer.TT_EOF)
		{	
			//System.out.println("(ADL) At POS: " + tokenizer.getTokenPos() + "\tTOKEN: " + tokenizer.tokenValue);
			
			// add error checking to make sure sections are not being overwritten!
			
			if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_START)
			{
				if (tokenizer.tokenValue.equalsIgnoreCase("VERSION"))
				{
					currentSection = new VersionSection();
					this.setVersionSection((VersionSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("PROJECT"))
				{
					currentSection = new ProjectSection();
					this.setProjectSection((ProjectSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SYSTEM"))
				{
					currentSection = new SystemSection();
					this.setSystemSection((SystemSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SEQUENCE"))
				{
					currentSection = new SequenceSection();
					this.setSequenceSection((SequenceSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("TRACKLIST"))
				{
					currentSection = new TracklistSection();
					this.setTracklistSection((TracklistSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("SOURCE_INDEX"))
				{
					currentSection = new SourceIndexSection();
					this.setSourceIndexSection((SourceIndexSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("EVENT_LIST"))
				{
					currentSection = new EventListSection();
					this.setEventListSection((EventListSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("MARK_LIST"))
				{
					currentSection = new MarkerListSection();
					this.setMarkerListSection((MarkerListSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("MARKER_LIST"))
				{
					currentSection = new MarkerListSection();
					this.setMarkerListSection((MarkerListSection)currentSection);
				}

				else if (tokenizer.tokenValue.equalsIgnoreCase("NUENDO_CUELIST"))
				{
					currentSection = new NuendoCuelistSection();
					this.setNuendoCuelistSection((NuendoCuelistSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("FADER_LIST"))
				{
					currentSection = new FaderListSection();
					this.setFaderListSection((FaderListSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("MUTE_LIST"))
				{
					currentSection = new MuteListSection();
					this.setMuteListSection((MuteListSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("PAN_LIST"))
				{
					currentSection = new PanListSection();
					this.setPanListSection((PanListSection)currentSection);
				}
				else if (tokenizer.tokenValue.equalsIgnoreCase("REF_LIST"))
				{
				
				}
				
				if (currentSection != null)
				{
					currentSection.sectionOpened();
					boolean b = currentSection.readHeader(tokenizer);
					currentSection = null;
				}
			} //end of section start if
			 /* unexpected keyword or data between sections. Illegal according to specification */
			else if (tokenizer.tokenType == ADLTokenizer.TT_KEYWORD)
			{
			
				// gather all clumped keywords in an object and then querry all section types for a section that contains them
				// if one is found create a new section (if one does not already exist) and back up the parser and read in data
				// this will catch a missing start tag.
				
				
				if (_malformedSections.isEmpty())
				{
					msd = new MalformedSectionData ();
					_malformedSections.addElement(msd);

				}
								
				if (((MalformedSectionData)_malformedSections.lastElement()).isClosed())
				{
					msd = new MalformedSectionData ();
					_malformedSections.addElement(msd);
				}
				
				Vector data = null;
				String keyword = null;
				int lineNumber = tokenizer.getLineNumber();
		
				keyword = tokenizer.tokenValue;
				data = tokenizer.allDataForCurrentKeyword();
				msd.add(keyword, data);
				
				if (throwExceptions)
				{
					throw new EDMLParserException("Unexpected keyword found between document sections.\"" + 
						keyword + "\"" + " At line: " + lineNumber);
				}
				
				
				this.addValidationError("Unexpected keyword found between document sections.\"" + 
						keyword + "\"" + " At line: " + lineNumber);
						
				this.wellFormed = false;
				rval = false;
				
				if (stopOnExceptions)
				{
					return rval;
				}
			}
			else if (tokenizer.tokenType == ADLTokenizer.TT_DATA)
			{
				if (throwExceptions)
				{
					throw new EDMLParserException("Unexpected data found between document sections.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				}
				
				this.addValidationError("Unexpected data found between document sections.\"" + 
						tokenizer.tokenValue + "\"" + " At line: " + tokenizer.getLineNumber());
				
				this.wellFormed = false;
				rval = false;
				
				if (stopOnExceptions)
				{
					return rval;
				}

			} // end of if for TT_SECTION_START
			else if (tokenizer.tokenType == ADLTokenizer.TT_SECTION_END)
			{
				if (tokenizer.tokenValue.equals(this.sectionName))
				{
					this.sectionClosed();
				}
				else
				{
					//close tag (for unopened section)
					if (!_malformedSections.isEmpty())
					{
						if (!((MalformedSectionData)_malformedSections.lastElement()).isClosed())
						{
							((MalformedSectionData)_malformedSections.lastElement()).setLikelySection(tokenizer.tokenValue);
						}

					}
					
					
					if (throwExceptions)
					{
						throw new EDMLParserException("Found close tag for unopened " + tokenizer.tokenValue +
							" section tag" + " At line: " + tokenizer.getLineNumber());
					}
				
					this.addValidationError("Found close tag for unopened " + tokenizer.tokenValue +
						" section tag" + " At line: " + tokenizer.getLineNumber());
					
					this.wellFormed = false;
					rval = false;
					
					if (stopOnExceptions)
					{
						return rval;
					}
				}
				//debug
				//System.out.println(adl);
			}

		} // end of while loop
		
		return rval; //tmp
	}
	
	/**
	*	Returns a String representation of this entire ADL document.
	*
	*	@return A String representation of this entire ADL document.
	*/
	public String toString ()
	{
		String rval = "<ADL>";
		
		if (_versionSection != null)
		{
			rval = rval.concat(_versionSection.toString());
		}
		
		if (_projectSection != null)
		{
			rval = rval.concat(_projectSection.toString());
		}
		
		if (_systemSection != null)
		{
			rval = rval.concat(_systemSection.toString());
		}
		
		if (_sequenceSection != null)
		{
			rval = rval.concat(_sequenceSection.toString());
		}
		
		if (_tracklistSection != null)
		{
			rval = rval.concat(_tracklistSection.toString());
		}
		
		if (_sourceIndexSection != null)
		{
			rval = rval.concat(_sourceIndexSection.toString());
		}
		
		if (_eventListSection != null)
		{
			rval = rval.concat(_eventListSection.toString());
		}
		
		if (_faderListSection != null)
		{
			rval = rval.concat(_faderListSection.toString());
		}
		
		if (_muteListSection != null)
		{
			rval = rval.concat(_muteListSection.toString());
		}
		
		if (_panListSection != null)
		{
			rval = rval.concat(_panListSection.toString());
		}
		
		if (_markerListSection != null)
		{
			rval = rval.concat(_markerListSection.toString());
		}
		
		if (_nuendoCuelistSection != null)
		{
			rval = rval.concat(_nuendoCuelistSection.toString());
		}
		
		rval = rval.concat("\n</ADL>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		Element e;
		Namespace xsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		
		
		e = new Element("adl");
		e.setNamespace(ADLSection.aes);
		e.addNamespaceDeclaration(xsd);
		e.addNamespaceDeclaration(xsi);
		e.setAttribute("schemaLocation", "http://www.aes.org/aes31 http://people.fas.harvard.edu/~dackerm/aes/aes31.xsd" , xsi);
		e.setAttribute("id", "_" + UUID.randomUUID().toString());
		//e.setAttribute("type", "adlType", xsi);
		
		if (_versionSection != null)
		{
			e.addContent(_versionSection.toXmlElement());
		}
		
		if (_projectSection != null)
		{
			e.addContent(_projectSection.toXmlElement());
		}
		
		if (_systemSection != null)
		{
			e.addContent(_systemSection.toXmlElement());
		}
		
		if (_sequenceSection != null)
		{
			e.addContent(_sequenceSection.toXmlElement());
		}
		
		if (_tracklistSection != null)
		{
			e.addContent(_tracklistSection.toXmlElement());
		}
		
		if (_sourceIndexSection != null)
		{
			e.addContent(_sourceIndexSection.toXmlElement());
		}
		
		if (_eventListSection != null)
		{
			e.addContent(_eventListSection.toXmlElement());
		}
		
		if (_faderListSection != null)
		{
			e.addContent(_faderListSection.toXmlElement());
		}
		
		if (_muteListSection != null)
		{
			e.addContent(_muteListSection.toXmlElement());
		}
		
		if (_panListSection != null)
		{
			e.addContent(_panListSection.toXmlElement());
		}
		
		if (_markerListSection != null)
		{
			e.addContent(_markerListSection.toXmlElement());
		}
		
		
		return e;
	}
	
	
	/**
	*	Returns the total number of error messages that occured while parsing an ADL document.
	*/
	public int getValidationErrorCount ()
	{
		int rval = 0;
		
		if (_versionSection != null)
		{
			rval += _versionSection.getNumValidationErrors();
		}
		if (_projectSection != null)
		{
			rval += _projectSection.getNumValidationErrors();
		}
		if (_systemSection != null)
		{
			rval += _systemSection.getNumValidationErrors();
		}
		if (_sequenceSection != null)
		{
			rval += _sequenceSection.getNumValidationErrors();
		}
		if (_tracklistSection != null)
		{
			rval += _tracklistSection.getNumValidationErrors();
		}if (_sourceIndexSection != null)
		{
			rval += _sourceIndexSection.getNumValidationErrors();
		}
		if (_eventListSection != null)
		{
			rval += _eventListSection.getNumValidationErrors();
		}
		if (_markerListSection != null)
		{
			rval += _markerListSection.getNumValidationErrors();
		}
		
		if (_nuendoCuelistSection != null)
		{
			rval += _nuendoCuelistSection.getNumValidationErrors();
		}
		
		if (_faderListSection != null)
		{
			rval += _faderListSection.getNumValidationErrors();
		}
		
		if (_muteListSection != null)
		{
			rval += _muteListSection.getNumValidationErrors();
		}
		
		if (_panListSection != null)
		{
			rval += _panListSection.getNumValidationErrors();
		}
		
		rval += this.getNumValidationErrors();
		
		return rval;
	}
	
	/**
	*	Prints to STD OUT all error messages that occured while parsing an ADL document.
	*/
	public void printAllErrors ()
	{
		if (_versionSection != null)
		{
			_versionSection.printErrors();
		}
		if (_projectSection != null)
		{
			_projectSection.printErrors();
		}
		if (_systemSection != null)
		{
			_systemSection.printErrors();
		}
		if (_sequenceSection != null)
		{
			_sequenceSection.printErrors();
		}
		if (_tracklistSection != null)
		{
			_tracklistSection.printErrors();
		}if (_sourceIndexSection != null)
		{
			_sourceIndexSection.printErrors();
		}
		if (_eventListSection != null)
		{
			_eventListSection.printErrors();
		}
		if (_markerListSection != null)
		{
			_markerListSection.printErrors();
		}
		
		if (_nuendoCuelistSection != null)
		{
			_nuendoCuelistSection.printErrors();
		}
		
		if (_faderListSection != null)
		{
			_faderListSection.printErrors();
		}
		
		if (_muteListSection != null)
		{
			_muteListSection.printErrors();
		}
		
		if (_panListSection != null)
		{
			_panListSection.printErrors();
		}
		
		this.printErrors();
	}
	
	/**
	 * @deprecated
	 *	Attempts to write the contents of this ADL document to the specified File object.
	*
	*	@param file The File object to which to write the ADL document.
	*/
	public void save (File file)
	{
		try
		{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(this.toString(), 0, this.toString().length());
			bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	*	Converts all TcfToken's contained in this ADL document to a new sample-rate representation. The sample remainder 
	*	of the TcfToken is adjusted by the ratio of the new sample-rate and the frame count.
	*
	*	@param sampleRate The new target sample-rate that all of this documents TcfToken's will be converted to. 
	*	@see TcfToken
	*	@see EventListSection
	*	@see SequenceSection
	*	@see SystemSection
	*/
	public void resample (double sampleRate)
	{
		_eventListSection.resample (sampleRate);
		_sequenceSection.resample (sampleRate);
		_systemSection.resample (sampleRate);
		_sourceIndexSection.resample(sampleRate);
		if (_markerListSection != null)
		{
			_markerListSection.resample(sampleRate);
		}
		if (_nuendoCuelistSection != null)
		{
			_nuendoCuelistSection.resample(sampleRate);
		}
		if (_faderListSection != null)
		{
			_faderListSection.resample(sampleRate);
		}
		if (_muteListSection != null)
		{
			_muteListSection.resample(sampleRate);
		}
		if (_panListSection != null)
		{
			_panListSection.resample(sampleRate);
		}
	}
	

	/**
	*	Creates and returns a deep clone of this <code>ADLSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		ADLSection rval = (ADLSection)super.clone();
		
		if (_versionSection != null)
		{
			rval.setVersionSection((VersionSection)_versionSection.clone());
		}
		
		if (_systemSection != null)
		{
			rval.setSystemSection((SystemSection)_systemSection.clone());
		}
		
		if (_sequenceSection != null)
		{
			rval.setSequenceSection((SequenceSection)_sequenceSection.clone());
		}
		
		if (_tracklistSection != null)
		{
			rval.setTracklistSection((TracklistSection)_tracklistSection.clone());
		}
		
		if (_sourceIndexSection != null)
		{
			rval.setSourceIndexSection((SourceIndexSection)_sourceIndexSection.clone());
		}
		
		if (_eventListSection != null)
		{
			rval.setEventListSection((EventListSection)_eventListSection.clone());
		}
		
		if (_markerListSection != null)
		{
			rval.setMarkerListSection((MarkerListSection)_markerListSection.clone());
		}
		
		if (_faderListSection != null)
		{
			rval.setFaderListSection((FaderListSection)_faderListSection.clone());
		}
		
		if (_muteListSection != null)
		{
			rval.setMuteListSection((MuteListSection)_muteListSection.clone());
		}
		
		if (_panListSection != null)
		{
			rval.setPanListSection((PanListSection)_panListSection.clone());
		}
		
		/*
		if (_refListSection != null)
		{
			rval.setRefListSection((RefListSection)_refListSection.clone());
		}
		*/
		
		if (_malformedSections != null)
		{
			rval._malformedSections = (Vector)_malformedSections.clone();
		}
		
		if (msd != null)
		{
			rval.msd = (MalformedSectionData)msd.clone();
		}
		
		
		return rval;
	}
	
}

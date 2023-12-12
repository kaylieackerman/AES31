/*
	-------------------------------------------------------------------------------
	SourceIndexSection.java
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

import java.text.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>SourceIndexSection</code> object contains a list of all of the audio sources utilized by the ADL.
*	Source index numbers must be unique and correspond 
*	to the source channels in the event list. For further details see the ADL specification, available 
*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class SourceIndexSection extends BaseSection implements Cloneable {
	
	private FlexibleTreeMap<Integer, SourceEntry> _indexMap;		/* list of all indexes for source recordings */
	
	/**
	*	Constructs a new empty <code>SourceIndexSection</code> object. 
	*/
	public SourceIndexSection ()
	{
		super ("SOURCE_INDEX");
		_init ();
	}
	
	private void _init ()
	{
		_indexMap = new FlexibleTreeMap<Integer, SourceEntry> ();
	}
	
	/**
	*	Adds a <code>SourceEntry</code> to this SourceIndexSection object, at the index specified by the passed index parameter, 
	*	if that index number is unoccupied in this SourceIndexSection. Use this method if you wish to avoid overwritting existing
	*	<code>SourceEntry</code> objects. If you wish to replace existing <code>SourceEntry</code> objects use <code>setSourceEntryAtIndex</code>
	*	instead.
	*
	*	@param index The index in this SourceIndexSection that the SoourceEntry should occupy.
	*	@param entry The SourceEntry to add to this SourceIndexSection.
	*
	*/
	public void addSourceEntryAtIndex (int index, SourceEntry entry) throws InvalidDataException
	{

		Integer i = new Integer(index);
		
		if (!validateIndexNumber(index))
		{
			throw new InvalidDataException("\"" + index + "\" is not valid. Number must be greater than or equal to 1");
		}
		
		if (_indexMap.containsKey(i))
		{
			throw new InvalidDataException("An entry allready exists at poistion \"" + i + "\" in the Source Index.");
		}
		
		if (_indexMap.containsValue(entry))
		{
			throw new InvalidDataException("Entry \"" + entry + "\" already exists in the Source Index at index \"" + this.getIndexForEntry(entry) + "\".");
		}
		
		_indexMap.put(i, entry);
		entry.setParent(this);
	}
	
	/**
	*	Appends the passed <code>SourceEntry</code> object to the end of this <code>SourceIndexSection</code> list. Note that this method sets
	*	the indexNumber of the passed <code>SourceEntry</code> object.
	*
	*	@param entry The <code>SourceEntry</code> object to add to this <code>SourceIndexSection</code>. 
	*/
	public void addSourceEntry (SourceEntry entry) throws InvalidDataException
	{	
		int lastKey;
		
		try
		{
			lastKey = ((Integer)_indexMap.lastKey()).intValue();
			lastKey++;
		}
		catch (NoSuchElementException e)
		{
			lastKey = 1;
		}
		
		if (!_indexMap.containsValue(entry))
		{
			_indexMap.put(new Integer(lastKey), entry);
			entry.setParent(this);
		}
		else
		{
			throw new InvalidDataException("Entry \"" + entry + "\" already exists in the Source Index at index \"" + this.getIndexForEntry(entry) + "\".");
		}
		
	}
	
	/**
	*	Removes all instances of the passed <code>SourceEntry</code> object from this <code>SourceIndexSection</code>.
	*
	*	@param entry The <code>SourceEntry</code> object to remove from this <code>SourceIndexSection</code>.
	*/
	public void deleteSourceEntry (SourceEntry entry)
	{
		_indexMap.removeKeysForValue(entry);
	}
	
	/**
	*	Removes the <code>SourceEntry</code> object specified by <code>index</code> from this <code>SourceIndexSection</code>.
	*
	*	@param index The index of the <code>SourceEntry</code> object to be removed from this <code>SourceIndexSection</code>.
	*/
	public void deleteSourceEntryAtIndex (int index)
	{
		Integer i = new Integer(index);
		
		if (_indexMap.containsKey(i))
		{
			SourceEntry tmp = (SourceEntry)_indexMap.get(i);
			_indexMap.remove(tmp);
		}
	}
	
	/**
	*	Sets the <code>SourceEntry</code> for the index argument. If an entry already exists at the index passed, then the entry is overwritten
	*	by <code>entry</code>.
	*
	*	@param index The index location in this <code>SourceIndexEntry</code> to place the <code>entry</code> parameter.
	*	@param entry The <code>SourceEntry</code> object to set in this list.
	*/
	public void setSourceEntryAtIndex (int index, SourceEntry entry) throws InvalidDataException
	{
		if (validateIndexNumber(index))
		{
			Integer key = new Integer(index);
			
			_indexMap.put(key, entry);
			entry.setParent(this);
		}
		else
		{
			throw new InvalidDataException("\"" + index + "\" is not valid. Number must be greater than or equal to 1");
		}
		
	}
	
	/**
	*	Returns an ArrayList containing all of the <code>SourceEntry</code> objects contained in this <code>SourceIndexSection</code>.
	*
	*	@return An ArrayList containing all of the <code>SourceEntry</code> objects contained in this <code>SourceIndexSection</code>. 
	*/
	public ArrayList<SourceEntry> getSourceEntries ()
	{
		ArrayList<SourceEntry> rval = new ArrayList<SourceEntry>(_indexMap.values());
		
		return rval;
	}
	
	/**
	*	Returns the <code>SourceEntry</code> that occupies the position in this <code>SouceIndexSection</code> secified by the <code>index</code>
	*	parameter, or null if there is no entry at the specified index.
	*
	*	@param index The position in this <code>SourceIndexSection</code> that contains the <code>SourceEntry</code> to be returned.
	*	@return The <code>SourceEntry</code> object that resides as <code>index</code> in this <code>SourceEntrySection</code>, or null 
	*	if there is no entry at the specified index.
	*/ 
	public SourceEntry getSourceEntryAtIndex (int index)
	{
		SourceEntry rval = null;
		
		rval = (SourceEntry)_indexMap.get(new Integer(index));
		
		return rval;
	}
	
	/**
	*	Returns the index number for the first instance of <code>entry</code> in this <code>SourceIndexSection</code>.
	*
	*	@param entry The <code>SourceEntry</code> object to search for in this <code>SourceIndexSection</code>.
	*	@return An int representing the index number in this <code>SourceIndexSection</code> where the first instance of
	*	the <code>entry</code> object is found.
	*/
	public int getIndexForEntry (SourceEntry entry)
	{
		int rval = -1;
		
		ArrayList al = _indexMap.keysForValue(entry);
		if (al.size() > 0)
		{
			rval = ((Integer)al.get(0)).intValue();
		}
		
		return rval;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		SourceEntry source = null;
		BaseIndexEntry entry = null;
		String indexNum = null;
		int index;
		
		try
		{
			if (keyword.equalsIgnoreCase("INDEX"))
			{
				indexNum = ((String)data.elementAt(0));
				source = new SourceEntry(this);
				try
				{
					Integer theIndex = new Integer(indexNum);
					this.addSourceEntryAtIndex(theIndex, source);
				}
				catch (NumberFormatException e)
				{
					throw new InvalidDataException("Source Index must be an integer.", e);
				}
				
				
			} 
			
			else if (keyword.equalsIgnoreCase("F"))
			{
				Integer lastKey = (Integer)_indexMap.lastKey();
				
				source = (SourceEntry)_indexMap.get(lastKey);
				index = source.getIndexNumber();
				
				entry = new FileSourceIndexEntry ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
							(String)data.elementAt(3), (String)data.elementAt(4), (String)data.elementAt(5));
				
				source.addIndexEntry(entry);
			}
			else if (keyword.equalsIgnoreCase("T"))
			{
				Integer lastKey = (Integer)_indexMap.lastKey();
				
				source = (SourceEntry)_indexMap.get(lastKey);
				index = source.getIndexNumber();
				
				entry = new TapeSourceIndexEntry ((String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
							(String)data.elementAt(3), (String)data.elementAt(4));
				
				source.addIndexEntry(entry);
			}
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	
	/**
	*	Returns a String representation of this <code>SourceIndexSection</code>.
	*
	*	@return A String representation of this <code>SourceIndexSection</code>.
	*/
	public String toString()
	{
		SourceEntry sie;
		DecimalFormat form4 = new DecimalFormat("0000");
		Integer key;
		
		String rval = "\n<SOURCE_INDEX>";
		
		Set<Integer> set = _indexMap.keySet();
		Iterator<Integer> it = set.iterator();
		
		while (it.hasNext())
		{
			key = it.next();
			
			sie = (SourceEntry)_indexMap.get(key);
			
			rval = rval.concat("\n\t(Index)\t" +  form4.format(key.intValue()) + sie);
		}
			
		rval = rval.concat("\n</SOURCE_INDEX>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		SourceEntry sie;
		DecimalFormat form4 = new DecimalFormat("0000");
		Integer key;
		
		Element vs;
		Element e;
		vs = new Element("sourceIndex");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		Set<Integer> set = _indexMap.keySet();
		Iterator<Integer> it = set.iterator();
		
		while (it.hasNext())
		{
			key = it.next();
			
			sie = (SourceEntry)_indexMap.get(key);
			
			e = new Element("index");
			e.setNamespace(ADLSection.aes);
			e.setAttribute("indexNumber", form4.format(key.intValue()));
			e.setAttribute("id", "_" + UUID.randomUUID().toString());
			e.addContent(sie.toXmlElement());
			vs.addContent(e);
		
		}
		
		return vs;
	}
	
	
	/**
	*	Creates and returns a deep clone of this <code>SourceIndexSection</code> object.
	*
	*	@return A deep-cloned copy of this object.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		SourceIndexSection rval = (SourceIndexSection)super.clone();

		if (_indexMap != null)
		{	
			rval._indexMap = (FlexibleTreeMap)_indexMap.clone();
			rval._indexMap.clear();
			SourceEntry se = null;
			Set<Integer> set = this._indexMap.keySet();
			Iterator<Integer> it = set.iterator();
			
			while (it.hasNext())
			{
				Integer next = it.next();
				se = this._indexMap.get(next);
				if (se != null)
				{
					SourceEntry theClone = (SourceEntry)se.clone();
					rval._indexMap.put(se.getIndexNumber(), theClone);
					theClone.setParent(rval);
				}
			}
		}
		return rval;
	}
	
	/**
	*	Converts all TcfToken's contained in this <code>SourceIndexSection</code> to a new sample-rate representation. The sample remainder 
	*	of the TcfToken is adjusted by the ratio of the new sample-rate and the frame count.
	*
	*	@param sampleRate The new target sample-rate that all of this object's TcfToken's will be converted to. 
	*	@see TcfToken
	*/
	public void resample (double sampleRate)
	{
		ArrayList<SourceEntry> seal = this.getSourceEntries();
		for (SourceEntry se : seal)
		{
			ArrayList<BaseIndexEntry> entries = se.getIndexEntries();
			for (BaseIndexEntry e : entries)
			{
				e.resample(sampleRate);
			}
			
		}
	}
	
	private boolean validateIndexNumber (int indexNumber)
	{
		boolean rval = false;
		
		if (indexNumber >= 1)
		{
			rval = true;
		}
		
		return rval;
	}
	/**
	*	Determines if the <code>SourceEntry</code> specified by the <code>entry</code> parameter exists within this <code>SourceIndexSection</code>.
	*
	*	@param entry The <code>SourceEntry</code> object to search for within this <code>SourceIndexSection</code>.
	*	@return Returns a boolean value, <code>true</code> if <code>entry</code> is present in this <code>SourceIndexSection</code>, 
	*	<code>false</code> otherwise.
	*/
	public boolean containsEntry (SourceEntry entry)
	{
		return _indexMap.containsValue(entry);
	}
}

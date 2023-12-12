/*
	-------------------------------------------------------------------------------
	SourceEntry.java
	AES31-3

	Created on 7/12/06.

	Copyright 2006 David Ackerman.
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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
*	The <code>SourceEntry</code> object is a wrapper for the source entry level information stored in the <code>SourceIndexSection</code>
*	of an ADL document. For further details see the ADL specification, available 
*	from the Audio Engineering Society, <a href="http://www.aes.org/publications/standards/">
*	AES31-3-1999: AES standard for network and file transfer of audio -- Audio-file transfer and exchange -- 
*	Part 3: Simple project interchange</a>.
*
*	@author David Ackerman
*/
public class SourceEntry implements Cloneable {

	private ArrayList<BaseIndexEntry> _indexEntries;
	private SourceIndexSection _parent;
	
	/**
	*	Used with <code>getIndexEntries</code> to indicate which type of index entry objects should be returned.
	*/
	public enum SourceType {
		FILE_SRC, URI_SRC, TAPE_SRC
	}
		
	/**
	*	Constructs a new empty <code>SourceEntry</code> object.
	*
	*/
	public SourceEntry ()
	{
		_init();
	}
	
	/**
	*	Constructs a new <code>SourceEntry</code> object.
	*
	*	@param parent The <code>SourceIndexSection</code> to which this <code>SourceEntry</code> belongs.
	*/
	public SourceEntry (SourceIndexSection parent) throws InvalidDataException
	{
		_init();
		setParent(parent);
	}
	
	
	/**
	*	Constructs a new <code>SourceEntry</code> object.
	*
	 *	@param parent The <code>SourceIndexSection</code> to which this <code>SourceEntry</code> belongs.
	*	@param entries An array of <code>BaseEditEntry</code> objects with which to initialize this <codeSourceEntry</code>.
	*/
	public SourceEntry (SourceIndexSection parent, BaseIndexEntry[] entries) throws InvalidDataException
	{
		_init();
		setParent(parent);
		
		for (int i = 0; i < entries.length; i++)
		{
			_indexEntries.add(entries[i]);
		}
	}
	
	/**
	*	Adds an entry to this <code>SourceEntry</code> object. The entry can be any subclass of <code>BaseIndexEntry</code>. If
	*	multiple entries are present, they should all represent the same exact audio clip. Any one of them may be used by 
	*	an application to reconstruct the audio in the Event List of the ADL.
	*
	*	@param entry An instance of <code>BaseIndexEntry</code> that represents the source audio clip for this <code>SourceEntry</code>.
	*/
	public void addIndexEntry (BaseIndexEntry entry)
	{
		_indexEntries.add(entry);
		entry.setParent(this);
	}
	
	/**
	*	Returns the index entries for this <code>SourceEntry</code> object.
	*
	*	@return An ArrayList of index entries for this <code>SourceEntry</code> object. The resulting ArrayList may contain a series of
	*	objects that are sublcasses of <code>BaseIndexEntry</code>.
	*/
	public ArrayList<BaseIndexEntry> getIndexEntries ()
	{
		return _indexEntries;
	}

	
	/**
		*	Returns the index entries for this <code>SourceEntry</code> object.
	 *
	 *	@param srcType Flag to determine which sub-class of BaseEndexEntry to return. If no such entries exist, then returns an empty
	 *	ArrayList. scrType may be one of <code>FILE_SRC</code>, <code>URI_SRC</code> or <code>TAPE_SRC</code>.
	 *	@return An ArrayList of index entries for this <code>SourceEntry</code> object. The type of entry returned is determined by
	 *	by the <code>srcType</code> parameter.
	 */
	public ArrayList getIndexEntries (SourceType srcType) throws InvalidDataException
	{
		ArrayList<BaseIndexEntry> rval = new ArrayList<BaseIndexEntry>();
		
		Iterator<BaseIndexEntry> it = _indexEntries.iterator();
		Class c = null;
		
		switch (srcType)
		{
			case FILE_SRC: c = FileSourceIndexEntry.class;
				break;
				
			case URI_SRC: 
				break;
				
			case TAPE_SRC: c = TapeSourceIndexEntry.class;
				break;
				
			default: throw new InvalidDataException ("srcType parameter not recognized");
		}
		
		while (it.hasNext())
		{
			Object nxt = it.next();
			if (nxt.getClass() == c)
			{
				rval.add((BaseIndexEntry)nxt);
			}
		}
		
		return rval;
	}
	
	/**
	*	Deletes the passed <code>BaseEditEntry</code> from this <code>SourceEntry</code>.
	*
	*	@param entry The <code>BaseIndexEntry</code> to remove from this <code>SourceEntry</code>.
	*/
	public void removeIndexEntry (BaseIndexEntry entry)
	{
		int test;
		
		test = _indexEntries.indexOf(entry);
		if (test != -1)
		{
			_indexEntries.remove(test);
			entry.setParent(null);
		}
	}
	
	/**
	*	Returns a String representation of this <code>SourceEntry</code>.
	*
	*	@return A String representation of this <code>SourceEntry</code>.
	*/
	public String toString ()
	{
		String rval = "";
		
		Iterator<BaseIndexEntry> it = _indexEntries.iterator();
		while (it.hasNext())
		{
			rval = rval.concat(((BaseIndexEntry)it.next()).toString());
		}
		
		return rval;
	}
	
	public ArrayList<Element> toXmlElement ()
	{
		
		ArrayList<Element> rval = new ArrayList<Element>();
			
		Iterator<BaseIndexEntry> it = _indexEntries.iterator();
		while (it.hasNext())
		{
			rval.add(((BaseIndexEntry)it.next()).toXmlElement());
		}
		
		return rval;
	}
	
	
	/**
	*	Sets the <code>SourceIndexSection</code> object to which this <code>SourceEntry</code> instance belongs.
	*
	*	@param parent The <code>SourceIndexSection</code> object that contains this <code>SourceEntry</code> object.
	*/
	public void setParent (SourceIndexSection parent)
	{
		_parent = parent;
	}
	
	/**
	*	Gets the <code>SourceIndexSection</code> object to which this <code>SourceEntry</code> belongs.
	*
	*	@return The <code>SourceIndexSection</code> object to which this <code>SourceEntry</code> belongs.
	*/
	public SourceIndexSection getParent ()
	{
		if (_parent != null)
		{
			if (!_parent.containsEntry(this))
			{
				_parent = null;
			}
		}
		
		return _parent;
	}
	
	/**
	*	Returns the index number for this <code>SourceEntry</code> instance, indicating this entry's place in the <code>SourceIndexSection</code>.
	*
	*	@return This Source Entry number.
	*/
	public int getIndexNumber ()
	{
		int rval = -1;
		
		SourceIndexSection p = this.getParent();
		if (p != null)
		{
			rval = p.getIndexForEntry(this);
		}
		
		return rval;
	}
	
	/**
	*	Creates and returns a semi deep clone of this <code>SourceEntry</code> object.
	*
	*	@return A deep-cloned copy of this object. Note that the new parent is not known by this object when cloning and is
	*	explicitly set by the <code>SourceIndexSection</code> when cloning is a result of a clone of the <code>
	*	SourceIndexSection</code>. If the clone is initiated from some other vector then it is the responsibility of that 
	*	code to set the parent as appropriate. If nothing is done to explicitly set the parent of the clone, it will continue to 
	*	point to the parent of the object from which it was cloned.
	*/
	public Object clone () throws CloneNotSupportedException
	{
		SourceEntry rval = (SourceEntry)super.clone();
		rval._indexEntries = (ArrayList)_indexEntries.clone();
		
		//rval._indexEntries.clear();
		BaseIndexEntry toClone = null;
		BaseIndexEntry theClone = null;
		
		for (int i = 0; i < _indexEntries.size(); i++)
		{
			toClone = (BaseIndexEntry)_indexEntries.get(i);
			if (toClone != null)
			{
				theClone = (BaseIndexEntry)toClone.clone();
				rval._indexEntries.set(i, theClone);
				theClone.setParent(rval);
			}
		}
		
		return rval;
	}
	
	private void _init ()
	{
		_indexEntries = new ArrayList<BaseIndexEntry> ();
		_parent = null;
	}
}

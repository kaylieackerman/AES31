/*
	-------------------------------------------------------------------------------
	EventListSection.java
	AES31-3

	Created by Kaylie Ackerman on 7/10/05.

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

import java.util.*;
import java.math.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class EventListSection extends BaseSection implements Cloneable {
	
	/**
     * @deprecated Replaced by enum BaseEditEntry.EntryType
     */
	@Deprecated
	public static final int CUT_ENTRY = 100;
	
	private Vector<BaseEditEntry> _entries;
	private String entryNo = null;
	private BaseEditEntry entry = null;
	
	public EventListSection ()
	{
		super ("EVENT_LIST");
		_init ();
	}
	
	private void _init ()
	{
		_entries = new Vector<BaseEditEntry> ();
	}
	
	/**
     * @deprecated Replaced by <code>addEventEntry</code>
     */
	@Deprecated
	public void addEntry (BaseEditEntry bee)
	{
		_entries.add(bee);
		
		//debug
		//System.out.println("(entry) " + bee);
	}
	
	public void addEventEntry (BaseEditEntry bee)
	{
		_entries.add(bee);
		
		//debug
		//System.out.println("(entry) " + bee);
	}
	
	/**
     * @deprecated shift to ArrayList and iterator
     */
	@Deprecated
	public Enumeration enumerateEventEntries ()
	{
		return _entries.elements();
	}
	
	public ArrayList<BaseEditEntry> getEventEntries ()
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>(_entries);
		
		return rval;
	}
	
	public void sortEventListByTrackAndTime ()
	{
		for (BaseEditEntry bee: _entries)
		{
			bee.setSortByChannel(true);
		}
		Collections.sort(_entries);
		//renumber entries
		int i = 0;
		for (BaseEditEntry bee: _entries)
		{
			bee.setEntryNumber(++i);
		}
	}
	
	public void sortEventListByTime ()
	{
		for (BaseEditEntry bee: _entries)
		{
			bee.setSortByChannel(false);
		}
		Collections.sort(_entries);
		//renumber entries
		int i = 0;
		for (BaseEditEntry bee: _entries)
		{
			bee.setEntryNumber(++i);
		}
	}
    
    public BigDecimal getDuration ()
    {
        TcfToken start = new TcfToken(0, 96000.0);
        TcfToken end = new TcfToken(0, 96000.0);
        for (BaseEditEntry bee: _entries)
        {
            if (bee.getDestIn().compareTo(start) == -1) {
                start = bee.getDestIn();
            }
            
            if (bee.getDestOut().compareTo(end) == 1) {
                end = bee.getDestOut();
            }
        }
        BigDecimal length = end.getNumberOfSecondsForTimeCode().subtract(start.getNumberOfSecondsForTimeCode());
        return length;
    }
	
	/**
	*	Returns the <code>BaseEditEntry</code> that coresponds to the entry number argument.
	*
	*	@param entryNo The unique entry number in this <code>EventListSection</code> that identifies the entry to be returned.
	*	@return The  <code>BaseEditEntry</code> object identified by the <code>entryNo</code> argument.
	*/
	public BaseEditEntry getEventEntryNumbered (long entryNo)
	{
		BaseEditEntry rval = null;
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			if (bei.getEntryNumber() == entryNo)
			{
				rval = bei;
			}
		}
		
		return rval;
	}
	
	/**
     * @deprecated Replaced by enum BaseEditEntry.EntryType
     */
	@Deprecated
	public ArrayList<BaseEditEntry> getEventEntries (int entryType)
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			
			BaseEditEntry cei = (BaseEditEntry)e.nextElement();
			
			if (entryType == CUT_ENTRY)
			{
				Class test = cei.getClass();
				if (test.getName() == "com.therockquarry.aes31.adl.CutEditEntry")
				{
					rval.add(cei);
				}
			}
		}
		
		return rval;
	}
	
	public ArrayList<BaseEditEntry> getEventEntries (BaseEditEntry.EntryType entryType)
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			
			if (entryType == BaseEditEntry.EntryType.CUT)
			{
				Class test = bei.getClass();
				if (test.getName() == "com.therockquarry.aes31.adl.CutEditEntry")
				{
					rval.add(bei);
				}
			}
			else if (entryType == BaseEditEntry.EntryType.SILENCE)
			{
				Class test = bei.getClass();
				if (test.getName() == "com.therockquarry.aes31.adl.SilenceEditEntry")
				{
					rval.add(bei);
				}
			}
			else if (entryType == BaseEditEntry.EntryType.AUX)
			{
				Class test = bei.getClass();
				if (test.getName() == "com.therockquarry.aes31.adl.AuxiliaryEditEntry")
				{
					rval.add(bei);
				}
			}
			else if (entryType == BaseEditEntry.EntryType.VID)
			{
				Class test = bei.getClass();
				if (test.getName() == "com.therockquarry.aes31.adl.VideoEditEntry")
				{
					rval.add(bei);
				}
			}
		}
		
		return rval;
	}
	
	
	public ArrayList<BaseEditEntry> getEventEntriesForSource (int s)
	{

		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			if (bei.getClass().getName() == "com.therockquarry.aes31.adl.CutEditEntry")
			{
				CutEditEntry cei = (CutEditEntry)bei;
				if (cei.getSrcIndex() == s)
				{

					rval.add(cei);
				}
				else
				{

					//check modifiers
					for (Enumeration f = cei.getModifiers(); f.hasMoreElements() ;) {
						BaseModifier bm = (BaseModifier)f.nextElement();
						
						if (bm.getClass() == InfadeModifier.class)
						{
							if (((InfadeModifier)bm).getSrcIndex() == s)
							{
								rval.add(cei);
							}
							
						}
						else if (bm.getClass() == OutfadeModifier.class)
						{
							if (((OutfadeModifier)bm).getSrcIndex() == s)
							{
								rval.add(cei);
							}
						}
						else if (bm.getClass() == XfadeModifier.class)
						{
							if (((XfadeModifier)bm).getSrcIndex() == s)
							{
								rval.add(cei);
							}
						}
					}
				}
			}
		}
		return rval;
	}
	
	public ArrayList<BaseEditEntry> getEventEntriesForDestChannel (int d)
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			Range test = bei.getDestChannels();
			if (test.isWithinRange(d))
			{
				rval.add(bei);
			}
		}
		
		return rval;
	}
	
	public ArrayList<BaseEditEntry> getEventEntriesForDestChannel (int d, BaseEditEntry.EntryType entryType)
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			Range rtest = bei.getDestChannels();
			if (rtest.isWithinRange(d))
			{
				if (entryType == BaseEditEntry.EntryType.CUT)
				{
					Class test = bei.getClass();
					if (test.getName() == "com.therockquarry.aes31.adl.CutEditEntry")
					{
						rval.add(bei);
					}
				}
				else if (entryType == BaseEditEntry.EntryType.SILENCE)
				{
					Class test = bei.getClass();
					if (test.getName() == "com.therockquarry.aes31.adl.SilenceEditEntry")
					{
						rval.add(bei);
					}
				}
				else if (entryType == BaseEditEntry.EntryType.AUX)
				{
					Class test = bei.getClass();
					if (test.getName() == "com.therockquarry.aes31.adl.AuxiliaryEditEntry")
					{
						rval.add(bei);
					}
				}
				else if (entryType == BaseEditEntry.EntryType.VID)
				{
					Class test = bei.getClass();
					if (test.getName() == "com.therockquarry.aes31.adl.VideoEditEntry")
					{
						rval.add(bei);
					}
				}
			}
		}
		
		return rval;
	}
	
	public ArrayList<BaseEditEntry> getEventEntriesForDestChannelRange (Range r)
	{
		ArrayList<BaseEditEntry> rval = new ArrayList<BaseEditEntry>();
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
			
			BaseEditEntry bei = (BaseEditEntry)e.nextElement();
			Range test = bei.getDestChannels();
			for (int i = r.getBegin(); i <= r.getEnd(); i++)
			{
				if (test.isWithinRange(i))
				{
					rval.add(bei);
					break; //avoid duplicates
				}
			}
		}
		
		return rval;
	}
	
	protected void addData (String keyword, Vector data, ADLTokenizer tokenizer)
	{
		
		try
		{
			if (keyword.equalsIgnoreCase("ENTRY"))
			{
				entryNo = ((String)data.elementAt(0));
			}
			else if (keyword.equalsIgnoreCase("CUT"))
			{
				entry = new CutEditEntry (entryNo, (String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2), 
						(String)data.elementAt(3), (String)data.elementAt(4), (String)data.elementAt(5), (String)data.elementAt(6), 
						(String)data.elementAt(7), this);
						this.addEntry(entry);
			}
			else if (keyword.equalsIgnoreCase("Infade"))
			{
				//entry.addModifier(new InfadeModifier(data));
				entry.setInFade(new InfadeModifier(data, entry));
			} 
			else if (keyword.equalsIgnoreCase("Outfade"))
			{
				//entry.addModifier(new OutfadeModifier(data));
				entry.setOutFade(new OutfadeModifier(data, entry));
			}
			else if (keyword.equalsIgnoreCase("xfade"))
			{
				//entry.addModifier(new XfadeModifier(data));
				entry.setCrossFade(new XfadeModifier(data, entry));
			}
			else if (keyword.equalsIgnoreCase("Alt"))
			{
				entry.addAltSrc(new AlternateSourceModifier(data, entry));
			}
			else if (keyword.equalsIgnoreCase("Rem"))
			{
				entry.addRemark(new RemarkType((String)data.elementAt(0), (String)data.elementAt(1)));
			}
			else if (keyword.equalsIgnoreCase("Gain"))
			{
				//entry.addModifier(new GainModifier(data));
				entry.addGain(new GainModifier(data, entry));
			}
			else if (keyword.equalsIgnoreCase("SILENCE"))
			{
				entry = new SilenceEditEntry(entryNo, (String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2),
					(String)data.elementAt(3), this);
				this.addEntry(entry);
			}
			else if (keyword.equalsIgnoreCase("AUX"))
			{
				entry = new AuxiliaryEditEntry(entryNo, (String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2),
					(String)data.elementAt(3), this);
				this.addEntry(entry);
			}
			else if (keyword.equalsIgnoreCase("Vid"))
			{
				entry = new VideoEditEntry(entryNo, (String)data.elementAt(0), (String)data.elementAt(1), (String)data.elementAt(2),
					(String)data.elementAt(3), (String)data.elementAt(4), (String)data.elementAt(5),  this);
				this.addEntry(entry);
			}
		}
		catch (Exception e)
		{
			this.addValidationError(e.toString());
					e.printStackTrace();
		}
	}
	

	
	public String toString ()
	{
		BaseEditEntry entry;
		String rval = "\n<EVENT_LIST>";
		
		Enumeration e = _entries.elements();
		while (e.hasMoreElements())
		{
			entry = (BaseEditEntry)e.nextElement();
			rval = rval.concat("\n\t(Entry)\t" + entry);
		}
		rval = rval.concat("\n</EVENT_LIST>");
		
		return rval;
	}
	
	public Element toXmlElement ()
	{
		BaseEditEntry entry;
		
		Element vs;
		Element e;
		vs = new Element("eventList");
		vs.setNamespace(ADLSection.aes);
		vs.setAttribute("id", "_" + UUID.randomUUID().toString());
		
		
		Enumeration en = _entries.elements();
		while (en.hasMoreElements())
		{
			entry = (BaseEditEntry)en.nextElement();
			
			e = new Element("eventEntry");
			e.setNamespace(ADLSection.aes);
			e.setAttribute("entryNumber", Long.toString(entry.getEntryNumber()));
			e.setAttribute("id", "_" + UUID.randomUUID().toString());
			e.addContent(entry.getXmlElements());
			vs.addContent(e);
		
		}
		
		return vs;
	}
	
	
	public void resample (double sr)
	{
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
				BaseEditEntry bei = (BaseEditEntry)e.nextElement();
				bei.resample(sr);
		}
	}
	
	public Object clone () throws CloneNotSupportedException
	{
		EventListSection rval = (EventListSection)super.clone();
		if (_entries != null)
		{
			rval._entries = (Vector)this._entries.clone();
		}
		
		if (entry != null)
		{
			rval.entry = (BaseEditEntry)this.entry.clone();
		}
		
		for (Enumeration e = _entries.elements(); e.hasMoreElements() ;) 
		{
				BaseEditEntry bei = (BaseEditEntry)e.nextElement();
				bei.setParent(this);
		}
		
		return rval;
	}
	
	public CutEditEntry findCutEditEntryInDestChannelForPosition(int channel, long position) {
		ArrayList<BaseEditEntry> destChannelEntries;
		CutEditEntry theCee = null;
		Iterator<BaseEditEntry> event_it;
		
		//System.out.println("findCutEditEntryInDestChannelForPosition(" + channel + "," + position + ")");
		
		destChannelEntries = getEventEntriesForDestChannel(channel);
		event_it = destChannelEntries.iterator();
		while (event_it.hasNext()) {
			BaseEditEntry bee;
			
			bee = event_it.next();
			if (bee.getClass().getName().equals("com.therockquarry.aes31.adl.CutEditEntry")) {
				CutEditEntry cee;
				long destIn, destOut;
				
				cee = (CutEditEntry)bee;
				destIn = cee.getDestIn().valueOf();
				destOut = cee.getDestOut().valueOf();
				
				// Does marker sample fall within dest time line of this cut entry
				if (position >= destIn && position <= destOut) {
					theCee = cee;
					break;
				}
			}
		}
		
		return theCee;
	}
	
	public ArrayList findCutsInDestChannelBetween(int channel, long start, long end) 
	{
		ArrayList cuts;
		ArrayList<BaseEditEntry> destChannelEntries;
		Iterator<BaseEditEntry> event_it;
		
		// System.out.println("findCutsInDestChannelBetween(" + channel + "," + start + "," + end + ")");
		cuts = new ArrayList();
		destChannelEntries = getEventEntriesForDestChannel(channel);
		event_it = destChannelEntries.iterator();
		while (event_it.hasNext()) {
			BaseEditEntry bee;
			
			bee = event_it.next();
			if (bee.getClass().getName().equals("com.therockquarry.aes31.adl.CutEditEntry")) {
				CutEditEntry cee;
				long destIn, destOut;
		
				cee = (CutEditEntry)bee;
				destIn = cee.getDestIn().valueOf();
				destOut = cee.getDestOut().valueOf();
				//System.out.println("Comparing di=" + destIn + " do=" + destOut);
				if ((destIn >= start && destIn < end) || (destOut <= end && destOut > start)) {
					cuts.add(cee);
					// System.out.println("Added di=" + destIn + " do=" + destOut);
				}
			}
		}
		return cuts;
	}
	
	public TreeSet<Integer> getDestChannels() {
		TreeSet<Integer> set;
		ArrayList cutEdits;
		Iterator<CutEditEntry> cit;
		
		set = new TreeSet<Integer>();
		cutEdits = getEventEntries(CUT_ENTRY);
		cit = cutEdits.iterator();
		while (cit.hasNext()) {
			CutEditEntry cee;
			Range r;
			
			cee = cit.next();
			r = cee.getDestChannels();
			for (int i = r.getBegin(); i <= r.getEnd(); i++) {
				set.add(new Integer(i));
			}
		}
		
		return set;
	}
}

//
//  AES31_3.java
//  AES31-3
//
//  Created by David Ackerman on 7/5/05.
//  Copyright (c) 2005 David Ackerman. All rights reserved.
//
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.text.*;

import com.therockquarry.aes31.adl.*;


//out of control test harness
public class AES31_3 {

    public static void main (String args[]) {
		EDMLParser p = null;
		try
		{
			p = new EDMLParser(args[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		ArrayList exceptions = new ArrayList();
		//com.therockquarry.aes31.adl.BwavParser wav = null;
		//long timestamp = 0;
		
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		
		System.out.println(System.getProperty("java.vendor"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(VersionSection.ADL_ID_SMPTE_LABEL);
		Long mytest = new Long((((400000L*60L)*60L)*24L));
		System.out.println(mytest);
		System.out.println(Long.MAX_VALUE);
		
		
		try {
			EDMLVersion edmlver = new EDMLVersion("03.03.00.00.04");
			EDMLVersion edmlver2 = (EDMLVersion)edmlver.clone();
			edmlver2.setMinorRelease(2);
			System.out.println("MY VERSION (2): " + edmlver2);
			System.out.println("MY VERSION: " + edmlver);
		}
		catch (Exception mye2)
		{
			mye2.printStackTrace();
		}
		
		
		ADLSection adl = null;
		ADLSection adl2 = null;
		try {
			System.out.println("Reading File: " + args[0]);
			adl = p.parse();
			
			if (adl != null)
			{
				//Date now = new Date();
				ProjectSection ps = adl.getProjectSection();
				System.out.println("FOUND: '" + ps.getProjNotes() + "'");
				SimpleDate sd = ps.getCreateDate();
				
				/*GregorianCalendar cal = sd.getAsGregorianCalendar();
				System.out.println("RETURNED CAL IS: " + cal);
				SimpleTimeZone stz = (SimpleTimeZone)cal.getTimeZone();
				System.out.println("RAW OFFSET: " + stz.getRawOffset());
				Date tst = cal.getTime();
				String pattern = "yyyy-MM-dd'T'HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String thedate = sdf.format(tst);
				System.out.println(thedate);
				*/
				Date tst = sd.getAsDate();
				String pattern = "yyyy-MM-dd'T'HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String thedate = sdf.format(tst);
				System.out.println("*****: " + thedate);
				
				try {
				TracklistSection tls = adl.getTracklistSection();
				tls.addTrackAtIndex("7", "MY TRACK 7");
				tls.addTrackAtIndex("6", "MY TRACK 6");
				tls.addTrackAtIndex("8", "MY TRACK 8");
				tls.addTrackAtIndex("5", "MY TRACK 5");
				
				
				tls.setTrackAtIndex(5, new TrackType("TESTING...123", tls));
				
				ArrayList<TrackType> tlist = tls.getTracks();
				System.out.println(tlist);
				
				tls.deleteTrackAtIndex(100);
				//TrackType mytt = tls.getTrackForIndex(-1);
				//System.out.println("*** "  + mytt.toString());
				
				} catch (Exception e11)
				{
					e11.printStackTrace();
				}
				
				
				
				/*try{
				SequenceSection seq = adl.getSequenceSection();
				seq.setSeqSampleRateFactor("4");
				} catch (Exception r)
				{
				
				}*/
			
			
			
				//SourceIndexSection srcIndex = adl.getSourceIndexSection();
				//EventListSection eventList = adl.getEventListSection();
				
				/*System.out.println("Retrieving Source Index");
				for (Enumeration e = srcIndex.getSourceEntries() ; e.hasMoreElements() ;)
				{
					FileSourceIndexEntry fsie = (FileSourceIndexEntry)e.nextElement();
					System.out.println("Processing index: " + fsie.getIndexNumber());
					
					System.out.println("Renaming: " + fsie.getPath() + " as backup copy");
					wav = new com.therockquarry.aes31.adl.BwavParser(fsie.getPath());
					System.out.println("Parsing Wave File");
					timestamp = wav.parse ();
					if (timestamp != 0)
					{
						timestamp++; //compensate for pyramix's incorrect calculation of the bwf timestamp
					}
					System.out.println("Wave files timestamp has been adjusted by: " + timestamp + "samples");
					System.out.println("Adjusting ADL SRC IN Edit entries to match");
					for (Enumeration f = eventList.getEventEntriesForSource(fsie.getIndexNumber()) ; f.hasMoreElements() ;)
					{
						CutEditEntry cei = (CutEditEntry)f.nextElement();
						cei.getSrcIn().adjustTime(timestamp);
					}
					
				}
				System.out.println("\n----------------\n\n\n\n" + adl);
				*/
				double sr = 96000;
				//adl.resample(sr);
				SourceIndexSection srcIndex = adl.getSourceIndexSection();
				SequenceSection myss = adl.getSequenceSection();
				TcfToken mytoken = myss.getSeqDestStart();
				TcfTokenFormatProperties ttfp = mytoken.getTcfTokenFormatProperties();
				
				TcfToken testconstr = new TcfToken(96002, 96000, ttfp);
				System.out.println("!!!!!!!!!!!!!!!!!: " + testconstr);
				
				ArrayList al = srcIndex.getSourceEntries();
				Iterator myi = al.iterator();
				while (myi.hasNext()) {
					System.out.println(myi.next());
				}
				
				//Iterator it;
				//it = srcIndex.getSourceEntries().iterator();
				//while (it.hasNext()) {
				//	FileSourceIndexEntry fsie = (FileSourceIndexEntry)it.next();
				//	String path = fsie.getPath();
				//	path = path.replaceAll("archival", "production");
				//	System.out.println("NEW PATH: " + path);
					//get timeinfo for each bwav
					//com.therockquarry.aes31.adl.BwavParser wav = new com.therockquarry.aes31.adl.BwavParser(path);
					
					//fsie.setPath("URL:file://localhost" + path);
				//	fsie.setPath(path);
					
					
					//long timestamp = wav.parse ();
					//TcfToken t = new TcfToken (timestamp, 30, false, 1, 1, sr);
					//fsie.setFileIn(t);
				//}
				
				EventListSection els = adl.getEventListSection();
				
				//test
				ArrayList cuts = els.getEventEntries(com.therockquarry.aes31.adl.EventListSection.CUT_ENTRY);
				CutEditEntry tt = (CutEditEntry)cuts.get(0);
				//System.out.println("TEST CUT MAP TO SOURCE: " + tt.getSourceIndexEntry());
				//test
				
				ArrayList t = els.getEventEntriesForDestChannelRange(new Range(1, 4));
				ListIterator li = t.listIterator();
				while (li.hasNext())
				{
					System.out.println((BaseEditEntry)li.next());
				}
				
				System.out.println(adl);
				adl.printAllErrors();
				
				/*try
					{
						File name = new File (args[0] + ".new.adl");
						
						FileWriter fw = new FileWriter(name);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(adl.toString(), 0, adl.toString().length());
						bw.close();
						System.out.println("Writing: " + name.toString());
						
						
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}*/
					
					File name = new File (args[0] + ".new.adl");
					adl.save(name);
					
					adl2 = (ADLSection)adl.clone();
					SystemSection ss = adl2.getSystemSection();
					ss.setSysGain("2");
					
					SourceIndexSection sis22 = adl2.getSourceIndexSection();
					SourceEntry se = sis22.getSourceEntryAtIndex(1);
					FileSourceIndexEntry fi = (FileSourceIndexEntry)se.getIndexEntries().get(0);
					fi.setAbsolutePath("file://localhost/mypath");
					//sis22.addSourceEntry(se);
					
					System.out.println("\n\n--------ADL1----------\n\n");

					System.out.println(adl);
					System.out.println("\n\n--------ADL2----------\n\n");
					System.out.println(adl2);
					
					System.out.println("\n\n--------ADL1----------\n\n");
					System.out.println(adl);
					
					MarkerListSection ms = new MarkerListSection();
					ArrayList<BaseMarkerPoint> marks = ms.getMarkers();
					marks.add(new SimpleMarker("2", "10|00|02.04*0011", "10|00|02.04*0014", "TWO", ms));
					marks.add(new SimpleMarker("1", "10|00|01.04*0011", "_", "ONE", ms));
					marks.add(new SimpleMarker("2", "10|00|02.04*0011", "_", "THREE", ms));
					marks.add(new SimpleMarker("3", "10|00|02.04*0011", "_", "FOUR", ms)); 
					Collections.sort(marks);
					for (BaseMarkerPoint bmp: marks)
					{
						System.out.println("**\t" + bmp);
					}
					
					EventListSection esl = adl.getEventListSection();
					els.sortEventListByTrackAndTime();
					
					System.out.println("\n\n--------ADL SORTED----------\n\n");
					System.out.println(adl);
					
					//els.sortEventListByTime();
					
					System.out.println("\n\n--------ADL SORTED----------\n\n");
					System.out.println(adl);
										
					

			}
		} catch (Exception e) {
			e.printStackTrace();
			//exceptions.add(e);
		}
		
		/*ListIterator li = exceptions.listIterator();
		while (li.hasNext())
		{
			System.out.println("#### " + ((Exception)li.next()).printStackTrace());
		}*/
    }
}

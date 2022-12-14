/**
	-------------------------------------------------------------------------------
	ADL.java
	AES31-3

	Created by David Ackerman on 10/16/06.

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

import java.io.*;
import java.net.*;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class ADL {
	EDMLParser parser;
	private File theFile = null;
	ADLSection adls;
	
	public ADL(String pathname) throws FileNotFoundException, IOException {
		theFile = new File(pathname);
		open("r");
		load();
	}
	
	public ADL(String pathname, String mode) throws FileNotFoundException, IOException {
		theFile = new File(pathname);
		open(mode);
		load();
	}
	
	public ADL(URI uri) throws FileNotFoundException, IOException {
		theFile = new File(uri);
		open("r");
		load();
	}
	
	public ADL(URI uri, String mode) throws FileNotFoundException, IOException {
		theFile = new File(uri);
		open(mode);
		load();
	}

	public ADL(File aFile) throws FileNotFoundException, IOException {
		theFile = aFile;
		open("r");
		load();
	}
	
	public ADL() {
		adls = new ADLSection();
	}

	public ADLSection getADLSection() {
		return adls;
	}
	
	public void setADLSection(ADLSection anADLSection) {
		adls = anADLSection;
	}
	
	public void setFile(File aFile) {
		theFile = aFile;
	}
	
	public File getFile() {
		return theFile;
	}
	
	public void setFileName(String aFileName) {
		theFile = new File(aFileName);
	}
	
	public String getFileName() {
		return theFile.getName();
	}
	
	public void open(String mode) throws FileNotFoundException, IOException {
		if (parser == null) {
			// parser = new EDMLParser(theFile, mode);
			parser = new EDMLParser(theFile);
		}
	}
	
	public void open() throws FileNotFoundException, IOException {
		open("rw");
	}
	
	public void close() throws IOException {
		if (parser != null) {
			parser.close();
			parser = null;
		}
	}
	
	public void save() {
		try {
			FileWriter fw = new FileWriter(theFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(adls.toString(), 0, adls.toString().length());
			bw.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveTo(File aFile) {
		try {
			FileWriter fw = new FileWriter(aFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(adls.toString(), 0, adls.toString().length());
			bw.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Document getXmlDocument ()
	{
		Document doc;
		
		doc = new Document();
		ProcessingInstruction pi = new ProcessingInstruction("xml-stylesheet","type='text/xsl' href='http://people.fas.harvard.edu/~dackerm/aes/aes31tohtml.xsl'");
		doc.addContent(pi);
		doc.setRootElement(adls.toXmlElement());
		return doc;
	}
	
	public void exportAsXml (File aFile)
	{
		try
		{
			XMLOutputter xo = new XMLOutputter();
			xo.setFormat(Format.getPrettyFormat());
			xo.output(getXmlDocument(), new FileWriter(aFile));
		}
		catch (Exception e)
		{
			System.err.println("An error occured while Exporting XML...");
			e.printStackTrace();
		}
		

	}
	
	public void load() {
		try {
			adls = parser.parse();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

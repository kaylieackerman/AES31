/*
	-------------------------------------------------------------------------------
	FlexibleTreeMap.java
	AES31-3

	Created on 7/24/06
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

/**
*	An extension of java.util.TreeMap that allows keys to be located in the list
*	and deleted based on the value of the mapped object. 
*/
public class FlexibleTreeMap<K, V> extends TreeMap<K, V> {
	
	/**
	*	Searches the map to locate all keys that contain a value matching the
	*	<code>value</code> parameter.
	*
	*	@param value The object to search for within this map.
	*	@return An Arraylist containing all of the keys within this map that contain
	*	the <code>value</code> object.
	*/	
	public ArrayList<K> keysForValue(Object value) {
		Iterator<Map.Entry<K, V>> it;
		ArrayList<K> al;
		
		al = new ArrayList<K>();
		it = entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<K, V> me;
			
			me = it.next();
			if (me.getValue() == value) {
				al.add(me.getKey());
			}
		}
		return al;
	}
	
	/**
	*	Removes all keys in this map that contain the <code>value</code> object.
	*
	*	@param value The object to search for within this map to determine which 
	*	keys should be removed.
	*/
	public void removeKeysForValue(Object value) {
		Iterator it;
		
		it = keysForValue(value).iterator();
		while (it.hasNext()) {
			Object key;
			
			key = it.next();
			remove(key);
		}
	}
}

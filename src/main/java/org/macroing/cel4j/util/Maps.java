/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.cel4j.
 * 
 * org.macroing.cel4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.cel4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.cel4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.cel4j.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A class that consists exclusively of static methods that performs various operations on {@code Map}s.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Maps {
	private Maps() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Map} that is a value-sorted version of {@code map}.
	 * <p>
	 * If {@code map} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param <K> the generic key type of the {@code Map}
	 * @param <V> the generic value type of the {@code Map}
	 * @param map a {@code Map}
	 * @return a new {@code Map} that is a value-sorted version of {@code map}
	 * @throws NullPointerException thrown if, and only if, {@code map} is {@code null}
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
		return map.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue())).collect(Collectors.<Entry<K, V>, K, V>toMap(Entry::getKey, Entry::getValue));
	}
}
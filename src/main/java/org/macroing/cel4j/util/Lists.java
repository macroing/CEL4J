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

import java.util.ArrayList;
import java.util.List;

/**
 * A class that consists exclusively of static methods that performs various operations on {@code List}s.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Lists {
	private Lists() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code List} with {@code object} and all {@code Object}s in {@code objects} in it.
	 * <p>
	 * This method does not check whether {@code object} or an {@code Object} in {@code objects} are {@code null}.
	 * 
	 * @param <T> the generic type of the returned {@code List}
	 * @param object the mandatory {@code Object} to add
	 * @param objects the optional {@code Object}s to add
	 * @return a new {@code List} with {@code object} and all {@code Object}s in {@code objects} in it
	 */
	@SafeVarargs
	public static <T> List<T> toList(final T object, final T... objects) {
		final
		List<T> list = new ArrayList<>();
		list.add(object);
		
		for(int i = 0; i < objects.length; i++) {
			list.add(objects[i]);
		}
		
		return list;
	}
}
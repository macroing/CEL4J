/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

import java.util.List;
import java.util.Objects;

/**
 * A class that consists exclusively of static methods that checks parameter argument validity.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ParameterArguments {
	private ParameterArguments() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks that {@code list} and all of its elements are not {@code null}.
	 * <p>
	 * Returns {@code list}.
	 * <p>
	 * If either {@code list}, an element in {@code list} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param <T> the generic type of {@code list}
	 * @param list the {@code List} to check
	 * @param name the name of the parameter argument used for {@code list}, that will be part of the message for the {@code NullPointerException}
	 * @return {@code list}
	 * @throws NullPointerException thrown if, and only if, either {@code list}, an element in {@code list} or {@code name} are {@code null}
	 */
	public static <T> List<T> requireNonNullList(final List<T> list, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(list, String.format("%s == null", name));
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				throw new NullPointerException(String.format("%s.get(%s) == null", name, Integer.toString(i)));
			}
		}
		
		return list;
	}
	
	/**
	 * Checks that {@code objects} and all of its elements are not {@code null}.
	 * <p>
	 * Returns {@code objects}.
	 * <p>
	 * If either {@code objects}, an element in {@code objects} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param <T> the generic type of {@code objects}
	 * @param objects the array to check
	 * @param name the name of the parameter argument used for {@code objects}, that will be part of the message for the {@code NullPointerException}
	 * @return {@code objects}
	 * @throws NullPointerException thrown if, and only if, either {@code objects}, an element in {@code objects} or {@code name} are {@code null}
	 */
	public static <T> T[] requireNonNullArray(final T[] objects, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(objects, String.format("%s == null", name));
		
		for(int i = 0; i < objects.length; i++) {
			Objects.requireNonNull(objects[i], String.format("%s[%s] == null", name, Integer.toString(i)));
		}
		
		return objects;
	}
	
	/**
	 * Checks that the specified value is within a given closed range, such as [minimum, maximum].
	 * <p>
	 * Returns the value itself.
	 * <p>
	 * If {@code value} is less than {@code minimum} or greater than {@code maximum}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * This method is implemented in terms of {@link ParameterArguments#requireRange(int, int, int, String)}, with a name of {@code "value"}.
	 * 
	 * @param value the value to check
	 * @param minimum the minimum (inclusive) bound of the range
	 * @param maximum the maximum (inclusive) bound of the range
	 * @return the value itself
	 * @throws IllegalArgumentException thrown if, and only if, {@code value} is less than {@code minimum} or greater than {@code maximum}
	 */
	public static int requireRange(final int value, final int minimum, final int maximum) {
		return requireRange(value, minimum, maximum, "value");
	}
	
	/**
	 * Checks that the specified value is within a given closed range, such as [minimum, maximum].
	 * <p>
	 * Returns the value itself.
	 * <p>
	 * If {@code value} is less than {@code minimum} or greater than {@code maximum}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the value to check
	 * @param minimum the minimum (inclusive) bound of the range
	 * @param maximum the maximum (inclusive) bound of the range
	 * @param name the name of the value, that will be part of the message to the {@code IllegalArgumentException}, if thrown
	 * @return the value itself
	 * @throws IllegalArgumentException thrown if, and only if, {@code value} is less than {@code minimum} or greater than {@code maximum}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public static int requireRange(final int value, final int minimum, final int maximum, final String name) {
		Objects.requireNonNull(name, "name == null");
		
		if(value < minimum) {
			throw new IllegalArgumentException(String.format("%s < %s", name, Integer.toString(minimum)));
		} else if(value > maximum) {
			throw new IllegalArgumentException(String.format("%s > %s", name, Integer.toString(maximum)));
		}
		
		return value;
	}
	
	/**
	 * Returns {@code array}, but only if its elements are within the range of {@code minimum} (inclusive) and {@code maximum} (inclusive).
	 * <p>
	 * If one or more of its elements are not within said range, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param array the array with elements to verify
	 * @param minimum the minimum value allowed (inclusive)
	 * @param maximum the maximum value allowed (inclusive)
	 * @return {@code array}, but only if its elements are within the range of {@code minimum} (inclusive) and {@code maximum} (inclusive)
	 * @throws IllegalArgumentException thrown if, and only if, {@code array} contains an element that is less than {@code minimum} or greater than {@code maximum}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static int[] requireRange(final int[] array, final int minimum, final int maximum) {
		for(final int element : array) {
			requireRange(element, minimum, maximum);
		}
		
		return array;
	}
}
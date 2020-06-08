/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;

/**
 * An {@code AttributeInfoReader} is used for reading sequences of bytes into instances of the {@link AttributeInfo} interface.
 * <p>
 * This interface is implemented and used internally by the API. But it is exposed to the public in order to act as a Service Provider Interface (SPI). This allows the user of this API to implement their own {@code AttributeInfo} and
 * {@code AttributeInfoReader} implementations. It is even possible to override existing implementations, but it is not recommended.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface AttributeInfoReader {
	/**
	 * Reads the byte sequence provided by {@code dataInput} into an {@link AttributeInfo} instance.
	 * <p>
	 * Returns an {@code AttributeInfo} instance.
	 * <p>
	 * If either {@code dataInput}, {@code constantPool} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If the {@code AttributeInfo} cannot be read, an {@code AttributeInfoReaderException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} to read an {@code AttributeInfo} instance from
	 * @param attributeNameIndex the attribute_name_index of the {@code AttributeInfo}
	 * @param constantPool a {@code List} of {@link CPInfo} instances that represents the constant_pool table
	 * @return an {@code AttributeInfo} instance
	 * @throws AttributeInfoReaderException thrown if, and only if, the {@code AttributeInfo} cannot be read
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 * @throws NullPointerException thrown if, and only if, either {@code dataInput}, {@code constantPool} or any of its elements are {@code null}
	 */
	AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool);
	
	/**
	 * Returns {@code true} if, and only if, this {@code AttributeInfoReader} supports reading {@link AttributeInfo} instances with a name of {@code name}, {@code false} otherwise.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name of the {@code AttributeInfo} instance to read
	 * @return {@code true} if, and only if, this {@code AttributeInfoReader} supports reading {@code AttributeInfo} instances with a name of {@code name}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	boolean isSupported(final String name);
}
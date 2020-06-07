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
import org.macroing.cel4j.node.NodeFormatException;

/**
 * A means to read {@link AttributeInfo}s.
 * <p>
 * It's also used as a Service Provider Interface (SPI) for the same reason.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface AttributeInfoReader {
	/**
	 * Returns an {@link AttributeInfo} based on the data provided by {@code dataInput}.
	 * <p>
	 * If either {@code dataInput}, {@code constantPool} or any entries in {@code constantPool} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If the {@code AttributeInfo} cannot be read, a {@code NodeFormatException} should be thrown.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} may be thrown. But no guarantees can be made.
	 * 
	 * @param dataInput the {@code DataInput} to read the data from
	 * @param attributeNameIndex the attribute_name_index of the {@code AttributeInfo} to read
	 * @param constantPool a {@code List} of {@link CPInfo}s that represents the constant_pool table
	 * @return an {@code AttributeInfo} based on the data provided by {@code dataInput}
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 * @throws NodeFormatException thrown if, and only if, the {@code AttributeInfo} cannot be read
	 * @throws NullPointerException thrown if, and only if, either {@code dataInput}, {@code constantPool} or any entries in {@code constantPool} are {@code null}
	 */
	AttributeInfo readAttributeInfo(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool);
	
	/**
	 * Returns {@code true} if, and only if, this {@code AttributeInfoReader} supports reading an {@link AttributeInfo} given a {@code String} with its name, {@code false} otherwise.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param name the {@code String} with the name for the {@code AttributeInfo} we are eventually about to read
	 * @return {@code true} if, and only if, this {@code AttributeInfoReader} supports reading an {@code AttributeInfo} given a {@code String} with its name, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	boolean isAttributeInfoReadingSupportedFor(final String name);
}
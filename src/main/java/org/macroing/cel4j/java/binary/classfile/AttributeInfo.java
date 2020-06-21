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
package org.macroing.cel4j.java.binary.classfile;

import java.io.DataOutput;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code AttributeInfo} represents an {@code attribute_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code attribute_info} structure has the following format:
 * <pre>
 * <code>
 * attribute_info {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1[attribute_length] info;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class AttributeInfo implements Node {
	private final String name;
	private int attributeNameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AttributeInfo} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param name the name associated with this {@code AttributeInfo} instance
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code AttributeInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	protected AttributeInfo(final String name, final int attributeNameIndex) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.attributeNameIndex = ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code AttributeInfo} instance.
	 * 
	 * @return a copy of this {@code AttributeInfo} instance
	 */
	public abstract AttributeInfo copy();
	
	/**
	 * Returns the name associated with this {@code AttributeInfo} instance.
	 * 
	 * @return the name associated with this {@code AttributeInfo} instance
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code AttributeInfo} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code AttributeInfo} instance
	 */
	public abstract int getAttributeLength();
	
	/**
	 * Returns the value of the {@code attribute_name_index} item associated with this {@code AttributeInfo} instance.
	 * 
	 * @return the value of the {@code attribute_name_index} item associated with this {@code AttributeInfo} instance
	 */
	public final int getAttributeNameIndex() {
		return this.attributeNameIndex;
	}
	
	/**
	 * Sets {@code attributeNameIndex} as the value for the {@code attribute_name_index} item associated with this {@code AttributeInfo} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code AttributeInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public final void setAttributeNameIndex(final int attributeNameIndex) {
		this.attributeNameIndex = ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code AttributeInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public abstract void write(final DataOutput dataOutput);
}
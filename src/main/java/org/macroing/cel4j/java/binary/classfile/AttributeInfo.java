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
 * An {@code AttributeInfo} denotes an attribute_info structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
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
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param name the name of this {@code AttributeInfo} instance
	 * @param attributeNameIndex the attribute_name_index of this {@code AttributeInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
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
	 * Returns the name of this {@code AttributeInfo} instance.
	 * 
	 * @return the name of this {@code AttributeInfo} instance
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the attribute_length of this {@code AttributeInfo} instance.
	 * 
	 * @return the attribute_length of this {@code AttributeInfo} instance
	 */
	public abstract int getAttributeLength();
	
	/**
	 * Returns the attribute_name_index of this {@code AttributeInfo} instance.
	 * 
	 * @return the attribute_name_index of this {@code AttributeInfo} instance
	 */
	public final int getAttributeNameIndex() {
		return this.attributeNameIndex;
	}
	
	/**
	 * Sets the attribute_name_index for this {@code AttributeInfo} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the new attribute_name_index
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public final void setAttributeNameIndex(final int attributeNameIndex) {
		this.attributeNameIndex = ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code AttributeInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} may be thrown. But no guarantees can be made.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	public abstract void write(final DataOutput dataOutput);
}
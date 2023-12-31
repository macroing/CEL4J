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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.UncheckedIOException;

import org.macroing.cel4j.node.Node;

/**
 * A {@code StackMapFrame} represents a {@code stack_map_frame} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * A {@code stack_map_frame} {@code union} is part of the {@code StackMapTable_attribute} structure.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface StackMapFrame extends Node {
	/**
	 * Returns a copy of this {@code StackMapFrame} instance.
	 * 
	 * @return a copy of this {@code StackMapFrame} instance
	 */
	StackMapFrame copy();
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code StackMapFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code StackMapFrame} instance
	 */
	int getFrameType();
	
	/**
	 * Returns the length of this {@code StackMapFrame} instance.
	 * 
	 * @return the length of this {@code StackMapFrame} instance
	 */
	int getLength();
	
	/**
	 * Writes this {@code StackMapFrame} to {@code dataOutput}.
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
	void write(final DataOutput dataOutput);
}
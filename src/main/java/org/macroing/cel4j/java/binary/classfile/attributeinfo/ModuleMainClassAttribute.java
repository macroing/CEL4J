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
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ModuleMainClassAttribute} represents a {@code ModuleMainClass_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code ModuleMainClass_attribute} structure has the following format:
 * <pre>
 * <code>
 * ModuleMainClass_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 main_class_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ModuleMainClassAttribute extends AttributeInfo {
	/**
	 * The name of the {@code ModuleMainClass_attribute} structure.
	 */
	public static final String NAME = "ModuleMainClass";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int mainClassIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ModuleMainClassAttribute} instance that is a copy of {@code moduleMainClassAttribute}.
	 * <p>
	 * If {@code moduleMainClassAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param moduleMainClassAttribute the {@code ModuleMainClassAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code moduleMainClassAttribute} is {@code null}
	 */
	public ModuleMainClassAttribute(final ModuleMainClassAttribute moduleMainClassAttribute) {
		super(NAME, moduleMainClassAttribute.getAttributeNameIndex());
		
		this.mainClassIndex = moduleMainClassAttribute.mainClassIndex;
	}
	
	/**
	 * Constructs a new {@code ModuleMainClassAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code mainClassIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code ModuleMainClassAttribute} instance
	 * @param mainClassIndex the value for the {@code main_class_index} item associated with this {@code ModuleMainClassAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code mainClassIndex} are less than {@code 1}
	 */
	public ModuleMainClassAttribute(final int attributeNameIndex, final int mainClassIndex) {
		super(NAME, attributeNameIndex);
		
		this.mainClassIndex = ParameterArguments.requireRange(mainClassIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ModuleMainClassAttribute} instance.
	 * 
	 * @return a copy of this {@code ModuleMainClassAttribute} instance
	 */
	@Override
	public ModuleMainClassAttribute copy() {
		return new ModuleMainClassAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ModuleMainClassAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code ModuleMainClassAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new ModuleMainClassAttribute(%s, %s)", Integer.toString(getAttributeNameIndex()), Integer.toString(getMainClassIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ModuleMainClassAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ModuleMainClassAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ModuleMainClassAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ModuleMainClassAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ModuleMainClassAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), ModuleMainClassAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != ModuleMainClassAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != ModuleMainClassAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getMainClassIndex() != ModuleMainClassAttribute.class.cast(object).getMainClassIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code ModuleMainClassAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code ModuleMainClassAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the value of the {@code main_class_index} item associated with this {@code ModuleMainClassAttribute} instance.
	 * 
	 * @return the value of the {@code main_class_index} item associated with this {@code ModuleMainClassAttribute} instance
	 */
	public int getMainClassIndex() {
		return this.mainClassIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ModuleMainClassAttribute} instance.
	 * 
	 * @return a hash code for this {@code ModuleMainClassAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getMainClassIndex()));
	}
	
	/**
	 * Sets {@code mainClassIndex} as the value for the {@code main_class_index} item associated with this {@code ModuleMainClassAttribute} instance.
	 * <p>
	 * If {@code mainClassIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param mainClassIndex the value for the {@code main_class_index} item associated with this {@code ModuleMainClassAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code mainClassIndex} is less than {@code 1}
	 */
	public void setMainClassIndex(final int mainClassIndex) {
		this.mainClassIndex = ParameterArguments.requireRange(mainClassIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ModuleMainClassAttribute} to {@code dataOutput}.
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
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getMainClassIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ModuleMainClassAttribute} instances in {@code node}.
	 * <p>
	 * All {@code ModuleMainClassAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ModuleMainClassAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ModuleMainClassAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ModuleMainClassAttribute.class);
	}
}
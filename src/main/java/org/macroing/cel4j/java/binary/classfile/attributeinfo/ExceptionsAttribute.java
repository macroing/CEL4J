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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code ExceptionsAttribute} denotes an Exceptions_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ExceptionsAttribute extends AttributeInfo {
	/**
	 * The name of the Exceptions_attribute structure.
	 */
	public static final String NAME = "Exceptions";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Integer> exceptionIndexTable = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code ExceptionsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public ExceptionsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a copy of this {@code ExceptionsAttribute} instance
	 */
	@Override
	public ExceptionsAttribute copy() {
		final ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute(getAttributeNameIndex());
		
		this.exceptionIndexTable.forEach(exceptionIndex -> exceptionsAttribute.addExceptionIndex(exceptionIndex.intValue()));
		
		return exceptionsAttribute;
	}
	
	/**
	 * Returns the exception_index_table of this {@code ExceptionsAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ExceptionsAttribute} instance.
	 * 
	 * @return the exception_index_table of this {@code ExceptionsAttribute} instance
	 */
	public List<Integer> getExceptionIndexTable() {
		return new ArrayList<>(this.exceptionIndexTable);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code ExceptionsAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Exceptions_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("number_of_exceptions=" + getNumberOfExceptions());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ExceptionsAttribute}, and that {@code ExceptionsAttribute} instance is equal to this {@code ExceptionsAttribute} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ExceptionsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ExceptionsAttribute}, and that {@code ExceptionsAttribute} instance is equal to this {@code ExceptionsAttribute} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ExceptionsAttribute)) {
			return false;
		} else if(!Objects.equals(ExceptionsAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(ExceptionsAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(ExceptionsAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(ExceptionsAttribute.class.cast(object).getNumberOfExceptions() != getNumberOfExceptions()) {
			return false;
		} else if(!Objects.equals(ExceptionsAttribute.class.cast(object).exceptionIndexTable, this.exceptionIndexTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code ExceptionsAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code ExceptionsAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getNumberOfExceptions() * 2;
		
		return attributeLength;
	}
	
	/**
	 * Returns the number_of_exceptions of this {@code ExceptionsAttribute} instance.
	 * 
	 * @return the number_of_exceptions of this {@code ExceptionsAttribute} instance
	 */
	public int getNumberOfExceptions() {
		return this.exceptionIndexTable.size();
	}
	
	/**
	 * Returns a hash code for this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a hash code for this {@code ExceptionsAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumberOfExceptions()), this.exceptionIndexTable);
	}
	
	/**
	 * Adds {@code exceptionIndex} to the exception_index_table of this {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code exceptionIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param exceptionIndex the exception index to add
	 * @throws IllegalArgumentException thrown if, and only if, {@code exceptionIndex} is less than or equal to {@code 0}
	 */
	public void addExceptionIndex(final int exceptionIndex) {
		this.exceptionIndexTable.add(Integer.valueOf(ParameterArguments.requireRange(exceptionIndex, 1, Integer.MAX_VALUE)));
	}
	
	/**
	 * Removes {@code exceptionIndex} from the exception_index_table of this {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code exceptionIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param exceptionIndex the exception index to remove
	 * @throws IllegalArgumentException thrown if, and only if, {@code exceptionIndex} is less than or equal to {@code 0}
	 */
	public void removeExceptionIndex(final int exceptionIndex) {
		this.exceptionIndexTable.remove(Integer.valueOf(ParameterArguments.requireRange(exceptionIndex, 1, Integer.MAX_VALUE)));
	}
	
	/**
	 * Writes this {@code ExceptionsAttribute} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getNumberOfExceptions());
			
			for(final int exceptionIndex : this.exceptionIndexTable) {
				dataOutput.writeShort(exceptionIndex);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ExceptionsAttribute}s.
	 * <p>
	 * All {@code ExceptionsAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ExceptionsAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ExceptionsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ExceptionsAttribute.class);
	}
}
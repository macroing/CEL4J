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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code ExceptionsAttribute} represents an {@code Exceptions_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code Exceptions_attribute} structure has the following format:
 * <pre>
 * <code>
 * Exceptions_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_exceptions;
 *     u2[number_of_exceptions] exception_index_table;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ExceptionsAttribute extends AttributeInfo {
	/**
	 * The name of the {@code Exceptions_attribute} structure.
	 */
	public static final String NAME = "Exceptions";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Integer> exceptionIndexTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ExceptionsAttribute} instance that is a copy of {@code exceptionsAttribute}.
	 * <p>
	 * If {@code exceptionsAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param exceptionsAttribute the {@code ExceptionsAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code exceptionsAttribute} is {@code null}
	 */
	public ExceptionsAttribute(final ExceptionsAttribute exceptionsAttribute) {
		super(NAME, exceptionsAttribute.getAttributeNameIndex());
		
		this.exceptionIndexTable = exceptionsAttribute.exceptionIndexTable.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code ExceptionsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public ExceptionsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.exceptionIndexTable = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a copy of this {@code ExceptionsAttribute} instance
	 */
	@Override
	public ExceptionsAttribute copy() {
		return new ExceptionsAttribute(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code exception_index_table} item associated with this {@code ExceptionsAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a {@code List} that represents the {@code exception_index_table} item associated with this {@code ExceptionsAttribute} instance
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
		return String.format("new ExceptionsAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ExceptionsAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ExceptionsAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ExceptionsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ExceptionsAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ExceptionsAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), ExceptionsAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != ExceptionsAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != ExceptionsAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumberOfExceptions() != ExceptionsAttribute.class.cast(object).getNumberOfExceptions()) {
			return false;
		} else if(!Objects.equals(this.exceptionIndexTable, ExceptionsAttribute.class.cast(object).exceptionIndexTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code ExceptionsAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code ExceptionsAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getNumberOfExceptions() * 2;
	}
	
	/**
	 * Returns the value of the {@code number_of_exceptions} item associated with this {@code ExceptionsAttribute} instance.
	 * 
	 * @return the value of the {@code number_of_exceptions} item associated with this {@code ExceptionsAttribute} instance
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
	 * Adds {@code exceptionIndex} to the {@code exception_index_table} item associated with this {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code exceptionIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param exceptionIndex the exception index to add
	 * @throws IllegalArgumentException thrown if, and only if, {@code exceptionIndex} is less than {@code 1}
	 */
	public void addExceptionIndex(final int exceptionIndex) {
		this.exceptionIndexTable.add(Integer.valueOf(ParameterArguments.requireRange(exceptionIndex, 1, Integer.MAX_VALUE, "exceptionIndex")));
	}
	
	/**
	 * Removes {@code exceptionIndex} from the {@code exception_index_table} item associated with this {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code exceptionIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param exceptionIndex the exception index to remove
	 * @throws IllegalArgumentException thrown if, and only if, {@code exceptionIndex} is less than {@code 1}
	 */
	public void removeExceptionIndex(final int exceptionIndex) {
		this.exceptionIndexTable.remove(Integer.valueOf(ParameterArguments.requireRange(exceptionIndex, 1, Integer.MAX_VALUE, "exceptionIndex")));
	}
	
	/**
	 * Writes this {@code ExceptionsAttribute} to {@code dataOutput}.
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
	 * Returns a {@code List} with all {@code ExceptionsAttribute} instances in {@code node}.
	 * <p>
	 * All {@code ExceptionsAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ExceptionsAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ExceptionsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ExceptionsAttribute.class);
	}
	
	/**
	 * Attempts to find an {@code ExceptionsAttribute} instance in {@code methodInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code ExceptionsAttribute} instance.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check in
	 * @return an {@code Optional} with the optional {@code ExceptionsAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public static Optional<ExceptionsAttribute> find(final MethodInfo methodInfo) {
		return methodInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof ExceptionsAttribute).map(attributeInfo -> ExceptionsAttribute.class.cast(attributeInfo)).findFirst();
	}
}
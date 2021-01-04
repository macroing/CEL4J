/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code LocalVariableTableAttribute} represents a {@code LocalVariableTable_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code LocalVariableTable_attribute} structure has the following format:
 * <pre>
 * <code>
 * LocalVariableTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_table_length;
 *     local_variable[local_variable_table_length] local_variable_table;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariableTableAttribute extends AttributeInfo {
	/**
	 * The name of the {@code LocalVariableTable_attribute} structure.
	 */
	public static final String NAME = "LocalVariableTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LocalVariable> localVariableTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LocalVariableTableAttribute} instance that is a copy of {@code localVariableTableAttribute}.
	 * <p>
	 * If {@code localVariableTableAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariableTableAttribute the {@code LocalVariableTableAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code localVariableTableAttribute} is {@code null}
	 */
	public LocalVariableTableAttribute(final LocalVariableTableAttribute localVariableTableAttribute) {
		super(NAME, localVariableTableAttribute.getAttributeNameIndex());
		
		this.localVariableTable = localVariableTableAttribute.localVariableTable.stream().map(localVariable -> localVariable.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code LocalVariableTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code LocalVariableTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public LocalVariableTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.localVariableTable = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link LocalVariable} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code LocalVariable} instances
	 */
	public List<LocalVariable> getLocalVariableTable() {
		return new ArrayList<>(this.localVariableTable);
	}
	
	/**
	 * Returns a copy of this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a copy of this {@code LocalVariableTableAttribute} instance
	 */
	@Override
	public LocalVariableTableAttribute copy() {
		return new LocalVariableTableAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariableTableAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new LocalVariableTableAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node} instances, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final LocalVariable localVariable : this.localVariableTable) {
					if(!localVariable.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code LocalVariableTableAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTableAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LocalVariableTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTableAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariableTableAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), LocalVariableTableAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != LocalVariableTableAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != LocalVariableTableAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getLocalVariableTableLength() != LocalVariableTableAttribute.class.cast(object).getLocalVariableTableLength()) {
			return false;
		} else if(!Objects.equals(this.localVariableTable, LocalVariableTableAttribute.class.cast(object).localVariableTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code LocalVariableTableAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getLocalVariableTableLength() * 10;
	}
	
	/**
	 * Returns the value of the {@code local_variable_table_length} item associated with this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return the value of the {@code local_variable_table_length} item associated with this {@code LocalVariableTableAttribute} instance
	 */
	public int getLocalVariableTableLength() {
		return this.localVariableTable.size();
	}
	
	/**
	 * Returns a hash code for this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a hash code for this {@code LocalVariableTableAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getLocalVariableTableLength()), this.localVariableTable);
	}
	
	/**
	 * Adds {@code localVariable} to this {@code LocalVariableTableAttribute} instance.
	 * <p>
	 * If {@code localVariable} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariable the {@link LocalVariable} to add
	 * @throws NullPointerException thrown if, and only if, {@code localVariable} is {@code null}
	 */
	public void addLocalVariable(final LocalVariable localVariable) {
		this.localVariableTable.add(Objects.requireNonNull(localVariable, "localVariable == null"));
	}
	
	/**
	 * Removes {@code localVariable} from this {@code LocalVariableTableAttribute} instance.
	 * <p>
	 * If {@code localVariable} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariable the {@link LocalVariable} to remove
	 * @throws NullPointerException thrown if, and only if, {@code localVariable} is {@code null}
	 */
	public void removeLocalVariable(final LocalVariable localVariable) {
		this.localVariableTable.remove(Objects.requireNonNull(localVariable, "localVariable == null"));
	}
	
	/**
	 * Writes this {@code LocalVariableTableAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getLocalVariableTableLength());
			
			for(final LocalVariable localVariable : this.localVariableTable) {
				localVariable.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code LocalVariableTableAttribute} instances in {@code node}.
	 * <p>
	 * All {@code LocalVariableTableAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code LocalVariableTableAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<LocalVariableTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), LocalVariableTableAttribute.class);
	}
}
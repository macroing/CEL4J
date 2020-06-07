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
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code LocalVariableTableAttribute} denotes a LocalVariableTable_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariableTableAttribute extends AttributeInfo {
	/**
	 * The name of the LocalVariableTable_attribute structure.
	 */
	public static final String NAME = "LocalVariableTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LocalVariable> localVariableTable = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private LocalVariableTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@code LocalVariable}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code LocalVariable}s
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
		final LocalVariableTableAttribute localVariableTableAttribute = new LocalVariableTableAttribute(getAttributeNameIndex());
		
		this.localVariableTable.forEach(localVariable -> localVariableTableAttribute.addLocalVariable(localVariable.copy()));
		
		return localVariableTableAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariableTableAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("LocalVariableTable_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("local_variable_table_length=" + getLocalVariableTableLength());
		
		final String toString = stringBuilder.toString();
		
		return toString;
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
	 * <li>traverse its child {@code Node}s, if it has any.</li>
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTableAttribute}, and that {@code LocalVariableTableAttribute} instance is equal to this {@code LocalVariableTableAttribute}
	 * instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LocalVariableTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTableAttribute}, and that {@code LocalVariableTableAttribute} instance is equal to this {@code LocalVariableTableAttribute}
	 * instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariableTableAttribute)) {
			return false;
		} else if(!Objects.equals(LocalVariableTableAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(LocalVariableTableAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(LocalVariableTableAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(LocalVariableTableAttribute.class.cast(object).getLocalVariableTableLength() != getLocalVariableTableLength()) {
			return false;
		} else if(!Objects.equals(LocalVariableTableAttribute.class.cast(object).localVariableTable, this.localVariableTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code LocalVariableTableAttribute} instance.
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getLocalVariableTableLength() * 10;
		
		return attributeLength;
	}
	
	/**
	 * Returns the local_variable_table_length of this {@code LocalVariableTableAttribute} instance.
	 * 
	 * @return the local_variable_table_length of this {@code LocalVariableTableAttribute} instance.
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
	 */
	public void removeLocalVariable(final LocalVariable localVariable) {
		this.localVariableTable.remove(Objects.requireNonNull(localVariable, "localVariable == null"));
	}
	
	/**
	 * Writes this {@code LocalVariableTableAttribute} to {@code dataOutput}.
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
	 * Returns a {@code List} with all {@code LocalVariableTableAttribute}s.
	 * <p>
	 * All {@code LocalVariableTableAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code LocalVariableTableAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<LocalVariableTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), LocalVariableTableAttribute.class);
	}
	
	/**
	 * Returns a new {@code LocalVariableTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code LocalVariableTableAttribute} instance
	 * @return a new {@code LocalVariableTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public static LocalVariableTableAttribute newInstance(final int attributeNameIndex) {
		return new LocalVariableTableAttribute(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE));
	}
}
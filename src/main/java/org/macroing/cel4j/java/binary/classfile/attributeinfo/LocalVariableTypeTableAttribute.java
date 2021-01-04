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
 * A {@code LocalVariableTypeTableAttribute} represents a {@code LocalVariableTypeTable_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code LocalVariableTypeTable_attribute} structure has the following format:
 * <pre>
 * <code>
 * LocalVariableTypeTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_type_table_length;
 *     local_variable_type[local_variable_type_table_length] local_variable_type_table;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariableTypeTableAttribute extends AttributeInfo {
	/**
	 * The name of the {@code LocalVariableTypeTable_attribute} structure.
	 */
	public static final String NAME = "LocalVariableTypeTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LocalVariableType> localVariableTypeTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LocalVariableTypeTableAttribute} instance that is a copy of {@code localVariableTypeTableAttribute}.
	 * <p>
	 * If {@code localVariableTypeTableAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariableTypeTableAttribute the {@code LocalVariableTypeTableAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code localVariableTypeTableAttribute} is {@code null}
	 */
	public LocalVariableTypeTableAttribute(final LocalVariableTypeTableAttribute localVariableTypeTableAttribute) {
		super(NAME, localVariableTypeTableAttribute.getAttributeNameIndex());
		
		this.localVariableTypeTable = localVariableTypeTableAttribute.localVariableTypeTable.stream().map(localVariableType -> localVariableType.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code LocalVariableTypeTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code LocalVariableTypeTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public LocalVariableTypeTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.localVariableTypeTable = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link LocalVariableType} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code LocalVariableType} instances
	 */
	public List<LocalVariableType> getLocalVariableTypeTable() {
		return new ArrayList<>(this.localVariableTypeTable);
	}
	
	/**
	 * Returns a copy of this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a copy of this {@code LocalVariableTypeTableAttribute} instance
	 */
	@Override
	public LocalVariableTypeTableAttribute copy() {
		return new LocalVariableTypeTableAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariableTypeTableAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new LocalVariableTypeTableAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
				for(final LocalVariableType localVariableType : this.localVariableTypeTable) {
					if(!localVariableType.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code LocalVariableTypeTableAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTypeTableAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LocalVariableTypeTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTypeTableAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariableTypeTableAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), LocalVariableTypeTableAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != LocalVariableTypeTableAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != LocalVariableTypeTableAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getLocalVariableTypeTableLength() != LocalVariableTypeTableAttribute.class.cast(object).getLocalVariableTypeTableLength()) {
			return false;
		} else if(!Objects.equals(this.localVariableTypeTable, LocalVariableTypeTableAttribute.class.cast(object).localVariableTypeTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code LocalVariableTypeTableAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getLocalVariableTypeTableLength() * 10;
	}
	
	/**
	 * Returns the value of the {@code local_variable_type_table_length} item associated with this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return the value of the {@code local_variable_type_table_length} item associated with this {@code LocalVariableTypeTableAttribute} instance
	 */
	public int getLocalVariableTypeTableLength() {
		return this.localVariableTypeTable.size();
	}
	
	/**
	 * Returns a hash code for this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a hash code for this {@code LocalVariableTypeTableAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getLocalVariableTypeTableLength()), this.localVariableTypeTable);
	}
	
	/**
	 * Adds {@code localVariableType} to this {@code LocalVariableTypeTableAttribute} instance.
	 * <p>
	 * If {@code localVariableType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariableType the {@link LocalVariableType} to add
	 * @throws NullPointerException thrown if, and only if, {@code localVariableType} is {@code null}
	 */
	public void addLocalVariableType(final LocalVariableType localVariableType) {
		this.localVariableTypeTable.add(Objects.requireNonNull(localVariableType, "localVariableType == null"));
	}
	
	/**
	 * Removes {@code localVariableType} from this {@code LocalVariableTypeTableAttribute} instance.
	 * <p>
	 * If {@code localVariableType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariableType the {@link LocalVariableType} to remove
	 * @throws NullPointerException thrown if, and only if, {@code localVariableType} is {@code null}
	 */
	public void removeLocalVariableType(final LocalVariableType localVariableType) {
		this.localVariableTypeTable.remove(Objects.requireNonNull(localVariableType, "localVariableType == null"));
	}
	
	/**
	 * Writes this {@code LocalVariableTypeTableAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getLocalVariableTypeTableLength());
			
			for(final LocalVariableType localVariableType : this.localVariableTypeTable) {
				localVariableType.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code LocalVariableTypeTableAttribute} instances in {@code node}.
	 * <p>
	 * All {@code LocalVariableTypeTableAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code LocalVariableTypeTableAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<LocalVariableTypeTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), LocalVariableTypeTableAttribute.class);
	}
}
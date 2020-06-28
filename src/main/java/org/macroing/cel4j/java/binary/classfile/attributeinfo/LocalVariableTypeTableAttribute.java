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
import java.lang.reflect.Field;//TODO: Update Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code LocalVariableTypeTableAttribute} denotes a LocalVariableTypeTable_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariableTypeTableAttribute extends AttributeInfo {
	/**
	 * The name of the LocalVariableTypeTable_attribute structure.
	 */
	public static final String NAME = "LocalVariableTypeTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LocalVariableType> localVariableTypeTable = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LocalVariableTypeTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code LocalVariableTypeTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public LocalVariableTypeTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@code LocalVariableType}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code LocalVariableType}s
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
		final LocalVariableTypeTableAttribute localVariableTypeTableAttribute = new LocalVariableTypeTableAttribute(getAttributeNameIndex());
		
		this.localVariableTypeTable.forEach(localVariableType -> localVariableTypeTableAttribute.addLocalVariableType(localVariableType.copy()));
		
		return localVariableTypeTableAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariableTypeTableAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("LocalVariableTypeTable_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("local_variable_type_table_length=" + getLocalVariableTypeTableLength());
		
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTypeTableAttribute}, and that {@code LocalVariableTypeTableAttribute} instance is equal to this
	 * {@code LocalVariableTypeTableAttribute} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LocalVariableTypeTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableTypeTableAttribute}, and that {@code LocalVariableTypeTableAttribute} instance is equal to this
	 * {@code LocalVariableTypeTableAttribute} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariableTypeTableAttribute)) {
			return false;
		} else if(!Objects.equals(LocalVariableTypeTableAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(LocalVariableTypeTableAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(LocalVariableTypeTableAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(LocalVariableTypeTableAttribute.class.cast(object).getLocalVariableTypeTableLength() != getLocalVariableTypeTableLength()) {
			return false;
		} else if(!Objects.equals(LocalVariableTypeTableAttribute.class.cast(object).localVariableTypeTable, this.localVariableTypeTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code LocalVariableTypeTableAttribute} instance.
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getLocalVariableTypeTableLength() * 10;
		
		return attributeLength;
	}
	
	/**
	 * Returns the local_variable_type_table_length of this {@code LocalVariableTypeTableAttribute} instance.
	 * 
	 * @return the local_variable_type_table_length of this {@code LocalVariableTypeTableAttribute} instance.
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
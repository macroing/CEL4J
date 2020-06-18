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
 * A {@code LineNumberTableAttribute} denotes a LineNumberTable_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LineNumberTableAttribute extends AttributeInfo {
	/**
	 * The name of the LocalVariableTable_attribute structure.
	 */
	public static final String NAME = "LineNumberTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LineNumber> lineNumberTable = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LineNumberTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code LineNumberTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public LineNumberTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a copy of this {@code LineNumberTableAttribute} instance
	 */
	@Override
	public LineNumberTableAttribute copy() {
		final LineNumberTableAttribute lineNumberTableAttribute = new LineNumberTableAttribute(getAttributeNameIndex());
		
		this.lineNumberTable.forEach(lineNumber -> lineNumberTableAttribute.addLineNumber(lineNumber.copy()));
		
		return lineNumberTableAttribute;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code LineNumber}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code LineNumber}s
	 */
	public List<LineNumber> getLineNumberTable() {
		return new ArrayList<>(this.lineNumberTable);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code LineNumberTableAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("LineNumberTable_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("line_number_table_length=" + getLineNumberTableLength());
		
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
				for(final LineNumber lineNumber : this.lineNumberTable) {
					if(!lineNumber.accept(nodeHierarchicalVisitor)) {
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineNumberTableAttribute}, and that {@code LineNumberTableAttribute} instance is equal to this {@code LineNumberTableAttribute} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LineNumberTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineNumberTableAttribute}, and that {@code LineNumberTableAttribute} instance is equal to this {@code LineNumberTableAttribute} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineNumberTableAttribute)) {
			return false;
		} else if(!Objects.equals(LineNumberTableAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(LineNumberTableAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(LineNumberTableAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(LineNumberTableAttribute.class.cast(object).getLineNumberTableLength() != getLineNumberTableLength()) {
			return false;
		} else if(!Objects.equals(LineNumberTableAttribute.class.cast(object).lineNumberTable, this.lineNumberTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code LineNumberTableAttribute} instance.
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getLineNumberTableLength() * 4;
		
		return attributeLength;
	}
	
	/**
	 * Returns the line_number_table_length of this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return the line_number_table_length of this {@code LineNumberTableAttribute} instance.
	 */
	public int getLineNumberTableLength() {
		return this.lineNumberTable.size();
	}
	
	/**
	 * Returns a hash code for this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a hash code for this {@code LineNumberTableAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getLineNumberTableLength()), this.lineNumberTable);
	}
	
	/**
	 * Adds {@code lineNumber} to this {@code LineNumberTableAttribute} instance.
	 * <p>
	 * If {@code lineNumber} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param lineNumber the {@link LineNumber} to add
	 */
	public void addLineNumber(final LineNumber lineNumber) {
		this.lineNumberTable.add(Objects.requireNonNull(lineNumber, "lineNumber == null"));
	}
	
	/**
	 * Removes {@code lineNumber} from this {@code LineNumberTableAttribute} instance.
	 * <p>
	 * If {@code lineNumber} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param lineNumber the {@link LineNumber} to remove
	 */
	public void removeLineNumber(final LineNumber lineNumber) {
		this.lineNumberTable.remove(Objects.requireNonNull(lineNumber, "lineNumber == null"));
	}
	
	/**
	 * Writes this {@code LineNumberTableAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getLineNumberTableLength());
			
			for(final LineNumber lineNumber : this.lineNumberTable) {
				lineNumber.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code LineNumberTableAttribute}s.
	 * <p>
	 * All {@code LineNumberTableAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code LineNumberTableAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<LineNumberTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), LineNumberTableAttribute.class);
	}
}
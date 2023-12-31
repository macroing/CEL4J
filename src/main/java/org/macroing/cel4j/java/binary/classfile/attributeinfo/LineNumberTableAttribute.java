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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code LineNumberTableAttribute} represents a {@code LineNumberTable_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code LineNumberTable_attribute} structure has the following format:
 * <pre>
 * <code>
 * LineNumberTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 line_number_table_length;
 *     line_number[line_number_table_length] line_number_table;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LineNumberTableAttribute extends AttributeInfo {
	/**
	 * The name of the {@code LineNumberTable_attribute} structure.
	 */
	public static final String NAME = "LineNumberTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LineNumber> lineNumberTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LineNumberTableAttribute} instance that is a copy of {@code lineNumberTableAttribute}.
	 * <p>
	 * If {@code lineNumberTableAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param lineNumberTableAttribute the {@code LineNumberTableAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code lineNumberTableAttribute} is {@code null}
	 */
	public LineNumberTableAttribute(final LineNumberTableAttribute lineNumberTableAttribute) {
		super(NAME, lineNumberTableAttribute.getAttributeNameIndex());
		
		this.lineNumberTable = lineNumberTableAttribute.lineNumberTable.stream().map(lineNumber -> lineNumber.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code LineNumberTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code LineNumberTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public LineNumberTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.lineNumberTable = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a copy of this {@code LineNumberTableAttribute} instance
	 */
	@Override
	public LineNumberTableAttribute copy() {
		return new LineNumberTableAttribute(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code line_number_table} item associated with this {@code LineNumberTableAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return a {@code List} that represents the {@code line_number_table} item associated with this {@code LineNumberTableAttribute} instance
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
		return String.format("new LineNumberTableAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code LineNumberTableAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineNumberTableAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LineNumberTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineNumberTableAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineNumberTableAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), LineNumberTableAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != LineNumberTableAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != LineNumberTableAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getLineNumberTableLength() != LineNumberTableAttribute.class.cast(object).getLineNumberTableLength()) {
			return false;
		} else if(!Objects.equals(this.lineNumberTable, LineNumberTableAttribute.class.cast(object).lineNumberTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code LineNumberTableAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getLineNumberTableLength() * 4;
	}
	
	/**
	 * Returns the value of the {@code line_number_table_length} item associated with this {@code LineNumberTableAttribute} instance.
	 * 
	 * @return the value of the {@code line_number_table_length} item associated with this {@code LineNumberTableAttribute} instance
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
	 * @throws NullPointerException thrown if, and only if, {@code lineNumber} is {@code null}
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
	 * @throws NullPointerException thrown if, and only if, {@code lineNumber} is {@code null}
	 */
	public void removeLineNumber(final LineNumber lineNumber) {
		this.lineNumberTable.remove(Objects.requireNonNull(lineNumber, "lineNumber == null"));
	}
	
	/**
	 * Writes this {@code LineNumberTableAttribute} to {@code dataOutput}.
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
	 * Returns a {@code List} with all {@code LineNumberTableAttribute} instances in {@code node}.
	 * <p>
	 * All {@code LineNumberTableAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code LineNumberTableAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<LineNumberTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), LineNumberTableAttribute.class);
	}
}
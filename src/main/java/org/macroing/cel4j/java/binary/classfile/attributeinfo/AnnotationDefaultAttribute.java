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
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code AnnotationDefaultAttribute} represents an {@code AnnotationDefault_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code AnnotationDefault_attribute} structure has the following format:
 * <pre>
 * <code>
 * AnnotationDefault_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     element_value default_value;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AnnotationDefaultAttribute extends AttributeInfo {
	/**
	 * The name of the {@code AnnotationDefault_attribute} structure.
	 */
	public static final String NAME = "AnnotationDefault";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ElementValue defaultValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AnnotationDefaultAttribute} instance that is a copy of {@code annotationDefaultAttribute}.
	 * <p>
	 * If {@code annotationDefaultAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param annotationDefaultAttribute the {@code AnnotationDefaultAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code annotationDefaultAttribute} is {@code null}
	 */
	public AnnotationDefaultAttribute(final AnnotationDefaultAttribute annotationDefaultAttribute) {
		super(NAME, annotationDefaultAttribute.getAttributeNameIndex());
		
		this.defaultValue = annotationDefaultAttribute.defaultValue;
	}
	
	/**
	 * Constructs a new {@code AnnotationDefaultAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code defaultValue} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code AnnotationDefaultAttribute} instance
	 * @param defaultValue the value for the {@code default_value} item associated with this {@code AnnotationDefaultAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code defaultValue} is {@code null}
	 */
	public AnnotationDefaultAttribute(final int attributeNameIndex, final ElementValue defaultValue) {
		super(NAME, attributeNameIndex);
		
		this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code AnnotationDefaultAttribute} instance.
	 * 
	 * @return a copy of this {@code AnnotationDefaultAttribute} instance
	 */
	@Override
	public AnnotationDefaultAttribute copy() {
		return new AnnotationDefaultAttribute(this);
	}
	
	/**
	 * Returns the value of the {@code default_value} item associated with this {@code AnnotationDefaultAttribute} instance.
	 * 
	 * @return the value of the {@code default_value} item associated with this {@code AnnotationDefaultAttribute} instance
	 */
	public ElementValue getDefaultValue() {
		return this.defaultValue;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AnnotationDefaultAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code AnnotationDefaultAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new AnnotationDefaultAttribute(%s, %s)", Integer.toString(getAttributeNameIndex()), getDefaultValue());
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
	 * <li>traverse its child {@code Node}.</li>
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
				if(!getDefaultValue().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code AnnotationDefaultAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AnnotationDefaultAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AnnotationDefaultAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AnnotationDefaultAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AnnotationDefaultAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), AnnotationDefaultAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != AnnotationDefaultAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != AnnotationDefaultAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(!Objects.equals(getDefaultValue(), AnnotationDefaultAttribute.class.cast(object).getDefaultValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code AnnotationDefaultAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code AnnotationDefaultAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return getDefaultValue().getLength();
	}
	
	/**
	 * Returns a hash code for this {@code AnnotationDefaultAttribute} instance.
	 * 
	 * @return a hash code for this {@code AnnotationDefaultAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), getDefaultValue());
	}
	
	/**
	 * Sets {@code defaultValue} as the value for the {@code default_value} item associated with this {@code AnnotationDefaultAttribute} instance.
	 * <p>
	 * If {@code defaultValue} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param defaultValue the value for the {@code default_value} item associated with this {@code AnnotationDefaultAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code defaultValue} is {@code null}
	 */
	public void setDefaultValue(final ElementValue defaultValue) {
		this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue == null");
	}
	
	/**
	 * Writes this {@code AnnotationDefaultAttribute} to {@code dataOutput}.
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
			
			final
			ElementValue elementValue = getDefaultValue();
			elementValue.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code AnnotationDefaultAttribute} instances in {@code node}.
	 * <p>
	 * All {@code AnnotationDefaultAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code AnnotationDefaultAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<AnnotationDefaultAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), AnnotationDefaultAttribute.class);
	}
}
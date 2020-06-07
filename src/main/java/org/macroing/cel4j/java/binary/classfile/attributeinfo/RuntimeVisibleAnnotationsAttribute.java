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
 * A {@code RuntimeVisibleAnnotationsAttribute} denotes a RuntimeVisibleAnnotations_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {
	/**
	 * The name of the RuntimeVisibleAnnotations_attribute structure.
	 */
	public static final String NAME = "RuntimeVisibleAnnotations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Annotation> annotations = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private RuntimeVisibleAnnotationsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return the annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	public List<Annotation> getAnnotations() {
		return new ArrayList<>(this.annotations);
	}
	
	/**
	 * Returns a copy of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return a copy of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	@Override
	public RuntimeVisibleAnnotationsAttribute copy() {
		final RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute = new RuntimeVisibleAnnotationsAttribute(getAttributeNameIndex());
		
		this.annotations.forEach(annotation -> runtimeVisibleAnnotationsAttribute.addAnnotation(annotation.copy()));
		
		return runtimeVisibleAnnotationsAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("RuntimeVisibleAnnotations_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("num_annotations=" + getNumAnnotations());
		
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
				for(final Annotation annotation : this.annotations) {
					if(!annotation.accept(nodeHierarchicalVisitor)) {
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleAnnotationsAttribute}, and that {@code RuntimeVisibleAnnotationsAttribute} instance is equal to this
	 * {@code RuntimeVisibleAnnotationsAttribute} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code RuntimeVisibleAnnotationsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleAnnotationsAttribute}, and that {@code RuntimeVisibleAnnotationsAttribute} instance is equal to this
	 * {@code RuntimeVisibleAnnotationsAttribute} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof RuntimeVisibleAnnotationsAttribute)) {
			return false;
		} else if(!Objects.equals(RuntimeVisibleAnnotationsAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(RuntimeVisibleAnnotationsAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(RuntimeVisibleAnnotationsAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(RuntimeVisibleAnnotationsAttribute.class.cast(object).getNumAnnotations() != getNumAnnotations()) {
			return false;
		} else if(!Objects.equals(RuntimeVisibleAnnotationsAttribute.class.cast(object).annotations, this.annotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 2;
		
		for(final Annotation annotation : this.annotations) {
			attributeLength += annotation.getLength();
		}
		
		return attributeLength;
	}
	
	/**
	 * Returns the num_annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return the num_annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	public int getNumAnnotations() {
		return this.annotations.size();
	}
	
	/**
	 * Returns a hash code for this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return a hash code for this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumAnnotations()), this.annotations);
	}
	
	/**
	 * Adds {@code annotation} to the annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * <p>
	 * If {@code annotation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param annotation the {@link Annotation} to add
	 * @throws NullPointerException thrown if, and only if, {@code annotation} is {@code null}
	 */
	public void addAnnotation(final Annotation annotation) {
		this.annotations.add(Objects.requireNonNull(annotation, "annotation == null"));
	}
	
	/**
	 * Removes {@code annotation} from the annotations of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * <p>
	 * If {@code annotation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param annotation the {@link Annotation} to remove
	 * @throws NullPointerException thrown if, and only if, {@code annotation} is {@code null}
	 */
	public void removeAnnotation(final Annotation annotation) {
		this.annotations.remove(Objects.requireNonNull(annotation, "annotation == null"));
	}
	
	/**
	 * Writes this {@code RuntimeVisibleAnnotationsAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getNumAnnotations());
			
			for(final Annotation annotation : this.annotations) {
				annotation.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code RuntimeVisibleAnnotationsAttribute}s.
	 * <p>
	 * All {@code RuntimeVisibleAnnotationsAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code RuntimeVisibleAnnotationsAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<RuntimeVisibleAnnotationsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), RuntimeVisibleAnnotationsAttribute.class);
	}
	
	/**
	 * Returns a new {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code RuntimeVisibleAnnotationsAttribute} instance
	 * @return a new {@code RuntimeVisibleAnnotationsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public static RuntimeVisibleAnnotationsAttribute newInstance(final int attributeNameIndex) {
		return new RuntimeVisibleAnnotationsAttribute(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE));
	}
}
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
 * A {@code RuntimeVisibleAnnotationsAttribute} represents a {@code RuntimeVisibleAnnotations_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code RuntimeVisibleAnnotations_attribute} structure has the following format:
 * <pre>
 * <code>
 * RuntimeVisibleAnnotations_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 num_annotations;
 *     annotation[num_annotations] annotations;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {
	/**
	 * The name of the {@code RuntimeVisibleAnnotations_attribute} structure.
	 */
	public static final String NAME = "RuntimeVisibleAnnotations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Annotation> annotations;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code RuntimeVisibleAnnotationsAttribute} instance that is a copy of {@code runtimeVisibleAnnotationsAttribute}.
	 * <p>
	 * If {@code runtimeVisibleAnnotationsAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param runtimeVisibleAnnotationsAttribute the {@code RuntimeVisibleAnnotationsAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code runtimeVisibleAnnotationsAttribute} is {@code null}
	 */
	public RuntimeVisibleAnnotationsAttribute(final RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute) {
		super(NAME, runtimeVisibleAnnotationsAttribute.getAttributeNameIndex());
		
		this.annotations = runtimeVisibleAnnotationsAttribute.annotations.stream().map(annotation -> annotation.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code RuntimeVisibleAnnotationsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public RuntimeVisibleAnnotationsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.annotations = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link Annotation} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Annotation} instances
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
		return new RuntimeVisibleAnnotationsAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code RuntimeVisibleAnnotationsAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new RuntimeVisibleAnnotationsAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code RuntimeVisibleAnnotationsAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code RuntimeVisibleAnnotationsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof RuntimeVisibleAnnotationsAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), RuntimeVisibleAnnotationsAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != RuntimeVisibleAnnotationsAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != RuntimeVisibleAnnotationsAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumAnnotations() != RuntimeVisibleAnnotationsAttribute.class.cast(object).getNumAnnotations()) {
			return false;
		} else if(!Objects.equals(this.annotations, RuntimeVisibleAnnotationsAttribute.class.cast(object).annotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code RuntimeVisibleAnnotationsAttribute} instance
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
	 * Returns the value of the {@code num_annotations} item associated with this {@code RuntimeVisibleAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code num_annotations} item associated with this {@code RuntimeVisibleAnnotationsAttribute} instance
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
	 * Adds {@code annotation} to this {@code RuntimeVisibleAnnotationsAttribute} instance.
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
	 * Removes {@code annotation} from this {@code RuntimeVisibleAnnotationsAttribute} instance.
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
	 * Returns a {@code List} with all {@code RuntimeVisibleAnnotationsAttribute} instances in {@code node}.
	 * <p>
	 * All {@code RuntimeVisibleAnnotationsAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code RuntimeVisibleAnnotationsAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<RuntimeVisibleAnnotationsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), RuntimeVisibleAnnotationsAttribute.class);
	}
}
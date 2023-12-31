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
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code AnnotationValueUnion} represents an unnamed {@code annotation_value} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The unnamed {@code annotation_value} {@code union} structure has the following format:
 * <pre>
 * <code>
 * {
 *     annotation annotation_value;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AnnotationValueUnion implements Union {
	private final Annotation annotationValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AnnotationValueUnion} instance.
	 * <p>
	 * If {@code annotationValue} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param annotationValue the value for the {@code annotation_value} item associated with this {@code AnnotationValueUnion} instance
	 * @throws NullPointerException thrown if, and only if, {@code annotationValue} is {@code null}
	 */
	public AnnotationValueUnion(final Annotation annotationValue) {
		this.annotationValue = Objects.requireNonNull(annotationValue, "annotationValue == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the value of the {@code annotation_value} item associated with this {@code AnnotationValueUnion} instance.
	 * 
	 * @return the value of the {@code annotation_value} item associated with this {@code AnnotationValueUnion} instance
	 */
	public Annotation getAnnotationValue() {
		return this.annotationValue;
	}
	
	/**
	 * Returns a copy of this {@code AnnotationValueUnion} instance.
	 * 
	 * @return a copy of this {@code AnnotationValueUnion} instance
	 */
	@Override
	public AnnotationValueUnion copy() {
		return new AnnotationValueUnion(getAnnotationValue().copy());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AnnotationValueUnion} instance.
	 * 
	 * @return a {@code String} representation of this {@code AnnotationValueUnion} instance
	 */
	@Override
	public String toString() {
		return String.format("new AnnotationValueUnion(%s)", getAnnotationValue());
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
	 * <li>traverse its child {@code Node}, an {@link Annotation}.</li>
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
				if(!getAnnotationValue().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code AnnotationValueUnion} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AnnotationValueUnion}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AnnotationValueUnion} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AnnotationValueUnion}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AnnotationValueUnion)) {
			return false;
		} else if(!Objects.equals(getAnnotationValue(), AnnotationValueUnion.class.cast(object).getAnnotationValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code AnnotationValueUnion} instance.
	 * 
	 * @return the length of this {@code AnnotationValueUnion} instance
	 */
	@Override
	public int getLength() {
		return getAnnotationValue().getLength();
	}
	
	/**
	 * Returns a hash code for this {@code AnnotationValueUnion} instance.
	 * 
	 * @return a hash code for this {@code AnnotationValueUnion} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAnnotationValue());
	}
	
	/**
	 * Writes this {@code AnnotationValueUnion} to {@code dataOutput}.
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
		final
		Annotation annotation = getAnnotationValue();
		annotation.write(dataOutput);
	}
}
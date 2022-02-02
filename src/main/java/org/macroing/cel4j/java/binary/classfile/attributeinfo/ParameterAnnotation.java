/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code ParameterAnnotation} represents an entry in the {@code parameter_annotations} table item of the {@code RuntimeInvisibleParameterAnnotations_attribute} and {@code RuntimeVisibleParameterAnnotations_attribute} structures.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 num_annotations;
 *     annotation[num_annotations] annotations;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ParameterAnnotation implements Node {
	private final List<Annotation> annotations;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code ParameterAnnotation} instance.
	 */
	public ParameterAnnotation() {
		this.annotations = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link Annotation} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ParameterAnnotation} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Annotation} instances
	 */
	public List<Annotation> getAnnotations() {
		return new ArrayList<>(this.annotations);
	}
	
	/**
	 * Returns a copy of this {@code ParameterAnnotation} instance.
	 * 
	 * @return a copy of this {@code ParameterAnnotation} instance
	 */
	public ParameterAnnotation copy() {
		final ParameterAnnotation parameterAnnotation = new ParameterAnnotation();
		
		for(final Annotation annotation : this.annotations) {
			parameterAnnotation.addAnnotation(annotation.copy());
		}
		
		return parameterAnnotation;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ParameterAnnotation} instance.
	 * 
	 * @return a {@code String} representation of this {@code ParameterAnnotation} instance
	 */
	@Override
	public String toString() {
		return "new ParameterAnnotation()";
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
	 * <li>traverse its child {@code Node} instances.</li>
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
	 * Compares {@code object} to this {@code ParameterAnnotation} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ParameterAnnotation}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ParameterAnnotation} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ParameterAnnotation}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ParameterAnnotation)) {
			return false;
		} else if(!Objects.equals(this.annotations, ParameterAnnotation.class.cast(object).annotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code ParameterAnnotation} instance.
	 * 
	 * @return the length of this {@code ParameterAnnotation} instance
	 */
	public int getLength() {
		int length = 2;
		
		for(final Annotation annotation : this.annotations) {
			length += annotation.getLength();
		}
		
		return length;
	}
	
	/**
	 * Returns the value of the {@code num_annotations} item associated with this {@code ParameterAnnotation} instance.
	 * 
	 * @return the value of the {@code num_annotations} item associated with this {@code ParameterAnnotation} instance
	 */
	public int getNumAnnotations() {
		return this.annotations.size();
	}
	
	/**
	 * Returns a hash code for this {@code ParameterAnnotation} instance.
	 * 
	 * @return a hash code for this {@code ParameterAnnotation} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.annotations);
	}
	
	/**
	 * Adds {@code annotation} to this {@code ParameterAnnotation} instance.
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
	 * Removes {@code annotation} from this {@code ParameterAnnotation} instance.
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
	 * Writes this {@code ParameterAnnotation} to {@code dataOutput}.
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
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getNumAnnotations());
			
			for(final Annotation annotation : this.annotations) {
				annotation.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
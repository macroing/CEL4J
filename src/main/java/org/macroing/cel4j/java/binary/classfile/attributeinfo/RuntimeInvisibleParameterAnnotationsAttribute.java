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

/**
 * A {@code RuntimeInvisibleParameterAnnotationsAttribute} denotes a RuntimeInvisibleParameterAnnotations_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeInfo {
	/**
	 * The name of the RuntimeInvisibleParameterAnnotations_attribute structure.
	 */
	public static final String NAME = "RuntimeInvisibleParameterAnnotations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<ParameterAnnotation> parameterAnnotations = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public RuntimeInvisibleParameterAnnotationsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the annotations of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the annotations of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	public List<ParameterAnnotation> getParameterAnnotations() {
		return new ArrayList<>(this.parameterAnnotations);
	}
	
	/**
	 * Returns a copy of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a copy of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public RuntimeInvisibleParameterAnnotationsAttribute copy() {
		final RuntimeInvisibleParameterAnnotationsAttribute runtimeInvisibleParameterAnnotationsAttribute = new RuntimeInvisibleParameterAnnotationsAttribute(getAttributeNameIndex());
		
		this.parameterAnnotations.forEach(parameterAnnotation -> runtimeInvisibleParameterAnnotationsAttribute.addParameterAnnotation(parameterAnnotation.copy()));
		
		return runtimeInvisibleParameterAnnotationsAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("RuntimeInvisibleParameterAnnotations_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("num_parameters=" + getNumParameters());
		
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
				for(final ParameterAnnotation parameterAnnotation : this.parameterAnnotations) {
					if(!parameterAnnotation.accept(nodeHierarchicalVisitor)) {
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code RuntimeInvisibleParameterAnnotationsAttribute}, and that {@code RuntimeInvisibleParameterAnnotationsAttribute} instance is equal to this
	 * {@code RuntimeInvisibleParameterAnnotationsAttribute} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code RuntimeInvisibleParameterAnnotationsAttribute}, and that {@code RuntimeInvisibleParameterAnnotationsAttribute} instance is equal to this
	 * {@code RuntimeInvisibleParameterAnnotationsAttribute} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof RuntimeInvisibleParameterAnnotationsAttribute)) {
			return false;
		} else if(!Objects.equals(RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getNumParameters() != getNumParameters()) {
			return false;
		} else if(!Objects.equals(RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).parameterAnnotations, this.parameterAnnotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 1;
		
		for(final ParameterAnnotation parameterAnnotation : this.parameterAnnotations) {
			attributeLength += parameterAnnotation.getLength();
		}
		
		return attributeLength;
	}
	
	/**
	 * Returns the num_parameters of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the num_parameters of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	public int getNumParameters() {
		return this.parameterAnnotations.size();
	}
	
	/**
	 * Returns a hash code for this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a hash code for this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumParameters()), this.parameterAnnotations);
	}
	
	/**
	 * Adds {@code parameterAnnotation} to the parameter_annotations of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * If {@code parameterAnnotation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterAnnotation the {@link ParameterAnnotation} to add
	 * @throws NullPointerException thrown if, and only if, {@code parameterAnnotation} is {@code null}
	 */
	public void addParameterAnnotation(final ParameterAnnotation parameterAnnotation) {
		this.parameterAnnotations.add(Objects.requireNonNull(parameterAnnotation, "parameterAnnotation == null"));
	}
	
	/**
	 * Removes {@code parameterAnnotation} from the parameter_annotations of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * If {@code parameterAnnotation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterAnnotation the {@link ParameterAnnotation} to remove
	 * @throws NullPointerException thrown if, and only if, {@code parameterAnnotation} is {@code null}
	 */
	public void removeParameterAnnotation(final ParameterAnnotation parameterAnnotation) {
		this.parameterAnnotations.remove(Objects.requireNonNull(parameterAnnotation, "parameterAnnotation == null"));
	}
	
	/**
	 * Writes this {@code RuntimeInvisibleParameterAnnotationsAttribute} to {@code dataOutput}.
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
			dataOutput.writeByte(getNumParameters());
			
			for(final ParameterAnnotation parameterAnnotation : this.parameterAnnotations) {
				parameterAnnotation.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code RuntimeInvisibleParameterAnnotationsAttribute}s.
	 * <p>
	 * All {@code RuntimeInvisibleParameterAnnotationsAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code RuntimeInvisibleParameterAnnotationsAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<RuntimeInvisibleParameterAnnotationsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), RuntimeInvisibleParameterAnnotationsAttribute.class);
	}
}
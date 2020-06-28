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
 * A {@code RuntimeInvisibleParameterAnnotationsAttribute} represents a {@code RuntimeInvisibleParameterAnnotations_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code RuntimeInvisibleParameterAnnotations_attribute} structure has the following format:
 * <pre>
 * <code>
 * RuntimeInvisibleParameterAnnotations_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 num_parameters;
 *     parameter_annotation[num_parameters] parameter_annotations;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeInfo {
	/**
	 * The name of the {@code RuntimeInvisibleParameterAnnotations_attribute} structure.
	 */
	public static final String NAME = "RuntimeInvisibleParameterAnnotations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<ParameterAnnotation> parameterAnnotations;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code RuntimeInvisibleParameterAnnotationsAttribute} instance that is a copy of {@code runtimeInvisibleParameterAnnotationsAttribute}.
	 * <p>
	 * If {@code runtimeInvisibleParameterAnnotationsAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param runtimeInvisibleParameterAnnotationsAttribute the {@code RuntimeInvisibleParameterAnnotationsAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code runtimeInvisibleParameterAnnotationsAttribute} is {@code null}
	 */
	public RuntimeInvisibleParameterAnnotationsAttribute(final RuntimeInvisibleParameterAnnotationsAttribute runtimeInvisibleParameterAnnotationsAttribute) {
		super(NAME, runtimeInvisibleParameterAnnotationsAttribute.getAttributeNameIndex());
		
		this.parameterAnnotations = runtimeInvisibleParameterAnnotationsAttribute.parameterAnnotations.stream().map(parameterAnnotation -> parameterAnnotation.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public RuntimeInvisibleParameterAnnotationsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.parameterAnnotations = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link ParameterAnnotation} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code ParameterAnnotation} instances
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
		return new RuntimeInvisibleParameterAnnotationsAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new RuntimeInvisibleParameterAnnotationsAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code RuntimeInvisibleParameterAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code RuntimeInvisibleParameterAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof RuntimeInvisibleParameterAnnotationsAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumParameters() != RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).getNumParameters()) {
			return false;
		} else if(!Objects.equals(this.parameterAnnotations, RuntimeInvisibleParameterAnnotationsAttribute.class.cast(object).parameterAnnotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
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
	 * Returns the value of the {@code num_parameters} item associated with this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code num_parameters} item associated with this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance
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
	 * Adds {@code parameterAnnotation} to this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
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
	 * Removes {@code parameterAnnotation} from this {@code RuntimeInvisibleParameterAnnotationsAttribute} instance.
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
	 * Returns a {@code List} with all {@code RuntimeInvisibleParameterAnnotationsAttribute} instances in {@code node}.
	 * <p>
	 * All {@code RuntimeInvisibleParameterAnnotationsAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code RuntimeInvisibleParameterAnnotationsAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<RuntimeInvisibleParameterAnnotationsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), RuntimeInvisibleParameterAnnotationsAttribute.class);
	}
}
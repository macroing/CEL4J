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
 * A {@code RuntimeVisibleParameterAnnotationsAttribute} represents a {@code RuntimeVisibleParameterAnnotations_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code RuntimeVisibleParameterAnnotations_attribute} structure has the following format:
 * <pre>
 * <code>
 * RuntimeVisibleParameterAnnotations_attribute {
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
public final class RuntimeVisibleParameterAnnotationsAttribute extends AttributeInfo {
	/**
	 * The name of the {@code RuntimeVisibleParameterAnnotations_attribute} structure.
	 */
	public static final String NAME = "RuntimeVisibleParameterAnnotations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<ParameterAnnotation> parameterAnnotations;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code RuntimeVisibleParameterAnnotationsAttribute} instance that is a copy of {@code runtimeVisibleParameterAnnotationsAttribute}.
	 * <p>
	 * If {@code runtimeVisibleParameterAnnotationsAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param runtimeVisibleParameterAnnotationsAttribute the {@code RuntimeVisibleParameterAnnotationsAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code runtimeVisibleParameterAnnotationsAttribute} is {@code null}
	 */
	public RuntimeVisibleParameterAnnotationsAttribute(final RuntimeVisibleParameterAnnotationsAttribute runtimeVisibleParameterAnnotationsAttribute) {
		super(NAME, runtimeVisibleParameterAnnotationsAttribute.getAttributeNameIndex());
		
		this.parameterAnnotations = runtimeVisibleParameterAnnotationsAttribute.parameterAnnotations.stream().map(parameterAnnotation -> parameterAnnotation.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public RuntimeVisibleParameterAnnotationsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.parameterAnnotations = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link ParameterAnnotation} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code ParameterAnnotation} instances
	 */
	public List<ParameterAnnotation> getParameterAnnotations() {
		return new ArrayList<>(this.parameterAnnotations);
	}
	
	/**
	 * Returns a copy of this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a copy of this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public RuntimeVisibleParameterAnnotationsAttribute copy() {
		return new RuntimeVisibleParameterAnnotationsAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new RuntimeVisibleParameterAnnotationsAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code RuntimeVisibleParameterAnnotationsAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleParameterAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code RuntimeVisibleParameterAnnotationsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code RuntimeVisibleParameterAnnotationsAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof RuntimeVisibleParameterAnnotationsAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), RuntimeVisibleParameterAnnotationsAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != RuntimeVisibleParameterAnnotationsAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != RuntimeVisibleParameterAnnotationsAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumParameters() != RuntimeVisibleParameterAnnotationsAttribute.class.cast(object).getNumParameters()) {
			return false;
		} else if(!Objects.equals(this.parameterAnnotations, RuntimeVisibleParameterAnnotationsAttribute.class.cast(object).parameterAnnotations)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
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
	 * Returns the value of the {@code num_parameters} item associated with this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return the value of the {@code num_parameters} item associated with this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
	 */
	public int getNumParameters() {
		return this.parameterAnnotations.size();
	}
	
	/**
	 * Returns a hash code for this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
	 * 
	 * @return a hash code for this {@code RuntimeVisibleParameterAnnotationsAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumParameters()), this.parameterAnnotations);
	}
	
	/**
	 * Adds {@code parameterAnnotation} to this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
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
	 * Removes {@code parameterAnnotation} from this {@code RuntimeVisibleParameterAnnotationsAttribute} instance.
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
	 * Writes this {@code RuntimeVisibleParameterAnnotationsAttribute} to {@code dataOutput}.
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
	 * Returns a {@code List} with all {@code RuntimeVisibleParameterAnnotationsAttribute} instances in {@code node}.
	 * <p>
	 * All {@code RuntimeVisibleParameterAnnotationsAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code RuntimeVisibleParameterAnnotationsAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<RuntimeVisibleParameterAnnotationsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), RuntimeVisibleParameterAnnotationsAttribute.class);
	}
}
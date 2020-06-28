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
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code MethodParametersAttribute} denotes a MethodParameters_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodParametersAttribute extends AttributeInfo {
	/**
	 * The name of the MethodParameters_attribute structure.
	 */
	public static final String NAME = "MethodParameters";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Parameter> parameters = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code MethodParametersAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code MethodParametersAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public MethodParametersAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@code Parameter}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Parameter}s
	 */
	public List<Parameter> getParameters() {
		return new ArrayList<>(this.parameters);
	}
	
	/**
	 * Returns a copy of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a copy of this {@code MethodParametersAttribute} instance
	 */
	@Override
	public MethodParametersAttribute copy() {
		final MethodParametersAttribute methodParametersAttribute = new MethodParametersAttribute(getAttributeNameIndex());
		
		this.parameters.forEach(parameter -> methodParametersAttribute.addParameter(parameter.copy()));
		
		return methodParametersAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code MethodParametersAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("MethodParameters_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		
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
				for(final Parameter parameter : this.parameters) {
					if(!parameter.accept(nodeHierarchicalVisitor)) {
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code MethodParametersAttribute}, and that {@code MethodParametersAttribute} instance is equal to this {@code MethodParametersAttribute}
	 * instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code MethodParametersAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code MethodParametersAttribute}, and that {@code MethodParametersAttribute} instance is equal to this {@code MethodParametersAttribute}
	 * instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodParametersAttribute)) {
			return false;
		} else if(!Objects.equals(MethodParametersAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(MethodParametersAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(MethodParametersAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(MethodParametersAttribute.class.cast(object).getParametersCount() != getParametersCount()) {
			return false;
		} else if(!Objects.equals(MethodParametersAttribute.class.cast(object).parameters, this.parameters)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code MethodParametersAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 1 + this.parameters.size() * 2;
	}
	
	/**
	 * Returns the parameters_count of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return the parameters_count of this {@code MethodParametersAttribute} instance
	 */
	public int getParametersCount() {
		return this.parameters.size();
	}
	
	/**
	 * Returns a hash code for this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a hash code for this {@code MethodParametersAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getParametersCount()), this.parameters);
	}
	
	/**
	 * Adds {@code parameter} to this {@code MethodParametersAttribute} instance.
	 * <p>
	 * If {@code parameter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameter the {@code Parameter} to add
	 * @throws NullPointerException thrown if, and only if, {@code parameter} is {@code null}
	 */
	public void addParameter(final Parameter parameter) {
		this.parameters.add(Objects.requireNonNull(parameter, "parameter == null"));
	}
	
	/**
	 * Removes {@code parameter} from this {@code MethodParametersAttribute} instance.
	 * <p>
	 * If {@code parameter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameter the {@code Parameter} to remove
	 * @throws NullPointerException thrown if, and only if, {@code parameter} is {@code null}
	 */
	public void removeParameter(final Parameter parameter) {
		this.parameters.remove(Objects.requireNonNull(parameter, "parameter == null"));
	}
	
	/**
	 * Writes this {@code MethodParametersAttribute} to {@code dataOutput}.
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
			dataOutput.writeByte(getParametersCount());
			
			for(final Parameter parameter : this.parameters) {
				parameter.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code MethodParametersAttribute} instances in {@code node}.
	 * <p>
	 * All {@code MethodParametersAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code MethodParametersAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<MethodParametersAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), MethodParametersAttribute.class);
	}
	
	/**
	 * Attempts to find a {@code MethodParametersAttribute} instance in {@code methodInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code MethodParametersAttribute} instance.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check in
	 * @return an {@code Optional} with the optional {@code MethodParametersAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public static Optional<MethodParametersAttribute> find(final MethodInfo methodInfo) {
		return methodInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof MethodParametersAttribute).map(attributeInfo -> MethodParametersAttribute.class.cast(attributeInfo)).findFirst();
	}
}
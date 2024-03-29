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
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code MethodParametersAttribute} represents a {@code MethodParameters_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code MethodParameters_attribute} structure has the following format:
 * <pre>
 * <code>
 * MethodParameters_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 parameters_count;
 *     parameter[parameters_count] parameters;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodParametersAttribute extends AttributeInfo {
	/**
	 * The name of the {@code MethodParameters_attribute} structure.
	 */
	public static final String NAME = "MethodParameters";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Parameter> parameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code MethodParametersAttribute} instance that is a copy of {@code methodParametersAttribute}.
	 * <p>
	 * If {@code methodParametersAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodParametersAttribute the {@code MethodParametersAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code methodParametersAttribute} is {@code null}
	 */
	public MethodParametersAttribute(final MethodParametersAttribute methodParametersAttribute) {
		super(NAME, methodParametersAttribute.getAttributeNameIndex());
		
		this.parameters = methodParametersAttribute.parameters.stream().map(parameter -> parameter.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code MethodParametersAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code MethodParametersAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public MethodParametersAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.parameters = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with the values of the {@code isFinal()} method of all currently added {@link Parameter} instances.
	 * 
	 * @return a {@code List} with the values of the {@code isFinal()} method of all currently added {@code Parameter} instances
	 */
	public List<Boolean> getParameterIsFinals() {
		final List<Boolean> parameterIsFinals = new ArrayList<>();
		
		for(final Parameter parameter : this.parameters) {
			parameterIsFinals.add(Boolean.valueOf(parameter.isFinal()));
		}
		
		return parameterIsFinals;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link Parameter} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Parameter} instances
	 */
	public List<Parameter> getParameters() {
		return new ArrayList<>(this.parameters);
	}
	
	/**
	 * Returns a {@code List} with the names of all currently added {@link Parameter} instances.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If, for any {@link Parameter} {@code parameter} in this {@code MethodParametersAttribute} instance, {@code parameter.getNameIndex()} is less than {@code 0} or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * <p>
	 * If, for any {@code Parameter} {@code parameter} in this {@code MethodParametersAttribute} instance, {@code classFile.getCPInfo(parameter.getNameIndex())} is not of type {@link ConstantUTF8Info}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} with the names of all currently added {@code Parameter} instances
	 * @throws IllegalArgumentException thrown if, and only if, {@code parameter.getNameIndex()} is less than {@code 0} or greater than or equal to {@code classFile.getCPInfoCount()} for any {@code Parameter} {@code parameter} in this {@code MethodParametersAttribute} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code classFile.getCPInfo(parameter.getNameIndex())} is not of type {@code ConstantUTF8Info} for any {@code Parameter} {@code parameter} in this {@code MethodParametersAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public List<String> getParameterNames(final ClassFile classFile) {
		Objects.requireNonNull(classFile, "classFile == null");
		
		final List<String> parameterNames = new ArrayList<>();
		
		for(final Parameter parameter : this.parameters) {
			parameterNames.add(parameter.getNameIndex() != 0 ? classFile.getCPInfo(parameter.getNameIndex(), ConstantUTF8Info.class).getStringValue() : "");
		}
		
		return parameterNames;
	}
	
	/**
	 * Returns a copy of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a copy of this {@code MethodParametersAttribute} instance
	 */
	@Override
	public MethodParametersAttribute copy() {
		return new MethodParametersAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodParametersAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code MethodParametersAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new MethodParametersAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code MethodParametersAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code MethodParametersAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code MethodParametersAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code MethodParametersAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodParametersAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), MethodParametersAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != MethodParametersAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != MethodParametersAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getParametersCount() != MethodParametersAttribute.class.cast(object).getParametersCount()) {
			return false;
		} else if(!Objects.equals(this.parameters, MethodParametersAttribute.class.cast(object).parameters)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code MethodParametersAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code MethodParametersAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 1 + this.parameters.size() * 2;
	}
	
	/**
	 * Returns the value of the {@code parameters_count} item associated with this {@code MethodParametersAttribute} instance.
	 * 
	 * @return the value of the {@code parameters_count} item associated with this {@code MethodParametersAttribute} instance
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
	 * @param parameter the {@link Parameter} to add
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
	 * @param parameter the {@link Parameter} to remove
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
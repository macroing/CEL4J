/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code MethodDescriptor} denotes a MethodDescriptor as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodDescriptor implements Node {
	private final List<ParameterDescriptor> parameterDescriptors;
	private final ReturnDescriptor returnDescriptor;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MethodDescriptor(final ReturnDescriptor returnDescriptor, final ParameterDescriptor... parameterDescriptors) {
		this(returnDescriptor, ParameterArguments.requireNonNullList(Arrays.asList(parameterDescriptors.clone()), "parameterDescriptors"));
	}
	
	MethodDescriptor(final ReturnDescriptor returnDescriptor, final List<ParameterDescriptor> parameterDescriptors) {
		this.parameterDescriptors = parameterDescriptors;
		this.returnDescriptor = returnDescriptor;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link ParameterDescriptor} instances associated with this {@code MethodDescriptor} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodDescriptor} instance.
	 * 
	 * @return a {@code List} with all {@code ParameterDescriptor} instances associated with this {@code MethodDescriptor} instance
	 */
	public List<ParameterDescriptor> getParameterDescriptors() {
		return new ArrayList<>(this.parameterDescriptors);
	}
	
	/**
	 * Returns the {@link ReturnDescriptor} associated with this {@code MethodDescriptor} instance.
	 * 
	 * @return the {@code ReturnDescriptor} associated with this {@code MethodDescriptor} instance
	 */
	public ReturnDescriptor getReturnDescriptor() {
		return this.returnDescriptor;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ReturnDescriptor} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ReturnDescriptor} instance in external form
	 */
	public String toExternalForm() {
		return String.format("%s(%s)", this.returnDescriptor.toExternalForm(), this.parameterDescriptors.stream().map(parameterDescriptor -> parameterDescriptor.toExternalForm()).collect(Collectors.joining(",")));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ReturnDescriptor} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ReturnDescriptor} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("(%s)%s", this.parameterDescriptors.stream().map(parameterDescriptor -> parameterDescriptor.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append), this.returnDescriptor.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodDescriptor} instance.
	 * 
	 * @return a {@code String} representation of this {@code MethodDescriptor} instance
	 */
	@Override
	public String toString() {
		return String.format("MethodDescriptor: [ReturnDescriptor=%s], [ParameterDescriptors=%s], [InternalForm=%s]", getReturnDescriptor(), getParameterDescriptors(), toInternalForm());
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
				if(!this.returnDescriptor.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				for(final ParameterDescriptor parameterDescriptor : this.parameterDescriptors) {
					if(!parameterDescriptor.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code MethodDescriptor} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code MethodDescriptor}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code MethodDescriptor} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code MethodDescriptor}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodDescriptor)) {
			return false;
		} else if(!Objects.equals(MethodDescriptor.class.cast(object).returnDescriptor, this.returnDescriptor)) {
			return false;
		} else if(!Objects.equals(MethodDescriptor.class.cast(object).parameterDescriptors, this.parameterDescriptors)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code MethodDescriptor} instance.
	 * 
	 * @return a hash code for this {@code MethodDescriptor} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.returnDescriptor, this.parameterDescriptors);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses all {@code MethodDescriptor} instances from all {@link MethodInfo} instances in {@code classFile}.
	 * <p>
	 * Returns a {@code List} with all {@code MethodDescriptor} instances that were parsed from all {@code MethodInfo} instances in {@code classFile}.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If, for any {@code MethodInfo} {@code methodInfo} in {@code classFile}, the {@link CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the
	 * {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If, for any {@code MethodInfo} {@code methodInfo} in {@code classFile}, {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be
	 * thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} with all {@code MethodDescriptor} instances that were parsed from all {@code MethodInfo} instances in {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, for any {@code MethodInfo} {@code methodInfo} in {@code classFile}, the {@code CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, or
	 *                                  the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for any {@code MethodInfo} {@code methodInfo} in {@code classFile}, {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to
	 *                                   {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static List<MethodDescriptor> parseMethodDescriptors(final ClassFile classFile) {
		return classFile.getMethodInfos().stream().map(methodInfo -> parseMethodDescriptor(classFile, methodInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Parses the {@code MethodDescriptor} of {@code methodInfo} in {@code classFile}.
	 * <p>
	 * Returns a {@code MethodDescriptor} instance.
	 * <p>
	 * If either {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link MethodInfo} instance that is equal to {@code methodInfo}, the {@link CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a {@link ConstantUTF8Info} instance, or the
	 * {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param methodInfo a {@code MethodInfo} instance
	 * @return a {@code MethodDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, the {@code CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code methodInfo} are {@code null}
	 */
	public static MethodDescriptor parseMethodDescriptor(final ClassFile classFile, final MethodInfo methodInfo) {
		return parseMethodDescriptor(ConstantUTF8Info.findByDescriptorIndex(classFile, methodInfo).getStringValue());
	}
	
	/**
	 * Parses {@code string} into a {@code MethodDescriptor} instance.
	 * <p>
	 * Returns a {@code MethodDescriptor} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code MethodDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static MethodDescriptor parseMethodDescriptor(final String string) {
		return Parsers.parseMethodDescriptor(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code MethodDescriptor} instance with {@code returnDescriptor} and {@code parameterDescriptors} as its associated {@link ReturnDescriptor} and {@link ParameterDescriptor} instances, respectively.
	 * <p>
	 * If either {@code returnDescriptor}, {@code parameterDescriptors} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param returnDescriptor the associated {@code ReturnDescriptor}
	 * @param parameterDescriptors the associated {@code ParameterDescriptor} instances
	 * @return a {@code MethodDescriptor} instance with {@code returnDescriptor} and {@code parameterDescriptors} as its associated {@code ReturnDescriptor} and {@code ParameterDescriptor} instances, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code returnDescriptor}, {@code parameterDescriptors} or any of its elements are {@code null}
	 */
	public static MethodDescriptor valueOf(final ReturnDescriptor returnDescriptor, final ParameterDescriptor... parameterDescriptors) {
		return new MethodDescriptor(Objects.requireNonNull(returnDescriptor, "returnDescriptor == null"), ParameterArguments.requireNonNullArray(parameterDescriptors, "parameterDescriptors"));
	}
}
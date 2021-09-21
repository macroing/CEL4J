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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.MethodParametersAttribute;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ParameterList} represents a list of parameters in a constructor or a method.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ParameterList implements Comparable<ParameterList> {
	private final List<Parameter> parameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ParameterList} instance.
	 * <p>
	 * If either {@code parameters} or at least one of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameters a {@code List} of {@link Parameter} instances
	 * @throws NullPointerException thrown if, and only if, either {@code parameters} or at least one of its elements are {@code null}
	 */
	public ParameterList(final List<Parameter> parameters) {
		this.parameters = ParameterArguments.requireNonNullList(parameters, "parameters");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains all {@link Parameter} instances associated with this {@code ParameterList} instance that are importable.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ParameterList} instance.
	 * 
	 * @return a {@code List} that contains all {@code Parameter} instances associated with this {@code ParameterList} instance that are importable
	 */
	public List<Parameter> getParameters() {
		return new ArrayList<>(this.parameters);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ParameterList} instance.
	 * 
	 * @return a {@code String} representation of this {@code ParameterList} instance
	 */
	@Override
	public String toString() {
		return String.format("new ParameterList(Arrays.asList(%s))", this.parameters.stream().map(parameter -> parameter.toString()).collect(Collectors.joining(", ")));
	}
	
	/**
	 * Compares {@code object} to this {@code ParameterList} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ParameterList}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ParameterList} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ParameterList}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ParameterList)) {
			return false;
		} else if(!Objects.equals(this.parameters, ParameterList.class.cast(object).parameters)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Compares this {@code ParameterList} instance with {@code parameterList} for order.
	 * <p>
	 * Returns a negative integer, zero or a positive integer as this {@code ParameterList} instance is less than, equal to or greater than {@code parameterList}.
	 * <p>
	 * If {@code parameterList} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterList a {@code ParameterList} instance
	 * @return a negative integer, zero or a positive integer as this {@code ParameterList} instance is less than, equal to or greater than {@code parameterList}
	 * @throws NullPointerException thrown if, and only if, {@code parameterList} is {@code null}
	 */
	@Override
	public int compareTo(final ParameterList parameterList) {
		final ParameterList parameterListThis = this;
		final ParameterList parameterListThat = parameterList;
		
		final List<Parameter> parametersThis = parameterListThis.getParameters();
		final List<Parameter> parametersThat = parameterListThat.getParameters();
		
		final int parameterCount = Math.min(parametersThis.size(), parametersThat.size());
		
		for(int i = 0; i < parameterCount; i++) {
			final int parameter = parametersThis.get(i).compareTo(parametersThat.get(i));
			
			if(parameter != 0) {
				return parameter;
			}
		}
		
		if(parametersThis.size() != parametersThat.size()) {
			return parametersThis.size() < parametersThat.size() ? -1 : 1;
		}
		
		return 0;
	}
	
	/**
	 * Returns a hash code for this {@code ParameterList} instance.
	 * 
	 * @return a hash code for this {@code ParameterList} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.parameters);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ParameterList} instance by loading it from {@code classFile} and {@code methodInfo}.
	 * <p>
	 * If either {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If loading fails, either an {@code IllegalArgumentException} or an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param methodInfo a {@link MethodInfo} instance
	 * @return a {@code ParameterList} instance by loading it from {@code classFile} and {@code methodInfo}
	 * @throws IllegalArgumentException thrown if, and only if, loading fails
	 * @throws IndexOutOfBoundsException thrown if, and only if, loading fails
	 */
	public static ParameterList load(final ClassFile classFile, final MethodInfo methodInfo) {
		Objects.requireNonNull(classFile, "classFile == null");
		Objects.requireNonNull(methodInfo, "methodInfo == null");
		
		final List<Parameter> parameters = new ArrayList<>();
		
		final MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(classFile, methodInfo);
		
		final List<ParameterDescriptor> parameterDescriptors = methodDescriptor.getParameterDescriptors();
		
		final Optional<MethodParametersAttribute> optionalMethodParametersAttribute = MethodParametersAttribute.find(methodInfo);
		final Optional<MethodSignature> optionalMethodSignature = MethodSignature.parseMethodSignatureOptionally(classFile, methodInfo);
		
		if(optionalMethodParametersAttribute.isPresent()) {
			final MethodParametersAttribute methodParametersAttribute = optionalMethodParametersAttribute.get();
			
			final List<Boolean> parameterIsFinals = methodParametersAttribute.getParameterIsFinals();
			final List<String> parameterNames = methodParametersAttribute.getParameterNames(classFile);
			
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
				for(int i = 0; i < parameterNames.size(); i++) {
					parameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), parameterNames.get(i), parameterIsFinals.get(i).booleanValue(), javaTypeSignatures.get(i)));
				}
			} else {
				for(int i = 0; i < parameterNames.size(); i++) {
					parameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), parameterNames.get(i), parameterIsFinals.get(i).booleanValue()));
				}
			}
		} else {
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
//				TODO: Find out why parameterDescriptors.size() can be different from javaTypeSignatures.size().
				if(parameterDescriptors.size() == javaTypeSignatures.size()) {
					for(int i = 0; i < parameterDescriptors.size(); i++) {
						parameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), "", false, javaTypeSignatures.get(i)));
					}
				} else {
					for(int i = 0; i < parameterDescriptors.size(); i++) {
						parameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i))));
					}
				}
			} else {
				for(int i = 0; i < parameterDescriptors.size(); i++) {
					parameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i))));
				}
			}
		}
		
		return new ParameterList(parameters);
	}
}
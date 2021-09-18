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
package org.macroing.cel4j.java.decompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.MethodParametersAttribute;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.util.ParameterArguments;

final class ParameterList implements Comparable<ParameterList> {
	private final List<Parameter> parameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ParameterList(final List<Parameter> parameters) {
		this.parameters = ParameterArguments.requireNonNullList(parameters, "parameters");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Parameter> getParameters() {
		return new ArrayList<>(this.parameters);
	}
	
	public String toExternalForm(final DecompilerConfiguration decompilerConfiguration, final Constructor constructor, final List<Type> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<Parameter> parameters = this.parameters;
		
		if(parameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(constructor.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			for(int i = 0; i < parameters.size(); i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(parameters.get(i).toExternalForm(jPackageNameFilter, localVariableNameGenerator, i));
			}
		}
		
		return stringBuilder.toString();
	}
	
	public String toExternalForm(final DecompilerConfiguration decompilerConfiguration, final Method jMethod, final List<Type> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<Parameter> parameters = this.parameters;
		
		if(parameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jMethod.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			for(int i = 0; i < parameters.size(); i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(parameters.get(i).toExternalForm(jPackageNameFilter, localVariableNameGenerator, i));
			}
		}
		
		return stringBuilder.toString();
	}
	
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ParameterList load(final ClassFile classFile, final MethodInfo methodInfo) {
		Objects.requireNonNull(classFile, "classFile == null");
		Objects.requireNonNull(methodInfo, "methodInfo == null");
		
		final List<Parameter> jParameters = new ArrayList<>();
		
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
					jParameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), parameterNames.get(i), parameterIsFinals.get(i).booleanValue(), javaTypeSignatures.get(i)));
				}
			} else {
				for(int i = 0; i < parameterNames.size(); i++) {
					jParameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), parameterNames.get(i), parameterIsFinals.get(i).booleanValue()));
				}
			}
		} else {
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
//				TODO: Find out why parameterDescriptors.size() can be different from javaTypeSignatures.size().
				if(parameterDescriptors.size() == javaTypeSignatures.size()) {
					for(int i = 0; i < parameterDescriptors.size(); i++) {
						jParameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i)), "", false, javaTypeSignatures.get(i)));
					}
				} else {
					for(int i = 0; i < parameterDescriptors.size(); i++) {
						jParameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i))));
					}
				}
			} else {
				for(int i = 0; i < parameterDescriptors.size(); i++) {
					jParameters.add(new Parameter(Type.valueOf(parameterDescriptors.get(i))));
				}
			}
		}
		
		return new ParameterList(jParameters);
	}
}
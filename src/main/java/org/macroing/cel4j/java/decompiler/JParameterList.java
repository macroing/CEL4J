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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Parameter;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.util.ParameterArguments;

final class JParameterList implements Comparable<JParameterList> {
	private final List<JParameter> parameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public JParameterList(final List<JParameter> parameters) {
		this.parameters = ParameterArguments.requireNonNullList(parameters, "parameters");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<JParameter> getParameters() {
		return new ArrayList<>(this.parameters);
	}
	
	public String toExternalForm(final DecompilerConfiguration decompilerConfiguration, final JMethod jMethod, final List<JType> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<JParameter> parameters = this.parameters;
		
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
	public int compareTo(final JParameterList parameterList) {
		final JParameterList parameterListThis = this;
		final JParameterList parameterListThat = parameterList;
		
		final List<JParameter> parametersThis = parameterListThis.getParameters();
		final List<JParameter> parametersThat = parameterListThat.getParameters();
		
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
	
	public static JParameterList load(final ClassFile classFile, final MethodInfo methodInfo) {
		Objects.requireNonNull(classFile, "classFile == null");
		Objects.requireNonNull(methodInfo, "methodInfo == null");
		
		final List<JParameter> jParameters = new ArrayList<>();
		
		final MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(classFile, methodInfo);
		
		final List<ParameterDescriptor> parameterDescriptors = methodDescriptor.getParameterDescriptors();
		
		final Optional<MethodParametersAttribute> optionalMethodParametersAttribute = MethodParametersAttribute.find(methodInfo);
		final Optional<MethodSignature> optionalMethodSignature = MethodSignature.parseMethodSignatureOptionally(classFile, methodInfo);
		
		if(optionalMethodParametersAttribute.isPresent()) {
			final MethodParametersAttribute methodParametersAttribute = optionalMethodParametersAttribute.get();
			
			final List<Parameter> parameters = methodParametersAttribute.getParameters();
			
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
				for(int i = 0; i < parameters.size(); i++) {
					jParameters.add(JParameter.valueOf(javaTypeSignatures.get(i), parameterDescriptors.get(i), classFile, parameters.get(i)));
				}
			} else {
				for(int i = 0; i < parameters.size(); i++) {
					jParameters.add(JParameter.valueOf(parameterDescriptors.get(i), classFile, parameters.get(i)));
				}
			}
		} else {
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
				for(int i = 0; i < parameterDescriptors.size(); i++) {
					jParameters.add(JParameter.valueOf(javaTypeSignatures.get(i), parameterDescriptors.get(i)));
				}
			} else {
				for(int i = 0; i < parameterDescriptors.size(); i++) {
					jParameters.add(JParameter.valueOf(parameterDescriptors.get(i)));
				}
			}
		}
		
		return new JParameterList(jParameters);
	}
}
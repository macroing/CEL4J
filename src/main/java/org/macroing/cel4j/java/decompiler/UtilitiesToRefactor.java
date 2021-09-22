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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.FieldSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.java.binary.classfile.signature.Result;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.java.model.ClassType;
import org.macroing.cel4j.java.model.Constructor;
import org.macroing.cel4j.java.model.Field;
import org.macroing.cel4j.java.model.InterfaceType;
import org.macroing.cel4j.java.model.Method;
import org.macroing.cel4j.java.model.Parameter;
import org.macroing.cel4j.java.model.ParameterList;
import org.macroing.cel4j.java.model.PrimitiveType;
import org.macroing.cel4j.java.model.Type;
import org.macroing.cel4j.java.model.VoidType;

final class UtilitiesToRefactor {
	private static final Pattern PATTERN_FULLY_QUALIFIED_TYPE_NAME = Pattern.compile("(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(\\.(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private UtilitiesToRefactor() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String generateAssignment(final Field field) {
		final Optional<Object> optionalAssignment = field.getAssignment();
		
		if(optionalAssignment.isPresent()) {
			final Object assignment = optionalAssignment.get();
			
			if(assignment instanceof Double) {
				return " = " + assignment + "D";
			} else if(assignment instanceof Float) {
				return " = " + assignment + "F";
			} else if(assignment instanceof Long) {
				return " = " + assignment + "L";
			} else if(assignment instanceof String) {
				return " = \"" + assignment + "\"";
			} else {
				return " = " + assignment;
			}
		}
		
		return "";
	}
	
	public static String generateDefaultReturnStatement(final Method method) {
		final Type returnType = method.getReturnType();
		
		if(returnType instanceof VoidType) {
			return "";
		} else if(returnType instanceof PrimitiveType) {
			final PrimitiveType primitiveType = PrimitiveType.class.cast(returnType);
			
			if(primitiveType.equals(PrimitiveType.BOOLEAN)) {
				return "return false;";
			} else if(primitiveType.equals(PrimitiveType.BYTE)) {
				return "return 0;";
			} else if(primitiveType.equals(PrimitiveType.CHAR)) {
				return "return '\\u0000';";
			} else if(primitiveType.equals(PrimitiveType.DOUBLE)) {
				return "return 0.0D;";
			} else if(primitiveType.equals(PrimitiveType.FLOAT)) {
				return "return 0.0F;";
			} else if(primitiveType.equals(PrimitiveType.INT)) {
				return "return 0;";
			} else if(primitiveType.equals(PrimitiveType.LONG)) {
				return "return 0L;";
			} else {
				return "return 0;";
			}
		} else {
			return "return null;";
		}
	}
	
	public static String generateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final ClassType classType, final List<Type> importableTypes) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		classType.getOptionalSuperClassType().ifPresent(superClass -> {
			if(!(isDiscardingExtendsObject && superClass.isObject())) {
				stringBuilder.append(" ");
				stringBuilder.append("extends");
				stringBuilder.append(" ");
				
				final Optional<ClassSignature> optionalClassSignature = classType.getOptionalClassSignature();
				
				final String string0 = optionalClassSignature.isPresent() ? optionalClassSignature.get().getSuperClassSignature().toExternalForm() : superClass.getExternalName();
				final String string1 = isDiscardingUnnecessaryPackageNames ? doFilterPackageNames(JPackageNameFilter.newUnnecessaryPackageName(classType.getExternalPackageName(), isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes), string0) : string0;
				
				stringBuilder.append(string1);
			}
		});
		
		return stringBuilder.toString();
	}
	
	public static String generateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final List<InterfaceType> interfaceTypes, final List<Type> importableTypes, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(interfaceTypes.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("extends");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes);
			
			if(optionalClassSignature.isPresent()) {
				final ClassSignature classSignature = optionalClassSignature.get();
				
				final List<SuperInterfaceSignature> superInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
				
				for(int i = 0; i < superInterfaceSignatures.size(); i++) {
					final SuperInterfaceSignature superInterfaceSignature = superInterfaceSignatures.get(i);
					
					final String string0 = superInterfaceSignature.toExternalForm();
					final String string1 = isDiscardingUnnecessaryPackageNames ? doFilterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			} else {
				for(int i = 0; i < interfaceTypes.size(); i++) {
					final String string0 = interfaceTypes.get(i).getExternalName();
					final String string1 = isDiscardingUnnecessaryPackageNames ? doFilterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String generateImplementsClause(final DecompilerConfiguration decompilerConfiguration, final List<InterfaceType> interfaceTypes, final List<Type> importableTypes, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(interfaceTypes.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("implements");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes);
			
			if(optionalClassSignature.isPresent()) {
				final ClassSignature classSignature = optionalClassSignature.get();
				
				final List<SuperInterfaceSignature> superInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
				
				for(int i = 0; i < superInterfaceSignatures.size(); i++) {
					final SuperInterfaceSignature superInterfaceSignature = superInterfaceSignatures.get(i);
					
					final String string0 = superInterfaceSignature.toExternalForm();
					final String string1 = isDiscardingUnnecessaryPackageNames ? doFilterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			} else {
				for(int i = 0; i < interfaceTypes.size(); i++) {
					final String string0 = interfaceTypes.get(i).getExternalName();
					final String string1 = isDiscardingUnnecessaryPackageNames ? doFilterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String generateReturnTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final Method method, final List<Type> importableTypes) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = method.getMethodSignature();
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(method.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes);
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final Result result = methodSignature.getResult();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final String string = isDiscardingExtendsObject ? typeParameters.toExternalForm().replaceAll(" extends java\\.lang\\.Object", "") : typeParameters.toExternalForm();
				
				stringBuilder.append(doFilterPackageNames(jPackageNameFilter, string));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(doFilterPackageNames(jPackageNameFilter, result.toExternalForm(), method.getReturnType().isInnerType()));
			
			return stringBuilder.toString();
		}
		
		return doFilterPackageNames(jPackageNameFilter, method.getReturnType().getExternalName(), method.getReturnType().isInnerType());
	}
	
	public static String generateType(final DecompilerConfiguration decompilerConfiguration, final Field field) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<FieldSignature> optionalFieldSignature = field.getFieldSignature();
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(field.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
		
		if(optionalFieldSignature.isPresent()) {
			final FieldSignature fieldSignature = optionalFieldSignature.get();
			
			return doFilterPackageNames(jPackageNameFilter, fieldSignature.toExternalForm());
		}
		
		return doFilterPackageNames(jPackageNameFilter, field.getType().getExternalName());
	}
	
	public static String generateTypeParameters(final DecompilerConfiguration decompilerConfiguration, final List<Type> importableTypes, final Optional<TypeParameters> optionalTypeParameters) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		if(optionalTypeParameters.isPresent()) {
			final TypeParameters typeParameters = optionalTypeParameters.get();
			
			final String typeParametersToExternalForm = typeParameters.toExternalForm((packageName0, simpleName) -> {
				if(packageName0.equals("java.lang.")) {
					if(simpleName.equals("Object")) {
						return isDiscardingExtendsObject ? null : isDiscardingUnnecessaryPackageNames ? "Object" : packageName0 + simpleName;
					} else if(isDiscardingUnnecessaryPackageNames) {
						return simpleName;
					}
				}
				
				if(isImportingTypes) {
					for(final Type importableType : importableTypes) {
						if(importableType.getExternalName().equals(packageName0 + simpleName)) {
							return simpleName;
						}
					}
				}
				
				return packageName0 + simpleName;
			});
			
			return typeParametersToExternalForm;
		}
		
		return "";
	}
	
	public static String generateTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final Constructor constructor, final String simpleName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = constructor.getOptionalMethodSignature();
		
		final Type enclosingType = constructor.getEnclosingType();
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(enclosingType.getExternalPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
				
				stringBuilder.append(doFilterPackageNames(jPackageNameFilter, typeParameters.toExternalForm()));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(Objects.requireNonNull(simpleName, "simpleName == null"));
			
			return stringBuilder.toString();
		}
		
		return Objects.requireNonNull(simpleName, "simpleName == null");
	}
	
	public static String toExternalForm(final DecompilerConfiguration decompilerConfiguration, final ParameterList parameterList, final Constructor constructor, final List<Type> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<Parameter> parameters = parameterList.getParameters();
		
		if(parameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(constructor.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			for(int i = 0; i < parameters.size(); i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(doToExternalForm(jPackageNameFilter, localVariableNameGenerator, parameters.get(i), i));
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String toExternalForm(final DecompilerConfiguration decompilerConfiguration, final ParameterList parameterList, final Method jMethod, final List<Type> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<Parameter> parameters = parameterList.getParameters();
		
		if(parameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jMethod.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			for(int i = 0; i < parameters.size(); i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(doToExternalForm(jPackageNameFilter, localVariableNameGenerator, parameters.get(i), i));
			}
		}
		
		return stringBuilder.toString();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doFilterPackageNames(final JPackageNameFilter jPackageNameFilter, final String string) {
		return doFilterPackageNames(jPackageNameFilter, string, false);
	}
	
	private static String doFilterPackageNames(final JPackageNameFilter jPackageNameFilter, final String string, final boolean isInnerType) {
		final Matcher matcher = PATTERN_FULLY_QUALIFIED_TYPE_NAME.matcher(string);
		
		final StringBuffer stringBuffer = new StringBuffer();
		
		while(matcher.find()) {
			final String fullyQualifiedName = matcher.group();
			final String packageName = doGetPackageName(fullyQualifiedName);
			final String simpleName = doGetSimpleName(fullyQualifiedName, isInnerType);
			
			if(!jPackageNameFilter.isAccepted(packageName, simpleName)) {
				matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(simpleName));
			}
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	private static String doGetPackageName(final String fullyQualifiedTypeName) {
		return fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(0, fullyQualifiedTypeName.lastIndexOf(".")) : "";
	}
	
	private static String doGetSimpleName(final String fullyQualifiedTypeName, final boolean isInnerType) {
		final String simpleName0 = fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(fullyQualifiedTypeName.lastIndexOf(".") + 1) : fullyQualifiedTypeName;
		final String simpleName1 = isInnerType && simpleName0.lastIndexOf('$') >= 0 ? simpleName0.substring(simpleName0.lastIndexOf('$') + 1) : simpleName0;
		
		return simpleName1;
	}
	
	private static String doToExternalForm(final JPackageNameFilter jPackageNameFilter, final LocalVariableNameGenerator localVariableNameGenerator, final Parameter parameter, final int index) {
		final String a = parameter.isFinal() ? "final " : "";
		final String b = doFilterPackageNames(jPackageNameFilter, parameter.getOptionalJavaTypeSignature().map(javaTypeSignature -> javaTypeSignature.toExternalForm()).orElse(parameter.getType().getExternalName()));
		final String c = parameter.isNamed() ? parameter.getName() : localVariableNameGenerator.generateLocalVariableName(parameter.getType().getExternalName(), index);
		
		return String.format("%s%s %s", a, b, c);
	}
}
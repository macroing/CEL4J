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

import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.FieldSignature;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.java.binary.classfile.signature.Result;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;

final class UtilitiesToRefactor {
	private UtilitiesToRefactor() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String generateAssignment(final JField jField) {
		final Optional<Object> optionalAssignment = jField.getAssignment();
		
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
	
	public static String generateDefaultReturnStatement(final JMethod jMethod) {
		final JType returnType = jMethod.getReturnType();
		
		if(returnType instanceof JVoid) {
			return "";
		} else if(returnType instanceof JPrimitive) {
			final JPrimitive jPrimitive = JPrimitive.class.cast(returnType);
			
			if(jPrimitive.equals(JPrimitive.BOOLEAN)) {
				return "return false;";
			} else if(jPrimitive.equals(JPrimitive.BYTE)) {
				return "return 0;";
			} else if(jPrimitive.equals(JPrimitive.CHAR)) {
				return "return '\\u0000';";
			} else if(jPrimitive.equals(JPrimitive.DOUBLE)) {
				return "return 0.0D;";
			} else if(jPrimitive.equals(JPrimitive.FLOAT)) {
				return "return 0.0F;";
			} else if(jPrimitive.equals(JPrimitive.INT)) {
				return "return 0;";
			} else if(jPrimitive.equals(JPrimitive.LONG)) {
				return "return 0L;";
			} else {
				return "return 0;";
			}
		} else {
			return "return null;";
		}
	}
	
	public static String generateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final JClass jClass) {
		return generateExtendsClause(decompilerConfiguration, jClass, new ArrayList<>());
	}
	
	public static String generateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final JClass jClass, final List<JType> typesToImport) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		jClass.getSuperClass().ifPresent(superClass -> {
			if(!(isDiscardingExtendsObject && superClass.isObject())) {
				stringBuilder.append(" ");
				stringBuilder.append("extends");
				stringBuilder.append(" ");
				
				final Optional<ClassSignature> optionalClassSignature = jClass.getClassSignature();
				
				final String string0 = optionalClassSignature.isPresent() ? optionalClassSignature.get().getSuperClassSignature().toExternalForm() : superClass.getName();
				final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(JPackageNameFilter.newUnnecessaryPackageName(jClass.getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes), string0) : string0;
				
				stringBuilder.append(string1);
			}
		});
		
		return stringBuilder.toString();
	}
	
	public static String generateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final List<JInterface> jInterfaces, final List<JType> typesToImport, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(jInterfaces.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("extends");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			if(optionalClassSignature.isPresent()) {
				final ClassSignature classSignature = optionalClassSignature.get();
				
				final List<SuperInterfaceSignature> superInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
				
				for(int i = 0; i < superInterfaceSignatures.size(); i++) {
					final SuperInterfaceSignature superInterfaceSignature = superInterfaceSignatures.get(i);
					
					final String string0 = superInterfaceSignature.toExternalForm();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			} else {
				for(int i = 0; i < jInterfaces.size(); i++) {
					final String string0 = jInterfaces.get(i).getName();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String generateImplementsClause(final DecompilerConfiguration decompilerConfiguration, final List<JInterface> jInterfaces, final List<JType> typesToImport, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(jInterfaces.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("implements");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			if(optionalClassSignature.isPresent()) {
				final ClassSignature classSignature = optionalClassSignature.get();
				
				final List<SuperInterfaceSignature> superInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
				
				for(int i = 0; i < superInterfaceSignatures.size(); i++) {
					final SuperInterfaceSignature superInterfaceSignature = superInterfaceSignatures.get(i);
					
					final String string0 = superInterfaceSignature.toExternalForm();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			} else {
				for(int i = 0; i < jInterfaces.size(); i++) {
					final String string0 = jInterfaces.get(i).getName();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String generateParameters(final DecompilerConfiguration decompilerConfiguration, final JConstructor jConstructor) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final JLocalVariableNameGenerator jLocalVariableNameGenerator = (type, index) -> decompilerConfiguration.getLocalVariableNameGenerator().generateLocalVariableName(type.getName(), index);
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final Optional<MethodSignature> optionalMethodSignature = jConstructor.getMethodSignature();
		
		final List<JParameter> jParameters = jConstructor.getParameters(jLocalVariableNameGenerator);
		
		if(jParameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jConstructor.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
			
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
				for(int i = 0; i < javaTypeSignatures.size(); i++) {
					final JParameter jParameter = jParameters.get(i);
					
					final JavaTypeSignature javaTypeSignature = javaTypeSignatures.get(i);
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(jParameter.isFinal() ? "final " : "");
					stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, javaTypeSignature.toExternalForm()));
					stringBuilder.append(" ");
					stringBuilder.append(jParameter.getName());
				}
			} else {
				for(int i = 0; i < jParameters.size(); i++) {
					final JParameter jParameter = jParameters.get(i);
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(jParameter.isFinal() ? "final " : "");
					stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, jParameter.getType().getName()));
					stringBuilder.append(" ");
					stringBuilder.append(jParameter.getName());
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static String generateReturnTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final JMethod jMethod, final List<JType> typesToImport) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = jMethod.getMethodSignature();
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jMethod.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final Result result = methodSignature.getResult();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final String string = isDiscardingExtendsObject ? typeParameters.toExternalForm().replaceAll(" extends java\\.lang\\.Object", "") : typeParameters.toExternalForm();
				
				stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, string));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, result.toExternalForm(), jMethod.getReturnType().isInnerType()));
			
			return stringBuilder.toString();
		}
		
		return Names.filterPackageNames(jPackageNameFilter, jMethod.getReturnType().getName(), jMethod.getReturnType().isInnerType());
	}
	
	public static String generateType(final DecompilerConfiguration decompilerConfiguration, final JField jField) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<FieldSignature> optionalFieldSignature = jField.getFieldSignature();
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jField.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
		
		if(optionalFieldSignature.isPresent()) {
			final FieldSignature fieldSignature = optionalFieldSignature.get();
			
			return Names.filterPackageNames(jPackageNameFilter, fieldSignature.toExternalForm());
		}
		
		return Names.filterPackageNames(jPackageNameFilter, jField.getType().getName());
	}
	
	public static String generateTypeParameters(final DecompilerConfiguration decompilerConfiguration, final List<JType> typesToImport, final Optional<TypeParameters> optionalTypeParameters) {
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
					for(final JType typeToImport : typesToImport) {
						if(typeToImport.getName().equals(packageName0 + simpleName)) {
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
	
	public static String generateTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final JConstructor jConstructor, final String simpleName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = jConstructor.getMethodSignature();
		
		final JType enclosingType = jConstructor.getEnclosingType();
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(enclosingType.getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
				
				stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, typeParameters.toExternalForm()));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(Objects.requireNonNull(simpleName, "simpleName == null"));
			
			return stringBuilder.toString();
		}
		
		return Objects.requireNonNull(simpleName, "simpleName == null");
	}
}
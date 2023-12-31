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
package org.macroing.cel4j.java.decompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.FieldSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.java.binary.classfile.signature.Result;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.java.model.AnnotationType;
import org.macroing.cel4j.java.model.ClassType;
import org.macroing.cel4j.java.model.Constructor;
import org.macroing.cel4j.java.model.EnumType;
import org.macroing.cel4j.java.model.Field;
import org.macroing.cel4j.java.model.InnerType;
import org.macroing.cel4j.java.model.InterfaceType;
import org.macroing.cel4j.java.model.Method;
import org.macroing.cel4j.java.model.Modifier;
import org.macroing.cel4j.java.model.Parameter;
import org.macroing.cel4j.java.model.ParameterList;
import org.macroing.cel4j.java.model.PrimitiveType;
import org.macroing.cel4j.java.model.Type;
import org.macroing.cel4j.java.model.VoidType;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class SourceCodeGenerator {
	private static final Pattern PATTERN_FULLY_QUALIFIED_TYPE_NAME = Pattern.compile("(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(\\.(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final DecompilerConfiguration decompilerConfiguration;
	private final Document document;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public SourceCodeGenerator(final DecompilerConfiguration decompilerConfiguration) {
		this.decompilerConfiguration = Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		this.document = new Document();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return this.document.toString();
	}
	
	public void generate(final Type type) {
		if(this.decompilerConfiguration.isDisplayingConfigurationParameters()) {
			doGenerateTraditionalComment(doGenerateTraditionalCommentAtTop(type));
		}
		
		if(type instanceof AnnotationType) {
			doGenerateAnnotationType(AnnotationType.class.cast(type));
		} else if(type instanceof ClassType) {
			doGenerateClassType(ClassType.class.cast(type));
		} else if(type instanceof EnumType) {
			doGenerateEnumType(EnumType.class.cast(type));
		} else if(type instanceof InterfaceType) {
			doGenerateInterfaceType(InterfaceType.class.cast(type));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<Modifier> doDiscardInterfaceMethodModifiers(final Type enclosingType, final List<Modifier> oldModifiers) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isDiscardingAbstractInterfaceMethodModifier = decompilerConfiguration.isDiscardingAbstractInterfaceMethodModifier();
		final boolean isDiscardingPublicInterfaceMethodModifier = decompilerConfiguration.isDiscardingPublicInterfaceMethodModifier();
		
		final List<Modifier> newModifiers = new ArrayList<>();
		
		for(final Modifier oldModifier : oldModifiers) {
			if(enclosingType instanceof InterfaceType && oldModifier == Modifier.ABSTRACT && isDiscardingAbstractInterfaceMethodModifier) {
				continue;
			} else if(enclosingType instanceof InterfaceType && oldModifier == Modifier.PUBLIC && isDiscardingPublicInterfaceMethodModifier) {
				continue;
			} else {
				newModifiers.add(oldModifier);
			}
		}
		
		return newModifiers;
	}
	
	private String doGenerateClassTypeExtendsClause(final ClassType classType, final List<Type> importableTypes) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateConstructorThrowsClause(final Constructor constructor, final List<Type> importableTypes) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final List<Type> exceptionTypes = constructor.getExceptionTypes();
		
		if(exceptionTypes.isEmpty()) {
			return "";
		}
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(constructor.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes);
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("throws");
		stringBuilder.append(" ");
		
		for(int i = 0; i < exceptionTypes.size(); i++) {
			final Type exceptionType = exceptionTypes.get(i);
			
			stringBuilder.append(i > 0 ? ", " : "");
			stringBuilder.append(doFilterPackageNames(jPackageNameFilter, exceptionType.getExternalName(), exceptionType.isInnerType()));
		}
		
		return stringBuilder.toString();
	}
	
	private String doGenerateConstructorTypeWithOptionalTypeParameters(final Constructor constructor, final String simpleName) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateFieldType(final Field field) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateInterfaceTypeExtendsClause(final List<InterfaceType> interfaceTypes, final List<Type> importableTypes, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateInterfaceTypeImplementsClause(final List<InterfaceType> interfaceTypes, final List<Type> importableTypes, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateMethodReturnTypeWithOptionalTypeParameters(final Method method, final List<Type> importableTypes) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doGenerateMethodThrowsClause(final Method method, final List<Type> importableTypes) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final List<Type> exceptionTypes = method.getExceptionTypes();
		
		if(exceptionTypes.isEmpty()) {
			return "";
		}
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(method.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, importableTypes, isImportingTypes);
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("throws");
		stringBuilder.append(" ");
		
		for(int i = 0; i < exceptionTypes.size(); i++) {
			final Type exceptionType = exceptionTypes.get(i);
			
			stringBuilder.append(i > 0 ? ", " : "");
			stringBuilder.append(doFilterPackageNames(jPackageNameFilter, exceptionType.getExternalName(), exceptionType.isInnerType()));
		}
		
		return stringBuilder.toString();
	}
	
	private String doGenerateTraditionalCommentAtTop(final Type type) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%s decompiled by CEL4J Java Decompiler.", type.getExternalName()));
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<AnnotatingDeprecatedMethods>: %s", Boolean.toString(decompilerConfiguration.isAnnotatingDeprecatedMethods())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<AnnotatingOverriddenMethods>: %s", Boolean.toString(decompilerConfiguration.isAnnotatingOverriddenMethods())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingAbstractInterfaceMethodModifier>: %s", Boolean.toString(decompilerConfiguration.isDiscardingAbstractInterfaceMethodModifier())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingExtendsObject>: %s", Boolean.toString(decompilerConfiguration.isDiscardingExtendsObject())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingPublicInterfaceMethodModifier>: %s", Boolean.toString(decompilerConfiguration.isDiscardingPublicInterfaceMethodModifier())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingUnnecessaryPackageNames>: %s", Boolean.toString(decompilerConfiguration.isDiscardingUnnecessaryPackageNames())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingAttributeInfos>: %s", Boolean.toString(decompilerConfiguration.isDisplayingAttributeInfos())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingConfigurationParameters>: %s", Boolean.toString(decompilerConfiguration.isDisplayingConfigurationParameters())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingInstructions>: %s", Boolean.toString(decompilerConfiguration.isDisplayingInstructions())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<ImportingTypes>: %s", Boolean.toString(decompilerConfiguration.isImportingTypes())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<SeparatingGroups>: %s", Boolean.toString(decompilerConfiguration.isSeparatingGroups())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<SortingGroups>: %s", Boolean.toString(decompilerConfiguration.isSortingGroups())));
		
		return stringBuilder.toString();
	}
	
	private String doGenerateTypeParameters(final List<Type> importableTypes, final Optional<TypeParameters> optionalTypeParameters) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doToExternalForm(final ParameterList parameterList, final Constructor constructor, final List<Type> typesToImport) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
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
	
	private String doToExternalForm(final ParameterList parameterList, final Method method, final List<Type> typesToImport) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final LocalVariableNameGenerator localVariableNameGenerator = decompilerConfiguration.getLocalVariableNameGenerator();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final List<Parameter> parameters = parameterList.getParameters();
		
		if(parameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(method.getEnclosingType().getExternalPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			for(int i = 0; i < parameters.size(); i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(doToExternalForm(jPackageNameFilter, localVariableNameGenerator, parameters.get(i), i));
			}
		}
		
		return stringBuilder.toString();
	}
	
	private void doGenerateAnnotationType(final AnnotationType annotationType) {
		final String packageName = annotationType.getExternalPackageName();
		final String modifiers = Modifier.toExternalForm(annotationType.getModifiers());
		final String simpleName = annotationType.getExternalSimpleName();
		
		final
		Document document = this.document;
		document.linef("package %s;", packageName);
		document.linef("");
		document.linef("%s@interface %s {", modifiers, simpleName);
		document.indent();
		document.outdent();
		document.line("}");
	}
	
	private void doGenerateClassType(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean hasSeparatorA = (classType.hasFields()) && (classType.hasConstructors() || classType.hasMethods() || classType.hasInnerTypes());
		final boolean hasSeparatorB = (classType.hasFields() || classType.hasConstructors()) && (classType.hasMethods() || classType.hasInnerTypes());
		final boolean hasSeparatorC = (classType.hasFields() || classType.hasConstructors() || classType.hasMethods()) && (classType.hasInnerTypes());
		final boolean hasSeparatorG = decompilerConfiguration.isSeparatingGroups() && decompilerConfiguration.isSortingGroups();
		
		doGenerateClassTypePackageDeclaration(classType);
		doGenerateClassTypeImportDeclarations(classType);
		doGenerateClassTypeComment(classType);
		doGenerateClassTypeClassDeclarationTop(classType);
		doGenerateClassTypeFields(classType);
		doGenerateSeparator(hasSeparatorA, hasSeparatorA && hasSeparatorG, hasSeparatorA && hasSeparatorG);
		doGenerateClassTypeConstructors(classType);
		doGenerateSeparator(hasSeparatorB, hasSeparatorB && hasSeparatorG, hasSeparatorB && hasSeparatorG);
		doGenerateClassTypeMethods(classType);
		doGenerateSeparator(hasSeparatorC, hasSeparatorC && hasSeparatorG, hasSeparatorC && hasSeparatorG);
		doGenerateClassTypeInnerTypes(classType);
		doGenerateClassTypeClassDeclarationBottom();
	}
	
	private void doGenerateClassTypeClassDeclarationBottom() {
		final
		Document document = this.document;
		document.outdent();
		document.line("}");
	}
	
	private void doGenerateClassTypeClassDeclarationTop(final ClassType classType) {
		final List<Type> importableTypes = classType.getImportableTypes();
		
		final String modifiers = Modifier.toExternalForm(classType.getModifiers());
		final String simpleName = classType.getExternalSimpleName();
		final String typeParameters = doGenerateTypeParameters(importableTypes, classType.getOptionalTypeParameters());
		final String extendsClause = doGenerateClassTypeExtendsClause(classType, importableTypes);
		final String implementsClause = doGenerateInterfaceTypeImplementsClause(classType.getInterfaceTypes(), importableTypes, classType.getOptionalClassSignature(), classType.getExternalPackageName());
		
		final
		Document document = this.document;
		document.linef("%sclass %s%s%s%s {", modifiers, simpleName, typeParameters, extendsClause, implementsClause);
		document.indent();
	}
	
	private void doGenerateClassTypeComment(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final List<AttributeInfo> attributeInfos = classType.getAttributeInfos();
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos() && attributeInfos.size() > 0;
		
		if(isDisplayingAttributeInfos) {
			final
			Document document = this.document;
			document.line("/*");
			
			for(final AttributeInfo attributeInfo : attributeInfos) {
				document.linef(" * %s", attributeInfo.getName());
			}
			
			document.line(" */");
		}
	}
	
	private void doGenerateClassTypeConstructors(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		final boolean isSortingGroups = decompilerConfiguration.isSortingGroups();
		
		final List<Constructor> constructors = isSortingGroups ? classType.getConstructorsSorted() : classType.getConstructors();
		
		for(int i = 0; i < constructors.size(); i++) {
			doGenerateSeparator(i > 0, false, false);
			
			final Constructor constructorA = constructors.get(i);
			final Constructor constructorB = constructors.get(i + 1 < constructors.size() ? i + 1 : i);
			
			doGenerateConstructor(constructorA);
			
			if(isSeparatingGroups && isSortingGroups && Constructor.inDifferentGroups(constructorA, constructorB)) {
				doGenerateSeparator(true, true, false);
			}
		}
	}
	
	private void doGenerateClassTypeFields(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos();
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		final boolean isSortingGroups = decompilerConfiguration.isSortingGroups();
		
		final List<Field> fields = isSortingGroups ? classType.getFieldsSorted() : classType.getFields();
		
		for(int i = 0; i < fields.size(); i++) {
			final Field fieldA = fields.get(i);
			final Field fieldB = fields.get(i + 1 < fields.size() ? i + 1 : i);
			
			doGenerateField(fieldA);
			
			if(isSeparatingGroups && isSortingGroups && Field.inDifferentGroups(fieldA, fieldB)) {
				doGenerateSeparator(true, true, true);
			} else if(fieldA != fieldB && isDisplayingAttributeInfos && fieldB.getAttributeInfoCount() > 0) {
				doGenerateSeparator(true, false, false);
			}
		}
	}
	
	private void doGenerateClassTypeImportDeclarations(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final List<Type> importableTypes = classType.getImportableTypes();
		
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes() && importableTypes.size() > 0;
		
		if(isImportingTypes) {
			final Document document = this.document;
			
			for(final Type importableType : importableTypes) {
				document.linef("import %s;", importableType.getExternalName());
			}
			
			document.line();
		}
	}
	
	private void doGenerateClassTypeInnerTypes(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final List<InnerType> innerTypes = classType.getInnerTypes();
		
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		for(int i = 0; i < innerTypes.size(); i++) {
			doGenerateSeparator(i > 0, i > 0 && isSeparatingGroups, i > 0 && isSeparatingGroups);
			doGenerateInnerType(innerTypes.get(i));
		}
	}
	
	private void doGenerateClassTypeMethods(final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		final boolean isSortingGroups = decompilerConfiguration.isSortingGroups();
		
		final List<Method> methods = isSortingGroups ? classType.getMethodsSorted() : classType.getMethods();
		
		for(int i = 0; i < methods.size(); i++) {
			doGenerateSeparator(i > 0, false, false);
			
			final Method methodA = methods.get(i);
			final Method methodB = methods.get(i + 1 < methods.size() ? i + 1 : i);
			
			doGenerateMethod(methodA);
			
			if(isSeparatingGroups && isSortingGroups && Method.inDifferentGroups(methodA, methodB)) {
				doGenerateSeparator(true, true, false);
			}
		}
	}
	
	private void doGenerateClassTypePackageDeclaration(final ClassType classType) {
		final
		Document document = this.document;
		document.linef("package %s;", classType.getExternalPackageName());
		document.linef("");
	}
	
	private void doGenerateConstructor(final Constructor constructor) {
		final Document document = this.document;
		
		final ParameterList parameterList = constructor.getParameterList();
		
		final List<Type> importableTypes = constructor.getImportableTypes();
		
		final String simpleName = constructor.getEnclosingType().getExternalSimpleName();
		final String modifiers = Modifier.toExternalForm(constructor.getModifiers());
		final String type = doGenerateConstructorTypeWithOptionalTypeParameters(constructor, simpleName);
		final String parameters = doToExternalForm(parameterList, constructor, new ArrayList<>());
		final String throwsClause = doGenerateConstructorThrowsClause(constructor, importableTypes);
		
		doGenerateConstructorComment(constructor);
		
		if(constructor.isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		document.linef("%s%s(%s)%s {", modifiers, type, parameters, throwsClause);
		document.indent();
		document.line();
		document.outdent();
		document.linef("}");
	}
	
	private void doGenerateConstructorComment(final Constructor constructor) {
		final ClassFile classFile = constructor.getClassFile();
		
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final List<AttributeInfo> attributeInfos = constructor.getAttributeInfos();
		final List<Instruction> instructions = constructor.getInstructions();
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos() && attributeInfos.size() > 0;
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions() && instructions.size() > 0;
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.line("/*");
		}
		
		if(isDisplayingAttributeInfos) {
			for(final AttributeInfo attributeInfo : attributeInfos) {
				document.linef(" * %s", attributeInfo.getName());
			}
		}
		
		if(isDisplayingAttributeInfos && isDisplayingInstructions) {
			document.line(" * ");
		}
		
		if(isDisplayingInstructions) {
			document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", "Mnemonic", "Index", "Opcode (Hex.)", "Opcode (Dec.)", "Operands", "Branch Offsets", "Data");
			document.linef(" * ");
			
			final AtomicInteger index = new AtomicInteger();
			
			for(final Instruction instruction : instructions) {
				final String mnemonic = instruction.getMnemonic();
				final String indexAsString = String.format("%04d", Integer.valueOf(index.get()));
				final String opcodeHex = String.format("0x%02X", Integer.valueOf(instruction.getOpcode()));
				final String opcodeDec = String.format("%03d", Integer.valueOf(instruction.getOpcode()));
				final String operands = Strings.optional(IntStream.of(instruction.getOperands()).boxed().collect(Collectors.toList()), "{", "}", ", ");
				final String branchOffsets = Arrays.toString(instruction.getBranchOffsets(index.get()));
				final String description = Instructions.toString(classFile, instruction);
				
				document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", mnemonic, indexAsString, opcodeHex, opcodeDec, operands, branchOffsets, description);
				
				index.addAndGet(instruction.getLength());
			}
		}
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.linef(" */");
		}
	}
	
	private void doGenerateEnumType(final EnumType enumType) {
		final String packageName = enumType.getExternalPackageName();
		final String modifiers = Modifier.toExternalForm(enumType.getModifiers());
		final String simpleName = enumType.getExternalSimpleName();
		
		final
		Document document = this.document;
		document.linef("package %s;", packageName);
		document.linef("");
		document.linef("%senum %s {", modifiers, simpleName);
		document.indent();
		document.outdent();
		document.linef("}");
	}
	
	private void doGenerateField(final Field field) {
		final Document document = this.document;
		
		final String modifiers = Modifier.toExternalForm(field.getModifiers());
		final String type = doGenerateFieldType(field);
		final String name = field.getName();
		final String assignment = doGenerateFieldAssignment(field);
		
		doGenerateFieldComment(field);
		
		document.linef("%s%s %s%s;", modifiers, type, name, assignment);
	}
	
	private void doGenerateFieldComment(final Field field) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final List<AttributeInfo> attributeInfos = field.getAttributeInfos();
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos() && attributeInfos.size() > 0;
		
		if(isDisplayingAttributeInfos) {
			document.linef("/*");
			
			for(final AttributeInfo attributeInfo : attributeInfos) {
				document.linef(" * %s", attributeInfo.getName());
			}
			
			document.linef(" */");
		}
	}
	
	private void doGenerateInnerType(final InnerType innerType) {
		final Type type = innerType.getType();
		
		if(type instanceof ClassType) {
			doGenerateInnerTypeClassType(innerType, ClassType.class.cast(type));
		}
	}
	
	private void doGenerateInnerTypeClassType(final InnerType innerType, final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean hasSeparatorA = (classType.hasFields()) && (classType.hasConstructors() || classType.hasMethods() || classType.hasInnerTypes());
		final boolean hasSeparatorB = (classType.hasFields() || classType.hasConstructors()) && (classType.hasMethods() || classType.hasInnerTypes());
		final boolean hasSeparatorC = (classType.hasFields() || classType.hasConstructors() || classType.hasMethods()) && (classType.hasInnerTypes());
		final boolean hasSeparatorG = decompilerConfiguration.isSeparatingGroups() && decompilerConfiguration.isSortingGroups();
		
		doGenerateClassTypeComment(classType);
		doGenerateInnerTypeClassTypeClassDeclarationTop(innerType, classType);
		doGenerateClassTypeFields(classType);
		doGenerateSeparator(hasSeparatorA, hasSeparatorA && hasSeparatorG, hasSeparatorA && hasSeparatorG);
		doGenerateClassTypeConstructors(classType);
		doGenerateSeparator(hasSeparatorB, hasSeparatorB && hasSeparatorG, hasSeparatorB && hasSeparatorG);
		doGenerateClassTypeMethods(classType);
		doGenerateSeparator(hasSeparatorC, hasSeparatorC && hasSeparatorG, hasSeparatorC && hasSeparatorG);
		doGenerateClassTypeInnerTypes(classType);
		doGenerateClassTypeClassDeclarationBottom();
	}
	
	private void doGenerateInnerTypeClassTypeClassDeclarationTop(final InnerType innerType, final ClassType classType) {
		final List<Type> importableTypes = classType.getImportableTypes();
		
		final String modifiers = Modifier.toExternalForm(innerType.getModifiers());
		final String simpleName = innerType.getSimpleName();
		final String typeParameters = doGenerateTypeParameters(importableTypes, classType.getOptionalTypeParameters());
		final String extendsClause = doGenerateClassTypeExtendsClause(classType, importableTypes);
		final String implementsClause = doGenerateInterfaceTypeImplementsClause(classType.getInterfaceTypes(), importableTypes, classType.getOptionalClassSignature(), classType.getExternalPackageName());
		
		final
		Document document = this.document;
		document.linef("%sclass %s%s%s%s {", modifiers, simpleName, typeParameters, extendsClause, implementsClause);
		document.indent();
	}
	
	private void doGenerateInterfaceType(final InterfaceType interfaceType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		final List<Type> importableTypes = interfaceType.getImportableTypes();
		
		final String packageName = interfaceType.getExternalPackageName();
		final String modifiers = Modifier.toExternalForm(interfaceType.getModifiers());
		final String simpleName = interfaceType.getExternalSimpleName();
		final String typeParameters = doGenerateTypeParameters(importableTypes, interfaceType.getOptionalTypeParameters());
		final String extendsClause = doGenerateInterfaceTypeExtendsClause(interfaceType.getInterfaceTypes(), importableTypes, interfaceType.getOptionalClassSignature(), interfaceType.getExternalPackageName());
		
		final List<Field> fields = interfaceType.getFields();
		final List<Method> methods = interfaceType.getMethods();
		
		final
		Document document = this.document;
		document.linef("package %s;", packageName);
		
		if(isImportingTypes && importableTypes.size() > 0) {
			document.line();
			
			for(final Type importableType : importableTypes) {
				document.linef("import %s;", importableType.getExternalName());
			}
		}
		
		document.linef("");
		document.linef("%sinterface %s%s%s {", modifiers, simpleName, typeParameters, extendsClause);
		document.indent();
		
		for(final Field field : fields) {
			doGenerateField(field);
		}
		
		if(fields.size() > 0 && methods.size() > 0) {
			document.line();
			
			if(isSeparatingGroups) {
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line("");
			}
		}
		
		for(int i = 0; i < methods.size(); i++) {
			if(i > 0) {
				document.line();
			}
			
			doGenerateMethod(methods.get(i));
		}
		
		document.outdent();
		document.linef("}");
	}
	
	private void doGenerateMethod(final Method method) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final boolean isAnnotatingDeprecatedMethods = decompilerConfiguration.isAnnotatingDeprecatedMethods();
		final boolean isAnnotatingOverriddenMethods = decompilerConfiguration.isAnnotatingOverriddenMethods();
		
		final ParameterList parameterList = method.getParameterList();
		final Type enclosingType = method.getEnclosingType();
		
		final List<Type> importableTypes = method.getImportableTypes();
		
		final String modifiers = Modifier.toExternalForm(doDiscardInterfaceMethodModifiers(enclosingType, method.getModifiers()));
		final String returnType = doGenerateMethodReturnTypeWithOptionalTypeParameters(method, importableTypes);
		final String name = method.getName();
		final String parameters = doToExternalForm(parameterList, method, importableTypes);
		final String returnStatement = doGenerateMethodDefaultReturnStatement(method);
		final String throwsClause = doGenerateMethodThrowsClause(method, importableTypes);
		
		doGenerateMethodComment(method);
		
		if(isAnnotatingDeprecatedMethods && method.isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		if(isAnnotatingOverriddenMethods && enclosingType.hasMethodOverridden(method) && !method.isPrivate() && !method.isStatic()) {
			document.linef("@Override");
		}
		
		if(method.isAbstract() || method.isNative()) {
			document.linef("%s%s %s(%s)%s;", modifiers, returnType, name, parameters, throwsClause);
		} else {
			document.linef("%s%s %s(%s)%s {", modifiers, returnType, name, parameters, throwsClause);
			document.indent();
			
			if(!returnStatement.isEmpty()) {
				document.linef("%s", returnStatement);
			} else {
				document.line();
			}
			
			document.outdent();
			document.linef("}");
		}
	}
	
	private void doGenerateMethodComment(final Method method) {
		final ClassFile classFile = method.getClassFile();
		
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final List<AttributeInfo> attributeInfos = method.getAttributeInfos();
		final List<Instruction> instructions = method.getInstructions();
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos() && attributeInfos.size() > 0;
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions() && instructions.size() > 0;
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.line("/*");
		}
		
		if(isDisplayingAttributeInfos) {
			for(final AttributeInfo attributeInfo : attributeInfos) {
				document.linef(" * %s", attributeInfo.getName());
			}
		}
		
		if(isDisplayingAttributeInfos && isDisplayingInstructions) {
			document.line(" * ");
		}
		
		if(isDisplayingInstructions) {
			document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", "Mnemonic", "Index", "Opcode (Hex.)", "Opcode (Dec.)", "Operands", "Branch Offsets", "Data");
			document.linef(" * ");
			
			final AtomicInteger index = new AtomicInteger();
			
			for(final Instruction instruction : instructions) {
				final String mnemonic = instruction.getMnemonic();
				final String indexAsString = String.format("%04d", Integer.valueOf(index.get()));
				final String opcodeHex = String.format("0x%02X", Integer.valueOf(instruction.getOpcode()));
				final String opcodeDec = String.format("%03d", Integer.valueOf(instruction.getOpcode()));
				final String operands = Strings.optional(IntStream.of(instruction.getOperands()).boxed().collect(Collectors.toList()), "{", "}", ", ");
				final String branchOffsets = Arrays.toString(instruction.getBranchOffsets(index.get()));
				final String description = Instructions.toString(classFile, instruction);
				
				document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", mnemonic, indexAsString, opcodeHex, opcodeDec, operands, branchOffsets, description);
				
				index.addAndGet(instruction.getLength());
			}
		}
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.linef(" */");
		}
	}
	
	private void doGenerateSeparator(final boolean isEmptyLineTop, final boolean isSeparatorMiddle, final boolean isEmptyLineBottom) {
		final Document document = this.document;
		
		if(isEmptyLineTop) {
			document.line("");
		}
		
		if(isSeparatorMiddle) {
			document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
		}
		
		if(isEmptyLineBottom) {
			document.line("");
		}
	}
	
	private void doGenerateTraditionalComment(final String text) {
		final String[] lines = text.split("\n");
		
		final
		Document document = this.document;
		document.line("/*");
		
		for(final String line : lines) {
			document.linef(" * %s", line);
		}
		
		document.line(" */");
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
	
	private static String doGenerateFieldAssignment(final Field field) {
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
	
	private static String doGenerateMethodDefaultReturnStatement(final Method method) {
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
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.model.AnnotationType;
import org.macroing.cel4j.java.model.ClassType;
import org.macroing.cel4j.java.model.Constructor;
import org.macroing.cel4j.java.model.EnumType;
import org.macroing.cel4j.java.model.Field;
import org.macroing.cel4j.java.model.InnerType;
import org.macroing.cel4j.java.model.InterfaceType;
import org.macroing.cel4j.java.model.Method;
import org.macroing.cel4j.java.model.Modifier;
import org.macroing.cel4j.java.model.ParameterList;
import org.macroing.cel4j.java.model.Type;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class SourceCodeGenerator {
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
	
	private String doGenerateTraditionalCommentAtTop(final Type jType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%s decompiled by CEL4J Java Decompiler.", jType.getExternalName()));
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
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final List<Type> importableTypes = classType.getImportableTypes();
		
		final String modifiers = Modifier.toExternalForm(classType.getModifiers());
		final String simpleName = classType.getExternalSimpleName();
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, importableTypes, classType.getOptionalTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, classType, importableTypes);
		final String implementsClause = UtilitiesToRefactor.generateImplementsClause(decompilerConfiguration, classType.getInterfaceTypes(), importableTypes, classType.getOptionalClassSignature(), classType.getExternalPackageName());
		
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
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final ParameterList parameterList = constructor.getParameterList();
		
		final String simpleName = constructor.getEnclosingType().getExternalSimpleName();
		final String modifiers = Modifier.toExternalForm(constructor.getModifiers());
		final String type = UtilitiesToRefactor.generateTypeWithOptionalTypeParameters(decompilerConfiguration, constructor, simpleName);
		final String parameters = UtilitiesToRefactor.toExternalForm(decompilerConfiguration, parameterList, constructor, new ArrayList<>());
		
		doGenerateConstructorComment(constructor);
		
		if(constructor.isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		document.linef("%s%s(%s) {", modifiers, type, parameters);
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
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final Document document = this.document;
		
		final String modifiers = Modifier.toExternalForm(field.getModifiers());
		final String type = UtilitiesToRefactor.generateType(decompilerConfiguration, field);
		final String name = field.getName();
		final String assignment = UtilitiesToRefactor.generateAssignment(field);
		
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
		doGenerateClassTypeInnerTypes(classType);//TODO: Fix StackOverflowError in javax.swing.JPanel.
		doGenerateClassTypeClassDeclarationBottom();
	}
	
	private void doGenerateInnerTypeClassTypeClassDeclarationTop(final InnerType innerType, final ClassType classType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final List<Type> importableTypes = classType.getImportableTypes();
		
		final String modifiers = Modifier.toExternalForm(innerType.getModifiers());
		final String simpleName = innerType.getSimpleName();
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, importableTypes, classType.getOptionalTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, classType, importableTypes);
		final String implementsClause = UtilitiesToRefactor.generateImplementsClause(decompilerConfiguration, classType.getInterfaceTypes(), importableTypes, classType.getOptionalClassSignature(), classType.getExternalPackageName());
		
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
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, importableTypes, interfaceType.getOptionalTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, interfaceType.getInterfaceTypes(), importableTypes, interfaceType.getOptionalClassSignature(), interfaceType.getExternalPackageName());
		
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
		final String returnType = UtilitiesToRefactor.generateReturnTypeWithOptionalTypeParameters(decompilerConfiguration, method, importableTypes);
		final String name = method.getName();
		final String parameters = UtilitiesToRefactor.toExternalForm(decompilerConfiguration, parameterList, method, importableTypes);
		final String returnStatement = UtilitiesToRefactor.generateDefaultReturnStatement(method);
		
		doGenerateMethodComment(method);
		
		if(isAnnotatingDeprecatedMethods && method.isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		if(isAnnotatingOverriddenMethods && enclosingType.hasMethodOverridden(method) && !method.isPrivate() && !method.isStatic()) {
			document.linef("@Override");
		}
		
		if(method.isAbstract() || method.isNative()) {
			document.linef("%s%s %s(%s);", modifiers, returnType, name, parameters);
		} else {
			document.linef("%s%s %s(%s) {", modifiers, returnType, name, parameters);
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
}
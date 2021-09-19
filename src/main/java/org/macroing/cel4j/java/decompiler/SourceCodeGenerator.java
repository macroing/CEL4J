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

import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.util.Document;

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
	
	private String doGenerateTraditionalCommentAtTop(final Type jType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%s decompiled by CEL4J Java Decompiler.", jType.getName()));
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
		final String packageName = annotationType.getPackageName();
		final String modifiers = Modifier.toExternalForm(annotationType.getModifiers());
		final String simpleName = annotationType.getSimpleName();
		
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
		final String simpleName = classType.getSimpleName();
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, importableTypes, classType.getOptionalTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, classType, importableTypes);
		final String implementsClause = UtilitiesToRefactor.generateImplementsClause(decompilerConfiguration, classType.getInterfaceTypes(), importableTypes, classType.getOptionalClassSignature(), classType.getPackageName());
		
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
			
			constructorA.decompile(decompilerConfiguration, this.document);
			
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
			
			fieldA.decompile(decompilerConfiguration, this.document);
			
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
				document.linef("import %s;", importableType.getName());
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
			
			final
			InnerType innerType = innerTypes.get(i);
			innerType.decompile(decompilerConfiguration, this.document);
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
			
			methodA.decompile(decompilerConfiguration, this.document);
			
			if(isSeparatingGroups && isSortingGroups && Method.inDifferentGroups(methodA, methodB)) {
				doGenerateSeparator(true, true, false);
			}
		}
	}
	
	private void doGenerateClassTypePackageDeclaration(final ClassType classType) {
		final
		Document document = this.document;
		document.linef("package %s;", classType.getPackageName());
		document.linef("");
	}
	
	private void doGenerateEnumType(final EnumType enumType) {
		final String packageName = enumType.getPackageName();
		final String modifiers = Modifier.toExternalForm(enumType.getModifiers());
		final String simpleName = enumType.getSimpleName();
		
		final
		Document document = this.document;
		document.linef("package %s;", packageName);
		document.linef("");
		document.linef("%senum %s {", modifiers, simpleName);
		document.indent();
		document.outdent();
		document.linef("}");
	}
	
	private void doGenerateInterfaceType(final InterfaceType interfaceType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		final List<Type> importableTypes = interfaceType.getTypesToImport();
		
		final String packageName = interfaceType.getPackageName();
		final String modifiers = Modifier.toExternalForm(interfaceType.getModifiers());
		final String simpleName = interfaceType.getSimpleName();
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, importableTypes, interfaceType.getTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, interfaceType.getInterfaces(), importableTypes, interfaceType.getClassSignature(), interfaceType.getPackageName());
		
		final List<Field> fields = interfaceType.getFields();
		final List<Method> methods = interfaceType.getMethods();
		
		final
		Document document = this.document;
		document.linef("package %s;", packageName);
		
		if(isImportingTypes && importableTypes.size() > 0) {
			document.line();
			
			for(final Type importableType : importableTypes) {
				document.linef("import %s;", importableType.getName());
			}
		}
		
		document.linef("");
		document.linef("%sinterface %s%s%s {", modifiers, simpleName, typeParameters, extendsClause);
		document.indent();
		
		for(final Field field : fields) {
			field.decompile(decompilerConfiguration, document);
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
			
			final
			Method method = methods.get(i);
			method.decompile(decompilerConfiguration, document);
		}
		
		document.outdent();
		document.linef("}");
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
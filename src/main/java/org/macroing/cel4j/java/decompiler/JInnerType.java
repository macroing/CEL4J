/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JInnerType {
	private final ClassFile classFile;
	private final InnerClass innerClass;
	private final JType type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	JInnerType(final ClassFile classFile, final InnerClass innerClass) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.innerClass = Objects.requireNonNull(innerClass, "innerClass == null");
		this.type = JType.valueOf(ClassName.parseClassName(classFile.getCPInfo(classFile.getCPInfo(innerClass.getInnerClassInfoIndex(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getString()).toExternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final JType type = getType();
		
		if(type instanceof JAnnotation) {
			doDecompileJAnnotation(decompilerConfiguration, document, JAnnotation.class.cast(type));
		} else if(type instanceof JClass) {
			doDecompileJClass(decompilerConfiguration, document, JClass.class.cast(type));
		} else if(type instanceof JEnum) {
			doDecompileJEnum(decompilerConfiguration, document, JEnum.class.cast(type));
		} else if(type instanceof JInterface) {
			doDecompileJInterface(decompilerConfiguration, document, JInterface.class.cast(type));
		}
		
		return document;
	}
	
	public JType getType() {
		return this.type;
	}
	
	public List<JModifier> getModifiers() {
		final List<JModifier> modifiers = new ArrayList<>();
		
		if(isPrivate()) {
			modifiers.add(JModifier.PRIVATE);
		} else if(isProtected()) {
			modifiers.add(JModifier.PROTECTED);
		} else if(isPublic()) {
			modifiers.add(JModifier.PUBLIC);
		}
		
		if(isStatic()) {
			modifiers.add(JModifier.STATIC);
		}
		
		if(isAbstract()) {
			modifiers.add(JModifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(JModifier.FINAL);
		}
		
		return modifiers;
	}
	
	public String getSimpleName() {
		return this.innerClass.getInnerNameIndex() != 0 ? this.classFile.getCPInfo(this.innerClass.getInnerNameIndex(), ConstantUTF8Info.class).getString() : "";
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JInnerType)) {
			return false;
		} else if(!Objects.equals(this.classFile, JInnerType.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.innerClass, JInnerType.class.cast(object).innerClass)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isAbstract() {
		return this.innerClass.isAbstract();
	}
	
	public boolean isFinal() {
		return this.innerClass.isFinal();
	}
	
	public boolean isPackageProtected() {
		return this.innerClass.isPackageProtected();
	}
	
	public boolean isPrivate() {
		return this.innerClass.isPrivate();
	}
	
	public boolean isProtected() {
		return this.innerClass.isProtected();
	}
	
	public boolean isPublic() {
		return this.innerClass.isPublic();
	}
	
	public boolean isStatic() {
		return this.innerClass.isStatic();
	}
	
	public boolean isSynthetic() {
		return this.innerClass.isSynthetic();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.innerClass);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doDecompileJAnnotation(final DecompilerConfiguration decompilerConfiguration, final Document document, final JAnnotation jAnnotation) {
//		TODO: Implement!
	}
	
	private void doDecompileJClass(final DecompilerConfiguration decompilerConfiguration, final Document document, final JClass jClass) {
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String simpleName = getSimpleName();
		final String typeParameters = doGenerateTypeParameters(decompilerConfiguration, jClass.getTypesToImport(), jClass.getTypeParameters(), jClass.getPackageName());
		final String extendsClause = doGenerateExtendsClause(decompilerConfiguration, jClass);
		final String implementsClause = doGenerateImplementsClause(decompilerConfiguration, jClass.getInterfaces(), jClass.getClassSignature(), jClass.getPackageName());
		
		final List<JConstructor> jConstructors = jClass.getConstructors();
		final List<JField> jFields = jClass.getFields();
		final List<JInnerType> jInnerTypes = jClass.getInnerTypes();
		final List<JMethod> jMethods = jClass.getMethods();
		
		document.linef("%sclass %s%s%s%s {", modifiers, simpleName, typeParameters, extendsClause, implementsClause);
		document.indent();
		
		for(final JField jField : jFields) {
			jField.decompile(decompilerConfiguration, document);
		}
		
		if(jFields.size() > 0 && (jConstructors.size() > 0 || jMethods.size() > 0 || jInnerTypes.size() > 0)) {
			document.line();
			
			if(isSeparatingGroups) {
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line("");
			}
		}
		
		for(int i = 0; i < jConstructors.size(); i++) {
			if(i > 0) {
				document.line();
			}
			
			final
			JConstructor jConstructor = jConstructors.get(i);
			jConstructor.decompile(decompilerConfiguration, document);
		}
		
		if((jFields.size() > 0 || jConstructors.size() > 0) && (jMethods.size() > 0 || jInnerTypes.size() > 0)) {
			document.line();
			
			if(isSeparatingGroups) {
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line("");
			}
		}
		
		for(int i = 0; i < jMethods.size(); i++) {
			if(i > 0) {
				document.line();
			}
			
			final
			JMethod jMethod = jMethods.get(i);
			jMethod.decompile(decompilerConfiguration, document);
		}
		
		if((jFields.size() > 0 || jConstructors.size() > 0 || jMethods.size() > 0) && jInnerTypes.size() > 0) {
			document.line();
			
			if(isSeparatingGroups) {
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line("");
			}
		}
		
		for(int i = 0; i < jInnerTypes.size(); i++) {
			if(i > 0) {
				document.line();
				
				if(isSeparatingGroups) {
					document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
					document.line("");
				}
			}
			
//			final
//			JInnerType jInnerType = jInnerTypes.get(i);
//			jInnerType.decompile(decompilerConfiguration, document);
		}
		
		document.outdent();
		document.linef("}");
	}
	
	private void doDecompileJEnum(final DecompilerConfiguration decompilerConfiguration, final Document document, final JEnum jEnum) {
//		TODO: Implement!
	}
	
	private void doDecompileJInterface(final DecompilerConfiguration decompilerConfiguration, final Document document, final JInterface jInterface) {
//		TODO: Implement!
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGenerateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final JClass jClass) {
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
				final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(JPackageNameFilter.newUnnecessaryPackageName(jClass.getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes), string0) : string0;
				
				stringBuilder.append(string1);
			}
		});
		
		return stringBuilder.toString();
	}
	
	private static String doGenerateImplementsClause(final DecompilerConfiguration decompilerConfiguration, final List<JInterface> jInterfaces, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(jInterfaces.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("implements");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
			
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
	
	private static String doGenerateTypeParameters(final DecompilerConfiguration decompilerConfiguration, final List<JType> typesToImport, final Optional<TypeParameters> optionalTypeParameters, final String packageName) {
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
}
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

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class InnerType {
	private final ClassFile classFile;
	private final InnerClass innerClass;
	private final Type type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	InnerType(final ClassFile classFile, final InnerClass innerClass) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.innerClass = Objects.requireNonNull(innerClass, "innerClass == null");
		this.type = Type.valueOf(ClassName.parseClassName(classFile.getCPInfo(classFile.getCPInfo(innerClass.getInnerClassInfoIndex(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue()).toExternalForm());
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
		
		final Type type = getType();
		
		if(type instanceof AnnotationType) {
			doDecompileJAnnotation(decompilerConfiguration, document, AnnotationType.class.cast(type));
		} else if(type instanceof ClassType) {
			doDecompileJClass(decompilerConfiguration, document, ClassType.class.cast(type));
		} else if(type instanceof EnumType) {
			doDecompileJEnum(decompilerConfiguration, document, EnumType.class.cast(type));
		} else if(type instanceof InterfaceType) {
			doDecompileJInterface(decompilerConfiguration, document, InterfaceType.class.cast(type));
		}
		
		return document;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public List<Modifier> getModifiers() {
		final List<Modifier> modifiers = new ArrayList<>();
		
		if(isPrivate()) {
			modifiers.add(Modifier.PRIVATE);
		} else if(isProtected()) {
			modifiers.add(Modifier.PROTECTED);
		} else if(isPublic()) {
			modifiers.add(Modifier.PUBLIC);
		}
		
		if(isStatic()) {
			modifiers.add(Modifier.STATIC);
		}
		
		if(isAbstract()) {
			modifiers.add(Modifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
		
		return modifiers;
	}
	
	public String getSimpleName() {
		return this.innerClass.getInnerNameIndex() != 0 ? this.classFile.getCPInfo(this.innerClass.getInnerNameIndex(), ConstantUTF8Info.class).getStringValue() : "";
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerType)) {
			return false;
		} else if(!Objects.equals(this.classFile, InnerType.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.innerClass, InnerType.class.cast(object).innerClass)) {
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
	
	@SuppressWarnings("unused")
	private void doDecompileJAnnotation(final DecompilerConfiguration decompilerConfiguration, final Document document, final AnnotationType jAnnotation) {
//		TODO: Implement!
	}
	
	private void doDecompileJClass(final DecompilerConfiguration decompilerConfiguration, final Document document, final ClassType jClass) {
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String simpleName = getSimpleName();
		final String typeParameters = UtilitiesToRefactor.generateTypeParameters(decompilerConfiguration, jClass.getImportableTypes(), jClass.getOptionalTypeParameters());
		final String extendsClause = UtilitiesToRefactor.generateExtendsClause(decompilerConfiguration, jClass);
		final String implementsClause = UtilitiesToRefactor.generateImplementsClause(decompilerConfiguration, jClass.getInterfaceTypes(), new ArrayList<>(), jClass.getOptionalClassSignature(), jClass.getPackageName());
		
		final List<Constructor> jConstructors = jClass.getConstructors();
		final List<Field> jFields = jClass.getFields();
		final List<InnerType> jInnerTypes = jClass.getInnerTypes();
		final List<Method> jMethods = jClass.getMethods();
		
		document.linef("%sclass %s%s%s%s {", modifiers, simpleName, typeParameters, extendsClause, implementsClause);
		document.indent();
		
		for(final Field jField : jFields) {
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
			Constructor jConstructor = jConstructors.get(i);
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
			Method jMethod = jMethods.get(i);
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
	
	@SuppressWarnings("unused")
	private void doDecompileJEnum(final DecompilerConfiguration decompilerConfiguration, final Document document, final EnumType jEnum) {
//		TODO: Implement!
	}
	
	@SuppressWarnings("unused")
	private void doDecompileJInterface(final DecompilerConfiguration decompilerConfiguration, final Document document, final InterfaceType jInterface) {
//		TODO: Implement!
	}
}
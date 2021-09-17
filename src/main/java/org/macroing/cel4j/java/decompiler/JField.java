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
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ConstantValueAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantDoubleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFloatInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantIntegerInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantLongInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantStringInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.FieldDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.FieldSignature;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JField implements Comparable<JField> {
	private final ClassFile classFile;
	private final FieldInfo fieldInfo;
	private final JType enclosingType;
	private final JType type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	JField(final ClassFile classFile, final FieldInfo fieldInfo, final JType enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.fieldInfo = Objects.requireNonNull(fieldInfo, "fieldInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.type = JType.valueOf(FieldDescriptor.parseFieldDescriptor(classFile, fieldInfo));
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
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos();
		
		final String accessModifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String type = UtilitiesToRefactor.generateType(decompilerConfiguration, this);
		final String name = getName();
		final String assignment = UtilitiesToRefactor.generateAssignment(this);
		final String attributeInfoComment = isDisplayingAttributeInfos ? doGenerateAttributeInfoComment() : "";
		
		document.linef("%s%s %s%s;%s", accessModifiers, type, name, assignment, attributeInfoComment);
		
		return document;
	}
	
	public JType getEnclosingType() {
		return this.enclosingType;
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
		
		if(isFinal()) {
			modifiers.add(JModifier.FINAL);
		}
		
		if(isTransient()) {
			modifiers.add(JModifier.TRANSIENT);
		}
		
		if(isVolatile()) {
			modifiers.add(JModifier.VOLATILE);
		}
		
		return modifiers;
	}
	
	public Optional<FieldSignature> getFieldSignature() {
		return FieldSignature.parseFieldSignatureOptionally(this.classFile, this.fieldInfo);
	}
	
	public Optional<Object> getAssignment() {
		final Optional<ConstantValueAttribute> optionalConstantValueAttribute = ConstantValueAttribute.find(this.fieldInfo);
		
		if(optionalConstantValueAttribute.isPresent()) {
			final ConstantValueAttribute constantValueAttribute = optionalConstantValueAttribute.get();
			
			final int constantValueIndex = constantValueAttribute.getConstantValueIndex();
			
			final CPInfo cPInfo = this.classFile.getCPInfo(constantValueIndex);
			
			if(cPInfo instanceof ConstantDoubleInfo) {
				return Optional.of(Double.valueOf(ConstantDoubleInfo.class.cast(cPInfo).getDoubleValue()));
			} else if(cPInfo instanceof ConstantFloatInfo) {
				return Optional.of(Float.valueOf(ConstantFloatInfo.class.cast(cPInfo).getFloatValue()));
			} else if(cPInfo instanceof ConstantIntegerInfo) {
				return Optional.of(Integer.valueOf(ConstantIntegerInfo.class.cast(cPInfo).getIntValue()));
			} else if(cPInfo instanceof ConstantLongInfo) {
				return Optional.of(Long.valueOf(ConstantLongInfo.class.cast(cPInfo).getLongValue()));
			} else if(cPInfo instanceof ConstantStringInfo) {
				return Optional.of(ConstantUTF8Info.findByStringIndex(this.classFile, ConstantStringInfo.class.cast(cPInfo)).getStringValue());
			}
		}
		
		return Optional.empty();
	}
	
	public String getName() {
		return ConstantUTF8Info.findByNameIndex(this.classFile, this.fieldInfo).getStringValue();
	}
	
	@Override
	public String toString() {
		return String.format("JField: [Name=%s], [Type=%s], [Assignment=%s]", getName(), getType(), getAssignment());
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JField)) {
			return false;
		} else if(!Objects.equals(this.classFile, JField.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.fieldInfo, JField.class.cast(object).fieldInfo)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isFinal() {
		return this.fieldInfo.isFinal();
	}
	
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	public boolean isPrivate() {
		return this.fieldInfo.isPrivate();
	}
	
	public boolean isProtected() {
		return this.fieldInfo.isProtected();
	}
	
	public boolean isPublic() {
		return this.fieldInfo.isPublic();
	}
	
	public boolean isStatic() {
		return this.fieldInfo.isStatic();
	}
	
	public boolean isSynthetic() {
		return this.fieldInfo.isSynthetic();
	}
	
	public boolean isTransient() {
		return this.fieldInfo.isTransient();
	}
	
	public boolean isVolatile() {
		return this.fieldInfo.isVolatile();
	}
	
	@Override
	public int compareTo(final JField field) {
		final JField fieldThis = this;
		final JField fieldThat = field;
		
		final boolean isStaticThis = fieldThis.isStatic();
		final boolean isStaticThat = fieldThat.isStatic();
		
		if(isStaticThis != isStaticThat) {
			return isStaticThis ? -1 : 1;
		}
		
		final boolean isPublicThis = fieldThis.isPublic();
		final boolean isPublicThat = fieldThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = fieldThis.isProtected();
		final boolean isProtectedThat = fieldThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPackageProtectedThis = fieldThis.isPackageProtected();
		final boolean isPackageProtectedThat = fieldThat.isPackageProtected();
		
		if(isPackageProtectedThis != isPackageProtectedThat) {
			return isPackageProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = fieldThis.isPrivate();
		final boolean isPrivateThat = fieldThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		final int simpleTypeName = fieldThis.getType().getSimpleName().compareTo(fieldThat.getType().getSimpleName());
		
		if(simpleTypeName != 0) {
			return simpleTypeName;
		}
		
		return fieldThis.getName().compareTo(fieldThat.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.fieldInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isInDifferentGroups(final JField fieldA, final JField fieldB) {
		if(fieldA.isStatic() != fieldB.isStatic()) {
			return true;
		}
		
		if(fieldA.isPublic() != fieldB.isPublic()) {
			return true;
		}
		
		if(fieldA.isProtected() != fieldB.isProtected()) {
			return true;
		}
		
		if(fieldA.isPackageProtected() != fieldB.isPackageProtected()) {
			return true;
		}
		
		if(fieldA.isPrivate() != fieldB.isPrivate()) {
			return true;
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doGenerateAttributeInfoComment() {
		final List<AttributeInfo> attributeInfos = this.fieldInfo.getAttributeInfos();
		
		if(attributeInfos.isEmpty()) {
			return "";
		}
		
		return attributeInfos.stream().map(attributeInfo -> attributeInfo.getName()).collect(Collectors.joining(", ", "//", ""));
	}
}
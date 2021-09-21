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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

/**
 * A {@code Field} represents a field.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Field implements Comparable<Field> {
	private final ClassFile classFile;
	private final FieldInfo fieldInfo;
	private final Type enclosingType;
	private final Type type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Field(final ClassFile classFile, final FieldInfo fieldInfo, final Type enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.fieldInfo = Objects.requireNonNull(fieldInfo, "fieldInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.type = Type.valueOf(FieldDescriptor.parseFieldDescriptor(classFile, fieldInfo));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code Field} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code Field} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
	}
	
	/**
	 * Returns the {@link FieldInfo} instance associated with this {@code Field} instance.
	 * 
	 * @return the {@code FieldInfo} instance associated with this {@code Field} instance
	 */
	public FieldInfo getFieldInfo() {
		return this.fieldInfo;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link AttributeInfo} instances associated with this {@code Field} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Field} instance.
	 * 
	 * @return a {@code List} that contains all {@code AttributeInfo} instances associated with this {@code Field} instance
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return this.fieldInfo.getAttributeInfos();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code Field} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Field} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code Field} instance
	 */
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
		
		if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
		
		if(isTransient()) {
			modifiers.add(Modifier.TRANSIENT);
		}
		
		if(isVolatile()) {
			modifiers.add(Modifier.VOLATILE);
		}
		
		return modifiers;
	}
	
	/**
	 * Returns the optional {@link FieldSignature} instance associated with this {@code Field} instance.
	 * 
	 * @return the optional {@code FieldSignature} instance associated with this {@code Field} instance
	 */
	public Optional<FieldSignature> getFieldSignature() {
		return FieldSignature.parseFieldSignatureOptionally(this.classFile, this.fieldInfo);
	}
	
	/**
	 * Returns the optionally assigned {@code Object} instance associated with this {@code Field} instance.
	 * 
	 * @return the optionally assigned {@code Object} instance associated with this {@code Field} instance
	 */
	public Optional<Object> getAssignment() {
		final Optional<ConstantValueAttribute> optionalConstantValueAttribute = ConstantValueAttribute.find(this.fieldInfo);
		
		if(optionalConstantValueAttribute.isPresent()) {
			final CPInfo cPInfo = this.classFile.getCPInfo(optionalConstantValueAttribute.get().getConstantValueIndex());
			
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
	
	/**
	 * Returns the name of this {@code Field} instance.
	 * 
	 * @return the name of this {@code Field} instance
	 */
	public String getName() {
		return ConstantUTF8Info.findByNameIndex(this.classFile, this.fieldInfo).getStringValue();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Field} instance.
	 * 
	 * @return a {@code String} representation of this {@code Field} instance
	 */
	@Override
	public String toString() {
		return "Field";
	}
	
	/**
	 * Returns the enclosing {@link Type} instance associated with this {@code Field} instance.
	 * 
	 * @return the enclosing {@code Type} instance associated with this {@code Field} instance
	 */
	public Type getEnclosingType() {
		return this.enclosingType;
	}
	
	/**
	 * Returns the {@link Type} instance associated with this {@code Field} instance.
	 * 
	 * @return the {@code Type} instance associated with this {@code Field} instance
	 */
	public Type getType() {
		return this.type;
	}
	
	/**
	 * Compares {@code object} to this {@code Field} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Field}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Field} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Field}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Field)) {
			return false;
		} else if(!Objects.equals(this.classFile, Field.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.fieldInfo, Field.class.cast(object).fieldInfo)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is final, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is final, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.fieldInfo.isFinal();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is package protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is package protected, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is private, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is private, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return this.fieldInfo.isPrivate();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is protected, {@code false} otherwise
	 */
	public boolean isProtected() {
		return this.fieldInfo.isProtected();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.fieldInfo.isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is static, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is static, {@code false} otherwise
	 */
	public boolean isStatic() {
		return this.fieldInfo.isStatic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is synthetic, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is synthetic, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return this.fieldInfo.isSynthetic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is transient, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is transient, {@code false} otherwise
	 */
	public boolean isTransient() {
		return this.fieldInfo.isTransient();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Field} instance is volatile, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Field} instance is volatile, {@code false} otherwise
	 */
	public boolean isVolatile() {
		return this.fieldInfo.isVolatile();
	}
	
	/**
	 * Compares this {@code Field} instance with {@code field} for order.
	 * <p>
	 * Returns a negative integer, zero or a positive integer as this {@code Field} instance is less than, equal to or greater than {@code field}.
	 * <p>
	 * If {@code field} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param field a {@code Field} instance
	 * @return a negative integer, zero or a positive integer as this {@code Field} instance is less than, equal to or greater than {@code field}
	 * @throws NullPointerException thrown if, and only if, {@code field} is {@code null}
	 */
	@Override
	public int compareTo(final Field field) {
		final Field fieldThis = this;
		final Field fieldThat = field;
		
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
		
		final int externalSimpleName = fieldThis.getType().getExternalSimpleName().compareTo(fieldThat.getType().getExternalSimpleName());
		
		if(externalSimpleName != 0) {
			return externalSimpleName;
		}
		
		return fieldThis.getName().compareTo(fieldThat.getName());
	}
	
	/**
	 * Returns the {@link AttributeInfo} count associated with this {@code Field} instance.
	 * 
	 * @return the {@code AttributeInfo} count associated with this {@code Field} instance
	 */
	public int getAttributeInfoCount() {
		return this.fieldInfo.getAttributeInfoCount();
	}
	
	/**
	 * Returns a hash code for this {@code Field} instance.
	 * 
	 * @return a hash code for this {@code Field} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.fieldInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code fieldA} and {@code fieldB} are in different groups, {@code false} otherwise.
	 * <p>
	 * If either {@code fieldA} or {@code fieldB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldA a {@code Field} instance
	 * @param fieldB a {@code Field} instance
	 * @return {@code true} if, and only if, {@code fieldA} and {@code fieldB} are in different groups, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code fieldA} or {@code fieldB} are {@code null}
	 */
	public static boolean inDifferentGroups(final Field fieldA, final Field fieldB) {
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
}
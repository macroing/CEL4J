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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;

/**
 * An {@code InnerType} represents an inner type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InnerType {
	private final ClassFile classFile;
	private final InnerClass innerClass;
	private final Type enclosingType;
	private final Type type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	InnerType(final ClassFile classFile, final InnerClass innerClass) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.innerClass = Objects.requireNonNull(innerClass, "innerClass == null");
		this.enclosingType = innerClass.getOuterClassInfoIndex() != 0 ? Type.valueOf(ClassName.parseClassName(classFile.getCPInfo(classFile.getCPInfo(innerClass.getOuterClassInfoIndex(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue()).toExternalForm()) : null;
		this.type = Type.valueOf(ClassName.parseClassName(classFile.getCPInfo(classFile.getCPInfo(innerClass.getInnerClassInfoIndex(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue()).toExternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code InnerType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InnerType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code InnerType} instance
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
		
		if(isAbstract()) {
			modifiers.add(Modifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
		
		return modifiers;
	}
	
	/**
	 * Returns the simple name of this {@code InnerType} instance.
	 * 
	 * @return the simple name of this {@code InnerType} instance
	 */
	public String getSimpleName() {
		return this.innerClass.getInnerNameIndex() != 0 ? this.classFile.getCPInfo(this.innerClass.getInnerNameIndex(), ConstantUTF8Info.class).getStringValue() : "";
	}
	
	/**
	 * Returns the optional enclosing {@link Type} instance associated with this {@code InnerType} instance.
	 * 
	 * @return the optional enclosing {@code Type} instance associated with this {@code InnerType} instance
	 */
	public Optional<Type> getOptionalEnclosingType() {
		return Optional.ofNullable(this.enclosingType);
	}
	
	/**
	 * Returns the {@link Type} instance associated with this {@code InnerType} instance.
	 * 
	 * @return the {@code Type} instance associated with this {@code InnerType} instance
	 */
	public Type getType() {
		return this.type;
	}
	
	/**
	 * Compares {@code object} to this {@code InnerType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InnerType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code InnerType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InnerType}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is abstract, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is abstract, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return this.innerClass.isAbstract();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is final, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is final, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.innerClass.isFinal();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is package protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is package protected, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return this.innerClass.isPackageProtected();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is private, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is private, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return this.innerClass.isPrivate();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is protected, {@code false} otherwise
	 */
	public boolean isProtected() {
		return this.innerClass.isProtected();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.innerClass.isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is static, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is static, {@code false} otherwise
	 */
	public boolean isStatic() {
		return this.innerClass.isStatic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InnerType} instance is synthetic, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InnerType} instance is synthetic, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return this.innerClass.isSynthetic();
	}
	
	/**
	 * Returns a hash code for this {@code InnerType} instance.
	 * 
	 * @return a hash code for this {@code InnerType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.innerClass);
	}
}
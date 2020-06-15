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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;

/**
 * An {@code InnerClass} that can be found as a part of any {@link InnerClassesAttribute} instances.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InnerClass implements Node {
	/**
	 * This field represents ACC_ABSTRACT in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_ABSTRACT = 0x0400;
	
	/**
	 * This field represents ACC_ANNOTATION in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_ANNOTATION = 0x2000;
	
	/**
	 * This field represents ACC_ENUM in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_ENUM = 0x4000;
	
	/**
	 * This field represents ACC_FINAL in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * This field represents ACC_INTERFACE in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_INTERFACE = 0x0200;
	
	/**
	 * This field represents ACC_PRIVATE in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_PRIVATE = 0x0002;
	
	/**
	 * This field represents ACC_PROTECTED in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_PROTECTED = 0x0004;
	
	/**
	 * This field represents ACC_PUBLIC in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_PUBLIC = 0x0001;
	
	/**
	 * This field represents ACC_STATIC in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_STATIC = 0x0008;
	
	/**
	 * This field represents ACC_SYNTHETIC in the inner_class_access_flags element of an inner_class structure.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int innerClassAccessFlags;
	private int innerClassInfoIndex;
	private int innerNameIndex;
	private int outerClassInfoIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code InnerClass} instance.
	 */
	public InnerClass() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code InnerClass} instance.
	 * 
	 * @return a copy of this {@code InnerClass} instance
	 */
	public InnerClass copy() {
		final
		InnerClass innerClass = new InnerClass();
		innerClass.setInnerClassAccessFlags(getInnerClassAccessFlags());
		innerClass.setInnerClassInfoIndex(getInnerClassInfoIndex());
		innerClass.setInnerNameIndex(getInnerNameIndex());
		innerClass.setOuterClassInfoIndex(getOuterClassInfoIndex());
		
		return innerClass;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InnerClass}, and that {@code InnerClass} instance is equal to this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code InnerClass} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InnerClass}, and that {@code InnerClass} instance is equal to this {@code InnerClass} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerClass)) {
			return false;
		} else if(InnerClass.class.cast(object).getInnerClassInfoIndex() != getInnerClassInfoIndex()) {
			return false;
		} else if(InnerClass.class.cast(object).getOuterClassInfoIndex() != getOuterClassInfoIndex()) {
			return false;
		} else if(InnerClass.class.cast(object).getInnerNameIndex() != getInnerNameIndex()) {
			return false;
		} else if(InnerClass.class.cast(object).getInnerClassAccessFlags() != getInnerClassAccessFlags()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ABSTRACT is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ABSTRACT is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (this.innerClassAccessFlags & ACC_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ANNOTATION is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ANNOTATION is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isAnnotation() {
		return (this.innerClassAccessFlags & ACC_ANNOTATION) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, neither of ACC_ANNOTATION, ACC_ENUM and ACC_INTERFACE are set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, neither of ACC_ANNOTATION, ACC_ENUM and ACC_INTERFACE are set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isClass() {
		return !isAnnotation() && !isEnum() && !isInterface();
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ENUM is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ENUM is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isEnum() {
		return (this.innerClassAccessFlags & ACC_ENUM) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_FINAL is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_FINAL is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.innerClassAccessFlags & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_INTERFACE is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_INTERFACE is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isInterface() {
		return (this.innerClassAccessFlags & ACC_INTERFACE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, neither of ACC_PRIVATE, ACC_PROTECTED and ACC_PUBLIC are set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, neither of ACC_PRIVATE, ACC_PROTECTED and ACC_PUBLIC are set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PRIVATE is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PRIVATE is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.innerClassAccessFlags & ACC_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PROTECTED is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PROTECTED is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.innerClassAccessFlags & ACC_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PUBLIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PUBLIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.innerClassAccessFlags & ACC_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_STATIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_STATIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (this.innerClassAccessFlags & ACC_STATIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SYNTHETIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SYNTHETIC is set in the inner_class_access_flags item of this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (this.innerClassAccessFlags & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Returns the inner_class_access_flags value of this {@code InnerClass} instance.
	 * 
	 * @return the inner_class_access_flags value of this {@code InnerClass} instance
	 */
	public int getInnerClassAccessFlags() {
		return this.innerClassAccessFlags;
	}
	
	/**
	 * Returns the inner_class_info_index value of this {@code InnerClass} instance.
	 * 
	 * @return the inner_class_info_index value of this {@code InnerClass} instance
	 */
	public int getInnerClassInfoIndex() {
		return this.innerClassInfoIndex;
	}
	
	/**
	 * Returns the inner_name_index value of this {@code InnerClass} instance.
	 * 
	 * @return the inner_name_index value of this {@code InnerClass} instance
	 */
	public int getInnerNameIndex() {
		return this.innerNameIndex;
	}
	
	/**
	 * Returns the outer_class_info_index value of this {@code InnerClass} instance.
	 * 
	 * @return the outer_class_info_index value of this {@code InnerClass} instance
	 */
	public int getOuterClassInfoIndex() {
		return this.outerClassInfoIndex;
	}
	
	/**
	 * Returns a hash code for this {@code InnerClass} instance.
	 * 
	 * @return a hash code for this {@code InnerClass} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getInnerClassInfoIndex()), Integer.valueOf(getOuterClassInfoIndex()), Integer.valueOf(getInnerNameIndex()), Integer.valueOf(getInnerClassAccessFlags()));
	}
	
	/**
	 * Sets a new inner_class_access_flags value for this {@code InnerClass} instance.
	 * 
	 * @param innerClassAccessFlags the new inner_class_access_flags value
	 */
	public void setInnerClassAccessFlags(final int innerClassAccessFlags) {
		this.innerClassAccessFlags = innerClassAccessFlags;
	}
	
	/**
	 * Sets a new inner_class_info_index value for this {@code InnerClass} instance.
	 * 
	 * @param innerClassInfoIndex the new inner_class_info_index value
	 */
	public void setInnerClassInfoIndex(final int innerClassInfoIndex) {
		this.innerClassInfoIndex = innerClassInfoIndex;
	}
	
	/**
	 * Sets a new inner_name_index value for this {@code InnerClass} instance.
	 * 
	 * @param innerNameIndex the new inner_name_index value
	 */
	public void setInnerNameIndex(final int innerNameIndex) {
		this.innerNameIndex = innerNameIndex;
	}
	
	/**
	 * Sets a new outer_class_info_index value for this {@code InnerClass} instance.
	 * 
	 * @param outerClassInfoIndex the new outer_class_info_index value
	 */
	public void setOuterClassInfoIndex(final int outerClassInfoIndex) {
		this.outerClassInfoIndex = outerClassInfoIndex;
	}
	
	/**
	 * Writes this {@code InnerClass} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getInnerClassInfoIndex());
			dataOutput.writeShort(getOuterClassInfoIndex());
			dataOutput.writeShort(getInnerNameIndex());
			dataOutput.writeShort(getInnerClassAccessFlags());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
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
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code InnerClass} represents an entry in the {@code classes} item of the {@code InnerClasses_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 inner_class_info_index;
 *     u2 outer_class_info_index;
 *     u2 inner_name_index;
 *     u2 inner_class_access_flags;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InnerClass implements Node {
	/**
	 * The value for the access flag {@code ACC_ABSTRACT} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_ABSTRACT = 0x0400;
	
	/**
	 * The value for the access flag {@code ACC_ANNOTATION} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_ANNOTATION = 0x2000;
	
	/**
	 * The value for the access flag {@code ACC_ENUM} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_ENUM = 0x4000;
	
	/**
	 * The value for the access flag {@code ACC_FINAL} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * The value for the access flag {@code ACC_INTERFACE} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_INTERFACE = 0x0200;
	
	/**
	 * The value for the access flag {@code ACC_PRIVATE} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_PRIVATE = 0x0002;
	
	/**
	 * The value for the access flag {@code ACC_PROTECTED} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_PROTECTED = 0x0004;
	
	/**
	 * The value for the access flag {@code ACC_PUBLIC} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_PUBLIC = 0x0001;
	
	/**
	 * The value for the access flag {@code ACC_STATIC} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_STATIC = 0x0008;
	
	/**
	 * The value for the access flag {@code ACC_SYNTHETIC} in the {@code inner_class_access_flags} item.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int innerClassAccessFlags;
	private int innerClassInfoIndex;
	private int innerNameIndex;
	private int outerClassInfoIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code InnerClass} instance.
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
	 * Returns a {@code String} representation of this {@code InnerClass} instance.
	 * 
	 * @return a {@code String} representation of this {@code InnerClass} instance
	 */
	@Override
	public String toString() {
		return "new InnerClass()";
	}
	
	/**
	 * Compares {@code object} to this {@code InnerClass} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InnerClass}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code InnerClass} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InnerClass}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerClass)) {
			return false;
		} else if(getInnerClassInfoIndex() != InnerClass.class.cast(object).getInnerClassInfoIndex()) {
			return false;
		} else if(getOuterClassInfoIndex() != InnerClass.class.cast(object).getOuterClassInfoIndex()) {
			return false;
		} else if(getInnerNameIndex() != InnerClass.class.cast(object).getInnerNameIndex()) {
			return false;
		} else if(getInnerClassAccessFlags() != InnerClass.class.cast(object).getInnerClassAccessFlags()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_ABSTRACT} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_ABSTRACT} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (getInnerClassAccessFlags() & ACC_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_ANNOTATION} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_ANNOTATION} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isAnnotation() {
		return (getInnerClassAccessFlags() & ACC_ANNOTATION) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_ANNOTATION}, {@code ACC_ENUM} and {@code ACC_INTERFACE} are not set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_ANNOTATION}, {@code ACC_ENUM} and {@code ACC_INTERFACE} are not set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isClass() {
		return !isAnnotation() && !isEnum() && !isInterface();
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_ENUM} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_ENUM} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isEnum() {
		return (getInnerClassAccessFlags() & ACC_ENUM) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_FINAL} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_FINAL} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (getInnerClassAccessFlags() & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_INTERFACE} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_INTERFACE} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isInterface() {
		return (getInnerClassAccessFlags() & ACC_INTERFACE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PRIVATE}, {@code ACC_PROTECTED} and {@code ACC_PUBLIC} are not set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PRIVATE}, {@code ACC_PROTECTED} and {@code ACC_PUBLIC} are not set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PRIVATE} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PRIVATE} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (getInnerClassAccessFlags() & ACC_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PROTECTED} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PROTECTED} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (getInnerClassAccessFlags() & ACC_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PUBLIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PUBLIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (getInnerClassAccessFlags() & ACC_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_STATIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_STATIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (getInnerClassAccessFlags() & ACC_STATIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_SYNTHETIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_SYNTHETIC} is set in the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (getInnerClassAccessFlags() & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Returns the value of the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance.
	 * 
	 * @return the value of the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance
	 */
	public int getInnerClassAccessFlags() {
		return this.innerClassAccessFlags;
	}
	
	/**
	 * Returns the value of the {@code inner_class_info_index} item associated with this {@code InnerClass} instance.
	 * 
	 * @return the value of the {@code inner_class_info_index} item associated with this {@code InnerClass} instance
	 */
	public int getInnerClassInfoIndex() {
		return this.innerClassInfoIndex;
	}
	
	/**
	 * Returns the value of the {@code inner_name_index} item associated with this {@code InnerClass} instance.
	 * 
	 * @return the value of the {@code inner_name_index} item associated with this {@code InnerClass} instance
	 */
	public int getInnerNameIndex() {
		return this.innerNameIndex;
	}
	
	/**
	 * Returns the value of the {@code outer_class_info_index} item associated with this {@code InnerClass} instance.
	 * 
	 * @return the value of the {@code outer_class_info_index} item associated with this {@code InnerClass} instance
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
	 * Sets {@code innerClassAccessFlags} as the value for the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance.
	 * <p>
	 * If {@code innerClassAccessFlags} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param innerClassAccessFlags the value for the {@code inner_class_access_flags} item associated with this {@code InnerClass} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code innerClassAccessFlags} is less than {@code 0}
	 */
	public void setInnerClassAccessFlags(final int innerClassAccessFlags) {
		this.innerClassAccessFlags = ParameterArguments.requireRange(innerClassAccessFlags, 0, Integer.MAX_VALUE, "innerClassAccessFlags");
	}
	
	/**
	 * Sets {@code innerClassInfoIndex} as the value for the {@code inner_class_info_index} item associated with this {@code InnerClass} instance.
	 * <p>
	 * If {@code innerClassInfoIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param innerClassInfoIndex the value for the {@code inner_class_info_index} item associated with this {@code InnerClass} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code innerClassInfoIndex} is less than {@code 1}
	 */
	public void setInnerClassInfoIndex(final int innerClassInfoIndex) {
		this.innerClassInfoIndex = ParameterArguments.requireRange(innerClassInfoIndex, 1, Integer.MAX_VALUE, "innerClassInfoIndex");
	}
	
	/**
	 * Sets {@code innerNameIndex} as the value for the {@code inner_name_index} item associated with this {@code InnerClass} instance.
	 * <p>
	 * If {@code innerNameIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param innerNameIndex the value for the {@code inner_name_index} item associated with this {@code InnerClass} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code innerNameIndex} is less than {@code 0}
	 */
	public void setInnerNameIndex(final int innerNameIndex) {
		this.innerNameIndex = ParameterArguments.requireRange(innerNameIndex, 0, Integer.MAX_VALUE, "innerNameIndex");
	}
	
	/**
	 * Sets {@code outerClassInfoIndex} as the value for the {@code outer_class_info_index} item associated with this {@code InnerClass} instance.
	 * <p>
	 * If {@code outerClassInfoIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param outerClassInfoIndex the value for the {@code outer_class_info_index} item associated with this {@code InnerClass} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code outerClassInfoIndex} is less than {@code 0}
	 */
	public void setOuterClassInfoIndex(final int outerClassInfoIndex) {
		this.outerClassInfoIndex = ParameterArguments.requireRange(outerClassInfoIndex, 0, Integer.MAX_VALUE, "outerClassInfoIndex");
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
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
 * A {@code Parameter} that can be found as a part of any {@link MethodParametersAttribute} instances.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Parameter implements Node {
	/**
	 * This field represents ACC_FINAL in the access_flags element of a parameter structure.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * This field represents ACC_MANDATED in the access_flags element of a parameter structure.
	 */
	public static final int ACC_MANDATED = 0x8000;
	
	/**
	 * This field represents ACC_SYNTHETIC in the access_flags element of a parameter structure.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int accessFlags;
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Parameter} instance.
	 */
	public Parameter() {
		this.nameIndex = 1;
		this.accessFlags = 0;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code Parameter} instance.
	 * 
	 * @return a copy of this {@code Parameter} instance
	 */
	public Parameter copy() {
		final
		Parameter parameter = new Parameter();
		parameter.accessFlags = this.accessFlags;
		parameter.nameIndex = this.nameIndex;
		
		return parameter;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Parameter}, and that {@code Parameter} instance is equal to this {@code Parameter} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code Parameter} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Parameter}, and that {@code Parameter} instance is equal to this {@code Parameter} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Parameter)) {
			return false;
		} else if(this.nameIndex != Parameter.class.cast(object).nameIndex) {
			return false;
		} else if(this.accessFlags != Parameter.class.cast(object).accessFlags) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_MANDATED is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_MANDATED is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise
	 */
	public boolean isMandated() {
		return (this.accessFlags & ACC_MANDATED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code Parameter} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (this.accessFlags & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Returns the access_flags element of this {@code Parameter} instance.
	 * 
	 * @return the access_flags element of this {@code Parameter} instance
	 */
	public int getAccessFlags() {
		return this.accessFlags;
	}
	
	/**
	 * Returns the length of this {@code Parameter} instance.
	 * 
	 * @return the length of this {@code Parameter} instance
	 */
	@SuppressWarnings("static-method")
	public int getLength() {
		return 4;
	}
	
	/**
	 * Returns the name_index element of this {@code Parameter} instance.
	 * 
	 * @return the name_index element of this {@code Parameter} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code Parameter} instance.
	 * 
	 * @return a hash code for this {@code Parameter} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getNameIndex()), Integer.valueOf(getAccessFlags()));
	}
	
	/**
	 * Adds or removes the access flag ACC_FINAL.
	 * 
	 * @param isFinal {@code true} to add the access flag ACC_FINAL
	 */
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags |= ACC_FINAL;
		} else {
			this.accessFlags &= ~ACC_FINAL;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_MANDATED.
	 * 
	 * @param isMandated {@code true} to add the access flag ACC_MANDATED
	 */
	public void setMandated(final boolean isMandated) {
		if(isMandated) {
			this.accessFlags |= ACC_MANDATED;
		} else {
			this.accessFlags &= ~ACC_MANDATED;
		}
	}
	
	/**
	 * Sets the name_index element of this {@code Parameter} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the new name_index element
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 0}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 0, Integer.MAX_VALUE, "nameIndex");
	}
	
	/**
	 * Adds or removes the access flag ACC_SYNTHETIC.
	 * 
	 * @param isSynthetic {@code true} to add the access flag ACC_SYNTHETIC
	 */
	public void setSynthetic(final boolean isSynthetic) {
		if(isSynthetic) {
			this.accessFlags |= ACC_SYNTHETIC;
		} else {
			this.accessFlags &= ~ACC_SYNTHETIC;
		}
	}
	
	/**
	 * Writes this {@code Parameter} to {@code dataOutput}.
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
			dataOutput.writeShort(this.nameIndex);
			dataOutput.writeShort(this.accessFlags);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
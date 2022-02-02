/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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

import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code ObjectVariableInfo} represents an {@code Object_variable_info} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The {@code Object_variable_info} {@code union} structure has the following format:
 * <pre>
 * <code>
 * Object_variable_info {
 *     u1 tag = ITEM_Object;
 *     u2 cpool_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ObjectVariableInfo implements VerificationTypeInfo {
	private final int constantPoolIndex;
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ObjectVariableInfo} instance.
	 * <p>
	 * If {@code constantPoolIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param constantPoolIndex the value of the {@code cpool_index} item associated with this {@code ObjectVariableInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code constantPoolIndex} is less than {@code 1}
	 */
	public ObjectVariableInfo(final int constantPoolIndex) {
		this.constantPoolIndex = ParameterArguments.requireRange(constantPoolIndex, 1, Integer.MAX_VALUE);
		this.tag = ITEM_OBJECT;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ObjectVariableInfo} instance.
	 * <p>
	 * Because this class is immutable, the same instance will be returned.
	 * 
	 * @return a copy of this {@code ObjectVariableInfo} instance
	 */
	@Override
	public ObjectVariableInfo copy() {
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ObjectVariableInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ObjectVariableInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ObjectVariableInfo(%s)", Integer.toString(getConstantPoolIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ObjectVariableInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ObjectVariableInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ObjectVariableInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ObjectVariableInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ObjectVariableInfo)) {
			return false;
		} else if(getTag() != ObjectVariableInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolIndex() != ObjectVariableInfo.class.cast(object).getConstantPoolIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code cpool_index} item associated with this {@code ObjectVariableInfo} instance.
	 * 
	 * @return the value of the {@code cpool_index} item associated with this {@code ObjectVariableInfo} instance
	 */
	public int getConstantPoolIndex() {
		return this.constantPoolIndex;
	}
	
	/**
	 * Returns the length of this {@code ObjectVariableInfo} instance.
	 * 
	 * @return the length of this {@code ObjectVariableInfo} instance
	 */
	@Override
	public int getLength() {
		return 3;
	}
	
	/**
	 * Returns the value of the {@code tag} item associated with this {@code ObjectVariableInfo} instance.
	 * 
	 * @return the value of the {@code tag} item associated with this {@code ObjectVariableInfo} instance
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	
	/**
	 * Returns a hash code for this {@code ObjectVariableInfo} instance.
	 * 
	 * @return a hash code for this {@code ObjectVariableInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolIndex()));
	}
	
	/**
	 * Writes this {@code ObjectVariableInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(getTag());
			dataOutput.writeShort(getConstantPoolIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
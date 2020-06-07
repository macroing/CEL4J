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
package org.macroing.cel4j.java.binary.classfile;

import java.io.DataOutput;
import java.util.Objects;

import org.macroing.cel4j.util.Document;

/**
 * A {@code ConstantUnreachableInfo} denotes a custom CONSTANT_Unreachable_info structure.
 * <p>
 * It is not part of the official ClassFile structure. But it is used in this library to denote an entry in a constant_pool table that is unreachable.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantUnreachableInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Unreachable_info structure.
	 */
	public static final String NAME = "CONSTANT_Unreachable";
	
	/**
	 * The tag for CONSTANT_Unreachable.
	 */
	public static final int TAG = 0;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantUnreachableInfo} instance.
	 */
	public ConstantUnreachableInfo() {
		super(NAME, TAG, 1);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantUnreachableInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantUnreachableInfo} instance
	 */
	@Override
	public ConstantUnreachableInfo copy() {
		return new ConstantUnreachableInfo();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantUnreachableInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantUnreachableInfo} instance
	 */
	@Override
	public String toString() {
		return "";
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantUnreachableInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantUnreachableInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantUnreachableInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantUnreachableInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantUnreachableInfo)) {
			return false;
		} else if(!Objects.equals(ConstantUnreachableInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantUnreachableInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantUnreachableInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ConstantUnreachableInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantUnreachableInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()));
	}
	
	/**
	 * Writes this {@code CPInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		Objects.requireNonNull(dataOutput, "dataOutput == null");
	}
	
	/**
	 * Writes this {@code CPInfo} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param document the {@link Document} to write to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public void write(final Document document) {
		Objects.requireNonNull(document, "document == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code ConstantUnreachableInfo}.
	 * 
	 * @return a new {@code ConstantUnreachableInfo}
	 */
	public static ConstantUnreachableInfo newInstance() {
		return new ConstantUnreachableInfo();
	}
}
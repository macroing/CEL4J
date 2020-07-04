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
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.util.Document;

/**
 * A {@code ConstantUnreachableInfo} represents a custom {@code CONSTANT_Unreachable_info} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Unreachable_info} structure is not defined by the Java Virtual Machine Specifications. It is defined by this library to represent an entry in the {@code constant_pool} table that is unreachable.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantUnreachableInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Unreachable_info} structure.
	 */
	public static final String NAME = "CONSTANT_Unreachable";
	
	/**
	 * The value for the {@code tag} item associated with the {@code CONSTANT_Unreachable_info} structure.
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
	 * Writes this {@code ConstantUnreachableInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantUnreachableInfo.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code ConstantUnreachableInfo} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		return Objects.requireNonNull(document, "document == null");
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
	 * Writes this {@code ConstantUnreachableInfo} to {@code dataOutput}.
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
		Objects.requireNonNull(dataOutput, "dataOutput == null");
	}
}
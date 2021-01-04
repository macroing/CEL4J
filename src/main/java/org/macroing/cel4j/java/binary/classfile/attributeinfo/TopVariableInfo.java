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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * A {@code TopVariableInfo} represents a {@code Top_variable_info} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The {@code Top_variable_info} {@code union} structure has the following format:
 * <pre>
 * <code>
 * Top_variable_info {
 *     u1 tag = ITEM_Top;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TopVariableInfo implements VerificationTypeInfo {
	private static final TopVariableInfo INSTANCE = new TopVariableInfo();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private TopVariableInfo() {
		this.tag = ITEM_TOP;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code TopVariableInfo} instance.
	 * <p>
	 * Because this class is immutable, the same instance will be returned.
	 * 
	 * @return a copy of this {@code TopVariableInfo} instance
	 */
	@Override
	public TopVariableInfo copy() {
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TopVariableInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code TopVariableInfo} instance
	 */
	@Override
	public String toString() {
		return "TopVariableInfo.getInstance()";
	}
	
	/**
	 * Compares {@code object} to this {@code TopVariableInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TopVariableInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TopVariableInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TopVariableInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TopVariableInfo)) {
			return false;
		} else if(getTag() != TopVariableInfo.class.cast(object).getTag()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code TopVariableInfo} instance.
	 * 
	 * @return the length of this {@code TopVariableInfo} instance
	 */
	@Override
	public int getLength() {
		return 1;
	}
	
	/**
	 * Returns the value of the {@code tag} item associated with this {@code TopVariableInfo} instance.
	 * 
	 * @return the value of the {@code tag} item associated with this {@code TopVariableInfo} instance
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	
	/**
	 * Returns a hash code for this {@code TopVariableInfo} instance.
	 * 
	 * @return a hash code for this {@code TopVariableInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getTag()));
	}
	
	/**
	 * Writes this {@code TopVariableInfo} to {@code dataOutput}.
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
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code TopVariableInfo} instance.
	 * 
	 * @return the {@code TopVariableInfo} instance
	 */
	public static TopVariableInfo getInstance() {
		return INSTANCE;
	}
}
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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code LineNumber} represents an entry in the {@code line_number_table} item of the {@code LineNumberTable_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 start_pc;
 *     u2 line_number;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LineNumber implements Node {
	private int lineNumber;
	private int startPC;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LineNumber} instance.
	 * <p>
	 * If either {@code startPC} or {@code lineNumber} are less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code LineNumber} instance
	 * @param lineNumber the value for the {@code line_number} item associated with this {@code LineNumber} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code startPC} or {@code lineNumber} are less than {@code 0}
	 */
	public LineNumber(final int startPC, final int lineNumber) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, Integer.MAX_VALUE);
		this.lineNumber = ParameterArguments.requireRange(lineNumber, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LineNumber} instance.
	 * 
	 * @return a copy of this {@code LineNumber} instance
	 */
	public LineNumber copy() {
		return new LineNumber(getStartPC(), getLineNumber());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LineNumber} instance.
	 * 
	 * @return a {@code String} representation of this {@code LineNumber} instance
	 */
	@Override
	public String toString() {
		return String.format("new LineNumber(%s, %s)", Integer.toString(getStartPC()), Integer.toString(getLineNumber()));
	}
	
	/**
	 * Compares {@code object} to this {@code LineNumber} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineNumber}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LineNumber} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineNumber}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineNumber)) {
			return false;
		} else if(getStartPC() != LineNumber.class.cast(object).getStartPC()) {
			return false;
		} else if(getLineNumber() != LineNumber.class.cast(object).getLineNumber()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code line_number} item associated with this {@code LineNumber} instance.
	 * 
	 * @return the value of the {@code line_number} item associated with this {@code LineNumber} instance
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	/**
	 * Returns the value of the {@code start_pc} item associated with this {@code LineNumber} instance.
	 * 
	 * @return the value of the {@code start_pc} item associated with this {@code LineNumber} instance
	 */
	public int getStartPC() {
		return this.startPC;
	}
	
	/**
	 * Returns a hash code for this {@code LineNumber} instance.
	 * 
	 * @return a hash code for this {@code LineNumber} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getStartPC()), Integer.valueOf(getLineNumber()));
	}
	
	/**
	 * Sets {@code lineNumber} as the value for the {@code line_number} item associated with this {@code LineNumber} instance.
	 * <p>
	 * If {@code lineNumber} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param lineNumber the value for the {@code line_number} item associated with this {@code LineNumber} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code lineNumber} is less than {@code 0}
	 */
	public void setLineNumber(final int lineNumber) {
		this.lineNumber = ParameterArguments.requireRange(lineNumber, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code startPC} as the value for the {@code start_pc} item associated with this {@code LineNumber} instance.
	 * <p>
	 * If {@code startPC} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code LineNumber} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code startPC} is less than {@code 0}
	 */
	public void setStartPC(final int startPC) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code LineNumber} to {@code dataOutput}.
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
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getStartPC());
			dataOutput.writeShort(getLineNumber());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
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
 * A {@code LineNumber} denotes a line_number structure somewhere in a LineNumberTable_attribute structure.
 * <p>
 * This class is not thread-safe.
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
	 * If {@code lineNumber} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the start_pc
	 * @param lineNumber the line_number
	 * @throws IllegalArgumentException thrown if, and only if, {@code lineNumber} is less than {@code 0}
	 */
	public LineNumber(final int startPC, final int lineNumber) {
		this.startPC = startPC;
		this.lineNumber = ParameterArguments.requireRange(lineNumber, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LineNumber} instance.
	 * 
	 * @return a copy of this {@code LineNumber} instance
	 */
	public LineNumber copy() {
		return new LineNumber(this.startPC, this.lineNumber);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LineNumber} instance.
	 * 
	 * @return a {@code String} representation of this {@code LineNumber} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("line_number");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("start_pc=" + getStartPC());
		stringBuilder.append(" ");
		stringBuilder.append("line_number=" + getLineNumber());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineNumber}, and that {@code LineNumber} instance is equal to this {@code LineNumber} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LineNumber} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineNumber}, and that {@code LineNumber} instance is equal to this {@code LineNumber} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineNumber)) {
			return false;
		} else if(LineNumber.class.cast(object).getStartPC() != getStartPC()) {
			return false;
		} else if(LineNumber.class.cast(object).getLineNumber() != getLineNumber()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the line_number of this {@code LineNumber} instance.
	 * 
	 * @return the line_number of this {@code LineNumber} instance.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	/**
	 * Returns the start_pc of this {@code LineNumber} instance.
	 * 
	 * @return the start_pc of this {@code LineNumber} instance.
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
	 * Sets the line_number for this {@code LineNumber} instance.
	 * <p>
	 * If {@code lineNumber} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param lineNumber the new line_number
	 * @throws IllegalArgumentException thrown if, and only if, {@code lineNumber} is less than {@code 0}
	 */
	public void setLineNumber(final int lineNumber) {
		this.lineNumber = ParameterArguments.requireRange(lineNumber, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets the start_pc for this {@code LineNumber} instance.
	 * 
	 * @param startPC the new start_pc
	 */
	public void setStartPC(final int startPC) {
		this.startPC = startPC;
	}
	
	/**
	 * Writes this {@code LineNumber} to {@code dataOutput}.
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
			dataOutput.writeShort(getStartPC());
			dataOutput.writeShort(getLineNumber());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
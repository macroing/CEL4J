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
 * An {@code ExceptionHandler} represents an entry in the {@code exception_table} item of the {@code Code_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 start_pc;
 *     u2 end_pc;
 *     u2 handler_pc;
 *     u2 catch_type;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ExceptionHandler implements Node {
	private int catchType;
	private int endPC;
	private int handlerPC;
	private int startPC;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ExceptionHandler} instance.
	 * <p>
	 * If either {@code startPC} is less than {@code 0}, {@code endPC} is less than or equal to {@code startPC}, {@code handlerPC} is less than {@code 0} or {@code catchType} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code ExceptionHandler} instance
	 * @param endPC the value for the {@code end_pc} item associated with this {@code ExceptionHandler} instance
	 * @param handlerPC the value for the {@code handler_pc} item associated with this {@code ExceptionHandler} instance
	 * @param catchType the value for the {@code catch_type} item associated with this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code startPC} is less than {@code 0}, {@code endPC} is less than or equal to {@code startPC}, {@code handlerPC} is less than {@code 0} or {@code catchType} is less than {@code 0}
	 */
	public ExceptionHandler(final int startPC, final int endPC, final int handlerPC, final int catchType) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, endPC - 1);
		this.endPC = ParameterArguments.requireRange(endPC, startPC + 1, Integer.MAX_VALUE);
		this.handlerPC = ParameterArguments.requireRange(handlerPC, 0, Integer.MAX_VALUE);
		this.catchType = ParameterArguments.requireRange(catchType, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ExceptionHandler} instance.
	 * 
	 * @return a copy of this {@code ExceptionHandler} instance
	 */
	public ExceptionHandler copy() {
		return new ExceptionHandler(getStartPC(), getEndPC(), getHandlerPC(), getCatchType());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ExceptionHandler} instance.
	 * 
	 * @return a {@code String} representation of this {@code ExceptionHandler} instance
	 */
	@Override
	public String toString() {
		return String.format("new ExceptionHandler(%s, %s, %s, %s)", Integer.toString(getStartPC()), Integer.toString(getEndPC()), Integer.toString(getHandlerPC()), Integer.toString(getCatchType()));
	}
	
	/**
	 * Compares {@code object} to this {@code ExceptionHandler} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ExceptionHandler}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ExceptionHandler} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ExceptionHandler}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ExceptionHandler)) {
			return false;
		} else if(getStartPC() != ExceptionHandler.class.cast(object).getStartPC()) {
			return false;
		} else if(getEndPC() != ExceptionHandler.class.cast(object).getEndPC()) {
			return false;
		} else if(getHandlerPC() != ExceptionHandler.class.cast(object).getHandlerPC()) {
			return false;
		} else if(getCatchType() != ExceptionHandler.class.cast(object).getCatchType()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code catch_type} item associated with this {@code ExceptionHandler} instance.
	 * 
	 * @return the value of the {@code catch_type} item associated with this {@code ExceptionHandler} instance
	 */
	public int getCatchType() {
		return this.catchType;
	}
	
	/**
	 * Returns the value of the {@code end_pc} item associated with this {@code ExceptionHandler} instance.
	 * 
	 * @return the value of the {@code end_pc} item associated with this {@code ExceptionHandler} instance
	 */
	public int getEndPC() {
		return this.endPC;
	}
	
	/**
	 * Returns the value of the {@code handler_pc} item associated with this {@code ExceptionHandler} instance.
	 * 
	 * @return the value of the {@code handler_pc} item associated with this {@code ExceptionHandler} instance
	 */
	public int getHandlerPC() {
		return this.handlerPC;
	}
	
	/**
	 * Returns the length of this {@code ExceptionHandler} instance.
	 * 
	 * @return the length of this {@code ExceptionHandler} instance
	 */
	@SuppressWarnings("static-method")
	public int getLength() {
		return 8;
	}
	
	/**
	 * Returns the value of the {@code start_pc} item associated with this {@code ExceptionHandler} instance.
	 * 
	 * @return the value of the {@code start_pc} item associated with this {@code ExceptionHandler} instance
	 */
	public int getStartPC() {
		return this.startPC;
	}
	
	/**
	 * Returns a hash code for this {@code ExceptionHandler} instance.
	 * 
	 * @return a hash code for this {@code ExceptionHandler} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getStartPC()), Integer.valueOf(getEndPC()), Integer.valueOf(getHandlerPC()), Integer.valueOf(getCatchType()));
	}
	
	/**
	 * Sets {@code catchType} as the value for the {@code catch_type} item associated with this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code catchType} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param catchType the value for the {@code catch_type} item associated with this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code catchType} is less than {@code 0}
	 */
	public void setCatchType(final int catchType) {
		this.catchType = ParameterArguments.requireRange(catchType, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code endPC} as the value for the {@code end_pc} item associated with this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code endPC} is less than or equal to {@code getStartPC()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param endPC the value for the {@code end_pc} item associated with this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code endPC} is less than or equal to {@code getStartPC()}
	 */
	public void setEndPC(final int endPC) {
		this.endPC = ParameterArguments.requireRange(endPC, getStartPC() + 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code handlerPC} as the value for the {@code handler_pc} item associated with this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code handlerPC} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param handlerPC the value for the {@code handler_pc} item associated with this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code handlerPC} is less than {@code 0}
	 */
	public void setHandlerPC(final int handlerPC) {
		this.handlerPC = ParameterArguments.requireRange(handlerPC, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code startPC} as the value for the {@code start_pc} item associated with this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code startPC} is less than {@code 0} or greater than or equal to {@code getEndPC()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code startPC} is less than {@code 0} or greater than or equal to {@code getEndPC()}
	 */
	public void setStartPC(final int startPC) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, getEndPC() - 1);
	}
	
	/**
	 * Writes this {@code ExceptionHandler} to {@code dataOutput}.
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
			dataOutput.writeShort(getEndPC());
			dataOutput.writeShort(getHandlerPC());
			dataOutput.writeShort(getCatchType());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
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
import java.lang.reflect.Field;//TODO: Update Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code ExceptionHandler} that can be found as a part of any {@link CodeAttribute} instances.
 * <p>
 * This class is not thread-safe.
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
	 * Constructs a new {@code ExceptionHandler} based on a start_pc, end_pc and handler_pc.
	 * <p>
	 * If {@code startPC} is less than {@code 0}, {@code endPC} is less than or equal to {@code startPC} or {@code handlerPC} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the start_pc to use
	 * @param endPC the end_pc to use
	 * @param handlerPC the handler_pc to use
	 * @throws IllegalArgumentException thrown if, and only if, {@code startPC} is less than {@code 0}, {@code endPC} is less than or equal to {@code startPC} or {@code handlerPC} is less than {@code 0}
	 */
	public ExceptionHandler(final int startPC, final int endPC, final int handlerPC) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, endPC - 1);
		this.endPC = ParameterArguments.requireRange(endPC, startPC + 1, 65535);
		this.handlerPC = ParameterArguments.requireRange(handlerPC, 0, 65535);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ExceptionHandler} instance.
	 * 
	 * @return a copy of this {@code ExceptionHandler} instance
	 */
	public ExceptionHandler copy() {
		final
		ExceptionHandler exceptionHandler = new ExceptionHandler(this.startPC, this.endPC, this.handlerPC);
		exceptionHandler.setCatchType(getCatchType());
		
		return exceptionHandler;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ExceptionHandler} instance.
	 * 
	 * @return a {@code String} representation of this {@code ExceptionHandler} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("exception");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("start_pc=" + getStartPC());
		stringBuilder.append(" ");
		stringBuilder.append("end_pc=" + getEndPC());
		stringBuilder.append(" ");
		stringBuilder.append("handler_pc=" + getHandlerPC());
		stringBuilder.append(" ");
		stringBuilder.append("catch_type=" + getCatchType());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ExceptionHandler}, and that {@code ExceptionHandler} instance is equal to this {@code ExceptionHandler} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ExceptionHandler} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ExceptionHandler}, and that {@code ExceptionHandler} instance is equal to this {@code ExceptionHandler} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ExceptionHandler)) {
			return false;
		} else if(ExceptionHandler.class.cast(object).getStartPC() != getStartPC()) {
			return false;
		} else if(ExceptionHandler.class.cast(object).getEndPC() != getEndPC()) {
			return false;
		} else if(ExceptionHandler.class.cast(object).getHandlerPC() != getHandlerPC()) {
			return false;
		} else if(ExceptionHandler.class.cast(object).getCatchType() != getCatchType()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the catch_type of this {@code ExceptionHandler} instance.
	 * 
	 * @return the catch_type of this {@code ExceptionHandler} instance
	 */
	public int getCatchType() {
		return this.catchType;
	}
	
	/**
	 * Returns the end_pc of this {@code ExceptionHandler} instance.
	 * 
	 * @return the end_pc of this {@code ExceptionHandler} instance
	 */
	public int getEndPC() {
		return this.endPC;
	}
	
	/**
	 * Returns the handler_pc of this {@code ExceptionHandler} instance.
	 * 
	 * @return the handler_pc of this {@code ExceptionHandler} instance
	 */
	public int getHandlerPC() {
		return this.handlerPC;
	}
	
	/**
	 * Returns the length of this {@code ExceptionHandler} instance.
	 * <p>
	 * All {@code ExceptionHandler}s have a length of {@code 8}.
	 * 
	 * @return the length of this {@code ExceptionHandler} instance
	 */
	@SuppressWarnings("static-method")
	public int getLength() {
		return 8;
	}
	
	/**
	 * Returns the start_pc of this {@code ExceptionHandler} instance.
	 * 
	 * @return the start_pc of this {@code ExceptionHandler} instance
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
	 * Sets a new catch_type for this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code catchType} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param catchType the new catch_type for this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code catchType} is less than {@code 0}
	 */
	public void setCatchType(final int catchType) {
		this.catchType = ParameterArguments.requireRange(catchType, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new end_pc for this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code endPC} is less than or equal to {@code getStartPC()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param endPC the new end_pc for this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code endPC} is less than or equal to {@code getStartPC()}
	 */
	public void setEndPC(final int endPC) {
		this.endPC = ParameterArguments.requireRange(endPC, this.startPC + 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new handler_pc for this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code handlerPC} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param handlerPC the new handler_pc for this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code handlerPC} is less than {@code 1}
	 */
	public void setHandlerPC(final int handlerPC) {
		this.handlerPC = ParameterArguments.requireRange(handlerPC, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new start_pc for this {@code ExceptionHandler} instance.
	 * <p>
	 * If {@code startPC} is less than {@code 1} or greater than or equal to {@code getEndPC()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the new start_pc for this {@code ExceptionHandler} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code startPC} is less {@code 1} or greater than or equal to {@code getEndPC()}
	 */
	public void setStartPC(final int startPC) {
		this.startPC = ParameterArguments.requireRange(startPC, 1, this.endPC - 1);
	}
	
	/**
	 * Writes this {@code ExceptionHandler} to {@code dataOutput}.
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
			dataOutput.writeShort(this.startPC);
			dataOutput.writeShort(this.endPC);
			dataOutput.writeShort(this.handlerPC);
			dataOutput.writeShort(this.catchType);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
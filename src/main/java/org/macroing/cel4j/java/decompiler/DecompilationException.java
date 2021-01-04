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
package org.macroing.cel4j.java.decompiler;

/**
 * Thrown to indicate that the decompilation process performed by a {@link Decompiler} failed.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DecompilationException extends IllegalStateException {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a {@code DecompilationException} with no detail message or cause.
	 */
	public DecompilationException() {
		
	}
	
	/**
	 * Constructs a {@code DecompilationException} with a detail message but no cause.
	 * 
	 * @param message a message describing this {@code DecompilationException}
	 */
	public DecompilationException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a {@code DecompilationException} with a detail message and a cause.
	 * 
	 * @param message a message describing this {@code DecompilationException}
	 * @param cause the {@code Throwable} that caused this {@code DecompilationException} to be thrown
	 */
	public DecompilationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a {@code DecompilationException} with no detail message but a cause.
	 * 
	 * @param cause the {@code Throwable} that caused this {@code DecompilationException} to be thrown
	 */
	public DecompilationException(final Throwable cause) {
		super(cause);
	}
}
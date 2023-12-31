/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.reader;

/**
 * Thrown to indicate that a {@link ClassFileReader} could not read a byte sequence properly.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassFileReaderException extends IllegalStateException {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a {@code ClassFileReaderException} with no detail message or cause.
	 */
	public ClassFileReaderException() {
		
	}
	
	/**
	 * Constructs a {@code ClassFileReaderException} with a detail message but no cause.
	 * 
	 * @param message a message describing this {@code ClassFileReaderException}
	 */
	public ClassFileReaderException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a {@code ClassFileReaderException} with a detail message and a cause.
	 * 
	 * @param message a message describing this {@code ClassFileReaderException}
	 * @param cause the {@code Throwable} that caused this {@code ClassFileReaderException} to be thrown
	 */
	public ClassFileReaderException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a {@code ClassFileReaderException} with no detail message but a cause.
	 * 
	 * @param cause the {@code Throwable} that caused this {@code ClassFileReaderException} to be thrown
	 */
	public ClassFileReaderException(final Throwable cause) {
		super(cause);
	}
}
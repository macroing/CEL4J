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
package org.macroing.cel4j.json;

/**
 * Thrown to indicate that an error has occurred while parsing JSON.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONParserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a {@code JSONParserException} with no detail message or cause.
	 */
	public JSONParserException() {
		
	}
	
	/**
	 * Constructs a {@code JSONParserException} with a detail message but no cause.
	 * 
	 * @param message a message describing this {@code JSONParserException}
	 */
	public JSONParserException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a {@code JSONParserException} with a detail message and a cause.
	 * 
	 * @param message a message describing this {@code JSONParserException}
	 * @param cause the {@code Throwable} that caused this {@code JSONParserException} to be thrown
	 */
	public JSONParserException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a {@code JSONParserException} with no detail message but a cause.
	 * 
	 * @param cause the {@code Throwable} that caused this {@code JSONParserException} to be thrown
	 */
	public JSONParserException(final Throwable cause) {
		super(cause);
	}
}
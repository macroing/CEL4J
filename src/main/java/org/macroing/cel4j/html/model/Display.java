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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * A {@code Display} represents a display property in CSS and HTML source code.
 * <p>
 * This enum is used as a measure to restrict what can and what cannot be added to an {@link Element} with children. It is also used to write the HTML source code in the correct way.
 * <p>
 * The display properties {@code contents}, {@code flex}, {@code grid}, {@code inherit}, {@code initial}, {@code inline-block}, {@code inline-flex}, {@code inline-grid}, {@code inline-table}, {@code list-item}, {@code run-in}, {@code table},
 * {@code table-caption}, {@code table-cell}, {@code table-column}, {@code table-column-group}, {@code table-header-group}, {@code table-footer-group}, {@code table-row} and {@code table-row-group} are not supported.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Display {
	/**
	 * A {@code Display} that represents {@code block}.
	 */
	BLOCK,
	
	/**
	 * A {@code Display} that represents {@code inline}.
	 */
	INLINE,
	
	/**
	 * A {@code Display} that represents {@code none}.
	 */
	NONE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Display() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, an {@link Element} with this {@code Display} instance as its display property supports adding an {@code Element} with {@code display} as its display property, {@code false} otherwise.
	 * <p>
	 * If {@code display} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param display a {@code Display} instance
	 * @return {@code true} if, and only if, an {@code Element} with this {@code Display} instance as its display property supports adding an {@code Element} with {@code display} as its display property, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code display} is {@code null}
	 */
	public boolean isSupporting(final Display display) {
		Objects.requireNonNull(display, "display == null");
		
		switch(this) {
			case BLOCK:
				return display != NONE;
			case INLINE:
				return display == INLINE;
			case NONE:
				return false;
			default:
				return false;
		}
	}
}
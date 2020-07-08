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
package org.macroing.cel4j.html.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.Document;

/**
 * A {@code Text} is a {@link Content} implementation that contains text content.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Text implements Content<Element> {
	private final String string;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Text} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Text("");
	 * }
	 * </pre>
	 */
	public Text() {
		this("");
	}
	
	/**
	 * Constructs a new {@code Text} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param string the {@code String} associated with this {@code Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Text(final String string) {
		this.string = Objects.requireNonNull(string, "string == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Display} associated with this {@code Text} instance.
	 * 
	 * @return the {@code Display} associated with this {@code Text} instance
	 */
	@Override
	public Display getDisplay() {
		return Display.INLINE;
	}
	
	/**
	 * Writes this {@code Text} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * text.write(new Document());
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
	 * Writes this {@code Text} to {@code document}.
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
		Objects.requireNonNull(document, "document == null");
		
		document.text(getString());
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link Element} instances currently added to this {@code Text} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Text} instance.
	 * 
	 * @return a {@code List} with all {@code Element} instances currently added to this {@code Text} instance
	 */
	@Override
	public List<Element> getElements() {
		return new ArrayList<>(Arrays.asList(new Span(this)));
	}
	
	/**
	 * Returns the {@code String} associated with this {@code Text} instance.
	 * 
	 * @return the {@code String} associated with this {@code Text} instance
	 */
	public String getString() {
		return this.string;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Text} instance.
	 * 
	 * @return a {@code String} representation of this {@code Text} instance
	 */
	@Override
	public String toString() {
		return String.format("new Text(\"%s\")", getString());
	}
	
	/**
	 * Compares {@code object} to this {@code Text} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Text}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Text} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Text}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Text)) {
			return false;
		} else if(!Objects.equals(getString(), Text.class.cast(object).getString())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Text} instance.
	 * 
	 * @return a hash code for this {@code Text} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getString());
	}
}
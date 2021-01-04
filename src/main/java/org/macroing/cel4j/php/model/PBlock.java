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
package org.macroing.cel4j.php.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code PBlock} represents a simple abstraction for a block in PHP source code.
 * <p>
 * The classes {@link PConstructor}, {@link PDocument} and {@link PMethod} all contains an instance of this {@code PBlock} class.
 * <p>
 * To use this class, consider the following example:
 * <pre>
 * {@code
 * PBlock pBlock = new PBlock();
 * pBlock.addComment("This is a comment!");
 * pBlock.addLinef("return $this->%s;", "name");
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PBlock implements Documentable {
	private final List<Line> lines;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code PBlock} instance.
	 */
	public PBlock() {
		this.lines = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PBlock} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pBlock.write(new Document());
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
	 * Writes this {@code PBlock} to {@code document}.
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
		
		for(final Line line : this.lines) {
			document.line(line.getTextAfterIndentation(), line.getTextBeforeIndentation());
		}
		
		return document;
	}
	
	/**
	 * Compares {@code object} to this {@code PBlock} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PBlock}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PBlock} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PBlock}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PBlock)) {
			return false;
		} else if(!Objects.equals(this.lines, PBlock.class.cast(object).lines)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PBlock} instance is empty, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PBlock} instance is empty, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return this.lines.isEmpty();
	}
	
	/**
	 * Returns a hash code for this {@code PBlock} instance.
	 * 
	 * @return a hash code for this {@code PBlock} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.lines);
	}
	
	/**
	 * Adds a line of text representing a comment to this {@code PBlock} instance.
	 * <p>
	 * If {@code comment} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param comment the comment
	 * @throws NullPointerException thrown if, and only if, {@code comment} is {@code null}
	 */
	public void addComment(final String comment) {
		this.lines.add(new Line(Objects.requireNonNull(comment, "comment == null"), "//"));
	}
	
	/**
	 * Adds a line of text to this {@code PBlock} instance.
	 * <p>
	 * If {@code line} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param line the line of text
	 * @throws NullPointerException thrown if, and only if, {@code line} is {@code null}
	 */
	public void addLine(final String line) {
		this.lines.add(new Line(Objects.requireNonNull(line, "line == null")));
	}
	
	/**
	 * Adds a line of text to this {@code PBlock} instance.
	 * <p>
	 * If either {@code lineFormat} or {@code objects} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param lineFormat the format {@code String} used as the first parameter argument for the method {@link String#format(String, Object...)}
	 * @param objects the {@code Object} array used as the second parameter argument for the method {@link String#format(String, Object...)}
	 * @throws NullPointerException thrown if, and only if, either {@code lineFormat} or {@code objects} are {@code null}
	 */
	public void addLinef(final String lineFormat, final Object... objects) {
		this.lines.add(new Line(String.format(Objects.requireNonNull(lineFormat, "lineFormat == null"), Objects.requireNonNull(objects, "objects == null"))));
	}
	
	/**
	 * Clears this {@code PBlock} instance.
	 */
	public void clear() {
		this.lines.clear();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Line {
		private final String textAfterIndentation;
		private final String textBeforeIndentation;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Line(final String textAfterIndentation) {
			this(textAfterIndentation, "");
		}
		
		public Line(final String textAfterIndentation, final String textBeforeIndentation) {
			this.textAfterIndentation = Objects.requireNonNull(textAfterIndentation, "textAfterIndentation == null");
			this.textBeforeIndentation = Objects.requireNonNull(textBeforeIndentation, "textBeforeIndentation == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public String getTextAfterIndentation() {
			return this.textAfterIndentation;
		}
		
		public String getTextBeforeIndentation() {
			return this.textBeforeIndentation;
		}
		
		@Override
		public String toString() {
			return String.format("new Line(\"%s\", \"%s\")", this.textAfterIndentation, this.textBeforeIndentation);
		}
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof Line)) {
				return false;
			} else if(!Objects.equals(this.textAfterIndentation, Line.class.cast(object).textAfterIndentation)) {
				return false;
			} else if(!Objects.equals(this.textBeforeIndentation, Line.class.cast(object).textBeforeIndentation)) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.textAfterIndentation, this.textBeforeIndentation);
		}
	}
}
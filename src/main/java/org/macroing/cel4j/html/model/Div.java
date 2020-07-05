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
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;

/**
 * A {@code Div} represents a {@code div} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Div extends Element {
	/**
	 * The initial {@link Display} associated with a {@code Div} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code Div} instance.
	 */
	public static final String NAME = "div";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Element> elements;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Div} instance.
	 */
	public Div() {
		super(NAME, DISPLAY_INITIAL);
		
		this.elements = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code Div} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * div.write(new Document());
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
	 * Writes this {@code Div} to {@code document}.
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
		return write(document, this, getElements());
	}
	
	/**
	 * Returns a {@code List} with all {@link Element} instances currently added to this {@code Div} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Div} instance.
	 * 
	 * @return a {@code List} with all {@code Element} instances currently added to this {@code Div} instance
	 */
	public List<Element> getElements() {
		return new ArrayList<>(this.elements);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Div} instance.
	 * 
	 * @return a {@code String} representation of this {@code Div} instance
	 */
	@Override
	public String toString() {
		return "new Div()";
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node} instances, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Attribute attribute : getAttributes()) {
					if(!attribute.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final Element element : getElements()) {
					if(!element.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Adds {@code element} to this {@code Div} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code element} was added, {@code false} otherwise.
	 * <p>
	 * If {@code element} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param element the {@link Element} to add
	 * @return {@code true} if, and only if, {@code element} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code element} is {@code null}
	 */
	public boolean addElement(final Element element) {
		Objects.requireNonNull(element, "element == null");
		
		if(getDisplay().isSupporting(element.getDisplay())) {
			return this.elements.add(element);
		}
		
		return false;
	}
	
	/**
	 * Compares {@code object} to this {@code Div} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Div}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Div} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Div}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Div)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Div.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Div.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getElements(), Div.class.cast(object).getElements())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Removes {@code element} from this {@code Div} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code element} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code element} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param element the {@link Element} to remove
	 * @return {@code true} if, and only if, {@code element} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code element} is {@code null}
	 */
	public boolean removeElement(final Element element) {
		return this.elements.remove(Objects.requireNonNull(element, "element == null"));
	}
	
	/**
	 * Returns a hash code for this {@code Div} instance.
	 * 
	 * @return a hash code for this {@code Div} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getElements());
	}
}
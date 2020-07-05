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

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code Elements} is a {@link Content} implementation that contains {@link Element} instances as content.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Elements implements Content {
	private final ContentElement contentElement;
	private final List<Element> elements;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Elements} instance.
	 * <p>
	 * If either {@code contentElement}, {@code elements} or an {@link Element} in {@code elements} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param contentElement the {@link ContentElement} associated with this {@code Elements} instance
	 * @param elements the {@code Element} instances to add to this {@code Elements} instance
	 * @throws NullPointerException thrown if, and only if, either {@code contentElement}, {@code elements} or an {@code Element} in {@code elements} are {@code null}
	 */
	public Elements(final ContentElement contentElement, final Element... elements) {
		this.contentElement = Objects.requireNonNull(contentElement, "contentElement == null");
		this.elements = new ArrayList<>(Arrays.asList(ParameterArguments.requireNonNullArray(elements, "elements")));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ContentElement} associated with this {@code Elements} instance.
	 * 
	 * @return the {@code ContentElement} associated with this {@code Elements} instance
	 */
	public ContentElement getContentElement() {
		return this.contentElement;
	}
	
	/**
	 * Returns the {@link Display} associated with this {@code Elements} instance.
	 * 
	 * @return the {@code Display} associated with this {@code Elements} instance
	 */
	@Override
	public Display getDisplay() {
		return getElements().stream().allMatch(element -> element.getDisplay() == Display.INLINE) ? Display.INLINE : Display.BLOCK;
	}
	
	/**
	 * Writes this {@code Elements} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * elements.write(new Document());
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
	 * Writes this {@code Elements} to {@code document}.
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
		
		if(getDisplay() == Display.INLINE) {
			for(final Element element : getElements()) {
				document.text(element.write().toString());
			}
		} else {
			for(final Element element : getElements()) {
				element.write(document);
			}
		}
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link Element} instances currently added to this {@code Elements} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Elements} instance.
	 * 
	 * @return a {@code List} with all {@code Element} instances currently added to this {@code Elements} instance
	 */
	public List<Element> getElements() {
		return new ArrayList<>(this.elements);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Elements} instance.
	 * 
	 * @return a {@code String} representation of this {@code Elements} instance
	 */
	@Override
	public String toString() {
		return String.format("new Elements(%s)", getContentElement());
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
	 * Adds {@code element} to this {@code Elements} instance.
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
		
		if(getContentElement().getDisplay().isSupporting(element.getDisplay())) {
			return this.elements.add(element);
		}
		
		return false;
	}
	
	/**
	 * Compares {@code object} to this {@code Elements} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Elements}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Elements} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Elements}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Elements)) {
			return false;
		} else if(!Objects.equals(getContentElement(), Elements.class.cast(object).getContentElement())) {
			return false;
		} else if(!Objects.equals(getElements(), Elements.class.cast(object).getElements())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Removes {@code element} from this {@code Elements} instance.
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
	 * Returns a hash code for this {@code Elements} instance.
	 * 
	 * @return a hash code for this {@code Elements} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getContentElement(), getElements());
	}
}
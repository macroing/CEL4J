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
package org.macroing.cel4j.html.model;

import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code ContentElement} represents an element in HTML source code that contains content.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class ContentElement<T extends Element, U extends Content<T>> extends Element {
	private U content;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ContentElement} instance.
	 * <p>
	 * If either {@code name}, {@code display} or {@code content} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name associated with this {@code ContentElement} instance
	 * @param display the {@link Display} associated with this {@code ContentElement} instance
	 * @param content the {@link Content} associated with this {@code ContentElement} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name}, {@code display} or {@code content} are {@code null}
	 */
	protected ContentElement(final String name, final Display display, final U content) {
		super(name, display);
		
		this.content = Objects.requireNonNull(content, "content == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code ContentElement} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * contentElement.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public final Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code ContentElement} to {@code document}.
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
	public final Document write(final Document document) {
		Objects.requireNonNull(document, "document == null");
		
		final Content<T> content = getContent();
		
		final Display display = content.getDisplay();
		
		final List<Attribute> attributes = getAttributesSet();
		
		final String name = getName();
		
		switch(display) {
			case BLOCK:
				document.linef("<%s%s>", name, Strings.optional(attributes, " ", "", " ", attribute -> attribute.getNameAndValue()));
				document.indent();
				
				content.write(document);
				
				document.outdent();
				document.linef("</%s>", name);
				
				break;
			case INLINE:
				document.linef("<%s%s>%s</%s>", name, Strings.optional(attributes, " ", "", " ", attribute -> attribute.getNameAndValue()), content.write().toString().replaceAll("\n|\r\n|\r|\t", ""), name);
				
				break;
			case NONE:
				document.linef("<%s%s>", name, Strings.optional(attributes, " ", "", " ", attribute -> attribute.getNameAndValue()));
				document.indent();
				
				content.write(document);
				
				document.outdent();
				document.linef("</%s>", name);
				
				break;
			default:
				break;
		}
		
		return document;
	}
	
	/**
	 * Returns the {@link Content} associated with this {@code ContentElement} instance.
	 * 
	 * @return the {@code Content} associated with this {@code ContentElement} instance
	 */
	public final U getContent() {
		return this.content;
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
	public final boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Attribute attribute : getAttributes()) {
					if(!attribute.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				if(!getContent().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Sets {@code content} as the {@link Content} associated with this {@code ContentElement} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@code Content} associated with this {@code ContentElement} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public final void setContent(final U content) {
		this.content = Objects.requireNonNull(content, "content == null");
	}
}
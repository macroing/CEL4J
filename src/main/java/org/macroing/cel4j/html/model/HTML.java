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

import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * An {@code HTML} represents an {@code html} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class HTML extends Element {
	/**
	 * The initial {@link Display} associated with an {@code HTML} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with an {@code HTML} instance.
	 */
	public static final String NAME = "html";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeXMLNS;
	private Body body;
	private Head head;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code HTML} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new HTML(new Head(), new Body());
	 * }
	 * </pre>
	 */
	public HTML() {
		this(new Head(), new Body());
	}
	
	/**
	 * Constructs a new {@code HTML} instance.
	 * <p>
	 * If either {@code head} or {@code body} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param head the {@link Head} associated with this {@code HTML} instance
	 * @param body the {@link Body} associated with this {@code HTML} instance
	 * @throws NullPointerException thrown if, and only if, either {@code head} or {@code body} are {@code null}
	 */
	public HTML(final Head head, final Body body) {
		super(NAME, DISPLAY_INITIAL);
		
		this.attributeXMLNS = new Attribute("xmlns");
		this.body = Objects.requireNonNull(body, "body == null");
		this.head = Objects.requireNonNull(head, "head == null");
		
		addAttribute(this.attributeXMLNS);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "xmlns"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "xmlns"}
	 */
	public Attribute getAttributeXMLNS() {
		return this.attributeXMLNS;
	}
	
	/**
	 * Returns the {@link Body} associated with this {@code HTML} instance.
	 * 
	 * @return the {@code Body} associated with this {@code HTML} instance
	 */
	public Body getBody() {
		return this.body;
	}
	
	/**
	 * Writes this {@code HTML} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * hTML.write(new Document());
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
	 * Writes this {@code HTML} to {@code document}.
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
		
		final Body body = getBody();
		
		final Head head = getHead();
		
		final List<Attribute> attributes = getAttributesSet();
		
		final String name = getName();
		
		document.linef("<!doctype html>");
		document.linef("<%s%s>", name, Strings.optional(attributes, " ", "", " ", attribute -> attribute.getNameAndValue()));
		document.indent();
		
		head.write(document);
		
		body.write(document);
		
		document.outdent();
		document.linef("</%s>", name);
		
		return document;
	}
	
	/**
	 * Returns the {@link Head} associated with this {@code HTML} instance.
	 * 
	 * @return the {@code Head} associated with this {@code HTML} instance
	 */
	public Head getHead() {
		return this.head;
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
	 * <li>traverse its child {@code Node} instances.</li>
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
				
				if(!getHead().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!getBody().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code HTML} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code HTML}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code HTML} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code HTML}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof HTML)) {
			return false;
		} else if(!Objects.equals(getAttributes(), HTML.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), HTML.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getHead(), HTML.class.cast(object).getHead())) {
			return false;
		} else if(!Objects.equals(getBody(), HTML.class.cast(object).getBody())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code HTML} instance.
	 * 
	 * @return a hash code for this {@code HTML} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getHead(), getBody());
	}
	
	/**
	 * Sets {@code body} as the {@link Body} associated with this {@code HTML} instance.
	 * <p>
	 * If {@code body} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param body the {@code Body} associated with this {@code HTML} instance
	 * @throws NullPointerException thrown if, and only if, {@code body} is {@code null}
	 */
	public void setBody(final Body body) {
		this.body = Objects.requireNonNull(body, "body == null");
	}
	
	/**
	 * Sets {@code head} as the {@link Head} associated with this {@code HTML} instance.
	 * <p>
	 * If {@code head} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param head the {@code Head} associated with this {@code HTML} instance
	 * @throws NullPointerException thrown if, and only if, {@code head} is {@code null}
	 */
	public void setHead(final Head head) {
		this.head = Objects.requireNonNull(head, "head == null");
	}
}
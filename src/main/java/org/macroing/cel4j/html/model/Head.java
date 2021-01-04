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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code Head} represents a {@code head} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Head extends Element {
	/**
	 * The initial {@link Display} associated with a {@code Head} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code Head} instance.
	 */
	public static final String NAME = "head";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Link> links;
	private final List<Meta> metas;
	private final List<Script> scripts;
	private final Title title;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Head} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Head(new Title());
	 * }
	 * </pre>
	 */
	public Head() {
		this(new Title());
	}
	
	/**
	 * Constructs a new {@code Head} instance.
	 * <p>
	 * If {@code title} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param title the {@link Title} associated with this {@code Head} instance
	 * @throws NullPointerException thrown if, and only if, {@code title} is {@code null}
	 */
	public Head(final Title title) {
		super(NAME, DISPLAY_INITIAL);
		
		this.links = new ArrayList<>();
		this.metas = new ArrayList<>();
		this.scripts = new ArrayList<>();
		this.title = Objects.requireNonNull(title, "title == null");
	}
	
	/**
	 * Constructs a new {@code Head} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Head(new Title(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in a {@link Title} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Head(final String string) {
		this(new Title(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code Head} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * head.write(new Document());
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
	 * Writes this {@code Head} to {@code document}.
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
		
		final List<Attribute> attributes = getAttributesSet();
		final List<Link> links = getLinks();
		final List<Meta> metas = getMetas();
		final List<Script> scripts = getScripts();
		
		final String name = getName();
		
		final Title title = getTitle();
		
		document.linef("<%s%s>", name, Strings.optional(attributes, " ", "", " ", attribute -> attribute.getNameAndValue()));
		document.indent();
		
		for(final Link link : links) {
			link.write(document);
		}
		
		for(final Meta meta : metas) {
			meta.write(document);
		}
		
		for(final Script script : scripts) {
			script.write(document);
		}
		
		title.write(document);
		
		document.outdent();
		document.linef("</%s>", name);
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link Link} instances currently added to this {@code Head} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Head} instance.
	 * 
	 * @return a {@code List} with all {@code Link} instances currently added to this {@code Head} instance
	 */
	public List<Link> getLinks() {
		return new ArrayList<>(this.links);
	}
	
	/**
	 * Returns a {@code List} with all {@link Meta} instances currently added to this {@code Head} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Head} instance.
	 * 
	 * @return a {@code List} with all {@code Meta} instances currently added to this {@code Head} instance
	 */
	public List<Meta> getMetas() {
		return new ArrayList<>(this.metas);
	}
	
	/**
	 * Returns a {@code List} with all {@link Script} instances currently added to this {@code Head} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Head} instance.
	 * 
	 * @return a {@code List} with all {@code Script} instances currently added to this {@code Head} instance
	 */
	public List<Script> getScripts() {
		return new ArrayList<>(this.scripts);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Head} instance.
	 * 
	 * @return a {@code String} representation of this {@code Head} instance
	 */
	@Override
	public String toString() {
		return String.format("new Head(%s)", getTitle());
	}
	
	/**
	 * Returns the {@link Title} associated with this {@code Head} instance.
	 * 
	 * @return the {@code Title} associated with this {@code Head} instance
	 */
	public Title getTitle() {
		return this.title;
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
				
				for(final Link link : getLinks()) {
					if(!link.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final Meta meta : getMetas()) {
					if(!meta.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final Script script : getScripts()) {
					if(!script.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				if(!getTitle().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Adds {@code link} to this {@code Head} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code link} was added, {@code false} otherwise.
	 * <p>
	 * If {@code link} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param link the {@link Link} to add
	 * @return {@code true} if, and only if, {@code link} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code link} is {@code null}
	 */
	public boolean addLink(final Link link) {
		return !this.links.contains(Objects.requireNonNull(link, "link == null")) ? this.links.add(link) : false;
	}
	
	/**
	 * Adds {@code meta} to this {@code Head} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code meta} was added, {@code false} otherwise.
	 * <p>
	 * If {@code meta} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param meta the {@link Meta} to add
	 * @return {@code true} if, and only if, {@code meta} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code meta} is {@code null}
	 */
	public boolean addMeta(final Meta meta) {
		return !this.metas.contains(Objects.requireNonNull(meta, "meta == null")) ? this.metas.add(meta) : false;
	}
	
	/**
	 * Adds {@code script} to this {@code Head} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code script} was added, {@code false} otherwise.
	 * <p>
	 * If {@code script} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param script the {@link Script} to add
	 * @return {@code true} if, and only if, {@code script} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code script} is {@code null}
	 */
	public boolean addScript(final Script script) {
		return !this.scripts.contains(Objects.requireNonNull(script, "script == null")) ? this.scripts.add(script) : false;
	}
	
	/**
	 * Compares {@code object} to this {@code Head} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Head}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Head} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Head}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Head)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Head.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Head.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getLinks(), Head.class.cast(object).getLinks())) {
			return false;
		} else if(!Objects.equals(getMetas(), Head.class.cast(object).getMetas())) {
			return false;
		} else if(!Objects.equals(getScripts(), Head.class.cast(object).getScripts())) {
			return false;
		} else if(!Objects.equals(getTitle(), Head.class.cast(object).getTitle())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Removes {@code link} from this {@code Head} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code link} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code link} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param link the {@link Link} to remove
	 * @return {@code true} if, and only if, {@code link} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code link} is {@code null}
	 */
	public boolean removeLink(final Link link) {
		return this.links.remove(Objects.requireNonNull(link, "link == null"));
	}
	
	/**
	 * Removes {@code meta} from this {@code Head} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code meta} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code meta} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param meta the {@link Meta} to remove
	 * @return {@code true} if, and only if, {@code meta} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code meta} is {@code null}
	 */
	public boolean removeMeta(final Meta meta) {
		return this.metas.remove(Objects.requireNonNull(meta, "meta == null"));
	}
	
	/**
	 * Removes {@code script} from this {@code Head} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code script} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code script} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param script the {@link Script} to remove
	 * @return {@code true} if, and only if, {@code script} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code script} is {@code null}
	 */
	public boolean removeScript(final Script script) {
		return this.scripts.remove(Objects.requireNonNull(script, "script == null"));
	}
	
	/**
	 * Returns a hash code for this {@code Head} instance.
	 * 
	 * @return a hash code for this {@code Head} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getLinks(), getMetas(), getScripts(), getTitle());
	}
}
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.Documentable;

/**
 * An {@code Element} represents an element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Element implements Documentable, Node {
	private final AtomicReference<Display> display;
	private final Attribute attributeAccessKey;
	private final Attribute attributeAriaControls;
	private final Attribute attributeAriaCurrent;
	private final Attribute attributeAriaDisabled;
	private final Attribute attributeAriaExpanded;
	private final Attribute attributeAriaHidden;
	private final Attribute attributeAriaLabel;
	private final Attribute attributeClass;
	private final Attribute attributeContentEditable;
	private final Attribute attributeDir;
	private final Attribute attributeDraggable;
	private final Attribute attributeDropZone;
	private final Attribute attributeHidden;
	private final Attribute attributeId;
	private final Attribute attributeLang;
	private final Attribute attributeSpellCheck;
	private final Attribute attributeStyle;
	private final Attribute attributeTabIndex;
	private final Attribute attributeTitle;
	private final Attribute attributeTranslate;
	private final List<Attribute> attributes;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Element} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * super(name, Display.BLOCK);
	 * }
	 * </pre>
	 * 
	 * @param name the case-insensitive name associated with this {@code Element} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	protected Element(final String name) {
		this(name, Display.BLOCK);
	}
	
	/**
	 * Constructs a new {@code Element} instance.
	 * <p>
	 * If either {@code name} or {@code display} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name associated with this {@code Element} instance
	 * @param display the {@link Display} associated with this {@code Element} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code display} are {@code null}
	 */
	protected Element(final String name, final Display display) {
		this.display = new AtomicReference<>(Objects.requireNonNull(display, "display == null"));
		this.attributeAccessKey = new Attribute("accesskey");
		this.attributeAriaControls = new Attribute("aria-controls");
		this.attributeAriaCurrent = new Attribute("aria-current");
		this.attributeAriaDisabled = new Attribute("aria-disabled");
		this.attributeAriaExpanded = new Attribute("aria-expanded");
		this.attributeAriaHidden = new Attribute("aria-hidden");
		this.attributeAriaLabel = new Attribute("aria-label");
		this.attributeClass = new Attribute("class");
		this.attributeContentEditable = new Attribute("contenteditable");
		this.attributeDir = new Attribute("dir");
		this.attributeDraggable = new Attribute("draggable");
		this.attributeDropZone = new Attribute("dropzone");
		this.attributeHidden = new Attribute("hidden");
		this.attributeId = new Attribute("id");
		this.attributeLang = new Attribute("lang");
		this.attributeSpellCheck = new Attribute("spellcheck");
		this.attributeStyle = new Attribute("style");
		this.attributeTabIndex = new Attribute("tabindex");
		this.attributeTitle = new Attribute("title");
		this.attributeTranslate = new Attribute("translate");
		this.attributes = new ArrayList<>();
		this.attributes.add(this.attributeAccessKey);
		this.attributes.add(this.attributeAriaControls);
		this.attributes.add(this.attributeAriaCurrent);
		this.attributes.add(this.attributeAriaDisabled);
		this.attributes.add(this.attributeAriaExpanded);
		this.attributes.add(this.attributeAriaHidden);
		this.attributes.add(this.attributeAriaLabel);
		this.attributes.add(this.attributeClass);
		this.attributes.add(this.attributeContentEditable);
		this.attributes.add(this.attributeDir);
		this.attributes.add(this.attributeDraggable);
		this.attributes.add(this.attributeDropZone);
		this.attributes.add(this.attributeHidden);
		this.attributes.add(this.attributeId);
		this.attributes.add(this.attributeLang);
		this.attributes.add(this.attributeSpellCheck);
		this.attributes.add(this.attributeStyle);
		this.attributes.add(this.attributeTabIndex);
		this.attributes.add(this.attributeTitle);
		this.attributes.add(this.attributeTranslate);
		this.name = name.toLowerCase();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "accesskey"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "accesskey"}
	 */
	public final Attribute getAttributeAccessKey() {
		return this.attributeAccessKey;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-controls"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-controls"}
	 */
	public final Attribute getAttributeAriaControls() {
		return this.attributeAriaControls;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-current"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-current"}
	 */
	public final Attribute getAttributeAriaCurrent() {
		return this.attributeAriaCurrent;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-disabled"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-disabled"}
	 */
	public final Attribute getAttributeAriaDisabled() {
		return this.attributeAriaDisabled;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-expanded"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-expanded"}
	 */
	public final Attribute getAttributeAriaExpanded() {
		return this.attributeAriaExpanded;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-hidden"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-hidden"}
	 */
	public final Attribute getAttributeAriaHidden() {
		return this.attributeAriaHidden;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "aria-label"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "aria-label"}
	 */
	public final Attribute getAttributeAriaLabel() {
		return this.attributeAriaLabel;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "class"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "class"}
	 */
	public final Attribute getAttributeClass() {
		return this.attributeClass;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "contenteditable"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "contenteditable"}
	 */
	public final Attribute getAttributeContentEditable() {
		return this.attributeContentEditable;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "dir"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "dir"}
	 */
	public final Attribute getAttributeDir() {
		return this.attributeDir;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "draggable"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "draggable"}
	 */
	public final Attribute getAttributeDraggable() {
		return this.attributeDraggable;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "dropzone"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "dropzone"}
	 */
	public final Attribute getAttributeDropZone() {
		return this.attributeDropZone;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "hidden"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "hidden"}
	 */
	public final Attribute getAttributeHidden() {
		return this.attributeHidden;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "id"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "id"}
	 */
	public final Attribute getAttributeId() {
		return this.attributeId;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "lang"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "lang"}
	 */
	public final Attribute getAttributeLang() {
		return this.attributeLang;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "spellcheck"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "spellcheck"}
	 */
	public final Attribute getAttributeSpellCheck() {
		return this.attributeSpellCheck;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "style"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "style"}
	 */
	public final Attribute getAttributeStyle() {
		return this.attributeStyle;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "tabindex"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "tabindex"}
	 */
	public final Attribute getAttributeTabIndex() {
		return this.attributeTabIndex;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "title"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "title"}
	 */
	public final Attribute getAttributeTitle() {
		return this.attributeTitle;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "translate"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "translate"}
	 */
	public final Attribute getAttributeTranslate() {
		return this.attributeTranslate;
	}
	
	/**
	 * Returns the {@link Display} associated with this {@code Element} instance.
	 * 
	 * @return the {@code Display} associated with this {@code Element} instance
	 */
	public final Display getDisplay() {
		return this.display.get();
	}
	
	/**
	 * Returns the initial {@link Display} associated with this {@code Element} instance.
	 * 
	 * @return the initial {@code Display} associated with this {@code Element} instance.
	 */
	public abstract Display getDisplayInitial();
	
	/**
	 * Returns a {@code List} with all {@link Attribute} instances currently added to this {@code Element} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Element} instance.
	 * 
	 * @return a {@code List} with all {@code Attribute} instances currently added to this {@code Element} instance
	 */
	public final List<Attribute> getAttributes() {
		return new ArrayList<>(this.attributes);
	}
	
	/**
	 * Returns a {@code List} with all {@link Attribute} instances currently added to this {@code Element} instance and are set with a non-empty value.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Element} instance.
	 * 
	 * @return a {@code List} with all {@code Attribute} instances currently added to this {@code Element} instance and are set with a non-empty value
	 */
	public final List<Attribute> getAttributesSet() {
		return this.attributes.stream().filter(attribute -> attribute.isSet()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns an {@code Optional} with the optional {@link Attribute} instance that may have been added to this {@code Element} instance and has a name of {@code name.toLowerCase()}.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name of the {@code Attribute} instance to get
	 * @return an {@code Optional} with the optional {@code Attribute} instance that may have been added to this {@code Element} instance and has a name of {@code name.toLowerCase()}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public final Optional<Attribute> getAttributeByName(final String name) {
		final String nameToLowerCase = name.toLowerCase();
		
		for(final Attribute attribute : this.attributes) {
			if(attribute.getName().equals(nameToLowerCase)) {
				return Optional.of(attribute);
			}
		}
		
		return Optional.empty();
	}
	
	/**
	 * Returns the name associated with this {@code Element} instance.
	 * <p>
	 * This method will return the name in lower case.
	 * 
	 * @return the name associated with this {@code Element} instance
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Adds {@code attribute} to this {@code Element} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attribute} was added, {@code false} otherwise.
	 * <p>
	 * If {@code attribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attribute the {@link Attribute} to add
	 * @return {@code true} if, and only if, {@code attribute} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attribute} is {@code null}
	 */
	public final boolean addAttribute(final Attribute attribute) {
		if(!this.attributes.contains(Objects.requireNonNull(attribute, "attribute == null"))) {
			return this.attributes.add(attribute);
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code attribute} is added to this {@code Element} instance, {@code false} otherwise.
	 * <p>
	 * If {@code attribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attribute the {@link Attribute} instance to check
	 * @return {@code true} if, and only if, {@code attribute} is added to this {@code Element} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attribute} is {@code null}
	 */
	public final boolean hasAttribute(final Attribute attribute) {
		return this.attributes.contains(Objects.requireNonNull(attribute, "attribute == null"));
	}
	
	/**
	 * Returns {@code true} if, and only if, an {@link Attribute} with the name {@code name.toLowerCase()} is added to this {@code Element} instance, {@code false} otherwise.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name of the {@code Attribute} instance to check
	 * @return {@code true} if, and only if, an {@code Attribute} with the name {@code name.toLowerCase()} is added to this {@code Element} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public final boolean hasAttributeByName(final String name) {
		final String nameToLowerCase = name.toLowerCase();
		
		for(final Attribute attribute : this.attributes) {
			if(attribute.getName().equals(nameToLowerCase)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes {@code attribute} from this {@code Element} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attribute} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code attribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attribute the {@link Attribute} to remove
	 * @return {@code true} if, and only if, {@code attribute} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attribute} is {@code null}
	 */
	public final boolean removeAttribute(final Attribute attribute) {
		return this.attributes.remove(Objects.requireNonNull(attribute, "attribute == null"));
	}
	
	/**
	 * Removes the {@link Attribute} instance that may have been added to this {@code Element} instance and has a name of {@code name.toLowerCase()}.
	 * <p>
	 * Returns {@code true} if, and only if, the {@code Attribute} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name of the {@code Attribute} instance to remove
	 * @return {@code true} if, and only if, the {@code Attribute} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public final boolean removeAttributeByName(final String name) {
		final Optional<Attribute> optionalAttribute = getAttributeByName(name);
		
		if(optionalAttribute.isPresent()) {
			return removeAttribute(optionalAttribute.get());
		}
		
		return false;
	}
	
	/**
	 * Sets {@code display} as the {@link Display} associated with this {@code Element} instance.
	 * <p>
	 * If {@code display} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param display the {@code Display} associated with this {@code Element} instance
	 * @throws NullPointerException thrown if, and only if, {@code display} is {@code null}
	 */
	public final void setDisplay(final Display display) {
		this.display.set(Objects.requireNonNull(display, "display == null"));
	}
}
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code PInterface} represents an interface and can be added to a {@link PDocument}.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PInterface implements Documentable {
	private final List<PMethod> methods;
	private String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PInterface} instance.
	 */
	public PInterface() {
		this.methods = new ArrayList<>();
		this.name = "MyInterface";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PInterface} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pInterface.write(new Document());
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
	 * Writes this {@code PInterface} to {@code document}.
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
		final List<PMethod> methods = getMethods();
		
		final String name = getName();
		
		document.linef("interface %s {", name);
		document.indent();
		
		for(int i = 0; i < methods.size(); i++) {
			final PMethod methodA = methods.get(i);
			final PMethod methodB = methods.get(i + 1 < methods.size() ? i + 1 : i);
			
			methodA.write(document);
			
			if(PMethod.isInDifferentGroups(methodA, methodB)) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			} else if(methodA != methodB) {
				document.line();
			}
		}
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link PMethod} instances that are currently added to this {@code PInterface} instance.
	 * 
	 * @return a {@code List} with all {@code PMethod} instances that are currently added to this {@code PInterface} instance
	 */
	public List<PMethod> getMethods() {
		return new ArrayList<>(this.methods);
	}
	
	/**
	 * Returns the name associated with this {@code PInterface} instance.
	 * 
	 * @return the name associated with this {@code PInterface} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Adds {@code method} to this {@code PInterface} instance.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method the {@link PMethod} to add
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public void addMethod(final PMethod method) {
		this.methods.add(Objects.requireNonNull(method, "method == null"));
	}
	
	/**
	 * Removes {@code method} from this {@code PInterface} instance.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method the {@link PMethod} to remove
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public void removeMethod(final PMethod method) {
		this.methods.remove(Objects.requireNonNull(method, "method == null"));
	}
	
	/**
	 * Sets {@code name} as the name for this {@code PInterface} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name for this {@code PInterface} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	/**
	 * Sorts this {@code PInterface} instance.
	 */
	public void sort() {
		Collections.sort(this.methods);
	}
}
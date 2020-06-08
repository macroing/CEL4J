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
package org.macroing.cel4j.php.model;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.Document;

//TODO: Add Javadocs!
public final class PInterface {
	private final List<PMethod> methods;
	private String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PInterface() {
		this.methods = new ArrayList<>();
		this.name = "MyInterface";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public List<PMethod> getMethods() {
		return new ArrayList<>(this.methods);
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public void addMethod(final PMethod method) {
		this.methods.add(Objects.requireNonNull(method, "method == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeMethod(final PMethod method) {
		this.methods.remove(Objects.requireNonNull(method, "method == null"));
	}
	
//	TODO: Add Javadocs!
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
//	TODO: Add Javadocs!
	public void sort() {
		Collections.sort(this.methods);
	}
}
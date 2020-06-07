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
	private final List<PMethod> pMethods;
	private String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PInterface() {
		this.pMethods = new ArrayList<>();
		this.name = "MyInterface";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		final List<PMethod> pMethods = getPMethods();
		
		final String name = getName();
		
		document.linef("interface %s {", name);
		document.indent();
		
		for(int i = 0; i < pMethods.size(); i++) {
			final PMethod pMethodA = pMethods.get(i);
			final PMethod pMethodB = pMethods.get(i + 1 < pMethods.size() ? i + 1 : i);
			
			pMethodA.write(document);
			
			if(PMethod.isInDifferentGroups(pMethodA, pMethodB)) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			} else if(pMethodA != pMethodB) {
				document.line();
			}
		}
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
//	TODO: Add Javadocs!
	public List<PMethod> getPMethods() {
		return new ArrayList<>(this.pMethods);
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public void addPMethod(final PMethod pMethod) {
		this.pMethods.add(Objects.requireNonNull(pMethod, "pMethod == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePMethod(final PMethod pMethod) {
		this.pMethods.remove(Objects.requireNonNull(pMethod, "pMethod == null"));
	}
	
//	TODO: Add Javadocs!
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
//	TODO: Add Javadocs!
	public void sort() {
		Collections.sort(this.pMethods);
	}
}
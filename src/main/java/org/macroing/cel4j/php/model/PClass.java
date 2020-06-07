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
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;

//TODO: Add Javadocs!
public final class PClass {
	private static final int ACCESS_FLAG_ABSTRACT = 0x0400;
	private static final int ACCESS_FLAG_FINAL = 0x0010;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<PConst> pConsts;
	private final List<PField> pFields;
	private final List<PMethod> pMethods;
	private final List<String> implementedInterfaces;
	private PConstructor pConstructor;
	private String extendedClass;
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PClass() {
		this.pConsts = new ArrayList<>();
		this.pFields = new ArrayList<>();
		this.pMethods = new ArrayList<>();
		this.implementedInterfaces = new ArrayList<>();
		this.pConstructor = null;
		this.extendedClass = null;
		this.name = "MyClass";
		this.accessFlags = 0;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		return write(document, false);
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document, final boolean isAligningPConsts) {
		final List<PConst> pConsts = getPConsts();
		final List<PField> pFields = getPFields();
		final List<PMethod> pMethods = getPMethods();
		
		final PConstructor pConstructor = this.pConstructor;
		
		final boolean hasPConsts = pConsts.size() > 0;
		final boolean hasPConstructor = pConstructor != null;
		final boolean hasPFields = pFields.size() > 0;
		final boolean hasPMethods = pMethods.size() > 0;
		
		final String accessModifier = isAbstract() ? "abstract " : isFinal() ? "final " : "";
		final String name = getName();
		final String extendsClause = doCreateExtendsClause();
		final String implementsClause = doCreateImplementsClause();
		
		document.linef("%sclass %s%s%s {", accessModifier, name, extendsClause, implementsClause);
		document.indent();
		
		if(hasPConsts) {
			if(isAligningPConsts) {
				int maximumLength = 0;
				
				for(final PConst pConst : pConsts) {
					maximumLength = Math.max(maximumLength, pConst.getAccessModifiersAsString("", " ").length() + pConst.getName().length());
				}
				
				for(int i = 0; i < pConsts.size(); i++) {
					final PConst pConstA = pConsts.get(i);
					final PConst pConstB = pConsts.get(i + 1 < pConsts.size() ? i + 1 : i);
					
					pConstA.write(document, maximumLength);
					
					if(PConst.isInDifferentGroups(pConstA, pConstB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			} else {
				for(int i = 0; i < pConsts.size(); i++) {
					final PConst pConstA = pConsts.get(i);
					final PConst pConstB = pConsts.get(i + 1 < pConsts.size() ? i + 1 : i);
					
					pConstA.write(document);
					
					if(PConst.isInDifferentGroups(pConstA, pConstB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			}
			
			if(hasPConstructor || hasPFields || hasPMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(hasPFields) {
			for(int i = 0; i < pFields.size(); i++) {
				final PField pFieldA = pFields.get(i);
				final PField pFieldB = pFields.get(i + 1 < pFields.size() ? i + 1 : i);
				
				pFieldA.write(document);
				
				if(PField.isInDifferentGroups(pFieldA, pFieldB)) {
					document.line();
					document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
					document.line();
				}
			}
			
			if(hasPConstructor || hasPMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(pConstructor != null) {
			pConstructor.write(document);
			
			if(hasPMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
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
	public List<PConst> getPConsts() {
		return new ArrayList<>(this.pConsts);
	}
	
//	TODO: Add Javadocs!
	public List<PField> getPFields() {
		return new ArrayList<>(this.pFields);
	}
	
//	TODO: Add Javadocs!
	public List<PMethod> getPMethods() {
		return new ArrayList<>(this.pMethods);
	}
	
//	TODO: Add Javadocs!
	public List<String> getImplementedInterfaces() {
		return new ArrayList<>(this.implementedInterfaces);
	}
	
//	TODO: Add Javadocs!
	public Optional<PConstructor> getPConstructor() {
		return Optional.ofNullable(this.pConstructor);
	}
	
//	TODO: Add Javadocs!
	public Optional<String> getExtendedClass() {
		return Optional.ofNullable(this.extendedClass);
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public boolean isAbstract() {
		return (this.accessFlags & ACCESS_FLAG_ABSTRACT) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isFinal() {
		return (this.accessFlags & ACCESS_FLAG_FINAL) != 0;
	}
	
//	TODO: Add Javadocs!
	public void addImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.add(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
//	TODO: Add Javadocs!
	public void addPConst(final PConst pConst) {
		if(!doContainsPConstByName(Objects.requireNonNull(pConst, "pConst == null"))) {
			this.pConsts.add(pConst);
		}
	}
	
//	TODO: Add Javadocs!
	public void addPField(final PField pField) {
		if(!doContainsPFieldByName(Objects.requireNonNull(pField, "pField == null"))) {
			this.pFields.add(pField);
		}
	}
	
//	TODO: Add Javadocs!
	public void addPMethod(final PMethod pMethod) {
		if(!doContainsPMethodByName(Objects.requireNonNull(pMethod, "pMethod == null"))) {
			this.pMethods.add(pMethod);
		}
	}
	
//	TODO: Add Javadocs!
	public void removeImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.remove(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePConst(final PConst pConst) {
		this.pConsts.remove(Objects.requireNonNull(pConst, "pConst == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePField(final PField pField) {
		this.pFields.remove(Objects.requireNonNull(pField, "pField == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePMethod(final PMethod pMethod) {
		this.pMethods.remove(Objects.requireNonNull(pMethod, "pMethod == null"));
	}
	
//	TODO: Add Javadocs!
	public void setAbstract(final boolean isAbstract) {
		if(isAbstract) {
			this.accessFlags |= ACCESS_FLAG_ABSTRACT;
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
		}
	}
	
//	TODO: Add Javadocs!
	public void setExtendedClass(final String extendedClass) {
		this.extendedClass = extendedClass;
	}
	
//	TODO: Add Javadocs!
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
			this.accessFlags |= ACCESS_FLAG_FINAL;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
		}
	}
	
//	TODO: Add Javadocs!
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
//	TODO: Add Javadocs!
	public void setPConstructor(final PConstructor pConstructor) {
		this.pConstructor = pConstructor;
	}
	
//	TODO: Add Javadocs!
	public void sort() {
		Collections.sort(this.pConsts);
		Collections.sort(this.pFields);
		Collections.sort(this.pMethods);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doCreateExtendsClause() {
		final String extendedClass = this.extendedClass;
		
		if(extendedClass == null) {
			return "";
		}
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("extends");
		stringBuilder.append(" ");
		stringBuilder.append(extendedClass);
		
		return stringBuilder.toString();
	}
	
	private String doCreateImplementsClause() {
		final List<String> implementedInterfaces = getImplementedInterfaces();
		
		if(implementedInterfaces.size() == 0) {
			return "";
		}
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("implements");
		stringBuilder.append(" ");
		stringBuilder.append(implementedInterfaces.stream().collect(Collectors.joining(", ")));
		
		return stringBuilder.toString();
	}
	
	private boolean doContainsPConstByName(final PConst pConst) {
		return this.pConsts.stream().anyMatch(currentPConst -> currentPConst.getName().equals(pConst.getName()));
	}
	
	private boolean doContainsPFieldByName(final PField pField) {
		return this.pFields.stream().anyMatch(currentPField -> currentPField.getName().equals(pField.getName()));
	}
	
	private boolean doContainsPMethodByName(final PMethod pMethod) {
		return this.pMethods.stream().anyMatch(currentPMethod -> currentPMethod.getName().equals(pMethod.getName()));
	}
}
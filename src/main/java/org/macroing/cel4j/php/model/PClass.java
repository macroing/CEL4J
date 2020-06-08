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
	
	private final List<PConst> consts;
	private final List<PField> fields;
	private final List<PMethod> methods;
	private final List<String> implementedInterfaces;
	private PConstructor constructor;
	private String extendedClass;
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PClass() {
		this.consts = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.implementedInterfaces = new ArrayList<>();
		this.constructor = null;
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
	public Document write(final Document document, final boolean isAligningConsts) {
		final List<PConst> consts = getConsts();
		final List<PField> fields = getFields();
		final List<PMethod> methods = getMethods();
		
		final PConstructor constructor = this.constructor;
		
		final boolean hasConsts = consts.size() > 0;
		final boolean hasConstructor = constructor != null;
		final boolean hasFields = fields.size() > 0;
		final boolean hasMethods = methods.size() > 0;
		
		final String accessModifier = isAbstract() ? "abstract " : isFinal() ? "final " : "";
		final String name = getName();
		final String extendsClause = doCreateExtendsClause();
		final String implementsClause = doCreateImplementsClause();
		
		document.linef("%sclass %s%s%s {", accessModifier, name, extendsClause, implementsClause);
		document.indent();
		
		if(hasConsts) {
			if(isAligningConsts) {
				int maximumLength = 0;
				
				for(final PConst currentConst : consts) {
					maximumLength = Math.max(maximumLength, currentConst.getAccessModifiersAsString("", " ").length() + currentConst.getName().length());
				}
				
				for(int i = 0; i < consts.size(); i++) {
					final PConst constA = consts.get(i);
					final PConst constB = consts.get(i + 1 < consts.size() ? i + 1 : i);
					
					constA.write(document, maximumLength);
					
					if(PConst.isInDifferentGroups(constA, constB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			} else {
				for(int i = 0; i < consts.size(); i++) {
					final PConst constA = consts.get(i);
					final PConst constB = consts.get(i + 1 < consts.size() ? i + 1 : i);
					
					constA.write(document);
					
					if(PConst.isInDifferentGroups(constA, constB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			}
			
			if(hasConstructor || hasFields || hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(hasFields) {
			for(int i = 0; i < fields.size(); i++) {
				final PField fieldA = fields.get(i);
				final PField fieldB = fields.get(i + 1 < fields.size() ? i + 1 : i);
				
				fieldA.write(document);
				
				if(PField.isInDifferentGroups(fieldA, fieldB)) {
					document.line();
					document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
					document.line();
				}
			}
			
			if(hasConstructor || hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(constructor != null) {
			constructor.write(document);
			
			if(hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
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
	public List<PConst> getConsts() {
		return new ArrayList<>(this.consts);
	}
	
//	TODO: Add Javadocs!
	public List<PField> getFields() {
		return new ArrayList<>(this.fields);
	}
	
//	TODO: Add Javadocs!
	public List<PMethod> getMethods() {
		return new ArrayList<>(this.methods);
	}
	
//	TODO: Add Javadocs!
	public List<String> getImplementedInterfaces() {
		return new ArrayList<>(this.implementedInterfaces);
	}
	
//	TODO: Add Javadocs!
	public Optional<PConstructor> getConstructor() {
		return Optional.ofNullable(this.constructor);
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
	public void addConst(final PConst pConst) {
		if(!doContainsConstByName(Objects.requireNonNull(pConst, "pConst == null"))) {
			this.consts.add(pConst);
		}
	}
	
//	TODO: Add Javadocs!
	public void addField(final PField field) {
		if(!doContainsFieldByName(Objects.requireNonNull(field, "field == null"))) {
			this.fields.add(field);
		}
	}
	
//	TODO: Add Javadocs!
	public void addImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.add(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
//	TODO: Add Javadocs!
	public void addMethod(final PMethod method) {
		if(!doContainsMethodByName(Objects.requireNonNull(method, "method == null"))) {
			this.methods.add(method);
		}
	}
	
//	TODO: Add Javadocs!
	public void removeConst(final PConst pConst) {
		this.consts.remove(Objects.requireNonNull(pConst, "pConst == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeField(final PField field) {
		this.fields.remove(Objects.requireNonNull(field, "field == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.remove(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeMethod(final PMethod method) {
		this.methods.remove(Objects.requireNonNull(method, "method == null"));
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
	public void setConstructor(final PConstructor constructor) {
		this.constructor = constructor;
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
	public void sort() {
		Collections.sort(this.consts);
		Collections.sort(this.fields);
		Collections.sort(this.methods);
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
	
	private boolean doContainsConstByName(final PConst pConst) {
		return this.consts.stream().anyMatch(currentPConst -> currentPConst.getName().equals(pConst.getName()));
	}
	
	private boolean doContainsFieldByName(final PField field) {
		return this.fields.stream().anyMatch(currentField -> currentField.getName().equals(field.getName()));
	}
	
	private boolean doContainsMethodByName(final PMethod method) {
		return this.methods.stream().anyMatch(currentMethod -> currentMethod.getName().equals(method.getName()));
	}
}
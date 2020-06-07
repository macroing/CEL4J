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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;

//TODO: Add Javadocs!
public final class PConstructor {
	private final List<PParameterArgument> pParameterArguments;
	private final PBlock pBlock;
	private boolean isFinal;
	private boolean isPrivate;
	private boolean isProtected;
	private boolean isPublic;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PConstructor() {
		this.pParameterArguments = new ArrayList<>();
		this.pBlock = new PBlock();
		this.isFinal = false;
		this.isPrivate = false;
		this.isProtected = false;
		this.isPublic = true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		final PBlock pBlock = this.pBlock;
		
		final String accessModifier = isPrivate() ? "private" : isProtected() ? "protected" : isPublic() ? "public" : "public";
		final String finalModifier = isFinal() ? " final" : "";
		final String parameterArguments = this.pParameterArguments.stream().map(pParameterArgument -> pParameterArgument.getSourceCode()).collect(Collectors.joining(", "));
		
		document.linef("%s%s function __construct(%s) {", accessModifier, finalModifier, parameterArguments);
		document.indent();
		
		pBlock.write(document);
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
//	TODO: Add Javadocs!
	public List<PParameterArgument> getPParameterArguments() {
		return new ArrayList<>(this.pParameterArguments);
	}
	
//	TODO: Add Javadocs!
	public PBlock getPBlock() {
		return this.pBlock;
	}
	
//	TODO: Add Javadocs!
	public boolean isDefaultCallable() {
		boolean isDefaultCallable = true;
		
		for(final PParameterArgument pParameterArgument : this.pParameterArguments) {
			if(!pParameterArgument.hasPValue()) {
				isDefaultCallable = false;
			}
		}
		
		return isDefaultCallable;
	}
	
//	TODO: Add Javadocs!
	public boolean isFinal() {
		return this.isFinal;
	}
	
//	TODO: Add Javadocs!
	public boolean isPrivate() {
		return this.isPrivate;
	}
	
//	TODO: Add Javadocs!
	public boolean isProtected() {
		return this.isProtected;
	}
	
//	TODO: Add Javadocs!
	public boolean isPublic() {
		return this.isPublic;
	}
	
//	TODO: Add Javadocs!
	public void addPParameterArgument(final PParameterArgument pParameterArgument) {
		this.pParameterArguments.add(Objects.requireNonNull(pParameterArgument, "pParameterArgument == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePParameterArgument(final PParameterArgument pParameterArgument) {
		this.pParameterArguments.remove(Objects.requireNonNull(pParameterArgument, "pParameterArgument == null"));
	}
	
//	TODO: Add Javadocs!
	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}
	
//	TODO: Add Javadocs!
	public void setPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
		this.isProtected = isPrivate ? false : this.isProtected;
		this.isPublic = isPrivate ? false : this.isPublic;
	}
	
//	TODO: Add Javadocs!
	public void setProtected(final boolean isProtected) {
		this.isPrivate = isProtected ? false : this.isPrivate;
		this.isProtected = isProtected;
		this.isPublic = isProtected ? false : this.isPublic;
	}
	
//	TODO: Add Javadocs!
	public void setPublic(final boolean isPublic) {
		this.isPrivate = isPublic ? false : this.isPrivate;
		this.isProtected = isPublic ? false : this.isProtected;
		this.isPublic = isPublic;
	}
}
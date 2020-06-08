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
public final class PConst implements Comparable<PConst> {
	private static final int ACCESS_FLAG_PRIVATE = 0x0002;
	private static final int ACCESS_FLAG_PROTECTED = 0x0004;
	private static final int ACCESS_FLAG_PUBLIC = 0x0001;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PValue value;
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PConst(final String name, final PValue value) {
		this.value = Objects.requireNonNull(value, "value == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.accessFlags = ACCESS_FLAG_PUBLIC;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		return write(document, this.name.length());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document, final int maximumLength) {
		final String accessModifiers = getAccessModifiersAsString("", " ");
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = accessModifiers.length() + this.name.length(); i < maximumLength; i++) {
			stringBuilder.append(" ");
		}
		
		document.linef("%sconst %s%s = %s;", accessModifiers, this.name, stringBuilder.toString(), this.value.getSourceCode());
		
		return document;
	}
	
//	TODO: Add Javadocs!
	public List<String> getAccessModifiers() {
		final List<String> accessModifiers = new ArrayList<>();
		
		if(isPrivate()) {
			accessModifiers.add("private");
		}
		
		if(isProtected()) {
			accessModifiers.add("protected");
		}
		
		if(isPublic()) {
			accessModifiers.add("public");
		}
		
		return accessModifiers;
	}
	
//	TODO: Add Javadocs!
	public String getAccessModifiersAsString() {
		return getAccessModifiersAsString("", "");
	}
	
//	TODO: Add Javadocs!
	public String getAccessModifiersAsString(final String prefix, final String suffix) {
		Objects.requireNonNull(prefix, "prefix == null");
		Objects.requireNonNull(suffix, "suffix == null");
		
		final List<String> accessModifiers = getAccessModifiers();
		
		if(accessModifiers.isEmpty()) {
			return "";
		}
		
		return accessModifiers.stream().collect(Collectors.joining(" ", prefix, suffix));
	}
	
//	TODO: Add Javadocs!
	public PValue getValue() {
		return this.value;
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PConst)) {
			return false;
		} else if(!Objects.equals(this.value, PConst.class.cast(object).value)) {
			return false;
		} else if(!Objects.equals(this.name, PConst.class.cast(object).name)) {
			return false;
		} else if(this.accessFlags != PConst.class.cast(object).accessFlags) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public boolean isPrivate() {
		return (this.accessFlags & ACCESS_FLAG_PRIVATE) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isProtected() {
		return (this.accessFlags & ACCESS_FLAG_PROTECTED) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isPublic() {
		return (this.accessFlags & ACCESS_FLAG_PUBLIC) != 0;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int compareTo(final PConst pConst) {
		final PConst constThis = this;
		final PConst constThat = pConst;
		
		final boolean isPublicThis = constThis.isPublic();
		final boolean isPublicThat = constThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = constThis.isProtected();
		final boolean isProtectedThat = constThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = constThis.isPrivate();
		final boolean isPrivateThat = constThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		final String nameThis = constThis.name;
		final String nameThat = constThat.name;
		
		return nameThis.compareTo(nameThat);
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.value, this.name, Integer.valueOf(this.accessFlags));
	}
	
//	TODO: Add Javadocs!
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
//	TODO: Add Javadocs!
	public void setPrivate(final boolean isPrivate) {
		if(isPrivate) {
			this.accessFlags |= ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
		}
	}
	
//	TODO: Add Javadocs!
	public void setProtected(final boolean isProtected) {
		if(isProtected) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags |= ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
		}
	}
	
//	TODO: Add Javadocs!
	public void setPublic(final boolean isPublic) {
		if(isPublic) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags |= ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		}
	}
	
//	TODO: Add Javadocs!
	public void setValue(final PValue value) {
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static boolean isInDifferentGroups(final PConst constA, final PConst constB) {
		if(constA.isPublic() != constB.isPublic()) {
			return true;
		}
		
		if(constA.isProtected() != constB.isProtected()) {
			return true;
		}
		
		if(constA.isPrivate() != constB.isPrivate()) {
			return true;
		}
		
		return false;
	}
}
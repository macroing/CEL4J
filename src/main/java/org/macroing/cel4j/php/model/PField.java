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
public final class PField implements Comparable<PField> {
	private static final int ACCESS_FLAG_PRIVATE = 0x0002;
	private static final int ACCESS_FLAG_PROTECTED = 0x0004;
	private static final int ACCESS_FLAG_PUBLIC = 0x0001;
	private static final int ACCESS_FLAG_STATIC = 0x0008;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PField() {
		this.name = "name";
		this.accessFlags = ACCESS_FLAG_PRIVATE;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		final String accessModifiers = getAccessModifiersAsString("", " ");
		final String name = getName();
		
		document.linef("%s$%s;", accessModifiers, name);
		
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
		
		if(isStatic()) {
			accessModifiers.add("static");
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
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PField)) {
			return false;
		} else if(!Objects.equals(this.name, PField.class.cast(object).name)) {
			return false;
		} else if(this.accessFlags != PField.class.cast(object).accessFlags) {
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
	public boolean isStatic() {
		return (this.accessFlags & ACCESS_FLAG_STATIC) != 0;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int compareTo(final PField field) {
		final PField fieldThis = this;
		final PField fieldThat = field;
		
		final boolean isStaticThis = fieldThis.isStatic();
		final boolean isStaticThat = fieldThat.isStatic();
		
		if(isStaticThis != isStaticThat) {
			return isStaticThis ? -1 : 1;
		}
		
		final boolean isPublicThis = fieldThis.isPublic();
		final boolean isPublicThat = fieldThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = fieldThis.isProtected();
		final boolean isProtectedThat = fieldThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = fieldThis.isPrivate();
		final boolean isPrivateThat = fieldThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		return this.name.compareTo(field.name);
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.name, Integer.valueOf(this.accessFlags));
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
	public void setStatic(final boolean isStatic) {
		if(isStatic) {
			this.accessFlags |= ACCESS_FLAG_STATIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_STATIC;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static boolean isInDifferentGroups(final PField fieldA, final PField fieldB) {
		if(fieldA.isStatic() != fieldB.isStatic()) {
			return true;
		}
		
		if(fieldA.isPublic() != fieldB.isPublic()) {
			return true;
		}
		
		if(fieldA.isProtected() != fieldB.isProtected()) {
			return true;
		}
		
		if(fieldA.isPrivate() != fieldB.isPrivate()) {
			return true;
		}
		
		return false;
	}
}
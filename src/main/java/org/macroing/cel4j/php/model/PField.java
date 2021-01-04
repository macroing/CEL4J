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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code PField} represents a field and can be added to a {@link PClass}.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PField implements Comparable<PField>, Documentable {
	private static final int ACCESS_FLAG_PRIVATE = 0x0002;
	private static final int ACCESS_FLAG_PROTECTED = 0x0004;
	private static final int ACCESS_FLAG_PUBLIC = 0x0001;
	private static final int ACCESS_FLAG_STATIC = 0x0008;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PField} instance.
	 */
	public PField() {
		this.name = "name";
		this.accessFlags = ACCESS_FLAG_PRIVATE;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PField} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pField.write(new Document());
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
	 * Writes this {@code PField} to {@code document}.
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
		final String accessModifiers = getAccessModifiersAsString("", " ");
		final String name = getName();
		
		document.linef("%s$%s;", accessModifiers, name);
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all access modifiers that are currently associated with this {@code PField} instance.
	 * 
	 * @return a {@code List} with all access modifiers that are currently associated with this {@code PField} instance
	 */
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
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PField} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pField.getAccessModifiersAsString("", "");
	 * }
	 * </pre>
	 * 
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PField} instance
	 */
	public String getAccessModifiersAsString() {
		return getAccessModifiersAsString("", "");
	}
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PField} instance.
	 * <p>
	 * If either {@code prefix} or {@code suffix} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param prefix a {@code String} to be used at the beginning of the access modifiers, if there are any
	 * @param suffix a {@code String} to be used at the end of the access modifiers, if there are any
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PField} instance
	 * @throws NullPointerException thrown if, and only if, either {@code prefix} or {@code suffix} are {@code null}
	 */
	public String getAccessModifiersAsString(final String prefix, final String suffix) {
		Objects.requireNonNull(prefix, "prefix == null");
		Objects.requireNonNull(suffix, "suffix == null");
		
		final List<String> accessModifiers = getAccessModifiers();
		
		if(accessModifiers.isEmpty()) {
			return "";
		}
		
		return accessModifiers.stream().collect(Collectors.joining(" ", prefix, suffix));
	}
	
	/**
	 * Returns the name associated with this {@code PField} instance.
	 * 
	 * @return the name associated with this {@code PField} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Compares {@code object} to this {@code PField} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PField}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PField} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PField}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code PField} instance represents a private field, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PField} instance represents a private field, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.accessFlags & ACCESS_FLAG_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PField} instance represents a protected field, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PField} instance represents a protected field, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.accessFlags & ACCESS_FLAG_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PField} instance represents a public field, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PField} instance represents a public field, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACCESS_FLAG_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PField} instance represents a static field, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PField} instance represents a static field, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (this.accessFlags & ACCESS_FLAG_STATIC) != 0;
	}
	
	/**
	 * Compares this {@code PField} instance to {@code field}.
	 * <p>
	 * Returns a comparison value.
	 * <p>
	 * If {@code field} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param field the {@code PField} to compare this {@code PField} instance to
	 * @return a comparison value
	 * @throws NullPointerException thrown if, and only if, {@code field} is {@code null}
	 */
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
	
	/**
	 * Returns a hash code for this {@code PField} instance.
	 * 
	 * @return a hash code for this {@code PField} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name, Integer.valueOf(this.accessFlags));
	}
	
	/**
	 * Sets {@code name} as the name for this {@code PField} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name for this {@code PField} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	/**
	 * Adds or removes the private access modifier for this {@code PField} instance.
	 * 
	 * @param isPrivate {@code true} if, and only if, this {@code PField} is private, {@code false} otherwise
	 */
	public void setPrivate(final boolean isPrivate) {
		if(isPrivate) {
			this.accessFlags |= ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
		}
	}
	
	/**
	 * Adds or removes the protected access modifier for this {@code PField} instance.
	 * 
	 * @param isProtected {@code true} if, and only if, this {@code PField} is protected, {@code false} otherwise
	 */
	public void setProtected(final boolean isProtected) {
		if(isProtected) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags |= ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
		}
	}
	
	/**
	 * Adds or removes the public access modifier for this {@code PField} instance.
	 * 
	 * @param isPublic {@code true} if, and only if, this {@code PField} is public, {@code false} otherwise
	 */
	public void setPublic(final boolean isPublic) {
		if(isPublic) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags |= ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		}
	}
	
	/**
	 * Adds or removes the static access modifier for this {@code PField} instance.
	 * 
	 * @param isStatic {@code true} if, and only if, this {@code PField} is static, {@code false} otherwise
	 */
	public void setStatic(final boolean isStatic) {
		if(isStatic) {
			this.accessFlags |= ACCESS_FLAG_STATIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_STATIC;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if {@code fieldA} is in a different group than {@code fieldB}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldA} is in a different group than {@code fieldB}, {@code false} otherwise.
	 * <p>
	 * If either {@code fieldA} or {@code fieldB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldA a {@link PField} instance
	 * @param fieldB a {@code PField} instance
	 * @return {@code true} if, and only if, {@code fieldA} is in a different group than {@code fieldB}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code fieldA} or {@code fieldB} are {@code null}
	 */
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
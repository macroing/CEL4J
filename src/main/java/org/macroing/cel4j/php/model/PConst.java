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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;

/**
 * A {@code PConst} represents a const and can be added to a {@link PClass}.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PConst implements Comparable<PConst> {
	private static final int ACCESS_FLAG_PRIVATE = 0x0002;
	private static final int ACCESS_FLAG_PROTECTED = 0x0004;
	private static final int ACCESS_FLAG_PUBLIC = 0x0001;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PValue value;
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PConst} instance.
	 * <p>
	 * If either {@code name} or {@code value} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name associated with this {@code PConst} instance
	 * @param value the value associated with this {@code PConst} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code value} are {@code null}
	 */
	public PConst(final String name, final PValue value) {
		this.value = Objects.requireNonNull(value, "value == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.accessFlags = ACCESS_FLAG_PUBLIC;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PConst} instance to a {@link Document}.
	 * <p>
	 * Returns the {@code Document} that was written to.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pConst.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document} that was written to
	 */
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code PConst} instance to a {@link Document}.
	 * <p>
	 * Returns the {@code Document} that was written to.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pConst.write(document, pConst.getName().length());
	 * }
	 * </pre>
	 * 
	 * @param document the {@code Document} to write to
	 * @return the {@code Document} that was written to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document) {
		return write(document, this.name.length());
	}
	
	/**
	 * Writes this {@code PConst} instance to a {@link Document}.
	 * <p>
	 * Returns the {@code Document} that was written to.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@code Document} to write to
	 * @param maximumNameLength the maximum name length, used when aligning all {@code JConst} instances
	 * @return the {@code Document} that was written to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document, final int maximumNameLength) {
		final String accessModifiers = getAccessModifiersAsString("", " ");
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = accessModifiers.length() + this.name.length(); i < maximumNameLength; i++) {
			stringBuilder.append(" ");
		}
		
		document.linef("%sconst %s%s = %s;", accessModifiers, this.name, stringBuilder.toString(), this.value.getSourceCode());
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all access modifiers that are currently associated with this {@code PConst} instance.
	 * 
	 * @return a {@code List} with all access modifiers that are currently associated with this {@code PConst} instance
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
		
		return accessModifiers;
	}
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PConst} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pConst.getAccessModifiersAsString("", "");
	 * }
	 * </pre>
	 * 
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PConst} instance
	 */
	public String getAccessModifiersAsString() {
		return getAccessModifiersAsString("", "");
	}
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PConst} instance.
	 * <p>
	 * If either {@code prefix} or {@code suffix} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param prefix a {@code String} to be used at the beginning of the access modifiers, if there are any
	 * @param suffix a {@code String} to be used at the end of the access modifiers, if there are any
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PConst} instance
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
	 * Returns the value associated with this {@code PConst} instance.
	 * 
	 * @return the value associated with this {@code PConst} instance
	 */
	public PValue getValue() {
		return this.value;
	}
	
	/**
	 * Returns the name associated with this {@code PConst} instance.
	 * 
	 * @return the name associated with this {@code PConst} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Compares {@code object} to this {@code PConst} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PConst}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PConst} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PConst}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConst} instance represents a private const, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConst} instance represents a private const, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.accessFlags & ACCESS_FLAG_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConst} instance represents a protected const, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConst} instance represents a protected const, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.accessFlags & ACCESS_FLAG_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConst} instance represents a public const, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConst} instance represents a public const, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACCESS_FLAG_PUBLIC) != 0;
	}
	
	/**
	 * Compares this {@code PConst} instance to {@code pConst}.
	 * <p>
	 * Returns a comparison value.
	 * <p>
	 * If {@code pConst} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pConst the {@code PConst} to compare this {@code PConst} instance to
	 * @return a comparison value
	 * @throws NullPointerException thrown if, and only if, {@code pConst} is {@code null}
	 */
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
	
	/**
	 * Returns a hash code for this {@code PConst} instance.
	 * 
	 * @return a hash code for this {@code PConst} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.value, this.name, Integer.valueOf(this.accessFlags));
	}
	
	/**
	 * Sets {@code name} as the name for this {@code PConst} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name for this {@code PConst} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	/**
	 * Adds or removes the private access modifier for this {@code PConst} instance.
	 * 
	 * @param isPrivate {@code true} if, and only if, this {@code PConst} is private, {@code false} otherwise
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
	 * Adds or removes the protected access modifier for this {@code PConst} instance.
	 * 
	 * @param isProtected {@code true} if, and only if, this {@code PConst} is protected, {@code false} otherwise
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
	 * Adds or removes the public access modifier for this {@code PConst} instance.
	 * 
	 * @param isPublic {@code true} if, and only if, this {@code PConst} is public, {@code false} otherwise
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
	 * Sets {@code value} as the value associated with this {@code PConst} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the value associated with this {@code PConst} instance
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void setValue(final PValue value) {
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if {@code constA} is in a different group than {@code constB}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code constA} is in a different group than {@code constB}, {@code false} otherwise.
	 * <p>
	 * If either {@code constA} or {@code constB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constA a {@link PConst} instance
	 * @param constB a {@code PConst} instance
	 * @return {@code true} if, and only if, {@code constA} is in a different group than {@code constB}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code constA} or {@code constB} are {@code null}
	 */
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
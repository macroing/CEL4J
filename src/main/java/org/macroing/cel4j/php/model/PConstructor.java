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
 * A {@code PConstructor} represents a constructor and can be added to a {@link PClass}.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PConstructor implements Documentable {
	private final List<PParameterArgument> parameterArguments;
	private final PBlock block;
	private boolean isFinal;
	private boolean isPrivate;
	private boolean isProtected;
	private boolean isPublic;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PConstructor} instance.
	 */
	public PConstructor() {
		this.parameterArguments = new ArrayList<>();
		this.block = new PBlock();
		this.isFinal = false;
		this.isPrivate = false;
		this.isProtected = false;
		this.isPublic = true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PConstructor} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pConstructor.write(new Document());
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
	 * Writes this {@code PConstructor} to {@code document}.
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
		final PBlock block = this.block;
		
		final String accessModifier = isPrivate() ? "private" : isProtected() ? "protected" : isPublic() ? "public" : "public";
		final String finalModifier = isFinal() ? " final" : "";
		final String parameterArguments = this.parameterArguments.stream().map(parameterArgument -> parameterArgument.getSourceCode()).collect(Collectors.joining(", "));
		
		document.linef("%s%s function __construct(%s) {", accessModifier, finalModifier, parameterArguments);
		document.indent();
		
		block.write(document);
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link PParameterArgument} instances that are currently added to this {@code PConstructor} instance.
	 * 
	 * @return a {@code List} with all {@code PParameterArgument} instances that are currently added to this {@code PConstructor} instance
	 */
	public List<PParameterArgument> getParameterArguments() {
		return new ArrayList<>(this.parameterArguments);
	}
	
	/**
	 * Returns the {@link PBlock} instance that is associated with this {@code PConstructor} instance.
	 * 
	 * @return the {@code PBlock} instance that is associated with this {@code PConstructor} instance
	 */
	public PBlock getBlock() {
		return this.block;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConstructor} instance can be called without any parameter arguments specified, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConstructor} instance can be called without any parameter arguments specified, {@code false} otherwise
	 */
	public boolean isDefaultCallable() {
		boolean isDefaultCallable = true;
		
		for(final PParameterArgument parameterArgument : this.parameterArguments) {
			if(!parameterArgument.hasValue()) {
				isDefaultCallable = false;
			}
		}
		
		return isDefaultCallable;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConstructor} instance represents a final constructor, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConstructor} instance represents a final constructor, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConstructor} instance represents a private constructor, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConstructor} instance represents a private constructor, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return this.isPrivate;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConstructor} instance represents a protected constructor, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConstructor} instance represents a protected constructor, {@code false} otherwise
	 */
	public boolean isProtected() {
		return this.isProtected;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PConstructor} instance represents a public constructor, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PConstructor} instance represents a public constructor, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.isPublic;
	}
	
	/**
	 * Adds {@code parameterArgument} to this {@code PConstructor} instance.
	 * <p>
	 * If {@code parameterArgument} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterArgument the {@link PParameterArgument} to add
	 * @throws NullPointerException thrown if, and only if, {@code parameterArgument} is {@code null}
	 */
	public void addParameterArgument(final PParameterArgument parameterArgument) {
		this.parameterArguments.add(Objects.requireNonNull(parameterArgument, "parameterArgument == null"));
	}
	
	/**
	 * Removes {@code parameterArgument} from this {@code PConstructor} instance.
	 * <p>
	 * If {@code parameterArgument} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterArgument the {@link PParameterArgument} to remove
	 * @throws NullPointerException thrown if, and only if, {@code parameterArgument} is {@code null}
	 */
	public void removeParameterArgument(final PParameterArgument parameterArgument) {
		this.parameterArguments.remove(Objects.requireNonNull(parameterArgument, "parameterArgument == null"));
	}
	
	/**
	 * Adds or removes the final access modifier for this {@code PConstructor} instance.
	 * 
	 * @param isFinal {@code true} if, and only if, this {@code PConstructor} is final, {@code false} otherwise
	 */
	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	/**
	 * Adds or removes the private access modifier for this {@code PConstructor} instance.
	 * 
	 * @param isPrivate {@code true} if, and only if, this {@code PConstructor} is private, {@code false} otherwise
	 */
	public void setPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
		this.isProtected = isPrivate ? false : this.isProtected;
		this.isPublic = isPrivate ? false : this.isPublic;
	}
	
	/**
	 * Adds or removes the protected access modifier for this {@code PConstructor} instance.
	 * 
	 * @param isProtected {@code true} if, and only if, this {@code PConstructor} is protected, {@code false} otherwise
	 */
	public void setProtected(final boolean isProtected) {
		this.isPrivate = isProtected ? false : this.isPrivate;
		this.isProtected = isProtected;
		this.isPublic = isProtected ? false : this.isPublic;
	}
	
	/**
	 * Adds or removes the public access modifier for this {@code PConstructor} instance.
	 * 
	 * @param isPublic {@code true} if, and only if, this {@code PConstructor} is public, {@code false} otherwise
	 */
	public void setPublic(final boolean isPublic) {
		this.isPrivate = isPublic ? false : this.isPrivate;
		this.isProtected = isPublic ? false : this.isProtected;
		this.isPublic = isPublic;
	}
}
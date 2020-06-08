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

import java.util.Objects;
import java.util.Optional;

/**
 * A {@code PParameterArgument} represents a parameter argument and can be added to a {@link PConstructor} or a {@link PMethod}.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PParameterArgument {
	private final PType type;
	private final PValue value;
	private final String name;
	private final boolean isNullable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PParameterArgument} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PParameterArgument("name");
	 * }
	 * </pre>
	 */
	public PParameterArgument() {
		this("name");
	}
	
	/**
	 * Constructs a new {@code PParameterArgument} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PParameterArgument(name, null);
	 * }
	 * </pre>
	 * 
	 * @param name the name of the parameter argument
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public PParameterArgument(final String name) {
		this(name, null);
	}
	
	/**
	 * Constructs a new {@code PParameterArgument} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PParameterArgument(name, type, null);
	 * }
	 * </pre>
	 * 
	 * @param name the name of the parameter argument
	 * @param type the type of the parameter argument, which may be {@code null}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public PParameterArgument(final String name, final PType type) {
		this(name, type, null);
	}
	
	/**
	 * Constructs a new {@code PParameterArgument} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PParameterArgument(name, type, value, false);
	 * }
	 * </pre>
	 * 
	 * @param name the name of the parameter argument
	 * @param type the type of the parameter argument, which may be {@code null}
	 * @param value the default value of the parameter argument, which may be {@code null}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public PParameterArgument(final String name, final PType type, final PValue value) {
		this(name, type, value, false);
	}
	
	/**
	 * Constructs a new {@code PParameterArgument} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name of the parameter argument
	 * @param type the type of the parameter argument, which may be {@code null}
	 * @param value the default value of the parameter argument, which may be {@code null}
	 * @param isNullable {@code true} if, and only if, the parameter argument may be {@code null} in the PHP source code, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public PParameterArgument(final String name, final PType type, final PValue value, final boolean isNullable) {
		this.type = type;
		this.value = value;
		this.name = Objects.requireNonNull(name, "name == null");
		this.isNullable = isNullable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@link PType} with the optional type associated with this {@code PParameterArgument}.
	 * 
	 * @return an {@code Optional} of {@code PType} with the optional type associated with this {@code PParameterArgument}
	 */
	public Optional<PType> getType() {
		return Optional.ofNullable(this.type);
	}
	
	/**
	 * Returns an {@code Optional} of {@link PValue} with the optional default value associated with this {@code PParameterArgument}.
	 * 
	 * @return an {@code Optional} of {@code PValue} with the optional default value associated with this {@code PParameterArgument}
	 */
	public Optional<PValue> getValue() {
		return Optional.ofNullable(this.value);
	}
	
	/**
	 * Returns the name associated with this {@code PParameterArgument}.
	 * 
	 * @return the name associated with this {@code PParameterArgument}
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a {@code String} with PHP source code.
	 * 
	 * @return a {@code String} with PHP source code
	 */
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(hasType() && isNullable() ? "?" : "");
		stringBuilder.append(hasType() ? this.type.getName() : "");
		stringBuilder.append(hasType() ? " " : "");
		stringBuilder.append("$" + getName());
		
		if(hasValue()) {
			stringBuilder.append(" = ");
			stringBuilder.append(this.value.getSourceCode());
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Compares {@code object} to this {@code PParameterArgument} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PParameterArgument}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PParameterArgument} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PParameterArgument}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PParameterArgument)) {
			return false;
		} else if(!Objects.equals(this.type, PParameterArgument.class.cast(object).type)) {
			return false;
		} else if(!Objects.equals(this.value, PParameterArgument.class.cast(object).value)) {
			return false;
		} else if(!Objects.equals(this.name, PParameterArgument.class.cast(object).name)) {
			return false;
		} else if(this.isNullable != PParameterArgument.class.cast(object).isNullable) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PParameterArgument} instance is associated with a type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PParameterArgument} instance is associated with a type, {@code false} otherwise
	 */
	public boolean hasType() {
		return this.type != null;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PParameterArgument} instance is associated with a default value, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PParameterArgument} instance is associated with a default value, {@code false} otherwise
	 */
	public boolean hasValue() {
		return this.value != null;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PParameterArgument} instance allows {@code null} in the PHP source code, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PParameterArgument} instance allows {@code null} in the PHP source code, {@code false} otherwise
	 */
	public boolean isNullable() {
		return this.isNullable;
	}
	
	/**
	 * Returns a hash code for this {@code PParameterArgument} instance.
	 * 
	 * @return a hash code for this {@code PParameterArgument} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.value, this.name, Boolean.valueOf(this.isNullable));
	}
}
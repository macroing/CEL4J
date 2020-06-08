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
package org.macroing.cel4j.php.generator;

import java.util.Objects;

import org.macroing.cel4j.php.model.PType;

/**
 * A {@code Property} represents a property of a {@link Model}.
 * <p>
 * The definitions used by this class are its code, name, name in plural, property builder and type.
 * <p>
 * The code is a {@code String} used in a const of the PHP class. This const is used in the {@code parseArray(array)}, {@code parseJSON(string)} and {@code toArray(bool)} methods that are generated.
 * <p>
 * The name is a {@code String} used to generate the name of the field, get and set methods and more. It is defined in a singular sense.
 * <p>
 * The name in plural is a {@code String} used to generate the name of the field, get and set methods and more. It is defined in a plural sense.
 * <p>
 * The property builder is a {@link PropertyBuilder} used to generate the consts, fields, methods and more for a specific {@code Property}, in the PHP class model.
 * <p>
 * The type is a {@link PType} used to define the type of the {@code Property}. It is used when generating the type for the fields, methods and more.
 * <p>
 * It is likely that the code and the name are equal, but that is not always the case. If the {@code Model} is built from scratch, where no JSON markup text is used to guide its implementation, the code and the name should preferably be equal. If, on
 * the other hand, the {@code Model} is built as a PHP class model for existing JSON markup text, they may be distinct.
 * <p>
 * It is likely that the name in plural is not used. Therefore, it may be an empty {@code String}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Property {
	private final PType type;
	private final PropertyBuilder propertyBuilder;
	private final String code;
	private final String name;
	private final String nameInPlural;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Property} instance.
	 * <p>
	 * If either {@code type}, {@code propertyBuilder} or {@code code} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code propertyBuilder.isTypeSupported(type)} returns {@code false}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Property(type, propertyBuilder, code, code);
	 * }
	 * </pre>
	 * 
	 * @param type the type associated with the {@code Property}
	 * @param propertyBuilder the property builder associated with the {@code Property}
	 * @param code the code associated with the {@code Property}
	 * @throws IllegalArgumentException thrown if, and only if, {@code propertyBuilder.isTypeSupported(type)} returns {@code false}
	 * @throws NullPointerException thrown if, and only if, either {@code type}, {@code propertyBuilder} or {@code code} are {@code null}
	 */
	public Property(final PType type, final PropertyBuilder propertyBuilder, final String code) {
		this(type, propertyBuilder, code, code);
	}
	
	/**
	 * Constructs a new {@code Property} instance.
	 * <p>
	 * If either {@code type}, {@code propertyBuilder}, {@code code} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code propertyBuilder.isTypeSupported(type)} returns {@code false}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Property(type, propertyBuilder, code, name, "");
	 * }
	 * </pre>
	 * 
	 * @param type the type associated with the {@code Property}
	 * @param propertyBuilder the property builder associated with the {@code Property}
	 * @param code the code associated with the {@code Property}
	 * @param name the name associated with the {@code Property}
	 * @throws IllegalArgumentException thrown if, and only if, {@code propertyBuilder.isTypeSupported(type)} returns {@code false}
	 * @throws NullPointerException thrown if, and only if, either {@code type}, {@code propertyBuilder}, {@code code} or {@code name} are {@code null}
	 */
	public Property(final PType type, final PropertyBuilder propertyBuilder, final String code, final String name) {
		this(type, propertyBuilder, code, name, "");
	}
	
	/**
	 * Constructs a new {@code Property} instance.
	 * <p>
	 * If either {@code type}, {@code propertyBuilder}, {@code code}, {@code name} or {@code nameInPlural} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code propertyBuilder.isTypeSupported(type)} returns {@code false}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param type the type associated with the {@code Property}
	 * @param propertyBuilder the property builder associated with the {@code Property}
	 * @param code the code associated with the {@code Property}
	 * @param name the name associated with the {@code Property}
	 * @param nameInPlural the name in plural associated with the {@code Property}
	 * @throws IllegalArgumentException thrown if, and only if, {@code propertyBuilder.isTypeSupported(type)} returns {@code false}
	 * @throws NullPointerException thrown if, and only if, either {@code type}, {@code propertyBuilder}, {@code code}, {@code name} or {@code nameInPlural} are {@code null}
	 */
	public Property(final PType type, final PropertyBuilder propertyBuilder, final String code, final String name, final String nameInPlural) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.propertyBuilder = doRequireValidPropertyBuilder(type, Objects.requireNonNull(propertyBuilder, "propertyBuilder == null"));
		this.code = Objects.requireNonNull(code, "code == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.nameInPlural = Objects.requireNonNull(nameInPlural, "nameInPlural == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the associated type.
	 * 
	 * @return the associated type
	 */
	public PType getType() {
		return this.type;
	}
	
	/**
	 * Returns the associated property builder.
	 * 
	 * @return the associated property builder
	 */
	public PropertyBuilder getPropertyBuilder() {
		return this.propertyBuilder;
	}
	
	/**
	 * Returns the associated code.
	 * 
	 * @return the associated code
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Returns the associated name.
	 * 
	 * @return the associated name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the associated name in plural.
	 * 
	 * @return the associated name in plural
	 */
	public String getNameInPlural() {
		return this.nameInPlural;
	}
	
	/**
	 * Compares {@code object} to this {@code Property} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Property}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Property} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Property}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Property)) {
			return false;
		} else if(!Objects.equals(this.type, Property.class.cast(object).type)) {
			return false;
		} else if(!Objects.equals(this.propertyBuilder, Property.class.cast(object).propertyBuilder)) {
			return false;
		} else if(!Objects.equals(this.code, Property.class.cast(object).code)) {
			return false;
		} else if(!Objects.equals(this.name, Property.class.cast(object).name)) {
			return false;
		} else if(!Objects.equals(this.nameInPlural, Property.class.cast(object).nameInPlural)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Property} instance.
	 * 
	 * @return a hash code for this {@code Property} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.propertyBuilder, this.code, this.name, this.nameInPlural);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PropertyBuilder doRequireValidPropertyBuilder(final PType type, final PropertyBuilder propertyBuilder) {
		if(!propertyBuilder.isTypeSupported(type)) {
			throw new IllegalArgumentException(String.format("The PropertyBuilder supplied does not accept \"%s\" as PType!", type.getName()));
		}
		
		return propertyBuilder;
	}
}
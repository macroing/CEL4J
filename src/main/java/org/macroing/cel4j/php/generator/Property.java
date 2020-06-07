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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.php.model.PType;

//TODO: Add Javadocs!
public final class Property {
	private final PType pType;
	private final PropertyBuilder propertyBuilder;
	private final String code;
	private final String name;
	private final String namePlural;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Property(final PType pType, final PropertyBuilder propertyBuilder, final String code) {
		this(pType, propertyBuilder, code, code);
	}
	
//	TODO: Add Javadocs!
	public Property(final PType pType, final PropertyBuilder propertyBuilder, final String code, final String name) {
		this(pType, propertyBuilder, code, name, "");
	}
	
//	TODO: Add Javadocs!
	public Property(final PType pType, final PropertyBuilder propertyBuilder, final String code, final String name, final String namePlural) {
		this.pType = Objects.requireNonNull(pType, "pType == null");
		this.propertyBuilder = doRequireValidPropertyBuilder(pType, Objects.requireNonNull(propertyBuilder, "propertyBuilder == null"));
		this.code = Objects.requireNonNull(code, "code == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.namePlural = Objects.requireNonNull(namePlural, "namePlural == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PType getPType() {
		return this.pType;
	}
	
//	TODO: Add Javadocs!
	public PropertyBuilder getPropertyBuilder() {
		return this.propertyBuilder;
	}
	
//	TODO: Add Javadocs!
	public String getCode() {
		return this.code;
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public String getNamePlural() {
		return this.namePlural;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Property)) {
			return false;
		} else if(!Objects.equals(this.pType, Property.class.cast(object).pType)) {
			return false;
		} else if(!Objects.equals(this.propertyBuilder, Property.class.cast(object).propertyBuilder)) {
			return false;
		} else if(!Objects.equals(this.code, Property.class.cast(object).code)) {
			return false;
		} else if(!Objects.equals(this.name, Property.class.cast(object).name)) {
			return false;
		} else if(!Objects.equals(this.namePlural, Property.class.cast(object).namePlural)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.pType, this.propertyBuilder, this.code, this.name, this.namePlural);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PropertyBuilder doRequireValidPropertyBuilder(final PType pType, final PropertyBuilder propertyBuilder) {
		if(!propertyBuilder.isPTypeSupported(pType)) {
			throw new IllegalArgumentException(String.format("The PropertyBuilder supplied does not accept \"%s\" as PType!", pType.getName()));
		}
		
		return propertyBuilder;
	}
}
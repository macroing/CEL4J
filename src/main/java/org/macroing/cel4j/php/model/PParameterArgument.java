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
import java.util.Objects;
import java.util.Optional;

//TODO: Add Javadocs!
public final class PParameterArgument {
	private final PType pType;
	private final PValue pValue;
	private final String name;
	private final boolean isNullable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PParameterArgument() {
		this("name");
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name) {
		this(name, null);
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name, final PType pType) {
		this(name, pType, null);
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name, final PType pType, final PValue pValue) {
		this(name, pType, pValue, false);
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name, final PType pType, final PValue pValue, final boolean isNullable) {
		this.pType = pType;
		this.pValue = pValue;
		this.name = Objects.requireNonNull(name, "name == null");
		this.isNullable = isNullable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Optional<PType> getPType() {
		return Optional.ofNullable(this.pType);
	}
	
//	TODO: Add Javadocs!
	public Optional<PValue> getPValue() {
		return Optional.ofNullable(this.pValue);
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(hasPType() && isNullable() ? "?" : "");
		stringBuilder.append(hasPType() ? this.pType.getName() : "");
		stringBuilder.append(hasPType() ? " " : "");
		stringBuilder.append("$" + getName());
		
		if(hasPValue()) {
			stringBuilder.append(" = ");
			stringBuilder.append(this.pValue.getSourceCode());
		}
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PParameterArgument)) {
			return false;
		} else if(!Objects.equals(this.pType, PParameterArgument.class.cast(object).pType)) {
			return false;
		} else if(!Objects.equals(this.pValue, PParameterArgument.class.cast(object).pValue)) {
			return false;
		} else if(!Objects.equals(this.name, PParameterArgument.class.cast(object).name)) {
			return false;
		} else if(this.isNullable != PParameterArgument.class.cast(object).isNullable) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public boolean hasPType() {
		return this.pType != null;
	}
	
//	TODO: Add Javadocs!
	public boolean hasPValue() {
		return this.pValue != null;
	}
	
//	TODO: Add Javadocs!
	public boolean isNullable() {
		return this.isNullable;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.pType, this.pValue, this.name, Boolean.valueOf(this.isNullable));
	}
}
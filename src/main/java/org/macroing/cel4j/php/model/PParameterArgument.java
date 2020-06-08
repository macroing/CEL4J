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
	private final PType type;
	private final PValue value;
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
	public PParameterArgument(final String name, final PType type) {
		this(name, type, null);
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name, final PType type, final PValue value) {
		this(name, type, value, false);
	}
	
//	TODO: Add Javadocs!
	public PParameterArgument(final String name, final PType type, final PValue value, final boolean isNullable) {
		this.type = type;
		this.value = value;
		this.name = Objects.requireNonNull(name, "name == null");
		this.isNullable = isNullable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Optional<PType> getType() {
		return Optional.ofNullable(this.type);
	}
	
//	TODO: Add Javadocs!
	public Optional<PValue> getValue() {
		return Optional.ofNullable(this.value);
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public boolean hasType() {
		return this.type != null;
	}
	
//	TODO: Add Javadocs!
	public boolean hasValue() {
		return this.value != null;
	}
	
//	TODO: Add Javadocs!
	public boolean isNullable() {
		return this.isNullable;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.value, this.name, Boolean.valueOf(this.isNullable));
	}
}
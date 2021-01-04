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
package org.macroing.cel4j.java.decompiler;

import java.util.Objects;

final class JParameter {
	private final JType type;
	private final String name;
	private final boolean isFinal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	JParameter(final JType type, final String name) {
		this(type, name, false);
	}
	
	JParameter(final JType type, final String name, final boolean isFinal) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.isFinal = isFinal;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public JType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return String.format("JParameter: [Type=%s], [Name=%s], [IsFinal=%s]", getType(), getName(), Boolean.valueOf(isFinal()));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JParameter)) {
			return false;
		} else if(!Objects.equals(this.type, JParameter.class.cast(object).type)) {
			return false;
		} else if(!Objects.equals(this.name, JParameter.class.cast(object).name)) {
			return false;
		} else if(this.isFinal != JParameter.class.cast(object).isFinal) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.name, Boolean.valueOf(this.isFinal));
	}
}
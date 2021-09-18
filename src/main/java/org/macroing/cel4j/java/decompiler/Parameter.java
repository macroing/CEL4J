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

import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;

final class Parameter implements Comparable<Parameter> {
	private final Type type;
	private final JavaTypeSignature javaTypeSignature;
	private final String name;
	private final boolean isFinal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Parameter(final Type type) {
		this(type, "");
	}
	
	public Parameter(final Type type, final String name) {
		this(type, name, false);
	}
	
	public Parameter(final Type type, final String name, final boolean isFinal) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.isFinal = isFinal;
		this.javaTypeSignature = null;
	}
	
	public Parameter(final Type type, final String name, final boolean isFinal, final JavaTypeSignature javaTypeSignature) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.isFinal = isFinal;
		this.javaTypeSignature = Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Type getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getNameOrGenerate(final LocalVariableNameGenerator localVariableNameGenerator, final int index) {
		return isNamed() ? getName() : localVariableNameGenerator.generateLocalVariableName(getType().getName(), index);
	}
	
	public String getTypeName(final JPackageNameFilter jPackageNameFilter) {
		return Names.filterPackageNames(jPackageNameFilter, this.javaTypeSignature != null ? this.javaTypeSignature.toExternalForm() : getType().getName());
	}
	
	public String toExternalForm(final JPackageNameFilter jPackageNameFilter, final LocalVariableNameGenerator localVariableNameGenerator, final int index) {
		return String.format("%s%s %s", isFinal() ? "final " : "", getTypeName(jPackageNameFilter), getNameOrGenerate(localVariableNameGenerator, index));
	}
	
	@Override
	public String toString() {
		return String.format("JParameter: [Type=%s], [Name=%s], [IsFinal=%s]", getType(), getName(), Boolean.valueOf(isFinal()));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Parameter)) {
			return false;
		} else if(!Objects.equals(this.type, Parameter.class.cast(object).type)) {
			return false;
		} else if(!Objects.equals(this.javaTypeSignature, Parameter.class.cast(object).javaTypeSignature)) {
			return false;
		} else if(!Objects.equals(this.name, Parameter.class.cast(object).name)) {
			return false;
		} else if(this.isFinal != Parameter.class.cast(object).isFinal) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	
	public boolean isNamed() {
		return !this.name.isEmpty();
	}
	
	@Override
	public int compareTo(final Parameter parameter) {
		return getType().getSimpleName().compareTo(parameter.getType().getSimpleName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.javaTypeSignature, this.name, Boolean.valueOf(this.isFinal));
	}
}
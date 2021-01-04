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
package org.macroing.cel4j.php.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

final class StringRegexPropertyBuilder extends AbstractPropertyBuilder {
	private final String pattern;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StringRegexPropertyBuilder(final String pattern) {
		this.pattern = Objects.requireNonNull(pattern, "pattern == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<PMethod> toMethods(final Property property) {
		final List<PMethod> methods = new ArrayList<>();
		
		methods.add(toMethodDoParseString());
		methods.add(toMethodGet(property));
		methods.add(toMethodHas(property));
		methods.add(doToMethodSet(property, this.pattern));
		methods.add(doToMethodDoUpdateStringByRegex());
		
		return methods;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof StringRegexPropertyBuilder)) {
			return false;
		} else if(!Objects.equals(this.pattern, StringRegexPropertyBuilder.class.cast(object).pattern)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isTypeSupported(final PType type) {
		final String typeName = type.getName();
		
		switch(typeName) {
			case "string":
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.pattern);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToMethodDoUpdateStringByRegex() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("newString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("oldString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("pattern", PType.STRING, null, false));
		pMethod.getBlock().addLine("if($newString === null) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else if(preg_match($pattern, $newString)) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else {");
		pMethod.getBlock().addLine("	return $oldString;");
		pMethod.getBlock().addLine("}");
		pMethod.setEnclosedByClass();
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByRegex");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodSet(final Property property, final String pattern) {
		final PType type = property.getType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, PValue.NULL, true));
		pMethod.getBlock().addLinef("return ($this->%s = self::doUpdateStringByRegex($%s, $this->%s, '%s')) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, pattern, nameCamelCaseModified);
		pMethod.setEnclosedByClass();
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
}
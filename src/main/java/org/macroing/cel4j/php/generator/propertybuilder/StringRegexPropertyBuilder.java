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
package org.macroing.cel4j.php.generator.propertybuilder;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.php.generator.Property;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

//TODO: Add Javadocs!
public final class StringRegexPropertyBuilder extends AbstractPropertyBuilder {
	private final String pattern;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public StringRegexPropertyBuilder(final String pattern) {
		this.pattern = Objects.requireNonNull(pattern, "pattern == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public List<PMethod> toPMethods(final Property property) {
		final List<PMethod> pMethods = new ArrayList<>();
		
		pMethods.add(toPMethodDoParseString());
		pMethods.add(toPMethodGet(property));
		pMethods.add(toPMethodHas(property));
		pMethods.add(doToPMethodSet(property, this.pattern));
		pMethods.add(doToPMethodDoUpdateStringByRegex());
		
		return pMethods;
	}
	
//	TODO: Add Javadocs!
	public String getPattern() {
		return this.pattern;
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public boolean isPTypeSupported(final PType pType) {
		final String pTypeName = pType.getName();
		
		switch(pTypeName) {
			case "string":
				return true;
			default:
				return false;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.pattern);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToPMethodDoUpdateStringByRegex() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("newString", PType.STRING, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("oldString", PType.STRING, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("pattern", PType.STRING, null, false));
		pMethod.getPBlock().addLine("if($newString === null) {");
		pMethod.getPBlock().addLine("	return $newString;");
		pMethod.getPBlock().addLine("} else if(preg_match($pattern, $newString)) {");
		pMethod.getPBlock().addLine("	return $newString;");
		pMethod.getPBlock().addLine("} else {");
		pMethod.getPBlock().addLine("	return $oldString;");
		pMethod.getPBlock().addLine("}");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByRegex");
		pMethod.setPReturnType(new PReturnType(PType.STRING, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodSet(final Property property, final String pattern) {
		final PType pType = property.getPType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, PValue.NULL, true));
		pMethod.getPBlock().addLinef("return ($this->%s = self::doUpdateStringByRegex($%s, $this->%s, '%s')) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, pattern, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
}
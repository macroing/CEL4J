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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

final class StringLengthPropertyBuilder extends AbstractPropertyBuilder {
	private final int maximumLength;
	private final int minimumLength;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StringLengthPropertyBuilder(final int lengthA, final int lengthB) {
		this.maximumLength = Math.max(lengthA, lengthB);
		this.minimumLength = Math.min(lengthA, lengthB);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<PMethod> toMethods(final Property property) {
		final List<PMethod> methods = new ArrayList<>();
		
		methods.add(toMethodDoParseString());
		methods.add(toMethodGet(property));
		methods.add(toMethodHas(property));
		methods.add(doToMethodSet(property, this.minimumLength, this.maximumLength));
		methods.add(doToMethodDoUpdateStringByLength());
		
		return methods;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof StringLengthPropertyBuilder)) {
			return false;
		} else if(this.maximumLength != StringLengthPropertyBuilder.class.cast(object).maximumLength) {
			return false;
		} else if(this.minimumLength != StringLengthPropertyBuilder.class.cast(object).minimumLength) {
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
		return Objects.hash(Integer.valueOf(this.maximumLength), Integer.valueOf(this.minimumLength));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToMethodDoUpdateStringByLength() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("newString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("oldString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("minimumLength", PType.INT, null, false));
		pMethod.addParameterArgument(new PParameterArgument("maximumLength", PType.INT, null, false));
		pMethod.getBlock().addLine("if($newString === null) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else if(mb_strlen($newString) >= $minimumLength && mb_strlen($newString) <= $maximumLength) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else {");
		pMethod.getBlock().addLine("	return $oldString;");
		pMethod.getBlock().addLine("}");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByLength");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodSet(final Property property, final int minimumLength, final int maximumLength) {
		final PType type = property.getType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, PValue.NULL, true));
		pMethod.getBlock().addLinef("return ($this->%s = self::doUpdateStringByLength($%s, $this->%s, %s, %s)) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, Integer.toString(minimumLength), Integer.toString(maximumLength), nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
}
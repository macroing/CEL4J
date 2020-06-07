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
public final class StringLengthPropertyBuilder extends AbstractPropertyBuilder {
	private final int maximumLength;
	private final int minimumLength;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public StringLengthPropertyBuilder(final int lengthA, final int lengthB) {
		this.maximumLength = Math.max(lengthA, lengthB);
		this.minimumLength = Math.min(lengthA, lengthB);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public List<PMethod> toPMethods(final Property property) {
		final List<PMethod> pMethods = new ArrayList<>();
		
		pMethods.add(toPMethodDoParseString());
		pMethods.add(toPMethodGet(property));
		pMethods.add(toPMethodHas(property));
		pMethods.add(doToPMethodSet(property, this.minimumLength, this.maximumLength));
		pMethods.add(doToPMethodDoUpdateStringByLength());
		
		return pMethods;
	}
	
//	TODO: Add Javadocs!
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
	public int getMaximumLength() {
		return this.maximumLength;
	}
	
//	TODO: Add Javadocs!
	public int getMinimumLength() {
		return this.minimumLength;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.maximumLength), Integer.valueOf(this.minimumLength));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToPMethodDoUpdateStringByLength() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("newString", PType.STRING, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("oldString", PType.STRING, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("minimumLength", PType.INT, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("maximumLength", PType.INT, null, false));
		pMethod.getPBlock().addLine("if($newString === null) {");
		pMethod.getPBlock().addLine("	return $newString;");
		pMethod.getPBlock().addLine("} else if(mb_strlen($newString) >= $minimumLength && mb_strlen($newString) <= $maximumLength) {");
		pMethod.getPBlock().addLine("	return $newString;");
		pMethod.getPBlock().addLine("} else {");
		pMethod.getPBlock().addLine("	return $oldString;");
		pMethod.getPBlock().addLine("}");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByLength");
		pMethod.setPReturnType(new PReturnType(PType.STRING, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodSet(final Property property, final int minimumLength, final int maximumLength) {
		final PType pType = property.getPType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, PValue.NULL, true));
		pMethod.getPBlock().addLinef("return ($this->%s = self::doUpdateStringByLength($%s, $this->%s, %s, %s)) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, Integer.toString(minimumLength), Integer.toString(maximumLength), nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
}
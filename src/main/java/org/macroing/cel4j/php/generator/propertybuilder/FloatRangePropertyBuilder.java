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
public final class FloatRangePropertyBuilder extends AbstractPropertyBuilder {
	private final float maximumValue;
	private final float minimumValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public FloatRangePropertyBuilder(final float valueA, final float valueB) {
		this.maximumValue = Math.max(valueA, valueB);
		this.minimumValue = Math.min(valueA, valueB);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public List<PMethod> toPMethods(final Property property) {
		final List<PMethod> pMethods = new ArrayList<>();
		
		pMethods.add(toPMethodDoParseFloat());
		pMethods.add(toPMethodGet(property));
		pMethods.add(toPMethodHas(property));
		pMethods.add(doToPMethodSet(property, this.minimumValue, this.maximumValue));
		pMethods.add(doToPMethodDoUpdateFloatByRange());
		
		return pMethods;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof FloatRangePropertyBuilder)) {
			return false;
		} else if(Float.compare(this.maximumValue, FloatRangePropertyBuilder.class.cast(object).maximumValue) != 0) {
			return false;
		} else if(Float.compare(this.minimumValue, FloatRangePropertyBuilder.class.cast(object).minimumValue) != 0) {
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
			case "float":
				return true;
			default:
				return false;
		}
	}
	
//	TODO: Add Javadocs!
	public float getMaximumValue() {
		return this.maximumValue;
	}
	
//	TODO: Add Javadocs!
	public float getMinimumValue() {
		return this.minimumValue;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.maximumValue), Float.valueOf(this.minimumValue));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToPMethodDoUpdateFloatByRange() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("newFloat", PType.FLOAT, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("oldFloat", PType.FLOAT, null, true));
		pMethod.addPParameterArgument(new PParameterArgument("minimumValue", PType.FLOAT, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("maximumValue", PType.FLOAT, null, false));
		pMethod.getPBlock().addLine("if($newFloat === null) {");
		pMethod.getPBlock().addLine("	return $newFloat;");
		pMethod.getPBlock().addLine("} else if($newFloat >= $minimumValue && $newFloat <= $maximumValue) {");
		pMethod.getPBlock().addLine("	return $newFloat;");
		pMethod.getPBlock().addLine("} else {");
		pMethod.getPBlock().addLine("	return $oldFloat;");
		pMethod.getPBlock().addLine("}");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doUpdateFloatByRange");
		pMethod.setPReturnType(new PReturnType(PType.FLOAT, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodSet(final Property property, final float minimumValue, final float maximumValue) {
		final PType pType = property.getPType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, PValue.NULL, true));
		pMethod.getPBlock().addLinef("return ($this->%s = self::doUpdateFloatByRange($%s, $this->%s, %s, %s)) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, Float.toString(minimumValue), Float.toString(maximumValue), nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
}
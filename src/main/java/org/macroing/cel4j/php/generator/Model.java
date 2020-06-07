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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.php.model.PClass;
import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PConstructor;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

//TODO: Add Javadocs!
public final class Model {
	private final List<Property> properties;
	private final String code;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Model(final String code, final String name) {
		this.properties = new ArrayList<>();
		this.code = Objects.requireNonNull(code, "code == null");
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<Property> getProperties() {
		return new ArrayList<>(this.properties);
	}
	
//	TODO: Add Javadocs!
	public PClass toPClass() {
		final String name = getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<Property> properties = getProperties();
		
		doSortPropertiesByName(properties);
		
		final
		PConstructor pConstructor = new PConstructor();
		pConstructor.setFinal(true);
		pConstructor.setPublic(true);
		
		final
		PClass pClass = new PClass();
		pClass.setFinal(true);
		pClass.setName(nameCamelCase);
		pClass.setPConstructor(pConstructor);
		pClass.addPConst(new PConst(nameUnderscoreSeparatedUpperCase, PValue.valueOf(getCode())));
		pClass.addPMethod(doToPMethodCopy(properties, name));
		pClass.addPMethod(doToPMethodParseArray(properties, name));
		pClass.addPMethod(doToPMethodParseJSON(name));
		pClass.addPMethod(doToPMethodSet(properties, name));
		pClass.addPMethod(doToPMethodToArray(properties));
		pClass.addPMethod(doToPMethodToJSON());
		
		for(final Property property : properties) {
			final PropertyBuilder propertyBuilder = property.getPropertyBuilder();
			
			final List<String> pConstructorLines = propertyBuilder.toPConstructorLines(property);
			
			for(final PConst pConst : propertyBuilder.toPConsts(property)) {
				pClass.addPConst(pConst);
			}
			
			for(final PField pField : propertyBuilder.toPFields(property)) {
				pClass.addPField(pField);
			}
			
			for(final PMethod pMethod : propertyBuilder.toPMethods(property)) {
				pClass.addPMethod(pMethod);
			}
			
			for(final String pConstructorLine : pConstructorLines) {
				pConstructor.getPBlock().addLine(pConstructorLine);
			}
		}
		
		pClass.sort();
		
		return pClass;
	}
	
//	TODO: Add Javadocs!
	public PDocument toPDocument(final String namespace) {
		final
		PDocument pDocument = new PDocument();
		pDocument.addPClass(toPClass());
		pDocument.setNamespace(namespace);
		
		return pDocument;
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
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Model)) {
			return false;
		} else if(!Objects.equals(this.properties, Model.class.cast(object).properties)) {
			return false;
		} else if(!Objects.equals(this.code, Model.class.cast(object).code)) {
			return false;
		} else if(!Objects.equals(this.name, Model.class.cast(object).name)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.properties, this.code, this.name);
	}
	
//	TODO: Add Javadocs!
	public void addProperty(final Property property) {
		this.properties.add(Objects.requireNonNull(property, "property == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToPMethodCopy(final List<Property> properties, final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getPBlock().addLinef("$%s = new %s();", nameCamelCaseModified, nameCamelCase);
		
		for(final Property property : properties) {
			final List<String> pMethodCopyLines = property.getPropertyBuilder().toPMethodCopyLines(property, nameCamelCaseModified);
			
			for(final String pMethodCopyLine : pMethodCopyLines) {
				pMethod.getPBlock().addLine(pMethodCopyLine);
			}
		}
		
		pMethod.getPBlock().addLinef("");
		pMethod.getPBlock().addLinef("return $%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("copy");
		pMethod.setPReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		pMethod.setPublic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodParseArray(final List<Property> properties, final String name) {
		final String nameArray = "array";
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameArray, PType.ARRAY, null, false));
		pMethod.getPBlock().addLinef("if(array_key_exists(self::%s, $%s)) {", nameUnderscoreSeparatedUpperCase, nameArray);
		pMethod.getPBlock().addLinef("	$%s = $%s[self::%s];", nameArray, nameArray, nameUnderscoreSeparatedUpperCase);
		pMethod.getPBlock().addLinef("	$%s = is_array($%s) ? $%s : [];", nameArray, nameArray, nameArray);
		pMethod.getPBlock().addLinef("}");
		pMethod.getPBlock().addLinef("");
		pMethod.getPBlock().addLinef("$%s = new %s();", nameCamelCaseModified, nameCamelCase);
		
		for(final Property property : properties) {
			final List<String> pMethodParseArrayLines = property.getPropertyBuilder().toPMethodParseArrayLines(property, nameArray, nameCamelCaseModified);
			
			for(final String pMethodParseArrayLine : pMethodParseArrayLines) {
				pMethod.getPBlock().addLine(pMethodParseArrayLine);
			}
		}
		
		pMethod.getPBlock().addLinef("");
		pMethod.getPBlock().addLinef("return $%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("parseArray");
		pMethod.setPReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		pMethod.setPublic(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodParseJSON(final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("stringJSON", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return self::parseArray(json_decode($stringJSON, true));");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("parseJSON");
		pMethod.setPReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		pMethod.setPublic(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodSet(final List<Property> properties, final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, PType.valueOf(nameCamelCase), null, false));
		
		for(final Property property : properties) {
			final List<String> pMethodSetLines = property.getPropertyBuilder().toPMethodSetLines(property, nameCamelCaseModified);
			
			for(final String pMethodSetLine : pMethodSetLines) {
				pMethod.getPBlock().addLine(pMethodSetLine);
			}
		}
		
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set");
		pMethod.setPReturnType(new PReturnType(PType.VOID, false));
		pMethod.setPublic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodToArray(final List<Property> properties) {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("isIncludingNull", PType.BOOL, PValue.valueOf(false), false));
		pMethod.getPBlock().addLine("$array = [];");
		
		for(final Property property : properties) {
			final List<String> pMethodToArrayLines = property.getPropertyBuilder().toPMethodToArrayLines(property);
			
			if(pMethodToArrayLines.size() > 0) {
				pMethod.getPBlock().addLine("");
				
				for(final String pMethodToArrayLine : pMethodToArrayLines) {
					pMethod.getPBlock().addLine(pMethodToArrayLine);
				}
			}
		}
		
		pMethod.getPBlock().addLine("");
		pMethod.getPBlock().addLine("return $array;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("toArray");
		pMethod.setPReturnType(new PReturnType(PType.ARRAY, false));
		pMethod.setPublic(true);
		
		return pMethod;
	}
	
	private static PMethod doToPMethodToJSON() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("isIncludingNull", PType.BOOL, PValue.valueOf(false), false));
		pMethod.addPParameterArgument(new PParameterArgument("isPrettyPrinting", PType.BOOL, PValue.valueOf(false), false));
		pMethod.getPBlock().addLine("return $isPrettyPrinting ? json_encode($this->toArray($isIncludingNull), JSON_PRETTY_PRINT) : json_encode($this->toArray($isIncludingNull));");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("toJSON");
		pMethod.setPReturnType(new PReturnType(PType.STRING, false));
		pMethod.setPublic(true);
		
		return pMethod;
	}
	
	private static void doSortPropertiesByName(final List<Property> properties) {
		Collections.sort(properties, (a, b) -> a.getName().compareTo(b.getName()));
	}
}
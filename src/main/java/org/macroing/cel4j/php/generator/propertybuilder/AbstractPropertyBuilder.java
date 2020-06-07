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

import org.macroing.cel4j.php.generator.Property;
import org.macroing.cel4j.php.generator.PropertyBuilder;
import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

//TODO: Add Javadocs!
public abstract class AbstractPropertyBuilder implements PropertyBuilder {
//	TODO: Add Javadocs!
	protected AbstractPropertyBuilder() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public List<PConst> toPConsts(final Property property) {
		final List<PConst> pConsts = new ArrayList<>();
		
		pConsts.add(toPConst(property));
		
		return pConsts;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<PField> toPFields(final Property property) {
		final List<PField> pFields = new ArrayList<>();
		
		pFields.add(toPField(property));
		
		return pFields;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<PMethod> toPMethods(final Property property) {
		final List<PMethod> pMethods = new ArrayList<>();
		
		final PType pType = property.getPType();
		
		final String pTypeName = pType.getName();
		
		switch(pTypeName) {
			case "array":
				pMethods.add(toPMethodDoParseArray());
				
				break;
			case "bool":
				pMethods.add(toPMethodDoParseBool());
				
				break;
			case "callable":
				break;
			case "float":
				pMethods.add(toPMethodDoParseFloat());
				
				break;
			case "int":
				pMethods.add(toPMethodDoParseInt());
				
				break;
			case "iterable":
				break;
			case "object":
				pMethods.add(toPMethodDoParseObject());
				
				break;
			case "self":
				break;
			case "string":
				pMethods.add(toPMethodDoParseString());
				
				break;
			case "void":
				break;
			default:
				pMethods.add(toPMethodDoParse(pTypeName));
				
				break;
		}
		
		pMethods.add(toPMethodGet(property));
		pMethods.add(toPMethodHas(property));
		pMethods.add(toPMethodSet(property));
		
		return pMethods;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toPConstructorLines(final Property property) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> pConstructorLines = new ArrayList<>();
		
		pConstructorLines.add(String.format("$this->set%s();", nameCamelCase));
		
		return pConstructorLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toPMethodCopyLines(final Property property, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> pMethodCopyLines = new ArrayList<>();
		
		pMethodCopyLines.add(String.format("$%s->set%s($this->get%s());", nameType, nameCamelCase, nameCamelCase));
		
		return pMethodCopyLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toPMethodParseArrayLines(final Property property, final String nameArray, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameMethod = doGetNameMethod(property);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<String> pMethodParseArrayLines = new ArrayList<>();
		
		if(!nameMethod.isEmpty()) {
			pMethodParseArrayLines.add(String.format("$%s->set%s(self::%s($%s, self::%s));", nameType, nameCamelCase, nameMethod, nameArray, nameUnderscoreSeparatedUpperCase));
		}
		
		return pMethodParseArrayLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toPMethodSetLines(final Property property, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> pMethodSetLines = new ArrayList<>();
		
		pMethodSetLines.add(String.format("$this->set%s($%s->get%s());", nameCamelCase, nameType, nameCamelCase));
		
		return pMethodSetLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toPMethodToArrayLines(final Property property) {
		final PType pType = property.getPType();
		
		final String pTypeName = pType.getName();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<String> pMethodToArrayLines = new ArrayList<>();
		
		switch(pTypeName) {
			case "array":
			case "bool":
			case "float":
			case "int":
			case "string":
				pMethodToArrayLines.add(String.format("if($isIncludingNull || $this->has%s()) {", nameCamelCase));
				pMethodToArrayLines.add(String.format("	$array[self::%s] = $this->get%s();", nameUnderscoreSeparatedUpperCase, nameCamelCase));
				pMethodToArrayLines.add(String.format("}"));
				
				break;
			case "callable":
			case "iterable":
			case "object":
			case "self":
			case "void":
				break;
			default:
				pMethodToArrayLines.add(String.format("if($isIncludingNull || $this->has%s()) {", nameCamelCase));
				pMethodToArrayLines.add(String.format("	$array[self::%s] = ($this->has%s() ? $this->get%s()->toArray($isIncludingNull) : $this->get%s());", nameUnderscoreSeparatedUpperCase, nameCamelCase, nameCamelCase, nameCamelCase));
				pMethodToArrayLines.add(String.format("}"));
				
				break;
		}
		
		return pMethodToArrayLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean isPTypeSupported(final PType pType) {
		final String pTypeName = pType.getName();
		
		switch(pTypeName) {
			case "array":
				return true;
			case "bool":
				return true;
			case "callable":
				return false;
			case "float":
				return true;
			case "int":
				return true;
			case "iterable":
				return false;
			case "object":
				return false;
			case "self":
				return false;
			case "string":
				return true;
			case "void":
				return false;
			default:
				return true;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	protected static PConst toPConst(final Property property) {
		return new PConst(Strings.formatUnderscoreSeparatedUpperCase(property.getName()), PValue.valueOf(property.getCode()));
	}
	
//	TODO: Add Javadocs!
	protected static PField toPField(final Property property) {
		final
		PField pField = new PField();
		pField.setName(Strings.formatCamelCaseModified(property.getName()));
		pField.setPrivate(true);
		
		return pField;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParse(final String pTypeName) {
		final String pTypeNameCamelCase = Strings.formatCamelCase(pTypeName);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLinef("return array_key_exists($key, $array) ? (is_array($array[$key]) ? %s::parseArray($array[$key]) : null) : null;", pTypeNameCamelCase);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParse" + pTypeNameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.valueOf(pTypeNameCamelCase), true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseArray() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return array_key_exists($key, $array) ? (is_array($array[$key]) ? $array[$key] : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseArray");
		pMethod.setPReturnType(new PReturnType(PType.ARRAY, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseBool() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? boolval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseBool");
		pMethod.setPReturnType(new PReturnType(PType.BOOL, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseFloat() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? floatval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseFloat");
		pMethod.setPReturnType(new PReturnType(PType.FLOAT, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseInt() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? intval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseInt");
		pMethod.setPReturnType(new PReturnType(PType.INT, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseObject() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseObject");
		pMethod.setPReturnType(new PReturnType(PType.OBJECT, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodDoParseString() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addPParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getPBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? strval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseString");
		pMethod.setPReturnType(new PReturnType(PType.STRING, true));
		pMethod.setPrivate(true);
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodGet(final Property property) {
		return PMethod.newClassMethodGet(Strings.formatCamelCase(property.getName()), property.getPType(), true);
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodHas(final Property property) {
		return PMethod.newClassMethodHas(Strings.formatCamelCase(property.getName()));
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toPMethodSet(final Property property) {
		final PType pType = property.getPType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, PValue.NULL, true));
		pMethod.getPBlock().addLinef("return ($this->%s = $%s) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGetNameMethod(final Property property) {
		final PType pType = property.getPType();
		
		final String pTypeName = pType.getName();
		
		switch(pTypeName) {
			case "array":
				return "doParseArray";
			case "bool":
				return "doParseBool";
			case "callable":
				return "";
			case "float":
				return "doParseFloat";
			case "int":
				return "doParseInt";
			case "iterable":
				return "";
			case "object":
				return "doParseObject";
			case "self":
				return "";
			case "string":
				return "doParseString";
			case "void":
				return "";
			default:
				return "doParse" + Strings.formatCamelCase(pTypeName);
		}
	}
}
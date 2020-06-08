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
	public List<PConst> toConsts(final Property property) {
		final List<PConst> consts = new ArrayList<>();
		
		consts.add(toConst(property));
		
		return consts;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<PField> toFields(final Property property) {
		final List<PField> fields = new ArrayList<>();
		
		fields.add(toField(property));
		
		return fields;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<PMethod> toMethods(final Property property) {
		final List<PMethod> methods = new ArrayList<>();
		
		final PType type = property.getType();
		
		final String typeName = type.getName();
		
		switch(typeName) {
			case "array":
				methods.add(toMethodDoParseArray());
				
				break;
			case "bool":
				methods.add(toMethodDoParseBool());
				
				break;
			case "callable":
				break;
			case "float":
				methods.add(toMethodDoParseFloat());
				
				break;
			case "int":
				methods.add(toMethodDoParseInt());
				
				break;
			case "iterable":
				break;
			case "object":
				methods.add(toMethodDoParseObject());
				
				break;
			case "self":
				break;
			case "string":
				methods.add(toMethodDoParseString());
				
				break;
			case "void":
				break;
			default:
				methods.add(toMethodDoParse(typeName));
				
				break;
		}
		
		methods.add(toMethodGet(property));
		methods.add(toMethodHas(property));
		methods.add(toMethodSet(property));
		
		return methods;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toConstructorLines(final Property property) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> constructorLines = new ArrayList<>();
		
		constructorLines.add(String.format("$this->set%s();", nameCamelCase));
		
		return constructorLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toMethodCopyLines(final Property property, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> methodCopyLines = new ArrayList<>();
		
		methodCopyLines.add(String.format("$%s->set%s($this->get%s());", nameType, nameCamelCase, nameCamelCase));
		
		return methodCopyLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toMethodParseArrayLines(final Property property, final String nameArray, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameMethod = doGetNameMethod(property);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<String> methodParseArrayLines = new ArrayList<>();
		
		if(!nameMethod.isEmpty()) {
			methodParseArrayLines.add(String.format("$%s->set%s(self::%s($%s, self::%s));", nameType, nameCamelCase, nameMethod, nameArray, nameUnderscoreSeparatedUpperCase));
		}
		
		return methodParseArrayLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toMethodSetLines(final Property property, final String nameType) {
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final List<String> methodSetLines = new ArrayList<>();
		
		methodSetLines.add(String.format("$this->set%s($%s->get%s());", nameCamelCase, nameType, nameCamelCase));
		
		return methodSetLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public List<String> toMethodToArrayLines(final Property property) {
		final PType type = property.getType();
		
		final String typeName = type.getName();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<String> methodToArrayLines = new ArrayList<>();
		
		switch(typeName) {
			case "array":
			case "bool":
			case "float":
			case "int":
			case "string":
				methodToArrayLines.add(String.format("if($isIncludingNull || $this->has%s()) {", nameCamelCase));
				methodToArrayLines.add(String.format("	$array[self::%s] = $this->get%s();", nameUnderscoreSeparatedUpperCase, nameCamelCase));
				methodToArrayLines.add(String.format("}"));
				
				break;
			case "callable":
			case "iterable":
			case "object":
			case "self":
			case "void":
				break;
			default:
				methodToArrayLines.add(String.format("if($isIncludingNull || $this->has%s()) {", nameCamelCase));
				methodToArrayLines.add(String.format("	$array[self::%s] = ($this->has%s() ? $this->get%s()->toArray($isIncludingNull) : $this->get%s());", nameUnderscoreSeparatedUpperCase, nameCamelCase, nameCamelCase, nameCamelCase));
				methodToArrayLines.add(String.format("}"));
				
				break;
		}
		
		return methodToArrayLines;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean isTypeSupported(final PType type) {
		final String typeName = type.getName();
		
		switch(typeName) {
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
	protected static PConst toConst(final Property property) {
		return new PConst(Strings.formatUnderscoreSeparatedUpperCase(property.getName()), PValue.valueOf(property.getCode()));
	}
	
//	TODO: Add Javadocs!
	protected static PField toField(final Property property) {
		final
		PField pField = new PField();
		pField.setName(Strings.formatCamelCaseModified(property.getName()));
		pField.setPrivate(true);
		
		return pField;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParse(final String typeName) {
		final String typeNameCamelCase = Strings.formatCamelCase(typeName);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLinef("return array_key_exists($key, $array) ? (is_array($array[$key]) ? %s::parseArray($array[$key]) : null) : null;", typeNameCamelCase);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParse" + typeNameCamelCase);
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.valueOf(typeNameCamelCase), true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseArray() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return array_key_exists($key, $array) ? (is_array($array[$key]) ? $array[$key] : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseArray");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.ARRAY, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseBool() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? boolval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseBool");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.BOOL, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseFloat() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? floatval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseFloat");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.FLOAT, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseInt() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? intval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseInt");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.INT, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseObject() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseObject");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.OBJECT, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodDoParseString() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("array", PType.ARRAY, null, false));
		pMethod.addParameterArgument(new PParameterArgument("key", PType.STRING, null, false));
		pMethod.getBlock().addLine("return array_key_exists($key, $array) ? ($array[$key] !== null ? strval($array[$key]) : null) : null;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doParseString");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodGet(final Property property) {
		return PMethod.newClassMethodGet(Strings.formatCamelCase(property.getName()), property.getType(), true);
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodHas(final Property property) {
		return PMethod.newClassMethodHas(Strings.formatCamelCase(property.getName()));
	}
	
//	TODO: Add Javadocs!
	protected static PMethod toMethodSet(final Property property) {
		final PType type = property.getType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, PValue.NULL, true));
		pMethod.getBlock().addLinef("return ($this->%s = $%s) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGetNameMethod(final Property property) {
		final PType type = property.getType();
		
		final String typeName = type.getName();
		
		switch(typeName) {
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
				return "doParse" + Strings.formatCamelCase(typeName);
		}
	}
}
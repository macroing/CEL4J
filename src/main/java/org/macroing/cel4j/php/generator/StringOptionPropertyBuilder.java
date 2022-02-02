/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
import java.util.stream.Collectors;

import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Pair;
import org.macroing.cel4j.util.Strings;

final class StringOptionPropertyBuilder extends AbstractPropertyBuilder {
	private final List<Pair<String, String>> stringOptions;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StringOptionPropertyBuilder(final List<Pair<String, String>> stringOptions) {
		this.stringOptions = new ArrayList<>(doRequireNonNullList(Objects.requireNonNull(stringOptions, "stringOptions == null")));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<PConst> toConsts(final Property property) {
		final List<PConst> consts = new ArrayList<>();
		
		consts.add(toConst(property));
		
		for(final Pair<String, String> stringOption : getStringOptions()) {
			consts.add(new PConst(Strings.formatUnderscoreSeparatedUpperCase(stringOption.getY()), PValue.valueOf(stringOption.getX())));
		}
		
		return consts;
	}
	
	@Override
	public List<PMethod> toMethods(final Property property) {
		final List<PMethod> methods = new ArrayList<>();
		
		methods.add(toMethodDoParseString());
		methods.add(toMethodGet(property));
		methods.add(toMethodHas(property));
		methods.add(doToMethodSet(property, getStringOptions()));
		methods.add(doToMethodDoUpdateStringByOption());
		
		return methods;
	}
	
	public List<Pair<String, String>> getStringOptions() {
		return new ArrayList<>(this.stringOptions);
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof StringOptionPropertyBuilder)) {
			return false;
		} else if(!Objects.equals(this.stringOptions, StringOptionPropertyBuilder.class.cast(object).stringOptions)) {
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
		return Objects.hash(this.stringOptions);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static List<Pair<String, String>> doRequireNonNullList(final List<Pair<String, String>> stringOptions) {
		Objects.requireNonNull(stringOptions, "stringOptions == null");
		
		for(int i = 0; i < stringOptions.size(); i++) {
			final Pair<String, String> stringOption = stringOptions.get(i);
			
			Objects.requireNonNull(stringOption, String.format("stringOptions[%s] == null", Integer.toString(i)));
			Objects.requireNonNull(stringOption.getX(), String.format("stringOptions[%s].getX() == null", Integer.toString(i)));
			Objects.requireNonNull(stringOption.getY(), String.format("stringOptions[%s].getY() == null", Integer.toString(i)));
		}
		
		return stringOptions;
	}
	
	private static PMethod doToMethodDoUpdateStringByOption() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("newString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("oldString", PType.STRING, null, true));
		pMethod.addParameterArgument(new PParameterArgument("stringOptions", PType.ARRAY, null, false));
		pMethod.getBlock().addLine("if($newString === null) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else if(in_array($newString, $stringOptions, true)) {");
		pMethod.getBlock().addLine("	return $newString;");
		pMethod.getBlock().addLine("} else {");
		pMethod.getBlock().addLine("	return $oldString;");
		pMethod.getBlock().addLine("}");
		pMethod.setEnclosedByClass();
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByOption");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodSet(final Property property, final List<Pair<String, String>> stringOptions) {
		final PType type = property.getType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final String stringOptionsArray = stringOptions.stream().sorted(StringOptionPropertyBuilder::doCompareStringOptions).map(stringOption -> "self::" + Strings.formatUnderscoreSeparatedUpperCase(stringOption.getY())).collect(Collectors.joining(", ", "[", "]"));
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, PValue.NULL, true));
		pMethod.getBlock().addLinef("return ($this->%s = self::doUpdateStringByOption($%s, $this->%s, %s)) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, stringOptionsArray, nameCamelCaseModified);
		pMethod.setEnclosedByClass();
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
	
	private static int doCompareStringOptions(final Pair<String, String> a, final Pair<String, String> b) {
		final String nameUnderscoreSeparatedUpperCaseA = Strings.formatUnderscoreSeparatedUpperCase(a.getY());
		final String nameUnderscoreSeparatedUpperCaseB = Strings.formatUnderscoreSeparatedUpperCase(b.getY());
		
		return nameUnderscoreSeparatedUpperCaseA.compareTo(nameUnderscoreSeparatedUpperCaseB);
	}
}
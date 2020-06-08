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
import java.util.stream.Collectors;

import org.macroing.cel4j.php.generator.Property;
import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

//TODO: Add Javadocs!
public final class StringOptionPropertyBuilder extends AbstractPropertyBuilder {
	private final List<StringOption> stringOptions;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public StringOptionPropertyBuilder(final StringOption... stringOptions) {
		this.stringOptions = doRequireNonNullList(Objects.requireNonNull(stringOptions, "stringOptions == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public List<PConst> toConsts(final Property property) {
		final List<PConst> consts = new ArrayList<>();
		
		consts.add(toConst(property));
		
		for(final StringOption stringOption : getStringOptions()) {
			consts.add(stringOption.toConst());
		}
		
		return consts;
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public List<StringOption> getStringOptions() {
		return new ArrayList<>(this.stringOptions);
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.stringOptions);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static final class StringOption {
		private final String code;
		private final String name;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		TODO: Add Javadocs!
		public StringOption(final String code, final String name) {
			this.code = Objects.requireNonNull(code, "code == null");
			this.name = Objects.requireNonNull(name, "name == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		TODO: Add Javadocs!
		public PConst toConst() {
			return new PConst(Strings.formatUnderscoreSeparatedUpperCase(getName()), PValue.valueOf(getCode()));
		}
		
//		TODO: Add Javadocs!
		public String getCode() {
			return this.code;
		}
		
//		TODO: Add Javadocs!
		public String getName() {
			return this.name;
		}
		
//		TODO: Add Javadocs!
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof StringOption)) {
				return false;
			} else if(!Objects.equals(this.code, StringOption.class.cast(object).code)) {
				return false;
			} else if(!Objects.equals(this.name, StringOption.class.cast(object).name)) {
				return false;
			} else {
				return true;
			}
		}
		
//		TODO: Add Javadocs!
		@Override
		public int hashCode() {
			return Objects.hash(this.code, this.name);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static List<StringOption> doRequireNonNullList(final StringOption... stringOptions) {
		final List<StringOption> list = new ArrayList<>();
		
		for(int i = 0; i < stringOptions.length; i++) {
			list.add(Objects.requireNonNull(stringOptions[i], String.format("stringOptions[%s] == null", Integer.toString(i))));
		}
		
		return list;
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
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("doUpdateStringByOption");
		pMethod.setPrivate(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, true));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodSet(final Property property, final List<StringOption> stringOptions) {
		final PType type = property.getType();
		
		final String name = property.getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final String stringOptionsArray = stringOptions.stream().sorted(StringOptionPropertyBuilder::doCompareStringOptions).map(stringOption -> "self::" + Strings.formatUnderscoreSeparatedUpperCase(stringOption.getName())).collect(Collectors.joining(", ", "[", "]"));
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, PValue.NULL, true));
		pMethod.getBlock().addLinef("return ($this->%s = self::doUpdateStringByOption($%s, $this->%s, %s)) === $%s;", nameCamelCaseModified, nameCamelCaseModified, nameCamelCaseModified, stringOptionsArray, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL, false));
		
		return pMethod;
	}
	
	private static int doCompareStringOptions(final StringOption a, final StringOption b) {
		final String nameUnderscoreSeparatedUpperCaseA = Strings.formatUnderscoreSeparatedUpperCase(a.getName());
		final String nameUnderscoreSeparatedUpperCaseB = Strings.formatUnderscoreSeparatedUpperCase(b.getName());
		
		return nameUnderscoreSeparatedUpperCaseA.compareTo(nameUnderscoreSeparatedUpperCaseB);
	}
}
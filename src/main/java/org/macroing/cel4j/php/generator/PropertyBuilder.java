/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

import java.util.List;

import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.util.Pair;

/**
 * A {@code PropertyBuilder} is responsible for generating all consts, fields, methods and more for a given {@link Property} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface PropertyBuilder {
	/**
	 * Returns a {@code List} of {@link PConst} with the consts to generate for {@code property}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code PConst} with the consts to generate for {@code property}
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PConst> toConsts(final Property property);
	
	/**
	 * Returns a {@code List} of {@link PField} with the fields to generate for {@code property}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code PField} with the fields to generate for {@code property}
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PField> toFields(final Property property);
	
	/**
	 * Returns a {@code List} of {@link PMethod} with the methods to generate for {@code property}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code PMethod} with the methods to generate for {@code property}
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PMethod> toMethods(final Property property);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated constructor.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated constructor
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toConstructorLines(final Property property);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code copy()}.
	 * <p>
	 * If either {@code property} or {@code nameType} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @param nameType the name of the type
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code copy()}
	 * @throws NullPointerException thrown if, and only if, either {@code property} or {@code nameType} are {@code null}
	 */
	List<String> toMethodCopyLines(final Property property, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code parseArray(array)}.
	 * <p>
	 * If either {@code property}, {@code nameArray} or {@code nameType} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @param nameArray the name of the array
	 * @param nameType the name of the type
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code parseArray(array)}
	 * @throws NullPointerException thrown if, and only if, either {@code property}, {@code nameArray} or {@code nameType} are {@code null}
	 */
	List<String> toMethodParseArrayLines(final Property property, final String nameArray, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code set(Type)}.
	 * <p>
	 * If either {@code property} or {@code nameType} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @param nameType the name of the type
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code set(Type)}
	 * @throws NullPointerException thrown if, and only if, either {@code property} or {@code nameType} are {@code null}
	 */
	List<String> toMethodSetLines(final Property property, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code toArray(bool)}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code toArray(bool)}
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toMethodToArrayLines(final Property property);
	
	/**
	 * Returns {@code true} if, and only if, {@code type} is supported by this {@code PropertyBuilder} instance, {@code false} otherwise.
	 * <p>
	 * If {@code type} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param type the {@link PType} to check support for
	 * @return {@code true} if, and only if, {@code type} is supported by this {@code PropertyBuilder} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code type} is {@code null}
	 */
	boolean isTypeSupported(final PType type);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on a default implementation.
	 * 
	 * @return a {@code PropertyBuilder} instance based on a default implementation
	 */
	static PropertyBuilder newDefault() {
		return new DefaultPropertyBuilder();
	}
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on an implementation that checks for {@code float} values within a range.
	 * 
	 * @param valueA the minimum or maximum value
	 * @param valueB the maximum or minimum value
	 * @return a {@code PropertyBuilder} instance based on an implementation that checks for {@code float} values within a range
	 */
	static PropertyBuilder newFloatRange(final float valueA, final float valueB) {
		return new FloatRangePropertyBuilder(valueA, valueB);
	}
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on an implementation that checks for {@code int} values within a range.
	 * 
	 * @param valueA the minimum or maximum value
	 * @param valueB the maximum or minimum value
	 * @return a {@code PropertyBuilder} instance based on an implementation that checks for {@code int} values within a range
	 */
	static PropertyBuilder newIntRange(final int valueA, final int valueB) {
		return new IntRangePropertyBuilder(valueA, valueB);
	}
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on an implementation that checks for {@code String} values with a length within a range.
	 * 
	 * @param lengthA the minimum or maximum length
	 * @param lengthB the maximum or minimum length
	 * @return a {@code PropertyBuilder} instance based on an implementation that checks for {@code String} values with a length within a range
	 */
	static PropertyBuilder newStringLength(final int lengthA, final int lengthB) {
		return new StringLengthPropertyBuilder(lengthA, lengthB);
	}
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on an implementation that checks that a {@code String} value is one of several options.
	 * <p>
	 * If either {@code stringOptions} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Each {@code Pair} has two values, X and Y. In this case, both are {@code String}s. X represents the code and Y represents the name of the option.
	 * <p>
	 * The code is a {@code String} used in the const of the PHP class. This const is used when checking the value.
	 * <p>
	 * The name is a {@code String} used to generate the name of the const.
	 * 
	 * @param stringOptions a {@code List} of {@code Pair} instances that represents the options a {@code String} may be equal to
	 * @return a {@code PropertyBuilder} instance based on an implementation that checks that a {@code String} value is one of several options
	 * @throws NullPointerException thrown if, and only if, either {@code stringOptions} or any of its elements are {@code null}
	 */
	static PropertyBuilder newStringOption(final List<Pair<String, String>> stringOptions) {
		return new StringOptionPropertyBuilder(stringOptions);
	}
	
	/**
	 * Returns a {@code PropertyBuilder} instance based on an implementation that checks that a {@code String} matches a Regular Expression.
	 * <p>
	 * If {@code pattern} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pattern the pattern of the Regular Expression
	 * @return a {@code PropertyBuilder} instance based on an implementation that checks that a {@code String} matches a Regular Expression
	 * @throws NullPointerException thrown if, and only if, {@code pattern} is {@code null}
	 */
	static PropertyBuilder newStringRegex(final String pattern) {
		return new StringRegexPropertyBuilder(pattern);
	}
}
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

import java.util.List;

import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PType;

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
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PConst> toConsts(final Property property);
	
	/**
	 * Returns a {@code List} of {@link PField} with the fields to generate for {@code property}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code PField} with the fields to generate for {@code property}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PField> toFields(final Property property);
	
	/**
	 * Returns a {@code List} of {@link PMethod} with the methods to generate for {@code property}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code PMethod} with the methods to generate for {@code property}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<PMethod> toMethods(final Property property);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated constructor.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated constructor
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toConstructorLines(final Property property);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code copy()}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code copy()}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toMethodCopyLines(final Property property, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code parseArray(array)}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code parseArray(array)}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toMethodParseArrayLines(final Property property, final String nameArray, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code set(Type)}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code set(Type)}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	List<String> toMethodSetLines(final Property property, final String nameType);
	
	/**
	 * Returns a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code toArray(bool)}.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param property the {@link Property} instance to generate for
	 * @return a {@code List} of {@code String} with the source code lines associated with {@code property} in the generated method {@code toArray(bool)}
	 * @throw NullPointerException thrown if, and only if, {@code property} is {@code null}
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
}
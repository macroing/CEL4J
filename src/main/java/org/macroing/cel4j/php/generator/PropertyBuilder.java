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
import java.util.List;

import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PType;

//TODO: Add Javadocs!
public interface PropertyBuilder {
//	TODO: Add Javadocs!
	List<PConst> toPConsts(final Property property);
	
//	TODO: Add Javadocs!
	List<PField> toPFields(final Property property);
	
//	TODO: Add Javadocs!
	List<PMethod> toPMethods(final Property property);
	
//	TODO: Add Javadocs!
	List<String> toPConstructorLines(final Property property);
	
//	TODO: Add Javadocs!
	List<String> toPMethodCopyLines(final Property property, final String nameType);
	
//	TODO: Add Javadocs!
	List<String> toPMethodParseArrayLines(final Property property, final String nameArray, final String nameType);
	
//	TODO: Add Javadocs!
	List<String> toPMethodSetLines(final Property property, final String nameType);
	
//	TODO: Add Javadocs!
	List<String> toPMethodToArrayLines(final Property property);
	
//	TODO: Add Javadocs!
	boolean isPTypeSupported(final PType pType);
}
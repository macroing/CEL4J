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
package org.macroing.cel4j.java.decompiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Names {
	private static final Pattern PATTERN_FULLY_QUALIFIED_TYPE_NAME = Pattern.compile("(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(\\.(?!(extends|super)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Names() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String filterPackageNames(final JPackageNameFilter jPackageNameFilter, final String string) {
		return filterPackageNames(jPackageNameFilter, string, false);
	}
	
	public static String filterPackageNames(final JPackageNameFilter jPackageNameFilter, final String string, final boolean isInnerType) {
		final Matcher matcher = PATTERN_FULLY_QUALIFIED_TYPE_NAME.matcher(string);
		
		final StringBuffer stringBuffer = new StringBuffer();
		
		while(matcher.find()) {
			final String fullyQualifiedName = matcher.group();
			final String packageName = getPackageName(fullyQualifiedName);
			final String simpleName = getSimpleName(fullyQualifiedName, isInnerType);
			
			if(!jPackageNameFilter.isAccepted(packageName, simpleName)) {
				matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(simpleName));
			}
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	public static String getPackageName(final String fullyQualifiedTypeName) {
		return fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(0, fullyQualifiedTypeName.lastIndexOf(".")) : "";
	}
	
	public static String getSimpleName(final String fullyQualifiedTypeName, final boolean isInnerType) {
		final String simpleName0 = fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(fullyQualifiedTypeName.lastIndexOf(".") + 1) : fullyQualifiedTypeName;
		final String simpleName1 = isInnerType && simpleName0.lastIndexOf('$') >= 0 ? simpleName0.substring(simpleName0.lastIndexOf('$') + 1) : simpleName0;
		
		return simpleName1;
	}
}
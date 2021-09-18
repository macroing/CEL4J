/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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

import java.util.List;
import java.util.Objects;

interface JPackageNameFilter {
	boolean isAccepted(final String packageName, final String simpleName);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static JPackageNameFilter newUnnecessaryPackageName(final String packageName, final boolean isDiscardingUnnecessaryPackageNames, final List<Type> typesToImport, final boolean isImportingTypes) {
		Objects.requireNonNull(packageName, "packageName == null");
		
		return (packageName0, simpleName) -> {
			if(isDiscardingUnnecessaryPackageNames) {
				if(packageName0.equals(packageName) || packageName0.equals("java.lang")) {
					return false;
				}
				
				if(isImportingTypes) {
					for(final Type typeToImport : typesToImport) {
						if(packageName0.equals(typeToImport.getPackageName())) {
							return false;
						}
					}
				}
			}
			
			return true;
		};
	}
}
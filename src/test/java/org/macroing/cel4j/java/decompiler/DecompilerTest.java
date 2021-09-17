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

public final class DecompilerTest {
	private DecompilerTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final
		Decompiler decompiler = Decompiler.newInstance();
		decompiler.getDecompilerConfiguration().setAnnotatingDeprecatedMethods(true);
		decompiler.getDecompilerConfiguration().setAnnotatingOverriddenMethods(true);
		decompiler.getDecompilerConfiguration().setDiscardingAbstractInterfaceMethodModifier(true);
		decompiler.getDecompilerConfiguration().setDiscardingExtendsObject(true);
		decompiler.getDecompilerConfiguration().setDiscardingPublicInterfaceMethodModifier(true);
		decompiler.getDecompilerConfiguration().setDiscardingUnnecessaryPackageNames(true);
		decompiler.getDecompilerConfiguration().setDisplayingAttributeInfos(true);
		decompiler.getDecompilerConfiguration().setDisplayingConfigurationParameters(true);
		decompiler.getDecompilerConfiguration().setDisplayingInstructions(false);
		decompiler.getDecompilerConfiguration().setImportingTypes(true);
		decompiler.getDecompilerConfiguration().setSeparatingGroups(true);
		decompiler.getDecompilerConfiguration().setSortingGroups(true);
		decompiler.addClass(Person.class);
		decompiler.decompile();
	}
}
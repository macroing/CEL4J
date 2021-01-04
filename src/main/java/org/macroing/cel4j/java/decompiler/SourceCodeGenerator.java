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

import java.util.Objects;

import org.macroing.cel4j.util.Document;

final class SourceCodeGenerator {
	private final DecompilerConfiguration decompilerConfiguration;
	private final Document document;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public SourceCodeGenerator(final DecompilerConfiguration decompilerConfiguration) {
		this.decompilerConfiguration = Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		this.document = new Document();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return this.document.toString();
	}
	
	public void generate(final JType jType) {
		if(this.decompilerConfiguration.isDisplayingConfigurationParameters()) {
			doGenerateTraditionalComment(doGenerateTraditionalCommentAtTop(jType));
		}
		
		if(jType instanceof JAnnotation) {
			JAnnotation.class.cast(jType).decompile(this.decompilerConfiguration, this.document);
		} else if(jType instanceof JClass) {
			JClass.class.cast(jType).decompile(this.decompilerConfiguration, this.document);
		} else if(jType instanceof JEnum) {
			JEnum.class.cast(jType).decompile(this.decompilerConfiguration, this.document);
		} else if(jType instanceof JInterface) {
			JInterface.class.cast(jType).decompile(this.decompilerConfiguration, this.document);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doGenerateTraditionalCommentAtTop(final JType jType) {
		final DecompilerConfiguration decompilerConfiguration = this.decompilerConfiguration;
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%s decompiled by CEL4J Java Decompiler.", jType.getName()));
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<AnnotatingDeprecatedMethods>: %s", Boolean.toString(decompilerConfiguration.isAnnotatingDeprecatedMethods())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<AnnotatingOverriddenMethods>: %s", Boolean.toString(decompilerConfiguration.isAnnotatingOverriddenMethods())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingAbstractInterfaceMethodModifier>: %s", Boolean.toString(decompilerConfiguration.isDiscardingAbstractInterfaceMethodModifier())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingExtendsObject>: %s", Boolean.toString(decompilerConfiguration.isDiscardingExtendsObject())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingPublicInterfaceMethodModifier>: %s", Boolean.toString(decompilerConfiguration.isDiscardingPublicInterfaceMethodModifier())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DiscardingUnnecessaryPackageNames>: %s", Boolean.toString(decompilerConfiguration.isDiscardingUnnecessaryPackageNames())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingAttributeInfos>: %s", Boolean.toString(decompilerConfiguration.isDisplayingAttributeInfos())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingConfigurationParameters>: %s", Boolean.toString(decompilerConfiguration.isDisplayingConfigurationParameters())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<DisplayingInstructions>: %s", Boolean.toString(decompilerConfiguration.isDisplayingInstructions())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<ImportingTypes>: %s", Boolean.toString(decompilerConfiguration.isImportingTypes())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<SeparatingGroups>: %s", Boolean.toString(decompilerConfiguration.isSeparatingGroups())));
		stringBuilder.append("\n");
		stringBuilder.append(String.format("<SortingGroups>: %s", Boolean.toString(decompilerConfiguration.isSortingGroups())));
		
		return stringBuilder.toString();
	}
	
	private void doGenerateTraditionalComment(final String text) {
		final String[] lines = text.split("\n");
		
		final
		Document document = this.document;
		document.line("/*");
		
		for(final String line : lines) {
			document.linef(" * %s", line);
		}
		
		document.line(" */");
	}
}
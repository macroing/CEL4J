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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;

final class DecompilerImpl extends AbstractDecompiler {
	private final Map<Class<?>, Consumer<String>> classes = new LinkedHashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DecompilerImpl() {
		this(Consumers.print());
	}
	
	public DecompilerImpl(final Consumer<String> defaultSourceConsumer) {
		super(defaultSourceConsumer);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void addClass(final Class<?> clazz, final Consumer<String> sourceConsumer) {
		this.classes.put(Objects.requireNonNull(clazz, "clazz == null"), Objects.requireNonNull(sourceConsumer, "sourceConsumer == null"));
	}
	
	@Override
	public void decompile() {
		try {
			for(final JClassInfo jClassInfo : doCreateJClassInfos()) {
				final Consumer<String> sourceConsumer = jClassInfo.getSourceConsumer();
				
				final JType jType = jClassInfo.getJType();
				
				notifyOfProgress("Decompiling " + jType.getName() + "...");
				
				final
				SourceCodeGenerator sourceCodeGenerator = new SourceCodeGenerator(getDecompilerConfiguration());
				sourceCodeGenerator.generate(jType);
				
				final String source = sourceCodeGenerator.toString();
				
				sourceConsumer.accept(source);
			}
		} catch(final Exception e) {
			throw new DecompilationException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<JClassInfo> doCreateJClassInfos() {
		final List<JClassInfo> jClassInfos = new ArrayList<>();
		
		for(final Entry<Class<?>, Consumer<String>> entry : this.classes.entrySet()) {
			final Consumer<String> sourceConsumer = entry.getValue();
			
			final Class<?> clazz = entry.getKey();
			
			jClassInfos.add(new JClassInfo(sourceConsumer, clazz));
		}
		
		return jClassInfos;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class JClassInfo {
		private final Consumer<String> sourceConsumer;
		private final JType jType;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public JClassInfo(final Consumer<String> sourceConsumer, final Class<?> clazz) {
			this(sourceConsumer, JType.valueOf(clazz));
		}
		
		public JClassInfo(final Consumer<String> sourceConsumer, final JType jType) {
			this.sourceConsumer = Objects.requireNonNull(sourceConsumer, "sourceConsumer == null");
			this.jType = Objects.requireNonNull(jType, "jType == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Consumer<String> getSourceConsumer() {
			return this.sourceConsumer;
		}
		
		public JType getJType() {
			return this.jType;
		}
	}
}
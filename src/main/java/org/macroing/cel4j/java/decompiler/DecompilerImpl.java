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
package org.macroing.cel4j.java.decompiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.cel4j.java.model.Type;

final class DecompilerImpl extends AbstractDecompiler {
	private final Map<Class<?>, Consumer<String>> classes;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DecompilerImpl() {
		this(Consumers.print());
	}
	
	public DecompilerImpl(final Consumer<String> defaultSourceConsumer) {
		super(defaultSourceConsumer);
		
		this.classes = new LinkedHashMap<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void addClass(final Class<?> clazz, final Consumer<String> sourceConsumer) {
		this.classes.put(Objects.requireNonNull(clazz, "clazz == null"), Objects.requireNonNull(sourceConsumer, "sourceConsumer == null"));
	}
	
	@Override
	public void decompile() {
		try {
			for(final ClassInfo classInfo : doCreateClassInfos()) {
				final Consumer<String> sourceConsumer = classInfo.getSourceConsumer();
				
				final Type type = classInfo.getType();
				
				notifyOfProgress("Decompiling " + type.getExternalName() + "...");
				
				final
				SourceCodeGenerator sourceCodeGenerator = new SourceCodeGenerator(getDecompilerConfiguration());
				sourceCodeGenerator.generate(type);
				
				final String source = sourceCodeGenerator.toString();
				
				sourceConsumer.accept(source);
			}
		} catch(final Exception e) {
			throw new DecompilationException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<ClassInfo> doCreateClassInfos() {
		final List<ClassInfo> classInfos = new ArrayList<>();
		
		for(final Entry<Class<?>, Consumer<String>> entry : this.classes.entrySet()) {
			final Consumer<String> sourceConsumer = entry.getValue();
			
			final Class<?> clazz = entry.getKey();
			
			classInfos.add(new ClassInfo(sourceConsumer, clazz));
		}
		
		return classInfos;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class ClassInfo {
		private final Consumer<String> sourceConsumer;
		private final Type type;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public ClassInfo(final Consumer<String> sourceConsumer, final Class<?> clazz) {
			this(sourceConsumer, Type.valueOf(clazz));
		}
		
		public ClassInfo(final Consumer<String> sourceConsumer, final Type type) {
			this.sourceConsumer = Objects.requireNonNull(sourceConsumer, "sourceConsumer == null");
			this.type = Objects.requireNonNull(type, "type == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Consumer<String> getSourceConsumer() {
			return this.sourceConsumer;
		}
		
		public Type getType() {
			return this.type;
		}
	}
}
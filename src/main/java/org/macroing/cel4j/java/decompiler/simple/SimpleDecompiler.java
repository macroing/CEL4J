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
package org.macroing.cel4j.java.decompiler.simple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.cel4j.java.decompiler.AbstractDecompiler;
import org.macroing.cel4j.java.decompiler.Consumers;
import org.macroing.cel4j.java.decompiler.DecompilationException;
import org.macroing.cel4j.java.decompiler.Decompiler;

/**
 * A {@code SimpleDecompiler} is a simple {@link Decompiler} implementation.
 * <p>
 * To use this class, consider the following example, that will print the decompiled source code to standard out:
 * <pre>
 * Decompiler decompiler = new SimpleDecompiler();
 * decompiler.addClass(Integer.class);
 * decompiler.decompile();
 * </pre>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SimpleDecompiler extends AbstractDecompiler {
	private final Map<Class<?>, Consumer<String>> classes = new LinkedHashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SimpleDecompiler} instance.
	 * <p>
	 * Calling this constructor is equivalent to calling {@code new SimpleDecompiler(Consumers.print())}.
	 */
	public SimpleDecompiler() {
		this(Consumers.print());
	}
	
	/**
	 * Constructs a new {@code SimpleDecompiler} instance.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param defaultSourceConsumer the default source {@code Consumer<String>} to use
	 * @throws NullPointerException thrown if, and only if, {@code defaultSourceConsumer} is {@code null}
	 */
	public SimpleDecompiler(final Consumer<String> defaultSourceConsumer) {
		super(defaultSourceConsumer);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the given {@code Class<?>} to be decompiled and the source {@code Consumer<String>} to hand its decompiled source code.
	 * <p>
	 * If either {@code clazz} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param clazz the {@code Class<?>} to add
	 * @param sourceConsumer the source {@code Consumer<String>} to hand the decompiled source code to
	 * @throws NullPointerException thrown if, and only if, either {@code clazz} or {@code sourceConsumer} are {@code null}
	 */
	@Override
	public void addClass(final Class<?> clazz, final Consumer<String> sourceConsumer) {
		this.classes.put(Objects.requireNonNull(clazz, "clazz == null"), Objects.requireNonNull(sourceConsumer, "sourceConsumer == null"));
	}
	
	/**
	 * Decompiles the class- and interface-types that were added to this {@code SimpleDecompiler} instance.
	 * <p>
	 * The methods to add the class- and interface-types are the following:
	 * <ul>
	 * <li>{@link #addClass(Class)}</li>
	 * <li>{@link #addClass(Class, Consumer)}</li>
	 * <li>{@link #addClass(String)}</li>
	 * <li>{@link #addClass(String, Consumer)}</li>
	 * </ul>
	 * When the decompilation process is finished, the source code of the decompiled class- and interface-types will be handed to the source {@code Consumer<String>} instances that were given to each individual class- and
	 * interface-type, respectively.
	 * <p>
	 * If the decompilation process fails, a {@link DecompilationException} will be thrown.
	 * 
	 * @throws DecompilationException thrown if, and only if, the decompilation process fails
	 */
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
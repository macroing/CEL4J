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

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@code Decompiler} decompiles Java bytecode into Java source code.
 * <p>
 * Implementations of this interface may or may not be thread-safe.
 * <p>
 * To use a {@code Decompiler}, consider the following example, that will print the decompiled source code to standard out:
 * <pre>
 * Decompiler decompiler = Decompiler.newInstance();
 * decompiler.addClass(Integer.class);
 * decompiler.decompile();
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface Decompiler {
	/**
	 * Returns the currently assigned default source {@code Consumer<String>}.
	 * <p>
	 * If no default source {@code Consumer<String>} has been explicitly set by the user of this API, the initial default source {@code Consumer<String>} will print the source to standard out via a call to
	 * {@code System.out.println(source)}.
	 * <p>
	 * This method will not return {@code null}.
	 * 
	 * @return the currently assigned default source {@code Consumer<String>}
	 */
	Consumer<String> getDefaultSourceConsumer();
	
	/**
	 * Returns the {@link DecompilerConfiguration} used by this {@code Decompiler} instance.
	 * 
	 * @return the {@code DecompilerConfiguration} used by this {@code Decompiler} instance
	 */
	DecompilerConfiguration getDecompilerConfiguration();
	
	/**
	 * Returns a {@code List} will all currently added {@link DecompilerObserver} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Decompiler} instance.
	 * 
	 * @return a {@code List} will all currently added {@code DecompilerObserver} instances
	 */
	List<DecompilerObserver> getDecompilerObservers();
	
	/**
	 * Adds the given {@code Class<?>} to be decompiled.
	 * <p>
	 * Calling this method is equivalent to calling {@code decompiler.addClass(clazz, decompiler.getDefaultSourceConsumer())}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param clazz the {@code Class<?>} to add
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	void addClass(final Class<?> clazz);
	
	/**
	 * Adds the given {@code Class<?>} to be decompiled and the source {@code Consumer<String>} to hand its decompiled source code.
	 * <p>
	 * If either {@code clazz} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param clazz the {@code Class<?>} to add
	 * @param sourceConsumer the source {@code Consumer<String>} to hand the decompiled source code to
	 * @throws NullPointerException thrown if, and only if, either {@code clazz} or {@code sourceConsumer} are {@code null}
	 */
	void addClass(final Class<?> clazz, final Consumer<String> sourceConsumer);
	
	/**
	 * Adds the given {@code Class<?>} to be decompiled, given its fully qualified name.
	 * <p>
	 * Calling this method is equivalent to calling {@code decompiler.addClass(className, decompiler.getDefaultSourceConsumer())}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no such class- or interface-type can be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @param className the fully qualified name of the class- or interface-type to find
	 * @throws ClassNotFoundException thrown if, and only if, no such class- or interface-type can be found
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 */
	void addClass(final String className) throws ClassNotFoundException;
	
	/**
	 * Adds the given {@code Class<?>} to be decompiled, given its fully qualified name and the source {@code Consumer<String>} to hand its decompiled source code.
	 * <p>
	 * Calling this method is equivalent to calling {@code decompiler.addClass(Class.forName(className), sourceConsumer)}.
	 * <p>
	 * If either {@code className} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no such class- or interface-type can be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @param className the fully qualified name of the class- or interface-type to find
	 * @param sourceConsumer the source {@code Consumer<String>} to hand the decompiled source code to
	 * @throws ClassNotFoundException thrown if, and only if, no such class- or interface-type can be found
	 * @throws NullPointerException thrown if, and only if, either {@code className} or {@code sourceConsumer} are {@code null}
	 */
	void addClass(final String className, final Consumer<String> sourceConsumer) throws ClassNotFoundException;
	
	/**
	 * Adds {@code decompilerObserver} to this {@code Decompiler} instance, if absent.
	 * <p>
	 * If {@code decompilerObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerObserver the {@link DecompilerObserver} to add
	 * @throws NullPointerException thrown if, and only if, {@code decompilerObserver} is {@code null}
	 */
	void addDecompilerObserver(final DecompilerObserver decompilerObserver);
	
	/**
	 * Adds the file represented by {@code file} to the class-path.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param file the {@code File} representing the file to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 */
	void addToClassPath(final File file);
	
	/**
	 * Adds the files represented by {@code files} to the class-path.
	 * <p>
	 * If {@code files} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param files the {@code List} of {@code File}s representing the files to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code files} or any of its elements are {@code null}
	 */
	void addToClassPath(final List<File> files);
	
	/**
	 * Adds the file represented by {@code filename} to the class-path.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param filename the filename representing the file to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 */
	void addToClassPath(final String filename);
	
	/**
	 * Decompiles the class- and interface-types that were added to this {@code Decompiler} instance.
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
	void decompile();
	
	/**
	 * Removes {@code decompilerObserver} from this {@code Decompiler} instance, if present.
	 * <p>
	 * If {@code decompilerObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerObserver the {@link DecompilerObserver} to remove
	 * @throws NullPointerException thrown if, and only if, {@code decompilerObserver} is {@code null}
	 */
	void removeDecompilerObserver(final DecompilerObserver decompilerObserver);
	
	/**
	 * Sets a new default source {@code Consumer<String>} for this {@code Decompiler} instance.
	 * <p>
	 * If no default source {@code Consumer<String>} has been explicitly set by the user of this API, the initial default source {@code Consumer<String>} will print the source to standard out via a call to
	 * {@code System.out.println(source)}.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param defaultSourceConsumer the new default source {@code Consumer<String>}
	 * @throws NullPointerException thrown if, and only if, {@code defaultSourceConsumer} is {@code null}
	 */
	void setDefaultSourceConsumer(final Consumer<String> defaultSourceConsumer);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Decompiler} instance.
	 * <p>
	 * The {@code Decompiler} instance returned by this method is not thread-safe.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Decompiler.newInstance(Consumers.print());
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Decompiler} instance
	 */
	static Decompiler newInstance() {
		return newInstance(Consumers.print());
	}
	
	/**
	 * Returns a new {@code Decompiler} instance.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code Decompiler} instance returned by this method is not thread-safe.
	 * 
	 * @param defaultSourceConsumer the default source {@code Consumer<String>} to use
	 * @return a new {@code Decompiler} instance
	 * @throws NullPointerException thrown if, and only if, {@code defaultSourceConsumer} is {@code null}
	 */
	static Decompiler newInstance(final Consumer<String> defaultSourceConsumer) {
		return new DecompilerImpl(Objects.requireNonNull(defaultSourceConsumer, "defaultSourceConsumer == null"));
	}
}
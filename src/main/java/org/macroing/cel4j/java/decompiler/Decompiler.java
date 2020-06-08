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
 * To use a {@code Decompiler}, consider the following example, that will print the decompiled source code to standard output:
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
	 * Returns a {@code Consumer} of type {@code String} that represents the currently assigned default source consumer.
	 * <p>
	 * If no default source consumer has been explicitly set by the user of this API, the initial default source consumer will print the source to standard output via a call to {@code System.out.println(String)}.
	 * <p>
	 * This method will not return {@code null}.
	 * 
	 * @return a {@code Consumer} of type {@code String} that represents the currently assigned default source consumer
	 */
	Consumer<String> getDefaultSourceConsumer();
	
	/**
	 * Returns the {@link DecompilerConfiguration} used by this {@code Decompiler} instance.
	 * 
	 * @return the {@code DecompilerConfiguration} used by this {@code Decompiler} instance
	 */
	DecompilerConfiguration getDecompilerConfiguration();
	
	/**
	 * Returns a {@code List} with all currently added {@link DecompilerObserver} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Decompiler} instance.
	 * 
	 * @return a {@code List} with all currently added {@code DecompilerObserver} instances
	 */
	List<DecompilerObserver> getDecompilerObservers();
	
	/**
	 * Adds {@code clazz} for decompilation.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * decompiler.addClass(clazz, decompiler.getDefaultSourceConsumer());
	 * }
	 * </pre>
	 * 
	 * @param clazz the {@code Class} to add for decompilation
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	void addClass(final Class<?> clazz);
	
	/**
	 * Adds {@code clazz} for decompilation with {@code sourceConsumer} as the consumer of the decompiled source code.
	 * <p>
	 * If either {@code clazz} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param clazz the {@code Class} to add for decompilation
	 * @param sourceConsumer a {@code Consumer} of type {@code String} that will consume the decompiled source code
	 * @throws NullPointerException thrown if, and only if, either {@code clazz} or {@code sourceConsumer} are {@code null}
	 */
	void addClass(final Class<?> clazz, final Consumer<String> sourceConsumer);
	
	/**
	 * Adds {@code Class.forName(className)} for decompilation.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails, a {@code ClassNotFoundException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * decompiler.addClass(className, decompiler.getDefaultSourceConsumer());
	 * }
	 * </pre>
	 * 
	 * @param className the fully qualified name of a {@code Class}
	 * @throws ClassNotFoundException thrown if, and only if, {@code Class.forName(className)} fails
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 */
	void addClass(final String className) throws ClassNotFoundException;
	
	/**
	 * Adds {@code Class.forName(className)} for decompilation with {@code sourceConsumer} as the consumer of the decompiled source code.
	 * <p>
	 * If either {@code className} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails, a {@code ClassNotFoundException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following, assuming that {@code className} is not {@code null}:
	 * <pre>
	 * {@code
	 * decompiler.addClass(Class.forName(className), sourceConsumer);
	 * }
	 * </pre>
	 * 
	 * @param className the fully qualified name of a {@code Class}
	 * @param sourceConsumer a {@code Consumer} of type {@code String} that will consume the decompiled source code
	 * @throws ClassNotFoundException thrown if, and only if, {@code Class.forName(className)} fails
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
	 * @param files a {@code List} of type {@code File} that represents the files to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code files} or any of its elements are {@code null}
	 */
	void addToClassPath(final List<File> files);
	
	/**
	 * Adds the file represented by {@code filename} to the class-path.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param filename the filename of the file to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 */
	void addToClassPath(final String filename);
	
	/**
	 * Decompiles the {@code Class} instances that were added to this {@code Decompiler} instance for decompilation.
	 * <p>
	 * The methods to add {@code Class} instances for decompilation, are the following:
	 * <ul>
	 * <li>{@link #addClass(Class)}</li>
	 * <li>{@link #addClass(Class, Consumer)}</li>
	 * <li>{@link #addClass(String)}</li>
	 * <li>{@link #addClass(String, Consumer)}</li>
	 * </ul>
	 * When the decompilation process is finished, the source code of the decompiled {@code Class} instances will be consumed by the source consumers that were added along with them.
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
	 * Sets a new default source consumer for this {@code Decompiler} instance.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no default source consumer has been explicitly set by the user of this API, the initial default source consumer will print the source to standard output via a call to {@code System.out.println(String)}.
	 * 
	 * @param defaultSourceConsumer a {@code Consumer} of type {@code String} that will consume the decompiled source code by default
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
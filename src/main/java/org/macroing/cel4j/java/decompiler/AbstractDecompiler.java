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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * An {@code AbstractDecompiler} is an abstract {@link Decompiler} implementation for convenience only.
 * <p>
 * By using this class you only have to override the methods {@link #addClass(Class, Consumer)} and {@link #decompile()}.
 * <p>
 * This class is thread-safe. But this may not be the case for all sub-classes extending it.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class AbstractDecompiler implements Decompiler {
	private final AtomicReference<Consumer<String>> defaultSourceConsumer;
	private final CopyOnWriteArrayList<DecompilerObserver> decompilerObservers;
	private final DecompilerConfiguration decompilerConfiguration;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AbstractDecompiler} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * super(Consumers.print());
	 * }
	 * </pre>
	 */
	protected AbstractDecompiler() {
		this(Consumers.print());
	}
	
	/**
	 * Constructs a new {@code AbstractDecompiler} instance.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param defaultSourceConsumer a {@code Consumer} of type {@code String} that will consume the decompiled source code by default
	 * @throws NullPointerException thrown if, and only if, {@code defaultSourceConsumer} is {@code null}
	 */
	protected AbstractDecompiler(final Consumer<String> defaultSourceConsumer) {
		this.defaultSourceConsumer = new AtomicReference<>(Objects.requireNonNull(defaultSourceConsumer, "defaultSourceConsumer == null"));
		this.decompilerObservers = new CopyOnWriteArrayList<>();
		this.decompilerConfiguration = new DecompilerConfiguration();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Consumer} of type {@code String} that represents the currently assigned default source consumer.
	 * <p>
	 * If no default source consumer has been explicitly set by the user of this API, the initial default source consumer will print the source to standard output via a call to {@code System.out.println(String)}.
	 * <p>
	 * This method will not return {@code null}.
	 * 
	 * @return a {@code Consumer} of type {@code String} that represents the currently assigned default source consumer
	 */
	@Override
	public final Consumer<String> getDefaultSourceConsumer() {
		return this.defaultSourceConsumer.get();
	}
	
	/**
	 * Returns the {@link DecompilerConfiguration} used by this {@code AbstractDecompiler} instance.
	 * 
	 * @return the {@code DecompilerConfiguration} used by this {@code AbstractDecompiler} instance
	 */
	@Override
	public final DecompilerConfiguration getDecompilerConfiguration() {
		return this.decompilerConfiguration;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link DecompilerObserver} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code AbstractDecompiler} instance.
	 * 
	 * @return a {@code List} with all currently added {@code DecompilerObserver} instances
	 */
	@Override
	public final List<DecompilerObserver> getDecompilerObservers() {
		return new ArrayList<>(this.decompilerObservers);
	}
	
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
	@Override
	public final void addClass(final Class<?> clazz) {
		addClass(clazz, getDefaultSourceConsumer());
	}
	
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
	@Override
	public final void addClass(final String className) throws ClassNotFoundException {
		addClass(className, getDefaultSourceConsumer());
	}
	
	/**
	 * Adds {@code Class.forName(className)} for decompilation with {@code sourceConsumer} as the consumer of the decompiled source code.
	 * <p>
	 * If either {@code className} or {@code sourceConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails, a {@code ClassNotFoundException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
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
	@Override
	public final void addClass(final String className, final Consumer<String> sourceConsumer) throws ClassNotFoundException {
		addClass(Class.forName(Objects.requireNonNull(className, "className == null")), sourceConsumer);
	}
	
	/**
	 * Adds {@code decompilerObserver} to this {@code AbstractDecompiler} instance, if absent.
	 * <p>
	 * If {@code decompilerObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerObserver the {@link DecompilerObserver} to add
	 * @throws NullPointerException thrown if, and only if, {@code decompilerObserver} is {@code null}
	 */
	@Override
	public final void addDecompilerObserver(final DecompilerObserver decompilerObserver) {
		this.decompilerObservers.addIfAbsent(Objects.requireNonNull(decompilerObserver, "decompilerObserver == null"));
	}
	
	/**
	 * Adds the file represented by {@code file} to the class-path.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param file the {@code File} representing the file to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 */
	@Override
	public final void addToClassPath(final File file) {
		addToClassPath(Arrays.asList(Objects.requireNonNull(file, "file == null")));
	}
	
	/**
	 * Adds the files represented by {@code files} to the class-path.
	 * <p>
	 * If {@code files} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param files a {@code List} of type {@code File} that represents the files to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code files} or any of its elements are {@code null}
	 */
	@Override
	public final void addToClassPath(final List<File> files) {
		try {
			for(final File file : files) {
				final Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class<?>[]{URL.class});
				
				final boolean isAccessible = method.isAccessible();
				
				method.setAccessible(true);
				method.invoke(URLClassLoader.class.cast(ClassLoader.getSystemClassLoader()), new Object[]{file.toURI().toURL()});
				method.setAccessible(isAccessible);
			}
		} catch(final IllegalAccessException | InvocationTargetException | MalformedURLException | NoSuchMethodException e) {
//			Do nothing!
		}
	}
	
	/**
	 * Adds the file represented by {@code filename} to the class-path.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param filename the filename of the file to add to the class-path
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 */
	@Override
	public final void addToClassPath(final String filename) {
		addToClassPath(new File(Objects.requireNonNull(filename, "filename == null")));
	}
	
	/**
	 * Removes {@code decompilerObserver} from this {@code AbstractDecompiler} instance, if present.
	 * <p>
	 * If {@code decompilerObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerObserver the {@link DecompilerObserver} to remove
	 * @throws NullPointerException thrown if, and only if, {@code decompilerObserver} is {@code null}
	 */
	@Override
	public final void removeDecompilerObserver(final DecompilerObserver decompilerObserver) {
		this.decompilerObservers.remove(Objects.requireNonNull(decompilerObserver, "decompilerObserver == null"));
	}
	
	/**
	 * Sets a new default source consumer for this {@code AbstractDecompiler} instance.
	 * <p>
	 * If {@code defaultSourceConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no default source consumer has been explicitly set by the user of this API, the initial default source consumer will print the source to standard output via a call to {@code System.out.println(String)}.
	 * 
	 * @param defaultSourceConsumer a {@code Consumer} of type {@code String} that will consume the decompiled source code by default
	 * @throws NullPointerException thrown if, and only if, {@code defaultSourceConsumer} is {@code null}
	 */
	@Override
	public final void setDefaultSourceConsumer(final Consumer<String> defaultSourceConsumer) {
		this.defaultSourceConsumer.set(Objects.requireNonNull(defaultSourceConsumer, "defaultSourceConsumer == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Notifies all currently added {@link DecompilerObserver}s with progress.
	 * <p>
	 * If either {@code format} or {@code objects} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param format the format to use
	 * @param objects an array with {@code Object}s to use in the formatted progress message
	 * @throws NullPointerException thrown if, and only if, either {@code format} or {@code objects} are {@code null}
	 */
	protected final void notifyOfProgress(final String format, final Object... objects) {
		final String progress = String.format(Objects.requireNonNull(format, "format == null"), Objects.requireNonNull(objects, "objects == null"));
		
		this.decompilerObservers.forEach(decompilerObserver -> decompilerObserver.onProgress(progress));
	}
}
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

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A class that consists exclusively of static methods that return {@code Consumer}s of type {@code String} and can be used with this Java Decompiler API.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Consumers {
	private Consumers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Consumer} that will write the source code to the file represented by {@code file}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs while writing to the file, an {@code UncheckedIOException} will be thrown. Calling this method will, however, not throw an {@code UncheckedIOException}.
	 * 
	 * @param file a {@code File} denoting the file to write the source code to
	 * @return a {@code Consumer} that will write the source code to the file represented by {@code file}
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 */
	public static Consumer<String> file(final File file) {
		Objects.requireNonNull(file, "file == null");
		
		return source -> {
			file.getParentFile().mkdirs();
			
			try {
				Files.write(file.toPath(), source.getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			} catch(final IOException e) {
				throw new UncheckedIOException(e);
			}
		};
	}
	
	/**
	 * Returns a {@code Consumer} that will write the source code to a file in the root directory represented by {@code file}.
	 * <p>
	 * If either {@code file} or {@code clazz} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The package name of {@code clazz} will be used in the path to the file.
	 * <p>
	 * If an I/O-error occurs while writing to the file, an {@code UncheckedIOException} will be thrown. Calling this method will, however, not throw an {@code UncheckedIOException}.
	 * 
	 * @param file the root directory to write the file to
	 * @param clazz a {@code Class}
	 * @return a {@code Consumer} that will write the source code to a file in the root directory represented by {@code file}
	 * @throws NullPointerException thrown if, and only if, either {@code file} or {@code clazz} are {@code null}
	 */
	public static Consumer<String> file(final File file, final Class<?> clazz) {
		return file(new File(Objects.requireNonNull(file, "file == null"), clazz.getName().replace('.', File.separatorChar) + ".java"));
	}
	
	/**
	 * Returns a {@code Consumer} that will write the source code to the file represented by the filename given by {@code filename}.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs while writing to the file, an {@code UncheckedIOException} will be thrown. Calling this method will, however, not throw an {@code UncheckedIOException}.
	 * <p>
	 * Calling this method is equivalent to the following, assuming that {@code filename} is not {@code null}:
	 * <pre>
	 * {@code
	 * Consumers.file(new File(filename));
	 * }
	 * </pre>
	 * 
	 * @param filename the filename of the file to write the source code to
	 * @return a {@code Consumer} that will write the source code to the file represented by the filename given by {@code filename}
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 */
	public static Consumer<String> file(final String filename) {
		return file(new File(Objects.requireNonNull(filename, "filename == null")));
	}
	
	/**
	 * Returns a {@code Consumer} that will write the source code to a file in the root directory represented by {@code filename}.
	 * <p>
	 * If either {@code filename} or {@code clazz} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The package name of {@code clazz} will be used in the path to the file.
	 * <p>
	 * If an I/O-error occurs while writing to the file, an {@code UncheckedIOException} will be thrown. Calling this method will, however, not throw an {@code UncheckedIOException}.
	 * 
	 * @param filename the root directory to write the file to
	 * @param clazz a {@code Class}
	 * @return a {@code Consumer} that will write the source code to a file in the root directory represented by {@code filename}
	 * @throws NullPointerException thrown if, and only if, either {@code filename} or {@code clazz} are {@code null}
	 */
	public static Consumer<String> file(final String filename, final Class<?> clazz) {
		return file(new File(Objects.requireNonNull(filename, "filename == null"), clazz.getName().replace('.', '/') + ".java"));
	}
	
	/**
	 * Returns a {@code Consumer} that will write the source code to a file in the root directory represented by {@code filename}.
	 * <p>
	 * If either {@code filename} or {@code className} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails, a {@code ClassNotFoundException} will be thrown.
	 * <p>
	 * The package name of {@code Class.forName(className)} will be used in the path to the file.
	 * <p>
	 * If an I/O-error occurs while writing to the file, an {@code UncheckedIOException} will be thrown. Calling this method will, however, not throw an {@code UncheckedIOException}.
	 * <p>
	 * Calling this method is equivalent to the following, assuming that {@code className} is not {@code null}:
	 * <pre>
	 * {@code
	 * Consumers.file(filename, Class.forName(className));
	 * }
	 * </pre>
	 * 
	 * @param filename the root directory to write the file to
	 * @param className the name of a class
	 * @return a {@code Consumer} that will write the source code to a file in the root directory represented by {@code filename}
	 * @throws ClassNotFoundException thrown if, and only if, {@code Class.forName(className)} fails
	 * @throws NullPointerException thrown if, and only if, either {@code filename} or {@code className} are {@code null}
	 */
	public static Consumer<String> file(final String filename, final String className) throws ClassNotFoundException {
		return file(filename, Class.forName(Objects.requireNonNull(className, "className == null")));
	}
	
	/**
	 * Returns a {@code Consumer} that will print the source code to standard output.
	 * <p>
	 * The source code will be printed by calling {@code System.out.println(String)}.
	 * 
	 * @return a {@code Consumer} that will print the source code to standard output
	 */
	public static Consumer<String> print() {
		return source -> System.out.println(source);
	}
}
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFormatException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * An {@code EnumType} is a {@link Type} implementation that represents an enum type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class EnumType extends Type {
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	private static final Map<String, EnumType> ENUM_TYPES = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitialized;
	private final ClassFile classFile;
	private final List<Modifier> modifiers;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private EnumType(final ClassFile classFile) {
		this.hasInitialized = new AtomicBoolean();
		this.classFile = classFile;
		this.modifiers = new ArrayList<>();
		this.name = ClassName.parseClassNameThisClass(this.classFile).toExternalForm();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code EnumType} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code EnumType} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
	}
	
	/**
	 * Decompiles this {@code EnumType} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * enumType.decompile(new DecompilerConfiguration());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Document} instance
	 */
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	/**
	 * Decompiles this {@code EnumType} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * If {@code decompilerConfiguration} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * enumType.decompile(decompilerConfiguration, new Document());
	 * }
	 * </pre>
	 * 
	 * @param decompilerConfiguration a {@link DecompilerConfiguration} instance
	 * @return a {@code Document} instance
	 * @throws NullPointerException thrown if, and only if, {@code decompilerConfiguration} is {@code null}
	 */
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	/**
	 * Decompiles this {@code EnumType} instance.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If either {@code decompilerConfiguration} or {@code document} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerConfiguration a {@link DecompilerConfiguration} instance
	 * @param document a {@link Document} instance
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, either {@code decompilerConfiguration} or {@code document} are {@code null}
	 */
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final String packageName = getPackageName();
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String simpleName = getSimpleName();
		
		document.linef("package %s;", packageName);
		document.linef("");
		document.linef("%senum %s {", modifiers, simpleName);
		document.indent();
		document.outdent();
		document.linef("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code EnumType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code EnumType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code EnumType} instance
	 */
	public List<Modifier> getModifiers() {
		return new ArrayList<>(this.modifiers);
	}
	
	/**
	 * Returns the name of this {@code EnumType} instance.
	 * 
	 * @return the name of this {@code EnumType} instance
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code EnumType} instance.
	 * 
	 * @return a {@code String} representation of this {@code EnumType} instance
	 */
	@Override
	public String toString() {
		return String.format("EnumType.valueOf(%s.class)", getName());
	}
	
	/**
	 * Compares {@code object} to this {@code EnumType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code EnumType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code EnumType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code EnumType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof EnumType)) {
			return false;
		} else if(!Objects.equals(this.classFile, EnumType.class.cast(object).classFile)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code false}.
	 * <p>
	 * This method has not been implemented yet.
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean isInnerType() {
		return false;//TODO: Implement!
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code EnumType} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code EnumType} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.classFile.isPublic();
	}
	
	/**
	 * Returns a hash code for this {@code EnumType} instance.
	 * 
	 * @return a hash code for this {@code EnumType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code EnumType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz.isEnum() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code EnumType} instances.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return an {@code EnumType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code clazz.isEnum() == false}
	 */
	public static EnumType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(!clazz.isEnum()) {
			throw new TypeException(String.format("An EnumType must refer to an enum type: %s", clazz));
		}
		
		try {
			synchronized(ENUM_TYPES) {
				final
				EnumType enumType = ENUM_TYPES.computeIfAbsent(clazz.getName(), name -> new EnumType(CLASS_FILES.computeIfAbsent(name, key -> new ClassFileReader().read(clazz))));
				enumType.doInitialize();
				
				return enumType;
			}
		} catch(final NodeFormatException e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns an {@code EnumType} instance that represents {@code Class.forName(className)}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails or {@code Class.forName(className).isEnum() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code EnumType} instances.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return an {@code EnumType} instance that represents {@code Class.forName(className)}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code Class.forName(className)} fails or {@code Class.forName(className).isEnum() == false}
	 */
	public static EnumType valueOf(final String className) {
		try {
			return valueOf(Class.forName(Objects.requireNonNull(className, "className == null")));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Clears the cache.
	 */
	public static void clearCache() {
		synchronized(ENUM_TYPES) {
			CLASS_FILES.clear();
			ENUM_TYPES.clear();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doInitialize() {
		if(this.hasInitialized.compareAndSet(false, true)) {
			doInitializeModifiers();
		}
	}
	
	private void doInitializeModifiers() {
		final List<Modifier> modifiers = this.modifiers;
		
		if(isPublic()) {
			modifiers.add(Modifier.PUBLIC);
		}
	}
}
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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClassesAttribute;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFormatException;

/**
 * An {@code AnnotationType} is a {@link Type} implementation that represents an annotation type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class AnnotationType extends Type {
	private static final Map<String, AnnotationType> ANNOTATION_TYPES = new HashMap<>();
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitializedModifiers;
	private final ClassFile classFile;
	private final List<Modifier> modifiers;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AnnotationType(final ClassFile classFile) {
		this.hasInitializedModifiers = new AtomicBoolean();
		this.classFile = classFile;
		this.modifiers = new ArrayList<>();
		this.name = ClassName.parseClassNameThisClass(this.classFile).toExternalForm();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code AnnotationType} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code AnnotationType} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code AnnotationType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code AnnotationType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code AnnotationType} instance
	 */
	public List<Modifier> getModifiers() {
		doInitializeModifiers();
		
		return new ArrayList<>(this.modifiers);
	}
	
	/**
	 * Returns the name of this {@code AnnotationType} instance.
	 * 
	 * @return the name of this {@code AnnotationType} instance
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AnnotationType} instance.
	 * 
	 * @return a {@code String} representation of this {@code AnnotationType} instance
	 */
	@Override
	public String toString() {
		return String.format("AnnotationType.valueOf(%s.class)", getName());
	}
	
	/**
	 * Compares {@code object} to this {@code AnnotationType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AnnotationType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AnnotationType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AnnotationType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AnnotationType)) {
			return false;
		} else if(!Objects.equals(this.classFile, AnnotationType.class.cast(object).classFile)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code AnnotationType} instance is an inner annotation, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code AnnotationType} instance is an inner annotation, {@code false} otherwise
	 */
	@Override
	public boolean isInnerType() {
		return InnerClassesAttribute.find(this.classFile).filter(innerClassesAttribute -> innerClassesAttribute.getInnerClasses().stream().anyMatch(innerClass -> innerClass.getOuterClassInfoIndex() != 0)).isPresent();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code AnnotationType} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code AnnotationType} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.classFile.isPublic();
	}
	
	/**
	 * Returns a hash code for this {@code AnnotationType} instance.
	 * 
	 * @return a hash code for this {@code AnnotationType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code AnnotationType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz.isAnnotation() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code AnnotationType} instances.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return an {@code AnnotationType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code clazz.isAnnotation() == false}
	 */
	public static AnnotationType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(!clazz.isAnnotation()) {
			throw new TypeException(String.format("An AnnotationType must refer to an annotation type: %s", clazz));
		}
		
		try {
			synchronized(ANNOTATION_TYPES) {
				return ANNOTATION_TYPES.computeIfAbsent(clazz.getName(), name -> new AnnotationType(CLASS_FILES.computeIfAbsent(name, key -> new ClassFileReader().read(clazz))));
			}
		} catch(final NodeFormatException e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns an {@code AnnotationType} instance that represents {@code Class.forName(className)}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails or {@code Class.forName(className).isAnnotation() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code AnnotationType} instances.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return an {@code AnnotationType} instance that represents {@code Class.forName(className)}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code Class.forName(className)} fails or {@code Class.forName(className).isAnnotation() == false}
	 */
	public static AnnotationType valueOf(final String className) {
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
		synchronized(ANNOTATION_TYPES) {
			ANNOTATION_TYPES.clear();
			CLASS_FILES.clear();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doInitializeModifiers() {
		if(this.hasInitializedModifiers.compareAndSet(false, true)) {
			final List<Modifier> modifiers = this.modifiers;
			
			if(isPublic()) {
				modifiers.add(Modifier.PUBLIC);
			}
		}
	}
}
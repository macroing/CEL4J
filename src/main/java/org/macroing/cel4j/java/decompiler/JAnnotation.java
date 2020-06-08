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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.string.ClassName;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFormatException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JAnnotation extends JType {
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	private static final Map<String, JAnnotation> J_ANNOTATIONS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitialized;
	private final Class<?> associatedClass;
	private final ClassFile associatedClassFile;
	private final List<JModifier> modifiers;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JAnnotation(final Class<?> associatedClass, final ClassFile associatedClassFile) {
		this.hasInitialized = new AtomicBoolean();
		this.associatedClass = associatedClass;
		this.associatedClassFile = associatedClassFile;
		this.modifiers = new ArrayList<>();
		this.name = ClassName.parseClassNameThisClass(this.associatedClassFile).toExternalForm();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Class<?> getAssociatedClass() {
		return this.associatedClass;
	}
	
	public ClassFile getAssociatedClassFile() {
		return this.associatedClassFile;
	}
	
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final String packageName = getPackageName();
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String simpleName = getSimpleName();
		
		document.linef("package %s;", packageName);
		document.linef("");
		document.linef("%s@interface %s {", modifiers, simpleName);
		document.indent();
		document.outdent();
		document.line("}");
		
		return document;
	}
	
	public List<JModifier> getModifiers() {
		return new ArrayList<>(this.modifiers);
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JAnnotation)) {
			return false;
		} else if(!Objects.equals(this.associatedClass, JAnnotation.class.cast(object).associatedClass)) {
			return false;
		} else if(!Objects.equals(this.associatedClassFile, JAnnotation.class.cast(object).associatedClassFile)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isInnerType() {
		return false;//TODO: Implement!
	}
	
	public boolean isPublic() {
		return this.associatedClassFile.isPublic();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.associatedClass, this.associatedClassFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static JAnnotation valueOf(final Class<?> associatedClass) {
		if(!associatedClass.isAnnotation()) {
			throw new JTypeException(String.format("A JAnnotation must refer to an annotation: %s", associatedClass));
		}
		
		try {
			synchronized(J_ANNOTATIONS) {
				final
				JAnnotation jAnnotation = J_ANNOTATIONS.computeIfAbsent(associatedClass.getName(), name -> new JAnnotation(associatedClass, CLASS_FILES.computeIfAbsent(name, name0 -> ClassFileReader.newInstance().readClassFile(associatedClass))));
				jAnnotation.doInitialize();
				
				return jAnnotation;
			}
		} catch(final NodeFormatException e) {
			throw new JTypeException(e);
		}
	}
	
	public static JAnnotation valueOf(final String name) {
		try {
			return valueOf(Class.forName(name));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new JTypeException(e);
		}
	}
	
	public static void clearCache() {
		synchronized(J_ANNOTATIONS) {
			J_ANNOTATIONS.clear();
			
			CLASS_FILES.clear();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doInitialize() {
		if(this.hasInitialized.compareAndSet(false, true)) {
			doInitializeModifiers();
		}
	}
	
	private void doInitializeModifiers() {
		final List<JModifier> modifiers = this.modifiers;
		
		if(isPublic()) {
			modifiers.add(JModifier.PUBLIC);
		}
	}
}
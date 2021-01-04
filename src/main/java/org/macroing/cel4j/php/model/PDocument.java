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
package org.macroing.cel4j.php.model;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code PDocument} represents a PHP document.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PDocument implements Documentable {
	private final List<PClass> classes;
	private final List<PInterface> interfaces;
	private final List<String> uses;
	private final PBlock block;
	private String namespace;
	private boolean isGeneratingComment;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PDocument} instance.
	 */
	public PDocument() {
		this.classes = new ArrayList<>();
		this.interfaces = new ArrayList<>();
		this.uses = new ArrayList<>();
		this.block = new PBlock();
		this.namespace = "";
		this.isGeneratingComment = false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PDocument} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pDocument.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code PDocument} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pDocument.write(document, false);
	 * }
	 * </pre>
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		return write(document, false);
	}
	
	/**
	 * Writes this {@code PDocument} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@code Document} to write to
	 * @param isAligningConsts {@code true} if, and only if, all {@link PConst} instances should be aligned, {@code false} otherwise
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document, final boolean isAligningConsts) {
		final List<PClass> classes = getClasses();
		final List<PInterface> interfaces = getInterfaces();
		final List<String> uses = getUses();
		
		final PBlock block = getBlock();
		
		final boolean hasBlockLines = !block.isEmpty();
		final boolean hasClasses = classes.size() > 0;
		final boolean hasInterfaces = interfaces.size() > 0;
		
		final boolean isGeneratingComment = isGeneratingComment();
		
		final String namespace = getNamespace();
		
		document.line("<?php");
		document.indent();
		
		if(isGeneratingComment) {
			document.linef("/**");
			document.linef(" * The PHP source code was generated by CEL4J PHP at %s.", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			document.linef(" */");
			document.linef("");
		}
		
		if(!namespace.isEmpty()) {
			document.linef("namespace %s;", namespace);
			document.linef("");
		}
		
		if(!uses.isEmpty()) {
			for(int i = 0; i < uses.size(); i++) {
				final String useA = uses.get(i);
				final String useB = uses.get(i + 1 < uses.size() ? i + 1 : i);
				
				document.linef("use %s;", useA);
				
				final String[] useAParts = useA.split("\\\\");
				final String[] useBParts = useB.split("\\\\");
				
				if(useAParts.length > 0 && useBParts.length > 0 && !useAParts[0].equals(useBParts[0])) {
					document.line();
				}
			}
			
			document.line();
		}
		
		for(int i = 0; i < interfaces.size(); i++) {
			final PInterface interfaceA = interfaces.get(i);
			final PInterface interfaceB = interfaces.get(i + 1 < interfaces.size() ? i + 1 : i);
			
			interfaceA.write(document);
			
			if(interfaceA != interfaceB) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if((hasClasses || hasBlockLines) && hasInterfaces) {
			document.line();
			document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
			document.line();
		}
		
		for(int i = 0; i < classes.size(); i++) {
			final PClass classA = classes.get(i);
			final PClass classB = classes.get(i + 1 < classes.size() ? i + 1 : i);
			
			classA.write(document, isAligningConsts);
			
			if(classA != classB) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(hasClasses && hasBlockLines) {
			document.line();
			document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
			document.line();
		}
		
		block.write(document);
		
		return document;
	}
	
	/**
	 * Writes this {@code PDocument} instance to a {@link Document} and the file represented by {@code file}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pDocument.write(file, false);
	 * }
	 * </pre>
	 * 
	 * @param file the {@code File} to write to
	 * @return the {@code Document}
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public Document write(final File file) {
		return write(file, false);
	}
	
	/**
	 * Writes this {@code PDocument} instance to a {@link Document} and the file represented by {@code file}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file the {@code File} to write to
	 * @param isAligningConsts {@code true} if, and only if, all {@link PConst} instances should be aligned, {@code false} otherwise
	 * @return the {@code Document}
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public Document write(final File file, final boolean isAligningConsts) {
		try {
			final Document document = write(new Document(), isAligningConsts);
			
			file.getParentFile().mkdirs();
			
			Files.write(file.toPath(), document.toString().getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			
			return document;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code List} with all {@link PClass} instances that are currently added to this {@code PDocument} instance.
	 * 
	 * @return a {@code List} with all {@code PClass} instances that are currently added to this {@code PDocument} instance
	 */
	public List<PClass> getClasses() {
		return new ArrayList<>(this.classes);
	}
	
	/**
	 * Returns a {@code List} with all {@link PInterface} instances that are currently added to this {@code PDocument} instance.
	 * 
	 * @return a {@code List} with all {@code PInterface} instances that are currently added to this {@code PDocument} instance
	 */
	public List<PInterface> getInterfaces() {
		return new ArrayList<>(this.interfaces);
	}
	
	/**
	 * Returns a {@code List} with all uses that are currently added to this {@code PDocument} instance.
	 * 
	 * @return a {@code List} with all uses that are currently added to this {@code PDocument} instance
	 */
	public List<String> getUses() {
		return new ArrayList<>(this.uses);
	}
	
	/**
	 * Returns the {@link PBlock} instance that is associated with this {@code PDocument} instance.
	 * 
	 * @return the {@code PBlock} instance that is associated with this {@code PDocument} instance
	 */
	public PBlock getBlock() {
		return this.block;
	}
	
	/**
	 * Returns the namespace associated with this {@code PDocument} instance.
	 * 
	 * @return the namespace associated with this {@code PDocument} instance
	 */
	public String getNamespace() {
		return this.namespace;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PDocument} instance should generate a comment at the top, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PDocument} instance should generate a comment at the top, {@code false} otherwise
	 */
	public boolean isGeneratingComment() {
		return this.isGeneratingComment;
	}
	
	/**
	 * Adds {@code pClass} to this {@code PDocument} instance.
	 * <p>
	 * If {@code pClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pClass the {@link PClass} to add
	 * @throws NullPointerException thrown if, and only if, {@code pClass} is {@code null}
	 */
	public void addClass(final PClass pClass) {
		this.classes.add(Objects.requireNonNull(pClass, "pClass == null"));
	}
	
	/**
	 * Adds {@code pInterface} to this {@code PDocument} instance.
	 * <p>
	 * If {@code pInterface} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pInterface the {@link PInterface} to add
	 * @throws NullPointerException thrown if, and only if, {@code pInterface} is {@code null}
	 */
	public void addInterface(final PInterface pInterface) {
		this.interfaces.add(Objects.requireNonNull(pInterface, "pInterface == null"));
	}
	
	/**
	 * Adds {@code use} to this {@code PDocument} instance.
	 * <p>
	 * If {@code use} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param use the use to add
	 * @throws NullPointerException thrown if, and only if, {@code use} is {@code null}
	 */
	public void addUse(final String use) {
		this.uses.add(Objects.requireNonNull(use, "use == null"));
	}
	
	/**
	 * Removes {@code pClass} from this {@code PDocument} instance.
	 * <p>
	 * If {@code pClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pClass the {@link PClass} to remove
	 * @throws NullPointerException thrown if, and only if, {@code pClass} is {@code null}
	 */
	public void removeClass(final PClass pClass) {
		this.classes.remove(Objects.requireNonNull(pClass, "pClass == null"));
	}
	
	/**
	 * Removes {@code pInterface} from this {@code PDocument} instance.
	 * <p>
	 * If {@code pInterface} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pInterface the {@link PInterface} to remove
	 * @throws NullPointerException thrown if, and only if, {@code pInterface} is {@code null}
	 */
	public void removeInterface(final PInterface pInterface) {
		this.interfaces.remove(Objects.requireNonNull(pInterface, "pInterface == null"));
	}
	
	/**
	 * Removes {@code use} from this {@code PDocument} instance.
	 * <p>
	 * If {@code use} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param use the use to remove
	 * @throws NullPointerException thrown if, and only if, {@code use} is {@code null}
	 */
	public void removeUse(final String use) {
		this.uses.remove(Objects.requireNonNull(use, "use == null"));
	}
	
	/**
	 * Sets whether or not this {@code PDocument} instance should generate a comment at the top.
	 * 
	 * @param isGeneratingComment {@code true} if, and only if, this {@code PDocument} instance should generate a comment at the top, {@code false} otherwise
	 */
	public void setGeneratingComment(final boolean isGeneratingComment) {
		this.isGeneratingComment = isGeneratingComment;
	}
	
	/**
	 * Sets the namespace for this {@code PDocument} instance.
	 * <p>
	 * If {@code namespace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param namespace the namespace for this {@code PDocument} instance
	 * @throws NullPointerException thrown if, and only if, {@code namespace} is {@code null}
	 */
	public void setNamespace(final String namespace) {
		this.namespace = Objects.requireNonNull(namespace, "namespace == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a namespace by joining all names in {@code names}.
	 * <p>
	 * If {@code names} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param names the names to join
	 * @return a namespace by joining all names in {@code names}
	 * @throws NullPointerException thrown if, and only if, {@code names} or any of its elements are {@code null}
	 */
	public static String toNamespace(final String... names) {
		return Arrays.asList(Objects.requireNonNull(names, "names == null")).stream().filter(name -> !name.isEmpty()).collect(Collectors.joining("\\"));
	}
}
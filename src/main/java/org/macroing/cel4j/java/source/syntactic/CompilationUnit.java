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
package org.macroing.cel4j.java.source.syntactic;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.source.JavaNode;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code CompilationUnit} denotes the nonterminal symbol CompilationUnit, as defined by the Java Language Specification.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class CompilationUnit implements JavaNode {
	private static final String SECTION_NAME_IMPORT_DECLARATIONS = "ImportDeclarations";
	private static final String SECTION_NAME_PACKAGE_DECLARATION = "PackageDeclaration";
	private static final String SECTION_NAME_TYPE_DECLARATIONS = "TypeDeclarations";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final SectionManager sectionManager;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code CompilationUnit} instance.
	 */
	public CompilationUnit() {
		this.sectionManager = new SectionManager();
		this.sectionManager.addSection(new Section(SECTION_NAME_PACKAGE_DECLARATION));
		this.sectionManager.addSection(new Section(SECTION_NAME_IMPORT_DECLARATIONS));
		this.sectionManager.addSection(new Section(SECTION_NAME_TYPE_DECLARATIONS));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link ImportDeclaration} instances.
	 * <p>
	 * This {@code CompilationUnit} instance will not be affected by modifications to the returned {@code List} itself. But modifications to the {@code ImportDeclaration} instances in the {@code List} will.
	 * 
	 * @return a {@code List} with all currently added {@code ImportDeclaration} instances
	 */
	public List<ImportDeclaration> getImportDeclarations() {
		return this.sectionManager.getJavaNodes(ImportDeclaration.class);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link TypeDeclaration} instances.
	 * <p>
	 * This {@code CompilationUnit} instance will not be affected by modifications to the returned {@code List} itself. But modifications to the {@code TypeDeclaration} instances in the {@code List} will.
	 * 
	 * @return a {@code List} with all currently added {@code TypeDeclaration} instances
	 */
	public List<TypeDeclaration> getTypeDeclarations() {
		return this.sectionManager.getJavaNodes(TypeDeclaration.class);
	}
	
//	TODO: Add Javadocs!
	public Section getSectionImportDeclarations() {
		return this.sectionManager.getSection(SECTION_NAME_IMPORT_DECLARATIONS);
	}
	
//	TODO: Add Javadocs!
	public Section getSectionPackageDeclaration() {
		return this.sectionManager.getSection(SECTION_NAME_PACKAGE_DECLARATION);
	}
	
//	TODO: Add Javadocs!
	public Section getSectionTypeDeclarations() {
		return this.sectionManager.getSection(SECTION_NAME_TYPE_DECLARATIONS);
	}
	
	/**
	 * Returns the source code of this {@code CompilationUnit} instance.
	 * 
	 * @return the source code of this {@code CompilationUnit} instance
	 */
	@Override
	public String getSourceCode() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final JavaNode javaNode : this.sectionManager.getJavaNodes()) {
			stringBuilder.append(javaNode.getSourceCode());
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code CompilationUnit} instance.
	 * 
	 * @return a {@code String} representation of this {@code CompilationUnit} instance
	 */
	@Override
	public String toString() {
		return "new CompilationUnit()";
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node}s, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final JavaNode javaNode : this.sectionManager.getJavaNodes()) {
					if(!javaNode.accept(nodeHierarchicalVisitor)) {
						break;
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code CompilationUnit} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code CompilationUnit}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code CompilationUnit} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code CompilationUnit}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof CompilationUnit)) {
			return false;
		} else if(!Objects.equals(this.sectionManager, CompilationUnit.class.cast(object).sectionManager)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code CompilationUnit} instance.
	 * 
	 * @return a hash code for this {@code CompilationUnit} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sectionManager);
	}
	
//	TODO: Add Javadocs!
	public void addImportDeclaration(final ImportDeclaration importDeclaration) {
		this.sectionManager.getSection(SECTION_NAME_IMPORT_DECLARATIONS).addJavaNodeLast(Objects.requireNonNull(importDeclaration, "importDeclaration == null"));
	}
	
//	TODO: Add Javadocs!
	public void addTypeDeclaration(final TypeDeclaration typeDeclaration) {
		this.sectionManager.getSection(SECTION_NAME_TYPE_DECLARATIONS).addJavaNodeLast(Objects.requireNonNull(typeDeclaration, "typeDeclaration == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeImportDeclaration(final ImportDeclaration importDeclaration) {
		this.sectionManager.getSection(SECTION_NAME_IMPORT_DECLARATIONS).removeJavaNodeLast(Objects.requireNonNull(importDeclaration, "importDeclaration == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeTypeDeclaration(final TypeDeclaration typeDeclaration) {
		this.sectionManager.getSection(SECTION_NAME_TYPE_DECLARATIONS).removeJavaNodeLast(Objects.requireNonNull(typeDeclaration, "typeDeclaration == null"));
	}
}
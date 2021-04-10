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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.source.JavaNode;
import org.macroing.cel4j.java.source.lexical.Comment;
import org.macroing.cel4j.java.source.lexical.WhiteSpace;

//TODO: Add Javadocs!
public final class Section {
	private final List<JavaNode> javaNodes;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Section(final String name) {
		this.javaNodes = new ArrayList<>();
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Section)) {
			return false;
		} else if(!Objects.equals(this.javaNodes, Section.class.cast(object).javaNodes)) {
			return false;
		} else if(!Objects.equals(this.name, Section.class.cast(object).name)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.javaNodes, this.name);
	}
	
//	TODO: Add Javadocs!
	public void addCommentFirst(final Comment comment) {
		addJavaNodeFirst(Objects.requireNonNull(comment, "comment == null"));
	}
	
//	TODO: Add Javadocs!
	public void addCommentLast(final Comment comment) {
		addJavaNodeLast(Objects.requireNonNull(comment, "comment == null"));
	}
	
//	TODO: Add Javadocs!
	public void addWhiteSpaceFirst(final WhiteSpace whiteSpace) {
		addJavaNodeFirst(Objects.requireNonNull(whiteSpace, "whiteSpace == null"));
	}
	
//	TODO: Add Javadocs!
	public void addWhiteSpaceLast(final WhiteSpace whiteSpace) {
		addJavaNodeLast(Objects.requireNonNull(whiteSpace, "whiteSpace == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeCommentFirst(final Comment comment) {
		removeJavaNodeFirst(Objects.requireNonNull(comment, "comment == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeCommentLast(final Comment comment) {
		removeJavaNodeLast(Objects.requireNonNull(comment, "comment == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeWhiteSpaceFirst(final WhiteSpace whiteSpace) {
		removeJavaNodeFirst(Objects.requireNonNull(whiteSpace, "whiteSpace == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeWhiteSpaceLast(final WhiteSpace whiteSpace) {
		removeJavaNodeLast(Objects.requireNonNull(whiteSpace, "whiteSpace == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	List<JavaNode> getJavaNodes() {
		return new ArrayList<>(this.javaNodes);
	}
	
	<T extends JavaNode> List<T> getJavaNodes(final Class<T> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		return getJavaNodes().stream().filter(node -> clazz.isAssignableFrom(node.getClass())).map(node -> clazz.cast(node)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	String getName() {
		return this.name;
	}
	
	void addJavaNodeFirst(final JavaNode javaNode) {
		Objects.requireNonNull(javaNode, "javaNode == null");
		
		this.javaNodes.add(0, javaNode);
	}
	
	void addJavaNodeLast(final JavaNode javaNode) {
		Objects.requireNonNull(javaNode, "javaNode == null");
		
		this.javaNodes.add(javaNode);
	}
	
	void removeJavaNodeFirst(final JavaNode javaNode) {
		Objects.requireNonNull(javaNode, "javaNode == null");
		
		this.javaNodes.remove(javaNode);
	}
	
	void removeJavaNodeLast(final JavaNode javaNode) {
		Objects.requireNonNull(javaNode, "javaNode == null");
		
		final int lastIndex = this.javaNodes.lastIndexOf(javaNode);
		
		if(lastIndex != -1) {
			this.javaNodes.remove(lastIndex);
		}
	}
}
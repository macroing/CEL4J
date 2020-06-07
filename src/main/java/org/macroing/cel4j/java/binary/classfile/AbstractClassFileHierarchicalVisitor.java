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
package org.macroing.cel4j.java.binary.classfile;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code AbstractClassFileHierarchicalVisitor} is used for traversing the structure of a {@link Node} in a hierarchical fashion. 
 * <p>
 * This abstract class delegates method calls for {@code visitEnter(Node)} and {@code visitLeave(Node)} to overloaded methods with the same name.
 * <p>
 * To use this class, extend it and override one or more of the existing overloaded methods. It has overloaded methods for {@link AttributeInfo}, {@link CPInfo}, {@link FieldInfo} and {@link MethodInfo}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class AbstractClassFileHierarchicalVisitor implements NodeHierarchicalVisitor {
	/**
	 * Constructs a new {@code AbstractClassFileHierarchicalVisitor} instance.
	 */
	protected AbstractClassFileHierarchicalVisitor() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Called by a {@link Node} instance when entering it.
	 * <p>
	 * Returns {@code true} if, and only if, {@code node}s child {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * This method should be called by a {@code Node} instance soon after its {@code accept(NodeHierarchicalVisitor)} method has been called.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code node} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class will attempt to delegate all calls to overloaded methods. If an overloaded method can be found for {@code node}, that method will be called. If a method was called, its returned value will be returned by
	 * this method. In any other case, {@code true} will be returned.
	 * 
	 * @param node the {@code Node} to enter
	 * @return {@code true} if, and only if, {@code node}s child {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code node} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	@Override
	public final boolean visitEnter(final Node node) {
		if(node instanceof AttributeInfo) {
			return visitEnter(AttributeInfo.class.cast(node));
		} else if(node instanceof CPInfo) {
			return visitEnter(CPInfo.class.cast(node));
		} else if(node instanceof FieldInfo) {
			return visitEnter(FieldInfo.class.cast(node));
		} else if(node instanceof MethodInfo) {
			return visitEnter(MethodInfo.class.cast(node));
		} else {
			return true;
		}
	}
	
	/**
	 * Called by a {@link Node} instance when leaving it.
	 * <p>
	 * Returns {@code true} if, and only if, {@code node}s sibling {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * This method should be called by a {@code Node} instance just before its {@code accept(NodeHierarchicalVisitor)} method returns. It should be the result returned by that method.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code node} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class will attempt to delegate all calls to overloaded methods. If an overloaded method can be found for {@code node}, that method will be called. If a method was called, its returned value will be returned by
	 * this method. In any other case, {@code true} will be returned.
	 * 
	 * @param node the {@code Node} to leave
	 * @return {@code true} if, and only if, {@code node}s sibling {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code node} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	@Override
	public final boolean visitLeave(final Node node) {
		if(node instanceof AttributeInfo) {
			return visitLeave(AttributeInfo.class.cast(node));
		} else if(node instanceof CPInfo) {
			return visitLeave(CPInfo.class.cast(node));
		} else if(node instanceof FieldInfo) {
			return visitLeave(FieldInfo.class.cast(node));
		} else if(node instanceof MethodInfo) {
			return visitLeave(MethodInfo.class.cast(node));
		} else {
			return true;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This method is called by {@link #visitEnter(Node)} when given a {@link Node} of type {@link AttributeInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo}s child {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code attributeInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} to enter
	 * @return {@code true} if, and only if, {@code attributeInfo}s child {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code attributeInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitEnter(final AttributeInfo attributeInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitEnter(Node)} when given a {@link Node} of type {@link CPInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code cPInfo}s child {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code cPInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param cPInfo the {@code CPInfo} to enter
	 * @return {@code true} if, and only if, {@code cPInfo}s child {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code cPInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitEnter(final CPInfo cPInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitEnter(Node)} when given a {@link Node} of type {@link FieldInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldInfo}s child {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code fieldInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param fieldInfo the {@code FieldInfo} to enter
	 * @return {@code true} if, and only if, {@code fieldInfo}s child {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code fieldInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitEnter(final FieldInfo fieldInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitEnter(Node)} when given a {@link Node} of type {@link MethodInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code methodInfo}s child {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code methodInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param methodInfo the {@code MethodInfo} to enter
	 * @return {@code true} if, and only if, {@code methodInfo}s child {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code methodInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitEnter(final MethodInfo methodInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitLeave(Node)} when given a {@link Node} of type {@link AttributeInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo}s sibling {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code attributeInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} to leave
	 * @return {@code true} if, and only if, {@code attributeInfo}s sibling {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code attributeInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitLeave(final AttributeInfo attributeInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitLeave(Node)} when given a {@link Node} of type {@link CPInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code cPInfo}s sibling {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code cPInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param cPInfo the {@code CPInfo} to leave
	 * @return {@code true} if, and only if, {@code cPInfo}s sibling {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code cPInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitLeave(final CPInfo cPInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitLeave(Node)} when given a {@link Node} of type {@link FieldInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldInfo}s sibling {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code fieldInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param fieldInfo the {@code FieldInfo} to leave
	 * @return {@code true} if, and only if, {@code fieldInfo}s sibling {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code fieldInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitLeave(final FieldInfo fieldInfo) {
		return true;
	}
	
	/**
	 * This method is called by {@link #visitLeave(Node)} when given a {@link Node} of type {@link MethodInfo}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code methodInfo}s sibling {@code Node}s should be visited, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code methodInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}, a {@link NodeTraversalException} may be thrown. But no guarantees can be made.
	 * <p>
	 * The implementation provided by this class returns {@code true} by default.
	 * 
	 * @param methodInfo the {@code MethodInfo} to leave
	 * @return {@code true} if, and only if, {@code methodInfo}s sibling {@code Node}s should be visited, {@code false} otherwise
	 * @throws NodeTraversalException thrown if, and only if, {@code methodInfo} could not be traversed by this {@code AbstractClassFileHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	@SuppressWarnings("static-method")
	protected boolean visitLeave(final MethodInfo methodInfo) {
		return true;
	}
}
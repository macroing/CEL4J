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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * A class that consists exclusively of static methods that returns {@link Element} instances related to Font Awesome.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class FontAwesome {
	private FontAwesome() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Script} instance that points to Font Awesome.
	 * 
	 * @return a {@code Script} instance that points to Font Awesome
	 */
	public static Script createScript() {
		final
		Script script = new Script();
		script.getAttributeSrc().setValue("https://kit.fontawesome.com/f3f524ba52.js");
		
		return script;
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-blog}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanBlog("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-blog}
	 */
	public static Span createSpanBlog() {
		return createSpanBlog("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-blog}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-blog}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanBlog(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-blog"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-cog}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanCog("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-cog}
	 */
	public static Span createSpanCog() {
		return createSpanCog("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-cog}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-cog}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanCog(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-cog"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code far} and {@code fa-comment}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanCommentRegular("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code far} and {@code fa-comment}
	 */
	public static Span createSpanCommentRegular() {
		return createSpanCommentRegular("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code far} and {@code fa-comment}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code far} and {@code fa-comment}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanCommentRegular(final String color) {
		return doCreateSpan(color, new String[] {"far", "fa-comment"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fas} and {@code fa-comment}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanCommentSolid("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fas} and {@code fa-comment}
	 */
	public static Span createSpanCommentSolid() {
		return createSpanCommentSolid("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fas} and {@code fa-comment}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fas} and {@code fa-comment}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanCommentSolid(final String color) {
		return doCreateSpan(color, new String[] {"fas", "fa-comment"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-edit}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanEdit("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-edit}
	 */
	public static Span createSpanEdit() {
		return createSpanEdit("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-edit}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-edit}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanEdit(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-edit"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-envelope}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanEnvelope("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-envelope}
	 */
	public static Span createSpanEnvelope() {
		return createSpanEnvelope("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-envelope}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-envelope}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanEnvelope(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-envelope"});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Span doCreateSpan(final String color, final String[] attributeClassValues) {
		final
		Span span = new Span();
		span.getAttributeClass().setValue(attributeClassValues);
		span.getAttributeStyle().setValue(Objects.requireNonNull(color, "color").isEmpty() ? "" : String.format("color: %s;", color));
		
		return span;
	}
}
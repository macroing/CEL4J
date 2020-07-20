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
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-file}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanFile("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-file}
	 */
	public static Span createSpanFile() {
		return createSpanFile("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-file}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-file}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanFile(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-file"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-folder}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanFolder("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-folder}
	 */
	public static Span createSpanFolder() {
		return createSpanFolder("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-folder}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-folder}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanFolder(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-folder"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-folder-open}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanFolderOpen("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-folder-open}
	 */
	public static Span createSpanFolderOpen() {
		return createSpanFolderOpen("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-folder-open}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-folder-open}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanFolderOpen(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-folder-open"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fab} and {@code fa-github}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanGitHub("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fab} and {@code fa-github}
	 */
	public static Span createSpanGitHub() {
		return createSpanGitHub("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fab} and {@code fa-github}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fab} and {@code fa-github}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanGitHub(final String color) {
		return doCreateSpan(color, new String[] {"fab", "fa-github"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-home}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanHome("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-home}
	 */
	public static Span createSpanHome() {
		return createSpanHome("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-home}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-home}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanHome(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-home"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-image}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanImage("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-image}
	 */
	public static Span createSpanImage() {
		return createSpanImage("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-image}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-image}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanImage(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-image"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-info}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanInfo("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-info}
	 */
	public static Span createSpanInfo() {
		return createSpanInfo("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-info}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-info}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanInfo(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-info"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fab} and {@code fa-instagram}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanInstagram("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fab} and {@code fa-instagram}
	 */
	public static Span createSpanInstagram() {
		return createSpanInstagram("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fab} and {@code fa-instagram}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fab} and {@code fa-instagram}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanInstagram(final String color) {
		return doCreateSpan(color, new String[] {"fab", "fa-instagram"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-link}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanLink("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-link}
	 */
	public static Span createSpanLink() {
		return createSpanLink("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-link}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-link}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanLink(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-link"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-list}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanList("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-list}
	 */
	public static Span createSpanList() {
		return createSpanList("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-list}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-list}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanList(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-list"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-newspaper}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanNewspaper("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-newspaper}
	 */
	public static Span createSpanNewspaper() {
		return createSpanNewspaper("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-newspaper}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-newspaper}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanNewspaper(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-newspaper"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-project-diagram}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanProjectDiagram("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-project-diagram}
	 */
	public static Span createSpanProjectDiagram() {
		return createSpanProjectDiagram("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-project-diagram}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-project-diagram}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanProjectDiagram(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-project-diagram"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-sign-in}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanSignIn("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-sign-in}
	 */
	public static Span createSpanSignIn() {
		return createSpanSignIn("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-sign-in}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-sign-in}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanSignIn(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-sign-in"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-sign-out}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanSignOut("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-sign-out}
	 */
	public static Span createSpanSignOut() {
		return createSpanSignOut("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-sign-out}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-sign-out}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanSignOut(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-sign-out"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code far} and {@code fa-star}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanStarRegular("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code far} and {@code fa-star}
	 */
	public static Span createSpanStarRegular() {
		return createSpanStarRegular("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code far} and {@code fa-star}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code far} and {@code fa-star}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanStarRegular(final String color) {
		return doCreateSpan(color, new String[] {"far", "fa-star"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fas} and {@code fa-star}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanStarSolid("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fas} and {@code fa-star}
	 */
	public static Span createSpanStarSolid() {
		return createSpanStarSolid("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fas} and {@code fa-star}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fas} and {@code fa-star}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanStarSolid(final String color) {
		return doCreateSpan(color, new String[] {"fas", "fa-comment"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-user}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanUser("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-user}
	 */
	public static Span createSpanUser() {
		return createSpanUser("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-user}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-user}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanUser(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-user"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-trash}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanTrash("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-trash}
	 */
	public static Span createSpanTrash() {
		return createSpanTrash("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-trash}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-trash}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanTrash(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-trash"});
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-wrench}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FontAwesome.createSpanWrench("");
	 * }
	 * </pre>
	 * 
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-wrench}
	 */
	public static Span createSpanWrench() {
		return createSpanWrench("");
	}
	
	/**
	 * Returns a {@link Span} instance with the classes {@code fa} and {@code fa-wrench}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the color of the {@code Span}, or an empty {@code String} to discard it
	 * @return a {@code Span} instance with the classes {@code fa} and {@code fa-wrench}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Span createSpanWrench(final String color) {
		return doCreateSpan(color, new String[] {"fa", "fa-wrench"});
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
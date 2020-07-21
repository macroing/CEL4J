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
 * A class that consists exclusively of static methods that returns {@link Element} instances related to Bootstrap.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Bootstrap {
	private Bootstrap() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code danger}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code danger}
	 */
	public static Div createDivAlertDanger(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "danger");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code dark}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code dark}
	 */
	public static Div createDivAlertDark(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "dark");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code info}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code info}
	 */
	public static Div createDivAlertInfo(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "info");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code light}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code light}
	 */
	public static Div createDivAlertLight(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "light");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code primary}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code primary}
	 */
	public static Div createDivAlertPrimary(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "primary");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code secondary}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code secondary}
	 */
	public static Div createDivAlertSecondary(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "secondary");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code success}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code success}
	 */
	public static Div createDivAlertSuccess(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "success");
	}
	
	/**
	 * Returns an alert {@link Div} instance with a color represented by {@code warning}.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content a {@link Content} instance
	 * @param isDismissible {@code true} if, and only if, the alert is dismissible, {@code false} otherwise
	 * @return an alert {@code Div} instance with a color represented by {@code warning}
	 */
	public static Div createDivAlertWarning(final Content<Element> content, final boolean isDismissible) {
		return doCreateDivAlert(content, isDismissible, "warning");
	}
	
	/**
	 * Returns an {@link H1} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H1} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H1 createH1(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H1.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H1(elements);
	}
	
	/**
	 * Returns an {@link H2} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H2} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H2 createH2(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H2.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H2(elements);
	}
	
	/**
	 * Returns an {@link H3} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H3} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H3 createH3(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H3.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H3(elements);
	}
	
	/**
	 * Returns an {@link H4} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H4} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H4 createH4(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H4.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H4(elements);
	}
	
	/**
	 * Returns an {@link H5} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H5} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H5 createH5(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H5.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H5(elements);
	}
	
	/**
	 * Returns an {@link H6} instance with {@code string} as the default text and {@code stringMuted} as the muted text.
	 * <p>
	 * If either {@code string} or {@code stringMuted} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code stringMuted.isEmpty()} returns {@code true}, it will not be added. Otherwise it will be added after a {@link Span} that separates the two.
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance in the {@code Span} instance that contains the default text
	 * @param stringMuted the {@code String} associated with a {@code Text} instance in the {@code Span} instance that contains the muted text
	 * @return an {@code H6} instance with {@code string} as the default text and {@code stringMuted} as the muted text
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code stringMuted} are {@code null}
	 */
	public static H6 createH6(final String string, final String stringMuted) {
		Objects.requireNonNull(string, "string == null");
		Objects.requireNonNull(stringMuted, "stringMuted == null");
		
		final
		Elements<Element> elements = new Elements<>(H6.DISPLAY_INITIAL);
		elements.addElement(new Span(string));
		
		if(!stringMuted.isEmpty()) {
			elements.addElement(new Span(" "));
			elements.addElement(new Span(stringMuted));
		}
		
		return new H6(elements);
	}
	
	/**
	 * Returns an {@link Img} instance that represents a thumbnail.
	 * <p>
	 * If either {@code alt}, {@code src}, {@code width} or {@code height} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param alt the value associated with the {@link Attribute} with the name {@code "alt"}
	 * @param src the value associated with the {@code Attribute} with the name {@code "src"}
	 * @param width the value associated with the {@code Attribute} with the name {@code "width"}
	 * @param height the value associated with the {@code Attribute} with the name {@code "height"}
	 * @return an {@code Img} instance that represents a thumbnail
	 * @throws NullPointerException thrown if, and only if, either {@code alt}, {@code src}, {@code width} or {@code height} are {@code null}
	 */
	public static Img createImgThumbnail(final String alt, final String src, final String width, final String height) {
		final
		Img img = new Img();
		img.getAttributeAlt().setValue(alt);
		img.getAttributeClass().setValue("img-thumbnail");
		img.getAttributeHeight().setValue(height);
		img.getAttributeSrc().setValue(src);
		img.getAttributeWidth().setValue(width);
		
		return img;
	}
	
	/**
	 * Returns a {@link Link} instance that points to Bootstrap.
	 * <p>
	 * The current implementation of this method points to Bootstrap version 4.1.0, minified.
	 * 
	 * @return a {@code Link} instance that points to Bootstrap
	 */
	public static Link createLink() {
		final
		Link link = new Link();
		link.getAttributeCrossOrigin().setValue("anonymous");
		link.getAttributeHRef().setValue("https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css");
		link.getAttributeRel().setValue("stylesheet");
		
		return link;
	}
	
	/**
	 * Returns a {@link Script} instance that points to Bootstrap.
	 * <p>
	 * The current implementation of this method points to Bootstrap version 4.1.0, minified.
	 * 
	 * @return a {@code Script} instance that points to Bootstrap
	 */
	public static Script createScript() {
		final
		Script script = new Script();
		script.getAttributeCrossOrigin().setValue("anonymous");
		script.getAttributeIntegrity().setValue("sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm");
		script.getAttributeSrc().setValue("https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js");
		
		return script;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Div doCreateDivAlert(final Content<Element> content, final boolean isDismissible, final String type) {
		final Elements<Element> elements = new Elements<>(Div.DISPLAY_INITIAL, content);
		
		final
		Div div = new Div(elements);
		div.getAttributeClass().setValue(new String[] {"alert", "alert-" + type});
		
		if(isDismissible) {
			div.getAttributeClass().addValue("alert-dismissible");
			div.getAttributeClass().addValue("fade");
			div.getAttributeClass().addValue("show");
			
			final
			Button button = new Button("&times;");
			button.addAttribute(new Attribute("data-dismiss", "alert"));
			button.getAttributeClass().setValue("close");
			button.getAttributeType().setValue("button");
			
			elements.addElement(button);
		}
		
		return div;
	}
}
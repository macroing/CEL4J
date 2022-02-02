/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.macroing.cel4j.java.source.JavaNode;

final class SectionManager {
	private final Map<String, Section> sections;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public SectionManager() {
		this.sections = new LinkedHashMap<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<JavaNode> getJavaNodes() {
		return getSections().stream().flatMap(section -> section.getJavaNodes().stream()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public <T extends JavaNode> List<T> getJavaNodes(final Class<T> clazz) {
		return getSections().stream().flatMap(section -> section.getJavaNodes(clazz).stream()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public List<Section> getSections() {
		return new ArrayList<>(this.sections.values());
	}
	
	public Section getSection(final String name) {
		return this.sections.get(Objects.requireNonNull(name, "name == null"));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SectionManager)) {
			return false;
		} else if(!Objects.equals(this.sections, SectionManager.class.cast(object).sections)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.sections);
	}
	
	public void addSection(final Section section) {
		this.sections.put(section.getName(), section);
	}
	
	public void removeSection(final Section section) {
		this.sections.remove(section.getName());
	}
}
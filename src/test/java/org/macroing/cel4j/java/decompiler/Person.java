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
import java.util.List;
import java.util.Objects;

public final class Person implements Comparable<Person> {
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	private static final int AGE = 18;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String lastName;
	private final String firstName;
	private final int age;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Person() {
		this(FIRST_NAME);
	}
	
	public Person(final String firstName) {
		this(firstName, LAST_NAME);
	}
	
	public Person(final String firstName, final String lastName) {
		this(firstName, lastName, AGE);
	}
	
	public Person(final String firstName, final String lastName, final int age) {
		this.firstName = Objects.requireNonNull(firstName, "firstName == null");
		this.lastName = Objects.requireNonNull(lastName, "lastName == null");
		this.age = Utilities.requireRange(age, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	@Override
	public String toString() {
		return String.format("new Person(\"%s\", \"%s\", %d)", this.firstName, this.lastName, Integer.valueOf(this.age));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Person)) {
			return false;
		} else if(!Objects.equals(this.firstName, Person.class.cast(object).firstName)) {
			return false;
		} else if(!Objects.equals(this.lastName, Person.class.cast(object).lastName)) {
			return false;
		} else if(this.age != Person.class.cast(object).age) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int compareTo(final Person person) {
		return Integer.compare(getAge(), person.getAge());
	}
	
	public int getAge() {
		return this.age;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.firstName, this.lastName, Integer.valueOf(this.age));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<Person> findByFirstName(final String firstName) {
		Objects.requireNonNull(firstName, "firstName == null");
		
		return new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Utilities {
		private Utilities() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static int requireRange(final int value, final int minimumValue, final int maximumValue) throws IllegalArgumentException {
			if(value < minimumValue) {
				throw new IllegalArgumentException();
			} else if(value > maximumValue) {
				throw new IllegalArgumentException();
			} else {
				return value;
			}
		}
	}
}
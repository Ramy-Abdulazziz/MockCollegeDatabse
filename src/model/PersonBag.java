package model;

import java.io.Serializable;
import java.util.Arrays;

import application.Main;

public class PersonBag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3315209290522108502L;
	private Person[] personArray;
	private int nElems;

	public PersonBag(int maxSize) {

		nElems = 0;
		personArray = new Person[maxSize];

	}

	public void insert(Person person) {

		personArray[nElems++] = person;

	}

	public Person searchById(String id) {
		for (int i = 0; i < nElems; i++) {
			if (personArray[i].getId().equals(id)) {
				return personArray[i];
			}
		}
		return null;
	}

	public int getNelms() {
		return nElems;
	}

	public Person[] getPersonArray() {

		return personArray;
	}

	public Person removeById(String id) {

		int i;
		for (i = 0; i < nElems; i++) {
			if (personArray[i].getId().contentEquals(id)) {
				break;
			}
		}

		if (i == nElems) {
			return null;
		} else {
			Person temp = personArray[i];
			for (int j = i; j < nElems - 1; j++) {
				personArray[j] = personArray[j + 1];
			}
			nElems--;
			return temp;
		}

	}

	public Student isStudent(String id) {

		int i;
		for (i = 0; i < nElems; i++) {

			if (personArray[i].getId().contentEquals(id) && personArray[i] instanceof Student) {
				return (Student) personArray[i];
			}
		}

		return null;

	}

	public Faculty isFaculty(String id) {
		int i;
		for (i = 0; i < nElems; i++) {

			if (personArray[i].getId().contentEquals(id) && personArray[i] instanceof Faculty) {

				return (Faculty) personArray[i];

			}
		}
		return null;
	}

	public Student[] getStudents() {
		int i = 0;
		int matchCount = 0;
		Student[] students = new Student[nElems];
		for (i = 0; i < nElems; i++) {

			if (personArray[i] instanceof Student) {

				students[i] = (Student) personArray[i];
				matchCount++;

			}
		}

		return Arrays.copyOf(students, matchCount);

	}

	public Faculty[] getFaculty() {

		int i = 0;
		int matchCount = 0;
		Faculty[] faculty = new Faculty[nElems];

		for (i = 0; i < nElems; i++) {

			if (personArray[i] instanceof Faculty) {

				faculty[i] = (Faculty) personArray[i];
				matchCount++;

			}
		}

		return Arrays.copyOf(faculty, matchCount);

	}

}

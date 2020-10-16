package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class MasterCourseBag implements Serializable {

	private Course[] courseArray;
	private int nElems;

	public MasterCourseBag(int maxSize) {
		courseArray = new Course[maxSize];
		nElems = 0;
	}

	public Course[] getCourseArray() {
		return courseArray;
	}

	public void setCourseArray(Course[] courseArray) {
		this.courseArray = courseArray;
	}

	public void insert(Course course) {
		courseArray[nElems++] = course;
	}

	public Course findByNum(String courseNumber) {
		
		for (int i = 0; i < nElems; i++) {

			if (courseArray[i].getCourseNumber().contentEquals(courseNumber)){
				
				return courseArray[i];

			}

		}
		return null;


	}

	public Course removeBy(String courseNumber) {

		int matchCount = 0;
		int i;
		for (i = 0; i < nElems; i++) {

			if (courseArray[i].getCourseNumber().contentEquals(courseNumber)) {
				break;
			}
		}
		if (i == nElems) {
			System.out.println("enter alert here noone found");

			return null;

		}

		else {

			Course temp = courseArray[i];
			for (int j = i; j < nElems - 1; j++) {

				courseArray[i] = courseArray[i + 1];
			}

			nElems--;
			return temp;

		}

	}

	public int getNelms() {
		return nElems;
	}

}

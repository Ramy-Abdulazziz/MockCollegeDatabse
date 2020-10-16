package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class ClassroomBag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4695525462157475053L;
	private Classroom[] classroomArray;
	private int nElems;

	public ClassroomBag(int maxSize) {

		classroomArray = new Classroom[maxSize];

	}

	public void insert(Classroom classRoom) {

		classroomArray[nElems++] = classRoom;
	}

	public Classroom[] getClassroomArray() {
		return classroomArray;
	}

	public void setClassroomArray(Classroom[] classroomArray) {
		this.classroomArray = classroomArray;
	}

	public Classroom removeBy(String roomNumber) {
		int i;
		for (i = 0; i < nElems; i++) {

			if (classroomArray[i].getRoomNumber().contentEquals(roomNumber)) {

				break;
			}
		}

		if (i == nElems) {
			return null;
		}

		Classroom temp = classroomArray[i];
		for (int j = i; j < nElems; j++) {

			classroomArray[i] = classroomArray[i + 1];

		}
		nElems--;
		return temp;
	}

	public Classroom findBy(String roomNumber) {

		for (int i = 0; i < nElems; i++) {

			if (classroomArray[i].getRoomNumber().contentEquals(roomNumber)) {

				return classroomArray[i];

			}
		}
		return null;
	}

	public int getnElems() {

		return nElems;
	}
}

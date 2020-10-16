package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class MiniFacultyCourseBag implements Serializable {

	private MiniFacultyCourseInfo[] miniFacultyCourseInfoArray;
	private int nElems;

	public MiniFacultyCourseBag(int maxSize) {
		super();
		miniFacultyCourseInfoArray = new MiniFacultyCourseInfo[maxSize];
		nElems = 0;
	}

	public void insert(MiniFacultyCourseInfo miniFacultyCourseInfo) {

		miniFacultyCourseInfoArray[nElems++] = miniFacultyCourseInfo;
	}

	public MiniFacultyCourseInfo[] findBy(Predicate<MiniFacultyCourseInfo> searchParam) {
		MiniFacultyCourseInfo[] results = new MiniFacultyCourseInfo[nElems];
		int matchCount = 0;
		for (int i = 0; i < nElems; i++) {

			if (searchParam.test(miniFacultyCourseInfoArray[i])) {

				results[matchCount++] = miniFacultyCourseInfoArray[i];
			}

		}

		return Arrays.copyOf(results, matchCount);

	}

	public MiniFacultyCourseInfo removeBy(Predicate<MiniFacultyCourseInfo> searchParam) {

		int matchCount = 0;
		int i;
		for (i = 0; i < nElems; i++) {

			if (searchParam.test(miniFacultyCourseInfoArray[i])) {
				break;
			}
		}
		if (i == nElems) {
			System.out.println("enter alert here noone found");

			return null;

		}

		else {

			MiniFacultyCourseInfo temp = miniFacultyCourseInfoArray[i];
			for (int j = i; j < nElems - 1; j++) {

				miniFacultyCourseInfoArray[i] = miniFacultyCourseInfoArray[i + 1];
			}

			nElems--;
			System.out.println("add alert here removed");
			return temp;

		}

	}

	public MiniFacultyCourseInfo[] getMiniFacultyCourseInfoArray() {
		return miniFacultyCourseInfoArray;
	}

	public void setMiniFacultyCourseInfoArray(MiniFacultyCourseInfo[] miniFacultyCourseInfoArray) {
		this.miniFacultyCourseInfoArray = miniFacultyCourseInfoArray;
	}

	public int getnElems() {
		return nElems;
	}

}

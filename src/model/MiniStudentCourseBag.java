package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class MiniStudentCourseBag implements Serializable {

	private MiniStudentInfo[] miniStudentInfoArray;

	private int nElems;

	public MiniStudentCourseBag(int maxSize) {
		miniStudentInfoArray = new MiniStudentInfo[maxSize];
		nElems = 0;
	}

	public void insert(MiniStudentInfo miniStudentInfo) {

		miniStudentInfoArray[nElems++] = miniStudentInfo;
	}

	public MiniStudentInfo[] findBy(Predicate<MiniStudentInfo> searchParam) {
		MiniStudentInfo[] results = new MiniStudentInfo[nElems];
		int matchCount = 0;
		for (int i = 0; i < nElems; i++) {

			if (searchParam.test(miniStudentInfoArray[i])) {

				results[matchCount++] = miniStudentInfoArray[i];
			}

		}

		return Arrays.copyOf(results, matchCount);

	}

	public MiniStudentInfo removeBy(Predicate<MiniStudentInfo> searchParam) {

		int matchCount = 0;
		int i;
		for (i = 0; i < nElems; i++) {

			if (searchParam.test(miniStudentInfoArray[i])) {
				break;
			}
		}
		if (i == nElems) {
			System.out.println("enter alert here noone found");

			return null;

		}

		else {

			MiniStudentInfo temp = miniStudentInfoArray[i];
			for (int j = i; j < nElems - 1; j++) {

				miniStudentInfoArray[i] = miniStudentInfoArray[i + 1];
			}

			nElems--;
			System.out.println("add alert here removed");
			return temp;

		}

	}

	public MiniStudentInfo[] getMiniStudentInfoArray() {
		return miniStudentInfoArray;
	}

	public int getnElems() {
		
		return nElems;
	}
}

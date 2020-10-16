package model;

import java.io.Serializable;

public enum LetterGrade implements Serializable {

	A, B_PLUS, B, C_PLUS, C, D_PLUS, D, F, NO_GRADE;

	public static LetterGrade toEnum(String grade) {

		switch (grade) {

		case "A":
			return A;
		case "B+":
			return B_PLUS;
		case "B":
			return B;
		case "C+":
			return C_PLUS;
		case "C":
			return C;
		case "D+":
			return D_PLUS;
		case "D":
			return D;
		case "F":
			return F;
		case "NO GRADE":
			return NO_GRADE;
		default:
			return NO_GRADE;

		}

	}
}

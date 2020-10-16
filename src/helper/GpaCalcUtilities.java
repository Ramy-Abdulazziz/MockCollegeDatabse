package helper;

import java.text.DecimalFormat;

import application.Main;
import model.CourseStatus;
import model.DivisionByZeroException;
import model.MiniStudentCourseBag;
import model.MiniStudentInfo;

public class GpaCalcUtilities {

	public static double convertLetterGrade(MiniStudentInfo course) {

		double gpaPoints = 0;

		switch (course.getLetterGrade()) {

		case A:
			gpaPoints = 4.0;
			break;
		case B_PLUS:
			gpaPoints = 3.5;
			break;
		case B:
			gpaPoints = 3.0;
			break;
		case C_PLUS:
			gpaPoints = 2.5;
			break;
		case C:
			gpaPoints = 2.0;
			break;
		case D_PLUS:
			gpaPoints = 1.5;
			break;
		case D:
			gpaPoints = 1.0;
			break;
		case F:
			gpaPoints = 0.0;
			break;
		case NO_GRADE:
			gpaPoints = 0.0;
		default:
			gpaPoints = 0.0;

		}

		return gpaPoints;

	}

	public static double calcGpa(MiniStudentCourseBag courses) {

		double gradePoints = 0;
		int totalCredits = 0;
		double gpa = 0;

		for (int i = 0; i < courses.getnElems(); i++) {

			if (courses.getMiniStudentInfoArray()[i].getCourseStatus() == CourseStatus.TAKEN) {
				String courseNumber = courses.getMiniStudentInfoArray()[i].getCourseNumber();
				gradePoints += Main.college.getCourseBag().findByNum(courseNumber).getNumberOfCredits()
						* convertLetterGrade(courses.getMiniStudentInfoArray()[i]);
				totalCredits += Main.college.getCourseBag().findByNum(courseNumber).getNumberOfCredits();
			}

		}

		if (totalCredits == 0) {
			try {
				throw new DivisionByZeroException();
			} catch (DivisionByZeroException e) {

				gpa = 0;
			}

		} else {

			gpa = gradePoints / totalCredits;
			DecimalFormat gpaFormat = new DecimalFormat("#.##");
			gpa = Double.valueOf(gpaFormat.format(gpa));
		}

		return gpa;

	}

}

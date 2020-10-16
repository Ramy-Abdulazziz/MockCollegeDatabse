package model;

import java.io.Serializable;

public class MiniStudentInfo implements Serializable {

	private String courseNumber;
	private LetterGrade letterGrade;
	private CourseStatus courseStatus;

	public MiniStudentInfo(String courseNumber, LetterGrade letterGrade, CourseStatus courseStatus) {
		super();
		this.courseNumber = courseNumber;
		this.letterGrade = letterGrade;
	}

	public MiniStudentInfo(String courseNumber) {
		super();
		this.courseNumber = courseNumber;
		this.letterGrade = LetterGrade.NO_GRADE;
		this.courseStatus = CourseStatus.TO_TAKE;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public LetterGrade getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(LetterGrade letterGrade) {
		this.letterGrade = letterGrade;
	}

	public CourseStatus getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(CourseStatus courseStatus) {

		this.courseStatus = courseStatus;

	}

}

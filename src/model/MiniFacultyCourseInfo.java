package model;

import java.io.Serializable;

public class MiniFacultyCourseInfo implements Serializable {

	private String courseNumber;
	private CourseStatus courseStatus;

	public MiniFacultyCourseInfo(String courseNumber, CourseStatus courseStatus) {
		super();
		this.courseNumber = courseNumber;
		this.courseStatus = courseStatus;
	}

	public MiniFacultyCourseInfo(String courseNumber) {
		this.courseNumber = courseNumber;
		this.courseStatus = CourseStatus.TEACHING;

	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public CourseStatus getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(CourseStatus courseStatus) {
		this.courseStatus = courseStatus;
	}

}

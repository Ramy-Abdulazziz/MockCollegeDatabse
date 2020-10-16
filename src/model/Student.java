package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import helper.BagFillUtilities;
import helper.GpaCalcUtilities;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Student extends Person implements Serializable {

	private Major major;
	private double gpa;
	private String phone;
	private MiniStudentCourseBag miniStudentCourseBag;
	private Name name;
	private String id;

	public Student(Name name, Major major, String phone) throws FileNotFoundException {
		super(name);
		this.name = name;
		this.major = major;
		this.phone = phone;
		this.miniStudentCourseBag = fillMiniStudentCourseBagByMajor(major);
		this.gpa = GpaCalcUtilities.calcGpa(miniStudentCourseBag);

	}

	public Student(String id, Name name, Major major, String phone, double gpa) throws FileNotFoundException {
		super(name);
		this.name = name;
		this.major = major;
		this.phone = phone;
		this.gpa = gpa;
		this.id = id;
		this.miniStudentCourseBag = fillMiniStudentCourseBagByMajor(major);

		setId(this.id);

	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [major=" + major + ", gpa=" + gpa + ", phone=" + phone + ", Name = " + name + "]";
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(Double newGpa) {

		this.gpa = newGpa;

	}

	public MiniStudentCourseBag getMiniStudentCourseBag() {
		return miniStudentCourseBag;
	}

	public MiniStudentCourseBag fillMiniStudentCourseBagByMajor(Major major) throws FileNotFoundException {
		MiniStudentCourseBag bag = null;
		try {
			bag = BagFillUtilities.fillWithMajorCourses(major);
		} catch (IOException e) {
			Alert fileNotFound = new Alert(AlertType.ERROR);
			fileNotFound.setTitle("File Import Failed");
			fileNotFound.setContentText("Please check major courses File");
			e.printStackTrace();
		}
		return bag;
	}

	public void UpdateGpa() {

		this.gpa = GpaCalcUtilities.calcGpa(miniStudentCourseBag);
	}

}

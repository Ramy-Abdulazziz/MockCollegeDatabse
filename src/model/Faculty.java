package model;

import java.io.IOException;
import java.io.Serializable;

import helper.BagFillUtilities;
import helper.GenRandom;

public class Faculty extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336478410378574014L;
	private Title title;
	private Major dept;
	private double salary;
	private String officePhone;
	private MiniFacultyCourseBag miniFacultyCourseBag;
	private String firstName;
	private String id;

	public Faculty(Name name, Title title, String officePhone, Major dept) throws IOException {
		super(name);
		this.dept = dept;
		this.title = title;
		this.officePhone = officePhone;
		this.salary = GenRandom.facultySalary(title);
		this.firstName = name.getFistName();
		miniFacultyCourseBag = new MiniFacultyCourseBag(CollegeInterface.MAX_BAG_SIZE);
		miniFacultyCourseBag = fillMiniFacultyCourseBagByDept(dept);

	}

	public Faculty(String id, Name name, Title title, String officePhone, Major dept, double salary) throws IOException {
		super(name);
		setId(id);
		this.title = title;
		this.officePhone = officePhone;
		this.dept = dept;
		this.salary = salary;

		miniFacultyCourseBag = new MiniFacultyCourseBag(CollegeInterface.MAX_BAG_SIZE);
		miniFacultyCourseBag = fillMiniFacultyCourseBagByDept(dept);

		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setDept(Major dept) {
		this.dept = dept;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setMiniFacultyCourseBag(MiniFacultyCourseBag miniFacultyCourseBag) {
		this.miniFacultyCourseBag = miniFacultyCourseBag;
	}

	public String getfirtName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public Title getTitle() {
		return title;
	}

	public Major getDept() {
		return dept;
	}

	public double getSalary() {
		return salary;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public MiniFacultyCourseBag getMiniFacultyCourseBag() {
		return miniFacultyCourseBag;
	}

	private MiniFacultyCourseBag fillMiniFacultyCourseBagByDept(Major dept) throws IOException {
		MiniFacultyCourseBag bag = null;
		bag = BagFillUtilities.fillWithFacultyCourses(dept);
		return bag;
	}

}

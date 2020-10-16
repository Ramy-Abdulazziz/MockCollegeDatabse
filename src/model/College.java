package model;

import java.io.Serializable;

public class College implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006101076736139828L;
	private PersonBag personBag;
	private ClassroomBag classroomBag;
	private TextBookBag textBookBag;
	private MasterCourseBag courseBag;

	public College(PersonBag personBag, ClassroomBag classroomBag, TextBookBag textBookBag, MasterCourseBag courseBag) {
		super();
		this.personBag = personBag;
		this.classroomBag = classroomBag;
		this.textBookBag = textBookBag;
		this.courseBag = courseBag;
	}

	public College() {

		this.courseBag = new MasterCourseBag(CollegeInterface.MAX_BAG_SIZE);
		this.textBookBag = new TextBookBag(CollegeInterface.MAX_BAG_SIZE);
		this.personBag = new PersonBag(CollegeInterface.MAX_BAG_SIZE);
		this.classroomBag = new ClassroomBag(CollegeInterface.MAX_BAG_SIZE);

	}


	public PersonBag getPersonBag() {
		return personBag;
	}

	public void setPersonBag(PersonBag personBag) {
		this.personBag = personBag;
	}

	public ClassroomBag getClassroomBag() {
		return classroomBag;
	}

	public void setClassroomBag(ClassroomBag classroomBag) {
		this.classroomBag = classroomBag;
	}

	public TextBookBag getTextBookBag() {
		return textBookBag;
	}

	public void setTextBookBag(TextBookBag textBookBag) {
		this.textBookBag = textBookBag;
	}

	public MasterCourseBag getCourseBag() {
		return courseBag;
	}

	public void setCourseBag(MasterCourseBag courseBag) {
		this.courseBag = courseBag;
	}

}

package helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import application.Main;
import model.BuildingName;
import model.Classroom;
import model.CollegeInterface;
import model.Faculty;
import model.Major;
import model.Name;
import model.Student;
import model.Title;

public class GenRandom {

	public static void genStudents() throws FileNotFoundException {

		for (int i = 0; i < 500; i++) {
			Name name = BagFillUtilities.popName();
			Major major = genRandomMajors();
			String phone = "1234567890";
			Student student = new Student(name, major, phone);
			Main.college.getPersonBag().insert(student);
		}

	}

	public static void genFaculty() throws IOException {

		for (int i = 0; i < 500; i++) {

			Name name = BagFillUtilities.popName();
			Major major = genRandomMajors();
			String phone = "1234567890";
			Title title = genRandomTitle();
			Faculty faculty = new Faculty(name, title, phone, major);
			Main.college.getPersonBag().insert(faculty);
		}

	}

	public static Major genRandomMajors() {

		Random random = new Random();
		int indx = random.nextInt(4 - 1) + 1;
		Major major = Major.CSE;

		if (indx == 1) {

			major = Major.CSE;
		} else if (indx == 2) {

			major = Major.MAT;
		} else if (indx == 3) {

			major = Major.ENG;
		}

		return major;

	}

	public static String genRandomPhone() {
		Random random = new Random();
		int count = 0;
		String phone = "";
		while (count < 10) {
			random.nextInt();

			phone.concat(random.toString());

			count++;

		}

		return phone;

	}

	public static double facultySalary(Title title) {

		double salary = 0;

		if (title == Title.PROF) {

			salary = 60000;
		} else if (title == Title.ASSISTANT_PROF) {

			salary = 50000;
		} else if (title == Title.ASSOCIATE_PROF) {

			salary = 40000;
		} else if (title == Title.INSTRUCTOR) {

			salary = 30000;
		}
		return salary;
	}

	public static Title genRandomTitle() {

		Random random = new Random();
		int indx = random.nextInt(5 - 1) + 1;
		Title title = Title.ASSISTANT_PROF;

		if (indx == 1) {

			title = Title.ASSISTANT_PROF;
		} else if (indx == 2) {

			title = Title.ASSOCIATE_PROF;
		} else if (indx == 3) {

			title = Title.INSTRUCTOR;
		} else if (indx == 4) {

			title = Title.PROF;
		}

		return title;

	}

	public static void genClassrooms() {

		for (int i = 0; i < CollegeInterface.MAX_BAG_SIZE - 500; i++) {

			Classroom clasroom = new Classroom(genBuildings());

			Main.college.getClassroomBag().insert(clasroom);
		}

	}

	public static BuildingName genBuildings() {

		Random random = new Random();
		int indx = random.nextInt(5 - 1) + 1;
		BuildingName title = BuildingName.Islip;

		if (indx == 1) {

			title = BuildingName.Islip;
		} else if (indx == 2) {

			title = BuildingName.Riverhead;
		} else if (indx == 3) {

			title = BuildingName.NFL;
		} else if (indx == 4) {

			title = BuildingName.Smithtown;
		}

		return title;
	}

}

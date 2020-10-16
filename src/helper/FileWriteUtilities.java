package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import application.Main;
import javafx.stage.FileChooser;
import model.Classroom;
import model.ClassroomBag;
import model.College;
import model.Course;
import model.Faculty;
import model.MasterCourseBag;
import model.Name;
import model.PersonBag;
import model.Student;
import model.TextBook;
import model.TextBookBag;

public class FileWriteUtilities {

	public static void writeCollege() throws IOException {

		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("College.dat");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("BINARY files (*.bin)", "*.bin");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objectOut = new ObjectOutputStream(fileOutput);

		objectOut.writeObject(Main.college);

		objectOut.close();
	}


	public static void writeStudents() throws IOException {

		int numPeople = Main.college.getPersonBag().getNelms();
		Student[] students = new Student[numPeople];
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Students.txt");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter studentWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < numPeople; i++) {

			if (Main.college.getPersonBag().getPersonArray()[i] instanceof Student) {

				students[i] = (Student) Main.college.getPersonBag().getPersonArray()[i];
			}

		}
		for (int j = 0; j < students.length; j++) {
			String id = students[j].getId().toString();
			String firstName = students[j].getName().getFistName();
			String lastName = students[j].getName().getLastName();
			double gpa = students[j].getGpa();
			String gpaStr = Double.toString(gpa);
			String major = students[j].getMajor().toString();
			String phone = students[j].getPhone();

			studentWriter.write(id);
			studentWriter.newLine();
			studentWriter.write(gpaStr);
			studentWriter.newLine();
			studentWriter.write(firstName);
			studentWriter.newLine();
			studentWriter.write(lastName);
			studentWriter.newLine();
			studentWriter.write(major);
			studentWriter.newLine();
			studentWriter.write(phone);
			studentWriter.newLine();

		}
		studentWriter.close();
		fileWriter.close();

	}

	public static void writeFaculty() throws IOException {

		int numPeople = Main.college.getPersonBag().getNelms();
		Faculty[] faculty = new Faculty[numPeople];
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Faculty.txt");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter facultytWriter = new BufferedWriter(fileWriter);

		faculty = Main.college.getPersonBag().getFaculty();

		for (int j = 0; j < faculty.length; j++) {
			String id = faculty[j].getId();
			String firstName = faculty[j].getName().getFistName();
			String lastName = faculty[j].getName().getLastName();
			double salary = faculty[j].getSalary();
			String salaryStr = Double.toString(salary);
			String dept = faculty[j].getDept().toString();
			String phone = faculty[j].getOfficePhone();
			String title = faculty[j].getTitle().toString();

			facultytWriter.write(id);
			facultytWriter.newLine();
			facultytWriter.write(title);
			facultytWriter.newLine();
			facultytWriter.write(dept);
			facultytWriter.newLine();
			facultytWriter.write(firstName);
			facultytWriter.newLine();
			facultytWriter.write(lastName);
			facultytWriter.newLine();
			facultytWriter.write(phone);
			facultytWriter.newLine();
			facultytWriter.write(salaryStr);
			facultytWriter.newLine();

		}
		facultytWriter.close();
		fileWriter.close();

	}

	public static void writeCourses() throws IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Faculty.txt");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter courseWriter = new BufferedWriter(fileWriter);

		Course[] courses = new Course[Main.college.getCourseBag().getNelms()];
		courses = Main.college.getCourseBag().getCourseArray();

		for (int i = 0; i < Main.college.getCourseBag().getNelms(); i++) {

			String courseNumber = Main.college.getCourseBag().getCourseArray()[i].getCourseNumber();
			String courseTitle = Main.college.getCourseBag().getCourseArray()[i].getCourseTitle();
			String courseDescription = Main.college.getCourseBag().getCourseArray()[i].getCourseDescription();
			String credits = Double.toString(Main.college.getCourseBag().getCourseArray()[i].getNumberOfCredits());

			courseWriter.write(courseNumber);
			courseWriter.newLine();
			courseWriter.write(courseTitle);
			courseWriter.newLine();
			courseWriter.write(courseDescription);
			courseWriter.newLine();
			courseWriter.write(credits);
			courseWriter.newLine();

		}

		courseWriter.close();
		fileWriter.close();
	}

	public static void writeTexts() throws IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("textbooks.txt");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter textbookWriter = new BufferedWriter(fileWriter);

		TextBook[] textbooks = new TextBook[Main.college.getTextBookBag().getnElems()];
		textbooks = Main.college.getTextBookBag().getTextBookArray();

		for (int i = 0; i < Main.college.getTextBookBag().getnElems(); i++) {

			String bookTitle = Main.college.getTextBookBag().getTextBookArray()[i].getBookTitle();
			String isbn = Main.college.getTextBookBag().getTextBookArray()[i].getIsbn();
			Name[] authors = Main.college.getTextBookBag().getTextBookArray()[i].getAuthors();
			String price = Double.toString(Main.college.getTextBookBag().getTextBookArray()[i].getPrice());

			textbookWriter.write(bookTitle);
			textbookWriter.newLine();
			textbookWriter.write(isbn);
			textbookWriter.newLine();
			textbookWriter.write(Arrays.toString(authors));
			textbookWriter.newLine();
			textbookWriter.write(price);
			textbookWriter.newLine();

		}

		textbookWriter.close();
		fileWriter.close();

	}

	public static void writeClassrooms() throws IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("textbooks.txt");
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File file = fileChooser.showSaveDialog(Main.fileChoosePane);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter classroomWriter = new BufferedWriter(fileWriter);

		Classroom[] classroom = new Classroom[Main.college.getCourseBag().getNelms()];
		classroom = Main.college.getClassroomBag().getClassroomArray();

		for (int i = 0; i < Main.college.getClassroomBag().getnElems(); i++) {

			String buildingName = classroom[i].getBuildingName().toString();
			String roomNumber = classroom[i].getRoomNumber();

			classroomWriter.write(buildingName);
			classroomWriter.newLine();
			classroomWriter.write(roomNumber);
			classroomWriter.newLine();

		}

		classroomWriter.close();
		fileWriter.close();

	}

}

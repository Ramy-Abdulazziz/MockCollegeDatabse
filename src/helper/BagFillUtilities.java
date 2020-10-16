package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.BuildingName;
import model.Classroom;
import model.College;
import model.Course;
import model.Faculty;
import model.Major;
import model.MasterCourseBag;
import model.MiniFacultyCourseBag;
import model.MiniFacultyCourseInfo;
import model.MiniStudentCourseBag;
import model.MiniStudentInfo;
import model.Name;
import model.Student;
import model.TextBook;
import model.TextBookBag;
import model.Title;

public class BagFillUtilities {

	public static MiniStudentCourseBag fillWithMajorCourses(Major major) throws IOException {
		MiniStudentCourseBag bag = new MiniStudentCourseBag(100);

		File file = new File("MajorCourses/" + major.toString());
		InputStreamReader courses = new InputStreamReader(new FileInputStream(file));
		BufferedReader courseReader = new BufferedReader(courses);
		while (courseReader.ready()) {

			String courseNumber = courseReader.readLine();
			bag.insert(new MiniStudentInfo(courseNumber));
		}
		courseReader.close();
		return bag;
	}

	public static MiniFacultyCourseBag fillWithFacultyCourses(Major dept) throws IOException {
		MiniFacultyCourseBag bag = new MiniFacultyCourseBag(100);

		File file = new File("MajorCourses/" + dept.toString());
		InputStreamReader courses = new InputStreamReader(new FileInputStream(file));
		BufferedReader courseReader = new BufferedReader(courses);
		while (courseReader.ready()) {

			String courseNumber = courseReader.readLine();
			bag.insert(new MiniFacultyCourseInfo(courseNumber));
		}
		courseReader.close();
		return bag;
	}

	public static void fillMasterCourseBag(MasterCourseBag masterCourseBag) throws NumberFormatException, IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File selectedFile = fileChooser.showOpenDialog(Main.fileChoosePane);
		try {
			InputStreamReader courses = new InputStreamReader(new FileInputStream(selectedFile));
			BufferedReader courseReader = new BufferedReader(courses);
			while (courseReader.ready()) {
				courseReader.readLine();
				String courseNumber = courseReader.readLine();
				String courseTitle = courseReader.readLine();
				String description = courseReader.readLine();
				double credits = Double.parseDouble(courseReader.readLine());
				Course course = new Course(courseNumber, courseTitle, description, credits);
				Main.college.getCourseBag().insert(course);
			}
			courseReader.close();
			courses.close();
			Main.coursePane.refreshTable();

		} catch (Exception e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Trouble loading file operation cancelled");
		}

	}

	public static void fillTextBookBag(TextBookBag textBooks) throws IOException {

		try {
			InputStreamReader titles = new InputStreamReader(new FileInputStream("textbooks/textbook_titles"));
			InputStreamReader isbns = new InputStreamReader(new FileInputStream("textbooks/textbook_isbns"));
			BufferedReader titleReader = new BufferedReader(titles);
			BufferedReader isbnReader = new BufferedReader(isbns);

			while (titleReader.ready() && isbnReader.ready()) {
				String title = titleReader.readLine();
				String isbn = isbnReader.readLine();
				Name[] authors = getAuthorArray();
				double price = genRandomPrice();
				TextBook textBook = new TextBook(title, isbn, authors, price);
				textBooks.insert(textBook);

			}

			titleReader.close();
			isbnReader.close();
			Main.textbookPane.refreshTable();
			Alert success = new Alert(AlertType.INFORMATION);
			success.setContentText("textbooks added successfully");
			success.showAndWait();

		} catch (Exception e) {

			Alert findFile = new Alert(AlertType.ERROR);
			findFile.setContentText("Please make sure program resources are correct");

		}

	}

	public static void fillClassroomBag() {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File selectedFile = fileChooser.showOpenDialog(Main.fileChoosePane);
		try {
			InputStreamReader classrooms = new InputStreamReader(new FileInputStream(selectedFile));
			BufferedReader classroomReader = new BufferedReader(classrooms);
			while (classroomReader.ready()) {
				String buildingStr = classroomReader.readLine();
				String roomNumber = classroomReader.readLine();
				BuildingName building = BuildingName.valueOf(buildingStr);
				Classroom classroom = new Classroom(building, roomNumber);
				Main.college.getClassroomBag().insert(classroom);
			}
			classroomReader.close();
			classrooms.close();
			Main.classroomPane.refreshTable();

		} catch (Exception e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Trouble loading file operation cancelled");
		}

	}

	public static void fillStudents() throws FileNotFoundException {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File selectedFile = fileChooser.showOpenDialog(Main.fileChoosePane);
		try {
			InputStreamReader students = new InputStreamReader(new FileInputStream(selectedFile));
			BufferedReader studentReader = new BufferedReader(students);
			while (studentReader.ready()) {
				String id = studentReader.readLine();
				String gpa = studentReader.readLine();
				String firstName = studentReader.readLine();
				String lastName = studentReader.readLine();
				Name name = new Name(firstName, lastName);
				String majorStr = studentReader.readLine();
				Major major = Major.valueOf(majorStr);
				String phone = studentReader.readLine();
				Student student = new Student(id, name, major, phone, Double.parseDouble(gpa));
				Main.college.getPersonBag().insert(student);
			}
			studentReader.close();
			students.close();
			Main.studentPane.refreshTable();

		} catch (Exception e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Trouble loading file operation cancelled");
		}

	}

	public static void fillFaculty() {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(fileType);
		File selectedFile = fileChooser.showOpenDialog(Main.fileChoosePane);
		try {
			InputStreamReader faculty = new InputStreamReader(new FileInputStream(selectedFile));
			BufferedReader facultyReader = new BufferedReader(faculty);
			while (facultyReader.ready()) {
				String id = facultyReader.readLine();
				String titleStr = facultyReader.readLine();
				String deptStr = facultyReader.readLine();
				String firstName = facultyReader.readLine();
				String lastName = facultyReader.readLine();
				String phone = facultyReader.readLine();
				String salary = facultyReader.readLine();
				Name name = new Name(firstName, lastName);
				Major dept = Major.valueOf(deptStr);
				Title title = Title.getEnum(titleStr);
				Faculty faculties = new Faculty(id, name, title, phone, dept, Double.parseDouble(salary));
				Main.college.getPersonBag().insert(faculties);
			}
			facultyReader.close();
			facultyReader.close();
			Main.facultyPane.refreshTable();

		} catch (Exception e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Trouble loading file operation cancelled");
		}

	}

	public static void getCollege() throws ClassNotFoundException, IOException {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("BINARY files (*.bin)", "*.bin");
		fileChooser.getExtensionFilters().add(fileType);
		File selectedFile = fileChooser.showOpenDialog(Main.fileChoosePane);
		FileInputStream fileIn = new FileInputStream(selectedFile);
		ObjectInputStream objectInt = new ObjectInputStream(fileIn);
		College college = (College) objectInt.readObject();

		Main.college.setPersonBag(college.getPersonBag());
		Main.college.setClassroomBag(college.getClassroomBag());
		Main.college.setTextBookBag(college.getTextBookBag());
		Main.college.setCourseBag(college.getCourseBag());

		objectInt.close();
		Main.studentPane.refreshTable();
		Main.classroomPane.refreshTable();
		Main.textbookPane.refreshTable();
		Main.facultyPane.refreshTable();
		Main.coursePane.refreshTable();

	}

	private static double genRandomPrice() {
		DecimalFormat twoPlaces = new DecimalFormat("0.00");
		double price = Math.random() * 200;
		String priceStr = twoPlaces.format(price);
		price = Double.parseDouble(priceStr);
		return price;
	}

	public static Name[] getAuthorArray() throws FileNotFoundException {
		Name[] nameArray = new Name[4];
		int randomNumber = (int) (Math.random() * 4 + 1);

		for (int i = 0; i < randomNumber; i++) {

			nameArray[i] = popName();

		}

		Name[] compactArray = Arrays.copyOf(nameArray, randomNumber);
		return compactArray;

	}

	public static Name popName() throws FileNotFoundException {
		String firstName = popRandomName("RawData/FirstNames");
		String lastName = popRandomName("RawData/LastNames");
		Name name = new Name(firstName, lastName);
		return name;

	}

	private static String popRandomName(String fileName) throws FileNotFoundException {
		String[] names = FileReadUtilities.readFile(fileName);
		int randomNumber = (int) (Math.random() * names.length);
		String randomName = names[randomNumber];
		return randomName;

	}

}

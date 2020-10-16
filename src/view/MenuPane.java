package view;

import java.io.IOException;

import application.Main;
import helper.BagFillUtilities;
import helper.FileWriteUtilities;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;

public class MenuPane implements ButtonInterface {

	private MenuBar menuBar;

	private Menu fileMenu;

	private MenuItem exitItem;
	private MenuItem backupItem;
	private MenuItem restoreItem;

	private Menu importMenu;

	private MenuItem studentImportItem;
	private MenuItem facultyImportItem;
	private MenuItem courseImportItem;
	private MenuItem textbookImportItem;
	private MenuItem classroomImportItem;

	private Menu exportMenu;

	private MenuItem studentExportItem;
	private MenuItem facultyExportItem;
	private MenuItem courseExportItem;
	private MenuItem textbookExportItem;
	private MenuItem classroomExportItem;

	private Menu editMenu;

	private MenuItem studentItem;
	private MenuItem facultyItem;
	private MenuItem textbookItem;
	private MenuItem courseItem;
	private MenuItem classroomItem;

	private BorderPane root = new BorderPane();

	public MenuPane() {

		menuBar = new MenuBar();
		buildFileMenu();
		buildEditMenu();
		menuBar.setStyle("-fx-background-color: #7b9dd1;");

		setActions();

	}

	public void buildFileMenu() {

		this.fileMenu = new Menu("File");
		fileMenu.setStyle("-fx-font: 30px Tahoma;");

		this.importMenu = new Menu("Import");
		this.studentImportItem = new MenuItem("Student");
		this.facultyImportItem = new MenuItem("Faculty");
		this.courseImportItem = new MenuItem("Course");
		this.textbookImportItem = new MenuItem("Textbook");
		this.classroomImportItem = new MenuItem("Classroom");
		exitItem = new MenuItem("Exit");
		this.exportMenu = new Menu("Export");
		importMenu.getItems().addAll(studentImportItem, facultyImportItem, courseImportItem, textbookImportItem,
				classroomImportItem);

		this.exportMenu = new Menu("Export");
		this.studentExportItem = new MenuItem("Student");
		this.facultyExportItem = new MenuItem("Faculty");
		this.courseExportItem = new MenuItem("Course");
		this.textbookExportItem = new MenuItem("Textbook");
		this.classroomExportItem = new MenuItem("Classroom");
		exportMenu.getItems().addAll(studentExportItem, facultyExportItem, courseExportItem, textbookExportItem,
				classroomExportItem);

		this.backupItem = new MenuItem("Backup");
		this.restoreItem = new MenuItem("Restore");
		fileMenu.getItems().addAll(importMenu, exportMenu, new SeparatorMenuItem(), backupItem, restoreItem,
				new SeparatorMenuItem(), exitItem);

	}

	public void buildEditMenu() {

		this.editMenu = new Menu("Edit");
		editMenu.setStyle("-fx-font: 30px Tahoma;");

		this.studentItem = new MenuItem("Student");
		this.facultyItem = new MenuItem("Faculty");
		this.courseItem = new MenuItem("Course");
		this.textbookItem = new MenuItem("Textbook");
		this.classroomItem = new MenuItem("Classroom");
		editMenu.getItems().addAll(studentItem, facultyItem, new SeparatorMenuItem(), courseItem, textbookItem,
				new SeparatorMenuItem(), classroomItem);

	}

	@Override
	public void setActions() {

		studentItem.setOnAction(e -> {

			Main.mainPane.setCenter(Main.studentPanePage);

		});

		facultyItem.setOnAction(e -> {

			Main.mainPane.setCenter(Main.facultyPanePage);

		});
		courseItem.setOnAction(e -> {

			Main.mainPane.setCenter(Main.coursePanePage);

		});
		textbookItem.setOnAction(e -> {

			Main.mainPane.setCenter(Main.textbookPanePage);

		});

		classroomItem.setOnAction(e -> {

			Main.mainPane.setCenter(Main.classroomPanePage);

		});

		studentExportItem.setOnAction(e -> {

			try {
				FileWriteUtilities.writeStudents();
			} catch (IOException e1) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");
			}

		});

		studentImportItem.setOnAction(e -> {

			try {
				BagFillUtilities.fillStudents();
				Main.studentPane.refreshTable();
			} catch (Exception e2) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("EXPORT FAILED");

			}
		});
		facultyExportItem.setOnAction(e -> {

			try {

				FileWriteUtilities.writeFaculty();
			} catch (IOException e3) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("EXPORT FAILED");

			}
		});
		facultyImportItem.setOnAction(e -> {

			try {
				BagFillUtilities.fillFaculty();
				Main.coursePane.refreshTable();
			} catch (Exception e4) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}

		});

		courseImportItem.setOnAction(e -> {

			try {
				BagFillUtilities.fillMasterCourseBag(Main.college.getCourseBag());
				Main.coursePane.refreshTable();
			} catch (NumberFormatException | IOException e5) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}

		});

		courseExportItem.setOnAction(e -> {

			try {
				FileWriteUtilities.writeCourses();
			} catch (Exception e6) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");

			}

		});

		textbookImportItem.setOnAction(e -> {

			try {
				BagFillUtilities.fillTextBookBag(Main.college.getTextBookBag());
			} catch (IOException e7) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}

		});
		textbookExportItem.setOnAction(e -> {
			try {
				FileWriteUtilities.writeTexts();
			} catch (Exception e8) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");

			}

		});

		classroomExportItem.setOnAction(e -> {

			try {

				FileWriteUtilities.writeClassrooms();

			} catch (Exception e9) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");

			}
		});

		classroomImportItem.setOnAction(e -> {

			try {
				BagFillUtilities.fillClassroomBag();
				Main.classroomPane.refreshTable();
			} catch (Exception e10) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}

		});

		exitItem.setOnAction(e -> {

			System.exit(0);

		});
		backupItem.setOnAction(e -> {

			try {

				FileWriteUtilities.writeCollege();

			} catch (Exception e11) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");

			}

		});
		restoreItem.setOnAction(e -> {

			try {
				BagFillUtilities.getCollege();
				Main.studentPane.refreshTable();
				Main.facultyPane.refreshTable();
				Main.classroomPane.refreshTable();
				Main.coursePane.refreshTable();
				Main.textbookPane.refreshTable();
				

			} catch (Exception e12) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}
		});

	}

	public MenuBar buildMenuBar() {
		this.menuBar.getMenus().addAll(this.fileMenu, this.editMenu);
		return menuBar;

	}

}

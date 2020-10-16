package view;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;
import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.CourseStatus;
import model.LetterGrade;
import model.Major;
import model.Name;
import model.Student;

public class StudentPane implements ButtonInterface {

	private VBox root;

	private TextField firstNameField;
	private TextField lastNameField;

	private TextField idField;
	private TextField phoneFIeld;
	private TextField gpaField;

	private HBox myButtonBar;
	private ButtonPane myButtons;
	private GridPane gridPane;
	private Button updateGrade;
	private Student rowData;

	private ComboBox<String> majorField;
	private ComboBox<String> gradeField;

	private ObservableList<Student> students;
	private TableView<Student> table;

	public StudentPane() {
		Init();
		buildTextFields();
		setTextPrompts();
		myButtons = new ButtonPane();
		myButtonBar = new HBox();
		myButtonBar = myButtons.getButtonBox();

		root.getChildren().addAll(gridPane, myButtonBar);
		root.setStyle("-fx-font: 25px Tahoma;");

		setActions();
	}

	private void buildTextFields() {

		VBox labelBox = new VBox(30);
		VBox textFieldsBox = new VBox(10);

		HBox nameLine = new HBox(5);
		Label nameLabel = new Label("NAME");
		nameLabel.setStyle("-fx-text-fill: #10417d;");

		nameLine.getChildren().addAll(firstNameField, lastNameField);

		HBox phoneLine = new HBox(200);
		Label phoneLabel = new Label("PHONE");
		phoneLabel.setStyle("-fx-text-fill: #10417d;");

		phoneLine.getChildren().addAll(phoneFIeld);

		HBox idLine = new HBox(200);
		Label idLabel = new Label("ID");
		idLabel.setStyle("-fx-text-fill: #10417d;");

		idLine.getChildren().addAll(idField);

		HBox gpaLine = new HBox(200);
		Label gpaLabel = new Label("GPA");
		gpaLabel.setStyle("-fx-text-fill: #10417d;");

		gpaLine.getChildren().addAll(gpaField);

		HBox majorLine = new HBox(200);
		Label majorLabel = new Label("Major");
		majorLabel.setStyle("-fx-text-fill: #10417d;");

		ObservableList<String> majors = FXCollections.observableArrayList("CSE", "ENG", "MAT");

		majorField.setItems(majors);
		majorLine.getChildren().addAll(majorField);

		labelBox.getChildren().addAll(nameLabel, phoneLabel, idLabel, gpaLabel, majorLabel);
		textFieldsBox.getChildren().addAll(nameLine, phoneLine, idLine, gpaLine, majorLine);

		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(80);
		gridPane.add(labelBox, 2, 2);
		gridPane.add(textFieldsBox, 5, 2);

		try {
			table = new TableView<>();
			table = genTable();
			gridPane.add(table, 7, 2);
		} catch (NullPointerException e) {

			System.out.println("this is null");
		}
		gridPane.setAlignment(Pos.CENTER);

	}

	@SuppressWarnings("unchecked")
	public TableView<Student> genTable() {

		students = FXCollections.observableArrayList();
		Student[] match;
		match = Main.college.getPersonBag().getStudents();

		students.addAll(match);
		TableView<Student> table = new TableView<Student>();
		table.setRowFactory(onClick -> {

			TableRow<Student> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					rowData = row.getItem();
					Alert moreInfo = new Alert(AlertType.CONFIRMATION);
					moreInfo.setTitle(rowData.getName().getFistName());
					moreInfo.setContentText("Click ok for more information about " + rowData.getName().getFistName());
					moreInfo.setHeaderText("Would you like more info?");

					Optional<ButtonType> result = moreInfo.showAndWait();
					ButtonType button = result.orElse(ButtonType.CANCEL);

					if (button == ButtonType.OK) {
						Alert details = new Alert(AlertType.INFORMATION);
						details.setTitle(rowData.getName().toString() + "'s details");
						details.setHeaderText(rowData.getName().toString() + "'s details");
						GridPane personDetails = new GridPane();

						ObservableList<String> grades = FXCollections.observableArrayList("A", "B+", "B", "C+", "C",
								"D+", "D", "F", "NO GRADE");
						gradeField = new ComboBox<String>();
						gradeField.setItems(grades);

						personDetails.addRow(0, new Label("Name:"));
						personDetails.addRow(1,
								new Label(rowData.getName().getFistName() + " " + rowData.getName().getLastName()));
						personDetails.addRow(2, new Label("ID:"));
						personDetails.addRow(3, new Label(rowData.getId()));
						personDetails.addRow(4, new Label("Courses:"));
						GridPane courseDetails = new GridPane();
						int rowNumber = 5;
						for (int i = 0; i < rowData.getMiniStudentCourseBag().getnElems(); i++) {

							int indx = i;
							updateGrade = new Button("update grade");

							updateGrade.setOnAction(action -> {

								Alert getGrade = new Alert(AlertType.CONFIRMATION);
								getGrade.setTitle("Please update the grade");
								getGrade.setContentText("Update the grade here");
								getGrade.getDialogPane().setContent(gradeField);

								getGrade.showAndWait();
								try {
									String gradeStr = gradeField.getSelectionModel().getSelectedItem();
									LetterGrade grade = LetterGrade.toEnum(gradeStr);
									String id = rowData.getId();
									Main.college.getPersonBag().isStudent(id).getMiniStudentCourseBag()
											.getMiniStudentInfoArray()[indx].setLetterGrade(grade);
									Main.college.getPersonBag().isStudent(id).getMiniStudentCourseBag()
											.getMiniStudentInfoArray()[indx].setCourseStatus(CourseStatus.TAKEN);
									rowData.UpdateGpa();
									table.refresh();
								} catch (NullPointerException e) {

									Alert pleaseChoose = new Alert(AlertType.ERROR);
									pleaseChoose.setTitle("Please choose a grade");
									pleaseChoose.setContentText("To update please choose a grade");
									pleaseChoose.showAndWait();

								}

							});

							HBox gradeCol = new HBox(10);
							GridPane buttonGrid = new GridPane();
							buttonGrid.addRow(i, updateGrade);
							Label courses = new Label(
									rowData.getMiniStudentCourseBag().getMiniStudentInfoArray()[i].getCourseNumber()
											+ " " + rowData.getMiniStudentCourseBag().getMiniStudentInfoArray()[i]
													.getLetterGrade().toString());
							gradeCol.getChildren().add(courses);

							courseDetails.addRow(rowNumber, gradeCol, buttonGrid);
							rowNumber++;

						}

						personDetails.addRow(rowNumber++, courseDetails);
						personDetails.addRow(rowNumber++, new Label("GPA"));
						personDetails.addRow(rowNumber, new Label(Double.toString(rowData.getGpa())));

						details.getDialogPane().setContent(personDetails);
						details.showAndWait();

					}

				}
			});
			return row;
		});

		table.setItems(students);

		TableColumn<Student, String> nameColumn = new TableColumn<>(("Name"));
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>(("name")));

		TableColumn<Student, Major> majorColumn = new TableColumn<>("Major");
		majorColumn.setMinWidth(100);
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));

		TableColumn<Student, Double> gpaColumn = new TableColumn<>("GPA");
		gpaColumn.setMinWidth(100);
		gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));

		TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(150);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		table.setItems(students);
		table.getColumns().addAll(nameColumn, majorColumn, gpaColumn, idColumn);

		return table;
	}

	public void Init() {
		root = new VBox(300);
		firstNameField = new TextField();
		lastNameField = new TextField();

		idField = new TextField();
		phoneFIeld = new TextField();
		gpaField = new TextField();
		majorField = new ComboBox<>();

	}

	public void setTextPrompts() {

		firstNameField.setPromptText("FIRST");
		lastNameField.setPromptText("LAST");

		idField.setPromptText("ID");
		idField.setDisable(true);
		phoneFIeld.setPromptText("PHONE");
		gpaField.setPromptText("GPA");
		gpaField.setDisable(true);
		majorField.setPromptText("MAJOR");

	}

	@Override
	public void setActions() {

		myButtons.getInsertBtn().setOnAction(e -> {

			try {
				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String majorString = majorField.getValue();
				String phone = phoneFIeld.getText();
				Major major;
				major = Major.valueOf(majorString);
				Name name = new Name(firstName, lastName);
				Student student = new Student(name, major, phone);
				Main.college.getPersonBag().insert(student);
				students.add(0, student);
				clearText();

				table.requestFocus();
				table.getSelectionModel().select(student);
				table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
				table.scrollTo(student);

				Alert success = new Alert(AlertType.INFORMATION);
				success.setTitle("Success");
				success.setHeaderText("Success");
				success.setContentText(student.getName().getFistName() + " Was Added Successfully");
				success.showAndWait();

			} catch (FileNotFoundException e1) {
				System.out.println("bad format");
				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				idField.clear();
			} catch (NullPointerException e1) {

				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();

			}

		});

		myButtons.getSearchBtn().setOnAction(e -> {

			try {

				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Enter ID");
				dialog.setHeaderText("Please Enter The ID to Search");
				dialog.setContentText("Please Enter ID ");

				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String id = result.get();
					int idInt = Integer.parseInt(id);
					id = String.format("%08d", idInt);
					idField.setText(id);
					// Person match = Main.college.getPersonBag().searchById(id);
					Student match = Main.college.getPersonBag().isStudent(id);

					firstNameField.setText(match.getName().getFistName());
					lastNameField.setText(match.getName().getLastName());
					phoneFIeld.setText(match.getPhone());
					majorField.getSelectionModel().select(String.valueOf(match.getMajor()));
					gpaField.setPromptText(String.valueOf(match.getGpa()));

					table.requestFocus();
					table.getSelectionModel().select(match);
					table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
					table.scrollTo(match);
				}
			} catch (NullPointerException e2) {
				Alert dne = new Alert(AlertType.WARNING);
				dne.setTitle("No Such ID");
				dne.setHeaderText("Sorry!");
				dne.setContentText("We could not find them");
				dne.showAndWait();
				clearText();
			} catch (ClassCastException e3) {
				Alert noStudents = new Alert(AlertType.WARNING);
				noStudents.setTitle("No Students With ID");
				noStudents.setHeaderText("Sorry!");
				noStudents.setContentText("No Students With ID");
				noStudents.showAndWait();
				clearText();
			} catch (NumberFormatException e4) {

				Alert badInput = new Alert(AlertType.ERROR);
				badInput.setTitle("Bad Formatting");
				badInput.setHeaderText("Sorry!");
				badInput.setContentText("Please Try Again");
				badInput.showAndWait();
			}

		});

		myButtons.getRemoveBtn().setOnAction(e ->

		{

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter ID");
			dialog.setHeaderText("Please Enter The ID to remove");
			dialog.setContentText("Please Enter ID ");
			Optional<String> result = dialog.showAndWait();

			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Are You Sure?");
			confirm.setHeaderText("This Cannot Be Undone!");
			confirm.setContentText("Hit Ok To Continue");
			confirm.showAndWait();

			if (result.isPresent()) {
				String id = result.get();
				int idInt = Integer.parseInt(id);
				id = String.format("%08d", idInt);
				idField.setText(id);

				try {

					table.getItems().remove(Main.college.getPersonBag().removeById(id));
					table.refresh();
					clearText();

				} catch (NullPointerException e2) {
					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such ID");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find them");
					dne.showAndWait();
					idField.clear();
				}

			}

		});

		myButtons.getUpdateBtn().setOnAction(e -> {

			String id = idField.getText();

			try {

				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String phone = phoneFIeld.getText();
				Major major = Major.valueOf(majorField.getSelectionModel().getSelectedItem());
				Name name = new Name(firstName, lastName);

				Main.college.getPersonBag().isStudent(id).setName(name);
				Main.college.getPersonBag().isStudent(id).setMajor(major);
				Main.college.getPersonBag().isStudent(id).setPhone(phone);

				table.refresh();

			} catch (IllegalFormatException e3) {
				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();

			} catch (NullPointerException e3) {
				Alert notFound = new Alert(AlertType.CONFIRMATION);
				notFound.setTitle("Update Failed");
				notFound.setHeaderText("We could not Find Who You Were Looking For!");
				notFound.setContentText("We could not update");
				notFound.showAndWait();
				clearText();
			}

		});

	}

	public Pane getPane() {

		return root;
	}

	public void clearText() {

		firstNameField.clear();
		lastNameField.clear();
		idField.clear();
		phoneFIeld.clear();
		majorField.getSelectionModel().clearSelection();
	}

	public void refreshTable() {

		students.clear();
		students.addAll(Main.college.getPersonBag().getStudents());
		table.refresh();
	}

}

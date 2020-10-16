package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Optional;

import application.Main;
import helper.GenRandom;
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
import model.Faculty;
import model.Major;
import model.Name;
import model.Title;

public class FacultyPane implements ButtonInterface {

	private VBox root;

	private TextField firstNameField;
	private TextField lastNameField;

	private TextField idField;
	private TextField phoneField;
	private TextField salaryField;

	private HBox myButtonBar;
	private ButtonPane myButtons;
	private GridPane gridPane;
	private Button updateStatus;

	private ComboBox<String> deptField;
	private ComboBox<String> titleField;
	private ComboBox<String> statusField;

	private ObservableList<Faculty> faculties;
	private TableView<Faculty> table;

	public FacultyPane() {
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

		phoneLine.getChildren().addAll(phoneField);

		HBox idLine = new HBox(200);
		Label idLabel = new Label("ID");
		idLabel.setStyle("-fx-text-fill: #10417d;");

		idLine.getChildren().addAll(idField);

		HBox salaryLine = new HBox(200);
		Label salaryLabel = new Label("Salary");
		salaryLabel.setStyle("-fx-text-fill: #10417d;");

		salaryLine.getChildren().addAll(salaryField);

		HBox deptLine = new HBox(200);
		Label deptLabel = new Label("Dept");
		deptLabel.setStyle("-fx-text-fill: #10417d;");

		HBox titleLine = new HBox(200);
		Label titleLabel = new Label("Title");
		titleLabel.setStyle("-fx-text-fill: #10417d;");

		ObservableList<String> depts = FXCollections.observableArrayList("CSE", "ENG", "MAT");
		ObservableList<String> titles = FXCollections.observableArrayList("PROF", "ASSOCIATE PROF", "ASSISTANT PROF",
				"INSTRUCTOR");

		deptField.setItems(depts);
		deptLine.getChildren().addAll(deptField);

		titleField.setItems(titles);
		titleLine.getChildren().addAll(titleField);

		labelBox.getChildren().addAll(nameLabel, phoneLabel, idLabel, salaryLabel, deptLabel, titleLabel);
		textFieldsBox.getChildren().addAll(nameLine, phoneLine, idLine, salaryLine, deptLine, titleLine);

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

		}
		gridPane.setAlignment(Pos.CENTER);

	}

	@SuppressWarnings("unchecked")
	public TableView<Faculty> genTable() {

		faculties = FXCollections.observableArrayList();
		Faculty[] match;
		match = Main.college.getPersonBag().getFaculty();

		faculties.addAll(match);
		TableView<Faculty> table = new TableView<Faculty>();
		table.setRowFactory(onClick -> {

			TableRow<Faculty> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Faculty rowData = row.getItem();
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

						ObservableList<String> courseStatus = FXCollections.observableArrayList("TAUGHT", "TEACHING");
						statusField = new ComboBox<String>();
						statusField.setItems(courseStatus);

						personDetails.addRow(0, new Label("Name:"));
						personDetails.addRow(1,
								new Label(rowData.getName().getFistName() + " " + rowData.getName().getLastName()));
						personDetails.addRow(2, new Label("ID:"));
						personDetails.addRow(3, new Label(rowData.getId()));
						personDetails.addRow(4, new Label("Courses:"));
						GridPane courseDetails = new GridPane();
						int rowNumber = 5;
						for (int i = 0; i < rowData.getMiniFacultyCourseBag().getnElems(); i++) {

							int indx = i;
							updateStatus = new Button("update Course Status");

							updateStatus.setOnAction(actions -> {

								Alert getStatus = new Alert(AlertType.CONFIRMATION);
								getStatus.setTitle("Please update Status here");
								getStatus.setContentText("Update Status Here");
								getStatus.getDialogPane().setContent(statusField);

								getStatus.showAndWait();

								try {

									String statusStr = statusField.getSelectionModel().getSelectedItem();
									CourseStatus status = CourseStatus.getEnum(statusStr);
									String id = rowData.getId();
									Main.college.getPersonBag().isFaculty(id).getMiniFacultyCourseBag()
											.getMiniFacultyCourseInfoArray()[indx].setCourseStatus(status);
									table.refresh();

								} catch (NullPointerException e) {

									Alert pleaseChoose = new Alert(AlertType.ERROR);
									pleaseChoose.setTitle("Please choose a status");
									pleaseChoose.setContentText("To update please choose a status");
									pleaseChoose.showAndWait();

								}

							});

							HBox statusCol = new HBox(10);
							GridPane buttonGrid = new GridPane();
							buttonGrid.addRow(i, updateStatus);
							Label courses = new Label(
									rowData.getMiniFacultyCourseBag().getMiniFacultyCourseInfoArray()[i]
											.getCourseNumber() + " "
											+ rowData.getMiniFacultyCourseBag().getMiniFacultyCourseInfoArray()[i]
													.getCourseStatus().toString());
							statusCol.getChildren().add(courses);

							courseDetails.addRow(rowNumber, statusCol, buttonGrid);
							rowNumber++;

						}
						personDetails.addRow(rowNumber++, courseDetails);

						details.getDialogPane().setContent(personDetails);
						details.showAndWait();

					}

				}

			});

			return row;
		});

		table.setItems(faculties);

		TableColumn<Faculty, String> nameColumn = new TableColumn<>(("Name"));
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>(("name")));

		TableColumn<Faculty, Major> deptColumn = new TableColumn<>("Dept");
		deptColumn.setMinWidth(100);
		deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));

		TableColumn<Faculty, Double> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setMinWidth(100);
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

		TableColumn<Faculty, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(150);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Faculty, Title> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(100);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		table.setItems(faculties);
		table.getColumns().addAll(nameColumn, deptColumn, salaryColumn, idColumn, titleColumn);
		return table;
	}

	public void Init() {
		root = new VBox(300);
		firstNameField = new TextField();
		lastNameField = new TextField();

		idField = new TextField();
		phoneField = new TextField();
		salaryField = new TextField();
		deptField = new ComboBox<>();
		titleField = new ComboBox<>();

	}

	public void setTextPrompts() {

		firstNameField.setPromptText("FIRST");
		lastNameField.setPromptText("LAST");

		idField.setPromptText("ID");
		idField.setDisable(true);
		phoneField.setPromptText("PHONE");
		deptField.setPromptText("DEPT");
		titleField.setPromptText("Title");
		salaryField.setPromptText("Salary");
		salaryField.setDisable(true);

	}

	@Override
	public void setActions() {

		myButtons.getInsertBtn().setOnAction(e -> {

			try {
				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String deptStr = deptField.getValue();
				String phone = phoneField.getText();
				String titleStr = titleField.getValue();
				Title title;
				title = Title.getEnum(titleStr);
				Major dept;
				dept = Major.valueOf(deptStr);
				Name name = new Name(firstName, lastName);
				Faculty faculty = new Faculty(name, title, phone, dept);
				Main.college.getPersonBag().insert(faculty);
				faculties.add(0, faculty);
				clearText();

				table.requestFocus();
				table.getSelectionModel().select(faculty);
				table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
				table.scrollTo(faculty);

				Alert success = new Alert(AlertType.INFORMATION);
				success.setTitle("Success");
				success.setHeaderText("Success");
				success.setContentText(faculty.getName().getFistName() + " Was Added Successfully");
				success.showAndWait();

			} catch (FileNotFoundException e1) {
				System.out.println("bad format");
				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();
			} catch (NullPointerException e1) {

				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();

			} catch (IOException e1) {
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
					Faculty match = Main.college.getPersonBag().isFaculty(id);

					firstNameField.setText(match.getName().getFistName());
					lastNameField.setText(match.getName().getLastName());
					phoneField.setText(match.getOfficePhone());
					deptField.getSelectionModel().select(String.valueOf(match.getDept()));
					salaryField.setText(String.valueOf(match.getSalary()));
					titleField.getSelectionModel().select(String.valueOf(match.getTitle()));
					idField.setText(match.getId());
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
				noStudents.setTitle("No Faculty With ID");
				noStudents.setHeaderText("Sorry!");
				noStudents.setContentText("No Faculty With ID");
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
				String phone = phoneField.getText();
				Major dept = Major.valueOf(deptField.getSelectionModel().getSelectedItem());
				Title title = Title.getEnum(titleField.getSelectionModel().getSelectedItem());
				double salary = GenRandom.facultySalary(title);
				Name name = new Name(firstName, lastName);

				Main.college.getPersonBag().isFaculty(id).setName(name);
				Main.college.getPersonBag().isFaculty(id).setOfficePhone(phone);
				Main.college.getPersonBag().isFaculty(id).setDept(dept);
				Main.college.getPersonBag().isFaculty(id).setSalary(salary);
				Main.college.getPersonBag().isFaculty(id).setTitle(title);

				clearText();
				table.refresh();

				Alert success = new Alert(AlertType.CONFIRMATION);
				success.setTitle("success");
				success.setContentText(Main.college.getPersonBag().isFaculty(id).getFirstName() + " was updated");
				success.showAndWait();
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

	public void clearText() {

		firstNameField.clear();
		lastNameField.clear();
		idField.clear();
		phoneField.clear();
		deptField.getSelectionModel().clearSelection();
		titleField.getSelectionModel().clearSelection();
	}

	public void refreshTable() {
		faculties.clear();
		faculties.addAll(Main.college.getPersonBag().getFaculty());
		table.refresh();

	}
	
	

	public Pane getPane() {

		return root;
	}

}

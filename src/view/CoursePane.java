package view;

import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Course;

public class CoursePane implements ButtonInterface {

	private VBox root;

	private TextField courseTitle;
	private TextArea courseDesc;

	private TextField courseNumbers;
	private TextField numberCredits;

	private HBox myButtonBar;
	private ButtonPane myButtons;
	private GridPane gridPane;

	private ObservableList<Course> courses;

	private TableView<Course> table;

	public CoursePane() {
		Init();
		buildTextFields();
		setTextPrompts();
		myButtons = new ButtonPane();
		myButtonBar = new HBox();
		myButtonBar = myButtons.getButtonBox();
		root.getChildren().addAll(gridPane, myButtonBar);
		root.setStyle("-fx-font: 20px Tahoma;");
		setActions();
	}

	private void buildTextFields() {

		VBox labelBox = new VBox(25);
		VBox textFieldsBox = new VBox(10);

		HBox nameLine = new HBox(5);
		nameLine.getChildren().addAll(courseTitle);

		HBox descLine = new HBox(200);
		descLine.setMinWidth(15);
		descLine.getChildren().addAll(courseDesc);

		HBox courseNumberLine = new HBox(200);
		courseNumberLine.getChildren().addAll(courseNumbers);

		HBox creditsLine = new HBox(200);
		creditsLine.getChildren().addAll(numberCredits);

		textFieldsBox.getChildren().addAll(nameLine, descLine, courseNumberLine, creditsLine);

		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(80);
		gridPane.add(textFieldsBox, 5, 2);

		try {
			table = new TableView<>();
			table = genTable();
			gridPane.add(table, 7, 2);
		} catch (NullPointerException e) {

			e.getMessage();
		}
		gridPane.setAlignment(Pos.CENTER);

	}

	@SuppressWarnings("unchecked")
	public TableView<Course> genTable() {

		courses.addAll(Main.college.getCourseBag().getCourseArray());
		TableView<Course> table = new TableView<Course>();
		table.setItems(courses);
		table.setRowFactory(onClick -> {

			TableRow<Course> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {

					Course rowData = row.getItem();
					Alert moreInfo = new Alert(AlertType.CONFIRMATION);
					moreInfo.setTitle(rowData.getCourseTitle());
					moreInfo.setContentText("Click ok for more information about " + rowData.getCourseNumber());
					moreInfo.setHeaderText("Would you like more info?");
					Optional<ButtonType> result = moreInfo.showAndWait();
					ButtonType button = result.orElse(ButtonType.CANCEL);

					if (button == ButtonType.OK) {

						Alert details = new Alert(AlertType.INFORMATION);
						details.setTitle(rowData.getCourseNumber() + " details");
						details.setHeaderText(rowData.getCourseTitle() + " details");
						GridPane courseDetails = new GridPane();

						courseDetails.addRow(0, new Label("Course Number: "));
						courseDetails.addRow(1, new Label(rowData.getCourseNumber()));
						courseDetails.addRow(2, new Label("Course Title: "));
						courseDetails.addRow(3, new Label(rowData.getCourseTitle()));
						courseDetails.addRow(4, new Label("Course Description "));

						VBox courseDescriptionBox = new VBox(30);
						TextArea courseField = new TextArea();
						courseField.setText(rowData.getCourseDescription());
						courseField.setWrapText(true);
						courseDescriptionBox.getChildren().add(courseField);

						courseDetails.addRow(5, courseDescriptionBox);
						courseDetails.addRow(6, new Label("Credits: "));
						courseDetails.addRow(7, new Label(Double.toString(rowData.getNumberOfCredits())));

						details.getDialogPane().setContent(courseDetails);
						details.showAndWait();

					}

				}

			});
			return row;
		});

		TableColumn<Course, String> nameColumn = new TableColumn<>(("Name"));
		nameColumn.setMinWidth(50);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));

		TableColumn<Course, String> majorColumn = new TableColumn<>("TITLE");
		majorColumn.setMinWidth(200);
		majorColumn.setCellValueFactory(new PropertyValueFactory<>(("courseTitle")));

		TableColumn<Course, Double> salaryColumn = new TableColumn<>("Credits");
		salaryColumn.setMinWidth(100);
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCredits"));

		table.setItems(courses);
		table.getColumns().addAll(nameColumn, majorColumn, salaryColumn);

		return table;
	}

	public void Init() {
		root = new VBox(300);
		courseTitle = new TextField();
		courseTitle.setMinWidth(30);

		courseDesc = new TextArea();
		courseDesc.autosize();
		courseDesc.setWrapText(true);

		courseNumbers = new TextField();
		numberCredits = new TextField();
		courses = FXCollections.observableArrayList();

	}

	public void setTextPrompts() {

		courseTitle.setPromptText("Title");
		courseDesc.setPromptText("Description");
		courseNumbers.setPromptText("courseNumber");
		numberCredits.setPromptText("Credits");

	}

	@Override
	public void setActions() {

		myButtons.getInsertBtn().setOnAction(e -> {

			try {
				String courseTitleText = courseTitle.getText();
				String courseDescription = courseDesc.getText();
				String courseNumber = courseNumbers.getText();
				String credits = numberCredits.getText();
				Double creditsD = Double.parseDouble(credits);

				Course course = new Course(courseNumber, courseTitleText, courseDescription, creditsD);
				Main.college.getCourseBag().insert(course);
				courses.add(0, course);
				table.requestFocus();
				table.getSelectionModel().select(course);
				table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
				table.scrollTo(0);
				clearText();
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

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter Course Number");
			dialog.setHeaderText("Please Enter The Number to Search");
			dialog.setContentText("Please Enter Number ");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				String number = result.get();
				courseNumbers.setText(number);

				try {

					Course match = (Main.college.getCourseBag().findByNum(number));
					courseTitle.setText(match.getCourseTitle());
					courseDesc.setText(match.getCourseDescription());
					courseNumbers.setText(match.getCourseNumber());
					numberCredits.setText(String.valueOf(match.getNumberOfCredits()));

					table.requestFocus();
					table.getSelectionModel().select(match);
					table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
					table.scrollTo(match);

				} catch (NullPointerException e2) {
					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such ID");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find them");
					dne.showAndWait();
					clearText();

				} catch (NumberFormatException e4) {

					Alert badInput = new Alert(AlertType.ERROR);
					badInput.setTitle("Bad Formatting");
					badInput.setHeaderText("Sorry!");
					badInput.setContentText("Please Try Again");
					badInput.showAndWait();
				}

			}

		});

		myButtons.getRemoveBtn().setOnAction(e -> {

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter ID");
			dialog.setHeaderText("Please Enter The Course to remove");
			dialog.setContentText("Please Enter Course Number ");
			Optional<String> result = dialog.showAndWait();

			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Are You Sure?");
			confirm.setHeaderText("This Cannot Be Undone!");
			confirm.setContentText("Hit Ok To Continue");
			confirm.showAndWait();

			if (result.isPresent()) {
				String courseNumber = result.get();
				courseNumbers.setText(result.get());

				try {
					Course toRemove = (Main.college.getCourseBag().findByNum(courseNumber));
					Main.college.getCourseBag().removeBy(courseNumber.toString());

					table.getItems().remove(toRemove);
					table.refresh();
					clearText();

				} catch (NullPointerException e2) {
					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such ID");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find them");
					dne.showAndWait();
					clearText();
				}

			}

		});

		myButtons.getUpdateBtn().setOnAction(e -> {

			String courseNumber = courseNumbers.getText();

			try {

				String courseDescription = courseDesc.getText();
				Double numCredits = Double.parseDouble(numberCredits.getText());
				String courseTitleText = courseTitle.getText();

				Main.college.getCourseBag().findByNum(courseNumber).setCourseDescription(courseDescription);
				Main.college.getCourseBag().findByNum(courseNumber).setCourseTitle(courseTitleText);
				Main.college.getCourseBag().findByNum(courseNumber).setNumberOfCredits(numCredits);
				clearText();

				table.refresh();

			} catch (Exception e3) {
				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();
			}
		});

	}

	public Pane getPane() {

		return root;
	}

	public void clearText() {

		courseTitle.clear();
		courseNumbers.clear();
		courseDesc.clear();
		numberCredits.clear();

	}

	public void refreshTable() {
		courses.clear();
		courses.addAll(Main.college.getCourseBag().getCourseArray());
		table.refresh();
	}

}

package view;

import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.BuildingName;
import model.Classroom;

public class ClassroomPane implements ButtonInterface {

	private VBox root;

	private TextField roomNumberField;
	private ComboBox<String> buildingsField;
	private HBox myButtonBar;
	private ButtonPane myButtons;
	private GridPane gridPane;

	private ObservableList<Classroom> classrooms;
	//private ObservableList<String> buildings;

	private TableView<Classroom> table;

	public ClassroomPane() {

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

	public void buildTextFields() {

		VBox textFieldsBox = new VBox(50);

		HBox buildingNameLine = new HBox(200);
		buildingNameLine.setMinWidth(15);

		ObservableList<String> buildings = FXCollections.observableArrayList("Riverhead", "Islip", "Smithtown", "NFL");
		buildingsField.setItems(buildings);
		buildingNameLine.getChildren().addAll(buildingsField);

		HBox roomNumberLine = new HBox(200);
		roomNumberLine.setMinWidth(15);
		roomNumberLine.getChildren().addAll(roomNumberField);

		textFieldsBox.getChildren().addAll(buildingNameLine, roomNumberLine);

		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(50);
		gridPane.add(textFieldsBox, 3, 5);

		table = new TableView<>();
		table = genTable();
		gridPane.add(table, 5, 5);

		gridPane.setAlignment(Pos.CENTER);

	}

	@SuppressWarnings("unchecked")
	public TableView<Classroom> genTable() {
		classrooms = FXCollections.observableArrayList();
		classrooms.addAll(Main.college.getClassroomBag().getClassroomArray());
		table.setItems(classrooms);

		TableColumn<Classroom, String> buildingNameCol = new TableColumn<>(("Building"));
		buildingNameCol.setMaxWidth(500);
		buildingNameCol.setCellValueFactory(new PropertyValueFactory<>("buildingName"));

		TableColumn<Classroom, String> roomNumberCol = new TableColumn<>("Room Number");
		roomNumberCol.setMinWidth(100);
		roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

		table.setItems(classrooms);
		table.getColumns().addAll(buildingNameCol, roomNumberCol);

		return table;

	}

	public void Init() {

		root = new VBox(100);
		roomNumberField = new TextField();
		buildingsField = new ComboBox<String>();
	}

	public void setTextPrompts() {

		roomNumberField.setPromptText("ROOM NUMBER");
		roomNumberField.setDisable(true);
		buildingsField.setPromptText("BUILDING");

	}

	@Override
	public void setActions() {

		myButtons.getInsertBtn().setOnAction(e -> {

			try {
				BuildingName building = BuildingName.valueOf(buildingsField.getSelectionModel().getSelectedItem());
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Enter Room Number");
				dialog.setHeaderText("Please Enter The Room Number to insert");
				dialog.setContentText("Please Enter the room number ");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {

					String roomNumber = result.get();
					Classroom classroom = new Classroom(building, roomNumber);
					Main.college.getClassroomBag().insert(classroom);

					classrooms.add(0, classroom);
					clearText();

				}

			} catch (Exception e1) {
				Alert badInput = new Alert(AlertType.ERROR);
				badInput.setTitle("Bad Formatting");
				badInput.setHeaderText("Sorry!");
				badInput.setContentText("Please Try Again");
				badInput.showAndWait();

			}

		});

		myButtons.getSearchBtn().setOnAction(e -> {

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter room Number");
			dialog.setHeaderText("Please Enter The Room Number to Search");
			dialog.setContentText("Please Enter room number ");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {

				String roomNumber = result.get();

				try {

					Classroom match = Main.college.getClassroomBag().findBy(roomNumber);
					roomNumberField.setText(match.getRoomNumber());
					buildingsField.getSelectionModel().select(match.getBuildingName().toString());

					table.requestFocus();
					table.getSelectionModel().select(match);
					table.getSelectionModel().focus(table.getSelectionModel().getFocusedIndex());
					table.scrollTo(match);

				} catch (Exception e2) {

					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such ID");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find them");
					dne.showAndWait();
					clearText();

				}

			}

		});

		myButtons.getRemoveBtn().setOnAction(e -> {

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter Room Number");
			dialog.setHeaderText("Please Enter The room to remove");
			dialog.setContentText("Please Enter room Number ");
			Optional<String> result = dialog.showAndWait();

			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Are You Sure?");
			confirm.setHeaderText("This Cannot Be Undone!");
			confirm.setContentText("Hit Ok To Continue");
			confirm.showAndWait();

			if (result.isPresent()) {

				try {
					String roomNumber = result.get();
					Classroom toRemove = (Main.college.getClassroomBag().findBy(roomNumber));

					Main.college.getClassroomBag().removeBy(roomNumber);

					table.getItems().remove(toRemove);
					table.refresh();
					clearText();
				} catch (NullPointerException e2) {
					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such room");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find it");
					dne.showAndWait();
				}

			}
		});

		myButtons.getUpdateBtn().setOnAction(e -> {

			String roomNumber = roomNumberField.getText();
			String buildingName = buildingsField.getSelectionModel().getSelectedItem();

			try {
				Main.college.getClassroomBag().findBy(roomNumber).setBuildingName(BuildingName.valueOf(buildingName));
				table.refresh();
				clearText();
				Alert success = new Alert(AlertType.INFORMATION);
				success.setTitle("Success");
				success.setHeaderText(
						Main.college.getClassroomBag().findBy(roomNumber).getRoomNumber() + " was updated!");
				success.setContentText(
						Main.college.getClassroomBag().findBy(roomNumber).getRoomNumber() + " was updated!");
				success.showAndWait();

			} catch (Exception e4) {
				Alert badFormat = new Alert(AlertType.WARNING);
				badFormat.setTitle("BadFormatting");
				badFormat.setHeaderText("Sorry!");
				badFormat.setContentText("We could not update");
				badFormat.showAndWait();
				clearText();

			}

		});

	}

	public void clearText() {

		roomNumberField.clear();
		buildingsField.getSelectionModel().clearSelection();

	}
	
	public void refreshTable() {
		classrooms.clear();
		classrooms.addAll(Main.college.getClassroomBag().getClassroomArray());
		table.refresh();
		
	}

	public Pane getPane() {

		return root;

	}


}

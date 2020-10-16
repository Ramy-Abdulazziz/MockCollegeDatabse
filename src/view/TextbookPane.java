package view;

import java.util.Arrays;
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
import model.Name;
import model.TextBook;

public class TextbookPane implements ButtonInterface {

	private VBox root;

	private TextField bookTitleField;
	private TextArea authorsArea;

	private TextField priceField;
	private TextField isbnField;

	private HBox myButtonBar;
	private ButtonPane myButtons;
	private GridPane gridPane;

	private ObservableList<TextBook> textbooks;
	private TableView<TextBook> table;

	public TextbookPane() {

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

		VBox textFieldsBox = new VBox(10);

		HBox titleLine = new HBox(5);
		titleLine.getChildren().addAll(bookTitleField);

		HBox authorLine = new HBox(200);
		authorLine.setMinWidth(15);
		authorLine.getChildren().addAll(authorsArea);

		HBox priceLine = new HBox(200);
		priceLine.getChildren().addAll(priceField);

		HBox isbnLine = new HBox(200);
		isbnLine.getChildren().addAll(isbnField);

		textFieldsBox.getChildren().addAll(bookTitleField, authorsArea, priceField, isbnField);

		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(50);
		gridPane.add(textFieldsBox, 5, 2);

		try {
			table = new TableView<>();
			table = genTable();
			gridPane.add(table, 5, 3);
		} catch (NullPointerException e) {

		}
		gridPane.setAlignment(Pos.CENTER);

	}

	@SuppressWarnings("unchecked")
	public TableView<TextBook> genTable() {

		textbooks.addAll(Main.college.getTextBookBag().getTextBookArray());

		TableView<TextBook> table = new TableView<TextBook>();
		table.setItems(textbooks);
		table.setRowFactory(onClick -> {

			TableRow<TextBook> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {

					TextBook rowData = row.getItem();
					Alert moreInfo = new Alert(AlertType.CONFIRMATION);
					moreInfo.setTitle(rowData.getBookTitle());
					moreInfo.setContentText("Click ok for more information about " + rowData.getBookTitle());
					moreInfo.setHeaderText("Would you like more info?");
					Optional<ButtonType> result = moreInfo.showAndWait();
					ButtonType button = result.orElse(ButtonType.CANCEL);

					if (button == ButtonType.OK) {

						Alert details = new Alert(AlertType.INFORMATION);
						details.setTitle(rowData.getBookTitle() + " details");
						details.setHeaderText(rowData.getBookTitle() + " details");
						GridPane bookDetails = new GridPane();

						bookDetails.addRow(0, new Label("Title: "));
						bookDetails.addRow(1, new Label(rowData.getBookTitle()));
						bookDetails.addRow(2, new Label("ISBN: "));
						bookDetails.addRow(3, new Label(rowData.getIsbn()));
						bookDetails.addRow(4, new Label("Authors "));

						VBox courseDescriptionBox = new VBox(30);
						TextArea courseField = new TextArea();
						courseField.setText(Arrays.toString(rowData.getAuthors()));
						courseField.setWrapText(true);
						courseDescriptionBox.getChildren().add(courseField);

						bookDetails.addRow(5, courseDescriptionBox);
						bookDetails.addRow(6, new Label("Price: "));
						bookDetails.addRow(7, new Label(Double.toString(rowData.getPrice())));

						details.getDialogPane().setContent(bookDetails);
						details.showAndWait();

					}

				}

			});
			return row;
		});

		TableColumn<TextBook, String> titleColumn = new TableColumn<>(("Title"));
		titleColumn.setMaxWidth(500);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

		TableColumn<TextBook, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		table.setItems(textbooks);
		table.getColumns().addAll(titleColumn, priceColumn);

		return table;
	}

	public void Init() {
		root = new VBox(100);
		bookTitleField = new TextField();
		bookTitleField.setMinWidth(30);

		authorsArea = new TextArea();
		authorsArea.autosize();
		authorsArea.setWrapText(true);

		priceField = new TextField();
		isbnField = new TextField();
		textbooks = FXCollections.observableArrayList();

	}

	public void setTextPrompts() {

		bookTitleField.setPromptText("Title");
		authorsArea.setPromptText("Authors");
		priceField.setPromptText("Price");
		isbnField.setPromptText("ISBN");

	}

	public void setActions() {

		myButtons.getInsertBtn().setOnAction(e -> {

			try {
				String bookTitle = bookTitleField.getText();
				String isbn = convertIsbn(isbnField.getText());
				String priceStr = priceField.getText();
				double price = Double.valueOf(priceStr);
				Name[] authors = convertNames(authorsArea.getText());
				TextBook textBook = new TextBook(bookTitle, isbn, authors, price);
				Main.college.getTextBookBag().insert(textBook);

				textbooks.add(0, textBook);
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
			dialog.setTitle("Enter ISBN Number");
			dialog.setHeaderText("Please Enter The ISBN Number to Search");
			dialog.setContentText("Please Enter ISBN With NO DASHES! ");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				String isbnUnformatted = result.get();
				String isbnFormatted = convertIsbn(isbnUnformatted);
				isbnField.setText(isbnFormatted);

				try {

					TextBook match = Main.college.getTextBookBag().findByIsbn(isbnFormatted);
					bookTitleField.setText(match.getBookTitle());
					authorsArea.setText(Arrays.toString(match.getAuthors()));
					priceField.setText(Double.toString(match.getPrice()));

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
			dialog.setTitle("Enter ISBN");
			dialog.setHeaderText("Please Enter The ISBN to remove");
			dialog.setContentText("Please Enter ISBN Number ");
			Optional<String> result = dialog.showAndWait();

			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Are You Sure?");
			confirm.setHeaderText("This Cannot Be Undone!");
			confirm.setContentText("Hit Ok To Continue");
			confirm.showAndWait();

			if (result.isPresent()) {
				String isbnUnformatted = result.get();
				String isbnFormatted = convertIsbn(isbnUnformatted);
				isbnField.setText(isbnFormatted);

				try {
					TextBook toRemove = (Main.college.getTextBookBag().findByIsbn(isbnFormatted));
					Main.college.getTextBookBag().removeByIsbn(isbnFormatted);

					table.getItems().remove(toRemove);
					table.refresh();
					clearText();

				} catch (NullPointerException e2) {
					Alert dne = new Alert(AlertType.WARNING);
					dne.setTitle("No Such ID");
					dne.setHeaderText("Sorry!");
					dne.setContentText("We could not find them");
					dne.showAndWait();
				}

			}

		});

		myButtons.getUpdateBtn().setOnAction(e -> {

			String isbn = isbnField.getText();

			try {

				Name[] authors = convertNames(authorsArea.getText());
				String bookTitle = bookTitleField.getText();
				Double price = Double.parseDouble(priceField.getText());

				Main.college.getTextBookBag().findByIsbn(isbn).setAuthor(authors);
				Main.college.getTextBookBag().findByIsbn(isbn).setBookTitle(bookTitle);
				Main.college.getTextBookBag().findByIsbn(isbn).setPrice(price);

				table.refresh();
				clearText();
				Alert success = new Alert(AlertType.INFORMATION);
				success.setTitle("Success");
				success.setHeaderText(Main.college.getTextBookBag().findByIsbn(isbn).getBookTitle() + " updated");
				success.setContentText(Main.college.getTextBookBag().findByIsbn(isbn).getBookTitle()
						+ " has been successfully updated!");
				success.showAndWait();

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
		bookTitleField.clear();
		authorsArea.clear();
		priceField.clear();
		isbnField.clear();

	}

	public String convertIsbn(String string) {

		char[] chars = string.toCharArray();

		StringBuilder isbn = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {

			isbn.append(chars[i]);

			if (i == 2 || i == 3 || i == 5 || i == 11) {

				isbn.append("-");
			}

		}

		return isbn.toString();

	}

	public Name[] convertNames(String string) {

		String[] names = authorsArea.getText().split(", ");
		Name[] newAuthors = new Name[names.length * 2];
		int authorCount = 0;

		for (int i = 0; i < names.length; i++) {

			String[] seperatedNames = names[i].split(" ");
			for (int j = 0; j <= i; j++) {

				String firstName = seperatedNames[0];
				String lastName = seperatedNames[1];
				newAuthors[i] = new Name(firstName, lastName);

			}
			authorCount++;

		}

		Name[] compactAuthors = Arrays.copyOf(newAuthors, authorCount);
		return compactAuthors;
	}

	public void refreshTable() {

		textbooks.clear();
		textbooks.addAll(Main.college.getTextBookBag().getTextBookArray());
		table.refresh();
		table.autosize();

	}

}

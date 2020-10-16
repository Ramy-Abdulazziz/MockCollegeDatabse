package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ButtonPane {

	private HBox buttonBox;
	private Button insertBtn;
	private Button searchBtn;
	private Button updateBtn;
	private Button removeBtn;

	public ButtonPane() {

		init();
		initButtonNames();
		genButtonBar();
		setBarAlignment(buttonBox, "center");

	}

	public void init() {

		buttonBox = new HBox();
		insertBtn = new Button();
		searchBtn = new Button();
		updateBtn = new Button();
		removeBtn = new Button();

	}

	public void initButtonNames() {
		insertBtn.setText("Insert");
		searchBtn.setText("Search");
		updateBtn.setText("Update");
		removeBtn.setText("remove");
	}

	public void genButtonBar() {

		buttonBox.getChildren().addAll(insertBtn, searchBtn, updateBtn, removeBtn);

	}

	public void setBarAlignment(HBox buttonbar, String alignment) {

		buttonbar.setAlignment(Pos.valueOf(alignment.toUpperCase()));
		buttonbar.setSpacing(50);
	}

	public Button getInsertBtn() {
		return insertBtn;
	}

	public Button getSearchBtn() {
		return searchBtn;
	}

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Button getRemoveBtn() {
		return removeBtn;
	}

	public HBox getButtonBox() {

		return buttonBox;
	}

}

package view;

import application.Main;
import helper.BagFillUtilities;
import helper.FileWriteUtilities;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainStage {

	private HBox quickButtonBar;
	private VBox quickMenu;
	private Button quickRestore;
	private Button quickBackup;

	public MainStage() {

		init();
		genQuickMenu();
		setAction();
	}

	public void init() {

		quickButtonBar = new HBox();

		quickRestore = new Button();
		quickRestore.setMinSize(200, 200);
		BackgroundSize backgroundSizeDown = new BackgroundSize(50, 50, true, true, true, false);
		BackgroundImage imageDown = new BackgroundImage(new Image("downloadIcon.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeDown);
		quickRestore.setBackground(new Background(imageDown));

		quickBackup = new Button();
		quickBackup.setMinSize(200, 200);
		BackgroundSize backgroundSizeUp = new BackgroundSize(50, 50, true, true, true, false);
		BackgroundImage imageUp = new BackgroundImage(new Image("uploadIcon.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeUp);
		quickBackup.setBackground(new Background(imageUp));

		quickMenu = new VBox(50);

	}

	public void genQuickMenu() {
		Label quickMenuLabel = new Label("QUICK MENU");
		quickMenuLabel.setTextFill(Color.web("#6d74fc"));

		quickMenuLabel.setStyle("-fx-font: 100px Tahoma;");

		HBox quickMenuLabelBox = new HBox();
		quickMenuLabelBox.setStyle("-fx-background-color: #000000 ");
		;
		quickButtonBar.getChildren().addAll(quickRestore, quickBackup);
		quickButtonBar.setAlignment(Pos.CENTER);
		quickButtonBar.setSpacing(25);

		quickMenuLabel.setAlignment(Pos.CENTER);
		quickMenuLabelBox.setAlignment(Pos.CENTER);
		quickMenuLabelBox.getChildren().add(quickMenuLabel);
		quickMenu.getChildren().addAll(quickMenuLabelBox, quickButtonBar);

	}

	public VBox getQuickMenu() {
		return quickMenu;
	}

	public void setAction() {

		quickBackup.setOnAction(e -> {
			try {

				FileWriteUtilities.writeCollege();

			} catch (Exception e11) {
				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("SAVE FAILED");
			}

		});

		quickRestore.setOnAction(e -> {

			try {
				BagFillUtilities.getCollege();

			} catch (Exception e12) {

				Alert failed = new Alert(AlertType.ERROR);
				failed.setContentText("Import Failed Check File");

			}
		});

	}

}

package application;

import java.io.IOException;

import helper.GenRandom;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.College;
import view.ClassroomPane;
import view.CoursePane;
import view.FacultyPane;
import view.MainStage;
import view.MenuPane;
import view.StudentPane;
import view.TextbookPane;

public class Main extends Application {

	public static BorderPane mainPane;
	public static Pane studentPanePage;
	public static Pane facultyPanePage;
	public static Pane coursePanePage;
	public static Pane textbookPanePage;
	public static Pane classroomPanePage;
	public static Pane mainPanePage;
	public static Stage fileChoosePane;
	public static MenuBar myMenuBar;
	public static HBox myButtonBar;
	public static College college;
	public static CoursePane coursePane;
	public static TextbookPane textbookPane;
	public static FacultyPane facultyPane;
	public static StudentPane studentPane;
	public static ClassroomPane classroomPane;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		college = new College();
		GenRandom.genFaculty();
		GenRandom.genStudents();
		GenRandom.genClassrooms();

		
		MenuPane menuBar = new MenuPane();
		MainStage mainStage = new MainStage();
		VBox myMainContent = new VBox();

		studentPane = new StudentPane();
		facultyPane = new FacultyPane();
		coursePane = new CoursePane();
		textbookPane = new TextbookPane();
		classroomPane = new ClassroomPane();

		
		classroomPanePage = classroomPane.getPane();
		coursePanePage = coursePane.getPane();
		facultyPanePage = facultyPane.getPane();
		studentPanePage = studentPane.getPane();
		mainPanePage = mainStage.getQuickMenu();
		myMenuBar = menuBar.buildMenuBar();
		textbookPanePage = textbookPane.getPane();
		
		myMainContent.getChildren().add(mainPanePage);
		myMainContent.setAlignment(Pos.CENTER);

		mainPane = new BorderPane();
		mainPane.setMinSize(1500, 1000);
		mainPane.setTop(myMenuBar);

		mainPane.setCenter(myMainContent);
		BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
		BackgroundImage image = new BackgroundImage(new Image("akatsuki.jpg"), BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
		mainPane.setBackground(new Background(image));

		Scene main = new Scene(mainPane);
		primaryStage.setTitle("College Database");
		primaryStage.setScene(main);

		primaryStage.show();

	}

}

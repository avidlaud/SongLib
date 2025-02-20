/**
 * Ken Erickson and David Lau
 * CS213
 */

package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

public class SongLib extends Application {
	Stage mainStage;
	
	@Override
	public void start(Stage stage) {
		mainStage = stage;
		mainStage.setTitle("Song Library");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SongLibUI.fxml")); 
			AnchorPane pane = (AnchorPane)loader.load();
			
			Controller controller = loader.getController();
			controller.start();
			controller.setMainStage(mainStage);
			
			Scene scene = new Scene(pane, 600, 400); 
			controller.setScene(scene);
			controller.addScreen("mainScene", FXMLLoader.load(getClass().getResource("/view/SongLibUI.fxml")));
			controller.activate("mainScene");
			mainStage.setScene(scene);
			mainStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

public class App extends Application {
	Stage mainStage;
	
	@Override
	public void start(Stage stage) {
		mainStage = stage;
		mainStage.setTitle("Song Library");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/SongLib.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			
			Controller controller = loader.getController();
			controller.setMainStage(mainStage);
			
			Scene scene = new Scene(pane, 400, 300); 
			mainStage.setScene(scene);
			mainStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}

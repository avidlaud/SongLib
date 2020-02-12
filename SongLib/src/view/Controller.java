package view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.Song;

public class Controller {

	Stage mainStage;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Label labelArtist;
	
	@FXML
	private Label labelAlbum;
	
	@FXML
	private Label labelYear;
	
	@FXML
	private Button buttonAdd;
	
	@FXML
	private Button buttonEdit;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private ListView<Song> listView;
	
	private ObservableList<Song> songList;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	public void start() {
		songList = FXCollections.observableArrayList();
	}

}

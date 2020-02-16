package view;

import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	private String filePath;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	public void start() {
		songList = FXCollections.observableArrayList();//
		filePath = "app/songList.txt";
		try(BufferedReader fr = new BufferedReader(new FileReader(filePath))) { //populate songList from file
			String line = fr.readLine();
			while(line!=null) {
				String[] data = new String[4];
				data = line.split(" ",4);
				Song s = new Song(data[0],data[2],data[3],Integer.parseInt(data[4]));
				songList.add(s);
			}
		} catch (IOException e) {
			
		}
		
	}
	public void write(Song s) { //add a song to the list in alphabetical order by writing to the file
		if(read(s)) {
			return; //song already exists
		}
		if(songList == null) {
			return;
		}
		songList.add(s); //needs to be added in alphabetical order
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath))) { //write to file
			fw.write(s.getName()+" "+s.getArtist()+" "+s.getAlbum()+" "+s.getYear()+"\n");
		} catch (IOException e) {
			
		}
	}
	public boolean read(Song s) { //search the file to see if a song exists, return true if it exists in the file already and updates it if necessary
		if(songList == null) {
			return false;
		}
		for(Song so : songList) {
			if(so.getName().compareTo(s.getName())==0 && so.getArtist().compareTo(s.getArtist())==0) {
				so.setAlbum(s.getAlbum()); //update the album and year if needed
				so.setYear(s.getYear()); //need to write these changes to file as well
				return true;
			}
		}
		return false; //
	}

}

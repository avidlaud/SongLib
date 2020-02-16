package view;

import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import javafx.scene.layout.Pane;
import javafx.scene.*;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
	private TextArea name;
	
	@FXML
	private TextArea artist;
	
	@FXML
	private TextArea album;
	
	@FXML
	private TextArea year;
	
	@FXML
	private ListView<Song> listView;
	
	private ObservableList<Song> songList;
	
	private String filePath;
	
	private HashMap<String, Pane> screenMap = new HashMap<>();
	
	private Scene mainScene;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	public void start() {
		songList = FXCollections.observableArrayList();
		filePath = "app/songList.txt";
		try(BufferedReader fr = new BufferedReader(new FileReader(filePath))) { //populate songList from file
			String line = fr.readLine();
			while(line!=null) {
				String[] data = new String[4];
				data = line.split("\t",4);
				Song s = new Song(data[0],data[2],data[3],Integer.parseInt(data[4]));
				songList.add(s);
				Collections.sort(songList);
			}
		} catch (IOException e) {
			
		}
		
	}
	public void setScene(Scene scene) {
		mainScene=scene;
	}
	public void addScreen(String name, Pane pane) {
		screenMap.put(name,pane);
	}
	public void removeScreen(String name) {
		screenMap.remove(name);
	}
	public void activate(String name) {
		mainScene.setRoot(screenMap.get(name));
	}
	public void add(Song s) { //add a song to the list in alphabetical order by writing to the file 
		if(read(s)) {
			return; //song already exists
		}
		if(songList == null || songList.isEmpty()) {
			return;
		}
		songList.add(s); 
		Collections.sort(songList);
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true))) { //write to file
			fw.write(s.toString()+"\n");
		} catch (IOException e) {
			
		}
	}
	public boolean delete(Song s) { //removes target song from the songList, returns true if it exists and false if it wasnt in the list in the first place
		if(songList == null || songList.isEmpty()) {
			return false;
		} 
		int i=0;
		for(Song so : songList) {
			if(so.getName().compareTo(s.getName())==0 && so.getArtist().compareTo(s.getArtist())==0) {
				songList.remove(i);
				write();
				return true;
			}
			i++;
		}
		return false;
	}
	public boolean edit(Song s, Song n) { //looks for s in the list and replaces it with n if it exists, returns false if s was not in the list already
		if(songList == null || songList.isEmpty()) {
			return false;
		}
		for(Song so : songList) {
			if(so.getName().compareTo(s.getName())==0 && so.getArtist().compareTo(s.getArtist())==0) {
				s.setName(n.getName());
				s.setArtist(n.getArtist());
				s.setAlbum(n.getAlbum());
				s.setYear(n.getYear());
				write();
				return true;
			}
		}
		return false;
	}
	private boolean read(Song s) { //search the file to see if a song exists, return true if it exists in the file already and updates it if necessary
		if(songList == null || songList.isEmpty()) {
			return false;
		}
		for(Song so : songList) {
			if(so.getName().compareTo(s.getName())==0 && so.getArtist().compareTo(s.getArtist())==0) {
				boolean b = false;
				if(so.getAlbum().compareTo(s.getAlbum())!=0) {
					so.setAlbum(s.getAlbum()); //update the album and year if needed
					b=true;
				}
				if(so.getYear()!=s.getYear()) {
					so.setAlbum(s.getAlbum()); 
					b=true;
				}
				if(b) {
					write();
				}
				return true;
			}
		}
		return false;
	}
	private void write() { //deletes the current file and creates a new one of the same name that is re-populated with data from the songList
		File curList = new File(filePath);
		curList.delete();
		File file = new File(filePath);
		if(songList==null || songList.isEmpty()) {
			return;
		}
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true))) { //write to file
			for(Song s : songList) {
				fw.write(s.toString()+"\n");
			}
		} catch (IOException e) {
			
		}
	}
	@FXML
	private void addScreen(ActionEvent e) { //should transition to a new scene when the add button is clicked
		activate("addScene");
	}
	@FXML
	private void deleteScreen(ActionEvent e) { //we also need a submit buttons for these scenes, which will then call add/edit/delete
		activate("deleteScene");
	}
	@FXML
	private void editScreen(ActionEvent e) {
		activate("editScene");
	}
	@FXML
	private void Cancel(ActionEvent e) {
		activate("mainScene");
	}
	@FXML
	private void addConfirm(ActionEvent e) {
		Song s=new Song(name.getText(),artist.getText(),album.getText(),Integer.parseInt(year.getText()));
		add(s);
		activate("mainScene");
	}
	@FXML
	private void deleteConfirm(ActionEvent e) {
		
	}
	@FXML
	private void editConfirm(ActionEvent e) {
		
	}
}

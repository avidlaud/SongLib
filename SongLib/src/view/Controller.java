package view;

import java.io.*;
import java.util.*;
import javafx.beans.value.*;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.*;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.*;
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
	private TextField textFieldName;
	
	@FXML
	private TextField textFieldArtist;
	
	@FXML
	private TextField textFieldAlbum;
	
	@FXML
	private TextField textFieldYear;
	
	@FXML
	private Button buttonConfirmAdd;
	
	@FXML
	private Button buttonConfirmEdit;
	
	@FXML
	private Button buttonCancel;
	
	@FXML
	private ListView<Song> listView;
	
	private ObservableList<Song> songList;
	
	private Song selected;
	
	private String filePath; 
	
	private HashMap<String, Pane> screenMap = new HashMap<>();
	
	private Scene mainScene;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	public void start() throws IOException {
		songList = FXCollections.observableArrayList();
		filePath = "src\\app\\songList.txt";
		try(BufferedReader fr = new BufferedReader(new FileReader(filePath))) { //populate songList from file
			String line;
			while((line = fr.readLine()) != null) {
				String[] data = new String[4];
				data = line.split("\t",4);
				Song s = new Song(data[0],data[1],data[2],Integer.parseInt(data[3]));
				songList.add(s);
			}
			Collections.sort(songList);
			listView.setItems(songList);
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
				@Override
				public void changed(ObservableValue<? extends Song> observable, Song oldVal, Song newVal) {
					labelName.setText(listView.getSelectionModel().getSelectedItem().getName());
					labelArtist.setText(listView.getSelectionModel().getSelectedItem().getArtist());
					labelAlbum.setText(listView.getSelectionModel().getSelectedItem().getAlbum());
					labelYear.setText(Integer.toString(listView.getSelectionModel().getSelectedItem().getYear()));
					return;
				}
			});
			
			if(!songList.isEmpty()) {
				listView.getSelectionModel().select(0);
				selected = listView.getSelectionModel().getSelectedItem();
			} else {
				selected = null;
			}
		} catch (IOException e) {
			File file = new File(filePath);
			try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true))) {
				fw.write("");
			}
			catch(IOException e2) {
				throw new IOException("Song list file not found");
			}
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
		System.out.println(screenMap.get(name));
		//mainScene.setRoot(screenMap.get(name));
		switch(name) {
			case "addScene":
				labelName.setVisible(false);
				labelArtist.setVisible(false);
				labelAlbum.setVisible(false);
				labelYear.setVisible(false);
				buttonAdd.setVisible(false);
				buttonEdit.setVisible(false);
				buttonDelete.setVisible(false);
				textFieldName.setVisible(true);
				textFieldArtist.setVisible(true);
				textFieldAlbum.setVisible(true);
				textFieldYear.setVisible(true);
				buttonConfirmAdd.setVisible(true);
				buttonConfirmEdit.setVisible(false);
				buttonCancel.setVisible(true);
				break;
			case "editScene":
				labelName.setVisible(false);
				labelArtist.setVisible(false);
				labelAlbum.setVisible(false);
				labelYear.setVisible(false);
				buttonAdd.setVisible(true);
				buttonEdit.setVisible(true);
				buttonDelete.setVisible(true);
				textFieldName.setVisible(true);
				textFieldArtist.setVisible(true);
				textFieldAlbum.setVisible(true);
				textFieldYear.setVisible(true);
				buttonConfirmAdd.setVisible(false);
				buttonConfirmEdit.setVisible(true);
				buttonCancel.setVisible(true);
				break;
			default:
				labelName.setVisible(true);
				labelArtist.setVisible(true);
				labelAlbum.setVisible(true);
				labelYear.setVisible(true);
				buttonAdd.setVisible(true);
				buttonEdit.setVisible(true);
				buttonDelete.setVisible(true);
				textFieldName.setVisible(false);
				textFieldArtist.setVisible(false);
				textFieldAlbum.setVisible(false);
				textFieldYear.setVisible(false);
				buttonConfirmAdd.setVisible(false);
				buttonConfirmEdit.setVisible(false);
				buttonCancel.setVisible(false);
		}
	}
	public boolean add(Song s) { //add a song to the list in alphabetical order by writing to the file 
		if(read(s)) {
			errorPop();
			return false; //song already exists
		}
		if(songList == null) {
			return false;
		}
		if(s.getName()==null || s.getArtist()==null) {
			return false;
		}
		songList.add(s); 
		Collections.sort(songList);
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true))) { //write to file
			fw.write(s.toString()+"\n");
		} catch (IOException e) {
			return false;
		}
		selected=s;
		listView.getSelectionModel().select(songList.indexOf(s));
		return true;
	}
	public boolean delete(Song s) { //removes target song from the songList, returns true if it exists and false if it wasnt in the list in the first place
		if(songList == null || songList.isEmpty()) {
			return false;
		} 
		int i=0;
		for(Song so : songList) {
			if(so.getName().compareTo(s.getName())==0 && so.getArtist().compareTo(s.getArtist())==0) {
				if(i==0) {
					if(songList.size()>1) {
						listView.getSelectionModel().select(1);
						selected=listView.getSelectionModel().getSelectedItem();
					} else {
						selected = null;
					}
				} else if(songList.size()==(i+1)) {
					listView.getSelectionModel().select(i-1);
					selected=listView.getSelectionModel().getSelectedItem();
				} else {
					listView.getSelectionModel().select(i+1);
					selected=listView.getSelectionModel().getSelectedItem();
				}
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
		if(read(n)) {
			errorPop();
			return false; //can't make this edit because it will cause a conflict
		}
		for(Song so : songList) {
			if(so.compareTo(s)==0) {
				so.setName(n.getName());
				so.setArtist(n.getArtist());
				so.setAlbum(n.getAlbum());
				so.setYear(n.getYear());
				write();
				return true;
			}
		}
		return false;
	}
	private boolean read(Song s) { //search the file to see if a song exists
		if(songList == null || songList.isEmpty()) {
			return false;
		}
		for(Song so : songList) {
			if(so.compareTo(s)==0) {
				return true;
			}
		}
		return false;
	}
	/*private void write() { //deletes the current file and creates a new one of the same name that is re-populated with data from the songList
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
	}*/
	private void write() {
		if(songList==null || songList.isEmpty()) {
			return;
		}
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath))) { //write to file
			for(Song s : songList) {
				fw.write(s.toString()+"\n");
			}
			fw.write("\n");
		} catch (IOException e) {
			
		}
	}
	@FXML
	private void addScreen(ActionEvent e) { //should transition to a new scene when the add button is clicked
		add(new Song("Name1", "Artist1", "Album1", 2020));
		textFieldName.setText("");
		textFieldArtist.setText("");
		textFieldAlbum.setText("");
		textFieldYear.setText("");
		activate("addScene");
	}
	@FXML
	private void deleteScreen(ActionEvent e) { //we also need a submit buttons for these scenes, which will then call add/edit/delete
		selected = listView.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			activate("deleteScene");
		}
	}
	@FXML
	private void editScreen(ActionEvent e) {
		selected = listView.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			activate("editScene");
			textFieldName.setText(selected.getName());
			textFieldArtist.setText(selected.getArtist());
			textFieldAlbum.setText(selected.getAlbum());
			textFieldYear.setText(selected.getYear()+"");
		}
	}
	@FXML
	private void Cancel(ActionEvent e) {
		activate("mainScene");
	}
	@FXML
	private void addConfirm(ActionEvent e) {
		Song s=new Song(textFieldName.getText(), textFieldArtist.getText(), textFieldAlbum.getText(), Integer.parseInt(textFieldYear.getText()));
		add(s); 
		activate("mainScene");
	}
	@FXML
	private void deleteConfirm(ActionEvent e) {
		delete(selected);
		activate("mainScene");
	}
	@FXML
	private void editConfirm(ActionEvent e) {
		Song n=new Song(textFieldName.getText(), textFieldArtist.getText(), textFieldAlbum.getText(),Integer.parseInt(textFieldYear.getText()));
		edit(selected,n);
		activate("mainScene");
	}
	private void errorPop() { //creates a pop up window to display an error message
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.SetTitle("Error");
		Label lab = new Label("The name and artist of the song you are attempting to create already exist in the list.");
		Button but = new Button("Close");
		but.setOnAction(e->popup.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(lab,but);
		layout.setAlignment(Pos.Center);
		popup.setScene(mainScene);
		popup.showAndWait();
	}
}

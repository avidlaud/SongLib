/**
 * Ken Erickson and David Lau
 * CS213
 */

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
	private Button buttonConfirmDelete;
	
	@FXML
	private Button buttonCancel;
	
	@FXML
	private ListView<Song> listView;
	
	private ObservableList<Song> songList;
	
	private Song selected;
	
	private String filePath; 
	
	private HashMap<String, Pane> screenMap = new HashMap<>();
	
	private Scene mainScene;
	
	private Song editSong;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	public void start() throws IOException {
		songList = FXCollections.observableArrayList();
		filePath = "src\\app\\songList.txt";
		try(BufferedReader fr = new BufferedReader(new FileReader(filePath))) { //populate songList from file
			String line;
			
			/* Read data from file and create a sorted list*/
			while((line = fr.readLine()) != null) {
				String[] data = new String[4];
				data = line.split("\t",4);
				Song s = new Song();
				s.setName(data[0]);
				s.setArtist(data[1]);
				if(data[2] != "") {
					s.setAlbum(data[2]);
				}
				if(data[3] != "") {
					s.setYear(data[3]);
				}
				songList.add(s);
			}
			Collections.sort(songList);
			
			listView.setItems(songList);
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
				@Override
				/**
				 * Display the selected song data
				 */
				public void changed(ObservableValue<? extends Song> observable, Song oldVal, Song newVal) {
					if(!songList.isEmpty()) {
						if(listView.getSelectionModel().getSelectedItem() != null) {
							labelName.setText("Name: " + listView.getSelectionModel().getSelectedItem().getName());
							labelArtist.setText("Artist: " + listView.getSelectionModel().getSelectedItem().getArtist());
							if(listView.getSelectionModel().getSelectedItem().getAlbum() != null) {
								labelAlbum.setText("Album: " + (listView.getSelectionModel().getSelectedItem().getAlbum()));
							}
							else {
								labelAlbum.setText("Album: ");
							}
							if(listView.getSelectionModel().getSelectedItem().getYear() != null) {
								labelYear.setText("Year: " + (listView.getSelectionModel().getSelectedItem().getYear()));
							}
							else {
								labelYear.setText("Year: ");
							}
						}
						else {
							listView.getSelectionModel().select(0);
						}
					}
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
			/**
			 * If the songlist file does not exist, create it
			 */
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
				buttonConfirmDelete.setVisible(false);
				buttonCancel.setVisible(true);
				break;
			case "editScene":
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
				buttonConfirmAdd.setVisible(false);
				buttonConfirmEdit.setVisible(true);
				buttonConfirmDelete.setVisible(false);
				buttonCancel.setVisible(true);
				break;
			case "deleteScene":
				labelName.setVisible(true);
				labelArtist.setVisible(true);
				labelAlbum.setVisible(true);
				labelYear.setVisible(true);
				buttonAdd.setVisible(false);
				buttonEdit.setVisible(false);
				buttonDelete.setVisible(false);
				textFieldName.setVisible(false);
				textFieldArtist.setVisible(false);
				textFieldAlbum.setVisible(false);
				textFieldYear.setVisible(false);
				buttonConfirmAdd.setVisible(false);
				buttonConfirmEdit.setVisible(false);
				buttonConfirmDelete.setVisible(true);
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
				buttonConfirmDelete.setVisible(false);
				buttonCancel.setVisible(false);
		}
	}
	
	/**
	 * Add a song to the song list, in alphabetical order
	 * @param s The song to insert
	 * @return True if successfully added, false otherwise
	 */
	public boolean add(Song s) {
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
		write();
		selected=s;
		listView.getSelectionModel().select(songList.indexOf(s));
		return true;
	}
	
	/**
	 * Delete a song from the list
	 * @param s The song to delete
	 * @return True if successfully deleted, false otherwise
	 */
	public boolean delete(Song s) { //removes target song from the songList, returns true if it exists and false if it wasnt in the list in the first place
		if(songList == null || songList.isEmpty()) {
			return false;
		} 
		int i=0;
		for(Song so : songList) {
			if(so.compareTo(s)==0) {
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
	
	/**
	 * Change the values of a song that already exists in the list
	 * @param s The song to edit
	 * @param n The new song values
	 * @return Returns true if successfully edited, false otherwise
	 */
	public boolean edit(Song s, Song n) { 
		if(songList == null || songList.isEmpty()) {
			return false;
		}
		songList.remove(s); //Delete the current selected song - will insert it again after
		if(read(n)) {
			errorPop();
			songList.add(s); //Add song back
			Collections.sort(songList);
			write();
			return false; //can't make this edit because it will cause a conflict
		}
		songList.add(n);
		Collections.sort(songList);
		write();
		return true;
	}
	
	/**
	 * Search the song list for a song
	 * @param s The song to search for
	 * @return True if found, false if not
	 */
	private boolean read(Song s) {
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

	/**
	 * Write the current song list to the text file
	 */
	private void write() {
		if(songList==null || songList.isEmpty()) {
			return;
		}
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filePath))) { //write to file
			File file = new File(filePath);
			PrintWriter pw = new PrintWriter(file);
			pw.print("");
			pw.close();
			for(int i=0;i<songList.size();i++) {
				Song s=songList.get(i);
				if(i==(songList.size()-1)) {
					fw.write(s.writeToFile());
				} else {
					fw.write(s.writeToFile()+"\n");
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	@FXML
	private void addScreen(ActionEvent e) {
		textFieldName.setText("");
		textFieldArtist.setText("");
		textFieldAlbum.setText("");
		textFieldYear.setText("");
		activate("addScene");
	}
	
	@FXML
	private void deleteScreen(ActionEvent e) {
		selected = listView.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			activate("deleteScene");
		}
	}
	
	@FXML
	private void editScreen(ActionEvent e) {
		selected = listView.getSelectionModel().getSelectedItem();
		editSong = selected; //Sets the currently selected song for use in edit()
		if(selected!=null) {
			activate("editScene");
			textFieldName.setText(selected.getName());
			textFieldArtist.setText(selected.getArtist());
			textFieldAlbum.setText(selected.getAlbum());
			textFieldYear.setText(selected.getYear());
		}
	}
	
	@FXML
	private void Cancel(ActionEvent e) {
		activate("mainScene");
	}
	
	@FXML
	private void addConfirm(ActionEvent e) {
		Song s=new Song();
		String t = textFieldName.getText().trim();
		if(t.compareTo("")==0) {
			return;
		}
		s.setName(t);
		t = textFieldArtist.getText().trim();
		if(t.compareTo("")==0) {
			return;
		}
		s.setArtist(t);
		t = textFieldAlbum.getText().trim();
		if(t.compareTo("")!=0) {
			s.setAlbum(t);
		}
		t = textFieldYear.getText().trim();
		if(t.compareTo("")!=0 && isInt(t)) {
			s.setYear(t);
		}
		add(s); 
		activate("mainScene");
	}
	
	/**
	 * Check if a string is a number - used to check if valid year
	 * @param s String to check
	 * @return True if number, false if not
	 */
	private boolean isInt(String s) {
		if(s==null) {
			return false;
		}
		for(int i=0;i<s.length();i++) {
			if(!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	@FXML
	private void deleteConfirm(ActionEvent e) {
		delete(selected);
		activate("mainScene");
	}
	
	@FXML
	private void editConfirm(ActionEvent e) {
		Song s=new Song();
		String t = textFieldName.getText();
		if(t.compareTo("")==0) {
			return;
		}
		s.setName(t);
		t = textFieldArtist.getText();
		if(t.compareTo("")==0) {
			return;
		}
		s.setArtist(t);
		t = textFieldAlbum.getText();
		if(t.compareTo("")!=0) {
			s.setAlbum(t);
		}
		t = textFieldYear.getText();
		if(t.compareTo("")!=0 && isInt(t)) {
			s.setYear(t);
		}
		edit(editSong,s);
		activate("mainScene");
	}
	
	/**
	 * Create error pop-up window for duplicate entries
	 */
	private void errorPop() {
		Alert alert = new Alert(AlertType.ERROR, "The name and artist of the song you are attempting to create already exists in the library.", ButtonType.CLOSE);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.CLOSE) {
		    //Error handling
		}
	}
}

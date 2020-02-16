package app;

public class Song implements Comparable<Song>{
	
	private String name;
	private String artist;
	private String album;
	private int year;
	
	public Song(String name, String artist, String album, int year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public Song() {	}
	
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int compareTo(Song s) {
		if(this.name.compareTo(s.getName()) > 0) {
			return 1;
		}
		else if(this.name.compareTo(s.getName()) < 0) {
			return -1;
		}
		if(this.artist.compareTo(s.getArtist()) == 0) {
			return 0;
		}
		return (this.artist.compareTo(s.getArtist()) > 0) ? 1:-1;	
	}
	public String toString() {
		String s = name+"\t"+artist+"\t"+album+"\t"+year; 
		return s;
	}
}

/**
 * Ken Erickson and David Lau
 * CS213
 */

package app;

public class Song implements Comparable<Song>{
	
	private String name;
	private String artist;
	private String album;
	private String year;
	
	public Song(String name, String artist, String album, String year) {
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
	
	public String getYear() {
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
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public int compareTo(Song s) {
		if(this.name.toLowerCase().compareTo(s.getName().toLowerCase()) > 0) {
			return 1;
		}
		else if(this.name.toLowerCase().compareTo(s.getName().toLowerCase()) < 0) {
			return -1;
		}
		if(this.artist.toLowerCase().compareTo(s.getArtist().toLowerCase()) == 0) {
			return 0;
		}
		return (this.artist.toLowerCase().compareTo(s.getArtist().toLowerCase()) > 0) ? 1:-1;	
	}
	public String toString() {
		String s = name + "-" + artist;
		return s;
	}
	
	public String writeToFile() {
		String writeName = (name == null) ? "":name;
		String writeArtist = (artist == null) ? "":artist;
		String writeAlbum = (album == null) ? "":album;
		String writeYear = (year == null) ? "":year;
		String s = writeName+"\t"+writeArtist+"\t"+writeAlbum+"\t"+writeYear;
		return s;
	}
}

package sokoban;

import java.io.File; 
import java.io.FileNotFoundException; 
import javafx.scene.media.AudioClip;
/**
 * Implementation of loading background music
 * @author Chen Haipei
 */

class BGM extends Thread{
	public static volatile boolean exit = false;
	String music;
	boolean play= true;
	AudioClip ac;
	
	public BGM(String file) {
		this.music = file;
	}
	public void run() {
			try {
				play(music);     
			} catch (FileNotFoundException  e) {
				e.printStackTrace();
			}
	}
	public void play(String music) throws FileNotFoundException{ 

		ac = new AudioClip(new File("music/"+music+".mp3").toURI().toString());
		ac.play();   //Start playing
		play= true;
	}
	public String setMusic(String music) {
		this.music=music;
		return music;
	}

	public void mystop() {
		if (!ac.isPlaying()) {

		} else {
			ac.stop();
		}
	}

	
}

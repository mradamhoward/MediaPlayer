

import java.net.URISyntaxException;
import java.net.URL;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class Mp3Player extends Thread implements AdvancedMediaPlayer, Observable {
	
	String fileName;
	MediaPlayer player;
	
	String localFileDir = "C:\\Users\\Adam\\workspace\\ChallengingLabs\\src\\files\\";
	
	public Mp3Player(String fileName, MediaPlayer defaultMedia){
		this.fileName = fileName;
		this.player = defaultMedia;
	}
	
	public void run(){
		
    	String resource = null;
		try {
			resource = getClass().getResource("files/"+fileName).toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final Media media = new Media(resource);
        player = new MediaPlayer(media);
        player.play();
        try {
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetPlayer(){
		player.pause();
		this.stop();
	}
	
	public void interrupt(){
		player.stop();
	}

	@Override
	public void playMp3(String fileName) {
		Thread.currentThread().run();
		
	}

	@Override
	public void playMp4(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	
}

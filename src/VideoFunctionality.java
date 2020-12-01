import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoFunctionality extends Application{

	
	@FXML
	private MediaView mediaV;
	@FXML
	private Media me;
	
	String play;
	
	public VideoFunctionality() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void popup(String play){
		Stage videoStage = new Stage();

		Parent root = null;
		try {
			FXMLLoader fl = null;
			try {
				fl = new FXMLLoader(getClass().getResource("VideoView.fxml").toURI().toURL());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fl.setController(new Controller());
			AnchorPane pne = fl.load();
			
			String path = new File("src/files/" + play).getAbsolutePath();
			Scene scene = new Scene(pne, 1280, 720);
			System.out.println("path from video" + path);
			videoStage.setTitle("Full Screen Video");
			videoStage.setScene(scene);

		
			Media video = new  Media(new File(path).toURI().toString());
			MediaPlayer player = new MediaPlayer(video);

			player.setAutoPlay(true);
			mediaV.setMediaPlayer(player);
			videoStage.show();
			System.out.println("Getting");
		} catch (IOException e) {
				e.printStackTrace();
		}
	}

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

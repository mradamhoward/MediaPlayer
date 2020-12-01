import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class loadFiles {
	public static void main(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(null);
	}
	
	public void showDialog(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage window  = new Stage();
		fileChooser.showOpenDialog(window);
	}
}

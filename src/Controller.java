	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.WatchKey;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;

public class Controller extends Application implements Initializable, Observer {

	//UI components
	@FXML    
	private MediaPlayer mediaPlayer;
	@FXML    
	private Duration duration;
	@FXML   
	private MediaView mediaView;
	@FXML   
	private MenuItem importLocal;
	@FXML   
	private MenuItem downloadEncrypt;
	
	@FXML   
	private Button playButt;
	@FXML   
	private Button viewImage;
	@FXML   
	private Button downloadFilesB;
	@FXML   
	private ToggleButton pauseButt;
	@FXML
	public  ListView<String> listv;
	@FXML
	Stage primaryStage;
	@FXML
	private Slider sl;
	@FXML
	private Slider progress;
	@FXML
	private Slider volume;
	@FXML
	private HBox labelBox;
	@FXML
	private ImageView imager;
	@FXML
	private ImageView imageViewer;
	@FXML
	private ImageView musicView;
	@FXML
	MenuItem downloadFromMenu;
	@FXML
	BorderPane rootLayout;
	@FXML
	private TabPane tabPane;
	@FXML
	private Label currentSongLbl;
	@FXML
	private ToggleButton tb;
	@FXML
	private Label endLb;
	@FXML
	private Label startLb;
	@FXML 
	private HBox dhb;
	@FXML
	private Button previous;
	@FXML
	private Button next;
	@FXML
	private Button goFull;
	
	EqualizerBand band = new EqualizerBand();
	
	Timer timer;
	
	Toolkit toolkit;
	
	private Tab tabMusic;
	private Tab tabVideo;
	private Tab tabImage;
	
	Task copyWorker;
	
	String localFileDir = "C:\\Users\\Adam\\workspace\\ChallengingLabs\\src\\files\\";
	MediaPlayer defaultMedia;
	
	final long delay = 1000;
	
	boolean keepPlaying = true;
	
	int directoryObserved = 0;
	boolean shouldObserve = true;
	
	//RMI info
	final int port = 1117; 
	final String host = "127.0.0.1";
	final String boundObject = "String";
	
	private static Map<WatchKey, Path> keyPathMap = new HashMap<>();
	
	private ArrayList<File> downloaded;
	//Key to bytes
	private static final byte[] key = "AdamsPAssssswwwwwwwqyyyy".getBytes();
	private static final long serialVersionUID = 1L;
	String toServe = "String from Server! RMI connection successful";
	//Which encryption algorithm to use
	private static final String transformation = "AES/ECB/PKCS5Padding";
	//Thread management
	final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	ExecutorService downloadControl = Executors.newCachedThreadPool();
	private static final String EOL = System.getProperty("line.separator");
	Future<?> f;
	private String encryptionKey = "Adams";
	//Custom objects in collections
	ArrayList<MP4> mp4List = new ArrayList<MP4>();
	ArrayList<MP3> mp3List = new ArrayList<MP3>();
	ArrayList<JPEG> jpegList = new ArrayList<JPEG>();
	String playButtonStatus = "play";
	//Thread management for playing music
	ExecutorService musicPlayerPool = Executors.newCachedThreadPool();
	public int lastDirectorySize = 0; 
	
	long directorySize  = 0;
	
	MediaPlayer playerVid;
	//Invoked by play button through JavaFX// most of the media playing is done here
	public void play() throws InterruptedException{

		keepPlaying = false;
		
		try{
			f.cancel(true);
			f.wait();
		} catch (Exception e){

		}
		
		//find the mode and selected item on view
		String selectedInList = listv.getSelectionModel().getSelectedItem().toString(); 
		String  selectedTab = tabPane.getSelectionModel().getSelectedItem().getText();

		String beforedot = selectedInList.split("\\.", 2)[0];


		
		//depending on the tab..
		switch (selectedTab) 

		{
		case "Music":  	
			
			try{
				playerVid.stop(); 
				
			} catch (Exception e){	
			}
			
			String f;
			String resource = null;
			try {
				resource = getClass().getResource("files/"+ selectedInList).toURI().toString();
			} catch (URISyntaxException e) {

			}
			
			final Media media = new Media(resource);
			try{
				defaultMedia.stop(); 
			} catch (Exception e){	
			}

			defaultMedia = new MediaPlayer(media);
			defaultMedia.play();
			
			currentSongLbl.setText(beforedot);
			labelBox.setAlignment(Pos.CENTER);
			keepPlaying = true;
			break;		

		case "Videos":  
			try{
				defaultMedia.stop(); 
			} catch (Exception e){	
			}
			
			
			String path = new File("src/files/" + selectedInList).getAbsolutePath();
			Media video = new  Media(new File(path).toURI().toString());
			playerVid = new MediaPlayer(video);

			playerVid.setAutoPlay(true);
			mediaView.setMediaPlayer(playerVid);
			
			playerVid.currentTimeProperty().addListener(new InvalidationListener() 
			{
				public void invalidated(Observable ov) {
					refreshLengthLabels();
				}
			});
			
			System.out.println(path);
			
		
			keepPlaying = true;
			break;


		case "Images":
			try{
				playerVid.stop(); 
				defaultMedia.stop();
			} catch (Exception e){	
			}
			
			Image value = new Image("files/" + selectedInList);
			System.out.println(value.getHeight());
			
			imageViewer.setImage(value);
			
			keepPlaying = true;
			break;
		}
		
		try{
			defaultMedia.currentTimeProperty().addListener(new InvalidationListener() 
			{
				public void invalidated(Observable ov) {
					refreshLengthLabels();
				}
			});
		} catch(Exception e) {
		}
		directorySize = folderSize(new File(localFileDir));
		
		refreshLengthLabels();
		
	}
	
	public void goFull(){
		
		String selectedInList = listv.getSelectionModel().getSelectedItem().toString(); 
		
		VideoFunctionality popup = new VideoFunctionality();
		popup.popup(selectedInList);
	}

	
	//View image full screen
	public void viewImage() throws FileNotFoundException
	{
		String selectedInList = listv.getSelectionModel().getSelectedItem().toString();
		String urlImage = new String(localFileDir + selectedInList);
		
		File file = new File(urlImage);
		System.out.println("ab path" + file.getAbsolutePath());
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		Image value = new Image(fis);
		System.out.println(value.getHeight());
		imager.setImage(value);
	}
	
	
	//This stops all media players
	public void pause() {
	
		try{
			defaultMedia.stop();
			playerVid.stop();
		} catch (Exception e){
			
		}
		
	}
	
	//Download files as byte array (chinks) over RMI, with Poling
	public void downloadRemoteFiles() throws InterruptedException, ExecutionException, NotBoundException, IOException{
		
		int pollCount = 0;
		//Connect to registry
		Registry reg = LocateRegistry.getRegistry(host, port);
	
		downloadRemoteString fi = (downloadRemoteString) reg.lookup(boundObject);
		
		//RMI connection test
		String download = fi.downloadString();
		
		//List of files from server
		downloaded = fi.getAllAsList();
		
		byte result[];
		
		//download sealed
		result = fi.downloadFiles();
		
		//initiate polling counter
		pollCount = fi.getPollingLimit();
		
		//Polling
		try{
			while(pollCount >= 0){
				
				
				//Using iterator
				byte data[] = fi.downloadNextFile();
				
				//write file
				FileOutputStream fos = new FileOutputStream(localFileDir+ "downloadedFile"  +  pollCount  +   ".jpg");
				fos.write(data);
				fos.close();
				
				System.out.println(pollCount);
				
				pollCount = fi.getPollingLimit();
				
			}
			
		} catch (Exception e){
			
		}
		
		System.out.println(result.toString());
	
        writeDownloaded();
        displayDownloaded();

	}
	
	public void displayDownloaded(){
		MediaFactory m = new MediaFactory();
	
		for(File f : downloaded){
			
			String path = f.getPath();
			String afterdot = path.substring(path.lastIndexOf(".")+1);
			System.out.println(afterdot);

			MediaType fileFromFactory = m.getMediaType(afterdot);
			fileFromFactory.setFile(f);
			File fl = fileFromFactory.getFile() ;
			System.out.println(fl.getName());
			
			if(fileFromFactory.getThisType() == "mp3"){
				mp3List.add((MP3)fileFromFactory);
			} else if(fileFromFactory.getThisType() == "mp4"){
				mp4List.add((MP4)fileFromFactory);
			} else  if(fileFromFactory.getThisType() == "jpg"){
				jpegList.add((JPEG)fileFromFactory);
			}
			
		}
		loadListViewFromTab();
	}
	
	
	
	public void downloadSecure() throws RemoteException, NotBoundException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, FileNotFoundException, NoSuchPaddingException{
		Registry reg = LocateRegistry.getRegistry(host, port);
		downloadRemoteString fi = (downloadRemoteString) reg.lookup(boundObject);


		SealedObject down = fi.downloadSecure();
		
		SecretKeySpec sks = new SecretKeySpec(key, "AES");
	    Cipher cipher = Cipher.getInstance(transformation);
	    cipher.init(Cipher.DECRYPT_MODE, sks);
		File got = null;
		try {
			got = (File) down.getObject(cipher);
		} catch (Exception e){
			e.printStackTrace();
		}

		
	}
	
	
	
	public void writeDownloaded() throws IOException{
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		fout = new FileOutputStream("files/");
		oos = new ObjectOutputStream(fout);
		
		System.out.println("trying");
		
		for(File f : downloaded){
			oos.writeObject(f);
			System.out.println(f.getName());
		}
		fout.close();
	}
	
	//Main application point
	public void start(Stage primaryStage) throws Exception {
		initRootLayout();
		imageViewer = new ImageView();

		ExecutorService es = Executors.newCachedThreadPool();

		Path myDir = Paths.get(localFileDir);       
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		DirectoryObserver d = new DirectoryObserver(myDir, this);
		
		es.execute(d);
		
	}
	

	//movement in listview/library
	public void decrementSelection(){
		listv.getSelectionModel().selectPrevious();
		try {
			play();
		} catch (InterruptedException e) {
		}
	}
	
	public void incrementSelection(){
		listv.getSelectionModel().selectNext();
		try {
			play();
		} catch (InterruptedException e) {
			
		}
	}
	
	//time labels updated 
	public void refreshLengthLabels(){	
		double start  = 0;	
		
		double current = defaultMedia.getCurrentTime().toSeconds();
		current = Math.round(current);
		double end = defaultMedia.getStopTime().toSeconds();
		end = Math.round(end);

		double percent = current/end * 100;
		String helper;
		String currentTxt = Double.toString(current);

		helper =   currentTxt ;
		
		percent = Math.round(percent);
		
		startLb.setText(helper.substring(0,2));
		String endText = Double.toString(end);
		String help = "";
		char c = endText.charAt(1);

		String minutes;
		
		help =   endText.substring(0, 3);
		endLb.setText(help);
		progress.setValue(percent);
		
		
	
	}
	public File getFileFromListView(){
		String selectedInList = listv.getSelectionModel().getSelectedItem().toString();
		File fle = new File(localFileDir + selectedInList);
		return fle;
	}
	
	//Fill library section (ArrayList) from project resource 
	public void loadMediaToLists(){
		MediaFactory m = new MediaFactory();

		File f = new File("C:\\Users\\Adam\\workspace\\ChallengingLabs\\src\\files\\");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		ObservableList<String> items = FXCollections.observableArrayList();

		int fileSize = files.size();
		System.out.println("Files size" + fileSize);

		if(fileSize > lastDirectorySize){
			
			mp3List.clear();
			mp4List.clear();
			jpegList.clear();
			
			for (File currentFile : files) {
				lastDirectorySize = fileSize;
				String path = currentFile.getPath();
				String afterdot = path.substring(path.lastIndexOf(".")+1);
				System.out.println(afterdot);

				MediaType fileFromFactory = m.getMediaType(afterdot);
				fileFromFactory.setFile(currentFile);
				File fl = fileFromFactory.getFile() ;
				System.out.println(fl.getName());
				
				if(fileFromFactory.getThisType() == "mp3"){
					mp3List.add((MP3)fileFromFactory);
				} else if(fileFromFactory.getThisType() == "mp4"){
					mp4List.add((MP4)fileFromFactory);
				} else  if(fileFromFactory.getThisType() == "jpg"){
					jpegList.add((JPEG)fileFromFactory);
				}
			}
		}
	}

	public ListView<String> getListView(){
		return listv;
	}
	
	//read a directory size
	public static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
	//This is invoked every tab change
	public void loadListViewFromTab(){
		loadMediaToLists();
		String  selectedTab = tabPane.getSelectionModel().getSelectedItem().getText();
		System.out.println(selectedTab);
		
		ObservableList<String> items = FXCollections.observableArrayList();
	
		switch (selectedTab) {
		
        case "Music":  
        				try{
        				playerVid.stop(); 
        				defaultMedia.stop();
        				} catch (Exception e){	
        				}
        				
        				for(MP3 f : mp3List)
        				{
						items.add(f.getFile().getName());
						
        				} 
        				break;
        				
        				
        case "Videos":  
        	try{
        		playerVid.stop(); 
        		defaultMedia.stop();
        	} catch (Exception e){	
        	}
        				
        				for(MP4 f : mp4List)
        				{
        				items.add(f.getFile().getName().toString());
						
						};
						break;
		
        case "Images":  
        	
        	try{
        		playerVid.stop(); 
        		defaultMedia.stop();
        	} catch (Exception e){	
        	}

        				for(JPEG f : jpegList)
        				{
        				items.add(f.getFile().getName().toString());

        				};
        				break;

     
		}
	
		System.out.println("mp3 size" + mp3List.size());
		System.out.println("mp4 size" + mp4List.size());
		System.out.println("jpg size" + jpegList.size());
		listv.setItems(items);
	}
	
	

	//Load GUI from FXML file
	public void initRootLayout() throws IOException {
		try {
			
			primaryStage = new Stage();
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("                                                                                                                      "
					+ "                             Media Player");
			
			Image img = new Image(getClass().getResourceAsStream("/title.png"));
			
			this.primaryStage.getIcons().add(img); 
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(Controller.class.getResource("GUI.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			primaryStage.show();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);


	}
	
	//invoked by slider movement
	
	public void setVolume(){
		double vol;
		vol = Math.floor(volume.getValue());
		defaultMedia.setVolume(vol);
	}
	
	
	//FileDialogue implementation
	
	@FXML
	public void importLocals() throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Files");
		FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
		FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
		FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
		fileChooser.getExtensionFilters().add(mp3Filter);
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.getExtensionFilters().add(mp4Filter);
		
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		copyChosenFiles(selectedFile);
	}

	

	public void copyChosenFiles(File chosenFile) throws IOException{

		String compositeStart = "C:\\Users\\Adam\\workspace\\ChallengingLabs\\src\\files\\";

		File newFile = new File(compositeStart + chosenFile.getName());

		Path source = Paths.get(chosenFile.getAbsolutePath());
		Path destination = Paths.get(newFile.getAbsolutePath());

		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		//loadMediaToLists();
	}

	@Override
	public void update() throws NoSuchMethodException, SecurityException {
		
		System.out.print("Observer hit controller");
		
		loadMediaToLists();
		
	}

	@Override
	public void setSubject(Subject sub) {
		
		
	}
}

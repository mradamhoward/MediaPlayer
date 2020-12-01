import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class DirectoryObserver extends Thread implements Observable{

	
	List<Observer> observers = new ArrayList<Observer>();
    private static final Kind<?> ENTRY_CREATE = null;
	private static final Kind<?> ENTRY_DELETE = null;
	private static final Kind<?> ENTRY_MODIFY = null;
	private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    
    String localFileDir = "C:\\Users\\Adam\\workspace\\ChallengingLabs\\src\\files\\";
 
    
    private static Map<WatchKey, Path> keyPathMap = new HashMap<>();
    
    public void notifyAllObservers() throws NoSuchMethodException, SecurityException {
    	System.out.println("Observers size" + observers.size());
    	
    	for(Observer o: observers){
    		o.update();
    	}
    }
    
    
    //This is a subject class that continually monitors a directory using WatchService (Java 7), when a file is copied to the directory, the observer (Controller is notified)
    private static void registerDir (Path path, WatchService watchService) throws
	IOException {
		if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			return;
		}
		System.out.println("registering library: " + path);

		WatchKey key = path.register(watchService,
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
		keyPathMap.put(key, path);

		for (File f : path.toFile().listFiles()) {
			registerDir(f.toPath(), watchService);
		}
	}

	private void startListening (WatchService watchService) throws Exception {
		while (true) {
			WatchKey queuedKey = watchService.take();
			for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {
				System.out.printf("File %s in library",
						watchEvent.kind()
						);

				if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					Path path = (Path) watchEvent.context();
					Path parentPath = keyPathMap.get(queuedKey);
		
					path = parentPath.resolve(path);
					registerDir(path, watchService);
					
					System.out.println("File Added");
					
					notifyAllObservers();
				}
				
				if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
					
					Path path = (Path) watchEvent.context();
			
					Path parentPath = keyPathMap.get(queuedKey);
		
					path = parentPath.resolve(path);

					registerDir(path, watchService);
					
					System.out.println("File Added");
					
					
					// Notify controller
					notifyAllObservers();
				}
			}
			if(!queuedKey.reset()){
				keyPathMap.remove(queuedKey);
			}
			if(keyPathMap.isEmpty()){
				break;
			}
		}
	}
	
	
	//The constructor is passed a reference to Controller as an Observer
	public DirectoryObserver(Path dir, Observer o) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        observers.add(o);
	}
	
	
	//this functionality is run on a different thread
	public void run(){
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
        	registerDir(Paths.get(localFileDir), watchService);
        	startListening(watchService);
        } catch(Exception e){

        }	
	}

	public static void main(String[] args) {
	}

	@Override
	public void addListener(InvalidationListener arg0) {
	}

	@Override
	public void removeListener(InvalidationListener listener) {
	}

}

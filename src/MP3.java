

import java.io.File;

public class MP3 implements MediaType{

	
	File mainFile;
	String thisType = "mp3"; 
	String path;
	public String getThisType() {
		return thisType;
	}

	public void setThisType(String thisType) {
		this.thisType = thisType;
	}

	public MP3() {
	}
	
	public MP3(String m) {
		this.path = m;
	}
	
	public MP3(File mainFile) {
		super();
		this.mainFile = mainFile;
	}

	public File getFile() {
		return mainFile;
	}

	@Override
	public void setFile(File f) {
		this.mainFile = f;
		
	}

	public String getName() {
		String name = mainFile.getPath();
		return name;
	}
	
	
}

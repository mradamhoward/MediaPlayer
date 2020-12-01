import java.io.File;

public class JPEG implements MediaType {
	String mainFilePath;
	File f;
	String thisType = "jpg"; 
	
	public String getThisType() {
		return thisType;
	}
	public void setThisType(String thisType) {
		this.thisType = thisType;
	}

	
	
	public JPEG(String mediaType) {
		this.mainFilePath = mediaType;
	}
	
	public File getFile() {
		return f;
	}
	public String getPath() {
		return mainFilePath;
	}
	public void setWithFile(File f){
		this.f = f;
	}
	public void setFile(File f) {
			this.f = f;
	}
	public String getName() {
		String name = f.getName();
		return name;
	}
}

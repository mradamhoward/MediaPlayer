import java.io.File;

public class MP4 implements MediaType {

	File f;
	String path;
	public String getThisType() {
		return thisType;
	}

	public void setThisType(String thisType) {
		this.thisType = thisType;
	}

	String thisType = "mp4";
	
	public MP4(File f) {
		this.f = f;
	}

	public MP4() {
		// TODO Auto-generated constructor stub
	}
	public MP4(String p) {
		this.path = p;
	}

	@Override
	public File getFile() {
		return f;
	}

	@Override
	public void setFile(File f) {
		this.f = f;
	}
	public String getName() {
		String name = f.getName();
		return name;
	}

}


public class MediaFactory {
	public MediaType getMediaType(String mediaType){
		if(mediaType == null){
			return null;
		}		
		if(mediaType.equalsIgnoreCase("JPG")){
			return new JPEG(mediaType);

		} else if(mediaType.equalsIgnoreCase("MP3")){
			return new MP3(mediaType);
		} else if(mediaType.equalsIgnoreCase("MP4")){
			return new MP4(mediaType);
		}
		return null;
	}

}


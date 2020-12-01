
public class MediaAdapter {

	AdvancedMediaPlayer advancedMusicPlayer;

	   public MediaAdapter(String audioType){
	   
	      if(audioType.equalsIgnoreCase("vlc") ){
	         //advancedMusicPlayer = new Mp3Player(audioType);			
	         
	      }else if (audioType.equalsIgnoreCase("mp4")){
	         advancedMusicPlayer = new Mp4Player(null, null, null);
	      }	
	   }

	   public void play(String audioType, String fileName) {
	   
	      if(audioType.equalsIgnoreCase("mp3")){
	         advancedMusicPlayer.playMp3(fileName);
	      }
	      else if(audioType.equalsIgnoreCase("mp4")){
	         advancedMusicPlayer.playMp4(fileName);
	      }
	   }
	

}

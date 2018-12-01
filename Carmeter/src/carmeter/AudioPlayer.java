
package carmeter;

import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
/**
 *
 * @author Hanna Nabil
 */
public class AudioPlayer {
    // to store current position 
    Long currentFrame; 
    Clip clip; 
    // current status of clip 
    String status; 
    AudioInputStream audioInputStream; 
    static String filePath; 
    
  
    // constructor to initialize streams and clip 
    public AudioPlayer() 
        throws UnsupportedAudioFileException, IOException, LineUnavailableException  
    { 
        filePath = "siren2.wav";
        // create AudioInputStream object 
        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
       
    } 

      
    // Method to play the audio 
    public void play()  
    { 
        clip.start(); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        status = "play"; 
    } 
    // Method to restart the audio 
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException  
    { 
        clip.stop(); 
        clip.close(); 
        resetAudioStream(); 
        currentFrame = 0L; 
        clip.setMicrosecondPosition(0); 
        this.play(); 
    }   
    // Method to stop the audio 
    public void stop() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
    { 
        currentFrame = 0L; 
        clip.stop(); 
        clip.close(); 
    }  
      
    // Method to reset audio stream 
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,  LineUnavailableException  
    { 
        audioInputStream = AudioSystem.getAudioInputStream( new File(filePath).getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
    
}
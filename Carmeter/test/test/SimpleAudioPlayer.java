/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Java program to play an Audio 
// file using Clip Object 
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
  
public class SimpleAudioPlayer  
{ 
  
    // to store current position 
    Long currentFrame; 
    Clip clip; 
      
    // current status of clip 
    String status; 
      
    AudioInputStream audioInputStream; 
    static String filePath; 
  
    // constructor to initialize streams and clip 
    public SimpleAudioPlayer() 
        throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException  
    { 
        // create AudioInputStream object 
        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream); 
          
        //clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
  
    public static void main(String[] args)  
    { 
        try
        { 
            filePath = "siren2.wav"; 
            SimpleAudioPlayer audioPlayer =  new SimpleAudioPlayer(); 
              
            //audioPlayer.play(); 
            Scanner sc = new Scanner(System.in); 
              
            while (true) 
            { 
                System.out.println("1. start"); 
                System.out.println("3. restart"); 
                System.out.println("4. stop"); 
              
                int c = sc.nextInt(); 
                audioPlayer.gotoChoice(c); 
                if (c == 5) 
                break; 
            } 
            sc.close(); 
        }  
          
        catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
          
          } 
    } 
      
    // Work as the user enters his choice 
      
    private void gotoChoice(int c) 
            throws IOException, LineUnavailableException, UnsupportedAudioFileException  
    { 
        switch (c)  
        { 
            
             case 1: 
                play(); 
                break;
            case 3: 
                restart(); 
                break; 
            case 4: 
                stop(); 
                break; 
           
      
        } 
      
    } 
      
    // Method to play the audio 
    public void play()  
    { 
        clip.start(); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        status = "play"; 
    } 
      

    // Method to restart the audio 
    public void restart() throws IOException, LineUnavailableException, 
                                            UnsupportedAudioFileException  
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
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
                                            LineUnavailableException  
    { 
        audioInputStream = AudioSystem.getAudioInputStream( 
        new File(filePath).getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
  
} 
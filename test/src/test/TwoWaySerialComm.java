/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.Sentence;

/**
 *
 * @author Hanna Nabil
 */
public class TwoWaySerialComm implements SentenceListener {
    private SentenceReader reader;
    SentenceParser parser;
    InputStream is;
    BufferedReader buf;
    int flag =0;
    String temp = null;
    //NMEA nmea ;
    public void readingPaused() {System.out.println("-- Paused --");}
	public void readingStarted() {System.out.println("-- Started --");}

	public void readingStopped() {System.out.println("-- Stopped --");}

	public void sentenceRead(SentenceEvent event) {

		// Safe to cast as we are registered only for GGA updates. Could
		// also cast to PositionSentence if interested only in position data.
		// When receiving all sentences without filtering, you should check the
		// sentence type before casting (e.g. with Sentence.getSentenceId()).
                
		GGASentence s = (GGASentence) event.getSentence();
		// Do something with sentence data..
		System.out.println(s.getPosition());
                //System.out.println(event.getSentence());
                
	}
    
    
    
    
     public TwoWaySerialComm()
    {
        super();
        
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(19200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
               
                InputStream in = serialPort.getInputStream();
                /*
                byte[] buffer = new byte[1024];
                int len = -1;
                 while ( ( len = in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));//new String(buffer,int offset,int length)			
                }
                SentenceReader sr = new SentenceReader(in);
               
                sr.addSentenceListener(this);
                sr.start();
                */
                (new Thread(new SerialReader(in))).start();
                Thread th = new Thread(new ReadLine());
                //th.start();
   

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    void inialize(){
        SentenceReader sr = new SentenceReader(is);
        sr.addSentenceListener( this);
        sr.start();
    }
    class ReadLine implements Runnable 
    {
        Boolean b = new Boolean(true);
        
        public void run ()
        {while(b){
           
            
            	
            try {
                while(buf != null &&(temp = buf.readLine()) != null){
                   
                    if (SentenceValidator.isValid(temp)) {
                            System.out.println("NMEA data found!");
                            inialize();
                                b =false;
                                break;

                    }}
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
          }      
        
        }
    }
     class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
            //buf = new BufferedReader(new InputStreamReader(in));
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            //NMEA nmea = new NMEA();
           //while(true){
            
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                   is = new ByteArrayInputStream(buffer);
                   buf = new BufferedReader(new InputStreamReader(is));
                   //if (buf != null && flag ==0){System.out.println("Entered");inialize(); flag =1;}
                    String GPGGA_str =new String(buffer,0,len);
                    System.out.print(GPGGA_str);//new String(buffer,int offset,int length)
                    //nmea.parse(GPGGA_str);
                    //System.out.println(nmea.position);
                  		
                }
                System.out.println("ByeBye");
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            } 
           //}
        }
    }
    
    public static void main ( String[] args )
    {
        try
        {
            (new TwoWaySerialComm()).connect("COM15");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

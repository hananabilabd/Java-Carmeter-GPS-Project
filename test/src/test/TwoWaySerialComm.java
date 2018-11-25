
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
import java.io.Reader;
import java.io.StringReader;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.parser.*;
import net.sf.marineapi.nmea.sentence.GLLSentence;
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
                
		//GGASentence s = (GGASentence) event.getSentence();
		
		//System.out.println(s.getPosition());
                //System.out.println(event.getSentence());
                System.out.println(event.getSentence().toString());
                
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
                (new Thread(new SerialReader(in))).start();
                Thread th = new Thread(new ReadLine());
                th.start();
   
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    void inialize() throws IOException{
 
         reader = new SentenceReader(is);
         reader.setInputStream(is);
        //reader.addSentenceListener( this);
        //reader.addSentenceListener(this, SentenceId.GGA);
       
	reader.start();
    }
    class ReadLine implements Runnable 
    {
        public void run ()
        {   while(true)
            {
            try {
                while(buf != null &&(temp = buf.readLine()) != null){
                        //System.out.println(temp );
                       
                    SentenceFactory sf = SentenceFactory.getInstance();
                    Sentence s= sf.createParser(temp);
                    String id =s.getSentenceId();
                     System.out.println(id );
                    if("GLL".equals(s.getSentenceId())) {
				GLLSentence gll = (GLLSentence) s;
				System.out.println("GLL position: " + gll.getPosition());
                    } else if ("GGA".equals(s.getSentenceId())) {
                            GGASentence gga = (GGASentence) s;
                            System.out.println("GGA position: " + gga.getPosition());
                    }
                    
                    //if (SentenceValidator.isValid(temp)) {
                          //}
                }
            } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }      
        
        }
    }
     class SerialReader implements Runnable 
    {
        InputStream in;
        String temp2;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            //NMEA nmea = new NMEA();
      
            
            try
            {
                while (( len = this.in.read(buffer)) > -1 )
                {
                   is = new ByteArrayInputStream(buffer);
                   //is = this.in;
                   
                    String str =new String(buffer,0,len);
                  
                    if (str.equals("$")){flag =1;continue;}// this is beacuse when handling GGA sentence the mobile sends a newline $ then the GGA sentence
                    if (flag ==1){str="$"+str;flag =0;}
                    Reader inputString = new StringReader(str);
                    buf = new BufferedReader(inputString);
                    //System.out.println(str);//new String(buffer,int offset,int length)
                   
                 
                    
                    
                    
                    //nmea.parse(GPGGA_str);
                    //System.out.println(nmea.position);
                  		
                }
                System.out.println("ByeBye");
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            } 
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
            e.printStackTrace();
        }
    }
}

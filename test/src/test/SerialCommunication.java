
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
import java.util.Enumeration;
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
public class SerialCommunication  {

    SentenceParser parser;
    InputStream is;
    BufferedReader buf;
    int flag =0;
    String temp = null;
    String portName;
    
     public SerialCommunication()
    {
        super();
    }
    
     void connect () throws Exception
    {
        Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();
        while (e.hasMoreElements()) {
            System.out.println(e);
                        CommPortIdentifier portIdentifier = (CommPortIdentifier) e.nextElement();
                        if ( portIdentifier.isCurrentlyOwned() )
                        {
                            System.out.println("Error: Port is currently in use");
                        }
                        else{
                            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {//only serial ports will be handled
                                    portName = portIdentifier.getName();
                                    System.out.println(portName);//print the serial port name
                                    
                                    SerialPort sp = (SerialPort) portIdentifier.open(this.getClass().getName(),2000);
                                    sp.setSerialPortParams(19200, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                                    InputStream in = sp.getInputStream();
                                    (new Thread(new SerialReader(in))).start();
                                    //Thread th = new Thread(new ReadLine1());
                                    //th.start();
                            }
                        }                   
                                
			}
        /*              
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
        }*/     
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
            try
            {
                while (( len = this.in.read(buffer)) > -1 )
                {
                   //is = new ByteArrayInputStream(buffer);
                   //is = this.in;
                   
                    String str =new String(buffer,0,len);
                    
                    if (str.equals("$")){flag =1;continue;}// this is beacuse when handling GGA sentence the mobile sends a newline $ then the GGA sentence
                    if (flag ==1){str="$"+str;flag =0;}
                    Reader inputString = new StringReader(str);
                    buf = new BufferedReader(inputString);
                    //System.out.println(str);//new String(buffer,int offset,int length)
                   
                  		
                }
                System.out.println("ByeBye");
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            } 
        }
    }
    /*
    public static void main ( String[] args )
    {
        try
        {
            (new SerialCommunication()).connect();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
*/
}

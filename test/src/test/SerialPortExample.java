/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
/**
 *
 * @author Hanna Nabil
 */
public class SerialPortExample implements SentenceListener {

	/**
	 * Constructor
	 */
	public SerialPortExample() {
		init();
	}
	public void readingPaused() {System.out.println("-- Paused --");}
	public void readingStarted() {System.out.println("-- Started --");}
	public void readingStopped() {System.out.println("-- Stopped --");}
	public void sentenceRead(SentenceEvent event) {
                GGASentence s = (GGASentence) event.getSentence();
		System.out.println(s.getPosition());
	}

	
	private SerialPort getSerialPort() {
		try {
			Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();
                        System.out.println(e.toString());
			while (e.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) e.nextElement();
                               
				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                                        System.out.println(id.getName());//print the serial port name
					SerialPort sp = (SerialPort) id.open(this.getClass().getName(),800);

					sp.setSerialPortParams(4800, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                                        
                                        
					InputStream is = sp.getInputStream();
                                        //(new Thread(new SerialReader(is))).start();
                                       
					
					BufferedReader buf = new BufferedReader(new InputStreamReader(is));

					System.out.println("Scanning port " + sp.getName());

					/*
					for (int i = 0; i < 10; i++) {
						try {
							//String data = buf.readLine();
							if (SentenceValidator.isValid(buf.toString())) {
								System.out.println("NMEA data found!");
								return sp;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}*/
					is.close();
					//isr.close();
					buf.close();
                                    return sp;
				}
			}
			System.out.println("NMEA data was not found..");
                   
		} catch (Exception e) {
			e.printStackTrace();
		}
                
		return null;
                                        
	}

	/**
	 * Init serial port and reader.
	 */
	private void init() {
		try {
			SerialPort sp = getSerialPort();

			
				InputStream is = sp.getInputStream();
				SentenceReader sr = new SentenceReader(is);
				sr.addSentenceListener(this);
				sr.start();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Startup method, no arguments required.
	 * 
	 * @param args None
	 */
    public static class SerialReader implements Runnable 
     {
        InputStream in ;
        InputStreamReader isr ;
        BufferedReader buf ;
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
           
            buf = new BufferedReader(new InputStreamReader(in));
	
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len) );
                    System.out.print("hi");
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
	public static void main(String[] args) {
            System.loadLibrary("rxtxSerial");
		new SerialPortExample();
	}
    
}

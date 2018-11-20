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

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
	public void readingPaused() {
		System.out.println("-- Paused --");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
	public void readingStarted() {
		System.out.println("-- Started --");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
	 */
	public void readingStopped() {
		System.out.println("-- Stopped --");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
	 * .nmea.event.SentenceEvent)
	 */
	public void sentenceRead(SentenceEvent event) {
		// here we receive each sentence read from the port
		System.out.println(event.getSentence());
	}

	/**
	 * Scan serial ports for NMEA data.
	 * 
	 * @return SerialPort from which NMEA data was found, or null if data was
	 *         not found in any of the ports.
	 */
	private SerialPort getSerialPort() {
		try {
			Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();
                        
                        System.out.println(e.toString());
			while (e.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) e.nextElement();
                               
				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                                        System.out.println(id.getName());//print the serial port name
					SerialPort sp = (SerialPort) id.open("SerialExample", 30);

					sp.setSerialPortParams(5000, SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

					InputStream is = sp.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader buf = new BufferedReader(isr);

					System.out.println("Scanning port " + sp.getName());

					// try each port few times before giving up
					for (int i = 0; i < 5; i++) {
						try {
							String data = buf.readLine();
							if (SentenceValidator.isValid(data)) {
								System.out.println("NMEA data found!");
								return sp;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					is.close();
					isr.close();
					buf.close();
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

			if (sp != null) {
				InputStream is = sp.getInputStream();
				SentenceReader sr = new SentenceReader(is);
				sr.addSentenceListener(this);
				sr.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Startup method, no arguments required.
	 * 
	 * @param args None
	 */
	public static void main(String[] args) {
            System.loadLibrary("rxtxSerial");
		new SerialPortExample();
	}
    
}

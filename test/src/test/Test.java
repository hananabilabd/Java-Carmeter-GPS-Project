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
public class Test {

    /**
     * @param args the command line arguments
     */
    static String GPGGA_Test = "$GPGSV,3,3,11,23,54,026,00,26,09,046,00,30,20,219,00*49";
    public static void main(String[] args) {
        // TODO code application logic here
        NMEA nmea = new NMEA();
        nmea.parse(GPGGA_Test);
        System.out.println(nmea.position);
    }
    
}

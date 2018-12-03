
package carmeter;
//import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
/**
 *
 * @author Hanna Nabil
 */
public class SerialCommunication  {
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

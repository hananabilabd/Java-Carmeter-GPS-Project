/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

 
/**
 *
 * @author esraa
 */
public class WifiCommunication {
    Socket mySocket;
DataInputStream dis;
    PrintStream ps;

    
        public static void main(String[] args) {
    new Client();
        }
        
        public Client(){
        
        try{
           mySocket=new Socket("192.168.43.118",5555);
           dis=new DataInputStream(mySocket.getInputStream());
           ps=new PrintStream(mySocket.getOutputStream());
            ps.println("Test");
        }    
    
    catch(IOException ex){
        ex.printStackTrace();
        }
        while(true){
            String replyMsg;
            try {
                replyMsg = dis.readLine();
              //  if ( replyMsg.contentEquals("$GPGGA"))
                System.out.println(replyMsg);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
     
        }
}
}
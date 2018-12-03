package carmeter;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.skins.ModernSkin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 *
 * @author Hanna Nabil
 */
public class Carmeter extends Application implements MapComponentInitializedListener {
    GoogleMapView mapView;
    GoogleMap map;
    SerialCommunication serialComm ;
    AudioPlayer audioPlayer;
    MarkerOptions markerOptions;
    Marker marker ;
    MapOptions mapOptions;
    double i = 0.0001;
    double latitude=30.0813565;double longitude=31.2383316; double speed =0;
   // TextField text_latitude;
    Text text_latitude;
    Text text_longitude;
    Text text_speed;
    Gauge gauge;
    Thread thread_readLine;
    int flag_position=0;
    public void alarmON(){
        try {
                    audioPlayer.restart();
                } catch (IOException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                }
        
    }
    public void alarmOFF(){
            try {
                    audioPlayer.stop();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Carmeter.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    @Override
    public void init(){
        try {
            serialComm=new SerialCommunication();
            serialComm.connect();
            thread_readLine = new Thread(new ReadLine());
            audioPlayer =  new AudioPlayer();
            //thread_readLine.start();
         
        } catch (Exception ex) {
            System.out.println("Init Exception");
            ex.printStackTrace();
        }
    }
   @Override
    public void start(Stage primaryStage) throws Exception {
 
        Button button1 = new Button("Start");
        Button button2 = new Button("Stop");
        button2.setDisable(true);
        Button button3 = new Button("Soun-On");
        Button button4 = new Button("Soun-Off");
      button1.setOnAction(new EventHandler<ActionEvent>() {//start button

            @Override
            public void handle(ActionEvent event) {
                if (thread_readLine.isAlive()==false){
            thread_readLine.start();}
            else {
                thread_readLine.resume();
            }
                button1.setDisable(true);button2.setDisable(false);
            }
        });
      
       button2.setOnAction(new EventHandler<ActionEvent>() {//stop button
            @Override
            public void handle(ActionEvent event) {
                if (thread_readLine.isAlive()==true){
                    thread_readLine.suspend();
                }
                button2.setDisable(true);
                button1.setDisable(false);
            //    text_latitude.clear();
              //  text_longitude.clear();
               // text_speed.clear();
                flag_position =0;
            }
        });
       
        button3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               alarmON();
                
            }
        });
         button4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                alarmOFF();
            }
        });
        gauge = GaugeBuilder.create()
//                         .skin(ModernSkin.class)
                         .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                                   new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                                   new Section(95, 100, "", Color.rgb(204, 0, 0)))
                         .title("Speed")
                         .unit("UNIT")
                         .threshold(85)
                         .thresholdVisible(true)
                         .animated(true)
                         .build();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
          
        
        button1.setId("shiny-orange");
        button2.setId("shiny-orange");
        button3.setId("round-red");
        button4.setId("round-red");
        button1.setMinHeight(50);
        button1.setMinWidth(50);
        button2.setMinHeight(50);
        button2.setMinWidth(50);
        button3.setMinHeight(40);
        button3.setMinWidth(40);
        button4.setMinHeight(40);
        button4.setMinWidth(40);
        
        
        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 2, 0, 1, 1);
        gridPane.add(button3, 4, 0, 1, 1);
        gridPane.add(button4, 6, 0, 1, 1);
        gridPane.add(gauge, 2, 4, 2, 2);
   
        Label label_lattitude = new Label("lattitude :");
         label_lattitude.setFont(Font.font("Tahoma", FontWeight.THIN, 20));
          gridPane.add(label_lattitude, 2, 15, 1, 1);
          Text text_latitude = new Text("");
       //   text_latitude.setEditable(false);
         
          gridPane.add(text_latitude, 3, 15, 1, 1);
        
         Label label_longitude = new Label("longitude :");
         label_longitude.setFont(Font.font("Tahoma", FontWeight.THIN,20));
         gridPane.add(label_longitude, 2, 20, 1, 1);
          text_longitude = new Text();
          //text_longitude.setEditable(false);
          gridPane.add(text_longitude, 3, 20, 1, 1);
         
         Label label_speed = new Label("Speed :");
         label_speed.setFont(Font.font("Tahoma", FontWeight.THIN, 20));
         gridPane.add(label_speed, 2, 25, 1, 1);
          text_speed = new Text();
         // text_speed.setEditable(false);
          gridPane.add(text_speed, 3, 25, 1, 1);
          
       //    text_latitude.setId("lion-default");
           text_longitude.setId("lion-default");
          text_speed.setId("lion-default");
     
        
          
//          text_longitude.setMinHeight(25);
//          text_longitude.setMinWidth(10);
//          
          //gridPane.setGridLinesVisible(true);
 //__________________________________________________________________________________________
          
          HBox hbox = new HBox();
          mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
 
    
        hbox.getChildren().addAll(mapView,gridPane);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        
          Stop[] stops = new Stop[] { new Stop(0, Color.BLUE), new Stop(1, Color.AZURE)}; 
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 0.5, true, CycleMethod.NO_CYCLE, stops);
        
        Rectangle r1 = new Rectangle(0, 0, 1400, 1000);
        r1.setId("grad");

       StackPane root = new StackPane();
        root.setId("grad");
         root.getChildren().add(r1); 
         root.getChildren().addAll(hbox);
        
        Scene scene = new Scene(root, 240, 100);
        scene.getStylesheets().add(getClass().getResource("/css/css.css").toString());
        //Scene scene = new Scene(hbox,1300,600);
        
        primaryStage.setTitle("CarMeter");
        primaryStage.getIcons().add(new Image("maps.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void mapInitialized() {
    //Set the initial properties of the map.
    mapOptions = new MapOptions();
    mapOptions.center(new LatLong(30.08056024, 31.23717248))
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .zoom(13);

    map = mapView.createMap(mapOptions);
    markerOptions = new MarkerOptions();
    markerOptions.position( new LatLong(latitude, longitude) )
                .visible(Boolean.TRUE)
                .title("My Marker");

    marker = new Marker( markerOptions );
    map.addMarker(marker);
    map.removeMarker(marker);
                    
    Thread t = new Thread( () -> {
        while (true){
           try {
               Thread.sleep(2000);
               //System.out.println("Calling showDirections from Java");
               Platform.runLater(() -> {
                   if (flag_position ==1){
                        //i+=0.001;
                        map.removeMarker(marker);
                       markerOptions = new MarkerOptions();
                       markerOptions.position( new LatLong(latitude,longitude) )
                        .visible(Boolean.TRUE)
                        .title("My Marker");
                         marker = new Marker( markerOptions );
                        map.addMarker(marker);
                        map.setCenter(new LatLong(latitude, longitude));
                        gauge.setValue(speed);
                   }
                   else {map.clearMarkers();
                   }
                       });
           } catch( Exception ex ) {
               ex.printStackTrace();
           }
    }});
        t.start();

}
  
    class ReadLine implements Runnable 
    {
        public void run ()
        {  
            while(true)
            {
            try {
                while(serialComm.buf != null &&((serialComm.temp = serialComm.buf.readLine()) != null)){
                    if (SentenceValidator.isValid(serialComm.temp)) {
                    //System.out.println(serialComm.temp );
                    
                    SentenceFactory sf = SentenceFactory.getInstance();
                    Sentence s= sf.createParser(serialComm.temp);
                
                    if("RMC".equals(s.getSentenceId())) { 
				RMCSentence rmc= (RMCSentence) s;
                                speed =rmc.getSpeed();
                                System.out.println("RMC speed: " + rmc.getSpeed());
                                
                                text_speed.setText(Double.toString(rmc.getSpeed()));
                                if (speed >1){alarmON();}
                                else {alarmOFF();}
         
                    }
                    else if ("GGA".equals(s.getSentenceId())) {
                            GGASentence gga = (GGASentence) s;

                            latitude=gga.getPosition().getLatitude();
                            longitude = gga.getPosition().getLongitude();
                         //   text_latitude.setText(Double.toString( latitude));
                            text_longitude.setText(Double.toString( longitude));
                            //System.out.println("latitude: " + latitude);
                            //System.out.println(",longitude: " + longitude);
                            System.out.println("GGA position: " + gga.getPosition());
                            flag_position=1;
                    }
                          }
                }
            } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }      
        
        }
    }
 
    public static void main(String[] args) {
        
        launch(args);
    }

    
}

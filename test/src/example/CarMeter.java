/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.skins.ModernSkin;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Basma
 */
public class CarMeter extends Application implements Runnable {
    
   @Override
    public void start(Stage primaryStage) {

        Button button1 = new Button("start");
        Button button2 = new Button("stop");
        Button button3 = new Button("Button 3");
        
        
      button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
       
            }
        });
      
       button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
            }
        });
       
        button3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
            }
        });

        
        Gauge gauge = GaugeBuilder.create()
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

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(gauge, 3, 3, 2, 2);
        

   
        Label lattitude = new Label("lattitude :");
         lattitude.setFont(Font.font("Tahoma", FontWeight.THIN, 20));
          gridPane.add(lattitude, 2, 10, 1, 1);
          
          TextField text = new TextField ();
          gridPane.add(text, 3, 10, 1, 1);
        
         Label longitude = new Label("longitude :");
         longitude.setFont(Font.font("Tahoma", FontWeight.THIN,20));
         gridPane.add(longitude, 2, 15, 1, 1);
          TextField text2 = new TextField ();
          gridPane.add(text2, 3, 15, 1, 1);
         
         Label speed = new Label("speed :");
         speed.setFont(Font.font("Tahoma", FontWeight.THIN, 20));
         gridPane.add(speed, 2, 20, 1, 1);
          TextField text3 = new TextField ();
          gridPane.add(text3, 3, 20, 1, 1);
        
        Scene scene = new Scene(gridPane, 240, 100);
        primaryStage.setTitle("CarMeter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

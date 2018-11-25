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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        
        gridPane.add(gauge, 3, 1, 2, 2);
    

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

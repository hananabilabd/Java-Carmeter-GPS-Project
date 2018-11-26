/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;



/**
 *
 * @author Hanna Nabil
 */
public class JavaFX_GoogleMaps extends Application {

  private Scene scene;
  MyBrowser myBrowser;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
      primaryStage.setTitle("java-buddy.blogspot.com");
    
      myBrowser = new MyBrowser();
      scene = new Scene(myBrowser, 640, 480);
    
      primaryStage.setScene(scene);
      primaryStage.show();
  }

  class MyBrowser extends Region{
    
      HBox toolbar;
    
      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
    
      public MyBrowser(){
        
          final URL urlGoogleMaps = getClass().getResource("googlemaps.html");
          webEngine.load(urlGoogleMaps.toExternalForm());

          getChildren().add(webView);
        
      }
    
  }
}
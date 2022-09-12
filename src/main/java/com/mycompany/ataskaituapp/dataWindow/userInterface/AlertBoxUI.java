
package com.mycompany.ataskaituapp.dataWindow.userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBoxUI {
    
    public static void createWindow(String text, int width, int height) {
        
        //Cia dar reiketu sutvrakyti vaizd, kad graziaau atrodytu viskas
        //poblema dar yra tokia, kad kai isjungiu si langa, uzsidaro ir HistoryUI...
        
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30, 30, 30, 30));
        
        Separator separator = new Separator(Orientation.HORIZONTAL);
        
        Label labelFault = new Label("Klaida!");
        labelFault.setFont(new Font(20));
        HBox hbox = new HBox();
        hbox.getChildren().addAll(labelFault);  
        
        Label label = new Label(text);
        label.setWrapText(true);
        label.setFont(new Font(15));
        Button button = new Button("OK");
        button.setPadding(new Insets(10,20,10,20));
        VBox.setMargin(button, new Insets(20, 0, 20, 0));
        
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        });
        
        layout.getChildren().addAll(hbox, separator, label, button);   
        
        Stage stage = new Stage();
//        Scene scene = new Scene(layout, 350, 200);
        Scene scene = new Scene(layout, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
  
    }
    
//    public static void alertBoxForReport() {
//        
//        createWindow("Įvesti laikotarpiai negali pasikartoti", 350, 200);
//    }
    
}

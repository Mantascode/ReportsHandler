
package com.mycompany.ataskaituapp.dataWindow.userInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.dataWindow.domain.Publisher;
import com.mycompany.ataskaituapp.dataWindow.domain.PublisherDao;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class DeletePublisherUI {
    
    private Publisher pub;
    private VBox mainLayout;
    
    public DeletePublisherUI(Publisher pub) {
        
        this.pub = pub;
        this.mainLayout = new VBox(10);
        
    }
    
    public VBox getView() {

        mainLayout.setPadding(new Insets(40, 40, 40, 40));
        mainLayout.setAlignment(Pos.CENTER);
        
        Label text = new Label("Ištrinti įrašą: " + pub.getName() + " " + pub.getLastName());
        text.setFont(new Font(20));
        Separator separator = new Separator(Orientation.HORIZONTAL);
        
        
        HBox box1 = new HBox();
        Label text1 = new Label("VISIŠKAS IŠTRYNIMAS");
        text1.setAlignment(Pos.TOP_LEFT);
        Label text1_1 = new Label("Ištrina visiškai šio skelbėjo duomenis tiek iš šios duomenų bazės, tiek ir iš bendruomenės skelbimo ataskaitų. Šis veiksmas gali turėti įtaką bendruomenės anktesnių mėnesių skelbimo statistikai. Šį veiksmą geriausia naudoti tada, kai norima visiškai pašalinti bet kokius skelbėjo duomenis šioje programoje"); 
        text1_1.setTextAlignment(TextAlignment.JUSTIFY);
        text1_1.setMaxWidth(400);
        text1_1.setMaxHeight(100);
        text1_1.setWrapText(true);
        Button button1 = new Button("Vykdyti");
        button1.setOnAction(e -> deleteAll());
        HBox.setMargin(button1, new Insets(20, 0, 0, 20));
        box1.getChildren().addAll(text1_1, button1);
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        
        HBox box2 = new HBox();
        Label text2 = new Label("DALINIS IŠTRYNIMAS");
        Label text2_1 = new Label("Iština šio skelbėjo duomenis iš šios duomenų bazės (duomenys nebebus matomi), bet palieka skelbėjo tarnybos įrašus bendruomenės skelbimo ataskaitose. Tai leidžia turėti tikslią bendruomenės ankstesnių mėnesių tarnybos statistiką. Šį veiksmą geriausia naudoti tada, kai skelbėjas persikelia į kitą bendruomenę ir norima išlaikyti tikslią tarnybos ataskaitų statistiką."); 
        text2_1.setTextAlignment(TextAlignment.JUSTIFY);
        text2_1.setMaxWidth(400);
        text2_1.setMaxHeight(100);
        text2_1.setWrapText(true);
        Button button2 = new Button("Vykdyti");
        button2.setOnAction(e -> deletePart());
        HBox.setMargin(button2, new Insets(20, 0, 0, 20));
        box2.getChildren().addAll(text2_1, button2);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        
        Button button3 = new Button("Atšaukti");
        button3.setOnAction(e -> {
            Stage stage = (Stage) button3.getScene().getWindow();
            stage.close();
        });
            
        button3.setAlignment(Pos.CENTER);
        
        
        mainLayout.getChildren().addAll(text, separator, text1, box1, separator1, text2, box2, separator2, button3);
        
        return mainLayout;
    }
    
    public void deleteAll() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        ReportDao reportOb = new ReportDao(link);
        PublisherDao publisherOb = new PublisherDao(link);
        
        reportOb.delete(pub.getId());
        publisherOb.delete(pub.getId());
        
        Stage stage = (Stage) this.mainLayout.getScene().getWindow();
        stage.close();
        
    }
    
    public void deletePart() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        PublisherDao publisherOb = new PublisherDao(link);
        
        publisherOb.partialDelete(pub.getId());
        
        Stage stage = (Stage) this.mainLayout.getScene().getWindow();
        stage.close();
        
    }
    
}

package com.mycompany.ataskaituapp;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.MinutesReport;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.MinutesReportDao;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.homePage.domain.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import com.mycompany.ataskaituapp.dataWindow.userInterface.DataUI;
import com.mycompany.ataskaituapp.dataWindow.domain.PublisherDao;
import com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface.CongregationReportUI;
import com.mycompany.ataskaituapp.dataWindow.domain.YearsMonthsServiceConverter;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettingsDao;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.collections.transformation.TransformationList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;



public class App extends Application {

    @Override
    public void start(Stage stage) throws UnsupportedEncodingException {
        
        //Testui
        

//        MinutesReportDao check = new MinutesReportDao(80, 1875, link);
//        check.updateMinutes(0.75);
//        check.minusMinutes();
        

        
        
        //GERAS KODAS APACIOJE
        
        //Pagrindinis langas. Atskiria meniu nuo darbo langu
        
        
        //Jei pirma karta prisijungta nauja menesi, tada iraso naujus menesius
        //i bendruomenes ataskaita
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        
        AddNewMonthsDao obDao = new AddNewMonthsDao(link);
        AddNewMonths obNewMonth = new AddNewMonths(obDao);
        obNewMonth.checkLastConnetcion();
        
        
        
        
        BorderPane mainLayout = new BorderPane();
        
        //Šis kodas leidžia naudoti utf-8!!!! Taip pat ir į config idėjau reikalingą eilutę
        System.setOut(new PrintStream(System.out, true, "UTF8"));
        
        //Meniu stulepelis kaireje
        VBox menuVBox = new VBox(30);
        menuVBox.setPadding(new Insets(40, 40, 40, 40));
        
        //Meniu mygukai  
        Button buttonHome = new Button("Pradžia");
        buttonHome.setWrapText(true);
        buttonHome.setFont(new Font(20));
        buttonHome.setMinSize(200, 80);
        buttonHome.setTextAlignment(TextAlignment.CENTER);

        Button buttonCongregationReport = new Button("Bendruomenės ataskaita");
        buttonCongregationReport.setWrapText(true);
        buttonCongregationReport.setFont(new Font(20));
        buttonCongregationReport.setMinSize(200, 80);
        buttonCongregationReport.setMaxSize(200, 80);
        buttonCongregationReport.setTextAlignment(TextAlignment.CENTER);
        buttonCongregationReport.setOnAction(e -> {
            CongregationReportUI ob = new CongregationReportUI(mainLayout);
            ob.start();
        });
        
        Button buttonPublisherReport = new Button("Skelbėjo ataskaita");
        buttonPublisherReport.setWrapText(true);
        buttonPublisherReport.setFont(new Font(20));
        buttonPublisherReport.setMinSize(200, 80);
        buttonPublisherReport.setMaxSize(200, 80);
        buttonPublisherReport.setTextAlignment(TextAlignment.CENTER);
        
        Button buttonCongregationAttendance = new Button("Bendruomenės lankomumas");
        buttonCongregationAttendance.setWrapText(true);
        buttonCongregationAttendance.setFont(new Font(20));
        buttonCongregationAttendance.setMinSize(200, 80);
        buttonCongregationAttendance.setMaxSize(200, 80);
        buttonCongregationAttendance.setTextAlignment(TextAlignment.CENTER);
        
        Button buttonData = new Button("Duomenys");
        buttonData.setWrapText(true);
        buttonData.setFont(new Font(20));
        buttonData.setMinSize(200, 80);
        buttonData.setOnAction(e -> {
            DataUI ob = new DataUI(mainLayout);
            ob.start();
        });
        
        Button buttonSettings = new Button("Nustatymai");
        buttonSettings.setWrapText(true);
        buttonSettings.setFont(new Font(20));
        buttonSettings.setMinSize(200, 80);
        
        menuVBox.getChildren().addAll(buttonHome, buttonCongregationReport,
                buttonPublisherReport, buttonCongregationAttendance, buttonData, buttonSettings);
        
        
        mainLayout.setLeft(menuVBox);
        
        Scene scene = new Scene(mainLayout, 640, 480);
        scene.getStylesheets().addAll(getClass().getResource("/border.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }

}
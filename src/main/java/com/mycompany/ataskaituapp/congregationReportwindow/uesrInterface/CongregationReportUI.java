
package com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface;


import com.mycompany.ataskaituapp.congregationReportwindow.domain.MinutesReportDao;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettingsDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


public class CongregationReportUI {
    
    private BorderPane mainLayout;
    private ChoiceBox yearChoice;
    private ChoiceBox monthChoice;
    private ChoiceBox choiceGroup;
    private ObservableList<Report> dataObsList;
    private ObservableSet<Report> setOfEditedItems;
    private ObservableSet<Report> setOfEditedItemsWhereWasMinutesBefore;
    private ObservableSet<Report> setOfEditedItemsWhereWasMinutesAfter;
    private VBox rightSide;
    private ReportDao reportOb;
    private MinutesReportDao minRep;
    private Button buttonSave;
    
    public CongregationReportUI(BorderPane window) {
        this.mainLayout = window;
        this.yearChoice = new ChoiceBox();
        this.monthChoice = new ChoiceBox();
        this.choiceGroup = new ChoiceBox();
        this.dataObsList = FXCollections.observableArrayList();
        this.setOfEditedItems = FXCollections.observableSet();
        this.setOfEditedItemsWhereWasMinutesBefore = FXCollections.observableSet();
        this.setOfEditedItemsWhereWasMinutesAfter = FXCollections.observableSet();
        this.rightSide = new VBox();
        this.buttonSave = new Button();
    }
    
    public void start() {
        HBox mainHBox = new HBox();
        
        //Kaires puses stulpelis
        VBox leftSide = new VBox();
        VBox SettingsVBox = new VBox();
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        YearsAndMonthsSettings datesOb = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
        this.minRep = new MinutesReportDao(link);
   
        datesOb.createHashMap();

        buttonSave.setDisable(true);
        
        Label yearLabel = new Label("Metai:");
        yearLabel.setPadding(new Insets(0, 10, 0, 10));
        this.yearChoice.getItems().addAll(datesOb.getYears1());
        this.yearChoice.setPrefWidth(90);
        this.yearChoice.setValue(setDefaultYear());
        VBox.setMargin(this.yearChoice, new Insets(0, 10, 15, 10));

        
        //Idomu, kad ChoiceBox grazina ne String, o int kaip index. Taigi, jei butu cia surasyti menesiai, man grzitu
        //tik int, o ne menuo. Tai palengvina darba, jeigu cia noriu manyti ne skaicius, o menesius.
        Label monthLabel = new Label("Mėnuo:");
        monthLabel.setPadding(new Insets(0, 10, 0, 10));
        this.monthChoice.setPrefWidth(90);
        this.monthChoice.getItems().addAll(datesOb.getMonthByYearFrom1((Integer)this.yearChoice.getValue()));
        this.monthChoice.setValue(setDefaultMonth());
        VBox.setMargin(this.monthChoice, new Insets(0, 10, 15, 10));
        
        Label choiceLabel = new Label("Rodyti pagal:");
        choiceLabel.setPadding(new Insets(0, 10, 0, 10));
        choiceGroup.setPrefWidth(90);
        choiceGroup.getItems().addAll(new ArrayList<String>(Arrays.asList("tarnystę", "tarn. grupę", "rodyti visus")));
        choiceGroup.getSelectionModel().selectFirst();
        VBox.setMargin(this.choiceGroup, new Insets(0, 10, 15, 10));
        
        ChangeListener monthListener = new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {  
                
                showTable((Integer) yearChoice.getValue(), datesOb.getMonthByYearFrom1((Integer)yearChoice.getValue()).get((Integer)t1), choiceGroup.getSelectionModel().getSelectedIndex());
                
            }
        };
        monthChoice.getSelectionModel().selectedIndexProperty().addListener(monthListener);
        
        
        choiceGroup.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                showTable((Integer) yearChoice.getValue(), (Integer) monthChoice.getValue(), (Integer) t1);
            }
        });
        
        
        this.yearChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                monthChoice.getSelectionModel().selectedIndexProperty().removeListener(monthListener);
                
                int month1 = (Integer) monthChoice.getValue();
                System.out.println(month1);                
                monthChoice.getItems().clear();
                ArrayList<Integer> monthList = datesOb.getMonthByYearFrom1(datesOb.getYears1().get((Integer)t1));
                
                if(monthList.contains(month1)) {
                    monthChoice.getItems().addAll(monthList);
                    monthChoice.setValue(month1);
                } else {
                    monthChoice.getItems().addAll(monthList);
                    monthChoice.getSelectionModel().selectFirst();
                }
                
                monthChoice.getSelectionModel().selectedIndexProperty().addListener(monthListener);
                
                showTable(datesOb.getYears1().get((Integer) t1), getValueChoiceBoxInteger(monthChoice), choiceGroup.getSelectionModel().getSelectedIndex());
                
                
            }
        });
        

        
        BorderedTitledPane borderSettings = new BorderedTitledPane("Nustatymai", SettingsVBox);
        borderSettings.setPrefSize(150, 10);
        borderSettings.setMaxHeight(100);
        
        Button auxSettings = new Button("Pagalbinių pionierių\nnustatymai");
        auxSettings.setWrapText(true);
        auxSettings.setTextAlignment(TextAlignment.CENTER);
        auxSettings.setPrefWidth(130);
        VBox.setMargin(auxSettings, new Insets(0, 0, 20,0));
        
        Button reportToBranch = new Button("Ataskaita filialui");
        reportToBranch.setOnAction(e -> {
            
            actionAfterSaveButtonIsPressed();

            ReportForBranchUI repBranch = new ReportForBranchUI((Integer)yearChoice.getValue(),
                    (Integer)monthChoice.getValue());
            
            VBox view = repBranch.getView();
            view.setPadding(new Insets(40, 40, 40, 40));
            
            Stage stage = new Stage();

            stage.sizeToScene();
            stage.setResizable(false);

            stage.setMinWidth(500);
            stage.setMinHeight(600);
            Scene scene = new Scene(view);
            
            
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        });
        
        reportToBranch.setWrapText(true);
        reportToBranch.setTextAlignment(TextAlignment.CENTER);
        reportToBranch.setPrefWidth(130);
        VBox.setMargin(reportToBranch, new Insets(0, 0, 20,0));

        
        SettingsVBox.getChildren().addAll(yearLabel, this.yearChoice, monthLabel, monthChoice, choiceLabel, choiceGroup, auxSettings);
        leftSide.getChildren().addAll(borderSettings, auxSettings, reportToBranch);
        
        
        //Desines puses stulpelis   
        
        this.reportOb = new ReportDao(link);
        
        ArrayList<Report> list = reportOb.getReportByYearAndMonth((Integer)yearChoice.getValue(), (Integer)monthChoice.getValue());
        
        this.dataObsList.addAll(list);
        
        //DataInTable tableOb = new DataInTable(this.dataObsList, this.setOfEditedItems);
        DataInTable tableOb = new DataInTable(this.dataObsList, this.setOfEditedItems, this.setOfEditedItemsWhereWasMinutesBefore, this.setOfEditedItemsWhereWasMinutesAfter, this.buttonSave);
        
        rightSide.getChildren().addAll(tableOb.showByService(), addButtons());
        rightSide.setPadding(new Insets(20,0,20,0));
        
        mainHBox.getChildren().addAll(leftSide, rightSide);
        this.mainLayout.setCenter(mainHBox);
    }    
    
    private void showTable(int y, int m, int g) {

        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        ReportDao reportOb = new ReportDao(link);
        
        ArrayList<Report> list = reportOb.getReportByYearAndMonth(y, m);
        this.dataObsList.clear();
        this.dataObsList.addAll(list);
//        this.setOfEditedItems.clear(); //Galbut ir si koda reiketu naudoti, dar nezinau...
        
//        DataInTable tableOb = new DataInTable(this.dataObsList, this.setOfEditedItems);
        DataInTable tableOb = new DataInTable(this.dataObsList, this.setOfEditedItems, this.setOfEditedItemsWhereWasMinutesBefore, this.setOfEditedItemsWhereWasMinutesAfter, this.buttonSave);
        
        rightSide.getChildren().clear();
        
        if(g == 0) {
            rightSide.getChildren().addAll(tableOb.showByService(), addButtons());
        } else if (g == 1) {
            rightSide.getChildren().addAll(tableOb.showByGroup(), addButtons());
        } else {
            rightSide.getChildren().addAll(tableOb.showAll(), addButtons());
        }
        
        

    }
    
    private HBox addButtons() {
        HBox hBox = new HBox(15);
        
        this.buttonSave.setText("Išsaugoti");
//        buttonSave.setDisable(true);
        
        HBox.setMargin(buttonSave, new Insets(10, 0, 20, 0));
        buttonSave.setOnAction(e -> {

//            for(Report r: this.setOfEditedItems) {
//                System.out.println(r.toStringFull());
//            }
//
//            if(!this.setOfEditedItemsWhereWasMinutesBefore.isEmpty()) {
//                this.minRep.minusMinutes(setOfEditedItemsWhereWasMinutesBefore);
//                this.setOfEditedItemsWhereWasMinutesBefore.clear();
//            }
//            
//            if(!this.setOfEditedItemsWhereWasMinutesAfter.isEmpty()) {
//                this.minRep.updateMinutes(setOfEditedItemsWhereWasMinutesAfter);
//                this.setOfEditedItemsWhereWasMinutesAfter.clear();
//            }
//            
//                    
//            reportOb.update(setOfEditedItems);
//            this.setOfEditedItems.clear();
//            
//            buttonSave.setDisable(true);

            actionAfterSaveButtonIsPressed();

        });
        
        Button buttonUpdate = new Button("Atnaujinti");
        HBox.setMargin(buttonUpdate, new Insets(10, 0, 20, 0));
        
        Button buttonDelete = new Button("Pašalinti");
        HBox.setMargin(buttonDelete, new Insets(10, 0, 20, 0));
        
        hBox.getChildren().addAll(buttonSave, buttonUpdate, buttonDelete);
        
        return hBox;
        
    }
    
    private void actionAfterSaveButtonIsPressed() {


        for(Report r: this.setOfEditedItems) {
            System.out.println(r.toStringFull());
        }

        if(!this.setOfEditedItemsWhereWasMinutesBefore.isEmpty()) {
            this.minRep.minusMinutes(setOfEditedItemsWhereWasMinutesBefore);
            this.setOfEditedItemsWhereWasMinutesBefore.clear();
        }

        if(!this.setOfEditedItemsWhereWasMinutesAfter.isEmpty()) {
            this.minRep.updateMinutes(setOfEditedItemsWhereWasMinutesAfter);
            this.setOfEditedItemsWhereWasMinutesAfter.clear();
        }

        reportOb.update(setOfEditedItems);
        this.setOfEditedItems.clear();

        buttonSave.setDisable(true);

    }
    
    public Integer getValueChoiceBoxInteger(ChoiceBox node) {
        return (Integer) node.getValue();
    }
    
    private int setDefaultMonth() {
        LocalDate today = LocalDate.now();
        if(today.getMonthValue() - 1 == 0) {
            return 12;
        } else {
            return today.getMonthValue() - 1;
        } 
    }
    
    
    private int setDefaultYear() {
        
        LocalDate today = LocalDate.now();
        
        if(today.getMonthValue() == 1) {
            return today.getYear() - 1;
        } else {
            return today.getYear();
        }

    }
    
    private ArrayList<String> monthsName() {
        
        return new ArrayList<>(Arrays.asList("Sausis", "Vasaris", "Kovas",
                "Balandis", "Gegužė", "Birželis", "Liepa", "Rugpjūtis",
                "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"));
        
    }
      
}


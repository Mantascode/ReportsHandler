
package com.mycompany.ataskaituapp.dataWindow.userInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.dataWindow.domain.CheckData;
import com.mycompany.ataskaituapp.dataWindow.domain.FromToService;
import com.mycompany.ataskaituapp.dataWindow.domain.YearsMonthsServiceConverter;
import com.mycompany.ataskaituapp.settings.domain.ServiceGroupDao;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettingsDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class HistoryUINew{
    
    private ArrayList<FromToService> listYearMonth;
    private ArrayList<Report> listReport;
    private int countRows;
    private FlowPane container;
    private ArrayList<HBox> listHBox;
    private boolean flagInsert;
    private YearsAndMonthsSettings date;
    private String group;
    private ArrayList<String> groupList;
    
    public HistoryUINew(ArrayList<Report> list, boolean flagInsert, String group) {
        this.listReport = list;
        this.container = new FlowPane();
        this.countRows = 0;
        this.listHBox = new ArrayList<>();
        this.flagInsert = flagInsert;
        this.group = group;
    }
    
    public void getData(ArrayList<Report> list) {
        this.listReport = list;
    }
    
    public VBox getView() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        this.date = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
        this.groupList = new ServiceGroupDao(link).listOfGroupNames();
   
        this.date.createHashMap();
        
        this.listYearMonth = YearsMonthsServiceConverter.listForShowing2(this.listReport);

        VBox mainLayout = new VBox();
        mainLayout.setPadding(new Insets(40, 40, 40, 40));
        
        Label text = new Label("Įveskite skelbejo teokratinę istoriją.");
        text.setPadding(new Insets(0, 10, 10, 0));
        
        Label text2 = new Label("Pridėti naują eilutę: ");
        text2.setPadding(new Insets(0, 0, 0, 0));
        
        this.container.setVgap(5);
        
        Button add = new Button("Pridėti");
        add.setOnAction(e -> {
            
            HBox newHBox = addRow();
            container.getChildren().add(newHBox);
            this.listHBox.add(newHBox);

            this.countRows = 0;
            for(HBox h: this.listHBox) {
                rewriteButton((Button)h.getChildren().get(7));
            }

        });
                
        Label textService = new Label("Tarnystė");
        textService.setPadding(new Insets(0, 0, 0, 40));
        
        Label textFrom = new Label("Nuo\nMetai        Mėnuo");
        textFrom.setTextAlignment(TextAlignment.CENTER);
        textFrom.setPadding(new Insets(0, 0, 0, 65));
        
        Label textTo = new Label("Iki\nMetai        Mėnuo");
        textTo.setTextAlignment(TextAlignment.CENTER);
        textTo.setPadding(new Insets(0, 0, 0, 55));
        
        Label textAddToCongregation = new Label("Pridėti prie\nbendruomenės\nataskaitos");
        textAddToCongregation.setTextAlignment(TextAlignment.CENTER);
        textAddToCongregation.resize(10, 10);
        textAddToCongregation.setPadding(new Insets(0, 0, 0, 10));
        
        Label textGroup = new Label("Tarnybos\ngrupė");
        textGroup.setTextAlignment(TextAlignment.CENTER);
        textGroup.resize(10, 10);
        
        //Tai padeda graziai islygiuoti teskta, kad rodyti per vidur5, nesvarbu kokio ilgio grupe
        container.widthProperty().addListener(e -> textGroup.setPadding(new Insets(0, 0, 0, ((container.getWidth() - 606) / 2 - 25) + 10)));
        
        HBox hBoxForAnnotation = new HBox();
        hBoxForAnnotation.setAlignment(Pos.BOTTOM_LEFT);
        hBoxForAnnotation.setPadding(new Insets(10, 0, 0, 0));
        hBoxForAnnotation.getChildren().addAll(textService, textFrom, textTo, textAddToCongregation, textGroup);
        
        
        HBox hBoxForInsertRow = new HBox();
        hBoxForInsertRow.setPadding(new Insets(5, 0, 5, 0));
        hBoxForInsertRow.getChildren().addAll(text2, add);
        
        ScrollPane scrollP = new ScrollPane(); 

        //Jei datos netinkama ir yra tuscia, tada reiketu naudoti kokias default datas, kad nebutu tuscia...
        for(FromToService dates: checkDates(this.listYearMonth)) {
            HBox newHBox1 = showTable(dates);
            container.getChildren().add(newHBox1);
            this.listHBox.add(newHBox1);
        }
        
        //Tuo atveju jei skelbejas buvo neveiklus ir tapo veiklus. Bet neveiklus buvo
        //uz saugomu datu, tada nebutu, kad parodyti ir visas formatavimas susigadina.
        //Todel pridedu nauja eilute iskart
        if(checkDates(this.listYearMonth).isEmpty()) {
            HBox newHBox = addRow();
            container.getChildren().add(newHBox);
            this.listHBox.add(newHBox);

            this.countRows = 0;
            for(HBox h: this.listHBox) {
                rewriteButton((Button)h.getChildren().get(7));
            }
        }
        
             
        //Sudedu veiksmus i mygtukus
        for(HBox h: this.listHBox) {
            rewriteButton((Button)h.getChildren().get(7));
        }
        
        scrollP.setContent(container);
        scrollP.setMaxHeight(147);
        scrollP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        HBox buttonsHBox = new HBox(10);
        
        Button buttonSave = new Button("Išsaugoti");
        buttonSave.setOnAction(e -> {
            
            if(this.flagInsert) {
                
                if(CheckData.checkRepeatReportWithAlertTable(getReportList())) {
                    Stage stage = (Stage) buttonSave.getScene().getWindow();
                    stage.close();
                } 

            } else {                
                saveDates();     
            }          
        });
        
        
        Button buttonCancel = new Button("Atšaukti");
        
        buttonCancel.setOnAction(e -> {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
        
        buttonsHBox.getChildren().addAll(buttonSave, buttonCancel);
        buttonsHBox.setPadding(new Insets(10, 0, 0, 0));
        
        mainLayout.getChildren().addAll(text, hBoxForInsertRow, hBoxForAnnotation, scrollP, buttonsHBox);
        
        return mainLayout;
    }
    
    public HBox showTable(FromToService numbers) {
        
        HBox layout = new HBox(10);

        String[] serviceList = {"Skelbėjas", "Pag. pionierius",
            "Reg. pionierius", "Spec. pionierius"};
        
        ArrayList<Integer> yList = this.date.getYears1();
        
        ChoiceBox yearTo = new ChoiceBox();
        yearTo.getItems().addAll(yList);
        yearTo.setValue(numbers.getToYear());
        yearTo.setPrefWidth(65);
        
        ChoiceBox monthTo = new ChoiceBox();
        
        //Cia meta klaida, kai neaktyvus, tampa akvytiu. Tarsi nera tos reiksmes.
        System.out.println("(Integer)yearTo.getValue():\n" +
                (Integer)yearTo.getValue());
        
        System.out.println("date.getMonthByYearFrom1((Integer)yearTo.getValue()):\n" +
                date.getMonthByYearFrom1((Integer)yearTo.getValue()));
        
        //Stai ka meta:
//        (Integer)yearTo.getValue():
//        2012
//        date.getMonthByYearFrom1((Integer)yearTo.getValue()):
//        null
                
        monthTo.getItems().addAll(date.getMonthByYearFrom1((Integer)yearTo.getValue()));
        monthTo.setValue(numbers.getToMonth());
        monthTo.setPrefWidth(45);
                
        ChoiceBox service = new ChoiceBox();
        service.getItems().addAll(serviceList);
        
        service.setValue(numberToService(numbers.getService()));
        
        ChoiceBox yearFrom = new ChoiceBox();
        yearFrom.getItems().addAll(yList);
        yearFrom.setValue(numbers.getFromYear());
        yearFrom.setPrefWidth(65);
        
        ChoiceBox monthFrom = new ChoiceBox();

        monthFrom.getItems().addAll(date.getMonthByYearFrom1((Integer)yearFrom.getValue()));
        monthFrom.setValue(numbers.getFromMonth());
        monthFrom.setPrefWidth(45);
        
        ChangeListener yearToListener = new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {              
                
                ArrayList<Integer> newYearList = date.getYearTobyYearFrom1(getValueChoiceBoxInteger(yearFrom));
 
                if(Objects.equals((date.getYearTobyYearFrom1(getValueChoiceBoxInteger(yearFrom)).get((Integer)t1)), getValueChoiceBoxInteger(yearFrom))) {
                    
                    ArrayList<Integer> list = date.getMonthByYearFrom1(newYearList.get((Integer)t1));
                    
                    int choosenMonth = getValueChoiceBoxInteger(monthFrom);

                    int numTo = getValueChoiceBoxInteger(monthTo);

                    monthTo.getItems().clear();
                    monthTo.getItems().addAll(date.getMonthTobyYearFromAndMonthFrom2(list, choosenMonth));

                    if(choosenMonth < numTo) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    } 
                    
                } else {
          
                    int toMonthValue = getValueChoiceBoxInteger(monthTo); 
                
                    monthTo.getItems().clear();
                    
                    ArrayList<Integer> monthList1 = date.getMonthByYearFrom1(newYearList.get((Integer)t1));
                    
                    monthTo.getItems().addAll(monthList1);

                    if(monthList1.get(0) <= toMonthValue &&
                            monthList1.get(monthList1.size()- 1) >= toMonthValue) {                    
                        monthTo.setValue(toMonthValue);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }
                }   
            }
        };
        
        yearTo.getSelectionModel().selectedIndexProperty().addListener(yearToListener);
        
        
        ChangeListener monthFromListener = new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                if(Objects.equals(getValueChoiceBoxInteger(yearFrom), getValueChoiceBoxInteger(yearTo))) {
                    
                    ArrayList<Integer> list = date.getMonthByYearFrom1(getValueChoiceBoxInteger(yearFrom));
                    
                    int choosenMonth = list.get((Integer) t1);
                    
                    int numTo = getValueChoiceBoxInteger(monthTo);
                    
                    monthTo.getItems().clear();
                    monthTo.getItems().addAll(date.getMonthTobyYearFromAndMonthFrom2(list, choosenMonth));
                    
                    if(choosenMonth < numTo) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }  
                }
            }
        };
        
        monthFrom.getSelectionModel().selectedIndexProperty().addListener(monthFromListener);
        
             
        yearFrom.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                monthFrom.getSelectionModel().selectedIndexProperty().removeListener(monthFromListener);
                yearTo.getSelectionModel().selectedIndexProperty().removeListener(yearToListener);
                
                int fromMonthValue = getValueChoiceBoxInteger(monthFrom);
                
                monthFrom.getItems().clear();
                ArrayList<Integer> monthList = date.getMonthByYearFrom1(yList.get((Integer)t1));
                monthFrom.getItems().addAll(monthList);
                
                if(monthList.get(0) <= fromMonthValue &&
                        monthList.get(monthList.size()- 1) >= fromMonthValue) {                    
                    monthFrom.setValue(fromMonthValue);
                } else {
                    monthFrom.getSelectionModel().selectFirst();
                }
                           
                //Sutvrako metuIki sarasa

                int yearSecond = getValueChoiceBoxInteger(yearTo);
                int choosenYear = yList.get((Integer) t1);

                yearTo.getItems().clear();
        
                yearTo.getItems().addAll(date.getYearTobyYearFrom1(yList.get((Integer)t1)));

                if(choosenYear < yearSecond) {               
                    yearTo.setValue(yearSecond);
                } else {
                    yearTo.getSelectionModel().selectFirst();
                }
                
                if(Objects.equals((yList.get((Integer)t1)), getValueChoiceBoxInteger(yearTo))) {
                                   
                    int choosenMonth = getValueChoiceBoxInteger(monthFrom);

                    int numTo = getValueChoiceBoxInteger(monthTo);

                    monthTo.getItems().clear();
                    ArrayList<Integer> newMontList = date.getMonthTobyYearFromAndMonthFrom1(yList.get((Integer)t1), choosenMonth);
                    monthTo.getItems().addAll(newMontList);

                    if(choosenMonth < numTo && newMontList.contains(numTo)) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    } 
                    
                } else {
                    
                    int toMonthValue = getValueChoiceBoxInteger(monthTo); 
                
                    monthTo.getItems().clear();
                    ArrayList<Integer> monthList1 = date.getMonthByYearFrom1(getValueChoiceBoxInteger(yearTo));
                    
                    monthTo.getItems().addAll(monthList1);

                    if(monthList1.get(0) <= toMonthValue &&
                            monthList1.get(monthList1.size()- 1) >= toMonthValue) {                    
                        monthTo.setValue(toMonthValue);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }
                }
                     
                monthFrom.getSelectionModel().selectedIndexProperty().addListener(monthFromListener);
                yearTo.getSelectionModel().selectedIndexProperty().addListener(yearToListener);
                
            }
        });
          
        CheckBox checkHistory = new CheckBox();
        checkHistory.setPadding(new Insets(3, 0, 0, 30));

        if(numbers.getHistory() == 1) {
            checkHistory.setSelected(true);
        }
        
        
        //Kazkodel pakeitus ChoiceBox to ComboBox kodas nebeveikia. Nazinau kodel...
        ComboBox choiceGroup = new ComboBox();
        choiceGroup.getItems().addAll(this.groupList);
        
        if(checkHistory.isSelected()) {
            choiceGroup.setDisable(false);
            if(numbers.getGroup() == null) {
                
                if(this.flagInsert) {
                    choiceGroup.setValue(group);
                } else {
                    choiceGroup.setValue("");
                }
                
            } else {
                choiceGroup.setValue(numbers.getGroup());
            } 
        } else {
            choiceGroup.setValue("-");
            choiceGroup.setDisable(true);
        }
        
        checkHistory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                if(newValue) {
                    choiceGroup.setDisable(false);                   
                    if(numbers.getGroup() == null) {
                        choiceGroup.setValue(group);
                    } else {
                        choiceGroup.setValue(numbers.getGroup());
                    } 
                } else {
                    choiceGroup.setValue("-");
                    choiceGroup.setDisable(true);
                }
            }
        });
         
        Button deleteButton = new Button("Pašalinti eilutę");

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 10));
        HBox.setMargin(yearTo, new Insets(0, 0, 0, 20));
        HBox.setMargin(choiceGroup, new Insets(0, 0, 0, 30));
        
        layout.getChildren().addAll(service, yearFrom, monthFrom, yearTo, monthTo, checkHistory, choiceGroup, deleteButton);
        
        return layout;
    }
    
    
    public HBox addRow() {
        
        HBox layout = new HBox(10);

        String[] serviceList = {"Skelbėjas", "Pag. pionierius",
            "Reg. pionierius", "Spec. pionierius"};
        
        ArrayList<Integer> yList = this.date.getYears1();
        
        ChoiceBox yearTo = new ChoiceBox();
        yearTo.getItems().addAll(yList);
        yearTo.getSelectionModel().selectFirst();
        yearTo.setPrefWidth(65);
        
        ChoiceBox monthTo = new ChoiceBox();
        monthTo.getItems().addAll(date.getMonthByYearFrom1((Integer)yearTo.getValue()));
        monthTo.getSelectionModel().selectFirst();
        monthTo.setPrefWidth(45);
                
        ChoiceBox service = new ChoiceBox();
        service.getItems().addAll(serviceList);
        
        service.getSelectionModel().selectFirst();
        
        ChoiceBox yearFrom = new ChoiceBox();
        yearFrom.getItems().addAll(yList);
        yearFrom.getSelectionModel().selectFirst();
        yearFrom.setPrefWidth(65);
        
        ChoiceBox monthFrom = new ChoiceBox();

        monthFrom.getItems().addAll(date.getMonthByYearFrom1((Integer)yearFrom.getValue()));
        monthFrom.getSelectionModel().selectFirst();
        monthFrom.setPrefWidth(45);
        
        ChangeListener yearToListener = new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {              
                
                ArrayList<Integer> newYearList = date.getYearTobyYearFrom1(getValueChoiceBoxInteger(yearFrom));
 
                if(Objects.equals((date.getYearTobyYearFrom1(getValueChoiceBoxInteger(yearFrom)).get((Integer)t1)), getValueChoiceBoxInteger(yearFrom))) {
                    
                    ArrayList<Integer> list = date.getMonthByYearFrom1(newYearList.get((Integer)t1));
                    
                    int choosenMonth = getValueChoiceBoxInteger(monthFrom);

                    int numTo = getValueChoiceBoxInteger(monthTo);

                    monthTo.getItems().clear();
                    monthTo.getItems().addAll(date.getMonthTobyYearFromAndMonthFrom2(list, choosenMonth));

                    if(choosenMonth < numTo) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    } 
                    
                } else {
          
                    int toMonthValue = getValueChoiceBoxInteger(monthTo); 
                
                    monthTo.getItems().clear();
                    
                    ArrayList<Integer> monthList1 = date.getMonthByYearFrom1(newYearList.get((Integer)t1));
                    
                    monthTo.getItems().addAll(monthList1);

                    if(monthList1.get(0) <= toMonthValue &&
                            monthList1.get(monthList1.size()- 1) >= toMonthValue) {                    
                        monthTo.setValue(toMonthValue);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }
                }   
            }
        };
        
        yearTo.getSelectionModel().selectedIndexProperty().addListener(yearToListener);
        
        
        ChangeListener monthFromListener = new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                if(Objects.equals(getValueChoiceBoxInteger(yearFrom), getValueChoiceBoxInteger(yearTo))) {
                    
                    ArrayList<Integer> list = date.getMonthByYearFrom1(getValueChoiceBoxInteger(yearFrom));
                    
                    int choosenMonth = list.get((Integer) t1);
                    
                    int numTo = getValueChoiceBoxInteger(monthTo);
                    
                    monthTo.getItems().clear();
                    monthTo.getItems().addAll(date.getMonthTobyYearFromAndMonthFrom2(list, choosenMonth));
                    
                    if(choosenMonth < numTo) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }  
                }
            }
        };
        
        monthFrom.getSelectionModel().selectedIndexProperty().addListener(monthFromListener);
        
             
        yearFrom.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {   
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                monthFrom.getSelectionModel().selectedIndexProperty().removeListener(monthFromListener);
                yearTo.getSelectionModel().selectedIndexProperty().removeListener(yearToListener);
                
                int fromMonthValue = getValueChoiceBoxInteger(monthFrom);
                
                monthFrom.getItems().clear();
                ArrayList<Integer> monthList = date.getMonthByYearFrom1(yList.get((Integer)t1));
                monthFrom.getItems().addAll(monthList);
                
                if(monthList.get(0) <= fromMonthValue &&
                        monthList.get(monthList.size()- 1) >= fromMonthValue) {                    
                    monthFrom.setValue(fromMonthValue);
                } else {
                    monthFrom.getSelectionModel().selectFirst();
                }
                           
                //Sutvrako metuIki sarasa

                int yearSecond = getValueChoiceBoxInteger(yearTo);
                int choosenYear = yList.get((Integer) t1);

                yearTo.getItems().clear();
        
                yearTo.getItems().addAll(date.getYearTobyYearFrom1(yList.get((Integer)t1)));

                if(choosenYear < yearSecond) {               
                    yearTo.setValue(yearSecond);
                } else {
                    yearTo.getSelectionModel().selectFirst();
                }
                
                if(Objects.equals((yList.get((Integer)t1)), getValueChoiceBoxInteger(yearTo))) {
                                   
                    int choosenMonth = getValueChoiceBoxInteger(monthFrom);

                    int numTo = getValueChoiceBoxInteger(monthTo);

                    monthTo.getItems().clear();
                    ArrayList<Integer> newMontList = date.getMonthTobyYearFromAndMonthFrom1(yList.get((Integer)t1), choosenMonth);
                    monthTo.getItems().addAll(newMontList);

                    if(choosenMonth < numTo && newMontList.contains(numTo)) {
                        monthTo.setValue(numTo);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    } 
                    
                } else {
                    
                    int toMonthValue = getValueChoiceBoxInteger(monthTo); 
                
                    monthTo.getItems().clear();
                    ArrayList<Integer> monthList1 = date.getMonthByYearFrom1(getValueChoiceBoxInteger(yearTo));
                    
                    monthTo.getItems().addAll(monthList1);

                    if(monthList1.get(0) <= toMonthValue &&
                            monthList1.get(monthList1.size()- 1) >= toMonthValue) {                    
                        monthTo.setValue(toMonthValue);
                    } else {
                        monthTo.getSelectionModel().selectFirst();
                    }
                }
                     
                monthFrom.getSelectionModel().selectedIndexProperty().addListener(monthFromListener);
                yearTo.getSelectionModel().selectedIndexProperty().addListener(yearToListener);
                
            }
        });
          
        CheckBox checkHistory = new CheckBox();
        checkHistory.setPadding(new Insets(3, 0, 0, 30));
        
        ComboBox choiceGroup = new ComboBox();
        choiceGroup.getItems().addAll(this.groupList);
        
        if(checkHistory.isSelected()) {
            choiceGroup.setDisable(false);                   
            choiceGroup.setValue(group);
        } else {
            choiceGroup.setValue("-");
            choiceGroup.setDisable(true);
        }
        
        checkHistory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                if(newValue) {
                    choiceGroup.setDisable(false);                   
                    choiceGroup.setValue(group);
                } else {
                    choiceGroup.setValue("-");
                    choiceGroup.setDisable(true);
                }
            }
        });
        
        Button deleteButton = new Button("Pašalinti eilutę");

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 10));
        HBox.setMargin(yearTo, new Insets(0, 0, 0, 20));
        HBox.setMargin(choiceGroup, new Insets(0, 0, 0, 30));
        
        layout.getChildren().addAll(service, yearFrom, monthFrom, yearTo, monthTo, checkHistory, choiceGroup, deleteButton);
      
        return layout;
    }
    
    private void rewriteButton(Button b) {
        //Atnaujina Button reiksmes. Kaskart istrynus irasa keciasi indeksas ArraList'e.
        //Jei reiksmes nebutu atnaujintos, Button gali duoti signala istrinti tai, ko
        //jau nera masyve
        
        int i = this.countRows;

        b.setOnAction(e -> {

            this.listHBox.remove(i);
            this.container.getChildren().remove(i);

            this.countRows = 0;
            for(HBox h: this.listHBox) {
                rewriteButton((Button)h.getChildren().get(7));
                
            }
            
        });
        
        this.countRows++;
        
    }
    
    
    private String numberToService(int num) {
        if(num == 1) {
            return "Skelbėjas";
        }
        if(num == 2) {
            return "Pag. pionierius";
        }
        if(num == 3) {
            return "Reg. pionierius";
        }
        if(num == 4) {
            return "Spec. pionierius";
        }
        return "Error";
    }
    
    private Integer serviceToNumber(String ser) {
        if(ser.equals("Skelbėjas")) {
            return 1;
        }
        if(ser.equals("Pag. pionierius")) {
            return 2;
        }
        if(ser.equals("Reg. pionierius")) {
            return 3;
        }
        if(ser.equals("Spec. pionierius")) {
            return 4;
        }
        return -1;
    }
    

    
    public ArrayList<Report> getReportList() {
        
        ArrayList<Report> fullReport = new ArrayList<>();
        
        for(HBox hbox: this.listHBox) {

            //Sukuria list of Report be Id
            fullReport.addAll(YearsMonthsServiceConverter.listForWriting2(serviceToNumber(
                    getValueChoiceBoxString((ChoiceBox)hbox.getChildren().get(0))),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(1)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(2)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(3)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(4)),
                    getValueCheckBoxHistory((CheckBox)hbox.getChildren().get(5)),
                    getValueComboBoxInteger((ComboBox)hbox.getChildren().get(6))));
        }
        
             
        return fullReport;
        
    }
    
    public ArrayList<Report> getReportListWithId() {
        
        
        ArrayList<Report> fullReport = new ArrayList<>();
        
        for(HBox hbox: this.listHBox) {

                //Sukuria list of Report su Id. Galbut truputi keistas budas gauti ta Id...
                
                fullReport.addAll(YearsMonthsServiceConverter.listForWritingWithIdNew(
                        this.listReport.get(0).getPubId(),
                        serviceToNumber(getValueChoiceBoxString((ChoiceBox)hbox.getChildren().get(0))),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(1)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(2)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(3)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(4)), 
                        getValueCheckBoxHistory((CheckBox)hbox.getChildren().get(5)),
                        getValueComboBoxInteger((ComboBox)hbox.getChildren().get(6))));
            }
        
        return fullReport;
    }
    
    public void saveDates() {
        
        ArrayList<Report> list = getReportListWithId();
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        ReportDao repOb = new ReportDao(link);
        
        if(CheckData.checkRepeatReportWithAlertTable(list)) {

            repOb.updateReport(list);
            
            //Naudoju this.container tik tam, kad gauciau stage
            Stage stage = (Stage) this.container.getScene().getWindow();
            stage.close();
        }
        
    }
    
    public Integer getValueChoiceBoxInteger(ChoiceBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        return (Integer) node.getValue();
    }
    
    public String getValueComboBoxInteger(ComboBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        return (String) node.getValue();
    }
    
    public String getValueChoiceBoxString(ChoiceBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        return (String) node.getValue();
    }
    
     public Integer getValueCheckBoxHistory(CheckBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        if(node.isSelected()) {
            return 1;
        }
        return 0;
    }
     
    public ArrayList<FromToService> checkDates(ArrayList<FromToService> list) {
        
        //Checkinu, jei neveiklus tampa veikliu. Ir jei neveiklus buvo seniai, tada nieko nerodo.
        //O jei neveikliu tapo saugoma laikotarpi, tada parodys tiek, kiek buvo veiklus ta saugoma laikotarpi.
        //Ankstesniu datu nerodo

        int minYear = this.date.yearAndMonthList().get(0).get(0);
        int minMonth = this.date.yearAndMonthList().get(0).get(1);

        ArrayList<FromToService> newList = new ArrayList<>();
        
        for(FromToService l : list) {
            
            if((l.getToYear() == minYear && l.getToMonth() >= minMonth) || l.getToYear() > minYear) {
                
                
                
                if((l.getFromYear() == minYear && l.getFromMonth() >= minMonth) || l.getFromYear() > minYear) {
                    
                    FromToService temp = new FromToService(l.getFromYear(),
                            l.getFromMonth(), l.getToYear(), l.getToMonth(),
                            l.getHistory(), l.getService(), l.getGroup());
                   
                    newList.add(temp);
                } else {
                    FromToService temp = new FromToService(minYear, minMonth,
                            l.getToYear(), l.getToMonth(),
                            l.getHistory(), l.getService(), l.getGroup());

                    newList.add(temp);
                }  
            }
        }
        
        return newList;
    }
}
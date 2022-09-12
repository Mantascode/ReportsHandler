

package com.mycompany.ataskaituapp.dataWindow.userInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.dataWindow.domain.CheckData;
import com.mycompany.ataskaituapp.dataWindow.domain.YearsMonthsServiceConverter;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettingsDao;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class HistoryUnactiveUI{
    

    private ArrayList<ArrayList<Integer>> listYearMonth;
    private ArrayList<Report> listReport;
    private int countRows;
    private FlowPane container;
    private ArrayList<HBox> listHBox;
    private boolean flagInsert;
    private YearsAndMonthsSettings date;
    private String text;
    
    public HistoryUnactiveUI(ArrayList<Report> list, boolean flagInsert, String text) {
        this.listReport = list;
        this.container = new FlowPane();
        this.countRows = 0;
        this.listHBox = new ArrayList<>();
        this.flagInsert = flagInsert;
        this.text = text;
    }
    
    public void getData(ArrayList<Report> list) {
        this.listReport = list;
    }
    
    public VBox getView() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        this.date = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
   
//        System.out.println(this.date.list().toString());

        //To reikia tik todel kas Converter veikia su ArrayList<ArrayList<Integer>>,
        //o ne su ArrayList<Report>. Reiketu perrasyti converteri
        ArrayList<ArrayList<Integer>> fullList = new ArrayList<>();
        for(Report rep: this.listReport) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(rep.getYear());
            temp.add(rep.getMonth());
            temp.add(rep.getService());
            temp.add(rep.getHistory());
            fullList.add(temp);
         }
       
        this.listYearMonth = YearsMonthsServiceConverter.listForShowing1(fullList);
        System.out.println(this.listYearMonth.toString());
        
        
        //Sukuria hashMap. Jei naujas irasas, sukuria univesrsalu HashMap, jei senas irasas,
        //sukuria individualu HashMap, pagal pateikta informacija
        if(flagInsert) {
            this.date.createHashMap();
        } else {
            this.date.createHashMapUn(this.listYearMonth.get(this.listYearMonth.size()-1).get(2),
                    this.listYearMonth.get(this.listYearMonth.size()-1).get(3));
        }
        
        
        VBox mainLayout = new VBox();
        mainLayout.setPadding(new Insets(40, 40, 40, 40));
        
        Label text = new Label("Įveskite skelbejo teokratinę istoriją.");
        text.setPadding(new Insets(0, 10, 10, 0));
        
        Label text2 = new Label("Pridėti naują eilutę: ");
        text2.setPadding(new Insets(0, 0, 0, 0));
        
        this.container.setVgap(5);
        
        
        //papildomas kodas del neaktyvaus kselbejo nustatymu
        
        Label text3 = new Label(this.text);
        Label text4 = new Label("Metai:");
        text4.setPadding(new Insets(0, 0, 0, 20));
        ComboBox lastYear = new ComboBox();
        lastYear.setPrefWidth(68);
        
        Label text5 = new Label("Mėnuo:");
        ComboBox lastMonth = new ComboBox();
        lastMonth.setPrefWidth(55);
        
        
        if(this.flagInsert) {
            lastYear.getItems().addAll(date.lastYearListUn());
            lastYear.getSelectionModel().selectFirst();
            
            lastMonth.getItems().addAll(date.lastMonthListbyYearUn((Integer) lastYear.getSelectionModel().getSelectedItem()));
            lastMonth.getSelectionModel().selectLast();
        } else {
            
            lastYear.getItems().addAll(date.lastYearListUn());
            lastYear.setValue(this.listYearMonth.get(this.listYearMonth.size()-1).get(2));
            
            lastMonth.getItems().addAll(date.lastMonthListbyYearUn((Integer) lastYear.getSelectionModel().getSelectedItem()));
            lastMonth.setValue(this.listYearMonth.get(this.listYearMonth.size()-1).get(3));
 
        }
        
        ChangeListener lastMonthListener = new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                
                date.createHashMapUn((Integer) lastYear.getSelectionModel().getSelectedItem(),
                        (Integer) lastMonth.getSelectionModel().getSelectedItem());
                
                System.out.println(date.getHashMap().toString());
                
               
                container.getChildren().clear();
                listHBox.clear();
                
                //pridedu nauja eilute. Nokopijavau nuo pridejimo mygtuko
                HBox newHBox = addRow();
                container.getChildren().add(newHBox);
                listHBox.add(newHBox);

                countRows = 0;
                for(HBox h: listHBox) {
                    rewriteButton((Button)h.getChildren().get(6));
                }
            }
        };
        
        ChangeListener lastYearListener = new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                
                lastMonth.getSelectionModel().selectedIndexProperty().removeListener(lastMonthListener);
                
//                System.out.println((Integer) lastYear.getSelectionModel().getSelectedItem());
                
                int numTo = (Integer) lastMonth.getSelectionModel().getSelectedItem();
                
                lastMonth.getItems().clear();
                ArrayList<Integer> temp = date.lastMonthListbyYearUn(date.lastYearListUn().get(newValue));
                lastMonth.getItems().addAll(temp);
                
                if(temp.contains(numTo)) {
                    lastMonth.setValue(numTo);
                } else {
                    lastMonth.getSelectionModel().selectFirst();
                }
                
                date.createHashMapUn((Integer) lastYear.getSelectionModel().getSelectedItem(),
                        (Integer) lastMonth.getSelectionModel().getSelectedItem());
                
                System.out.println(date.getHashMap().toString());
                
                lastMonth.getSelectionModel().selectedIndexProperty().addListener(lastMonthListener);
                
                container.getChildren().clear();
                listHBox.clear();
                
                HBox newHBox = addRow();
                container.getChildren().add(newHBox);
                listHBox.add(newHBox);

                countRows = 0;
                for(HBox h: listHBox) {
                    rewriteButton((Button)h.getChildren().get(6));
                }                       
            }
        };
        
        lastYear.getSelectionModel().selectedIndexProperty().addListener(lastYearListener);
        lastMonth.getSelectionModel().selectedIndexProperty().addListener(lastMonthListener);
        
        //Baigiasi kodas susijes su neaktyviu skelbeju

        

        
        HBox hBoxForLastDate = new HBox(5);
        hBoxForLastDate.setPadding(new Insets(5, 0, 5, 0));
        hBoxForLastDate.getChildren().addAll(text3, text4, lastYear, text5, lastMonth); 
        
        Button add = new Button("Pridėti");
        add.setOnAction(e -> {
            
            HBox newHBox = addRow();
            container.getChildren().add(newHBox);
            this.listHBox.add(newHBox);
            
            System.out.println(this.listHBox.size());

            this.countRows = 0;
            for(HBox h: this.listHBox) {
                rewriteButton((Button)h.getChildren().get(6));
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

        HBox hBoxForAnnotation = new HBox();
        hBoxForAnnotation.setAlignment(Pos.BOTTOM_LEFT);
        hBoxForAnnotation.setPadding(new Insets(10, 0, 0, 0));
        hBoxForAnnotation.getChildren().addAll(textService, textFrom, textTo, textAddToCongregation);
        
        
        HBox hBoxForInsertRow = new HBox();
        hBoxForInsertRow.setPadding(new Insets(5, 0, 5, 0));
        hBoxForInsertRow.getChildren().addAll(text2, add);
        
        ScrollPane scrollP = new ScrollPane();
        

        for(ArrayList<Integer> dates: this.listYearMonth) {
            System.out.println("dates: " + dates);
            HBox newHBox1 = showTable(dates);
            container.getChildren().add(newHBox1);
            this.listHBox.add(newHBox1);
        }
        
        //Sudedu veiksmus i mygtukus
        for(HBox h: this.listHBox) {
            rewriteButton((Button)h.getChildren().get(6));
        }
        
        scrollP.setContent(container);
        
        HBox buttonsHBox = new HBox(10);
        
        Button buttonSave = new Button("Išsaugoti");
        buttonSave.setOnAction(e -> {
            
            if(this.flagInsert) {
                
                if(CheckData.checkRepeatReportWithAlertTable(getReportList())) {
                    Stage stage = (Stage) buttonSave.getScene().getWindow();
                    stage.close();
                }
//                else {
//                    AlertBoxUI.alertBoxForReport();
//                }
                

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
        
        
        mainLayout.getChildren().addAll(text, hBoxForLastDate, hBoxForInsertRow, hBoxForAnnotation, scrollP, buttonsHBox);
        
        return mainLayout;
    }
    
    public HBox showTable(ArrayList<Integer> numbers) {
        
        
        HBox layout = new HBox(10);

        String[] serviceList = {"Skelbėjas", "Pag. pionierius",
            "Reg. pionierius", "Spec. pionierius"};
        
        ArrayList<Integer> yList = this.date.getYears1();
        
        ChoiceBox yearTo = new ChoiceBox();
        yearTo.getItems().addAll(yList);
        yearTo.setValue(numbers.get(2));
        yearTo.setPrefWidth(65);
                
        ChoiceBox monthTo = new ChoiceBox();
        monthTo.getItems().addAll(date.getMonthByYearFrom1((Integer)yearTo.getValue()));
        monthTo.setValue(numbers.get(3));
        monthTo.setPrefWidth(45);
                
        ChoiceBox service = new ChoiceBox();
        service.getItems().addAll(serviceList);
        
        service.setValue(numberToService(numbers.get(4)));
        
        ChoiceBox yearFrom = new ChoiceBox();
        yearFrom.getItems().addAll(yList);
        yearFrom.setValue(numbers.get(0));
        yearFrom.setPrefWidth(65);
        
        ChoiceBox monthFrom = new ChoiceBox();

        monthFrom.getItems().addAll(date.getMonthByYearFrom1((Integer)yearFrom.getValue()));
        monthFrom.setValue(numbers.get(1));
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
        if(numbers.get(5) == 1) {
            checkHistory.setSelected(true);
        }
        
        Button deleteButton = new Button("Pašalinti eilutę");

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 30));
        HBox.setMargin(yearTo, new Insets(0, 0, 0, 20));
        
        layout.getChildren().addAll(service, yearFrom, monthFrom, yearTo, monthTo, checkHistory, deleteButton);
      
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
        
        Button deleteButton = new Button("Pašalinti eilutę");

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 30));
        HBox.setMargin(yearTo, new Insets(0, 0, 0, 20));
        
        layout.getChildren().addAll(service, yearFrom, monthFrom, yearTo, monthTo, checkHistory, deleteButton);
      
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
                rewriteButton((Button)h.getChildren().get(6));
                
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
            fullReport.addAll(YearsMonthsServiceConverter.listForWriting1(serviceToNumber(
                    getValueChoiceBoxString((ChoiceBox)hbox.getChildren().get(0))),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(1)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(2)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(3)),
                    getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(4)),
                    getValueCheckBoxHistory((CheckBox)hbox.getChildren().get(5))));
        }
        

             
        return fullReport;
        
    }
    
    public ArrayList<Report> getReportListWithId() {
        
        
        ArrayList<Report> fullReport = new ArrayList<>();
        
        for(HBox hbox: this.listHBox) {

                //Sukuria list of Report su Id. Galbut truputi keistas budas gauti ta Id...
                
                fullReport.addAll(YearsMonthsServiceConverter.listForWritingWithId(
                        this.listReport.get(0).getPubId(),
                        serviceToNumber(getValueChoiceBoxString((ChoiceBox)hbox.getChildren().get(0))),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(1)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(2)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(3)),
                        getValueChoiceBoxInteger((ChoiceBox)hbox.getChildren().get(4)),
                        getValueCheckBoxHistory((CheckBox)hbox.getChildren().get(5))));
            }
        
        return fullReport;
    }
    
    public void saveDates() {
        
        ArrayList<Report> list = getReportListWithId();
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        ReportDao repOb = new ReportDao(link);
        
        
        if(CheckData.checkRepeatReportWithAlertTable(list)) {
            repOb.update(list);
            repOb.insertAfterUpadate(list);
            repOb.removeIfNotInIdList(list);
            
            Stage stage = (Stage) this.container.getScene().getWindow();
            stage.close();
        }
//        else {
//            AlertBoxUI.alertBoxForReport();
//        }
        
    }
    
    public Integer getValueChoiceBoxInteger(ChoiceBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        return (Integer) node.getValue();
    }
    
    public Integer getValueComboBoxInteger(ComboBox node) {
        //Kazkodel neveikia CheckBox.getValue. Taigi, si funkcija padeda gauti reiksme is ChoiceBox
        return (Integer) node.getValue();
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
     

    
     
}

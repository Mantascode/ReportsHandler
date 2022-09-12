
package com.mycompany.ataskaituapp.dataWindow.userInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.dataWindow.domain.CheckData;
import com.mycompany.ataskaituapp.dataWindow.domain.ListViewSettings;
import com.mycompany.ataskaituapp.dataWindow.domain.Publisher;
import com.mycompany.ataskaituapp.dataWindow.domain.PublisherDao;
import com.mycompany.ataskaituapp.settings.domain.ServiceGroupDao;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettingsDao;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;


public class DataUI {
    
    final BorderPane mainLayout;
    final ListView listView;
    final TextField nameText;
    final TextField lastNameText;
    final CheckBox checkBoxWoman;
    final CheckBox checkBoxMan;
    final DatePicker birthDate;
    final DatePicker baptismDate;
    final CheckBox checkIfBaptized;
    final CheckBox checkBoxAnnointed;
    final CheckBox checkBoxOtherSheep;
    final CheckBox serviceBoxPioneer;
    final CheckBox serviceBoxOtherElder;
    final CheckBox serviceBoxOtherMinister;
    final CheckBox serviceBoxOtherSpec;
    final CheckBox serviceBoxOtherAuxaliary;
    final ChoiceBox choiceGroup;
    final CheckBox inactiveCheck;
    final CheckBox historyCheck;
    final CheckBox showDeletedCheck;
    final Label groupInfoLabel;
    
    private PublisherDao data;
    private ServiceGroupDao groups;
    private HistoryUINew pubHistory;
    private HistoryUnactiveUINew pubHistoryUnactive;
    private ListViewSettings listViewOb;

    private boolean flagInsert;
    private boolean flagHistory;
    private int choosenId;
  
    public DataUI(BorderPane window) {
        this.mainLayout = window;
        this.listView = new ListView();
        this.nameText = new TextField();
        this.lastNameText = new TextField();
        this.checkBoxWoman = new CheckBox("Moteris");
        this.checkBoxMan = new CheckBox("Vyras");
        this.birthDate = new DatePicker();
        this.baptismDate = new DatePicker();
        this.checkIfBaptized = new CheckBox("Nekrikštytas skelbėjas");
        this.checkBoxAnnointed = new CheckBox("Pateptasis");
        this.checkBoxOtherSheep = new CheckBox("Kita avis");
        this.serviceBoxPioneer = new CheckBox("Pionierius");
        this.serviceBoxOtherElder = new CheckBox("Vyresnysis");
        this.serviceBoxOtherMinister = new CheckBox("Patarnautojas");
        this.serviceBoxOtherSpec  = new CheckBox("Spec. pionierius/misionierius");
        this.serviceBoxOtherAuxaliary = new CheckBox("Nuolatinis pag. pionierius");
        this.choiceGroup = new ChoiceBox();
        this.inactiveCheck = new CheckBox("Neveiklus");
        this.historyCheck = new CheckBox();
        this.flagInsert = true;
        this.flagHistory = false;
        this.groupInfoLabel = new Label();
        this.showDeletedCheck = new CheckBox("Rodyti ne visiškai ištrintus įrašus");  
    }
    
    public void start() {
        
        //Uzkraua duomenis is duomenu bazes i PublisherDao
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        this.data = new PublisherDao(link);
        this.groups = new ServiceGroupDao(link);
        this.listViewOb = new ListViewSettings(this.listView, this.data.list());
        
        for(Publisher p: data.list()) {
            System.out.println(p.toString());
        }
        
        BorderPane mainDataLayout = new BorderPane();
        mainDataLayout.setPadding(new Insets(40, 40, 40, 40));
        
        //Kaires puses stulpelis, su sarasu
        VBox leftSide = new VBox();
        Label label1 = new Label("Skelbėjų sąrašas:");
        this.showDeletedCheck.setPadding(new Insets(10, 0, 0, 15));
        this.showDeletedCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    listViewOb.updateList(data.listOfDeletedPub());
                    clearAllFields();
                    inactiveCheck.setDisable(true);
                } else {
                    listViewOb.updateList(data.list());
                    clearAllFields();
                    inactiveCheck.setDisable(false);
                }
            }
        });
        
        
        leftSide.getChildren().addAll(label1, this.listView, this.showDeletedCheck);
        
        mainDataLayout.setLeft(leftSide);
        
        //Duomenu suvedimo langas
        GridPane centerLayout = new GridPane();
        centerLayout.setPadding(new Insets(20, 20, 20, 20));
        centerLayout.setVgap(10);
        centerLayout.setHgap(10);
        
        Label nameLabel = new Label("Vardas:");
        centerLayout.add(nameLabel, 0, 0);
        centerLayout.add(this.nameText, 1, 0);
        
        Label lastNameLabel = new Label("Pavardė:"); 
        centerLayout.add(lastNameLabel, 0, 1);
        centerLayout.add(this.lastNameText, 1, 1);
        
        Label sexLabel = new Label("Lytis:");
        HBox sexBox = new HBox(10);
        sexBox.getChildren().addAll(this.checkBoxWoman, this.checkBoxMan);
        centerLayout.add(sexLabel, 0, 2);
        centerLayout.add(sexBox, 1, 2);
        
        Label birthLabel = new Label("Gimimo data:");
        HBox birthBox = new HBox(10);
        Label ageLabel = new Label("Amžius:");
        Label showAgeLabel = new Label("");
        dataFormater(this.birthDate);
        this.birthDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 40, LocalDate.now().getDayOfYear()));
        birthBox.getChildren().addAll(this.birthDate, ageLabel, showAgeLabel);
        centerLayout.add(birthLabel, 0, 3);
        centerLayout.add(birthBox, 1, 3);
        
        Label baptismLabel = new Label("Krikšto data:");
        HBox baptismBox = new HBox(10);
        
        Label baptismAgeLabel = new Label("Metai po krikšto:");
        Label showBaptismAgeLabel = new Label("");
        dataFormater(this.baptismDate);
        this.baptismDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 20, LocalDate.now().getDayOfYear()));
        baptismBox.getChildren().addAll(this.baptismDate, baptismAgeLabel, showBaptismAgeLabel);
        centerLayout.add(baptismLabel, 0, 4);
        centerLayout.add(baptismBox, 1, 4);
         
        centerLayout.add(this.checkIfBaptized, 1, 5);
        
        Label hopeLabel = new Label("Viltis:");
        HBox hopeBox = new HBox(10);    
        hopeBox.getChildren().addAll(this.checkBoxOtherSheep, this.checkBoxAnnointed);
        centerLayout.add(hopeLabel, 0, 6);
        centerLayout.add(hopeBox, 1, 6);
        
        Label serviceLabel = new Label("Tarnystė:");
        FlowPane serviceFlow = new FlowPane();
        serviceFlow.setHgap(10);
        
        serviceFlow.getChildren().addAll(this.serviceBoxPioneer, this.serviceBoxOtherElder, this.serviceBoxOtherMinister,
                this.serviceBoxOtherSpec, this.serviceBoxOtherAuxaliary);
        centerLayout.add(serviceLabel, 0, 7);
        centerLayout.add(serviceFlow, 1, 7);
        
        Label groupLabel = new Label("Grupė");
        HBox groupBox = new HBox(10);
        this.groupInfoLabel.setTextFill(Color.color(0.8, 0, 0));
        
        if (this.groups.list().isEmpty()){          
            this.groupInfoLabel.setText("Grupės neįrašytos");
        } else {
            this.choiceGroup.getItems().addAll(this.groups.list());
            this.choiceGroup.getSelectionModel().selectFirst();
            this.groupInfoLabel.setText("");
        }
        groupBox.getChildren().addAll(this.choiceGroup, this.groupInfoLabel);
        centerLayout.add(groupLabel, 0, 8);
        centerLayout.add(groupBox, 1, 8);
        
        Label inactiveLabel = new Label("Kita informacija:");
        
        centerLayout.add(inactiveLabel, 0, 9);
        centerLayout.add(this.inactiveCheck, 1, 9);
        
        Label historyLabel = new Label("Teokratinė istorija:");
        Button historybutton = new Button("Nustatytmai");
        historybutton.setOnAction(e -> {
            this.flagHistory = true;
            if(this.showDeletedCheck.isSelected()) {
                String textForDeleted = "Kada skelbėjo duomenys buvo ištrinti?";      
                goToHistoryUnactive(textForDeleted);   
            } else if (this.inactiveCheck.isSelected()) {
                String textForUnActive = "Kada skelbėjas tapo neaktyvus?";
                goToHistoryUnactive(textForUnActive);
            } else {
                goToHistory();
            }
            
        });
        centerLayout.add(historyLabel, 0, 10);
        centerLayout.add(historybutton, 1, 10);
        
        Label showInCongregaionLabel = new Label("Įtraukti į\nbendruomenės\nataskaitą");
        
        centerLayout.add(showInCongregaionLabel, 0, 11);
        centerLayout.add(this.historyCheck, 1, 11);
        
        HBox buttonBox = new HBox(10);
        Button newData = new Button("Naujas įrašas");
//        newData.setOnAction(e -> clearFields());
        newData.setOnAction(e -> clearAllFields());

        Button saveData = new Button("Išsaugoti įrašą");
        saveData.setOnAction(e -> saveOrUpdateData());
        Button deleteData = new Button("Ištrinti įrašą");
        deleteData.setOnAction(e -> deleteData());
        buttonBox.getChildren().addAll(newData, saveData, deleteData);
        centerLayout.add(buttonBox, 1, 12);
        
               
        mainDataLayout.setCenter(centerLayout);
        
        this.mainLayout.setCenter(mainDataLayout);
        
        //uzpildomas listView ir akvytbuojama funkcija jei kazkas yra pasirinkta        
        fillListView();
        
        this.addListenersToCheckBoxes();
    }
    
    private void clearFields() {
        //Si koda naudoju, kai vaikstau per listview
        this.nameText.clear();
        this.lastNameText.clear();
        this.checkBoxWoman.setSelected(false);
        this.checkBoxMan.setSelected(false);
        this.birthDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 40, LocalDate.now().getDayOfYear()));
        this.baptismDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 20, LocalDate.now().getDayOfYear()));
        this.checkIfBaptized.setSelected(false);
        this.checkBoxAnnointed.setSelected(false);
        this.checkBoxOtherSheep.setSelected(false);
        this.serviceBoxPioneer.setSelected(false);
        this.serviceBoxOtherElder.setSelected(false);
        this.serviceBoxOtherMinister.setSelected(false);
        this.serviceBoxOtherSpec.setSelected(false);
        this.serviceBoxOtherAuxaliary.setSelected(false);
        
        if (this.groups.list().isEmpty()){
            this.groupInfoLabel.setText("Grupės neįrašytos");
        } else {
            this.choiceGroup.getSelectionModel().selectFirst();
            this.groupInfoLabel.setText("");
        }       
        
        //Klausimas del apatines eilutes. Gal jos nereikia, tada gal ir nereikia apatines funkcijos...
        this.choiceGroup.getSelectionModel().selectFirst();
        this.inactiveCheck.setSelected(false);
        this.historyCheck.setSelected(false);
//        this.listView.getSelectionModel().clearSelection();
        //Nesu tikras del apatine eilutes. Atrodo, kad nereikia
//        this.flagInsert = true;
        this.flagHistory = false;
    }
    
    private void clearAllFields() {
        //Si koda naudoju, kai paspaudziu "naujas irasas"
        this.nameText.clear();
        this.lastNameText.clear();
        this.checkBoxWoman.setSelected(false);
        this.checkBoxMan.setSelected(false);
        this.birthDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 40, LocalDate.now().getDayOfYear()));
        this.baptismDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 20, LocalDate.now().getDayOfYear()));
        //Cia dar reikia pataisyti su birthdate ir baptismdate. eiluteje, kur galima irasyti datas, nera default reiksmes
        //lieka paskutine reiksme, kokia naudojau.
//        this.birthDate.getEditor().setV
        this.checkIfBaptized.setSelected(false);
        this.checkBoxAnnointed.setSelected(false);
        this.checkBoxOtherSheep.setSelected(false);
        this.serviceBoxPioneer.setSelected(false);
        this.serviceBoxOtherElder.setSelected(false);
        this.serviceBoxOtherMinister.setSelected(false);
        this.serviceBoxOtherSpec.setSelected(false);
        this.serviceBoxOtherAuxaliary.setSelected(false);
        
        if (this.groups.list().isEmpty()){
            this.groupInfoLabel.setText("Grupės neįrašytos");
        } else {
            this.choiceGroup.getSelectionModel().selectFirst();
            this.groupInfoLabel.setText("");
        }       
        
        this.choiceGroup.getSelectionModel().selectFirst();
        this.inactiveCheck.setSelected(false);
        this.historyCheck.setSelected(false);
        this.listView.getSelectionModel().clearSelection();
        
        this.flagInsert = true;
        this.flagHistory = false;
    }
    
    private void saveOrUpdateData() {
        System.out.println(this.nameText.getText());
        System.out.println(this.lastNameText.getText());
        System.out.println(this.checkBoxWoman.isSelected());
        System.out.println(this.checkBoxMan.isSelected());
        System.out.println(this.birthDate.getValue());
        System.out.println(this.baptismDate.getValue());
        System.out.println(this.checkBoxAnnointed.isSelected());
        System.out.println(this.checkBoxOtherSheep.isSelected());
        System.out.println(this.serviceBoxPioneer.isSelected());
        System.out.println(this.serviceBoxOtherElder.isSelected());
        System.out.println(this.serviceBoxOtherMinister.isSelected());
        System.out.println(this.serviceBoxOtherSpec.isSelected());
        System.out.println(this.serviceBoxOtherAuxaliary.isSelected());
        System.out.println(this.choiceGroup.getValue());
        System.out.println(this.inactiveCheck.isSelected());
        
        if(CheckData.checkPublisherData(this.nameText.getText(), this.lastNameText.getText())) {
             
            if(this.flagInsert) {
            
                this.data.insert(new Publisher(cleanString(this.nameText.getText()),
                    cleanString(this.lastNameText.getText()),
                    insertSexNumber(this.checkBoxWoman),
                    this.birthDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    (this.baptismDate.getValue() != null ? this.baptismDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null),
                    insertAnnointedNumber(this.checkBoxAnnointed),
                    insertElderNumber(this.serviceBoxOtherElder),
                    insertMinisterialNumber(this.serviceBoxOtherMinister),
                    insertSErviceNumber(this.serviceBoxOtherSpec, this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary),
                    String.valueOf(this.choiceGroup.getValue()),
                    this.insertActiveNumber(this.inactiveCheck)));

                if(this.inactiveCheck.isSelected()) {

                    if(this.flagHistory) {

                        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
                        ReportDao insertReports = new ReportDao(link);


                        if(pubHistoryUnactive.getReportList().isEmpty()) {
                            this.createReport();
                        } else {

                            if(CheckData.checkRepeatReportWithoutAlertTable(pubHistoryUnactive.getReportList())) {
                                insertReports.insertWithoutIdNew(pubHistoryUnactive.getReportList());
                            } else {
                                this.createReport();
                            }
                        }

                    } else {
                        this.createReport();
                    }

                } else {

                    if(this.flagHistory) {
                        //Jei buvo ieita i istorijos nustatymus, tada naudoja si koda

                        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
                        ReportDao insertReports = new ReportDao(link);


                        if(pubHistory.getReportList().isEmpty() || pubHistory == null) {
                            this.createReport();
                        } else {

                            if(CheckData.checkRepeatReportWithoutAlertTable(pubHistory.getReportList())) {
                                insertReports.insertWithoutIdNew(pubHistory.getReportList());
                            } else {
                                this.createReport();
                            }
                        }

                    } else {
                        //Jei nebuvo ieita i istorijos nustatymus, tada naudoja si koda
                        this.createReport();
                    }
                }


            } else {


                this.data.update(this.choosenId, new Publisher(cleanString(this.nameText.getText()),
                    cleanString(this.lastNameText.getText()),
                    insertSexNumber(this.checkBoxWoman),
                    this.birthDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    (this.baptismDate.getValue() != null ? this.baptismDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null),
                    insertAnnointedNumber(this.checkBoxAnnointed),
                    insertElderNumber(this.serviceBoxOtherElder),
                    insertMinisterialNumber(this.serviceBoxOtherMinister),
                    insertSErviceNumber(this.serviceBoxOtherSpec, this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary),
                    String.valueOf(this.choiceGroup.getValue()),
                    this.insertActiveNumber(this.inactiveCheck)));

            }
        
            clearAllFields();

            if(this.showDeletedCheck.isSelected()) {
                this.listViewOb.updateList(data.listOfDeletedPub());
            } else {
                this.listViewOb.updateList(data.list());
            }
            
            this.flagInsert = true;
            this.flagHistory = false;
            
        }
        
    }
    
    private void createReport() {
        //Sukuria naujai sukurto iraso ataskaita ataskaitu lenteleje
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
//        ArrayList<ArrayList<Integer>> list = new YearsAndMonthsSettingsDao(link).list();
        
        YearsAndMonthsSettings object = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
        object.createHashMap();
        ArrayList<ArrayList<Integer>> list = object.yearAndMonthList();
        
        
        ArrayList<Report> reportList = new ArrayList<>();
        
        
        for(ArrayList<Integer> a: list) {
//            reportList.add(new Report(a.get(0), a.get(1), insertSErviceNumber(this.serviceBoxOtherSpec, this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary), insertHistoryNumber(this.historyCheck)));

            reportList.add(new Report(a.get(0), a.get(1), insertSErviceNumber(this.serviceBoxOtherSpec,
                    this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary), insertHistoryNumber(this.historyCheck),
                    String.valueOf(this.choiceGroup.getValue())));
        }
        
        ReportDao insertReports = new ReportDao(link);
        
        System.out.println("reportList -> " + reportList.toString());
        
//        insertReports.insertWithoutId(reportList);
        insertReports.insertWithoutIdNew(reportList);
        
      
    }
    
    public void goToHistory() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        
        YearsAndMonthsSettings object = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
        object.createHashMap();
        ArrayList<ArrayList<Integer>> list = object.yearAndMonthList();
        System.out.println(list.toString());
        
        ArrayList<Report> reportList = new ArrayList<>();
        
        
        
        if(this.flagInsert) {
            
            for(ArrayList<Integer> a: list) {
                reportList.add(new Report(a.get(0), a.get(1), insertSErviceNumber(this.serviceBoxOtherSpec,
                        this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary), insertHistoryNumber(this.historyCheck),
                        String.valueOf(this.choiceGroup.getValue())));
            }
            
            //Bandu HistoryUINEW, todel apatine eilute uzkomentuota
//            pubHistory = new HistoryUI(reportList, this.flagInsert);
//            System.out.println(String.valueOf(this.choiceGroup.getValue()));
            pubHistory = new HistoryUINew(reportList, this.flagInsert, String.valueOf(this.choiceGroup.getValue()));
        } else {
            ReportDao reportOb = new ReportDao(link);
            System.out.println(choosenId);
//            reportList = reportOb.getShortReportById(choosenId);
            reportList = reportOb.getShortReportByIdNew(choosenId);
             
            
            //Bandu HistoryUINEW, todel apatine eilute uzkomentuota
//            pubHistory = new HistoryUI(reportList, this.flagInsert);
//            System.out.println(String.valueOf(this.choiceGroup.getValue()));
            pubHistory = new HistoryUINew(reportList, this.flagInsert, String.valueOf(this.choiceGroup.getValue()));
            
        }
        
        VBox view = pubHistory.getView();
        Stage stage = new Stage();
        
        stage.sizeToScene();
        stage.setResizable(false);

        stage.setMinWidth(800);
        stage.setMinHeight(450);
        Scene scene = new Scene(view);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void goToHistoryUnactive(String text) {
        
        //Kazkodel nlogai isveda neveikliojo grupes. Su veikliaisiai viskas yra gerai
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        
        YearsAndMonthsSettings object = new YearsAndMonthsSettingsDao(link).getYearsAndMonthobject();
        object.createHashMap();
        ArrayList<ArrayList<Integer>> list = object.yearAndMonthList();
        System.out.println(list.toString());
        
        ArrayList<Report> reportList = new ArrayList<>();
        
        
        
        
        if(this.flagInsert) {
            
            for(ArrayList<Integer> a: list) {
                reportList.add(new Report(a.get(0), a.get(1), insertSErviceNumber(this.serviceBoxOtherSpec,
                this.serviceBoxPioneer, this.serviceBoxOtherAuxaliary), insertHistoryNumber(this.historyCheck),
                String.valueOf(this.choiceGroup.getValue())));
            }
            
//            pubHistoryUnactive = new HistoryUnactiveUI(reportList, this.flagInsert, text);
            pubHistoryUnactive = new HistoryUnactiveUINew(reportList, this.flagInsert, text, String.valueOf(this.choiceGroup.getValue()));
        } else {
            ReportDao reportOb = new ReportDao(link);
            System.out.println(choosenId);
            reportList = reportOb.getShortReportByIdNew(choosenId);
            
//            pubHistoryUnactive = new HistoryUnactiveUI(reportList, this.flagInsert, text);
            pubHistoryUnactive = new HistoryUnactiveUINew(reportList, this.flagInsert, text, String.valueOf(this.choiceGroup.getValue()));
        }
        
        VBox view = pubHistoryUnactive.getView();
        Stage stage = new Stage();
        stage.sizeToScene();
        stage.setResizable(false);

        stage.setMinWidth(800);
        stage.setMinHeight(500);
        Scene scene = new Scene(view);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    
    private int insertSexNumber(CheckBox w) {
        
        //Tikrina ar tie lytimi pazymeta "moteris" 
        if(w.isSelected()) {          
            return 0;
        }
        
        return 1;
    }
    
    private int insertAnnointedNumber(CheckBox a) {
        //Tikrina ar ties viltimi pazymeta "pateptasis"       
        if(a.isSelected()) {
            return 1;
        }
        return 0;
    }
    
    private int insertElderNumber(CheckBox a) {
        //Tikrina ar ties pareigos pazymeta "vyresnysis"       
        if(a.isSelected()) {
            return 1;
        }
        
        return 0;
    } 
    
    private int insertMinisterialNumber(CheckBox a) {
        //Tikrina ar ties pareigos pazymeta "patarnautojas"       
        if(a.isSelected()) {
            return 1;
        }
        
        return 0;
    }
    
    private int insertHistoryNumber(CheckBox a) {
        //Tikrina ar ties pareigos pazymeta "patarnautojas"       
        if(a.isSelected()) {
            return 1;
        }
        
        return 0;
    }
    
    private int insertSErviceNumber(CheckBox spec, CheckBox reg, CheckBox aux) {
        //Iraso tarnystes numeri
        //4 - spec/mision.
        //3 - reg. pion.
        //2 - pag. pion.
        //1 - seklb.
        if(spec.isSelected()) {
            return 4;
        }
        
        if(reg.isSelected()) {
            return 3;
        }
        
        if(aux.isSelected()) {
            return 2;
        }
        
        return 1;
    }
    
    private int insertActiveNumber(CheckBox in) {
        
        if(in.isSelected()) {
            return 0;
        }
        
        return 1;
    } 
    
    private void deleteData() {
        
        Publisher pub = (Publisher) listView.getSelectionModel().getSelectedItem();
        
        if(pub != null) {
            
            if(this.showDeletedCheck.isSelected()) {
                
                DeleteDeletedPublisherUI deleteOb = new DeleteDeletedPublisherUI(pub);
        
                VBox view = deleteOb.getView();
                Stage stage = new Stage();
                stage.setResizable(false);
                Scene scene = new Scene(view, 600, 300);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();


                //Tas kodas reikalingas, kad būtų atnaujinti įrašiai listView lange
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {


                        if(showDeletedCheck.isSelected()) {
                            listViewOb.updateList(data.listOfDeletedPub());
                        } else {
                            listViewOb.updateList(data.list());
                        }

                        clearAllFields();
                    }
                }); 
            }
                
                
            else {
                
                DeletePublisherUI deleteOb = new DeletePublisherUI(pub);
        
                VBox view = deleteOb.getView();
                Stage stage = new Stage();
                stage.setResizable(false);
                Scene scene = new Scene(view, 600, 450);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();


                //Tas kodas reikalingas, kad būtų atnaujinti įrašiai listView lange
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {

                        if(showDeletedCheck.isSelected()) {
                            listViewOb.updateList(data.listOfDeletedPub());
                        } else {
                            listViewOb.updateList(data.list());
                        }

                        clearAllFields();
                    }
                }); 
            }
            
        } else {
            AlertBoxUI.createWindow("Nepasirinktas skelbėjas", 350, 200);
            
        } 
        
    }
    
    private void dataFormater(DatePicker d) {
        //Pakeicia datos formata
        
        String pattern = "yyyy-MM-dd";

        d.setPromptText(pattern.toLowerCase());

        d.setConverter(new StringConverter<LocalDate>() {
             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

             @Override 
             public String toString(LocalDate date) {
                 if (date != null) {
                     return dateFormatter.format(date);
                 } else {
                     return "";
                 }
             }

             @Override 
             public LocalDate fromString(String string) {
                 if (string != null && !string.isEmpty()) {
                     return LocalDate.parse(string, dateFormatter);
                 } else {
                     return null;
                 }
             }
         });
    }
    
    private void fillListView() {
        //uzpildo sarasa su skelbejais
        this.listView.getItems().addAll(this.data.list());
        
        this.listView.setCellFactory(lv -> new ListCell<Publisher>() {
            @Override
            public void updateItem(Publisher pub, boolean empty) {
                super.updateItem(pub, empty);
                setText(empty ? null : pub.getKey());
            }
        });
        
        
        
        //Reaguoja jei kazkas sarase yra pazymimas
        this.listView.setOnMouseClicked(new EventHandler<MouseEvent>() {            
            
            @Override
            public void handle(MouseEvent event) {
                
                //pries pasirenkant nauja skelbeja, isvalomi laukai
                clearFields();
                
                flagInsert = false;               
                
                Publisher pub = (Publisher) listView.getSelectionModel().getSelectedItem();
                
                //pub gali buti null kai pazymeta yra tuscia eilute
                if(pub != null) {
                    choosenId = pub.getId();

                    nameText.setText(pub.getName());
                    lastNameText.setText(pub.getLastName());
                    checkFalse(checkBoxWoman, pub.getSex());
                    checkTrue(checkBoxMan, pub.getSex());
                    birthDate.setValue(LocalDate.parse(pub.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    if(pub.getBaptiseDate() == null) {
                        checkIfBaptized.setSelected(true);
                    } else {
                        baptismDate.setValue(LocalDate.parse(pub.getBaptiseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                    checkTrue(checkBoxAnnointed, pub.getHope());
                    checkFalse(checkBoxOtherSheep, pub.getHope());
                    checkTrue(serviceBoxOtherElder, pub.getElder());
                    checkTrue(serviceBoxOtherMinister, pub.getMinisterial());
                    if(groups.list().isEmpty()) {
                        groupInfoLabel.setText("Grupės neįrašytos");
                    } else if(pub.getGroup() == null) {
                        groupInfoLabel.setText("Skelbėjas nepriskirtas prie grupės");
                    } else {
                        groupInfoLabel.setText("");
                    }
                    choiceGroup.setValue(pub.getGroup());
                    checkService(serviceBoxOtherAuxaliary,
                            serviceBoxPioneer, serviceBoxOtherSpec, pub.getService());
                    checkActive(inactiveCheck, pub.getActive());
                }
            }
        });     
    }
    
    private void checkFalse(CheckBox box, int a) {
        if(a == 1) {
            box.setSelected(false);
        } else {
            box.setSelected(true);
        }
    }
    
    private void checkTrue(CheckBox box, int a) {
        if(a == 1) {
            box.setSelected(true);
        } else {
            box.setSelected(false);
        }
    }
    
    private void checkService(CheckBox aux,
            CheckBox pion, CheckBox spec, int num) {
        
        if(num == 2) {
            aux.setSelected(true);
        }
        
        if(num == 3) {
            pion.setSelected(true);
        }
        
        if(num == 4) {
            spec.setSelected(true);
        }
    }
    
    private void checkActive(CheckBox in, int num) {
        
        if(num == 1) {
            in.setSelected(false);
        } else {
            in.setSelected(true);
        }
    }
    
    private void addListenersToCheckBoxes() {
        
        this.checkBoxWoman.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && checkBoxMan.isSelected()) {
                    checkBoxMan.setSelected(false);
                }
            }
        });
        
        this.checkBoxMan.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && checkBoxWoman.isSelected()) {
                    checkBoxWoman.setSelected(false);
                }
            }
        });
        
        this.checkBoxAnnointed.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && checkBoxOtherSheep.isSelected()) {
                    checkBoxOtherSheep.setSelected(false);
                }
            }
        });
        
        this.checkBoxOtherSheep.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && checkBoxAnnointed.isSelected()) {
                    checkBoxAnnointed.setSelected(false);
                }
            }
        });
        
        this.serviceBoxOtherElder.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && serviceBoxOtherMinister.isSelected()) {
                    serviceBoxOtherMinister.setSelected(false);
                }
            }
        });
        
        this.serviceBoxOtherMinister.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && serviceBoxOtherElder.isSelected()) {
                    serviceBoxOtherElder.setSelected(false);
                }
            }
        });
        
        this.serviceBoxPioneer.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && (serviceBoxOtherSpec.isSelected() || serviceBoxOtherAuxaliary.isSelected())) {
                    serviceBoxOtherSpec.setSelected(false);
                    serviceBoxOtherAuxaliary.setSelected(false);
                }
            }
        });
        
        this.serviceBoxOtherSpec.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && (serviceBoxPioneer.isSelected() || serviceBoxOtherAuxaliary.isSelected())) {
                    serviceBoxPioneer.setSelected(false);
                    serviceBoxOtherAuxaliary.setSelected(false);
                }
            }
        });
        
        this.serviceBoxOtherAuxaliary.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && (serviceBoxPioneer.isSelected() || serviceBoxOtherSpec.isSelected())) {
                    serviceBoxPioneer.setSelected(false);
                    serviceBoxOtherSpec.setSelected(false);
                }
            }
        });
        
        this.checkIfBaptized.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    baptismDate.getEditor().setDisable(true);
                    baptismDate.setDisable(true);
                    baptismDate.setValue(null);
                } else {
                    baptismDate.getEditor().setDisable(false);
                    baptismDate.setDisable(false);
                    baptismDate.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 20, LocalDate.now().getDayOfYear()));
                }
            }
        });

    }
    
    private String cleanString(String str) {
        
        String cleanString = "";
        
        str = str.trim().toLowerCase().replaceAll(" +", " ");
        String[] splitted = str.split(" ");
        
        if(splitted.length > 1) {
            for(int i=0; i < splitted.length; i++) {
                cleanString += splitted[i].substring(0, 1).toUpperCase() + splitted[i].substring(1);
                if(i < splitted.length - 1) {
                    cleanString += " ";
                }
            }
        } else {
            cleanString = str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        
        return cleanString;   
    }
    
}

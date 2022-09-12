
package com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.CountingSumAverage;
import com.mycompany.ataskaituapp.settings.domain.ServiceGroupDao;
import java.text.DecimalFormat;
import java.text.NumberFormat;
//import static com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface.CongregationReportUI.removeScrollBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class DataInTable {
    
    public ObservableList<Report> list;
    public ObservableSet<Report> editedSet;
    public ObservableSet<Report> editedSetMinutesBefore;
    public ObservableSet<Report> editedSetMinutesAfter;
    public int sumPublication;
    public VBox vBox;
    public double width;
    public Button buttonSave;
    
    public DataInTable(ObservableList<Report> list, ObservableSet<Report> editedList,
            ObservableSet<Report> editedSetMinutesBefore, ObservableSet<Report> editedSetMinutesAfter, Button button) {
        this.list = list;
        this.sumPublication = 0;
        this.vBox = new VBox();
        this.width = 150;
        this.editedSet = editedList;
        this.editedSetMinutesBefore = editedSetMinutesBefore;
        this.editedSetMinutesAfter = editedSetMinutesAfter;
        this.buttonSave = button;
        this.addListenerToButtonSave();
    }
    
    public ScrollPane showByGroup() {
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(850);
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        HashMap<Integer, String> groupsIdName = new ServiceGroupDao(link).hashMapList();
        
        HashMap<String, ArrayList<Report>> mapGroup = new HashMap<>();
        
        for(Integer group: groupsIdName.keySet()) {
            for(Report rep: this.list) {
                if(Objects.equals(rep.getGroupId(), group)) {
                    mapGroup.putIfAbsent(groupsIdName.get(group), new ArrayList<>());
                    
                    ArrayList<Report> added = mapGroup.get(groupsIdName.get(group));
                    added.add(rep);
                }
            }
        }
        
        //Dar gerai butu padaryti, kad pagal abecele surusiuoja grupes
        for(String groupName: mapGroup.keySet()) {

            ObservableList<Report> obList = FXCollections.observableArrayList();
            
            obList.addAll(mapGroup.get(groupName));
            
            Label pubLabel = new Label(groupName);
            pubLabel.setPadding(new Insets(15, 0, 10, 10));

            pubLabel.setStyle("-fx-font-weight: bold;");
            pubLabel.setFont(new Font("Arial", 15));

            vBox.getChildren().add(pubLabel);
            vBox.getChildren().add(this.getTable(obList));
        }
        
        scrollPane.setContent(vBox); 
        
    
        return scrollPane;
    }
  
    public ScrollPane showByService() {
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(850);
        
        ObservableList<Report> listPub = FXCollections.observableArrayList();
        ObservableList<Report> listAux = FXCollections.observableArrayList();
        ObservableList<Report> listPio = FXCollections.observableArrayList();
        ObservableList<Report> listSpec = FXCollections.observableArrayList();
        
        for(Report report: list) {
            if(report.getService() == 1) {
                listPub.add(report);
            }
            
            if(report.getService() == 2) {
                listAux.add(report);
            }
            
            if(report.getService() == 3) {
                listPio.add(report);
            }
            
            if(report.getService() == 4) {
                listSpec.add(report);
            }
        }
        
        if(!listPub.isEmpty()) {
            Label pubLabel = new Label("Skelbėjai");
            pubLabel.setPadding(new Insets(15, 0, 10, 10));

            pubLabel.setStyle("-fx-font-weight: bold;");
            pubLabel.setFont(new Font("Arial", 15));

            vBox.getChildren().add(pubLabel);
            vBox.getChildren().add(this.getTable(listPub));
        }
        
        if(!listAux.isEmpty()) {
            Label pubLabel = new Label("Pagalbiniai pionieriai");
            pubLabel.setPadding(new Insets(15, 0, 10, 10));

            pubLabel.setStyle("-fx-font-weight: bold;");
            pubLabel.setFont(new Font("Arial", 15));

            vBox.getChildren().add(pubLabel);
            vBox.getChildren().add(this.getTable(listAux));
        }
        
        if(!listPio.isEmpty()) {
            Label pubLabel = new Label("Reguliarieji pionieriai");
            pubLabel.setPadding(new Insets(15, 0, 10, 10));

            pubLabel.setStyle("-fx-font-weight: bold;");
            pubLabel.setFont(new Font("Arial", 15));

            vBox.getChildren().add(pubLabel);
            vBox.getChildren().add(this.getTable(listPio));
        }
        
        if(!listSpec.isEmpty()) {
            Label pubLabel = new Label("Specialieji pionieriai/misionieriai");
            pubLabel.setPadding(new Insets(15, 0, 10, 10));

            pubLabel.setStyle("-fx-font-weight: bold;");
            pubLabel.setFont(new Font("Arial", 15));

            vBox.getChildren().add(pubLabel);
            vBox.getChildren().add(this.getTable(listSpec));
        }
        
        scrollPane.setContent(vBox);   
        
        return scrollPane;
    }
    
    public ScrollPane showAll() {
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(850);
        Label pubLabel = new Label("Visi skelbėjai");
        pubLabel.setPadding(new Insets(15, 0, 10, 10));
        
        pubLabel.setStyle("-fx-font-weight: bold;");
        pubLabel.setFont(new Font("Arial", 15));
        
        vBox.getChildren().add(pubLabel);
        vBox.getChildren().add(this.getTable(this.list));
        
        scrollPane.setContent(vBox);
        
        return scrollPane;
    }
    
    private VBox getTable(ObservableList<Report> newList) {
        
        VBox vBoxTable = new VBox();
        
        TableView<Report> table = new TableView<>();
        table.setEditable(true);
//        removeScrollBar(table); //Nesu tikras ar to reikia


        
        TableColumn<Report, String> nameCol = new TableColumn("Pavardė, vardas");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameCol.setMinWidth(50);
        nameCol.setMaxWidth(200);
        nameCol.setPrefWidth(width);
        
        
        //Sis kodas pravers, kad neatisrastu betvarke apacioje, kur skiaciuojami vidurkiai,
        //kad tas apatinis tekstas irgi judetu pagal pakeitimus cia
        nameCol.widthProperty().addListener(new ChangeListener<Number> () {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                width = (Double) t1;
                vBoxTable.getChildren().remove(2);
                vBoxTable.getChildren().remove(1);
                vBoxTable.getChildren().add(getSum(newList));
                vBoxTable.getChildren().add(getAverage(newList));
            }
        });

        
        TableColumn<Report, Integer> publicationCol = new TableColumn("Leidiniai");
        publicationCol.setCellValueFactory(new PropertyValueFactory<>("publication"));
        publicationCol.setPrefWidth(60);
        publicationCol.setResizable(false);
        publicationCol.setStyle("-fx-alignment: CENTER;");   
                    
        
        publicationCol.setCellFactory(tc -> new TextFieldTableCell <Report, Integer>(new IntegerStringConverter()) {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value <= 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
        });
        

        publicationCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, Integer> t) {
                    ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setPublication(Integer.valueOf(t.getNewValue()));

                    vBoxTable.getChildren().remove(2);
                    vBoxTable.getChildren().remove(1);
                    vBoxTable.getChildren().add(getSum(newList));
                    vBoxTable.getChildren().add(getAverage(newList));
                    
                    editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                }
            }
        );
         

        TableColumn<Report, Integer> videoCol = new TableColumn("Vaizdo siužetai");
        videoCol.setCellValueFactory(new PropertyValueFactory<>("video"));
        videoCol.setPrefWidth(95);
        videoCol.setResizable(false);
        videoCol.setStyle("-fx-alignment: CENTER;");  
        
        videoCol.setCellFactory(tc -> new TextFieldTableCell <Report, Integer>(new IntegerStringConverter()) {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value <= 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
        });
        
        videoCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, Integer> t) {
                    ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setVideo(Integer.valueOf(t.getNewValue()));
                    
                    vBoxTable.getChildren().remove(2);
                    vBoxTable.getChildren().remove(1);
                    vBoxTable.getChildren().add(getSum(newList));
                    vBoxTable.getChildren().add(getAverage(newList));
                    
                    editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                }
            }
        );
        
        
        TableColumn<Report, Double> hoursCol = new TableColumn("Valandos");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hour"));
        hoursCol.setPrefWidth(80);
        hoursCol.setResizable(false);
        hoursCol.setStyle("-fx-alignment: CENTER;");   
               
        hoursCol.setCellFactory(tc -> new TextFieldTableCell <Report, Double>(new DoubleStringConverter()) {
            @Override
            public void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    
                    double value = item.doubleValue();

                    if(value >= 1)  {
                        setText(Integer.toString((int)value));
                    } else if(value == 0.25 || value == 0.5 || value == 0.75) {       
                        setText(Double.toString(value));
                    } else {
                        setText("");
                    }
                }
            }
        });
        
        hoursCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, Double>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, Double> t) {
                    
                    if((t.getOldValue() > 0 && t.getOldValue() < 1) && t.getNewValue() >= 1) {
                        System.out.println("ikeliama i editedSetMinutesBefore");
                        editedSetMinutesBefore.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    } 
                    
                    if(t.getNewValue() > 0 && t.getNewValue() < 1) {
                        System.out.println("ikeliame i editedSetMinutesAfter");
                        editedSetMinutesAfter.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    }
                    
                    if(checkHours(t.getNewValue())) {
                        ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setHour(Double.valueOf(t.getNewValue()));

                        vBoxTable.getChildren().remove(2);
                        vBoxTable.getChildren().remove(1);
                        vBoxTable.getChildren().add(getSum(newList));
                        vBoxTable.getChildren().add(getAverage(newList));
                        
                        editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));                     
                                       
                    } else {
                        table.refresh();
                    }
                    
                    
                         
                }
            }
        );
     
        
        TableColumn<Report, Integer> returnsCol = new TableColumn("Aplankymai");
        returnsCol.setCellValueFactory(new PropertyValueFactory<>("returnVisit"));
        returnsCol.setPrefWidth(80);
        returnsCol.setResizable(false);
        returnsCol.setStyle("-fx-alignment: CENTER;"); 
        
        returnsCol.setCellFactory(tc -> new TextFieldTableCell <Report, Integer>(new IntegerStringConverter()) {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value <= 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
        });
        
        returnsCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, Integer> t) {
                    ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setReturnVisit(Integer.valueOf(t.getNewValue()));
                    
                    vBoxTable.getChildren().remove(2);
                    vBoxTable.getChildren().remove(1);
                    vBoxTable.getChildren().add(getSum(newList));
                    vBoxTable.getChildren().add(getAverage(newList));
                    
                    editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                }
            }
        );
        
        
        TableColumn<Report, Integer> studyCol = new TableColumn("Biblijos studijos");
        studyCol.setCellValueFactory(new PropertyValueFactory<>("study"));
        studyCol.setPrefWidth(95);
        studyCol.setResizable(false);
        studyCol.setStyle("-fx-alignment: CENTER;"); 
        
        studyCol.setCellFactory(tc -> new TextFieldTableCell <Report, Integer>(new IntegerStringConverter()) {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value <= 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
        });
        
        studyCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, Integer> t) {
                    ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStudy(Integer.valueOf(t.getNewValue()));
                    
                    vBoxTable.getChildren().remove(2);
                    vBoxTable.getChildren().remove(1);
                    vBoxTable.getChildren().add(getSum(newList));
                    vBoxTable.getChildren().add(getAverage(newList));
                    
                    editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                }
            }
        );
        
        
        TableColumn<Report, String> notesCol = new TableColumn("Pastabos");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        notesCol.setCellFactory(TextFieldTableCell.forTableColumn());  
        notesCol.setPrefWidth(238);
        notesCol.setResizable(false);
        
        notesCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Report, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Report, String> t) {
                    ((Report) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setNote(t.getNewValue());
                    
                    editedSet.add((Report) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                }
            }
        );
        
        
        nameCol.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                        notesCol.setPrefWidth(800 - 2 - publicationCol.getWidth() - videoCol.getWidth() -
                                hoursCol.getWidth() - returnsCol.getWidth() - studyCol.getWidth() -
                                (Double) t1);
                }
        });
        
        //Sis kodas padeda pasalinti nulius, kai nebuna paspausta Enter
        table.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                table.refresh();
            }
        });
        

        
        table.setItems(newList);        
        table.getColumns().addAll(nameCol, publicationCol, videoCol, hoursCol, returnsCol, studyCol, notesCol);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.01)));
        table.minHeightProperty().bind(table.prefHeightProperty());
        table.maxHeightProperty().bind(table.prefHeightProperty());
        table.setPrefWidth(800);
        
        HBox hBox = new HBox();
        
        VBox rowNumberVBox = getRowNumbers(newList.size());
        rowNumberVBox.setPadding(new Insets(0, 2, 0, 0));
        
        hBox.getChildren().addAll(rowNumberVBox, table);
        hBox.setPadding(new Insets(0, 5, 0, 15));
        
        
        vBoxTable.getChildren().addAll(hBox, getSum(newList), getAverage(newList));
        
        table.getSortOrder().add(nameCol);
        

        return vBoxTable;
    }
    
    private GridPane getSum(ObservableList<Report> l) {
               
        CountingSumAverage sumOb = new CountingSumAverage(l);
        
        GridPane gridpane = new GridPane();
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(width + 30);
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(60);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(95);
        col3.setHalignment(HPos.CENTER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(80);
        col4.setHalignment(HPos.CENTER);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPrefWidth(80);
        col5.setHalignment(HPos.CENTER);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPrefWidth(95);
        col6.setHalignment(HPos.CENTER);
        
        gridpane.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6);
          
        Label text = new Label("Suma: ");
        text.setStyle("-fx-font-weight: bold;");
        text.setFont(new Font("Arial", 15));
        gridpane.add(text, 0, 0);
        
        Label sumPubLabel = new Label(Integer.toString(sumOb.sumPublication()));
        sumPubLabel.setStyle("-fx-font-weight: bold;");
        sumPubLabel.setFont(new Font("Arial", 15));
        gridpane.add(sumPubLabel, 1, 0);
        
        Label sumVideo = new Label(Integer.toString(sumOb.sumVideo()));
        sumVideo.setStyle("-fx-font-weight: bold;");
        sumVideo.setFont(new Font("Arial", 15));
        gridpane.add(sumVideo, 2, 0);
        
        Label sumHour = new Label(Double.toString(sumOb.sumHour()));
        sumHour.setStyle("-fx-font-weight: bold;");
        sumHour.setFont(new Font("Arial", 15));
        gridpane.add(sumHour, 3, 0);
        
        Label sumReturn = new Label(Integer.toString(sumOb.sumReturnVisit()));
        sumReturn.setStyle("-fx-font-weight: bold;");
        sumReturn.setFont(new Font("Arial", 15));
        gridpane.add(sumReturn, 4, 0);
        
        Label sumStudy = new Label(Integer.toString(sumOb.sumStudy()));
        sumStudy.setStyle("-fx-font-weight: bold;");
        sumStudy.setFont(new Font("Arial", 15));
        gridpane.add(sumStudy, 5, 0);
        
     
        return gridpane;
    }
    
    private GridPane getAverage(ObservableList<Report> l) {
            
        CountingSumAverage sumOb = new CountingSumAverage(l);
        
        GridPane gridpane = new GridPane();
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(width + 30);
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(60);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(95);
        col3.setHalignment(HPos.CENTER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(80);
        col4.setHalignment(HPos.CENTER);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPrefWidth(80);
        col5.setHalignment(HPos.CENTER);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPrefWidth(95);
        col6.setHalignment(HPos.CENTER);
        
        gridpane.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6);     
        
        Label text = new Label("Vidurkis: ");
        text.setStyle("-fx-font-weight: bold;");
        text.setFont(new Font("Arial", 15));
        gridpane.add(text, 0, 0);
        
        Label sumPubLabel = new Label(sumOb.avPublication());
        sumPubLabel.setStyle("-fx-font-weight: bold;");
        sumPubLabel.setFont(new Font("Arial", 15));
        gridpane.add(sumPubLabel, 1, 0);
        
        Label sumVideo = new Label(sumOb.avVideo());
        sumVideo.setStyle("-fx-font-weight: bold;");
        sumVideo.setFont(new Font("Arial", 15));
        gridpane.add(sumVideo, 2, 0);
        
        Label sumHour = new Label(sumOb.avHour());
        sumHour.setStyle("-fx-font-weight: bold;");
        sumHour.setFont(new Font("Arial", 15));
        gridpane.add(sumHour, 3, 0);
        
        Label sumReturn = new Label(sumOb.avReturnVisit());
        sumReturn.setStyle("-fx-font-weight: bold;");
        sumReturn.setFont(new Font("Arial", 15));
        gridpane.add(sumReturn, 4, 0);
        
        Label sumStudy = new Label(sumOb.avStudy());
        sumStudy.setStyle("-fx-font-weight: bold;");
        sumStudy.setFont(new Font("Arial", 15));
        gridpane.add(sumStudy, 5, 0);
        
        gridpane.setPadding(new Insets(0, 0, 15, 0));
     
        return gridpane;
    }
    
    private boolean checkHours(double h) {
        
        if(h >= 1 && Integer.valueOf(Double.toString(h).split("\\.")[1]) == 0) {
            return true;
        }
        
        if(h == 0.75) {
            return true;
        }
        
        if(h == 0.5) {
            return true;
        }
        
        if(h == 0.25) {
            return true;
        }
        
        return false;
    }
    
    private VBox getRowNumbers(int size) {
        
        VBox vBox = new VBox(8);
        
        for(int i = 0; i <= size; i++) {
            if(i == 0) {
                vBox.getChildren().add(new Label(""));
            } else {
                vBox.getChildren().add(new Label(Integer.toString(i)));
            }
            
        }
        
        vBox.setAlignment(Pos.BASELINE_RIGHT);
        
        return vBox;
    }
    
    private void addListenerToButtonSave() {
        
        //Jei masyvas tuscias, tada neleidzia nuspausti "Issaugoti", o jei measyvas
        //nerua tuscias, aktyvuoja isausogit mygtuka
        
        editedSet.addListener(new SetChangeListener() {
            @Override
            public void onChanged(SetChangeListener.Change change) {
                if(editedSet.isEmpty()) {
                    buttonSave.setDisable(true);
                } else {
                    buttonSave.setDisable(false);
                }
            }
        });
    }
    
    
}

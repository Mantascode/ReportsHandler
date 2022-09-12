
package com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.ReportDao;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.Sum;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.SumDao;
import com.mycompany.ataskaituapp.congregationReportwindow.domain.SummaryReport;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class ReportForBranchUI {
    
    private int year;
    private int month;
    
    public ReportForBranchUI(int year, int month) {
        this.year = year;
        this.month = month;
    }
    
 
    public VBox getView() {
        
        String link = "jdbc:sqlite:C:/Users/Manta/OneDrive/Kalbos/NetBeansProjects/AtaskaituApp/data.db";
        
        SumDao reports = new SumDao(link);
        ArrayList<Sum> list = reports.getReports(year, month);
        
        SummaryReport sumRep = new SummaryReport(list);
        sumRep.count();
        
        VBox vBox = new VBox(5);
        
        GridPane grid1 = new GridPane();
        grid1.add(new Label("Veiklūs skelbėjai:"), 0, 0);
        grid1.add(new Label("Savaitgalio sueigų lankomumo vidurkis:"), 0, 1);
        
        Label labelPub = new Label("Skelbėjai");
        
        GridPane grid2 = new GridPane();
        
        Label label1 = new Label("Ataskaitų skaičius:");
        GridPane.setHalignment(label1, HPos.RIGHT);
        grid2.add(label1, 0, 0);
        
        Label label2 = new Label("Spaudiniai ir el. failai:");
        GridPane.setHalignment(label2, HPos.RIGHT);
        grid2.add(label2, 0, 1);
        
        Label label3 = new Label("Vaizdo siužetai:");
        GridPane.setHalignment(label3, HPos.RIGHT);
        grid2.add(label3, 0, 2);
        
        Label label4 = new Label("Valandos:");
        GridPane.setHalignment(label4, HPos.RIGHT);
        grid2.add(label4, 0, 3);
        
        Label label5 = new Label("Pakartotiniai aplankymai:");
        GridPane.setHalignment(label5, HPos.RIGHT);
        grid2.add(label5, 0, 4);

        Label label6 = new Label("Biblijos studijos:");
        GridPane.setHalignment(label6, HPos.RIGHT);
        grid2.add(label6, 0, 5);
        
        grid2.add(new Label(String.valueOf(sumRep.getPubCount())), 1, 0);
        grid2.add(new Label(String.valueOf(sumRep.getPubPubl())), 1, 1);
        grid2.add(new Label(String.valueOf(sumRep.getPubVid())), 1, 2);
        grid2.add(new Label(String.valueOf(sumRep.getPubHour())), 1, 3);
        grid2.add(new Label(String.valueOf(sumRep.getPubRet())), 1, 4);
        grid2.add(new Label(String.valueOf(sumRep.getPubSt())), 1, 5);
        
        Label labelAux = new Label("Pagalbiniai pionieriai");
        
        GridPane grid3 = new GridPane();
        grid3.add(new Label("Ataskaitų skaičius:"), 0, 0);
        grid3.add(new Label("Spaudiniai ir el. failai:"), 0, 1);
        grid3.add(new Label("Vaizdo siužetai:"), 0, 2);
        grid3.add(new Label("Valandos:"), 0, 3);
        grid3.add(new Label("Pakartotiniai aplankymai:"), 0, 4);
        grid3.add(new Label("Biblijos studijos:"), 0, 5);
        
        grid3.add(new Label(String.valueOf(sumRep.getAuxCount())), 1, 0);
        grid3.add(new Label(String.valueOf(sumRep.getAuxPubl())), 1, 1);
        grid3.add(new Label(String.valueOf(sumRep.getAuxVid())), 1, 2);
        grid3.add(new Label(String.valueOf(sumRep.getAuxHour())), 1, 3);
        grid3.add(new Label(String.valueOf(sumRep.getAuxRet())), 1, 4);
        grid3.add(new Label(String.valueOf(sumRep.getAuxSt())), 1, 5);
        
        Label labelReg = new Label("Reguliarieji pionieriai");
        
        GridPane grid4 = new GridPane();
        grid4.add(new Label("Ataskaitų skaičius:"), 0, 0);
        grid4.add(new Label("Spaudiniai ir el. failai:"), 0, 1);
        grid4.add(new Label("Vaizdo siužetai:"), 0, 2);
        grid4.add(new Label("Valandos:"), 0, 3);
        grid4.add(new Label("Pakartotiniai aplankymai:"), 0, 4);
        grid4.add(new Label("Biblijos studijos:"), 0, 5);
        
        grid4.add(new Label(String.valueOf(sumRep.getPioCount())), 1, 0);
        grid4.add(new Label(String.valueOf(sumRep.getPioPubl())), 1, 1);
        grid4.add(new Label(String.valueOf(sumRep.getPioVid())), 1, 2);
        grid4.add(new Label(String.valueOf(sumRep.getPioHour())), 1, 3);
        grid4.add(new Label(String.valueOf(sumRep.getPioRet())), 1, 4);
        grid4.add(new Label(String.valueOf(sumRep.getPioSt())), 1, 5);
        
        vBox.getChildren().addAll(grid1, labelPub, grid2, labelAux, grid3, labelReg, grid4);
        

        return vBox;
    }
}

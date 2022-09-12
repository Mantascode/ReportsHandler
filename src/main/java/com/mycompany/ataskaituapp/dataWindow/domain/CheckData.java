
package com.mycompany.ataskaituapp.dataWindow.domain;

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import com.mycompany.ataskaituapp.dataWindow.userInterface.AlertBoxUI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CheckData {
    
    public static boolean checkRepeatReportWithAlertTable(ArrayList<Report> list) {
        
        //Patikra nepriekaistingiau viektu, jei atsizvelgtu i tai, kokia tarnyste buvo ivesta.
        //Tarkim, datos kartojasi, bet tuo ir vienu ir kitu atveju, buvo skelbejas. Gal nieko tokio
        //jei tokia klaida praeis. Svarbiausia nepraleisti irasu, kur tarnystes skirtingos, bet datos
        //to pacios. Jei labai netingesiu, galima ta sutvarkyti
        
        Set<String> uniqueValues = new HashSet();
        
        for(Report rep: list) {

            String dates = rep.getYear() + "." + rep.getMonth();
            
            if(uniqueValues.contains(dates)) {
                
                AlertBoxUI.createWindow("Įvesti laikotarpiai negali pasikartoti", 350, 200);
                return false;
                
            } else {
                uniqueValues.add(dates);
            } 
        }
        return true;       
    }
    
    public static boolean checkRepeatReportWithoutAlertTable(ArrayList<Report> list) {
        
        //Patikra nepriekaistingiau viektu, jei atsizvelgtu i tai, kokia tarnyste buvo ivesta.
        //Tarkim, datos kartojasi, bet tuo ir vienu ir kitu atveju, buvo skelbejas. Gal nieko tokio
        //jei tokia klaida praeis. Svarbiausia nepraleisti irasu, kur tarnystes skirtingos, bet datos
        //to pacios. Jei labai netingesiu, galima ta sutvarkyti
        
        Set<String> uniqueValues = new HashSet();
        
        for(Report rep: list) {

            String dates = rep.getYear() + "." + rep.getMonth();
            
            if(uniqueValues.contains(dates)) {
                
                return false;
                
            } else {
                uniqueValues.add(dates);
            } 
        }
        return true;       
    }
    
    public static boolean checkPublisherData(String name, String lastName) {
        
        //deja negaliu naudoti Publisher pub cia, reikes atskirai kiekviena elementa
        //imesti, kuri noriu tikrinti
        
        String finalError = "";
        
        String nameErorr = "Įveskite vardą\n";
        String lastNameErorr = "Įveskite pavardę\n";
        
        if(name.trim().isEmpty()) {
            finalError += nameErorr;
        }
        
        if(lastName.trim().isEmpty()) {
            finalError += lastNameErorr;
        }
        

        if(finalError.isEmpty()) {
            return true;
        } else {
            AlertBoxUI.createWindow(finalError, 350, 250);    
            return false; 
        }         
    }
    

}

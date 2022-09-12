
package com.mycompany.ataskaituapp.homePage.domain;

import java.time.LocalDate;
import java.util.ArrayList;


public class AddNewMonths {
    
    private AddNewMonthsDao obDao;
    
    public AddNewMonths(AddNewMonthsDao obDao) {
        this.obDao = obDao;
    }
    
    
    public void checkLastConnetcion() {
        
        String lastDate = obDao.getLastDates();
      
        if(!lastDate.equals(getCurrentDate())) {
            obDao.writeNewDates(getDatesNeedToWrite(lastDate, getCurrentDate()), getCurrentDate());
        }
    }
    
    private String getCurrentDate() {
        
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        String date = year + "." + month;
        
        return date;  
    }
    
    private ArrayList<ArrayList<Integer>> getDatesNeedToWrite(String oldDate, String currenDate) {
        
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        
        
        String[] oldD = oldDate.split("\\.");
        int oldYear = Integer.valueOf(oldD[0]);
        int oldMonth = Integer.valueOf(oldD[1]);
        
        String[] currentD = currenDate.split("\\.");
        int currentYear = Integer.valueOf(currentD[0]);
        int currentMonth = Integer.valueOf(currentD[1]);
        
        while(oldYear <= currentYear) {
            
            if(oldYear < currentYear) { 
                
                if(oldMonth == 12) {
                    oldMonth = 1;
                    oldYear++;
                } else {
                   oldMonth++; 
                }
                
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(oldYear);
                temp.add(oldMonth);
                
                list.add(temp);
                       
            } else {
                
                while(oldMonth < currentMonth) {  
                    oldMonth++;
                    
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(oldYear);
                    temp.add(oldMonth);

                    list.add(temp);
                    
                }
                break;
            }
        }
        
        return list;
    }
    
    
}


package com.mycompany.ataskaituapp.settings.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

//Klase, kuri sukuria metu ir menesiu sarasus. Juos galima naudoti ChoiceBox

public class YearsAndMonthsSettings {
    
    private HashMap<Integer, ArrayList<Integer>> hashList;
    private int num;
    
    public YearsAndMonthsSettings(int num) {
        this.num = num;
        this.hashList = new HashMap<>();
    }
    
    private int startingYear(int year, int month) {
        
        int startingYear;
        
        if (month > 8) {
            startingYear = year - num;
        } else {
            startingYear = year - num - 1;
        }
        
        return startingYear;
    }
    
    private int finalYear(int year, int month) {
        
        int finalYear;
        
        if (month > 8) {
            finalYear = year + 1;
        } else {
            finalYear = year;
        }
        
        return finalYear;
    }
    
    private ArrayList<Integer> yearsList(int startingYear, int finalYear) {
        
        ArrayList<Integer> years = new ArrayList<>();
        
        while(startingYear <= finalYear) {
            years.add(startingYear);
            startingYear++;
        }
        
        return years;
    }
    
    public ArrayList<ArrayList<Integer>> list() {
        
        //Sarasas metu ir menesiu
        
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        
        int startingYear = startingYear(currentYear(), currentMonth());
        int finalYear = finalYear(currentYear(), currentMonth());
        
        ArrayList<Integer> monthsList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        ArrayList<Integer> monthsListStart = new ArrayList<>(Arrays.asList(9, 10, 11, 12));
        ArrayList<Integer> monthsListFinal = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        
        ArrayList<Integer> years = yearsList(startingYear, finalYear);
        
        for(int i = 0; i < years.size(); i++) {
            
            if (i == 0) {
                for(int j = 0; j < monthsListStart.size(); j++) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(years.get(i));
                    temp.add(monthsListStart.get(j));
                    list.add(temp);
                }
                
            } else if (i == years.size() - 1) {
                for(int j = 0; j < monthsListFinal.size(); j++) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(years.get(i));
                    temp.add(monthsListFinal.get(j));
                    list.add(temp);
                }
                
            } else {
                for(int j = 0; j < monthsList.size(); j++) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(years.get(i));
                    temp.add(monthsList.get(j));
                    list.add(temp);
                }
                
            }
        }      
        return list;
    }
    
    public int getYears() {
        return this.num;
    }
    
    public void setYears(int num) {
        this.num = num;
    }
    
    public ArrayList<Integer> yearList() {
        
        //Atspausdina metu sarasa pagal pasirinkta issaugoti metu sarasa
        
        ArrayList<Integer> list = new ArrayList<>();
        
        int startYear = startingYear(currentYear(), currentMonth());
        int finalYear = finalYear(currentYear(), currentMonth());
        
        while(startYear <= finalYear) {
            list.add(startYear);
            startYear++;
        }
        
        return list;
    }
    
    public ArrayList<Integer> yearListByFirstChoice(int y) {
        
        //Atspausdina metu sarasa pagal pasirinkta issaugoti metu sarasa
        //NAUJA FUNKCIJA
        
        ArrayList<Integer> list = new ArrayList<>();
        
        int startYear = startingYear(currentYear(), currentMonth());
        int finalYear = finalYear(currentYear(), currentMonth());
        
        while(startYear <= finalYear) {
            if(startYear >= y) {
                list.add(startYear);
            }
            startYear++;
        }
        
        
        return list;
    }
    
    public ArrayList<Integer> monthList(int choosenYear) {
        
        //Pagal pasirinktus metus, pakeicia menesiu sarasa.
        
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        if(choosenYear == startingYear(year, month)) {
            return new ArrayList<>(Arrays.asList(9, 10, 11, 12));
        }
        
        if(choosenYear == finalYear(year, month)) {
            return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        }
        
        return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    }
    
    public ArrayList<Integer> monthListByFirstChoice(ArrayList<Integer> list, int choosenMonth) {
        
        //Pagal pasirinktus metus, pakeicia menesiu sarasa.
        //NAUJA FUNKCIJA
               
        ArrayList<Integer> newList = new ArrayList<>();
        
        for(Integer i: list) {
            if(i >= choosenMonth) {
                newList.add(i);
            }
        }

        return newList;
    }
    
    private int currentMonth() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue();
    }
    
    private int currentYear() {
        LocalDate today = LocalDate.now();
        return today.getYear();
    }
    
    //Noretusi programa sukurti taip, kad metu ir menesiu sarasas butu tik iki
    //einamojo menesio, kad nebutu galima irasineti i prieki. Taip gali buti daug
    //tvarkingiau daromi irasai. Apacioje bandau kurti naujas funkcijas
    
    //Funkcija atrodo pavyko parastyti gerai, jas patikrinau. Dabar tiesiog jas reikia perkelti
    //i HistoryUI
    
    
    public void createHashMap() {
        
        int startYear = startingYear(currentYear(), currentMonth());
        int startMonth = 9;
        int finalYear = currentYear();
        int finalMonth = currentMonth();
        
        
        
        while((startMonth <= finalMonth && startYear == finalYear) || startYear < finalYear) {
            
            this.hashList.putIfAbsent(startYear, new ArrayList<>());
            
            ArrayList<Integer> months = this.hashList.get(startYear);
            months.add(startMonth);
            
            if(startMonth == 12) {
                startMonth = 0;
                startYear++;
            }
            
            startMonth++;
        }
    }
    
    public HashMap<Integer, ArrayList<Integer>> createHashMap1() {
        
        //Sio kodo niekur nenaudoju
        
        int startYear = startingYear(currentYear(), currentMonth());
        int startMonth = 9;
        int finalYear = currentYear();
        int finalMonth = currentMonth();
        
        HashMap<Integer, ArrayList<Integer>> hashMapList = new HashMap<>();
        
        while((startMonth <= finalMonth && startYear == finalYear) || startYear < finalYear) {
            
            hashMapList.putIfAbsent(startYear, new ArrayList<>());
            
            ArrayList<Integer> months = hashMapList.get(startYear);
            months.add(startMonth);
            
            if(startMonth == 12) {
                startMonth = 0;
                startYear++;
            }
            
            startMonth++;
        }
        
        return hashMapList;
    }
    
    public HashMap<Integer, ArrayList<Integer>> getHashMap() {
        return this.hashList;
    }
    
    public ArrayList<Integer> getYears1() {
        Set<Integer> setList = this.hashList.keySet();
        ArrayList<Integer> yearsList = new ArrayList<>(setList);
        Collections.sort(yearsList);
        return yearsList;
    }
    
    public ArrayList<Integer> getMonthByYearFrom1(int y) {
        return this.hashList.get(y);
    }
    
    public ArrayList<Integer> getYearTobyYearFrom1(int y) {
        
        ArrayList<Integer> years = getYears1();
        ArrayList<Integer> newList = new ArrayList<>();
        
        for(int a: years) {
            if(a >= y) {
                newList.add(a);
            }
        }
        
        return newList;
    }
    
    public ArrayList<Integer> getMonthTobyYearFromAndMonthFrom1(int y, int m) {
        
        ArrayList<Integer> months = getMonthByYearFrom1(y);
        ArrayList<Integer> newList = new ArrayList<>();
        
        for(int a: months) {
            if(a >= m) {
                newList.add(a);
            }
        }
        
        return newList;
    }
    
    public ArrayList<Integer> getMonthTobyYearFromAndMonthFrom2(ArrayList<Integer> months, int m) {
        
        ArrayList<Integer> newList = new ArrayList<>();
        
        for(int a: months) {
            if(a >= m) {
                newList.add(a);
            }
        }
        
        return newList;
    }
    
    public ArrayList<ArrayList<Integer>> yearAndMonthList() {
        
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        
        for(Integer year: this.getYears1()) {
            
            for(Integer month: getMonthByYearFrom1(year)) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(year);
                temp.add(month);
                list.add(temp);
            } 
        }
        
        return list;       
    }
    
    public ArrayList<Integer> lastYearListUn() {
        
        ArrayList<Integer> list = new ArrayList<>();
        
        for(int i = currentYear(); i >= currentYear() - 100; i--) {
            list.add(i);
        }

        return list;
    }
    
     public ArrayList<Integer> lastMonthListbyYearUn(int c) {
//        System.out.println(c);
        
        if(c == currentYear()) {
            ArrayList<Integer> list = new ArrayList<>();
            
            for(int i = 1; i <= currentMonth(); i++) {
                list.add(i);
            }
            
            return list;
        }
        
        if(c == currentYear() - 100) {     
            return new ArrayList<>(Arrays.asList(9, 10, 11, 12));
        }  
        
        return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    }
     
    public void createHashMapUn(int finalYear, int finalMonth) {
        
        int startYear = startingYear(finalYear, finalMonth);
        int startMonth = 9;
        
        this.hashList.clear();
 
        while((startMonth <= finalMonth && startYear == finalYear) || startYear < finalYear) {
            
            this.hashList.putIfAbsent(startYear, new ArrayList<>());
            
            ArrayList<Integer> months = this.hashList.get(startYear);
            months.add(startMonth);
            
            if(startMonth == 12) {
                startMonth = 0;
                startYear++;
            }
            
            startMonth++;
        }
    }
    
}

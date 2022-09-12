
package com.mycompany.ataskaituapp.dataWindow.domain;

//Sukuriamas teokratines istorijos sarasas su tarnybos numeriu

import com.mycompany.ataskaituapp.congregationReportwindow.domain.Report;
import java.util.ArrayList;
import java.util.Objects;


public class YearsMonthsServiceConverter {
    
    public static ArrayList<Report> listForWriting1(int service, int startYear, int startMonth,
            int finalYear, int finalMonth, int history) {
        
        //Cia irgi reikia patobulinti, kad butu atsizvlegta i grupe
        
        ArrayList<Report> list = new ArrayList<>();
        
        while(startYear <= finalYear) {
            
            if(startYear < finalYear) {
                
                Report temp = new Report(startYear, startMonth, service, history);

                list.add(temp);
                
                if(startMonth == 12) {
                    startMonth = 1;
                    startYear++;
                } else {
                   startMonth++; 
                }
                       
            } else {
                
                while(startMonth <= finalMonth) {
                    
                    Report temp = new Report(startYear, startMonth, service, history);
                    
                    list.add(temp);
                    
                    startMonth++;
                }
                
                break;
            }
        } 
        return list;
    }
    
    public static ArrayList<Report> listForWriting2(int service, int startYear, int startMonth,
            int finalYear, int finalMonth, int history, String group) {
        
        //Cia irgi reikia patobulinti, kad butu atsizvlegta i grupe
        //CIA dar KAZDAS neveikia
        
        ArrayList<Report> list = new ArrayList<>();
        
        while(startYear <= finalYear) {
            
            if(startYear < finalYear) {
                
                Report temp = new Report(startYear, startMonth, service, history, group);

                list.add(temp);
                
                if(startMonth == 12) {
                    startMonth = 1;
                    startYear++;
                } else {
                   startMonth++; 
                }
                       
            } else {
                
                while(startMonth <= finalMonth) {
                    
                    Report temp = new Report(startYear, startMonth, service, history, group);
                    
                    list.add(temp);
                    
                    startMonth++;
                }
                
                break;
            }
        }
        
        for(Report rep: list) {
            System.out.println(rep.toStringMaindDataWithoutId());
        }
        
        
        return list;
    }
    
    public static ArrayList<Report> listForWritingWithId(int id, int service, int startYear, int startMonth,
            int finalYear, int finalMonth, int history) {
        
        ArrayList<Report> list = new ArrayList<>();
        
        while(startYear <= finalYear) {
            
            if(startYear < finalYear) {
                
                Report temp = new Report(id, startYear, startMonth, service, history);

                list.add(temp);
                
                if(startMonth == 12) {
                    startMonth = 1;
                    startYear++;
                } else {
                   startMonth++; 
                }
                       
            } else {
                
                while(startMonth <= finalMonth) {
                    
                    Report temp = new Report(id, startYear, startMonth, service, history);
                    
                    list.add(temp);
                    
                    startMonth++;
                }
                
                break;
            }
        } 
        return list;
    }
    
    public static ArrayList<Report> listForWritingWithIdNew(int id, int service, int startYear, int startMonth,
            int finalYear, int finalMonth, int history, String group) {
        
        ArrayList<Report> list = new ArrayList<>();
        
        while(startYear <= finalYear) {
            
            if(startYear < finalYear) {
                
                Report temp = new Report(id, startYear, startMonth, service, history, group);

                list.add(temp);
                
                if(startMonth == 12) {
                    startMonth = 1;
                    startYear++;
                } else {
                   startMonth++; 
                }
                       
            } else {
                
                while(startMonth <= finalMonth) {
                    
                    Report temp = new Report(id, startYear, startMonth, service, history, group);
                    
                    list.add(temp);
                    
                    startMonth++;
                }
                
                break;
            }
        } 
        return list;
    }
    
    public static ArrayList<ArrayList<Integer>> listForShowing1(ArrayList<ArrayList<Integer>> fullList) {
        
        //Si koda dar reiketu patobulinti. Reiketu, kad atsizvlegut i tarnybos grupes pokycius
        //Dat turiu tarnyba prideti prie Report, to anksciau ten neturejau.
        
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        
        int service = fullList.get(0).get(2);
        int history = fullList.get(0).get(3);

        int finalYear = fullList.get(0).get(0);
        int finalMonth = fullList.get(0).get(1);
        
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(fullList.get(0).get(0));
        temp.add(fullList.get(0).get(1));
        
        //Tiek kintamieji yra butini del to, kad butu sekamas nusokelumas, chronologiskumas
        int itMonth = fullList.get(0).get(1);
        int itYear = fullList.get(0).get(0);
       
        for(int i = 0; i < fullList.size(); i++) {
            
            //cia ieinama kai pasikeite tarnyba/istorija arba dingo nuoskelumas/chronologija.
            //Patikrinta. Veikia
            
            if((fullList.get(i).get(2) != service || fullList.get(i).get(3) != history) || 
                    (itMonth != fullList.get(i).get(1) || (itMonth == fullList.get(i).get(1) && itYear != fullList.get(i).get(0)))) {
                
                temp.add(finalYear);
                temp.add(finalMonth);
                temp.add(service);
                temp.add(history);

                service = fullList.get(i).get(2);
                history = fullList.get(i).get(3);

                list.add(temp);
                
                temp = new ArrayList<>();

                temp.add(fullList.get(i).get(0));
                temp.add(fullList.get(i).get(1));  
                
                itYear = fullList.get(i).get(0);              
                itMonth = fullList.get(i).get(1);
                
            }
               
            finalYear = fullList.get(i).get(0);
            finalMonth = fullList.get(i).get(1);
            
            if(i == fullList.size() - 1) {
                temp.add(finalYear);
                temp.add(finalMonth);
                temp.add(service);
                temp.add(history);
                
                list.add(temp);
            }                
            
            if (itMonth == 12) {
                itMonth = 1;
                itYear++;
            } else {
                itMonth++;
            }             
        }

        return list;
    }
    
    public static ArrayList<FromToService> listForShowing2(ArrayList<Report> fullList) {
        
        //Perarasiau koda su REport, atrodo, kad viskas veikia. Dar pridejau FromToService. Irgi veikia
        
        ArrayList<FromToService> list = new ArrayList<>();
        
        int service = fullList.get(0).getService();
        int history = fullList.get(0).getHistory();
        String group = fullList.get(0).getGroup();

        int finalYear = fullList.get(0).getYear();
        int finalMonth = fullList.get(0).getMonth();
        
        FromToService temp = new FromToService(fullList.get(0).getYear(), fullList.get(0).getMonth());
        
        //Tiek kintamieji yra butini del to, kad butu sekamas nusokelumas, chronologiskumas
        int itMonth = fullList.get(0).getMonth();
        int itYear = fullList.get(0).getYear();
       
        for(int i = 0; i < fullList.size(); i++) {
            
            //cia ieinama kai pasikeite tarnyba/istorija arba dingo nuoskelumas/chronologija.
            //Patikrinta. Veikia
            
            if((fullList.get(i).getService() != service || fullList.get(i).getHistory() != history 
               || !Objects.equals(fullList.get(i).getGroup(), group)) ||
                    (itMonth != fullList.get(i).getMonth() || (itMonth == fullList.get(i).getMonth() && itYear != fullList.get(i).getYear()))) {
                
                temp.setToYear(finalYear);
                temp.setToMonth(finalMonth);
                temp.setService(service);
                temp.setHistory(history);
                temp.setGroup(group);
                
                //Cia klaida. Negaliu ideti viska i ArrayList, nes group yra String, o ne int. Todel reikia
                //sukurti atskira klase, kuri su tuo susitvarkytu. Ir tada viska deti i ja
                
//                temp.add(group); //naujas

                service = fullList.get(i).getService();
                history = fullList.get(i).getHistory();
                group = fullList.get(i).getGroup(); //naujas

                list.add(temp);
                
                temp = new FromToService(fullList.get(i).getYear(), fullList.get(i).getMonth());
                
                itYear = fullList.get(i).getYear();              
                itMonth = fullList.get(i).getMonth();
                
            }
               
            finalYear = fullList.get(i).getYear();
            finalMonth = fullList.get(i).getMonth();
            
            if(i == fullList.size() - 1) {
                temp.setToYear(finalYear);
                temp.setToMonth(finalMonth);
                temp.setService(service);
                temp.setHistory(history);
                temp.setGroup(group);
                
                list.add(temp);
            }                
            
            if (itMonth == 12) {
                itMonth = 1;
                itYear++;
            } else {
                itMonth++;
            }             
        }
        

        //Cia viskas veikia, tad kazkas blogai su interface.
        for(FromToService f: list) {
            System.out.println("f -> " + f.toString());
        }
        

        return list;
    }
    
}

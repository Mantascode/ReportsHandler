
package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableSet;
import org.sqlite.SQLiteConfig;


public class MinutesReportDao {

    private String link;
 
    public MinutesReportDao(String link) {
        this.link = link;
    }
    
    private Connection conn() {
        Connection conn = null;
        
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            conn = DriverManager.getConnection(this.link, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    public void updateMinutes(ObservableSet<Report> setReport) {
        
        String sql1 = "SELECT ketvirtis FROM duomenys WHERE id = ?";
        
        String sql2 = "UPDATE duomenys SET ketvirtis = ? WHERE id = ?";
        
        String sql3 = "UPDATE ataskaita SET ketvirtis = ?, valandos = ? WHERE id = ?";
        
        String sql4 = "UPDATE ataskaita SET ketvirtis = ? WHERE id = ?";
        
        String sql5 = "SELECT id, valandos, ketvirtis FROM ataskaita WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            
            for(Report rep: setReport) {
                
                if(rep.getHour() < 1 && rep.getHour() > 0) {
                    
                    ArrayList<MinutesReport> minR = getForwardMinutes(rep.getPubId(), rep.getId());
                    
                    double oldValue = 0;
                    double min = rep.getHour();
                    double newMin = min;
                    
                    System.out.println("newMin1 -> " + newMin);

                    //Patikrinimas, ar anksciau buvo irasytas ketvirtis. Pagal tai atitinkamai pakeicia min reiksme.
                    stat = conn.prepareStatement(sql5);
                    stat.setInt(1, rep.getId());

                    results = stat.executeQuery();

                    results.next();
                    MinutesReport pubMinRep = new MinutesReport(results.getInt("id"), results.getDouble("valandos"), results.getDouble("ketvirtis"));

                    if(pubMinRep.getMinutes() < 1 && pubMinRep.getMinutes() > 0) {
                        min = min - pubMinRep.getMinutes();
                    }
                    
                    System.out.println("newMin2 -> " + newMin);

                    stat = conn.prepareStatement(sql1);
                    stat.setInt(1, rep.getPubId());
                    results = stat.executeQuery();
                    oldValue = results.getDouble("ketvirtis");

                    //Naujoji ketviciu reiksme irasoma i duomenys
                    stat = conn.prepareStatement(sql2);
                    stat.setDouble(1, min + oldValue);
                    stat.setInt(2, rep.getPubId());
                    stat.executeUpdate();

                    if(minR.isEmpty()) {
                    //Irasao i ataskaitos ketvirciu galutine ketvirciu suma, jei tai yra paskutinis irasas
                        stat = conn.prepareStatement(sql4);
                        stat.setDouble(1, min + oldValue);
                        stat.setDouble(2, newMin);
                        stat.setInt(3, rep.getId());
                        stat.executeUpdate(); 
                    } else {
                        //Atskaiciuoja, kokia yra totalMinutes reiksme tai eilutei
                        double newOld = minR.get(0).getTotalMinutes() - minR.get(0).getMinutes();

                        stat = conn.prepareStatement(sql3);
                        stat.setDouble(1, min + newOld);
                        stat.setDouble(2, newMin);
                        stat.setInt(3, rep.getId());
                        stat.executeUpdate();

                        //Perrasau telesnes minuciu reiksmes
                        for(MinutesReport minRep: minR) {
                            System.out.println(minRep.toString());
                            stat = conn.prepareStatement(sql4);
                            stat.setDouble(1, min +  minRep.getTotalMinutes());
                            stat.setInt(2, minRep.getRowId());
                            stat.executeUpdate();
                        }    
                    }  
                }
            }  
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException  e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void minusMinutes(ObservableSet<Report> setReport) {
        
        //Pirmas irasau ketvircius, po to normalia ataskaita. VEIKIA
        
        String sql1 = "SELECT ketvirtis FROM duomenys WHERE id = ?";
        double oldValue = 0;
        
        String sql2 = "UPDATE duomenys SET ketvirtis = ? WHERE id = ?";
        
        String sql3 = "UPDATE ataskaita SET ketvirtis = ?, valandos = ? WHERE id = ?";
        
        String sql4 = "UPDATE ataskaita SET ketvirtis = ? WHERE id = ?";
        
        String sql5 = "SELECT valandos FROM ataskaita WHERE id = ?";
        double min = 0;
       
        
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            
            for(Report rep: setReport) {
                
                //Dar karta patikrinu, ar tikrai reikia trinti, o ne update vykdyti. Tiesiog galiu tureti Report masyve,
                //jei jis vel buvo koreguotas dar pries issaugojima, jis vistiek masyve lieka, nors ir reiksme gal jau pasikeite
                if(rep.getHour() >= 1) {
                    
                    ArrayList<MinutesReport> minR = getForwardMinutes(rep.getPubId(), rep.getId());
                
                    stat = conn.prepareStatement(sql5);
                    stat.setInt(1, rep.getId());
                    results = stat.executeQuery();

                    if(results.next()) {
                        min = results.getDouble("valandos");
                    }

                    //Gauname sena ketviciu reiksme
                    stat = conn.prepareStatement(sql1);
                    stat.setInt(1, rep.getPubId());
                    results = stat.executeQuery();
                    oldValue = results.getDouble("ketvirtis");

                    //Naujoji ketviciu reiksme irasoma i duomenys
                    stat = conn.prepareStatement(sql2);
                    stat.setDouble(1, oldValue - min);
                    stat.setInt(2, rep.getPubId());
                    stat.executeUpdate();     

                    if(minR.isEmpty()) {
                    //Irasao i ataskaitos ketvirciu galutine ketvirciu suma, jei tai yra paskutinis irasas
                        stat = conn.prepareStatement(sql3);
                        stat.setInt(3, rep.getId());
                        stat.executeUpdate(); 
                    } else {
                        //Atskaiciuoja, kokia yra totalMinutes reiksme tai eilutei
                        double newOld = minR.get(0).getTotalMinutes() - minR.get(0).getMinutes();

                        stat = conn.prepareStatement(sql3);
                        stat.setInt(3, rep.getId());
                        stat.executeUpdate();

                        //Perrasau telesnes minuciu reiksmes
                        for(MinutesReport minRep: minR) {
                            System.out.println(minRep.toString());
                            stat = conn.prepareStatement(sql4);
                            stat.setDouble(1, minRep.getTotalMinutes() - min);
                            stat.setInt(2, minRep.getRowId());
                            stat.executeUpdate();
                        }    
                    }
                    
                }

            }
            
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException  e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private ArrayList<MinutesReport> getForwardMinutes(int id, int rowId) {
        
        //Veikia sis kodas. Gauna MinutesReport tolesniu menesiu, nei tada, kai yra irasoma
        
        ArrayList<MinutesReport> minRep = new ArrayList<>();
        
        String sql = "SELECT id, valandos, ketvirtis FROM ataskaita WHERE valandos < 1 AND "
                + "skelbejas_id = ? AND metai = ? AND menuo = ?";
        
        String sql1 = "SELECT metai, menuo FROM ataskaita WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            
            stat = conn.prepareStatement(sql1);
            stat.setInt(1, rowId);
            results = stat.executeQuery();
            results.next();
            
            ArrayList<ArrayList<Integer>> list = getYearMonthList(results.getInt("metai"),
                results.getInt("menuo"), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

            for(ArrayList<Integer> date: list) { 
 
                stat = conn.prepareStatement(sql);
                stat.setInt(1, id);
                stat.setInt(2, date.get(0));
                stat.setInt(3, date.get(1));
                
                results = stat.executeQuery();
                
                if(results.next()) {
                    minRep.add(new MinutesReport(results.getInt("id"), results.getDouble("valandos"), results.getDouble("ketvirtis")));
                }
            }           
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException  e) {
                System.out.println(e.getMessage());
            }
        }
        
        
        return minRep;
    }
    
    private ArrayList<ArrayList<Integer>> getYearMonthList(int startYear,
            int startMonth, int finalYear, int finalMonth) {
        
        //Veikia puikiai. Sukuria tai [[2021, 1], [2021, 2] ... ]
        if(startMonth < 12) {
            startMonth++;
        } else {
            startMonth = 1;
            startYear++;
        }
        
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();

        while(startYear <= finalYear) {
            
            if(startYear != finalYear) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(startYear);
                temp.add(startMonth);
                
                list.add(temp);
                         
                if(startMonth < 12) {
                    startMonth++;
                } else {
                    startMonth = 1;
                    startYear++;
                }

            } else {
                
                while(startMonth <= finalMonth) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(startYear);
                    temp.add(startMonth);
                    
                    list.add(temp);
                    
                    startMonth++;
                }
                //kad iseiciau is ciklo
                startYear++;
            }   
        }
        return list;
    }   
}

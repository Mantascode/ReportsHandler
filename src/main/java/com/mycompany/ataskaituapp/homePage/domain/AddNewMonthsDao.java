
package com.mycompany.ataskaituapp.homePage.domain;

import com.mycompany.ataskaituapp.settings.domain.YearsAndMonthsSettings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteConfig;


public class AddNewMonthsDao {
    
    private String databaseLink;
    
    public AddNewMonthsDao(String link) {
        this.databaseLink = link;
    }
    
    private Connection conn() {
        Connection conn = null;
        
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            conn = DriverManager.getConnection(this.databaseLink, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    public String getLastDates() {
        
        String date = "";
        
        String sql = "SELECT paskutinis_prisijungimas FROM metu_nustatymai WHERE id = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            
            date = results.getString("paskutinis_prisijungimas");
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException  e) {
                System.out.println(e.getMessage());
            }
        }

        return date;       
    }
    
    public void writeNewDates(ArrayList<ArrayList<Integer>> dates, String currentDate) {
        
        //Teliko visa tai patikrinti!!!!
        
        String sqlGetId = "SELECT id, tarnyste, grupe_id FROM duomenys WHERE aktyvus = 1 AND istrinta = 0";
        ArrayList<DataForNewDates> listId = new ArrayList<>();
        
        String sqlWriteNewMonths = "INSERT OR IGNORE INTO ataskaita(metai, menuo,"
                + "skelbejas_id, tarnyste, grupe_id, istorija) VALUES(?,?,?,?,?,1)";
        
        String sqlUpdateLastDate = "UPDATE metu_nustatymai SET paskutinis_prisijungimas = ? WHERE id = 1";
        
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sqlGetId).executeQuery();
            
            while(results.next()) {
                listId.add(new DataForNewDates(results.getInt("id"), results.getInt("tarnyste"), results.getInt("grupe_id")));
            }
            
//            for(DataForNewDates list: listId) {
//                System.out.println(list.toString());
//            }
            
            
            stat = conn.prepareStatement(sqlWriteNewMonths);
            
            for(DataForNewDates list: listId) {
                
                for(ArrayList<Integer> yAndM: dates) {
                    
//                    System.out.println("yAndM.get(0) -> " + yAndM.get(0));
//                    System.out.println("yAndM.get(1) -> " + yAndM.get(1));
//                    System.out.println("list.getId()) -> " + list.getId());
//                    System.out.println("list.getService() -> " + list.getService());
//                    System.out.println("list.getGroup() -> " + list.getGroup());
//                    System.out.println("------------------------------------");
                    
                    stat.setInt(1, yAndM.get(0));
                    stat.setInt(2, yAndM.get(1));
                    stat.setInt(3, list.getId());
                    stat.setInt(4, list.getService());
                    stat.setInt(5, list.getGroup());

                    stat.executeUpdate();
                } 
            }
            
            stat = conn.prepareStatement(sqlUpdateLastDate);
            stat.setString(1, currentDate);
  
            stat.executeUpdate();
            

            
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
    
    
}



package com.mycompany.ataskaituapp.dataWindow.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import org.sqlite.SQLiteConfig;
//



//Cia dar niekas nepatikrinta


 

//

public class YearsMonthsServiceDao {
    
    private String databaseLink;
    
    public YearsMonthsServiceDao(String link) {
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
    
    public ArrayList<YearsMonthsService> list() {
        ArrayList<YearsMonthsService> list = new ArrayList<>();
        
        String sql = "SELECT ataskaita.metai, ataskaita.menuo, ataskaita.tarnyste" +
                     "WHERE skelbejas_id = 0";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.add(new YearsMonthsService(results.getInt("metai"),
                        results.getInt("menuo"), results.getInt("tarnyste"), results.getInt("")));
            }
            
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

        return list;
    }
    
    public void insert(YearsMonthsService ob) {
        String sql = "INSERT INTO ataskaita(metai, menuo, skelbejas_id, tarnyste)"
                + "VALUES(?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, ob.getYear());
            stat.setInt(2, ob.getMonth());
            stat.setInt(3, ob.getId());
            stat.setInt(4, ob.getService());


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
    
    public void update(int id, Publisher pub) {
        String sql = "UPDATE ataskaita SET tarnyste = ?,"
                   + "WHERE metai = ?, menuo = ?, id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setString(1, pub.getName());
            stat.setString(2, pub.getLastName());
            stat.setInt(3, pub.getSex());
            stat.setString(4, pub.getBirthDate());
            stat.setString(5, pub.getBaptiseDate());
            stat.setInt(6, pub.getHope());
            stat.setInt(7, pub.getElder());
            stat.setInt(8, pub.getMinisterial());
            stat.setInt(9, pub.getService());
            stat.setString(10, pub.getGroup());
            
            stat.setInt(11, id);
            
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


package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteConfig;


public class SumDao {
    
    private String link;
    
    public SumDao(String link) {
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
    
    
    public ArrayList<Sum> getReports(int year, int month) {
        
        String sql = "SELECT leidiniai, siuzetai, valandos, aplankymai, studijos, tarnyste, ketvirtis FROM ataskaita " +
                     "WHERE tarnyste > 0 AND tarnyste < 4 AND metai = ? AND menuo = ? AND istorija = 1";
        
        
        ArrayList<Sum> list = new ArrayList();
        
        Connection conn = null;
        ResultSet results = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, year);
            stat.setInt(2, month);
            results = stat.executeQuery();
            while(results.next()) {

                list.add(new Sum(results.getInt("leidiniai"), results.getInt("siuzetai"),
                    results.getDouble("valandos"), results.getInt("aplankymai"),
                    results.getInt("studijos"), results.getInt("tarnyste"),
                    results.getDouble("ketvirtis")));
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
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException  e) {
                System.out.println(e.getMessage());
            }
        }
        
        
        return list;
        
    }
}

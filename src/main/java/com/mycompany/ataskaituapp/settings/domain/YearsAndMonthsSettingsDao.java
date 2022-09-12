

package com.mycompany.ataskaituapp.settings.domain;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteConfig;

public class YearsAndMonthsSettingsDao {
    
    private String dataBase;
    
    public YearsAndMonthsSettingsDao(String link) {
        this.dataBase = link;
    }
    
    private Connection conn() {
        Connection conn = null;
        
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            conn = DriverManager.getConnection(this.dataBase, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    public ArrayList<ArrayList<Integer>> list() {
             
        //ATRODO, KAD TO NEBEREIKIA
        String sql = "Select * FROM metu_nustatymai WHERE id = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        YearsAndMonthsSettings ob = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            
            //Ta dar reiketu patikrinti
            ob = new YearsAndMonthsSettings(results.getInt("kiek_metu"));
            
            
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

        return ob.list();
    }
    
    public YearsAndMonthsSettings getYearsAndMonthobject() {
             
        //Atrodo, kad ustenka tik sios vienos funkcijos ir update siame DAO.
        String sql = "Select * FROM metu_nustatymai WHERE id = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        YearsAndMonthsSettings ob = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            
            //Ta dar reiketu patikrinti
            ob = new YearsAndMonthsSettings(results.getInt("kiek_metu"));
            
            
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

        return ob;
    }
    
    public ArrayList<Integer> listYear() {
             
        //ATRODO, KAD TO NEBEREIKIA
        String sql = "Select * FROM metu_nustatymai WHERE id = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        YearsAndMonthsSettings ob = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            
            //Ta dar reiketu patikrinti
            ob = new YearsAndMonthsSettings(results.getInt("kiek_metu"));
            
            
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

        return ob.yearList();
    }
    
    public ArrayList<Integer> listMonth(int y) {
             
        //ATRODO, KAD TO NEBEREIKIA
        String sql = "Select * FROM metu_nustatymai WHERE id = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        YearsAndMonthsSettings ob = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            
            //Ta dar reiketu patikrinti
            ob = new YearsAndMonthsSettings(results.getInt("kiek_metu"));
            
            
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

        return ob.monthList(y);
    }  
    
    
    public void update(YearsAndMonthsSettings m) {
        String sql = "UPDATE metu_nustatymai SET kiek_metu = ?  WHERE id = 1";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, m.getYears());
  
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
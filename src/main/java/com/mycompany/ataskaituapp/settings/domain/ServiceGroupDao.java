

package com.mycompany.ataskaituapp.settings.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.sqlite.SQLiteConfig;


public class ServiceGroupDao {
    
    private String dataBase;
    
    public ServiceGroupDao(String link) {
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
    
    public ArrayList<ServiceGroup> list() {
        ArrayList<ServiceGroup> list = new ArrayList<>();
        
        String sql = "Select * FROM grupes";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.add(new ServiceGroup(results.getInt("id"), results.getString("grupe")));
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
        
        Collections.sort(list);
        
        return list;
    }
    
    public ArrayList<String> listOfGroupNames() {
        ArrayList<String> list = new ArrayList<>();
        
        String sql = "Select grupe FROM grupes ORDER BY grupe ASC";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.add(results.getString("grupe"));
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
        
        Collections.sort(list);
        
        return list;
    }
    
    public HashMap<Integer, String> hashMapList() {
        
        
        HashMap<Integer, String> list = new HashMap<>();
        
        String sql = "Select * FROM grupes";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.putIfAbsent(results.getInt("id"), results.getString("grupe"));
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
    
    public void insert(ServiceGroup grupe) {
        String sql = "INSERT INTO grupes(grupe) VALUES(?)";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setString(1, grupe.getName());

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
    
    public void update(int id, ServiceGroup grupe) {
        String sql = "UPDATE grupes SET grupe = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setString(1, grupe.getName());
            stat.setInt(2, id);
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
    
    public void remove(int id) {
        String sql = "DELETE FROM grupes WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
         try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
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

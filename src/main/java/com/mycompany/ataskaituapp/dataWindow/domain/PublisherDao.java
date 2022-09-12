
package com.mycompany.ataskaituapp.dataWindow.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import org.sqlite.SQLiteConfig;

public class PublisherDao {
    
    private String databaseLink;
    
    public PublisherDao(String link) {
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
    
    public ArrayList<Publisher> list() {
        ArrayList<Publisher> list = new ArrayList<>();
        
        String sql = "SELECT duomenys.id, duomenys.vardas, duomenys.pavarde, duomenys.lytis,"
                      + "duomenys.gimimo_data, duomenys.kriksto_data, duomenys.viltis, duomenys.vyresnysis,\n" +
                        "duomenys.patarnautojas, duomenys.tarnyste, grupes.grupe, duomenys.aktyvus\n" +
                     "FROM duomenys\n" +
                     "LEFT JOIN grupes\n" +
                     "ON grupes.id = duomenys.grupe_id\n" +
                     "WHERE duomenys.istrinta = 0";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.add(new Publisher(results.getInt("id"), results.getString("vardas"),
                    results.getString("pavarde"), results.getInt("lytis"),
                    results.getString("gimimo_data"), results.getString("kriksto_data"),
                    results.getInt("viltis"), results.getInt("vyresnysis"), results.getInt("patarnautojas"),
                    results.getInt("tarnyste"), results.getString("grupe"), results.getInt("aktyvus")));
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
    
     public ArrayList<Publisher> listOfDeletedPub() {
        ArrayList<Publisher> list = new ArrayList<>();
        
        String sql = "SELECT duomenys.id, duomenys.vardas, duomenys.pavarde, duomenys.lytis,"
                      + "duomenys.gimimo_data, duomenys.kriksto_data, duomenys.viltis, duomenys.vyresnysis,\n" +
                        "duomenys.patarnautojas, duomenys.tarnyste, grupes.grupe, duomenys.aktyvus\n" +
                     "FROM duomenys\n" +
                     "LEFT JOIN grupes\n" +
                     "ON grupes.id = duomenys.grupe_id\n" +
                     "WHERE duomenys.istrinta = 1";
        
        Connection conn = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            results = conn.prepareStatement(sql).executeQuery();        
            while(results.next()) {
                list.add(new Publisher(results.getInt("id"), results.getString("vardas"),
                    results.getString("pavarde"), results.getInt("lytis"),
                    results.getString("gimimo_data"), results.getString("kriksto_data"),
                    results.getInt("viltis"), results.getInt("vyresnysis"), results.getInt("patarnautojas"),
                    results.getInt("tarnyste"), results.getString("grupe"), results.getInt("aktyvus")));
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
    
    public void insert(Publisher pub) {
        String sql = "INSERT INTO duomenys(vardas, pavarde, lytis, gimimo_data, kriksto_data,"
                + "viltis, vyresnysis, patarnautojas, tarnyste, grupe_id, istrinta, aktyvus)"
                + "VALUES(?,?,?,?,?,?,?,?,?,(SELECT id FROM grupes WHERE grupe=?), ?, ?)";
        
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
            stat.setInt(11, 0);
            stat.setInt(12, pub.getActive());

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
        String sql = "UPDATE duomenys SET vardas = ?, pavarde = ?, lytis = ?, gimimo_data = ?,"
                + "kriksto_data = ?, viltis = ?, vyresnysis = ?, patarnautojas = ?,"
                + "tarnyste = ?, grupe_id = (SELECT id FROM grupes WHERE grupe=?), aktyvus = ? WHERE id = ?";
        
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
            stat.setInt(11, pub.getActive());
            
            stat.setInt(12, id);
            
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
    
    public void delete(int id) {
        String sql = "DELETE FROM duomenys WHERE id = ?";
        
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
    
    public void partialDelete(int id) {
        String sql = "UPDATE duomenys SET istrinta = 1 WHERE id = ?";
        
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

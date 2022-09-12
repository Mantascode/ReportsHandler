
package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.collections.ObservableSet;
import org.sqlite.SQLiteConfig;

public class ReportDao {
    
    private String link;
    
    public ReportDao(String link) {
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
    
    private ArrayList<Report> listByMonth(int year, int month) {
        ArrayList<Report> list = new ArrayList<>();
        
        
        
        return list;
    }
    
    private ArrayList<Report> listByYear(int id, int years) {
        ArrayList<Report> list = new ArrayList<>();
        
        
        
        return list;
    }
    
    public void insert(Report report) {
        //Cia dar reikia patikrinti, ar isvis ta koda naudojus ir ar nera efektyiau
        //ikelti ne Report report, o ArrayList<report>
        //Atodo, kad sios funkcijos niekus nenaudoju
        String sql = "INSERT OR IGNORE INTO ataskaita(metai, menuo,"
                + "skelbejas_id, istorija, tarnyste)"
                + "VALUES(?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, report.getYear());
            stat.setInt(2, report.getMonth());
            stat.setInt(3, report.getPubId());

            stat.setInt(4, report.getHistory());
            stat.setInt(5, report.getService());

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
    
    public void insertWithoutId(ArrayList<Report> reportList) {
        String sql = "INSERT OR IGNORE INTO ataskaita(metai, menuo,"
                + "skelbejas_id, istorija, tarnyste)"
                + "VALUES(?, ?, (SELECT seq FROM sqlite_sequence WHERE name = \"duomenys\"),?,?)";
        
        //SELECT last_insert_rowid() veikia tik viena karta. Pirma karta irasant duomenis
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            
            for(Report report: reportList) {
                stat.setInt(1, report.getYear());
                stat.setInt(2, report.getMonth());
                stat.setInt(3, report.getHistory());
                stat.setInt(4, report.getService());

                stat.executeUpdate();
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
    
    public void insertWithoutIdNew(ArrayList<Report> reportList) {
        String sql = "INSERT OR IGNORE INTO ataskaita(metai, menuo,"
                + "skelbejas_id, istorija, tarnyste, grupe_id)"
                + "VALUES(?, ?, (SELECT seq FROM sqlite_sequence WHERE name = \"duomenys\"),?,?,"
                + "(SELECT id FROM grupes WHERE grupe = ?))";
        
        //SELECT last_insert_rowid() veikia tik viena karta. Pirma karta irasant duomenis
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            
            for(Report report: reportList) {
                stat.setInt(1, report.getYear());
                stat.setInt(2, report.getMonth());
                stat.setInt(3, report.getHistory());
                stat.setInt(4, report.getService());
                stat.setString(5, report.getGroup());
                System.out.println(report.getGroup());

                stat.executeUpdate();
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
    
    public ArrayList<Report> getShortReportById(int id) {
       
        ArrayList<Report> list = new ArrayList<>();
        
        String sql = "SELECT skelbejas_id, metai, menuo, istorija, tarnyste FROM ataskaita "
                   + "WHERE skelbejas_id = ? ORDER BY metai ASC, menuo ASC";
        
        Connection conn = null;
        ResultSet results = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            results = stat.executeQuery();
            while(results.next()) {

                list.add(new Report(results.getInt("skelbejas_id"), results.getInt("metai"),
                    results.getInt("menuo"), results.getInt("tarnyste"),
                    results.getInt("istorija")));
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
    
    public ArrayList<Report> getShortReportByIdNew(int id) {
       
        ArrayList<Report> list = new ArrayList<>();

        String sql = "SELECT skelbejas_id, metai, menuo, istorija, tarnyste, grupe FROM ataskaita LEFT JOIN grupes ON grupes.id = grupe_id "
                   + "WHERE skelbejas_id = ? ORDER BY metai ASC, menuo ASC";

        Connection conn = null;
        ResultSet results = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            results = stat.executeQuery();
            while(results.next()) {

                list.add(new Report(results.getInt("skelbejas_id"), results.getInt("metai"),
                    results.getInt("menuo"), results.getInt("tarnyste"),
                    results.getInt("istorija"), results.getString("grupe")));
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
    
    public void insertAfterUpadate(ArrayList<Report> reportList) {
        
        String sql = "INSERT OR IGNORE INTO ataskaita(skelbejas_id, metai, "
                + "menuo, tarnyste, istorija) "
                + "VALUES(?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            
            for(Report report: reportList) {

                stat.setInt(1, report.getPubId());
                stat.setInt(2, report.getYear());
                stat.setInt(3, report.getMonth());

                stat.setInt(4, report.getService());
                stat.setInt(5, report.getHistory());

                stat.executeUpdate(); 
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
    
    public void update(ArrayList<Report> list) {
        
        String sql = "UPDATE ataskaita SET tarnyste = ?, istorija = ? "
                + "WHERE skelbejas_id = ? AND metai = ? AND menuo = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            
            for(Report rep: list) {
                stat.setInt(1, rep.getService());
                stat.setInt(2, rep.getHistory());
                stat.setInt(3, rep.getPubId());
                stat.setInt(4, rep.getYear());
                stat.setInt(5, rep.getMonth());
                
                stat.executeUpdate();
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
    
    public void removeIfNotInIdList(ArrayList<Report> list) {
        
        ArrayList<Integer> idList = new ArrayList<>();
        
        //veil keistu budu gaunu Id, bet veikia
        int skelbId = list.get(0).getPubId();
        
        String sql = "SELECT id FROM ataskaita WHERE skelbejas_id = ? AND metai = ? AND menuo = ?";  
        
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            
            for(Report rep: list) {
                
                stat = conn.prepareStatement(sql);
                stat.setInt(1, rep.getPubId());
                stat.setInt(2, rep.getYear());
                stat.setInt(3, rep.getMonth());
                
                results = stat.executeQuery();
                while(results.next()) {
                    idList.add(results.getInt("id"));
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
        
        deleteIfNotInList(idList, skelbId);
    }
    
    public void deleteIfNotInList(ArrayList<Integer> list, int id) {
        //istrina tai, kas buvo pasalinta redaguojant skelbejo istorija
        //sql veikia kai masyvas yra tarp skliausteliu, pvz (1, 2, 3);
             
        
        String listString = list.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
        
        String sql = "DELETE FROM ataskaita WHERE id NOT IN (" + listString + ") AND skelbejas_id = " + id;
        
        Connection conn = null;
        PreparedStatement stat = null;
        
         try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
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
        String sql = "DELETE FROM ataskaita WHERE skelbejas_id =  ?";
        
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
    
    public ArrayList<Report> getReportByYearAndMonth(int year, int month) {
       
        ArrayList<Report> list = new ArrayList<>();

//        String sql = "SELECT ataskaita.id, duomenys.pavarde, duomenys.vardas, ataskaita.leidiniai, ataskaita.siuzetai, ataskaita.valandos, " +
//                    "ataskaita.aplankymai, ataskaita.studijos, ataskaita.pastabos, ataskaita.tarnyste, ataskaita.grupe_id " +
//                    "FROM ataskaita LEFT JOIN duomenys ON duomenys.id = ataskaita.skelbejas_id " +
//                    "WHERE ataskaita.metai = ? AND ataskaita.menuo = ? AND ataskaita.istorija = 1";
        
        String sql = "SELECT ataskaita.id, ataskaita.skelbejas_id, duomenys.pavarde, duomenys.vardas, ataskaita.leidiniai, ataskaita.siuzetai, ataskaita.valandos, " +
                    "ataskaita.aplankymai, ataskaita.studijos, ataskaita.pastabos, ataskaita.tarnyste, ataskaita.grupe_id " +
                    "FROM ataskaita LEFT JOIN duomenys ON duomenys.id = ataskaita.skelbejas_id " +
                    "WHERE ataskaita.metai = ? AND ataskaita.menuo = ? AND ataskaita.istorija = 1";
        
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
                
                list.add(new Report(results.getInt("id"), results.getInt("skelbejas_id"), fullName(results.getString("pavarde"), results.getString("vardas")),
                    results.getInt("leidiniai"), results.getInt("siuzetai"),
                    results.getDouble("valandos"), results.getInt("aplankymai"),
                    results.getInt("studijos"), results.getString("pastabos"),
                    results.getInt("tarnyste"), results.getInt("grupe_id")));
                    
//                list.add(new Report(results.getInt("id"), fullName(results.getString("pavarde"), results.getString("vardas")),
//                    results.getInt("leidiniai"), results.getInt("siuzetai"),
//                    results.getDouble("valandos"), results.getInt("aplankymai"),
//                    results.getInt("studijos"), results.getString("pastabos"),
//                    results.getInt("tarnyste"), results.getInt("grupe_id")));
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
    
    private String fullName(String a, String b) {
        return a + " " + b;
    }
    
    public void update(ObservableSet<Report> list) {
        
        String sql = "UPDATE ataskaita SET leidiniai = ?, siuzetai = ?, "
                + "valandos = ?, aplankymai = ?, studijos = ?, pastabos "
                + "= ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stat = null;
        
        try {
            conn = this.conn();
            stat = conn.prepareStatement(sql);
            
            for(Report rep: list) {
                stat.setInt(1, rep.getPublication());
                stat.setInt(2, rep.getVideo());
                stat.setDouble(3, rep.getHour());
                stat.setInt(4, rep.getReturnVisit());
                stat.setInt(5, rep.getStudy());
                stat.setString(6, rep.getNote());
                
                stat.setInt(7, rep.getId());
                
                stat.executeUpdate();
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
    
//UPDATING NUO CIA ->
    
    public void updateReport(ArrayList<Report> reportList) {
        
        //Atrodo, kad sis kodas veikia puikiai
         
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet results = null;
        
        try {
            conn = this.conn();
            
            //Atnaujinu irasus Reporte
            String sql1 = "UPDATE ataskaita SET tarnyste = ?, istorija = ?, grupe_id = "
                + "(SELECT id FROM grupes WHERE grupe = ?) "
                + "WHERE skelbejas_id = ? AND metai = ? AND menuo = ?";
            
            stat = conn.prepareStatement(sql1);
            
            for(Report rep: reportList) {
                stat.setInt(1, rep.getService());
                stat.setInt(2, rep.getHistory());
                stat.setString(3, rep.getGroup());
                stat.setInt(4, rep.getPubId());
                stat.setInt(5, rep.getYear());
                stat.setInt(6, rep.getMonth());
                
                stat.executeUpdate();
            }

            //Irasau naujus irasu i Report
            //Pataisytas, bet nepatikrintas
             String sql2 = "INSERT OR IGNORE INTO ataskaita(skelbejas_id, metai, "
                + "menuo, tarnyste, istorija, grupe_id) "
                + "VALUES(?,?,?,?,?, (SELECT id FROM grupes WHERE grupe = ?))";
            
            stat = conn.prepareStatement(sql2);
            
            for(Report report: reportList) {

                stat.setInt(1, report.getPubId());
                stat.setInt(2, report.getYear());
                stat.setInt(3, report.getMonth());
                stat.setInt(4, report.getService());
                stat.setInt(5, report.getHistory());
                stat.setString(6, report.getGroup());

                stat.executeUpdate(); 
            }
            
            //Surenku id sarasa is ataxskaitos, kuri reikia palikit, o vis kita bus istrinta
            ArrayList<Integer> idList = new ArrayList<>();
            int skelbId = reportList.get(0).getPubId();
            
            String sql3 = "SELECT id FROM ataskaita WHERE skelbejas_id = ? AND metai = ? AND menuo = ?";
            
            for(Report rep: reportList) {
                
                stat = conn.prepareStatement(sql3);
                stat.setInt(1, rep.getPubId());
                stat.setInt(2, rep.getYear());
                stat.setInt(3, rep.getMonth());
                
                results = stat.executeQuery();
                while(results.next()) {
                    idList.add(results.getInt("id"));
                }
            }
            
            //Istrinu irasus, kuriu nera id sarase
            
            String listString = idList.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
        
            String sql4 = "DELETE FROM ataskaita WHERE id NOT IN (" + listString + ") AND skelbejas_id = " + skelbId;
            
            stat = conn.prepareStatement(sql4);
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
    
    public ArrayList<Report> getReportByYearAndMonthForBranch(int year, int month) {
       
        ArrayList<Report> list = new ArrayList<>();

        String sql = "SELECT ataskaita.skelbejas_id, " +
                     "ataskaita.leidiniai, " +
                     "ataskaita.siuzetai, " +
                     "ataskaita.valandos, " +
                     "ataskaita.aplankymai, " +
                     "ataskaita.studijos, " +
                     "ataskaita.tarnyste " +
                     "FROM ataskaita " +
                     "WHERE ataskaita.metai = ? " +
                     "AND ataskaita.menuo = ? " +
                     "AND ataskaita.istorija = 1 " +
                     "AND tarnyste < 4";
        
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
                
                list.add(new Report(results.getInt("skelbejas_id"),
                    results.getInt("leidiniai"), results.getInt("siuzetai"),
                    results.getDouble("valandos"), results.getInt("aplankymai"),
                    results.getInt("studijos"), results.getInt("tarnyste")));
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
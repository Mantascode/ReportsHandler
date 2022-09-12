
package com.mycompany.ataskaituapp.congregationReportwindow.domain;


public class Report {
    
    private int id;
    private int year;
    private int month;
    private int pubId;
    private int publication;
    private int video;
    private double hour;
    private int returnVisit;
    private int study;
    private String note;
    private int history;
    private int service;
    private String fullName;
    private int idInReport;
    private String group;
    private int groupId;
    

    //Atrodo, kad sio konstruktoriau niekue nenaudoju
    public Report(int id, int year, int month, int pubId, int publication,
            int video, double hour, int returnVisit, int study, String note,
            int history, int service) {
        
        this.id = id;
        this.year = year;
        this.month = month;
        this.pubId = pubId;
        this.publication = publication;
        this.video = video;
        this.hour = hour;
        this.returnVisit = returnVisit;
        this.study = study;
        this.note = note;
        this.history = history;
        this.service = service;
        
    }
    
    public Report(int id, String fullName, int publication,
            int video, double hour, int returnVisit, int study, String note,
            int service) {
         
        this.id = id;
        this.fullName = fullName;
        this.publication = publication;
        this.video = video;
        this.hour = hour;
        this.returnVisit = returnVisit;
        this.study = study;
        this.note = note;
        this.service = service; 

    }
    
    public Report(int id, String fullName, int publication,
            int video, double hour, int returnVisit, int study, String note,
            int service, int group) {
         
        this.id = id;
        this.fullName = fullName;
        this.publication = publication;
        this.video = video;
        this.hour = hour;
        this.returnVisit = returnVisit;
        this.study = study;
        this.note = note;
        this.service = service; 
        this.groupId = group;
    }
    
    public Report(int id, int pubId, String fullName, int publication,
            int video, double hour, int returnVisit, int study, String note,
            int service, int group) {
         
        this.id = id;
        this.pubId = pubId;
        this.fullName = fullName;
        this.publication = publication;
        this.video = video;
        this.hour = hour;
        this.returnVisit = returnVisit;
        this.study = study;
        this.note = note;
        this.service = service; 
        this.groupId = group;
    }
    
    public Report(int pubId, int year, int month, 
            int service, int history) {
        
        this.pubId = pubId;
        this.year = year;
        this.month = month;      
        this.history = history;
        this.service = service;  
        
    }
    
    public Report(int year, int month, int service,
            int history) {
        
        this.year = year;
        this.month = month;
        this.history = history;
        this.service = service;  
    }
    
    //Nauji Report su this.group
    public Report(int pubId, int year, int month, 
            int service, int history, String group) {
        
        this.pubId = pubId;
        this.year = year;
        this.month = month;      
        this.history = history;
        this.service = service;
        this.group = group;
        
    }
    
    //Nauji Report su this.group.
    
    public Report(int year, int month, int service,
            int history, String group) {
        
        this.year = year;
        this.month = month;
        this.history = history;
        this.service = service;
        this.group = group;
    }
    
    public Report(int id, int publication, int video, double hour,
            int returns, int studies, int service) {
        //Sis kodas naudojamas ruosiant ataskaita filialui
        this.pubId = id;
        this.publication = publication;
        this.video = video;
        this.hour = hour;
        this.returnVisit = returns;
        this.study = studies;
        this.service = service;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String name) {
        this.fullName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int group) {
        this.groupId = group;
    }


    public int getYear() {
        return year;
    }

 
    public void setYear(int year) {
        this.year = year;
    }


    public int getMonth() {
        return month;
    }


    public void setMonth(int month) {
        this.month = month;
    }


    public int getPubId() {
        
        return pubId;
    }


    public void setPubId(int pubId) {
        this.pubId = pubId;
    }


    public int getPublication() {
        return publication;
    }


    public void setPublication(int pulication) {
        this.publication = pulication;
    }


    public int getVideo() {
        return video;
    }


    public void setVideo(int video) {
        this.video = video;
    }


    public double getHour() {
        return hour;
    }


    public void setHour(double hour) {
        this.hour = hour;
    }


    public int getReturnVisit() {
        return returnVisit;
    }

 
    public void setReturnVisit(int returnVisit) {
        this.returnVisit = returnVisit;
    }


    public int getStudy() {
        return study;
    }


    public void setStudy(int study) {
        this.study = study;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


    public int getHistory() {
        return history;
    }


    public void setHistory(int history) {
        this.history = history;
    }

    
    public int getService() {
        return service;
    }


    public void setService(int service) {
        this.service = service;
    }
    
    
    
    public String toStringMaindDataWithoutId() {
        return this.year + "." + this.month + ": tarnyste- " + 
                this.service + ", istorija- " + this.history + ", grupe: " + this.group;
    }
    
    public String toStringMaindDataWithId() {
        return this.getPubId() + ": " + this.year + "." + this.month + ": tarnyste- " + 
                this.service + ", istorija- " + this.history + ", grupe: " + this.group;
    }
    
    public String toStringFull() {
        return this.id + "-" + this.fullName + "(" + this.pubId + ")" + ": " + ": tarnyste- " + 
                this.service + ", istorija- "  + ", grupe: " + this.group + this.history + "\nLeid.: " + this.publication + ", siuz.: " +
                this.video + ", val.: " + this.hour + ", apl.: " + this.returnVisit + ", st.: " + this.study +
                ", pastabos: " + this.note;
    }
    
    
}

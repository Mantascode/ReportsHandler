
package com.mycompany.ataskaituapp.congregationReportwindow.domain;


public class Sum {
    
    private int publication;
    private int video;
    private double hour;
    private int returnV;
    private int study;
    private int service;
    private double quater;
    
    public Sum(int p, int v, double h, int r, int st, int s, double q) {
        this.publication = p;
        this.video = v;
        this.hour = h;
        this.returnV = r;
        this.study = st;
        this.service = s;
        this.quater = q;
    }

    public int getPublication() {
        return publication;
    }

    public void setPublication(int publication) {
        this.publication = publication;
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

    public int getReturnV() {
        return returnV;
    }

    public void setReturnV(int returnV) {
        this.returnV = returnV;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }
    
    public int getService() {
        return service;
    }

    public void setStervice(int service) {
        this.service = service;
    }
    

    public double getQuater() {
        return quater;
    }

    public void setQuater(double quater) {
        this.quater = quater;
    }
    
    public String toString() {
        return "pub: " + this.publication + ", vid: " + this.video +
                ", hours: " + this.hour + ", ret: " + this.returnV +
                ", st: " + this.study + ", ser: " + this.service + 
                ", q: " + this.quater;
    }
}


package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.util.ArrayList;


public class CountReportForBranch {
    
    private ArrayList<Report> list; 
    private int publications;
    private int videos;
    private int hours;
    private int returns;
    private int studies;
    private int year;
    private int month;
    
    public CountReportForBranch(ArrayList<Report> list, int year, int month) {
        this.list = list;
        this.publications = 0;
        this.videos = 0;
        this.hours = 0;
        this.returns = 0;
        this.studies = 0;
        this.year = year;
        this.month = month;
    }
    
    public void count() {
        
        if(!list.isEmpty()) {
            for(Report rep: list) {
                this.publications += rep.getPublication();
                this.videos += rep.getVideo();
                this.hours += rep.getHour();
                this.returns += rep.getReturnVisit();
                this.studies += rep.getStudy();
            }
        }  
    }
    
    private int workWithDoubles(double d) {
        
        String[] arr = String.valueOf(d).split("\\.");
        int[] intArr = new int[2];
        intArr[0] = Integer.parseInt(arr[0]);
        intArr[1] = Integer.parseInt(arr[1]);
        
        if(intArr[1] == 0) {
            return intArr[0];
        } else {
            return 0;
        }
    } 

    public int getPublications() {
        return publications;
    }

    public int getVideos() {
        return videos;
    }

    public int getHours() {
        return hours;
    }

    public int getReturns() {
        return returns;
    }

    public int getStudies() {
        return studies;
    }
    
}

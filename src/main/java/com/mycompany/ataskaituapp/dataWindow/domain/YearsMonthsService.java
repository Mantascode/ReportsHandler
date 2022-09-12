
package com.mycompany.ataskaituapp.dataWindow.domain;


public class YearsMonthsService {
    
    private int year;
    private int month;
    private int service;
    private int id;
    
    public YearsMonthsService(int year, int month, int service, int id) {
        this.year = year;
        this.month = month;
        this.service = service;
        this.id = id;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public int getMonth() {
        return this.month;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public int getService() {
        return this.service;
    }
    
    public void setService(int service) {
        this.service = service;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }  
}


package com.mycompany.ataskaituapp.homePage.domain;


public class DataForNewDates {
    
    private int id;
    private int service;
    private int group;
    
    public DataForNewDates(int id, int service, int group) {
        this.id = id;
        this.service = service;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
    
    public String toString() {
        return "Id : " + this.id + ", group : " + this.group + ", service : "  + this.service;
    }
    
}

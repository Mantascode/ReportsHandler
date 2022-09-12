
package com.mycompany.ataskaituapp.dataWindow.domain;

import java.text.Collator;
import java.util.Collections;


public class Publisher implements Comparable<Publisher>{
    
    private int ID;
    private String name;
    private String lastName;
    private int sex;
    private String birthDate;
    private String baptiseDate;
    private int hope;
    private int elder;
    private int ministerial;
    private int service;
    private String group;
    private int active;
    
    private int groupId;


    public Publisher(int ID, String name, String lastName, int sex, String birthDate, String baptiseDate, int hope, int elder, int ministerial, int service, String group, int active) {       
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.baptiseDate = baptiseDate;
        this.hope = hope;
        this.elder = elder;
        this.ministerial = ministerial;
        this.service = service;
        this.group = group;
        this.active = active;
    }
    
    public Publisher(String name, String lastName, int sex, String birthDate, String baptiseDate, int hope, int elder, int ministerial, int service, String group, int active) {
        this(-1, name, lastName, sex, birthDate, baptiseDate, hope, elder, ministerial, service, group, active);
    }
    
    
    public String getKey() {
        return lastName + " " + name;
    }
    
    public int getId() {
        return this.ID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLastName() {
        return lastName;
    }

 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

 
    public int getSex() {
        return sex;
    }


    public void setSex(int sex) {
        this.sex = sex;
    }


    public String getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public String getBaptiseDate() {
        return baptiseDate;
    }

 
    public void setBaptiseDate(String baptiseDate) {
        this.baptiseDate = baptiseDate;
    }


    public int getHope() {
        return hope;
    }


    public void setHope(int hope) {
        this.hope = hope;
    }


    public int getService() {
        return service;
    }

 
    public void setService(int service) {
        this.service = service;
    }


    public int getElder() {
        return elder;
    }


    public void setElder(int elder) {
        this.elder = elder;
    }

    public int getMinisterial() {
        return ministerial;
    }

    public void setMinisterial(int ministerial) {
        this.ministerial = ministerial;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    

    public String toString() {
        return this.ID + ": " + this.name + " " + this.lastName + ", lytis: "
               + this.sex + ", gim. data: " + this.birthDate + ", gim. data: "
               + this.baptiseDate + ", viltis: " + this.hope + ", vyr: " + this.elder + ", patar: "
               + this.ministerial + ", tarnyste: " + this.service + ", grupe: " + this.group
               + "akvytus: " + this.active;
    }
    
    public int compareTo(Publisher c) {
// Sis kodas rusiuoja ne pagal vietine abc
//        return this.getKey().compareTo(c.getKey());

// Sis kodas rusiuoja pagal vietine abc
        return Collator.getInstance().compare(getKey(), c.getKey());
    }
    
    
}

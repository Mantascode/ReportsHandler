
package com.mycompany.ataskaituapp.settings.domain;

import java.text.Collator;


public class ServiceGroup  implements Comparable<ServiceGroup>{
    
    private int id;
    private String name;
    
    public ServiceGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public ServiceGroup(String name) {
        this(-1, name);
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(ServiceGroup o) {
         return Collator.getInstance().compare(getName(), o.getName());
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}

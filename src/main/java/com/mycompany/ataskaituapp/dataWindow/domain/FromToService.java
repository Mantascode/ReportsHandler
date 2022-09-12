
package com.mycompany.ataskaituapp.dataWindow.domain;
    

public class FromToService {
    
    private int fromYear;
    private int fromMonth;
    private int toYear;
    private int toMonth;
    private int service;
    private int history;
    private String group;
    
    public FromToService(int yearFrom, int monthFrom) {
        this.fromYear = yearFrom;
        this.fromMonth = monthFrom;  
    }
    
    public FromToService(int yearFrom, int monthFrom, int toYear, int toMonth,
            int history, int service, String group) {
        this.fromYear = yearFrom;
        this.fromMonth = monthFrom;  
        this.toYear = toYear ;
        this.toMonth = toMonth;
        this.history = history;
        this.service = service; 
        this.group = group;
    }

    /**
     * @return the fromYear
     */
    public int getFromYear() {
        return fromYear;
    }

    /**
     * @param fromYear the fromYear to set
     */
    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    /**
     * @return the fromMonth
     */
    public int getFromMonth() {
        return fromMonth;
    }

    /**
     * @param fromMonth the fromMonth to set
     */
    public void setFromMonth(int fromMonth) {
        this.fromMonth = fromMonth;
    }

    /**
     * @return the toYear
     */
    public int getToYear() {
        return toYear;
    }

    /**
     * @param toYear the toYear to set
     */
    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    /**
     * @return the toMonth
     */
    public int getToMonth() {
        return toMonth;
    }

    /**
     * @param toMonth the toMonth to set
     */
    public void setToMonth(int toMonth) {
        this.toMonth = toMonth;
    }

    /**
     * @return the service
     */
    public int getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(int service) {
        this.service = service;
    }

    /**
     * @return the history
     */
    public int getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(int history) {
        this.history = history;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }
    
    public String toString() {
        return "From " + this.fromYear + "." + this.fromMonth + " To: " 
                + this.toYear + "." + this.toMonth + " - service: " +
                this.service + ", group: " + this.group + ", history: " 
                + this.history;
    }
    
}

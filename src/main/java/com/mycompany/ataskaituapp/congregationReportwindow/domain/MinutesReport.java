
package com.mycompany.ataskaituapp.congregationReportwindow.domain;


public class MinutesReport {
    
    private int rowId;
    private double minutes;
    private double totalMinutes;
    
    public MinutesReport(int rowId, double minutes, double totalMinutes) {
        this.rowId = rowId;
        this.minutes = minutes;
        this.totalMinutes = totalMinutes;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public double getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(double totalMintues) {
        this.totalMinutes = totalMintues;
    }
    
    public String toString() {
        return "rowId: " + this.rowId + ", minutes: " + this.minutes + ", totalMinutes: " + this.totalMinutes;
    }
    
}

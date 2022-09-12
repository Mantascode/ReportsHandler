

package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.text.DecimalFormat;
import javafx.collections.ObservableList;


public class CountingSumAverage {
    
    private ObservableList<Report> list;
    
    public CountingSumAverage(ObservableList<Report> list) {
        this.list = list;
    }
    
    public int sumPublication() {
        int sum = 0;
        
        for(Report rep: this.list) {
            sum += rep.getPublication();
        }
        
        return sum;
    }
    
    public int sumVideo() {
        int sum = 0;
        
        for(Report rep: this.list) {
            sum += rep.getVideo();
        }
        
        return sum;
    }
    
    public double sumHour() {
        double sum = 0;
        
        for(Report rep: this.list) {
            sum += rep.getHour();
        }
        
        return sum;
    }
    
    public int sumReturnVisit() {
        int sum = 0;
        
        for(Report rep: this.list) {
            sum += rep.getReturnVisit();
        }
        
        return sum;
    }
    
    public int sumStudy() {
        int sum = 0;
        
        for(Report rep: this.list) {
            sum += rep.getStudy();
        }
        
        return sum;
    }
    
    public String avPublication() {
        
        int div = this.list.size();
        
        if(div == 0) {
            return "null";
        } else {
            return new DecimalFormat("##.##").format(Double.valueOf(sumPublication()) / div);
        }

    }
    
    public String avVideo() {
        
        int div = this.list.size();
        
        if(div == 0) {
            return "null";
        } else {
            return new DecimalFormat("##.##").format(Double.valueOf(sumVideo()) / div);
        }

    }
    
    public String avHour() {
        
        int div = this.list.size();
        
        if(div == 0) {
            return "null";
        } else {
            return new DecimalFormat("##.##").format(Double.valueOf(sumHour()) / div);
        }
        
    }
    
    public String avReturnVisit() {
        
        int div = this.list.size();
        
        if(div == 0) {
            return "null";
        } else {
            return new DecimalFormat("##.##").format(Double.valueOf(sumReturnVisit()) / div);
        }

    }
    
    public String avStudy() {
        
        int div = this.list.size();
        
        if(div == 0) {
            return "null";
        } else {
            return new DecimalFormat("##.##").format(Double.valueOf(sumStudy()) / div);
        }
    }
    
    
}


package com.mycompany.ataskaituapp.congregationReportwindow.domain;

import java.util.ArrayList;


public class SummaryReport {
    
    private ArrayList<Sum> list;
    
    private int pubCount;
    private int pubPubl;
    private int pubVid;
    private int pubHour;
    private int pubRet;
    private int pubSt;
    
    private int auxCount;
    private int auxPubl;
    private int auxVid;
    private int auxHour;
    private int auxRet;
    private int auxSt;
    
    private int pioCount;
    private int pioPubl;
    private int pioVid;
    private int pioHour;
    private int pioRet;
    private int pioSt;
    
    public SummaryReport(ArrayList<Sum> list) {       
        this.list = list;
        
        this.pubCount = 0;
        this.pubPubl = 0;
        this.pubVid = 0;
        this.pubHour = 0;
        this.pubRet = 0;
        this.pubSt = 0;

        this.auxCount = 0;
        this.auxPubl = 0;
        this.auxVid = 0;
        this.auxHour = 0;
        this.auxRet = 0;
        this.auxSt = 0;

        this.pioCount = 0;
        this.pioPubl = 0;
        this.pioVid = 0;
        this.pioHour = 0;
        this.pioRet = 0;
        this.pioSt = 0;
        
    }
    
    public void count() {
        
        ArrayList<Sum> pubList = new ArrayList();
        ArrayList<Sum> auxList = new ArrayList();
        ArrayList<Sum> pioList = new ArrayList();
        
        for(Sum pub: this.list) {
            
            if(pub.getService() == 1) {
                pubList.add(pub);
            }
            
            if(pub.getService() == 2) {
                auxList.add(pub);
            }
            
            if(pub.getService() == 3) {
                pioList.add(pub);
            }
        }
        
        for(Sum pub: pubList) {
            this.pubCount++; 
            this.pubPubl += pub.getPublication();
            this.pubVid += pub.getVideo();
            if(pub.getHour() < 1) {
                this.pubHour += checkQuater(pub);
            } else {
                this.pubHour += pub.getHour();
            }
            this.pubRet += pub.getReturnV();
            this.pubSt += pub.getStudy();
        }
        
        for(Sum pub: auxList) {
            this.auxCount++;
            this.auxPubl += pub.getPublication();
            this.auxVid += pub.getVideo();
            if(pub.getHour() < 1) {
                this.auxHour += checkQuater(pub);
            } else {
                this.auxHour += pub.getHour();
            }
            this.auxRet += pub.getReturnV();
            this.auxSt += pub.getStudy();
        }
        
        for(Sum pub: pioList) {
            this.pioCount++;
            this.pioPubl += pub.getPublication();
            this.pioVid += pub.getVideo();
            if(pub.getHour() < 1) {
                this.pioHour += checkQuater(pub);
            } else {
                this.pioHour += pub.getHour();
            }
            this.pioRet += pub.getReturnV();
            this.pioSt += pub.getStudy();
        }
        
        
    }
    
    private int checkQuater(Sum pub) {
        
        String quaterquaterFirstPart = String.valueOf(pub.getQuater()).split("\\.")[0];

        double valueBefore = pub.getQuater() - pub.getHour();
        
        String quaterquaterFirstPartBefore = String.valueOf(valueBefore).split("\\.")[0];
        
        if(quaterquaterFirstPart.equals(quaterquaterFirstPartBefore)) {
            return 0;
        } else {
            return 1;
        }
        
    }

    public int getPubPubl() {
        return pubPubl;
    }

    public int getPubVid() {
        return pubVid;
    }

    public int getPubHour() {
        return pubHour;
    }

    public int getPubRet() {
        return pubRet;
    }

    public int getPubSt() {
        return pubSt;
    }

    public int getAuxPubl() {
        return auxPubl;
    }

    public int getAuxVid() {
        return auxVid;
    }

    public int getAuxHour() {
        return auxHour;
    }

    public int getAuxRet() {
        return auxRet;
    }

    public int getAuxSt() {
        return auxSt;
    }

    public int getPioPubl() {
        return pioPubl;
    }

    public int getPioVid() {
        return pioVid;
    }

    public int getPioHour() {
        return pioHour;
    }

    public int getPioRet() {
        return pioRet;
    }

    public int getPioSt() {
        return pioSt;
    }
    
    public int getPubCount() {
        return pubCount;
    }
    
    public int getAuxCount() {
        return auxCount;
    }
    
    public int getPioCount() {
        return pioCount;
    }
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.Date;

/**
 *
 * @author lev
 */
public class VisitsPerPatient {
    private String patientName;
    private int numberOfVisits;

    
    private Date lastVisitDate;
    
    public VisitsPerPatient(){
        numberOfVisits = 0;
        lastVisitDate = new Date();
        lastVisitDate.setTime(0);
    }
    
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        if(this.lastVisitDate.getTime() < lastVisitDate.getTime()){
            this.lastVisitDate.setTime(lastVisitDate.getTime());
        }
    }
}

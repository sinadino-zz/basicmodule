/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.Date;

/**
 *
 * @author redyoonnk1
 */
public class ViewsPerPatient {
    private String patientName;
    private int numberOfViews;
    private Date lastAccessDate;
    
    public ViewsPerPatient(){
        numberOfViews = 0;
        lastAccessDate = new Date();
        lastAccessDate.setTime(0);
    }
    
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        if(this.lastAccessDate.getTime() < lastAccessDate.getTime())
            this.lastAccessDate.setTime(lastAccessDate.getTime());
    }
    
    
}

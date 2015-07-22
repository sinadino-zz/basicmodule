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
public class AddsPerUser {
    private String username;
    private int patientsAdded;
    private Date lastAddDate;

    public AddsPerUser(){
        patientsAdded = 0;
        lastAddDate = new Date();
        lastAddDate.setTime(0);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPatientsAdded() {
        return patientsAdded;
    }

    public void setPatientsAdded(int patientsAdded) {
        this.patientsAdded = patientsAdded;
    }

    public Date getLastAddDate() {
        return lastAddDate;
    }

    public void setLastAddDate(Date lastAddDate) {
        if(this.lastAddDate.getTime() < lastAddDate.getTime())
            this.lastAddDate.setTime(lastAddDate.getTime());
    }
    
    
}

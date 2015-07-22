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
public class EncountersPerPatient {
    private String patientName;
    private int numberOfEncounters;
    private Date lastEncounterDate;
    
    public EncountersPerPatient(){
        numberOfEncounters = 0;
        lastEncounterDate = new Date();
        lastEncounterDate.setTime(0);
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getNumberOfEncounters() {
        return numberOfEncounters;
    }

    public void setNumberOfEncounters(int numberOfEncounters) {
        this.numberOfEncounters = numberOfEncounters;
    }

    public Date getLastEncounterDate() {
        return lastEncounterDate;
    }

    public void setLastEncounterDate(Date lastEncounterDate) {
        if(this.lastEncounterDate.getTime() < lastEncounterDate.getTime()){
            this.lastEncounterDate.setTime(lastEncounterDate.getTime());
        }
    }
    
    
}

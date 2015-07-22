/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.Date;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;
import org.openmrs.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 *
 * @author lev
 */
public class ActivityRecord extends BaseOpenmrsData{
    private Integer id;
    private Patient patient;
    private User user;
    private Date accessDate;
    
    public static long RECORD_ACCESS_MINIMAL_TIME_GAP = 5*60*1000; //5 minutes

    public ActivityRecord() {
    }

    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer intgr) {
        this.id = intgr;
    }
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patientAccessed) {
        this.patient = patientAccessed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }
    
    public static List<ActivityRecord> filterByUser(List<ActivityRecord> source, User user){
        ArrayList<ActivityRecord> result = new ArrayList<ActivityRecord>();
        for(ActivityRecord ar : source){
            if(ar.getUser().getUserId() == user.getUserId()){
                result.add(ar);
            }
        }
        
        Collections.sort(result, new Comparator<ActivityRecord>(){  
            @Override
            public int compare(ActivityRecord o1, ActivityRecord o2) {
                return o1.getAccessDate().getTime() < o2.getAccessDate().getTime()? 1 : o1.getAccessDate().getTime() == o2.getAccessDate().getTime()? 0 : -1;
                        //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
            }
        });
        
        return result;
    }
    
    public static List<ActivityRecord> filterByPatient(List<ActivityRecord> source, Patient patient){
        ArrayList<ActivityRecord> result = new ArrayList<ActivityRecord>();
        for(ActivityRecord ar : source){
            if(ar.getPatient().getPatientId() == patient.getPatientId()){
                result.add(ar);
            }
        }
        
        Collections.sort(result, new Comparator<ActivityRecord>(){
            @Override
    public int compare(ActivityRecord o1, ActivityRecord o2) {
        return o1.getAccessDate().compareTo(o2.getAccessDate());
    }
        });
        
        return result;
    }
    
    
}

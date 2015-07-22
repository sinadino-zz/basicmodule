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
public class UserActivity extends BaseOpenmrsData{
    private Integer id;
    private Patient patient;
    private User user;
    private Date accessDate;
    private Integer activityType;
    
    public static long RECORD_ACCESS_MINIMAL_TIME_GAP = 5*60*1000; //5 minutes
    public static long RECORD_ACCESS_MINIMAL_TIME_VISIT_ENCOUNTER = 3*1000; //3 seconds

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public UserActivity() {
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
    
    public static List<UserActivity> filterByUser(List<UserActivity> source, User user){
        ArrayList<UserActivity> result = new ArrayList<UserActivity>();
        for(UserActivity ar : source){
            if(ar.getUser().getUserId() == user.getUserId()){
                result.add(ar);
            }
        }
        
        Collections.sort(result, new Comparator<UserActivity>(){  
            @Override
            public int compare(UserActivity o1, UserActivity o2) {
                return o1.getAccessDate().getTime() < o2.getAccessDate().getTime()? 1 : o1.getAccessDate().getTime() == o2.getAccessDate().getTime()? 0 : -1;
                        //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
            }
        });
        
        return result;
    }
    
    public static List<UserActivity> filterByPatient(List<UserActivity> source, Patient patient){
        ArrayList<UserActivity> result = new ArrayList<UserActivity>();
        for(UserActivity ar : source){
            if(ar.getPatient().getPatientId() == patient.getPatientId()){
                result.add(ar);
            }
        }
        
        Collections.sort(result, new Comparator<UserActivity>(){
            @Override
    public int compare(UserActivity o1, UserActivity o2) {
        return o1.getAccessDate().compareTo(o2.getAccessDate());
    }
        });
        
        return result;
    }
    
    public UserActivityContainer getContainer(){
        return new UserActivityContainer(activityType,
                accessDate,
                UserActivity.getFullPatientName(patient),
                UserActivity.getFullUserName(user)
                );
    }
    
    public static String getFullPatientName(Patient p){
        String patientName = "";
        if(p.getGivenName() != null){
                        patientName += p.getGivenName() + " ";
                    }
                    if(p.getMiddleName() != null){
                        patientName += p.getMiddleName() + " ";
                    }
                    if(p.getFamilyName() != null){
                        patientName += p.getFamilyName();
                    }
        return patientName;
    }
    
    public static String getFullUserName(User u){
            //users have no middle name
        String userName = "";
        if(u.getGivenName() != null){
                        userName += u.getGivenName() + " ";
                    }
                    if(u.getFamilyName() != null){
                        userName += u.getFamilyName();
                    }
        return userName;
    }
    
}

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
public class UserActivityContainer {
    private Integer activityType;
    private Date activityDate;
    private String userName;
    private String patientName;
    
    public UserActivityContainer(Integer type, Date date, String un, String pn){
        activityType = type;
        activityDate = date;
        userName = un;
        patientName = pn;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    
    
}

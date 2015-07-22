/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.Date;
import java.util.List;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;

/**
 *
 * @author lev
 */
public interface UserActivityService extends OpenmrsService{
    public UserActivity saveUserActivity(UserActivity ua);
    public UserActivity getUserActivity(Integer id);
    public List<UserActivity> getRecordsByPatient(Patient p);
    public List<UserActivity> getRecordsByUser(User u);
    
    public int[] getPatientViewsByMonthYear(int month, int year);
    public List<ViewsPerPatient> getFrequentlyViewedPatients();
    public List<ViewsPerPatient> getRecentlyViewedPatients();
    public int[] getPatientAddsByMonthYear(int month, int year);
    public List<ViewsPerPatient> getRecentlyAddedPatients();
    public List<AddsPerUser> getMostActivelyAddingUsers();
    //public List<UserActivity> getRecordsByActivityType(ActivityType at);
    public List<int[]> getAggregateActivityNumbers(int month, int year);
    public List<UserActivityContainer> getMostRecentActivities();
    public int[] getPatientVisitsByMonthYear(int month, int year);
    public List<VisitsPerPatient> getMostFrequentlyVisitedPatients();
    public int[] getPatientEncountersByMonthYear(int month, int year);
    public List<EncountersPerPatient> getMostFrequentlyEncounteredPatients();
}

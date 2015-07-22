/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule.db;

import java.util.Date;
import java.util.List;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.basicmodule.AddsPerUser;
import org.openmrs.module.basicmodule.EncountersPerPatient;
import org.openmrs.module.basicmodule.UserActivity;
import org.openmrs.module.basicmodule.UserActivityContainer;
import org.openmrs.module.basicmodule.ViewsPerPatient;
import org.openmrs.module.basicmodule.VisitsPerPatient;

/**
 *
 * @author lev
 */
public interface UserActivityDAO {
    
    UserActivity getUserActivity(Integer id);
    List<UserActivity> getUserActivitysByPatient(Patient patient);   
    List<UserActivity> getUserActivitysByUser(User user);
    UserActivity saveUserActivity(UserActivity note);   
    int[] getPatientViewsByMonthYear(int month, int year);   
    List<ViewsPerPatient> getFrequentlyViewedPatients();   
    List<ViewsPerPatient> getRecentlyViewedPatients(); 
    int[] getPatientAddsByMonthYear(int month, int year);
    List<ViewsPerPatient> getRecentlyAddedPatients();
    List<AddsPerUser> getMostActivelyAddingUsers();
    
    List<int[]> getAggregateActivityNumbers(int month, int year);
    List<UserActivityContainer> getMostRecentActivities();
    int[] getPatientVisitsByMonthYear(int month, int year);
    List<VisitsPerPatient> getMostFrequentlyVisitedPatients();
    int[] getPatientEncountersByMonthYear(int month, int year);
    List<EncountersPerPatient> getMostFrequentlyEncounteredPatients();
}

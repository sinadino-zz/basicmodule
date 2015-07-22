/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmrs.module.basicmodule.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.basicmodule.ActivityType;
import org.openmrs.module.basicmodule.ActivityTypeService;
import org.openmrs.module.basicmodule.AddsPerUser;
import org.openmrs.module.basicmodule.EncountersPerPatient;
import org.openmrs.module.basicmodule.UserActivity;
import org.openmrs.module.basicmodule.UserActivityContainer;
import org.openmrs.module.basicmodule.UserActivityService;
import org.openmrs.module.basicmodule.ViewsPerPatient;
import org.openmrs.module.basicmodule.VisitsPerPatient;
import org.openmrs.module.basicmodule.db.hibernate.HibernateUserActivityDAO;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportsController {
    
    /** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping("module/basicmodule/reports")
	public void showReport(ModelMap model){
            System.out.println("Reports Controller showForm method***************");
            
                //HibernateUserActivityDAO actObj = new HibernateUserActivityDAO();
                
                //int[] stats = actObj.getPatientViewsByMonthYear(4, 2014);
                
            UserActivityService uaservice = Context.getService(UserActivityService.class);
            int[] stats = uaservice.getPatientViewsByMonthYear(5, 2014);
                //int[] stats = {12,54,65,87,45,98,65,54};
            
            List<ViewsPerPatient> vppList = uaservice.getFrequentlyViewedPatients();
            List<ViewsPerPatient> rvpList = uaservice.getRecentlyViewedPatients();
                
            // push the data in map 
		model.put("stats", stats);
                model.put("vpplist",vppList);
                model.put("rvplist",rvpList);
                
              
	}
        
        @RequestMapping(value="module/basicmodule/reports", method=RequestMethod.POST)
	public void showReportPost(ModelMap model, @RequestParam("statMonth") int month, @RequestParam("statYear") int year){
            System.out.println("Reports Controller showForm method***************");
            
                //HibernateUserActivityDAO actObj = new HibernateUserActivityDAO();
                
                //int[] stats = actObj.getPatientViewsByMonthYear(4, 2014);
                
            UserActivityService uaservice = Context.getService(UserActivityService.class);
            int[] stats = uaservice.getPatientViewsByMonthYear(month, year);
                //int[] stats = {12,54,65,87,45,98,65,54};
            
            List<ViewsPerPatient> vppList = uaservice.getFrequentlyViewedPatients();
            List<ViewsPerPatient> rvpList = uaservice.getRecentlyViewedPatients();
                
            // push the data in map 
		model.put("stats", stats);
                model.put("vpplist",vppList);
                model.put("rvplist",rvpList);
                
              
	}
        
        
        @RequestMapping("module/basicmodule/reportPatientsAdded")
	public void showPatientAdds(ModelMap model){
            System.out.println("Reports Controller showPatientsAdded method***************");
            
            UserActivityService uaservice = Context.getService(UserActivityService.class);
            int[] stats = uaservice.getPatientAddsByMonthYear(5, 2014);
                //int[] stats = {12,54,65,87,45,98,65,54};
            
           
            List<ViewsPerPatient> rapList = uaservice.getRecentlyAddedPatients();
            List<AddsPerUser> appList = uaservice.getMostActivelyAddingUsers();    
            // push the data in map 
		model.put("stats", stats);
                model.put("applist",appList);
                model.put("raplist",rapList);
                
	}
        
        @RequestMapping("module/basicmodule/userActions")
	public void showUserActions(ModelMap model){
            System.out.println("Reports Controller showUserActions method***************");
            
            UserActivityService uaservice = Context.getService(UserActivityService.class);
            List<int[]> stats = uaservice.getAggregateActivityNumbers(5, 2014);
            
            List<UserActivityContainer> rapList = uaservice.getMostRecentActivities();
               
            
            // push the data in map 
		model.put("stats", stats);
               
                model.put("ralist",rapList);
	}
        
        @RequestMapping("module/basicmodule/patientVisits")
	public void showPatientVisits(ModelMap model){
            System.out.println("Reports Controller showPatientVisits method***************");
            
            UserActivityService uaservice = Context.getService(UserActivityService.class);
            int[] stats = uaservice.getPatientVisitsByMonthYear(5, 2014);
            List<VisitsPerPatient> visitList = uaservice.getMostFrequentlyVisitedPatients();
            
            model.put("stats", stats);
            model.put("vpplist", visitList);
                
	}
        
        @RequestMapping("module/basicmodule/patientEncounters")
	public void showPatientEncounters(ModelMap model){
            System.out.println("Reports Controller showPatientEncounters method***************");
        
                UserActivityService uaservice = Context.getService(UserActivityService.class);
                int[] stats = uaservice.getPatientEncountersByMonthYear(5, 2014);
                List<EncountersPerPatient> eppList = uaservice.getMostFrequentlyEncounteredPatients();
                
                model.put("stats", stats);
                model.put("epplist", eppList);
}
        
}

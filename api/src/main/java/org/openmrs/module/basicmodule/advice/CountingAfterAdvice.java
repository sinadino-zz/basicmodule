package org.openmrs.module.basicmodule.advice;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.Patient;
import org.openmrs.Encounter;
import org.springframework.aop.AfterReturningAdvice;
import java.util.List;
import org.openmrs.User;
import org.openmrs.module.basicmodule.ActivityType;
import org.openmrs.module.basicmodule.ActivityTypeService;
import org.openmrs.module.basicmodule.AddsPerUser;
import org.openmrs.module.basicmodule.UserActivity;
import org.openmrs.module.basicmodule.UserActivityService;
import org.openmrs.module.basicmodule.ViewsPerPatient;


/*
Refer to https://wiki.openmrs.org/display/docs/OpenMRS+AOP
*/
public class CountingAfterAdvice implements AfterReturningAdvice {

    private Log log = LogFactory.getLog(this.getClass());
    private int count = 0;    
    private List<ActivityType> activityTypes = Context.getService(ActivityTypeService.class).getAllActivityTypes();
    private static Patient p = new Patient();

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.debug("Method: " + method.getName() + ". After advice called " + (++count) + " time(s) now.");
        String userName = Context.getUserContext().getAuthenticatedUser().getFamilyName();
        System.out.println("AOP - by: " + userName + " Method: " + method.getName() + ". After advice called " + (++count) + " time(s) now.");
        
        if (returnValue == null){
            createNewUserActivity(this.p, method);
        }
        else if (returnValue != null) { 
            
            if (returnValue.getClass() == Patient.class) {
                Patient p = (Patient) returnValue;
                System.out.println("Patient: " + p.getFamilyName());
                
                createNewUserActivity(p, method);
            }
            if (returnValue.getClass() == Encounter.class) {
                Encounter enc = (Encounter)returnValue;
                Patient p = enc.getPatient();
                System.out.println(" Patient: " + p.getFamilyName());
                
                createNewUserActivity(p, method);
            }
            
            if (method.getName().contains("save")
                    || method.getName().contains("void")
                    || method.getName().contains("unvoid")
                    || method.getName().contains("discontinue")
                    || method.getName().contains("purge")
                    || method.getName().contains("end")){
               createNewUserActivity(this.p, method);
            }
        }

        /*
        Other useful services to monitor include PersonService (create new persons (patients), OrderService (lab/drug orders),
        VisitService
        Also, check usage statistics module for other logging information
        
        App to use for creating charts: http://dvillela.servehttp.com:4000/apostilas/jfreechart_tutorial.pdf
        */
        }
    
    
    
    public void createNewUserActivity(Patient p, Method method){
        ActivityType at = ActivityType.getActivityContainingMethod(activityTypes, method);
        if(at==null){
            return;
        }
        
        User currentUser = Context.getUserContext().getAuthenticatedUser();
        Date currDate = new Date();
        UserActivityService uaservice = Context.getService(UserActivityService.class);
        
        if(at.getId() == 1 || at.getId() == 5 || at.getId() == 3){ //if it is patient view/visit or encounter type
            this.p.setPatientId(p.getPatientId());//set instance p to the local p, since edit patient has no patient return value. The patient return value will be the previous patient viewed.
            List<UserActivity> existingRecords = uaservice.getRecordsByPatient(p);
            existingRecords = UserActivity.filterByUser(existingRecords, currentUser);

            if(!existingRecords.isEmpty()){
                UserActivity lastRecord = existingRecords.get(0);
                long diff = currDate.getTime() - lastRecord.getAccessDate().getTime();
                
                if(at.getId() == 1) {//if view
                    if(diff < UserActivity.RECORD_ACCESS_MINIMAL_TIME_GAP){//updates if previous record was within 5min
                        lastRecord.setAccessDate(currDate);
                        uaservice.saveUserActivity(lastRecord);
                        System.out.println("xxx: USER activity updated " + lastRecord.toString());
                        return;
                    }
                }
                else if (at.getId() == 5 || at.getId() == 3){//if visit or encounter
                    if(diff < UserActivity.RECORD_ACCESS_MINIMAL_TIME_VISIT_ENCOUNTER){//updates if previous record was within 3secs
                        lastRecord.setAccessDate(currDate);
                        uaservice.saveUserActivity(lastRecord);
                        System.out.println("xxx: USER activity updated " + lastRecord.toString());
                        return;
                    }
                }
            }
        }
        
        UserActivity ua = new UserActivity();
        ua.setPatient(this.p);
        ua.setUser(currentUser);
        ua.setAccessDate(currDate);
        ua.setActivityType(at.getId());
        uaservice.saveUserActivity(ua);
        System.out.println("xxx: USER Activity inserted " + ua.toString());
    }

}

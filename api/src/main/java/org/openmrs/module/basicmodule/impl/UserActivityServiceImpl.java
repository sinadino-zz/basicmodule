package org.openmrs.module.basicmodule.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.basicmodule.AddsPerUser;
import org.openmrs.module.basicmodule.EncountersPerPatient;
import org.openmrs.module.basicmodule.UserActivity;
import org.openmrs.module.basicmodule.UserActivityContainer;
import org.openmrs.module.basicmodule.UserActivityService;
import org.openmrs.module.basicmodule.ViewsPerPatient;
import org.openmrs.module.basicmodule.VisitsPerPatient;
import org.openmrs.module.basicmodule.db.UserActivityDAO;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.basicmodule.impl.UserActivityServiceImpl;

/**
 * Implementation of our {@link UserActivityService} interface. This class is set in the
 * /metadata/moduleApplicationContext.xml file to be matched to the
 * {@link UserActivityService} interface. <br/>
 * <br/>
 * This class extends {@link BaseOpenmrsService} so that there are empty methods
 * for onStartup and onShutdown. This allows sheilds us from changes to the
 * OpenmrsService interface from forcing us to implement the methods. <br/>
 * <br/>
 * NEVER call "new UserActivityServiceImpl()....". To use this class, you do: <br/>
 * <code>
 *   
 *   Context.getService(UserActivityService.class).saveUserActivity(note)...
 *   
 * </code>
 * 
 */
public class UserActivityServiceImpl extends BaseOpenmrsService implements UserActivityService {

	/**
	 * This "dao" object is set by spring. See the
	 * /metadata/moduleApplicationContext.xml for what value gets set here. We
	 * can assume that this will never be null, even though it never gets set in
	 * here. This is called Inversion of Control (IoC)
	 */
	private UserActivityDAO dao;

	/**
	 * This is the method that spring calls to set the DAO
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(UserActivityDAO dao) {
		this.dao = dao;
	}

	/**
	 * @see org.openmrs.module.patientnotes.UserActivityService#getUserActivity(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public UserActivity getUserActivity(Integer id) {
		return dao.getUserActivity(id);
	}

	@Transactional(readOnly = true)
	public List<UserActivity> getRecordsByPatient(Patient patient) {
		return dao.getUserActivitysByPatient(patient);
	}
        
        @Transactional(readOnly = true)
	public List<UserActivity> getRecordsByUser(User user) {
		return dao.getUserActivitysByUser(user);
	}

	@Transactional
	public UserActivity saveUserActivity(UserActivity ua) {
		return dao.saveUserActivity(ua);
	}

        @Transactional(readOnly = true)
	public int[] getPatientViewsByMonthYear(int month, int year) {
		return dao.getPatientViewsByMonthYear(month, year);
	}
        
        @Transactional(readOnly = true)
	public List<ViewsPerPatient> getFrequentlyViewedPatients() {
		return dao.getFrequentlyViewedPatients();
	}
        
        @Transactional(readOnly = true)
	public List<ViewsPerPatient> getRecentlyViewedPatients() {
		return dao.getRecentlyViewedPatients();
	}
        
        @Transactional(readOnly = true)
	public int[] getPatientAddsByMonthYear(int month, int year) {
		return dao.getPatientAddsByMonthYear(month, year);
	}
        
        @Transactional(readOnly = true)
	public List<ViewsPerPatient> getRecentlyAddedPatients() {
		return dao.getRecentlyAddedPatients();
	}
        @Transactional(readOnly = true)
        public List<AddsPerUser> getMostActivelyAddingUsers(){
            		return dao.getMostActivelyAddingUsers();
        }
        
        @Transactional(readOnly = true)
        public List<int[]> getAggregateActivityNumbers(int month, int year){
            return dao.getAggregateActivityNumbers(month, year);
        }
        
        @Transactional(readOnly = true)
        public List<UserActivityContainer> getMostRecentActivities(){
            return dao.getMostRecentActivities();
        }
        
        @Transactional(readOnly = true)
        public int[] getPatientVisitsByMonthYear(int month, int year){
            return dao.getPatientVisitsByMonthYear(month, year);
        }
        
        @Transactional(readOnly = true)
        public List<VisitsPerPatient> getMostFrequentlyVisitedPatients(){
            return dao.getMostFrequentlyVisitedPatients();
        }
        
        @Transactional(readOnly = true)
        public int[] getPatientEncountersByMonthYear(int month, int year){
            return dao.getPatientEncountersByMonthYear(month, year);
        }
        
        @Transactional(readOnly = true)
        public List<EncountersPerPatient> getMostFrequentlyEncounteredPatients(){
            return dao.getMostFrequentlyEncounteredPatients();
        }
}

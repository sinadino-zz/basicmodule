package org.openmrs.module.basicmodule.db.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.basicmodule.AddsPerUser;
import org.openmrs.module.basicmodule.EncountersPerPatient;
import org.openmrs.module.basicmodule.UserActivity;
import org.openmrs.module.basicmodule.UserActivityContainer;
import org.openmrs.module.basicmodule.UserActivityService;
import org.openmrs.module.basicmodule.ViewsPerPatient;
import org.openmrs.module.basicmodule.VisitsPerPatient;
import org.openmrs.module.basicmodule.db.UserActivityDAO;

/**
 * This class should not be called directly. Instead, the {@link UserActivityService}
 * should be using this. A developer should do: <code>
 *   
 *   Context.getService(UserActivityService.class).saveUserActivity(note)...
 *   
 * </code>
 * 
 */
public class HibernateUserActivityDAO implements UserActivityDAO {

	private SessionFactory sessionFactory;

	/**
	 * This is a Hibernate object. It gives us metadata about the currently
	 * connected database, the current session, the current db user, etc. To
	 * save and get objects, calls should go through
	 * sessionFactory.getCurrentSession() <br/>
	 * <br/>
	 * This is called by Spring. See the /metadata/moduleApplicationContext.xml
	 * for the "sessionFactory" setting. See the applicationContext-service.xml
	 * file in CORE openmrs for where the actual "sessionFactory" object is
	 * first defined.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserActivity getUserActivity(Integer id) {
		return (UserActivity) sessionFactory.getCurrentSession().get(UserActivity.class, id);
	}

	public List<UserActivity> getUserActivitysByPatient(Patient patient) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				UserActivity.class);
		crit.add(Restrictions.eq("patient", patient));
		return crit.list();
	}
        
        public List<UserActivity> getUserActivitysByUser(User user) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				UserActivity.class);
		crit.add(Restrictions.eq("user", user));
		return crit.list();
	}

	public UserActivity saveUserActivity(UserActivity ar) {
		sessionFactory.getCurrentSession().saveOrUpdate(ar);
		return ar;
	}
        
        public int[] getPatientViewsByMonthYear(int month, int year) {
                //returns an array in which index 0 corresponds to day 1, index 1 to day 2 etc..
                year -= 1900;
                
                Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                                UserActivity.class);
                
                int[] viewsByDay = new int[31];//creates index 0-30.
                crit.add(Restrictions.eq("activityType", 1)); //filters for only patient views
                for(UserActivity ua : (List<UserActivity>)crit.list()){
                        if(ua.getAccessDate().getMonth()+1 == month && ua.getAccessDate().getYear() == year){
                            viewsByDay[ua.getAccessDate().getDate()-1]++; //matches the month and year and increments the corresponding index by 1
                        }
                }
                return viewsByDay;
	}
        
        public List<ViewsPerPatient> getFrequentlyViewedPatients(){
            List<ViewsPerPatient> vppList = new ArrayList();
            int patientId = 0;
            UserActivity usera, ua;
            ViewsPerPatient vpp;
            
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                                UserActivity.class);
              
            crit.add(Restrictions.eq("activityType", 1)); //filters for only patient views
            crit.addOrder(Order.asc("patient"));
            
            if(crit.list().isEmpty()){
                return vppList;
            }
            
            usera = (UserActivity)crit.list().get(0); //get the first entry
            vpp = new ViewsPerPatient(); 
            vppList.add(vpp);
            patientId = usera.getPatient().getId(); //this is the first id to compare against

            vpp.setNumberOfViews(vpp.getNumberOfViews()+1);
            vpp.setLastAccessDate(usera.getAccessDate());
            
            vpp.setPatientName(UserActivity.getFullPatientName(usera.getPatient()));
            
            for(int i=1; i<crit.list().size(); i++){
               ua = (UserActivity)crit.list().get(i);
               if (ua.getPatient().getId() != patientId){ //if patientId is different then previous stop counting and create new vpp
                   vpp = new ViewsPerPatient();
                   vppList.add(vpp);
                   patientId = ua.getPatient().getId();
                   
                   vpp.setNumberOfViews(vpp.getNumberOfViews()+1);
                   vpp.setLastAccessDate(ua.getAccessDate());
                   vpp.setPatientName(UserActivity.getFullPatientName(ua.getPatient()));
               }
               else{
                   vpp.setNumberOfViews(vpp.getNumberOfViews()+1);
               }
            }   
            
            Collections.sort(vppList, new Comparator<ViewsPerPatient>(){  //sort based on numbers of views in descending order 
            //@Override
                public int compare(ViewsPerPatient o1, ViewsPerPatient o2) {
                    return o1.getNumberOfViews() > o2.getNumberOfViews()? -1 : o1.getNumberOfViews() == o2.getNumberOfViews()? 0 : 1;
                            //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
                }
            });
            
            return vppList;
        }
        
        public List<ViewsPerPatient> getRecentlyViewedPatients(){
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                            UserActivity.class);
            crit.add(Restrictions.eq("activityType", 1));
            crit.addOrder(Order.desc("accessDate"));
            
            List<UserActivity> actList = crit.list();
            
            List<ViewsPerPatient> vppList = new ArrayList<ViewsPerPatient>();
            for(int i = 0; i < Math.min(actList.size(), 10); i++){
                UserActivity ua = actList.get(i);
                ViewsPerPatient vpp = new ViewsPerPatient();
                Patient p = ua.getPatient();
                vpp.setPatientName(UserActivity.getFullPatientName(p));
                vpp.setLastAccessDate(ua.getAccessDate());
                vppList.add(vpp);
            }
            
            return vppList;
        }
        
        public int[] getPatientAddsByMonthYear(int month, int year) {
                //returns an array in which index 0 corresponds to day 1, index 1 to day 2 etc..
                year -= 1900;
                
                Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                                UserActivity.class);
                
                int[] addsByDay = new int[31];//creates index 0-30.
                crit.add(Restrictions.eq("activityType", 3)); //filters for only patient views
                for(UserActivity ua : (List<UserActivity>)crit.list()){
                        if(ua.getAccessDate().getMonth()+1 == month && ua.getAccessDate().getYear() == year){
                            addsByDay[ua.getAccessDate().getDate()-1]++; //matches the month and year and increments the corresponding index by 1
                        }
                }
                return addsByDay;
	}
        
        public int[] getPatientVisitsByMonthYear(int month, int year) {
                //returns an array in which index 0 corresponds to day 1, index 1 to day 2 etc..
                year -= 1900;
                
                Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                                UserActivity.class);
                
                int[] visitsByDay = new int[31];//creates index 0-30.
                crit.add(Restrictions.eq("activityType", 4)); //filters for only patient visits
                for(UserActivity ua : (List<UserActivity>)crit.list()){
                        if(ua.getAccessDate().getMonth()+1 == month && ua.getAccessDate().getYear() == year){
                            visitsByDay[ua.getAccessDate().getDate()-1]++; //matches the month and year and increments the corresponding index by 1
                        }
                }
                return visitsByDay;
	}
        
        public List<ViewsPerPatient> getRecentlyAddedPatients(){
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                            UserActivity.class);
            crit.add(Restrictions.eq("activityType", 3));
            crit.addOrder(Order.desc("accessDate"));
            
            List<UserActivity> actList = crit.list();
            
            List<ViewsPerPatient> vppList = new ArrayList<ViewsPerPatient>();
            for(int i = 0; i < Math.min(actList.size(), 10); i++){
                UserActivity ua = actList.get(i);
                ViewsPerPatient vpp = new ViewsPerPatient();
                Patient p = ua.getPatient();
                vpp.setPatientName(UserActivity.getFullPatientName(p));
                vpp.setLastAccessDate(ua.getAccessDate());
                vppList.add(vpp);
            }
            
            return vppList;
        }
        
        public List<AddsPerUser> getMostActivelyAddingUsers(){
            List<AddsPerUser> apuList = new ArrayList();
            int userId = 0;
            UserActivity usera, ua;
            AddsPerUser apu;
            
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                                UserActivity.class);
              
            crit.add(Restrictions.eq("activityType", 3)); //filters for only patient adds
            crit.addOrder(Order.asc("user"));
            
            if(crit.list().size()==0){
                return apuList;
            }
            
            usera = (UserActivity)crit.list().get(0); //get the first entry
            apu = new AddsPerUser(); 
            apuList.add(apu);
            userId = usera.getUser().getId(); //this is the first id to compare against

            apu.setPatientsAdded(apu.getPatientsAdded()+1);
            apu.setLastAddDate(usera.getAccessDate());
            apu.setUsername(UserActivity.getFullUserName(usera.getUser()));
            
            for(int i=1; i<crit.list().size(); i++){
               ua = (UserActivity)crit.list().get(i);
               if (ua.getUser().getId() != userId){ //if userId is different then previous stop counting and create new apu
                   apu = new AddsPerUser();
                   apuList.add(apu);
                   userId = ua.getUser().getId();
                   
                   apu.setPatientsAdded(apu.getPatientsAdded()+1);
                   apu.setLastAddDate(ua.getAccessDate());
                   apu.setUsername(UserActivity.getFullUserName(ua.getUser()));
               }
               else{
                   apu.setPatientsAdded(apu.getPatientsAdded()+1);
               }
            }   
            
            Collections.sort(apuList, new Comparator<AddsPerUser>(){  //sort based on numbers of views in descending order 
            //@Override
                public int compare(AddsPerUser o1, AddsPerUser o2) {
                    return o1.getPatientsAdded() > o2.getPatientsAdded()? -1 : o1.getPatientsAdded() == o2.getPatientsAdded()? 0 : 1;
                            //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
                }
            });
            
            return apuList;
        }
        
        public List<int[]> getAggregateActivityNumbers(int month, int year){
            int[] patientViews = this.getPatientViewsByMonthYear(month, year);
            int[] patientAdds = this.getPatientAddsByMonthYear(month, year);
            
            int[] patientVisits = this.getPatientVisitsByMonthYear(month, year);
            
            List<int[]> aggregateList = new ArrayList<int[]>();
            for(int i = 0; i < patientViews.length; i++){
                int[] dayEntry = new int[3];
                
                dayEntry[0] = patientAdds[i];
                dayEntry[1] = patientVisits[i];
                dayEntry[2] = patientViews[i];
                
                aggregateList.add(dayEntry);
            }
            
            return aggregateList;
        }
        
        public List<UserActivityContainer> getMostRecentActivities(){
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserActivity.class);
            crit.addOrder(Order.desc("accessDate"));
            
            List<UserActivity> actList = crit.list();
            
            List<UserActivityContainer> acList = new ArrayList<UserActivityContainer>();
            for(int i = 0; i < Math.min(actList.size(), 10); i++){
                UserActivity ua = actList.get(i);
                acList.add(ua.getContainer());
            }
            
            return acList;
        }

        public List<VisitsPerPatient> getMostFrequentlyVisitedPatients() {
        List<VisitsPerPatient> vppList = new ArrayList();
        int patientID = 0;
        UserActivity usera, ua;
        VisitsPerPatient vpp;

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserActivity.class);

        crit.add(Restrictions.eq("activityType", 4)); //filters for only patient visits
        crit.addOrder(Order.asc("patient"));

        if(crit.list().isEmpty()){
            return vppList;
        }

        usera = (UserActivity)crit.list().get(0); //get the first entry
        vpp = new VisitsPerPatient(); 
        vppList.add(vpp);
        patientID = usera.getPatient().getId(); //this is the first id to compare against
        vpp.setNumberOfVisits(vpp.getNumberOfVisits() + 1);
        vpp.setLastVisitDate(usera.getAccessDate());
        vpp.setPatientName(UserActivity.getFullPatientName(usera.getPatient()));

        for(int i=1; i<crit.list().size(); i++){
           ua = (UserActivity)crit.list().get(i);
           if (ua.getPatient().getId() != patientID){ //if patientID is different then previous stop counting and create new vpp
               vpp = new VisitsPerPatient();
               vppList.add(vpp);
               patientID = ua.getPatient().getId();
               vpp.setNumberOfVisits(vpp.getNumberOfVisits() + 1);
               vpp.setLastVisitDate(ua.getAccessDate());
               vpp.setPatientName(UserActivity.getFullPatientName(ua.getPatient()));
           }
           else{
               vpp.setNumberOfVisits(vpp.getNumberOfVisits() + 1);
           }
        }   

        Collections.sort(vppList, new Comparator<VisitsPerPatient>(){  //sort based on numbers of views in descending order 
        //@Override
            public int compare(VisitsPerPatient o1, VisitsPerPatient o2) {
                return o1.getNumberOfVisits() > o2.getNumberOfVisits()? -1 : o1.getNumberOfVisits() == o2.getNumberOfVisits()? 0 : 1;
                        //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
            }
        });

        return vppList;
    }

        public int[] getPatientEncountersByMonthYear(int month, int year) {
        year -= 1900;
                
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(
                        UserActivity.class);

        int[] encountersByDay = new int[31];//creates index 0-30.
        crit.add(Restrictions.eq("activityType", 5)); //filters for only patient visits
        for(UserActivity ua : (List<UserActivity>)crit.list()){
                if(ua.getAccessDate().getMonth()+1 == month && ua.getAccessDate().getYear() == year){
                    encountersByDay[ua.getAccessDate().getDate()-1]++; //matches the month and year and increments the corresponding index by 1
                }
        }
        return encountersByDay;
    }

    public List<EncountersPerPatient> getMostFrequentlyEncounteredPatients() {
        List<EncountersPerPatient> eppList = new ArrayList();
        int patientID = 0;
        UserActivity usera, ua;
        EncountersPerPatient epp;

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserActivity.class);

        crit.add(Restrictions.eq("activityType", 5)); //filters for only patient visits
        crit.addOrder(Order.asc("patient"));

        if(crit.list().isEmpty()){
            return eppList;
        }

        usera = (UserActivity)crit.list().get(0); //get the first entry
        epp = new EncountersPerPatient(); 
        eppList.add(epp);
        patientID = usera.getPatient().getId(); //this is the first id to compare against
        epp.setNumberOfEncounters(epp.getNumberOfEncounters() + 1);
        epp.setLastEncounterDate(usera.getAccessDate());
        epp.setPatientName(UserActivity.getFullPatientName(usera.getPatient()));

        for(int i=1; i<crit.list().size(); i++){
           ua = (UserActivity)crit.list().get(i);
           if (ua.getPatient().getId() != patientID){ //if patientID is different then previous stop counting and create new vpp
               epp = new EncountersPerPatient();
               eppList.add(epp);
               patientID = ua.getPatient().getId();
                epp.setNumberOfEncounters(epp.getNumberOfEncounters() + 1);
               epp.setLastEncounterDate(ua.getAccessDate());
               epp.setPatientName(UserActivity.getFullPatientName(ua.getPatient()));
           }
           else{
               epp.setNumberOfEncounters(epp.getNumberOfEncounters() + 1);
           }
        }   

        Collections.sort(eppList, new Comparator<EncountersPerPatient>(){  //sort based on numbers of views in descending order 
        //@Override
            public int compare(EncountersPerPatient o1, EncountersPerPatient o2) {
                return o1.getNumberOfEncounters() > o2.getNumberOfEncounters()? -1 : o1.getNumberOfEncounters() == o2.getNumberOfEncounters()? 0 : 1;
                        //returns positve(means swtich positions), 0(means leave as is) or negative number(mean leave as is)
            }
        });

        return eppList;
    }
        
        
}

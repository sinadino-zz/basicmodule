/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule.db.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.openmrs.module.basicmodule.ActivityType;
import org.openmrs.module.basicmodule.db.ActivityTypeDAO;

/**
 *
 * @author lev
 */
public class HibernateActivityTypeDAO implements ActivityTypeDAO{
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
        
    public ActivityType saveActivityType(ActivityType at){
        sessionFactory.getCurrentSession().saveOrUpdate(at);
	return at;
    }
    
    public ActivityType getActivityType(Integer id){
        return (ActivityType) sessionFactory.getCurrentSession().get(ActivityType.class, id);
    }
    
    public List<ActivityType> getAllActivityTypes(){
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ActivityType.class);
	return crit.list();
    }
    
    
}

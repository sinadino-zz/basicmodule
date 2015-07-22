package org.openmrs.module.basicmodule.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.basicmodule.ActivityRecord;
import org.openmrs.module.basicmodule.ActivityRecordService;
import org.openmrs.module.basicmodule.db.ActivityRecordDAO;

/**
 * This class should not be called directly. Instead, the {@link ActivityRecordService}
 * should be using this. A developer should do: <code>
 *   
 *   Context.getService(ActivityRecordService.class).saveActivityRecord(note)...
 *   
 * </code>
 * 
 */
public class HibernateActivityRecordDAO implements ActivityRecordDAO {

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

	public ActivityRecord getActivityRecord(Integer id) {
		return (ActivityRecord) sessionFactory.getCurrentSession().get(ActivityRecord.class, id);
	}

	public List<ActivityRecord> getActivityRecordsByPatient(Patient patient) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ActivityRecord.class);
		crit.add(Restrictions.eq("patient", patient));
		return crit.list();
	}
        
        public List<ActivityRecord> getActivityRecordsByUser(User user) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ActivityRecord.class);
		crit.add(Restrictions.eq("user", user));
		return crit.list();
	}

	public ActivityRecord saveActivityRecord(ActivityRecord ar) {
		sessionFactory.getCurrentSession().saveOrUpdate(ar);
		return ar;
	}

}

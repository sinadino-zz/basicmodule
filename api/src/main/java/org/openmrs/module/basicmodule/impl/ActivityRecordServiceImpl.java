package org.openmrs.module.basicmodule.impl;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.basicmodule.ActivityRecord;
import org.openmrs.module.basicmodule.ActivityRecordService;
import org.openmrs.module.basicmodule.db.ActivityRecordDAO;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.basicmodule.impl.ActivityRecordServiceImpl;

/**
 * Implementation of our {@link ActivityRecordService} interface. This class is set in the
 * /metadata/moduleApplicationContext.xml file to be matched to the
 * {@link ActivityRecordService} interface. <br/>
 * <br/>
 * This class extends {@link BaseOpenmrsService} so that there are empty methods
 * for onStartup and onShutdown. This allows sheilds us from changes to the
 * OpenmrsService interface from forcing us to implement the methods. <br/>
 * <br/>
 * NEVER call "new ActivityRecordServiceImpl()....". To use this class, you do: <br/>
 * <code>
 *   
 *   Context.getService(ActivityRecordService.class).saveActivityRecord(note)...
 *   
 * </code>
 * 
 */
public class ActivityRecordServiceImpl extends BaseOpenmrsService implements ActivityRecordService {

	/**
	 * This "dao" object is set by spring. See the
	 * /metadata/moduleApplicationContext.xml for what value gets set here. We
	 * can assume that this will never be null, even though it never gets set in
	 * here. This is called Inversion of Control (IoC)
	 */
	private ActivityRecordDAO dao;

	/**
	 * This is the method that spring calls to set the DAO
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ActivityRecordDAO dao) {
		this.dao = dao;
	}

	/**
	 * @see org.openmrs.module.patientnotes.ActivityRecordService#getActivityRecord(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public ActivityRecord getActivityRecord(Integer id) {
		return dao.getActivityRecord(id);
	}

	@Transactional(readOnly = true)
	public List<ActivityRecord> getRecordsByPatient(Patient patient) {
		return dao.getActivityRecordsByPatient(patient);
	}
        
        @Transactional(readOnly = true)
	public List<ActivityRecord> getRecordsByUser(User user) {
		return dao.getActivityRecordsByUser(user);
	}

	@Transactional
	public ActivityRecord saveActivityRecord(ActivityRecord ar) {
		return dao.saveActivityRecord(ar);
	}

}

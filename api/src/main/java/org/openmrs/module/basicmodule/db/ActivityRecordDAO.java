/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule.db;

import java.util.List;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.basicmodule.ActivityRecord;

/**
 *
 * @author lev
 */
public interface ActivityRecordDAO {
    
    ActivityRecord getActivityRecord(Integer id);

    List<ActivityRecord> getActivityRecordsByPatient(Patient patient);
    
    List<ActivityRecord> getActivityRecordsByUser(User user);

    ActivityRecord saveActivityRecord(ActivityRecord note);
}

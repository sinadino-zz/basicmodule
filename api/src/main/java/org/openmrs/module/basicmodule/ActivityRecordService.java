/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.List;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;

/**
 *
 * @author lev
 */
public interface ActivityRecordService extends OpenmrsService{
    public ActivityRecord saveActivityRecord(ActivityRecord ar);
    public ActivityRecord getActivityRecord(Integer id);
    public List<ActivityRecord> getRecordsByPatient(Patient p);
    public List<ActivityRecord> getRecordsByUser(User u);
}

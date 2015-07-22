/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.util.List;
import org.openmrs.api.OpenmrsService;

/**
 *
 * @author lev
 */
public interface ActivityTypeService extends OpenmrsService{
    public ActivityType saveActivityType(ActivityType at);
    public ActivityType getActivityType(Integer id);
    public List<ActivityType> getAllActivityTypes();
}

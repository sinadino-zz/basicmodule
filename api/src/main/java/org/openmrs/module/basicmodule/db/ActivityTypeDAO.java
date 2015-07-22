/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule.db;

import java.util.List;
import org.openmrs.module.basicmodule.ActivityType;

/**
 *
 * @author lev
 */
public interface ActivityTypeDAO {
    public ActivityType saveActivityType(ActivityType at);
    public ActivityType getActivityType(Integer id);
    public List<ActivityType> getAllActivityTypes();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule.impl;

import java.util.List;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.basicmodule.ActivityType;
import org.openmrs.module.basicmodule.ActivityTypeService;
import org.openmrs.module.basicmodule.db.ActivityTypeDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lev
 */
public class ActivityTypeServiceImpl extends BaseOpenmrsService implements ActivityTypeService{
    private ActivityTypeDAO dao;
    public void setDao(ActivityTypeDAO dao) {
         this.dao = dao;
    }
    
    @Transactional(readOnly = true)
    public ActivityType getActivityType(Integer id) {
            return dao.getActivityType(id);
    }
    
    @Transactional
    public ActivityType saveActivityType(ActivityType at) {
            return dao.saveActivityType(at);
    }
    
    public List<ActivityType> getAllActivityTypes(){
        return dao.getAllActivityTypes();
    }
}

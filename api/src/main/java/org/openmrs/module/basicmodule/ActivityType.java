/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicmodule;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openmrs.BaseOpenmrsData;

/**
 *
 * @author lev
 */
public class ActivityType extends BaseOpenmrsData{
    private Integer id;
    private String activityName;
    private String activityMethods;
    private ArrayList<String> methodList;
    
    private boolean isReady = false;
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer _id){
        this.id = _id;
    }
    
    public String getActivityName(){
        return activityName;
    }
    
    public void setActivityName(String an){
        this.activityName = an;
    }
    
    public String getActivityMethods(){
        return activityMethods;
    }
    
    public void setActivityMethods(String am){
        this.activityMethods = am;
    }
    
    private void processActivityMethods(){
        if(methodList == null){
            methodList = new ArrayList<String>();
        }else{
            methodList.clear();
        }
        String[] methods = activityMethods.split(":");
        methodList.addAll(Arrays.asList(methods));
        isReady = true;
    }
    
    public static ActivityType getActivityContainingMethod(List<ActivityType> types, Method m){
        for(ActivityType at : types){
            if(!at.isReady){
                at.processActivityMethods();
            }
            if(at.methodList.contains(m.getName())){
                return at;
            }
        }
        
        return null;
    }
}

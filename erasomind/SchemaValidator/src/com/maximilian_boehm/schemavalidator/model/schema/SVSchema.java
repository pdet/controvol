package com.maximilian_boehm.schemavalidator.model.schema;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Schema containing several conditions
 */
public class SVSchema{

    private final Map<String, SVFieldCondition> map = new HashMap<>();
    private Calendar Date;

    public Map<String, SVFieldCondition> getMap(){
        return map;
    }

    public void addCondition(SVFieldCondition condition) {
        map.put(condition.getKey(), condition);
    }

    public SVFieldCondition getCondition(String sKey){
        return map.get(sKey);
    }

    public boolean hasCondition(String sKey){
        return map.containsKey(sKey);
    }

    public Calendar getDate() {
        return Date;
    }

    public void setDate(Calendar date) {
        Date = date;
    }

    public void printConditions() {
        for(Map.Entry<String,SVFieldCondition> entry : map.entrySet()) {
            String key = entry.getKey();
            SVFieldCondition value = entry.getValue();

            System.out.println(value.getField().getType() + " "+ key);
        }
        System.out.println("------------------------");
    }


}

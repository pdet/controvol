package com.maximilian_boehm.schemavalidator.model.schema;

import com.maximilian_boehm.javasourceparser.access.struct.JPField;

/**
 * One Condition in the schema
 */
public class SVFieldCondition{

    private String key;
    private JPField field;

    public JPField getField() {
        return field;
    }

    public void setField(JPField field) {
        this.field = field;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        SVFieldCondition sc = (SVFieldCondition)obj;
        return getKey().equals(sc.getKey());
    }
}

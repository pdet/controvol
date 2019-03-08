package com.maximilian_boehm.javasourceparser.model.meta;

import com.maximilian_boehm.javasourceparser.access.struct.JPField;
import com.maximilian_boehm.javasourceparser.model.meta.base.JPAnnotationHolderImpl;

/**
 * Implementation of the meta-model of a field
 */
public class JPFieldImpl extends JPAnnotationHolderImpl implements JPField{

    private String name;
    private String type;

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.struct.JPField#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.struct.JPField#getType()
     */
    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        JPField field = (JPField)obj;
        return this.getType().equals(field.getType()) && this.getName().equals(field.getName());
    }

    public boolean nameEquals(Object obj) {
        JPField field = (JPField)obj;
        return this.getName().equals(field.getName());
    }
}
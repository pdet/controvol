package com.maximilian_boehm.javasourceparser.access.struct;

import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotationHolder;

/**
 * Meta-Model for a Field
 */
public interface JPField extends JPAnnotationHolder{

    /**
     * @return the name of the field. e.g. private long lLongValue would result in 'lLongValue'
     */
    public String getName();

    /**
     * @return the type of the field. e.g. private long lLongValue would result in 'long'
     */
    public String getType();

}

package com.maximilian_boehm.schemavalidator.access.struct;

import com.maximilian_boehm.javasourceparser.access.struct.JPField;

/**
 * One Result of a Comparison between several schemata
 */
public interface SVCompareResult {

    /**
     * @return name of the affected field
     */
    public String  getFieldName();

    /**
     * @return original field
     */
    public JPField getOldField();

    /**
     * @return current field
     */
    public JPField getNewField();

    /**
     * @return type of change
     */
    public SVCompareResultType getType();

}

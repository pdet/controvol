package com.maximilian_boehm.schemavalidator.model.result;

import com.maximilian_boehm.javasourceparser.access.struct.JPField;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultType;

/**
 * One Result
 */
public class SVCompareResultImpl implements SVCompareResult{

    private JPField oldField;
    private JPField newField;
    private String  sFieldName;
    private SVCompareResultType type;

    public SVCompareResultImpl() {}

    public SVCompareResultImpl(SVCompareResultType t, String s) {
        setFieldName(s);
        setType(t);
    }

    public void setOldField(JPField oldField) {
        this.oldField = oldField;
    }

    public void setNewField(JPField newField) {
        this.newField = newField;
    }

    public void setType(SVCompareResultType type) {
        this.type = type;
    }

    public void setFieldName(String fieldName) {
        sFieldName = fieldName;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult#getOldField()
     */
    @Override
    public JPField getOldField() {
        return oldField;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult#getNewField()
     */
    @Override
    public JPField getNewField() {
        return newField;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult#getType()
     */
    @Override
    public SVCompareResultType getType() {
        return type;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult#getFieldName()
     */
    @Override
    public String getFieldName() {
        return sFieldName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        SVCompareResultImpl svcr = (SVCompareResultImpl)obj;
        return getFieldName().equals(svcr.getFieldName()) && getType() == svcr.getType();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getType()+": "+getFieldName();
    }

}

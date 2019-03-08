package com.maximilian_boehm.schemavalidator.access;

import com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager;

public interface SVHome {

    /**
     * Create a manager which can contain several schemata
     * and compare them
     */
    public SVSchemaManager createSchemaManager() throws Exception;
}

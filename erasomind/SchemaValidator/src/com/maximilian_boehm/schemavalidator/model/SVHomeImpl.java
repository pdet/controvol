package com.maximilian_boehm.schemavalidator.model;

import com.maximilian_boehm.schemavalidator.access.SVHome;
import com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager;

public class SVHomeImpl implements SVHome{

    @Override
    public SVSchemaManager createSchemaManager() throws Exception {
        return new SVSchemaManagerImpl();
    }


}

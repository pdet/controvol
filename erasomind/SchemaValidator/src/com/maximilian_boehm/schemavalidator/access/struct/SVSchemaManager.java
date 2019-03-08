package com.maximilian_boehm.schemavalidator.access.struct;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import com.maximilian_boehm.javasourceparser.access.struct.JPClass;

public interface SVSchemaManager {

    public void addSchemaByFile(JPClass jpClass, Calendar date) throws Exception;
    public void addSchemaByFile(File f, Calendar date) throws Exception;
    public List<SVCompareResultTable> compareSchemata() throws Exception;

}

package com.maximilian_boehm.edasomind.access;

import java.io.File;

import com.maximilian_boehm.edasomind.access.struct.EdasomindResultList;

public interface EdasomindHome {

    /**
     * Analyze a file if there are common errors according to agile Schema-Evolution with Object-Mapper
     * Steps:
     * a) Get previous versions from GIT (Provided thorugh GitAccess)
     * b) Analyze and extract information of java files (JavaSourceParser)
     * c) Extract information by comparing the previous results (SchemaValidator)
     * @param f which is a file to the java source file
     */
    public EdasomindResultList analyzeFile(File f) throws Exception;

}

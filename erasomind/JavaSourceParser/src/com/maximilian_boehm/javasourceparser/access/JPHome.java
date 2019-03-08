package com.maximilian_boehm.javasourceparser.access;

import java.io.File;

import com.maximilian_boehm.javasourceparser.access.struct.JPClass;

public interface JPHome {

    /**
     * Extract information of file
     * @param file which is a class and should get analyzed
     * @return a structure which contains meta information about the analyzed class
     */
    public JPClass getParsedClass(File f) throws Exception;

}

package com.maximilian_boehm.javasourceparser.access.struct;

import java.util.List;

import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotationHolder;

/**
 * Meta-Model for a Class
 */
public interface JPClass extends JPAnnotationHolder{

    /**
     * @return the name of the package the class is defined to. e.g. "com.maximilian_boehm.javasourceparser.access.struct"
     */
    public String getPackageName();

    /**
     * @return the name of the class, e.g. "JPClass"
     */
    public String getClassName();

    /**
     * @return all fields of a class.
     */
    public List<JPField> getFields() throws Exception;
}

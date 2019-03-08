package com.maximilian_boehm.javasourceparser.access.struct.base;

import java.util.List;

/**
 * Interface for all types who can have one or more annotations (e.g. classes or fields)
 */
public interface JPAnnotationHolder {

    /**
     * @return the annotations
     */
    public List<JPAnnotation> getAnnotations() throws Exception;

    /**
     * @return true if this holder has one or more annotations
     */
    public boolean hasAnnotations();

    /**
     * @return true if the field has an annotation which name matches with given string
     */
    public boolean hasAnnotation(String sAnnotation);
}

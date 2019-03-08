package com.maximilian_boehm.javasourceparser.access.struct.base;

import java.util.Map;

/**
 * Meta-Model for an @Annotation
 */
public interface JPAnnotation {

    /**
     * 
     * @return Type of annotation (e.g. 'AlsoLoad' for @AlsoLoad-Annotation)
     */
    public String getType();

    /**
     * @return The Attribute-Map of an Annotation (e.g. a map with 'value'='123' and 'abc'='456' equates @AlsoLoad(value="123", abc="456")
     */
    public Map<String, String> getAttributes() throws Exception;

    /**
     * @return true if the Map of getAttributes is not empty
     */
    public boolean hasAttributes();
}
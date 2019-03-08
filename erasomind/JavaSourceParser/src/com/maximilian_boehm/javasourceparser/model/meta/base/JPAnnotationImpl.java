package com.maximilian_boehm.javasourceparser.model.meta.base;

import java.util.HashMap;
import java.util.Map;

import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation;

/**
 * Implementation for the Meta-Model of an @Annotation
 */
public class JPAnnotationImpl implements JPAnnotation{

    private String type;
    private Map<String, String> mapAttributes;

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation#getType()
     */
    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addAttribute(String key, String value){
        if(getAttributes()==null) mapAttributes = new HashMap<String, String>();
        mapAttributes.put(key, value);
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation#getAttributes()
     */
    @Override
    public Map<String, String> getAttributes() {
        return mapAttributes;
    }

    public void setAttributes(Map<String, String> mapAttributes) {
        this.mapAttributes = mapAttributes;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation#hasAttributes()
     */
    @Override
    public boolean hasAttributes() {
        return getAttributes()!=null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        JPAnnotationImpl com = (JPAnnotationImpl)arg0;
        if(com.getType().equals(getType()))
            if(getAttributes().equals(com.getAttributes()))
                return true;

        return super.equals(arg0);
    }
}
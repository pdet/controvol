package com.maximilian_boehm.javasourceparser.model.meta.base;

import java.util.ArrayList;
import java.util.List;

import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation;
import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotationHolder;

/**
 * Implementation of an AnnotationHolder
 */
public class JPAnnotationHolderImpl implements JPAnnotationHolder{

    private List<JPAnnotation> listAnnotations;

    /**
     * Add annotation
     * @param annotation
     */
    public void addAnnotation(JPAnnotation annotation){
        if(listAnnotations==null) listAnnotations = new ArrayList<JPAnnotation>();
        listAnnotations.add(annotation);
    }

    /**
     * Set a list of annotations
     */
    public void setAnnotations(List<JPAnnotation> listAnnotations) {
        this.listAnnotations = listAnnotations;
    }

    /**
     * @return all annotations as list
     */
    @Override
    public List<JPAnnotation> getAnnotations() {
        return listAnnotations;
    }

    /**
     * @return true if there are annotations
     */
    @Override
    public boolean hasAnnotations(){
        return getAnnotations()!=null;
    }

    /**
     * @return true if the field has an annotation which name matches with given string
     */
    @Override
    public boolean hasAnnotation(String sAnnotation) {
        if(!hasAnnotations())
            return false;

        // look through all annotations
        for(JPAnnotation anno:getAnnotations())
            if(anno.getType().equals(sAnnotation))
                return true;

        return false;
    }
}
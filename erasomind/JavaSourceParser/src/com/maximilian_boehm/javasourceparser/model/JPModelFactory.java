package com.maximilian_boehm.javasourceparser.model;

import com.maximilian_boehm.javasourceparser.model.meta.JPClassImpl;
import com.maximilian_boehm.javasourceparser.model.meta.JPFieldImpl;
import com.maximilian_boehm.javasourceparser.model.meta.base.JPAnnotationImpl;

public class JPModelFactory {
   
   public static JPClassImpl createJPClass(){
      return new JPClassImpl();
   }
   public static JPAnnotationImpl createJPAnnotation(){
      return new JPAnnotationImpl();
   }
   public static JPFieldImpl createJPField(){
      return new JPFieldImpl();
   }
   
}

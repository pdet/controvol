package com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Property {

    String value();

}

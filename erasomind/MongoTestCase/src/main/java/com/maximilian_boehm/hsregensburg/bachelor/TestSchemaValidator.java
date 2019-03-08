package com.maximilian_boehm.hsregensburg.bachelor;

import com.google.code.morphia.annotations.AlsoLoad;
import com.google.code.morphia.annotations.NotSaved;
import com.google.code.morphia.annotations.Transient;

public class TestSchemaValidator {

    @Transient
    private String ABC;

    @NotSaved
    private long DEF;

    @AlsoLoad("ABC")
    private String GHI;

}

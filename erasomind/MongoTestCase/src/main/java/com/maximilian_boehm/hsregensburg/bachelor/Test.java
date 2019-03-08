package com.maximilian_boehm.hsregensburg.bachelor;

import com.google.code.morphia.annotations.AlsoLoad;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.NotSaved;

@Entity
public class Test {
    @AlsoLoad("aa")
    private String Test;
    @NotSaved
    private String ABC;

}

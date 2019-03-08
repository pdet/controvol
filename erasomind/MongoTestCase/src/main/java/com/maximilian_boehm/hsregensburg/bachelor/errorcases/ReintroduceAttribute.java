package com.maximilian_boehm.hsregensburg.bachelor.errorcases;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.NotSaved;

@SuppressWarnings(value={"unused"})
@Entity
public class ReintroduceAttribute {

    private String lastname;
    private String firstname;

    // ERROR: Reintroduce name which was already existent but previously not listened any more
    @NotSaved
    private String name;

}

package com.maximilian_boehm.hsregensburg.bachelor;

import com.google.code.morphia.annotations.AlsoLoad;
import com.google.code.morphia.annotations.NotSaved;
import com.google.code.morphia.annotations.Transient;

@SuppressWarnings(value={"unused"})
public class TestBenchmark {

    @Transient
    private String ABC;

    @NotSaved
    private long DEF;

    @AlsoLoad("ABC")
    private String GHI;


    private String s1;
    private String s2;
    @NotSaved
    private String s5;
    @AlsoLoad("n2")
    private int n1;
    private int n4;
    @NotSaved
    private int n6;
    private long l1;
    private long l4;
    @AlsoLoad("XXX")
    private long l9;
    private char a2;
    private char a4;
    @AlsoLoad("AASD")
    private char a5;

    private double d1;
    private double d2;
    private double d3;
    private double d4;
    private double d5;
    private double d6;
    private double d7;
    private double d8;
    private double d9;

}

package com.maximilian_boehm.edasomind.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Class for Testing all modules
 */
@RunWith(Suite.class)
@SuiteClasses({
    TestModules.class,
    TestEDASOMIND.class
})
public class TestAll {}

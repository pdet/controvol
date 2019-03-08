package com.maximilian_boehm.edasomind.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.maximilian_boehm.gitaccess.test.TestGitAccess;
import com.maximilian_boehm.javasourceparser.test.TestJavaSourceParser;
import com.maximilian_boehm.schemavalidator.test.TestSchemaValidator;


/**
 * Class for Testing the Modules
 */
@RunWith(Suite.class)
@SuiteClasses({
    TestGitAccess.class,
    TestJavaSourceParser.class,
    TestSchemaValidator.class
})
public class TestModules {}

package com.maximilian_boehm.edasomind.test;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.maximilian_boehm.edasomind.access.EdasomindAccessFactory;


/**
 * Class for Benchmark Testing EDASOMIND
 */
public class TestBenchmark {

    private static String PACKAGE_PREFIX;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Get the test-file
        PACKAGE_PREFIX = com.maximilian_boehm.hsregensburg.bachelor.TestSchemaValidator.class.getResource("locate.txt").getFile();

        // Workaround: Get path to src-directory
        PACKAGE_PREFIX = PACKAGE_PREFIX.replace("/target/classes/com/maximilian_boehm/hsregensburg/bachelor/locate.txt", "");
        PACKAGE_PREFIX += "/src/main/java/com/maximilian_boehm/hsregensburg/bachelor";

    }


    @Test
    public void testBenchmarkRegular() throws Exception{
        runTestAndMeasureDuration(PACKAGE_PREFIX+"/TestBenchmark.java");
    }

    @Test
    public void testBenchmarkManyFields() throws Exception{
        runTestAndMeasureDuration(PACKAGE_PREFIX+"/benchmark/TestHighNumberVar.java");
    }

    @Test
    public void testBenchmarkManyCommits() throws Exception{
        runTestAndMeasureDuration(PACKAGE_PREFIX+"/benchmark/HighNumberCommit.java");
    }

    /**
     * @param sFile which is a String to the java-source-file
     */
    private void runTestAndMeasureDuration(String sFile) throws Exception{
        // Remember start time
        long lStartTime = System.currentTimeMillis();
        // do it 400 times
        for (int i = 0; i < 50; i++) {
            // measure time of each run
            long nCurrent = System.currentTimeMillis();
            // analyze that one file
            EdasomindAccessFactory.getHome().analyzeFile(new File(sFile));
            // sysout the needed time
            System.out.println(System.currentTimeMillis()-nCurrent);
        }
        // sysout time for whole test
        System.out.println("Time needed for whole test: "+(System.currentTimeMillis()-lStartTime));

    }

}
package com.maximilian_boehm.gitaccess.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.maximilian_boehm.gitaccess.access.GTAccessFactory;
import com.maximilian_boehm.gitaccess.access.struct.GTHistory;
import com.maximilian_boehm.gitaccess.access.struct.GTHistoryFile;

/**
 * Test-Case for Retrieving the specific history of a file and check if the retrieved contents are ok.
 */
public class TestGitAccessPlayerApp {

    GTHistory history;

    /**
     * Setting up the environment
     * Get the history out from the classpath, workaround to get the src-path
     */
    @Before
    public void setUp() throws Exception {
        // Get the test-file
        String sFile = "/Users/Thomas/git/player/src/com/example/player/Player.java";
    
        // Retrieve history
        history = GTAccessFactory.getHome().getGitHistoryOfFile(new File(sFile));
    }

    /**
     * Retrieve all versions of a file
     * The file is a simple txt-file which content increments from 0 to 5
     * Thereby just compare if the retrieved integer is ok
     */
    @Test
    public void testGitAccess() throws Exception{
        // Content starts at 0
        int nStart =  0;

        // get the current time
        Calendar cal = Calendar.getInstance();

        for(GTHistoryFile historyFile : history.getHistoryFiles()){
        	System.out.println(historyFile.getId());
        	System.out.println(historyFile.getAuthor());
        	System.out.println(historyFile.getComment());
        	System.out.println(historyFile.getCommitDate());
        	nStart = nStart+1;
        	System.out.println("");
        }
        
    }

    /**
     * @return content from a given file as an integer
     * @throws IOException
     */
    private int getIntFromFile(File f) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        return Integer.parseInt(new String(encoded));
    }
}

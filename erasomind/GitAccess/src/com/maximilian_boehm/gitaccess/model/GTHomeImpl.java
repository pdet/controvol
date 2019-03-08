package com.maximilian_boehm.gitaccess.model;

import java.io.File;

import com.maximilian_boehm.gitaccess.access.GTHome;
import com.maximilian_boehm.gitaccess.access.struct.GTHistory;

/**
 * Implementation of home-interface
 */
public class GTHomeImpl implements GTHome {

    @Override
    public GTHistory getGitHistoryOfFile(File f) throws Exception{
        return new MGitHistory().getHistory(f);
    }
}
package com.maximilian_boehm.gitaccess.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.maximilian_boehm.gitaccess.access.struct.GTHistory;
import com.maximilian_boehm.gitaccess.access.struct.GTHistoryFile;

/**
 * Implementation of interface
 */
public class GTHistoryImpl implements GTHistory {

    private File currentFile;
    private List<GTHistoryFile> listHistory;

    @Override
    public File getCurrentFile() {return currentFile;}
    public void setCurrentFile(File currentFile) {this.currentFile = currentFile;}

    @Override
    public List<GTHistoryFile> getHistoryFiles() {return listHistory;}
    public void addHistoryFile(GTHistoryFile historyFile){
        // create new list if it's not yet there
        if(listHistory==null) listHistory = new ArrayList<>();
        listHistory.add(historyFile);
    }

}

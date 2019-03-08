package com.maximilian_boehm.gitaccess.model;

/**
 * Factory for creating the Model-Classes
 */
public class GTModelFactory {

    /**
     * @return instance of History
     */
    public static GTHistoryImpl createHistoryImpl() {
        return new GTHistoryImpl();
    }

    /**
     * @return instance of historyfile
     */
    public static GTHistoryFileImpl createHistoryFileImpl() {
        return new GTHistoryFileImpl();
    }
}

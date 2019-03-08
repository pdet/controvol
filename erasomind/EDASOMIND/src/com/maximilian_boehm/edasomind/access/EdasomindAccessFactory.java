package com.maximilian_boehm.edasomind.access;

import com.maximilian_boehm.edasomind.model.EdasomindHomeImpl;

/**
 * Factory for Accessing Edasomind-Module
 */
public class EdasomindAccessFactory {

    private static EdasomindHome home = null;

    /**
     * Get Home for EDASOMIND
     */
    public static EdasomindHome getHome() {
        if(home==null) home = new EdasomindHomeImpl();
        return home;
    }
}
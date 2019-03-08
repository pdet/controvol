package com.maximilian_boehm.gitaccess.access;

import com.maximilian_boehm.gitaccess.model.GTHomeImpl;

/**
 * Factory for Retrieving the Home of GitAccess
 */
public class GTAccessFactory {

    private static GTHome home = null;

    /**
     * @return GitAccess-Home
     */
    public static GTHome getHome(){
        if(home==null) home=new GTHomeImpl();
        return home;
    }
}
package com.maximilian_boehm.javasourceparser.access;

import com.maximilian_boehm.javasourceparser.model.JPHomeImpl;

/**
 * Factory for Accessing JavaSourceParser
 */
public class JPAccessFactory {

    private static JPHome home = null;

    /**
     * @return home of JavaSourceParser
     */
    public static JPHome getHome() {
        if(home==null) home = new JPHomeImpl();
        return home;
    }
}

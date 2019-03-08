package com.maximilian_boehm.javasourceparser.model;

import java.io.File;

import com.maximilian_boehm.javasourceparser.access.JPHome;
import com.maximilian_boehm.javasourceparser.access.struct.JPClass;

public class JPHomeImpl implements JPHome{

    private final JPSourceReader jsr = new JPSourceReader();

    /* (non-Javadoc)
     * @see com.maximilian_boehm.javasourceparser.access.JPHome#getParsedClass(java.io.File)
     */
    @Override
    public JPClass getParsedClass(File f) throws Exception {
        return jsr.parseJavaSourceFile(f, JPModelFactory.createJPClass());
    }

}

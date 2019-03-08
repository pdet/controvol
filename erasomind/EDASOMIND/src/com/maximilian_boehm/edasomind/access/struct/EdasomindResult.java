package com.maximilian_boehm.edasomind.access.struct;

import java.util.Calendar;

/**
 * Result-Interface for GUI containing necessary information without logic
 */
public interface EdasomindResult {

    /**
     * @return get relevance of result
     */
    public EdasomindSignificance getSignificance();

    /**
     * @return get date before change occurred
     */
    public Calendar getCalendarFrom();

    /**
     * @return get date after change occurred
     */
    public Calendar getCalendarTo();

    /**
     * @return message what happened in this result
     */
    public String getMessage();

}

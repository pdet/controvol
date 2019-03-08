package com.maximilian_boehm.schemavalidator.access.struct;

import java.util.Calendar;
import java.util.List;

/**
 * Table which contains a list of results
 */
public interface SVCompareResultTable {

    public boolean hasResults();

    /**
     * @return the results of a comparison
     */
    public List<SVCompareResult> getResults();

    /**
     * @return date of old file
     */
    public Calendar getDateOldFile();

    /**
     * @return date of new file
     */
    public Calendar getDateNewFile();

}

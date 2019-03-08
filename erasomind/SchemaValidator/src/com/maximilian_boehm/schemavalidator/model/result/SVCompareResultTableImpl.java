package com.maximilian_boehm.schemavalidator.model.result;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable;

/**
 * Result-Table
 */
public class SVCompareResultTableImpl implements SVCompareResultTable {

    List<SVCompareResult> listResults = new ArrayList<SVCompareResult>();
    private Calendar DateOldFile;
    private Calendar DateNewFile;

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable#hasResults()
     */
    @Override
    public boolean hasResults() {
        return !getResults().isEmpty();
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable#getResults()
     */
    @Override
    public List<SVCompareResult> getResults() {
        return listResults;
    }

    public void addResult(SVCompareResult result){
        listResults.add(result);
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable#getDateOldFile()
     */
    @Override
    public Calendar getDateOldFile() {
        return DateOldFile;
    }

    public void setDateOldFile(Calendar dateOldFile) {
        DateOldFile = dateOldFile;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable#getDateNewFile()
     */
    @Override
    public Calendar getDateNewFile() {
        return DateNewFile;
    }

    public void setDateNewFile(Calendar dateNewFile) {
        DateNewFile = dateNewFile;
    }
}
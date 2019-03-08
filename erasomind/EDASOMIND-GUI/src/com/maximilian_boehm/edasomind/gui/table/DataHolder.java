package com.maximilian_boehm.edasomind.gui.table;

import java.util.ArrayList;
import java.util.List;

import com.maximilian_boehm.edasomind.access.struct.EdasomindResultList;

public class DataHolder {

    // List for all listener
    private final List<DataHolderListener> listListener = new ArrayList<DataHolderListener>();
    // Maintains result from analyzing
    private EdasomindResultList resultList = null;

    /**
     * Set Result from analyzing
     */
    public void setResult(EdasomindResultList result){
        resultList = result;
        // notify all listener
        for(DataHolderListener dhl:listListener)
            dhl.dataChanged();
    }

    /**
     * @return result from analyzing
     */
    public EdasomindResultList getResult() {
        return resultList;
    }

    /**
     * @return number of results
     */
    public int getResultRowCount() {
        return resultList==null ? 0 : resultList.size();
    }

    /**
     * Register a listener
     * @param listener which implements the interface
     */
    public void registerListener(DataHolderListener listener){
        listListener.add(listener);
    }


}

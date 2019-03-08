package com.maximilian_boehm.edasomind.gui.table;

/**
 * Interface which needs to be implemented if sb. wants to get informed after data has changed
 */
public interface DataHolderListener {

    /**
     * This event will be fired if the data has changed
     */
    public void dataChanged();
}
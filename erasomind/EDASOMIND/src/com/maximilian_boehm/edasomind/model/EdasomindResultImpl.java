package com.maximilian_boehm.edasomind.model;

import java.util.Calendar;

import com.maximilian_boehm.edasomind.access.struct.EdasomindResult;
import com.maximilian_boehm.edasomind.access.struct.EdasomindSignificance;

/**
 * Result for GUI
 */
public class EdasomindResultImpl implements EdasomindResult{

    private EdasomindSignificance Significance;
    private Calendar CalendarFrom;
    private Calendar CalendarTo;
    private String   sMessage;

    /**
     * Setting all data for testing
     */
    public void setAllData(EdasomindSignificance sig, long calFrom, long calTo, String sMsg) {
        setSignificance(sig);
        setCalendarFrom(new Calendar.Builder().setInstant(calFrom).build());
        setCalendarTo(new Calendar.Builder().setInstant(calTo).build());
        setMessage(sMsg);;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.access.struct.EdasomindResult#getSignificance()
     */
    @Override
    public EdasomindSignificance getSignificance() {
        return Significance;
    }

    public void setSignificance(EdasomindSignificance significance) {
        Significance = significance;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.access.struct.EdasomindResult#getCalendarFrom()
     */
    @Override
    public Calendar getCalendarFrom() {
        return CalendarFrom;
    }

    public void setCalendarFrom(Calendar calendarFrom) {
        CalendarFrom = calendarFrom;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.access.struct.EdasomindResult#getCalenderTo()
     */
    @Override
    public Calendar getCalendarTo() {
        return CalendarTo;
    }

    public void setCalendarTo(Calendar calendarTo) {
        CalendarTo = calendarTo;
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.access.struct.EdasomindResult#getMessage()
     */
    @Override
    public String getMessage() {
        return sMessage;
    }

    public void setMessage(String message) {
        sMessage = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        EdasomindResultImpl c = (EdasomindResultImpl)obj;

        return  c.getMessage()      .equals(this.getMessage     ()) &&
                c.getCalendarTo()   .equals(this.getCalendarTo  ()) &&
                c.getCalendarFrom() .equals(this.getCalendarFrom()) &&
                c.getSignificance() .equals(this.getSignificance());
    }

}

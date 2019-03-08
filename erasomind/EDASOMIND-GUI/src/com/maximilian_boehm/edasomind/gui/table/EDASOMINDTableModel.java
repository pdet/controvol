package com.maximilian_boehm.edasomind.gui.table;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import com.maximilian_boehm.edasomind.access.struct.EdasomindResult;

public class EDASOMINDTableModel extends AbstractTableModel implements DataHolderListener{

    private static final long serialVersionUID = 1L;
    private final DataHolder dataholder;
    private final SimpleDateFormat fmt = new SimpleDateFormat();

    public EDASOMINDTableModel(DataHolder dh) {
        dataholder = dh;
        dh.registerListener(this);
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.gui.table.DataHolderListener#dataChanged()
     */
    @Override
    public void dataChanged(){
        fireTableDataChanged();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return dataholder.getResultRowCount();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        if     (column==0) return "Commit";
        else if(column==1) return "Commit";
        else if(column==2) return "Priority";
        else if(column==3) return "Message";
        else new Throwable().printStackTrace();return null;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // No results yet? no value!
        if(dataholder.getResult()==null) return null;

        // get result depending on row
        EdasomindResult result = dataholder.getResult().get(rowIndex);

        // fill columns
        // column0: CalendarFrom
        if     (columnIndex==0) return format(result.getCalendarFrom());
        // column1: CalendarTo
        else if(columnIndex==1) return format(result.getCalendarTo());
        // column2: Significance
        else if(columnIndex==2) return result.getSignificance();
        // column3: Message
        else if(columnIndex==3) return result.getMessage();
        else new Throwable().printStackTrace();return null;
    }

    /**
     * @param calendar
     * @return
     */
    private String format(Calendar calendar){
        return fmt.format(calendar.getTime());
    }

}

package com.maximilian_boehm.edasomind.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.maximilian_boehm.edasomind.access.struct.EdasomindSignificance;

public class EDASOMINDTableCellRenderer extends DefaultTableCellRenderer{

    private static final long serialVersionUID = 1L;
    private DataHolder dataholder = new DataHolder();

    public EDASOMINDTableCellRenderer(DataHolder dh) {
        dataholder = dh;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        // get component
        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // color the row depending on the significance!
        if(dataholder.getResult().get(row).getSignificance() == EdasomindSignificance.HIGH){
            c.setForeground(Color.WHITE);
            c.setBackground(Color.RED);

        } else if(dataholder.getResult().get(row).getSignificance() == EdasomindSignificance.MEDIUM){
            c.setForeground(Color.black);
            c.setBackground(Color.ORANGE);

        } else{
            c.setBackground(Color.GREEN);
            c.setForeground(Color.black);
        }

        return c;
    }

}

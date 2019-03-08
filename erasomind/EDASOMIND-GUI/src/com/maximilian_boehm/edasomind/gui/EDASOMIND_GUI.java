package com.maximilian_boehm.edasomind.gui;

import java.awt.Container;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.maximilian_boehm.edasomind.access.EdasomindAccessFactory;
import com.maximilian_boehm.edasomind.access.struct.EdasomindResultList;
import com.maximilian_boehm.edasomind.gui.table.DataHolder;
import com.maximilian_boehm.edasomind.gui.table.EDASOMINDTableCellRenderer;
import com.maximilian_boehm.edasomind.gui.table.EDASOMINDTableModel;

/**
 * Class for creating the GUI
 */
public class EDASOMIND_GUI  {

    DataHolder dh = new DataHolder();

    /**
     * Starting point for GUI
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EDASOMIND_GUI().createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("EDASOMIND");
        // Basic settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create select-file-button
        JButton button = new JButton("Select File");
        button.setSize(300, 100);
        // add action
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // create native file dialogue
                FileDialog dialog = new FileDialog(frame, "Load Java-File",FileDialog.LOAD);
                // limit selection to .java-files
                dialog.setFile("*.java");
                // show dialogue
                dialog.setVisible(true);

                // retrieve file from dialogue
                File f = new File(dialog.getDirectory() + dialog.getFile());
                if(f.exists())
                    try {
                        // try to retrieve results
                        EdasomindResultList result = EdasomindAccessFactory.getHome().analyzeFile(f);
                        // set the results to the data-holder
                        dh.setResult(result);
                    } catch (Exception e) {
                        // Exception? Print it!
                        e.printStackTrace();
                    }
            }
        });

        // Create layout & append to frame
        GridLayout l = new GridLayout(2,1);
        frame.setLayout(l);
        Container c = frame.getContentPane();

        // Create table for displaying results
        // create specific table-model and TableCellRenderer
        EDASOMINDTableModel tm = new EDASOMINDTableModel(dh);
        JTable table = new JTable(tm);
        TableCellRenderer ren = new EDASOMINDTableCellRenderer(dh);
        table.setDefaultRenderer(Object.class, ren);

        // Add 'Select-File'-Button
        c.add(button);
        // Add Table
        c.add( new JScrollPane( table ) );

        // mac-osx-specific (possibility to 'fullscreen' an application)
        String className = "com.apple.eawt.FullScreenUtilities";
        String methodName = "setWindowCanFullScreen";
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, new Class<?>[] {Window.class, boolean.class });
            method.invoke(null, frame, true);
        } catch (Throwable t) {
            System.err.println("Full screen mode is not supported");
            //t.printStackTrace();
        }

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @return Holder for all Business-Data
     */
    public DataHolder getDataHolder() {return dh;}

}
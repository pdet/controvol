package com.maximilian_boehm.edasomind.model;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import com.maximilian_boehm.edasomind.access.EdasomindHome;
import com.maximilian_boehm.edasomind.access.struct.EdasomindResultList;
import com.maximilian_boehm.edasomind.access.struct.EdasomindSignificance;
import com.maximilian_boehm.gitaccess.access.GTAccessFactory;
import com.maximilian_boehm.gitaccess.access.GTHome;
import com.maximilian_boehm.gitaccess.access.struct.GTHistory;
import com.maximilian_boehm.gitaccess.access.struct.GTHistoryFile;
import com.maximilian_boehm.javasourceparser.access.JPAccessFactory;
import com.maximilian_boehm.javasourceparser.access.JPHome;
import com.maximilian_boehm.javasourceparser.access.struct.JPClass;
import com.maximilian_boehm.schemavalidator.access.SVAccessFactory;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable;
import com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager;

public class EdasomindHomeImpl implements EdasomindHome{

    JPHome homeJP = JPAccessFactory.getHome();
    GTHome homeGT = GTAccessFactory.getHome();

    /* (non-Javadoc)
     * @see com.maximilian_boehm.edasomind.access.EdasomindHome#analyzeFile(java.io.File)
     */
    @Override
    public EdasomindResultList analyzeFile(File f) throws Exception {
        // Retrieve history
        GTHistory history = homeGT.getGitHistoryOfFile(f);

        // create new schema manager
        SVSchemaManager manager = SVAccessFactory.getSVHome().createSchemaManager();

        // add current, not checked in, file
        manager.addSchemaByFile(homeJP.getParsedClass(f), new Calendar.Builder().setInstant(f.lastModified()).build());

        // iterate over files in vcs
        for(GTHistoryFile historyFile:history.getHistoryFiles()){
            // Parse class
            JPClass clzz = homeJP.getParsedClass(historyFile.getFile());
            // add files to manager
            manager.addSchemaByFile(clzz, historyFile.getCommitDate());
        }

        // compare all schemata against each other
        List<SVCompareResultTable> listResultTable = manager.compareSchemata();

        EdasomindResultListImpl list = new EdasomindResultListImpl();

        // iterate over all comparisons
        for(SVCompareResultTable resultTable:listResultTable){
            // iterate over results from one comparison
            for(SVCompareResult result:resultTable.getResults()){
                // Create result
                EdasomindResultImpl edasoResult = (EdasomindResultImpl)list.createResult();
                edasoResult.setCalendarFrom(resultTable.getDateOldFile());
                edasoResult.setCalendarTo(resultTable.getDateNewFile());

                // Set Significance and message according to type
                switch (result.getType()) {
                case CHANGE_FIELD:
                    edasoResult.setSignificance(EdasomindSignificance.HIGH);
                    edasoResult.setMessage("Changed Datatype of field "+result.getFieldName());
                    break;
                case ADD_FIELD:
                    edasoResult.setSignificance(EdasomindSignificance.NONE);
                    edasoResult.setMessage("Added field "+result.getFieldName());

                    break;
                case REINTRODUCE:
                    edasoResult.setSignificance(EdasomindSignificance.HIGH);
                    edasoResult.setMessage("Reintroduced field "+result.getFieldName());

                    break;
                case REMOVE_FIELD:
                    edasoResult.setSignificance(EdasomindSignificance.MEDIUM);
                    edasoResult.setMessage("Removed field "+result.getFieldName());

                    break;
                default:
                    break;
                }
            }
        }
        return list;
    }

}

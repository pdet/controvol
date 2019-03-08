package com.maximilian_boehm.schemavalidator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.maximilian_boehm.javasourceparser.access.JPAccessFactory;
import com.maximilian_boehm.javasourceparser.access.struct.JPClass;
import com.maximilian_boehm.javasourceparser.access.struct.JPField;
import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultType;
import com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager;
import com.maximilian_boehm.schemavalidator.model.result.SVCompareResultImpl;
import com.maximilian_boehm.schemavalidator.model.result.SVCompareResultTableImpl;
import com.maximilian_boehm.schemavalidator.model.schema.SVFieldCondition;
import com.maximilian_boehm.schemavalidator.model.schema.SVSchema;

public class SVSchemaManagerImpl implements SVSchemaManager{

    private final List<SVSchema> listSchema = new ArrayList<SVSchema>();
    private final boolean bDebug = false;

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager#addSchemaByFile(java.io.File, java.util.Calendar)
     */
    @Override
    public void addSchemaByFile(File f, Calendar date) throws Exception {
        JPClass jpClass = JPAccessFactory.getHome().getParsedClass(f);
        addSchemaByFile(jpClass, date);
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager#addSchemaByFile(com.maximilian_boehm.javasourceparser.access.struct.JPClass, java.util.Calendar)
     */
    @Override
    public void addSchemaByFile(JPClass jpClass, Calendar date) throws Exception {
        // Create new Schema
        SVSchema schema = new SVSchema();
        // Set date
        schema.setDate(date);

        // iterate over fields
        for(JPField field:jpClass.getFields()){
            // has the field annotations?
            if(field.hasAnnotations())
                // iterate over annotations
                for(JPAnnotation anno:field.getAnnotations()){
                    // is it the "AlsoLoad"-Annotation?
                    if(anno.getType().equals("AlsoLoad")){
                        // retrieve the field which should get loaded additionally
                        String value = anno.getAttributes().get("value");
                        // create a new condition
                        SVFieldCondition cond = new SVFieldCondition();
                        cond.setKey(value);
                        cond.setField(field);
                        // Add the condition to the schema
                        schema.addCondition(cond);
                    }
                }

            // create condition for the variable
            SVFieldCondition cond = new SVFieldCondition();
            // set name of variable as key
            cond.setKey(field.getName());
            cond.setField(field);

            // add condition to schema
            schema.addCondition(cond);
        }
        // add the schema to the list of conditions
        listSchema.add(schema);

        // print the conditions
        if(bDebug)
            schema.printConditions();
    }

    /* (non-Javadoc)
     * @see com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager#compareSchemata()
     */
    @Override
    public List<SVCompareResultTable> compareSchemata() throws Exception {
        // Create new List for ResultTables
        List<SVCompareResultTable> listResults = new ArrayList<SVCompareResultTable>();

        // ##################################################
        // ##################################################
        // Create Pairs of Schema for Comparison, e.g.
        // ...Schema 3 & Schema 4
        //    Schema 2 & Schema 3
        //    Schema 1 & Schema 2
        // During this pairing, compare them
        // ##################################################
        // ##################################################
        for (int i = (listSchema.size()-1); i != 0; i--)
            compare(listSchema.get(i-1), listSchema.get(i), listResults);

        // return list of tables with results
        return listResults;
    }

    public void compare(SVSchema schemaNEW, SVSchema schemaOLD, List<SVCompareResultTable> listResults) {
        //        System.out.println("schema "+schemaOLD.getDate().getTime() +" vs. "+schemaNEW.getDate().getTime());
        SVCompareResultTableImpl tableImpl = new SVCompareResultTableImpl();
        tableImpl.setDateNewFile(schemaNEW.getDate());
        tableImpl.setDateOldFile(schemaOLD.getDate());

        // Iterate over Conditions from the NEW Schema
        for(Map.Entry<String,SVFieldCondition> entry:schemaNEW.getMap().entrySet()) {
            // Get Key
            String sKey = entry.getKey();
            // Get Condition
            SVFieldCondition condNEW = entry.getValue();

            // Field not present in old schema?
            if(!schemaOLD.hasCondition(sKey)){
                // ######################################
                // Case 1: Field got reintroduced
                // ######################################
                if(wasFieldPreviouslyRemoved(sKey, listResults))
                    addResult(tableImpl, sKey, null, null, SVCompareResultType.REINTRODUCE);

                // ######################################
                // Case 2: Field got added
                // ######################################
                addResult(tableImpl, sKey, null, condNEW.getField(), SVCompareResultType.ADD_FIELD);
            }

            else {
                SVFieldCondition condOLD =  schemaOLD.getCondition(sKey);
                // ######################################
                // Case 3: Field exists, but maybe another data-type?
                // ######################################
                if(!condOLD.getField().getType().equals(condNEW.getField().getType()))
                    addResult(tableImpl, sKey, condOLD.getField(), condNEW.getField(), SVCompareResultType.CHANGE_FIELD);

                // ######################################
                // Case 4: Field got NotSaved but this was later removed!
                // ######################################
                if(condOLD.getField().hasAnnotation("NotSaved") && !condNEW.getField().hasAnnotation("NotSaved"))
                    addResult(tableImpl, sKey, null, null, SVCompareResultType.REINTRODUCE);
            }
        }

        // Remove all entries from schemaNEW from schemaOLD because they already
        // have been handled
        schemaOLD.getMap().entrySet().removeAll(schemaNEW.getMap().entrySet());

        // Iterate over Conditions from Schema #1
        for(Map.Entry<String,SVFieldCondition> entry:schemaOLD.getMap().entrySet()) {
            String sKey = entry.getKey();
            SVFieldCondition value = entry.getValue();
            // ######################################
            // Case 5: Field was removed
            // ######################################
            addResult(tableImpl, sKey, null, value.getField(), SVCompareResultType.REMOVE_FIELD);
        }

        listResults.add(tableImpl);
    }

    /**
     * Check if the field has been removed previously
     */
    private boolean wasFieldPreviouslyRemoved(String sField, List<SVCompareResultTable> listResults) {
        // Iterate over all result-tables
        for(SVCompareResultTable resultTable:listResults)
            // are there results in the table?
            if(resultTable.hasResults())
                // iterate over the results
                for(SVCompareResult result:resultTable.getResults())
                    // was the field removed?
                    if(result.getFieldName().equals(sField) && result.getType() == SVCompareResultType.REMOVE_FIELD)
                        return true;
        return false;
    }

    /**
     * Create and add a new result
     */
    private void addResult(SVCompareResultTableImpl tableImpl, String sKey, JPField fieldNEW, JPField fieldOLD, SVCompareResultType type){
        SVCompareResultImpl result = new SVCompareResultImpl();
        result.setType(type);
        result.setFieldName(sKey);
        result.setOldField(fieldOLD);
        result.setNewField(fieldNEW);
        tableImpl.addResult(result);
    }

}

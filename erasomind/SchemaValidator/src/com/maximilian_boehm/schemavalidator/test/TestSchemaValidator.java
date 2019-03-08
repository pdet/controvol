package com.maximilian_boehm.schemavalidator.test;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;

import com.maximilian_boehm.schemavalidator.access.SVAccessFactory;
import com.maximilian_boehm.schemavalidator.access.SVHome;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResult;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultTable;
import com.maximilian_boehm.schemavalidator.access.struct.SVCompareResultType;
import com.maximilian_boehm.schemavalidator.access.struct.SVSchemaManager;
import com.maximilian_boehm.schemavalidator.model.result.SVCompareResultImpl;
public class TestSchemaValidator {

    private SVHome home;
    private String sPath;

    /**
     * Retrieve Java-Source-File from Test-Data
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Get the test-file
        sPath = TestSchemaValidator.class.getResource("testdata/Locate.txt").getFile();

        // Workaround: Get path to src-directory
        sPath = sPath.replace("SchemaValidator/bin/com/", "SchemaValidator/src/com/");
        sPath = sPath.replace("testdata/Locate.txt", "testdata/");

        home = SVAccessFactory.getSVHome();

    }

    /**
     * Test the recognition of a new field
     * @throws Exception
     */
    @org.junit.Test
    public void testNewField() throws Exception {
        SVSchemaManager manager = home.createSchemaManager();
        // Add Schemata to Manager
        manager.addSchemaByFile(new File(sPath+"Test_Add_After.java" ), Calendar.getInstance());
        manager.addSchemaByFile(new File(sPath+"Test_Add_Before.java"), getCalendar(1));
        // Get first and only result
        SVCompareResult result = assureOneResult(manager);

        // Assure the right result
        Assert.assertEquals(SVCompareResultType.ADD_FIELD, result.getType());
        Assert.assertEquals("ABC",result.getFieldName());
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testRemoveField() throws Exception {
        SVSchemaManager manager = home.createSchemaManager();
        manager.addSchemaByFile(new File(sPath+"Test_Delete_After.java" ), Calendar.getInstance());
        manager.addSchemaByFile(new File(sPath+"Test_Delete_Before.java"), getCalendar(1));

        SVCompareResult result = assureOneResult(manager);
        Assert.assertEquals(SVCompareResultType.REMOVE_FIELD, result.getType());
        Assert.assertEquals("ABC", result.getFieldName());
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testNotSaved() throws Exception {
        SVSchemaManager manager = home.createSchemaManager();
        manager.addSchemaByFile(new File(sPath+"Test_NotSaved_After.java" ), Calendar.getInstance());
        manager.addSchemaByFile(new File(sPath+"Test_NotSaved_Before.java"), getCalendar(1));

        SVCompareResult result = assureOneResult(manager);
        Assert.assertEquals(SVCompareResultType.REINTRODUCE, result.getType());
        Assert.assertEquals("ABC", result.getFieldName());
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testChangeDatatype() throws Exception {
        SVSchemaManager manager = home.createSchemaManager();
        manager.addSchemaByFile(new File(sPath+"Test_Datatype_After.java" ), Calendar.getInstance());
        manager.addSchemaByFile(new File(sPath+"Test_Datatype_Before.java"), getCalendar(1));

        SVCompareResult result = assureOneResult(manager);
        Assert.assertEquals(SVCompareResultType.CHANGE_FIELD, result.getType());
        Assert.assertEquals("ABC", result.getFieldName());
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testReintroduceField() throws Exception {
        SVSchemaManager manager = home.createSchemaManager();
        manager.addSchemaByFile(new File(sPath+"Test_Reintroduce_After.java" ), Calendar.getInstance());
        manager.addSchemaByFile(new File(sPath+"Test_Reintroduce_Middle.java" ), getCalendar(1));
        manager.addSchemaByFile(new File(sPath+"Test_Reintroduce_Before.java" ), getCalendar(2));

        List<SVCompareResultTable> listResult = manager.compareSchemata();

        // 2 Results need to be there
        Assert.assertEquals(listResult.size(), 2);
        // Compare results
        Assert.assertThat(listResult.get(0).getResults(), equalTo(getResultList0()));
        Assert.assertThat(listResult.get(1).getResults(), equalTo(getResultList1()));
    }

    /**
     * Create result for comparison
     * @return
     */
    private List<SVCompareResult> getResultList1() {
        List<SVCompareResult> listResult = new ArrayList<SVCompareResult>();
        listResult.add(new SVCompareResultImpl(SVCompareResultType.REINTRODUCE, "ABC"));
        listResult.add(new SVCompareResultImpl(SVCompareResultType.ADD_FIELD, "ABC"));
        return listResult;
    }
    /**
     * Create result for comparison
     * @return
     */
    private List<SVCompareResult> getResultList0() {
        List<SVCompareResult> listResult = new ArrayList<SVCompareResult>();
        listResult.add(new SVCompareResultImpl(SVCompareResultType.REMOVE_FIELD, "ABC"));
        return listResult;
    }

    /**
     * Create Calendar decremented days by param
     * @param nDayMinus
     * @return
     */
    private Calendar getCalendar(int nDayMinus){
        // create new calendar (today, now)
        Calendar cal = Calendar.getInstance();
        // iterate over param
        for (int i = 0; i < nDayMinus; i++)
            // decrement by param
            cal.roll(Calendar.DAY_OF_MONTH, false);
        return cal;
    }

    /**
     * @param manager
     * @return
     * @throws Exception
     */
    private SVCompareResult assureOneResult(SVSchemaManager manager) throws Exception{
        // Compare Schemata
        List<SVCompareResultTable> listResultTable = manager.compareSchemata();
        // Just one result
        Assert.assertEquals(listResultTable.size(), 1);
        // Get first and only resulttable
        SVCompareResultTable resultTable = listResultTable.get(0);

        // results need to be there
        Assert.assertEquals(resultTable.hasResults(), true);
        // exactly one
        Assert.assertEquals(resultTable.getResults().size(), 1);

        // return result-list
        return resultTable.getResults().get(0);
    }

}

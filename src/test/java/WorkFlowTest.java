import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;


public class WorkFlowTest {
    Workflow wf1, wf2;
    File testFile1, testFile2;
    String filename1 = "src/test/testFile1.txt";
    String filename2 = "src/test/testFile2.txt";

    String testFile1text = "123,REVIEW,101101010,John,Smith,12/12/1968,Male,White,Sydney,Australia\n" +
            "678,REVIEW,202202020,Harry,Potter,07/31/1980,Male,Asian,London,England\n" +
            "543,APPROVE,303303030,Rita,Skeeter,03/28/1998,Female,African,Cape Town,South Africa\n" +
            "555,APPROVE,404404040,Julian,Rogers,05/17/1980,Male,Hispanic,Mexico City,Mexico\n" +
            "111,DONE,505505050,Jane,Smith,01/01/1970,Female,White,Hamburg,Germany";
    String testFile2text = "123,REVIEW,101101010,John,Smith,12/12/1968,Male,White,Sydney,Australia\n" +
            "678,REVIEW,202202020,Harry,Potter,07/31/1980,Male,Asian,London,England\n" +
            "543,APPROVE,303303030,Rita,Skeeter,03/28/1998,Female,African,Cape Town,South Africa\n" +
            "555,APPROVE,404404040,Julian,Rogers,05/17/1980,Male,Hispanic,Mexico City,Mexico";

    @BeforeEach
    void setup() {
        try {
            testFile1 = new File(filename1);
            testFile2 = new File(filename2);

            if(testFile1.createNewFile()) {
                FileWriter writer = new FileWriter(filename1);
                writer.write(testFile1text);
                writer.close();
            }
            if(testFile2.createNewFile()) {
                FileWriter writer = new FileWriter(filename2);
                writer.write(testFile2text);
                writer.close();
            }

            wf1 = new Workflow(filename1);
            wf2 = new Workflow(filename2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void breakdown() {
        testFile1.delete();
        testFile2.delete();
    }

    @Test
    void getWFItemTest1() {
        // tests that form gets correct formID given NextStep.REVIEW
        int id1 = wf1.getWFItem(NextStep.REVIEW);
        Assertions.assertEquals(123, id1);
    }

    @Test
    void getWFItemTest2() {
        // tests that form gets correct formID given NextStep.APPROVE
        int id1 = wf1.getWFItem(NextStep.APPROVE);
        Assertions.assertEquals(543, id1);
    }

    @Test
    void getWFItemTest3() {
        // tests that form gets correct formID given NextStep.DONE
        int id1 = wf1.getWFItem(NextStep.DONE);
        Assertions.assertEquals(111, id1);
    }

    @Test
    void getWFItemTest4() {
        // tests that form returns -1 if no forms with NextStep exists
        Assertions.assertEquals(-1, wf2.getWFItem(NextStep.DONE));
    }

    @Test
    void updateWFItemTest1() {
        // tests that form return true on update
        Assertions.assertTrue(wf1.updateWFItem(123, NextStep.APPROVE));
    }

    @Test
    void updateWFItemTest2() {
        // tests that form correctly updates form
        wf1.updateWFItem(123, NextStep.APPROVE);
        Assertions.assertEquals(123, wf1.getWFItem(NextStep.APPROVE));
    }
}

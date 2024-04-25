import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

class NewImmFormTest {
    NewImmForm nif1;
    String[] testData1 = new String[]{"A101101010", "John", "Smith", "01/01/1990", "Male", "White", "Sydney", "Australia"};
    File testFile1;
    String testFile1text = "123,REVIEW,101101010,John,Smith,12/12/1968,Male,White,Sydney,Australia\n" +
            "678,REVIEW,202202020,Harry,Potter,07/31/1980,Male,Asian,London,England\n" +
            "543,APPROVE,303303030,Rita,Skeeter,03/28/1998,Female,African,Cape Town,South Africa\n" +
            "555,APPROVE,404404040,Julian,Rogers,05/17/1980,Male,Hispanic,Mexico City,Mexico\n" +
            "111,DONE,505505050,Jane,Smith,01/01/1970,Female,White,Hamburg,Germany";

    String filename1 = "src/test/testFile1.txt";


    @BeforeEach
    void setup() {
        try {
            testFile1 = new File(filename1);
            if(testFile1.createNewFile()) {
                FileWriter writer = new FileWriter(filename1);
                writer.write(testFile1text);
                writer.close();
            }
            nif1 = new NewImmForm(filename1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void breakdown() {
        testFile1.delete();
    }

    @Test
    void addNewImmFormTest1() {
        // tests that successfully adds form to dbList
        int formID = nif1.addNewImmForm(testData1);
        Form form1 = nif1.getNewImmForm(formID);
        Assertions.assertEquals(formID, form1.formID);
    }


    @Test
    void getNewImmFormTest1() {
        // tests that gets correct form
        int formID = nif1.addNewImmForm(testData1);
        Form form1 = nif1.getNewImmForm(formID);
        Assertions.assertEquals(formID, form1.formID);
    }

    @Test
    void getNewImmFormTest2() {
        // tests that returns null if formID does not exist
        int formID = nif1.addNewImmForm(testData1);
        Assertions.assertNull(nif1.getNewImmForm(formID-1));
    }

    @Test
    void updateNewImmFormTest1() {
        // tests that returns false given nonexistent formID
        int formID = nif1.addNewImmForm(testData1);
        Form form2 = new Form(formID-1, NextStep.REVIEW, "A101101010", "Jackson", "Smith", "01/01/1990", "Male", "White", "Sydney", "Australia");
        Assertions.assertFalse(nif1.updateNewImmForm(form2));
    }
}
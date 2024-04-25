import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

class NewImmFormTest {
    NewImmForm nif1;
    String[] testData1 = new String[]{"101101010","John","Smith","01/01/1990","Male","White","Sydney","Australia"};
    File testFile1;

    String filename1 = "src/test/testFile1.txt";


    @BeforeEach
    void setup() {
        try {
            testFile1 = new File(filename1);
            testFile1.createNewFile();
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
    void addNewImmFormTest2() {
        //tests that successfully adds form to database
        int formID = nif1.addNewImmForm(testData1);

    }
}
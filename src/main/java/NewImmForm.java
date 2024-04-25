import java.util.ArrayList;
import java.util.Random;

// This is the shared business object that will be used in the product
public class NewImmForm {
    private ArrayList<Form> dbList;
    private String fileName;

    // Constructor initializes the ArrayList from .txt file
    // ArrayList will be used to quickly access data
    NewImmForm(String fileName) {
        this.dbList = Util.toList(fileName);
        this.fileName = fileName;
    }

    // generates a random 3 digit number to be the formID
    // appends formID, NextStep=REVIEW, and info to the database (.txt file)
    // returns -1 if not successful
    // info = {ANumber, firstName, lastName, dob, gender, ethnicity, city, country}
    public int addNewImmForm(String[] info) {
        // generate random formID
        Random rand = new Random();
        int formID = rand.nextInt(1000);

        // add new Form to local dbList
        Form newForm = new Form(formID, NextStep.REVIEW, info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7]);
        dbList.add(newForm);

        // update actual database
        if (Util.updateDatabase(fileName, newForm, true)){
            return formID;
        }
        return -1;
    }

    // given a formID returns Form object matching the formID
    // return null if not found
    public Form getNewImmForm(int formID) {
        for (Form form : dbList) {
            if (form.formID == formID) {
                return form;
            }
        }
        return null;
    }

    // given a formObj with existing formID, updates database (.txt file)
    // returns true on successful update
    // returns false on unsuccessful update or database does not contain that formID
    public boolean updateNewImmForm(Form formObj) {
        System.out.println("In NewImmForm.java - " + formObj.lastName);
        if(Util.updateDatabase(fileName, formObj, false)) {
            for(int i = 0; i < dbList.size(); i++) {
                Form oldForm = dbList.get(i);
                if(oldForm.formID == formObj.formID) {
                    oldForm.ANumber = formObj.ANumber;
                    oldForm.firstName = formObj.firstName;
                    oldForm.lastName = formObj.lastName;
                    oldForm.dob = formObj.dob;
                    oldForm.gender = formObj.gender;
                    oldForm.ethnicity = formObj.ethnicity;
                    oldForm.city = formObj.city;
                    oldForm.country = formObj.country;
                }
            }
            return true;
        }
        return false;
    }
}
import java.util.ArrayList;

//This is the shared workflow class that will be used in the project
public class Workflow{
    private ArrayList<Form> dbList;
    private String fileName;

    Workflow(String fileName) {
        this.dbList = Util.toList(fileName);
        this.fileName = fileName;
    }

    // given the step (REVIEW or APPROVE) returns the next formID to be reviewed or approved
    // return -1 on failure or if no more files with that NextStep exist
    public int getWFItem(NextStep nextStep) {
        int numForms = dbList.size();
        Form form;
        for(int i = 0; i < numForms; i++) {
            form = dbList.get(i);
            if(form.nextStep == nextStep) {
                return form.formID;
            }
        }
        return -1;
    }

    // this method updates that form in the database to the updated NextStep
    // returns true on successful update, false on failure or if no form with that formID was found
    public boolean updateWFItem(int formID, NextStep nextStep){
        int numForms = dbList.size();
        Form form;
        for(int i = 0; i < numForms; i++) {
            form = dbList.get(i);
            if(form.formID == formID){
                Form updatedForm = new Form(form.formID, nextStep, form.ANumber, form.firstName, form.lastName, form.dob, form.gender, form.ethnicity, form.city, form.country);

                // update database
                if(Util.updateDatabase(fileName, updatedForm, false)) {
                    // update local ArrayList
                    form.nextStep = nextStep;
                    return true;
                }
            }
        }
        return false;
    }
}
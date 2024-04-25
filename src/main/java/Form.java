public class Form {
    public int formID;
    public NextStep nextStep;
    public String ANumber;
    public String firstName;
    public String lastName;
    public String dob;
    public String gender;
    public String ethnicity;
    public String city;
    public String country;

    Form (int formID, NextStep nextStep, String ANumber, String firstName, String lastName, String dob, String gender, String ethnicity, String city, String country) {
        this.formID = formID;
        this.nextStep = nextStep;
        this.ANumber = ANumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return(formID+","+ANumber+","+firstName+","+lastName+","+dob+","+gender+","+ethnicity+","+city+","+country);
    }
}

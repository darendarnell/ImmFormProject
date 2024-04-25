import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public final class Util {

    //Reads the whole file and changes it into an array
    // parses file and creates ArrayList<Form> version of database (the file)
    public static ArrayList<Form> toList(String fileName) {
        ArrayList<Form> dbList = new ArrayList<>();
        File dbFile = new File(fileName);
        String line;
        String[] splitLine;
        try (BufferedReader br = new BufferedReader(new FileReader(dbFile))){
            while((line = br.readLine()) != null) {
                splitLine = line.split(",");
                System.out.println(splitLine[0]);
                dbList.add(new Form(Integer.parseInt(splitLine[0]), toNextStep(splitLine[1]), splitLine[2], splitLine[3], splitLine[4], splitLine[5], splitLine[6], splitLine[7], splitLine[8], splitLine[9]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbList;
    }

    // updates file given updated Form object
    public static boolean updateDatabase(String fileName, Form updatedForm, boolean append) {
        File file = new File(fileName);
        String line, newLine;
        String[] splitLine;
        boolean success = false;

        // convert updatedForm into string for .txt file
        newLine = updatedForm.formID+","+updatedForm.nextStep+","+updatedForm.ANumber+","+updatedForm.firstName+","+updatedForm.lastName+","+updatedForm.dob+","+updatedForm.gender+","+updatedForm.ethnicity+","+updatedForm.city+","+updatedForm.country;
        System.out.println("In Util.java - " + newLine);
        if(append) {
            try {
                FileWriter writer = new FileWriter(fileName, true);
                writer.write(newLine);
                writer.write(System.lineSeparator());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            try {
                // create updated file as a String
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder modifiedFile = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    splitLine = line.split(",");
                    if (Integer.parseInt(splitLine[0]) == updatedForm.formID) {
                        modifiedFile.append(newLine);
                        modifiedFile.append('\n');
                        success = true;
                    } else {
                        modifiedFile.append(line);
                        modifiedFile.append('\n');
                    }
                }
                reader.close();

                // overwrite original file with modified file String
                FileOutputStream writer = new FileOutputStream(file);
                writer.write(modifiedFile.toString().getBytes());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return success;
    }

    // Return NextStep corresponding to string
    // **NOT CASE SENSITIVE**
    public static NextStep toNextStep(String nxtStepStr) {
        String str = nxtStepStr.toUpperCase();
        if (str.equals("REVIEW")) {
            return NextStep.REVIEW;
        };
        if (str.equals("APPROVE")) {
            return NextStep.APPROVE;
        }
        if (str.equals("DONE")) {
            return NextStep.DONE;
        }
        return null;
    }

    public static ArrayList<String[]> readApplicantInfo() {
        ArrayList<String[]> applicantList = new ArrayList<>();
        int formID = 10011;

        // File path is passed as parameter
        File file = new File("workers.txt");

        // Creating an object of BufferedReader class
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;
            while ((st = br.readLine()) != null) {
                String[] appArray = new String[6];
                appArray[0] = String.valueOf(formID++);
                for (int i = 1; i < 6; i++) {
                    appArray[i] = st;
                    if (i != 5) {
                        st = br.readLine();
                    }
                }
                applicantList.add(appArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print the applicant list
        for (String[] applicant : applicantList) {
            for (String field : applicant) {
                System.out.print(field + " ");
            }
            System.out.println();
        }

        return applicantList;
    }
}
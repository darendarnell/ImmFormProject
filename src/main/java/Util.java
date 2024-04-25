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
        if(append) {
            try {
                FileWriter writer = new FileWriter(fileName, true);
                writer.write(newLine);
                writer.write(System.lineSeparator());
                writer.close();
                success = true;
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
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class ReviewScreen {

    static JButton getForm;
    static JButton savesubmit;
    static JButton check;
    static JButton verify;

    static int currID;
    static NewImmForm immForm;
    static Workflow workflow;

    public static void main(String[] args) {
        // ANumber field
        JPanel aNumPanel = new JPanel();
        JLabel aNumDescr = new JLabel("ANumber: ");
        JTextField aNumText = new JTextField();
        aNumText.setPreferredSize(new Dimension(100,35));
        aNumPanel.add(aNumDescr);
        aNumPanel.add(aNumText);

        // First Name field
        JPanel fNamePanel = new JPanel();
        JLabel fNameDescr = new JLabel("First Name: ");
        JTextField fNameText = new JTextField();
        fNameText.setPreferredSize(new Dimension(100,35));
        fNamePanel.add(fNameDescr);
        fNamePanel.add(fNameText);

        // Last Name field
        JPanel lNamePanel = new JPanel();
        JLabel lNameDescr = new JLabel("Last Name: ");
        JTextField lNameText = new JTextField();
        lNameText.setPreferredSize(new Dimension(100,35));
        lNamePanel.add(lNameDescr);
        lNamePanel.add(lNameText);

        // dob field
        JPanel dobPanel = new JPanel();
        JLabel dobDescr = new JLabel("Date of Birth (mmddyy): ");
        JTextField dobText = new JTextField();
        dobText.setPreferredSize(new Dimension(100,35));
        dobPanel.add(dobDescr);
        dobPanel.add(dobText);

        // gender field
        JPanel genderPanel = new JPanel();
        JLabel genderDescr = new JLabel("Gender: ");
        JTextField genderText = new JTextField();
        genderText.setPreferredSize(new Dimension(100,35));
        genderPanel.add(genderDescr);
        genderPanel.add(genderText);

        // ethnicity field
        JPanel ethnicityPanel = new JPanel();
        JLabel ethnicityDescr = new JLabel("Ethnicity: ");
        JTextField ethnicityText = new JTextField();
        ethnicityText.setPreferredSize(new Dimension(100,35));
        ethnicityPanel.add(ethnicityDescr);
        ethnicityPanel.add(ethnicityText);

        // City field
        JPanel cityPanel = new JPanel();
        JLabel cityDescr = new JLabel("City: ");
        JTextField cityText = new JTextField();
        cityText.setPreferredSize(new Dimension(100,35));
        cityPanel.add(cityDescr);
        cityPanel.add(cityText);

        // Country field
        JPanel countryPanel = new JPanel();
        JLabel countryDescr = new JLabel("Country: ");
        JTextField countryText = new JTextField();
        countryText.setPreferredSize(new Dimension(100,35));
        countryPanel.add(countryDescr);
        countryPanel.add(countryText);

        JPanel form = new JPanel();
        form.setLayout(new GridLayout(8, 1, 0, 10));
        form.add(aNumPanel);
        form.add(fNamePanel);
        form.add(lNamePanel);
        form.add(dobPanel);
        form.add(genderPanel);
        form.add(ethnicityPanel);
        form.add(cityPanel);
        form.add(countryPanel);

        JPanel footer = new JPanel();
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setPreferredSize(new Dimension(100,80));
        getForm = new JButton("Get Form");
        savesubmit = new JButton("Save & Submit");
        check = new JButton("Check");
        verify = new JButton("Verify");
        footer.add(getForm);
        footer.add(savesubmit);
        footer.add(check);
        footer.add(verify);

        JFrame frame = new JFrame();
        frame.setTitle("ReviewScreen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.setVisible(true);
        frame.add(form, BorderLayout.NORTH);
        frame.add(footer, BorderLayout.SOUTH);


        immForm = new NewImmForm("src/workers.txt");
        workflow = new Workflow("src/workers.txt");

        getForm.addActionListener(e -> {
            immForm = new NewImmForm("src/workers.txt");
            workflow = new Workflow("src/workers.txt");
            if ((currID = workflow.getWFItem(NextStep.REVIEW)) == -1) {
                showMessageDialog(null, "No more review items in workflow.");
            }
            else {
                Form currForm = immForm.getNewImmForm(currID);
                aNumText.setText(currForm.ANumber);
                fNameText.setText(currForm.firstName);
                lNameText.setText(currForm.lastName);
                dobText.setText(currForm.dob);
                genderText.setText(currForm.gender);
                ethnicityText.setText(currForm.ethnicity);
                cityText.setText(currForm.city);
                countryText.setText(currForm.country);
            }
        });

        savesubmit.addActionListener(e -> {
            Form updatedForm = new Form(currID, NextStep.APPROVE, aNumText.getText(),
                    fNameText.getText(), lNameText.getText(), dobText.getText(), genderText.getText(),
                    ethnicityText.getText(), cityText.getText(), countryText.getText());
            System.out.println("In ReviewScreen.java - " + lNameText.getText());
            workflow.updateWFItem(currID, NextStep.APPROVE);
            immForm.updateNewImmForm(updatedForm);
        });

        check.addActionListener(e -> {
            Form currForm = immForm.getNewImmForm(currID);
            ArrayList<Form> currDB = immForm.getDatabase();

            int numForms = currDB.size();
            for(int i = 0; i < numForms; i++) {
                if(currDB.get(i).ANumber.equals(currForm.ANumber) || currDB.get(i).formID != currID) {
                    showMessageDialog(null, "ALERT: Two instance of person with A#: " + currForm.ANumber + " in database!");
                }
            }
        });

        verify.addActionListener(e -> {
            showMessageDialog(null, "VALID");
        });
    }
}
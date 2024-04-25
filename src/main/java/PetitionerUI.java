import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * UI class for the entry process. Utilizes Swing to create a basic UI
 * and turns the input into a field that later gets written into a file
 * called workers.txt
 */
public class PetitionerUI extends JFrame{

    private JTextField firstName;
    private JTextField lastName;
    private JTextField dob;
    private JTextField id;
    private JTextField gender;
    private JTextField ethnicity;
    private JTextField personAddress;

    private JButton submitButton;

    /**
     * constructor that sets the title, size, and backend exit operation.
     */
    public PetitionerUI() {
        setTitle("Petitioner Information");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initialize();

    }
    private boolean isNumeric(String inp) {
        for (int i = 0; i < inp.length(); i++) {
            if (!Character.isDigit(inp.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Core of this UI. Initializes all the buttons, graphics, and labels.
     * Creates a submit button as well that turns all input into fields that
     * write in the information.
     */
    private void initialize() {
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        firstName = new JTextField(20);
        lastName = new JTextField(20);
        dob = new JTextField(20);
        id = new JTextField(20);
        gender = new JTextField(20);
        ethnicity = new JTextField(20);
        personAddress = new JTextField(20);
        submitButton = new JButton("Submit");
        panel.add(new JLabel("First Name:"));
        panel.add(firstName);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastName);
        panel.add(new JLabel("Date of Birth (mmddyy):"));
        panel.add(dob);
        panel.add(new JLabel("A-Number:"));
        panel.add(id);
        panel.add(new JLabel("Gender: (Male/Female/Other"));
        panel.add(gender);
        panel.add(new JLabel("Ethnicity"));
        panel.add(ethnicity);
        panel.add(new JLabel("Location (City, Country)"));
        panel.add(personAddress);
        panel.add(submitButton);
        /**
         * Inner class that validates input and writes it into a file.
         * This specific action happens when the submit button is pressed.
         * Validations are:
         * 6 digit birthday with only numbers
         * Gender input of Male, Female, or Other
         */
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                String first = firstName.getText();
                String last = lastName.getText();

                String birthday = dob.getText();
                String identification = id.getText();
                String mf = gender.getText();
                String eth = ethnicity.getText();
                String perAdd = personAddress.getText();
                String[] cityCountry = perAdd.split(",");
                String[] info = {identification, first, last, birthday, mf, eth, cityCountry[0], cityCountry[1]};
                if (birthday.length() != 6) {
                    JOptionPane.showMessageDialog(null, "Birthday must be 6 digits long (no slashes)", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else if (!isNumeric(birthday)) {
                    JOptionPane.showMessageDialog(null, "Birthday must only contain numbers (no slashes)", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else if ((!mf.equals("Male")) && (!mf.equals("Female")) && (!mf.equals("Other"))) {
                    JOptionPane.showMessageDialog(null, "Gender must be Male, Female, or Other (capitalize first letter)", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    NewImmForm newImm = new NewImmForm("src/workers.txt");
                    newImm.addNewImmForm(info);
                    JOptionPane.showMessageDialog(null, "Information logged.", "Success",JOptionPane.INFORMATION_MESSAGE);
                }


            }
        });



    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PetitionerUI().setVisible(true);
            }
        });
    }

}
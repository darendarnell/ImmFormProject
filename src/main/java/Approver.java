import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;
import java.util.*;

public class Approver extends JFrame{

    final private Font mainfont = new Font("Segoe print", Font.BOLD, 18);
    int currentApplicantNumber = -1;

    JPanel loginPanel;
    JPanel mainPanel;
    JPanel applicantInfoPanel;
    JPanel buttonsPanel;

    Workflow nextWF;    //Workflow item
    int nextFormID;     //FormID
    String applicant;   //Application Information


    //The purpose of this method is the read the information
    //of the next applicant waiting for approval in the database

    public String readApplicantInfo(){

        //Get the next workflow item
        nextWF = new Workflow("src/workers.txt");
        nextFormID = nextWF.getWFItem(NextStep.APPROVE);

        //Get the next immigrant form
        NewImmForm newForm = new NewImmForm("src/workers.txt");
        Form applicantList = newForm.getNewImmForm(nextFormID);

        //Check if the information is null
        if(applicantList == null){
            return null;
        }
        //Convert the form to a string
        return applicantList.toString();

    }//end readApplicantInfo


    public void initialize(){
        applicant = readApplicantInfo();

        if(applicant == null){
            JOptionPane.showMessageDialog(mainPanel, "You went through all the applicants in the list", "End of Applicants", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Form Pane 
        JLabel lbName = new JLabel("Employee Name: Swetha Sathishkumar");
        lbName.setFont(mainfont);

        JLabel lbTitle = new JLabel("Employee Role: Approver");
        lbTitle.setFont(mainfont);

        JButton btnLoad = new JButton("Load Next Applicant");
        btnLoad.setFont(mainfont);
        //Listener for load next application button. Gets called when the user clicks it
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                initialize();
                //Only show the applicant information if there is one. Do not show null values
                if(applicant != null){
                    applicantInfoPanel.setVisible(true);
                    buttonsPanel.setVisible(true);
                }
                else{
                    applicantInfoPanel.setVisible(false);
                    buttonsPanel.setVisible(false);
                }
            }
        });

        //Create a header panel to contain the components listed above
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(4,1,5,5));
        headerPanel.setOpaque(false);   //Sets header panel transparent
        headerPanel.add(lbName);    //adds all the elements
        headerPanel.add(lbTitle);
        headerPanel.add(btnLoad);

        //This reads the content of the application and stores it in an array
        String[] values = applicant.split(",");

        JLabel aNum = new JLabel("A-Number: " + values[1]);
        aNum.setFont(mainfont);
        JLabel appName = new JLabel("Applicant Name: " + values[2] + " " + values[3]);
        appName.setFont(mainfont);
        JLabel appDOB = new JLabel("Applicant Date of Birth: " + values[4]);
        appDOB.setFont(mainfont);
        JLabel appGener = new JLabel("ApplicantGender: " + values[5]);
        appGener.setFont(mainfont);
        JLabel appEthnicity = new JLabel("Applicant Ethnicity: " + values[6]);
        appEthnicity.setFont(mainfont);
        JLabel appLoc = new JLabel("Applicant Location: " + values[7]);
        appLoc.setFont(mainfont);


        //Applicant Information Panel
        applicantInfoPanel = new JPanel();
        applicantInfoPanel.setLayout(new GridLayout(6, 1, 5, 2));
        applicantInfoPanel.setBackground(new Color(128, 128, 255));
        applicantInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //add all the elements to the applicant information panel
        applicantInfoPanel.add(aNum);
        applicantInfoPanel.add(appName);
        applicantInfoPanel.add(appDOB);
        applicantInfoPanel.add(appGener);
        applicantInfoPanel.add(appEthnicity);
        applicantInfoPanel.add(appLoc);
        applicantInfoPanel.setVisible(false);

        //The Buttons panel will contain the approve and the reject features
        JButton btnReject = new JButton("Reject Application");  //Reject button
        btnReject.setFont(mainfont);
        //This is the listener for the reject button
        btnReject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //make the other panels invisible
                applicantInfoPanel.setVisible(false);
                buttonsPanel.setVisible(false);
                //Send a rejection message
                JOptionPane.showMessageDialog(mainPanel, "Application is rejected! The reviewer will be notified soon!", "Application Rejected", JOptionPane.INFORMATION_MESSAGE);
                //Update the workflow item
                nextWF.updateWFItem(nextFormID, NextStep.REVIEW);
            }
        });


        JButton btnApprove = new JButton("Approve Application");    //Approve button
        btnApprove.setFont(mainfont);
        //This is the listener for the approve button
        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //Make the other panels invisible
                applicantInfoPanel.setVisible(false);
                buttonsPanel.setVisible(false);
                //notify the user
                JOptionPane.showMessageDialog(mainPanel, "Application is approved! The petitioner will be notified soon!", "Application Approved", JOptionPane.INFORMATION_MESSAGE);
                //Update the workflow item
                nextWF.updateWFItem(nextFormID, NextStep.DONE);
            }
        });

        //This panel is for tjhe buttons used at the bottom of the UI - Approve/Reject Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(btnReject);        //add the reject button to the panel
        buttonsPanel.add(btnApprove);       //add the approve button to the panel
        buttonsPanel.setOpaque(false);
        buttonsPanel.setVisible(false);       //Only show them when an application is loaded

        //This is the main panel - it controls all the other panels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerPanel, BorderLayout.NORTH);         //Header panel with the employee information
        mainPanel.add(applicantInfoPanel, BorderLayout.CENTER); //Panel for the applicant information
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);        //Panel for the buttons

        add(mainPanel);     //Adds the mainPanel to the UI

        //General Information for the UI
        setTitle("Welcome To USCIS Website");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }//end initializa()

    //This is the main method
    public static void main(String[] args){
        Approver myFrame = new Approver();
        myFrame.initialize();   //Calls the program
    }//end main()
}//end Approver class
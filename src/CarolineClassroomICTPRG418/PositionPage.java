package CarolineClassroomICTPRG418;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class PositionPage extends JFrame
{
    private StudentInfo[] students;
    JLabel name, x, y;
    JTextField[][] textFields;
    SpringLayout myLayout = new SpringLayout();
    ClassroomMain parentFrame;


    public PositionPage(StudentInfo[] array, ClassroomMain parent)
    {
        // Various declarations for UI Components and Components that store information
        parentFrame = parent;
        students = array;
        textFields = new JTextField[students.length][3];

        // Sets and formats overall layout
        setSize(250, students.length * 20 + 100);
        setLocation(400,50);
        setLayout(myLayout);
        AddWindowListenerToFrame();
        SetupLabels();
        SetupTextFields();
        DisplayStudents();
        this.setBackground(Color.LIGHT_GRAY);
        this.setResizable(false);
        this.setTitle("Sort Page");

        setVisible(true);



    }

    // Allows functionality of WindowListener to Frame - for closing operation
    private void AddWindowListenerToFrame()
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                dispose();
                parentFrame.SortFormClose();
            }
        });
    }

    // Method to set up all the text fields
    private void SetupTextFields()
    {
        for (int y = 0; y < textFields.length ; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                int xPos = x * 60 + 25;
                int yPos = y * 20 + 25;
                textFields[y][x] = UIComponents.CreateJTextField(5,xPos,yPos,this,myLayout);
                textFields[y][x].setEditable(false);
            }
        }
    }

    // Method to set up all the labels
    private void SetupLabels()
    {
        name = UIComponents.CreateJLabel("Name", 30,5,this,myLayout);
        x = UIComponents.CreateJLabel("X", 110,5,this,myLayout);
        y = UIComponents.CreateJLabel("Y", 170,5,this,myLayout);
    }

    // Method to display students in GUI
    private void DisplayStudents()
    {
        Arrays.sort(students);
        for (int i = 0; i < students.length; i++)
        {
            textFields[i][0].setText(students[i].getStudentName());
            textFields[i][1].setText(Integer.toString(students[i].getxPos() + 1));
            textFields[i][2].setText(Integer.toString(students[i].getyPos() + 1));
        }
    }




}

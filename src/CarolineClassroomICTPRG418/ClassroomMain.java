package CarolineClassroomICTPRG418;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;


public class ClassroomMain  extends Frame implements ActionListener, WindowListener
{
        // Various declarations for UI Components and Components that store information
        SpringLayout myLayout = new SpringLayout();
        JButton btnExit, btnOpen, btnClear, btnSaveRAF, btnSaveCSV, btnFind, btnSort;
        JTextField[][] textFields = new JTextField[18][9];
        JTextField txtSearch, txtTeacher, txtClass, txtRoom, txtDate;
        JLabel lblTeacher, lblClass, lblRoom, lblDate;
        JLabel[] xLabels = new JLabel[10];
        JLabel[] yLabels = new JLabel[19];

        // Declaration for the sorted list page and the find function page
        PositionPage sort;
        FindPage find;


    public ClassroomMain()
    {
        // Sets and formats overall layout
        setSize(550,500);
        setLocation(200,200);
        AddWindowListenerToFrame();
        setLayout(myLayout);
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("Classroom Plan");
        this.setResizable(false);

        // Sets up various UI Components
        SetupLabels();
        SetupButtons();
        SetupTxt();
        setVisible(true);
    }

    // Method to set up all the buttons using library
    private void SetupButtons()
    {
        btnClear = UIComponents.CreateJButton("Clear", 70,20, 30, 420,this,this,myLayout);
        btnSaveRAF = UIComponents.CreateJButton("Save RAF",90,20,120,440,this,this,myLayout);
        btnSaveCSV = UIComponents.CreateJButton("Save CSV", 90, 20,30, 440, this, this, myLayout);
        btnOpen = UIComponents.CreateJButton("Open", 70, 20, 100, 420, this, this, myLayout);
        btnSort = UIComponents.CreateJButton("Sort", 70, 20, 170, 420, this, this, myLayout);
        btnFind = UIComponents.CreateJButton("Find", 70, 20, 240, 420, this, this, myLayout);
        btnExit = UIComponents.CreateJButton("Exit", 70, 20,455, 420, this, this, myLayout );
    }

    // Method to set up all the text fields
    private void SetupTxt()
    {
        txtSearch = UIComponents.CreateJTextField(7,310,420,this,myLayout);
        txtTeacher = UIComponents.CreateJTextField(7,75,5,this,myLayout);
        txtClass = UIComponents.CreateJTextField(7,200, 5, this, myLayout);
        txtRoom = UIComponents.CreateJTextField(7,325,5, this, myLayout);
        txtDate = UIComponents.CreateJTextField(7,445, 5, this, myLayout);

        // Sets up text data fields for GUI
        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                int xPos = 55 * x + 30;
                int yPos = 20 * y + 50;
                textFields[y][x] = UIComponents.CreateJTextField(5,xPos,yPos,this,myLayout);
                textFields[y][x].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        super.focusLost(e);
                        JTextField text = (JTextField) e.getSource();
                        if (Objects.equals(text.getText(), "desk"))  // Changes background colour to represent desks
                        {
                            text.setBackground(Color.blue);
                        }
                        else
                        {
                            text.setBackground(Color.white);
                        }
                    }
                });


            }
        }
    }

    // Method to set up all the labels using library
    private void SetupLabels()
    {
        lblTeacher = UIComponents.CreateJLabel("Teacher",20,5,this,myLayout);
        lblClass = UIComponents.CreateJLabel("Class", 160, 5, this, myLayout);
        lblRoom = UIComponents.CreateJLabel("Room",285,5, this, myLayout);
        lblDate = UIComponents.CreateJLabel("Date", 410,5, this, myLayout);

        // Sets up the x coordinate numbers of main text fields from left to right
        for (int x = 1; x < xLabels.length; x++)
        {
            int xPos = 55 * x + 5;
            int yPos = 30;
            xLabels[x] = UIComponents.CreateJLabel((Integer.toString(x)),xPos,yPos,this, myLayout);

        }

        // Sets up the y coordinate numbers of main text fields from up to down
        for (int y = 1; y < yLabels.length; y++)
        {
            int xPos = 10;
            int yPos = 20 * y + 30;
            yLabels[y] = UIComponents.CreateJLabel((Integer.toString(y)),xPos,yPos,this, myLayout);

        }

    }

    // Allows functionality of WindowListener to Frame - for closing operation
    private void AddWindowListenerToFrame()
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                System.exit(0);
            }
        });
    }


    // Various button event handlers - when button is clicked what happens
    @Override
    public void actionPerformed(ActionEvent e) {
        // Closes application
        if(e.getSource() == btnExit)
        {
            System.exit(0);
        }
        // Runs method - see method for explanation
        if(e.getSource() == btnClear)
        {
            ClearScreen();
        }
        // Runs method - see method for explanation
        if(e.getSource() == btnSaveRAF)
        {
            SaveRAFFile();
        }
        // Runs method - see method for explanation
        if(e.getSource() == btnSaveCSV)
        {
            SaveCSVFile();
        }
        // Runs method - see method for explanation - and opens sorted page
        if(e.getSource() == btnSort)
        {
            StudentInfo[] students = GatherStudentDetailsForSortForm();
            if (sort == null)
            {
                sort = new PositionPage(students, this);
            }
        }
        // Runs method - see method for explanation - and opens find page
        if(e.getSource() == btnFind)
        {
            StudentInfo[] students = GatherStudentDetailsForSortForm();
            if (find == null)
            {
                find = new FindPage(students, txtSearch.getText(), this);
                // Gets search result and sets the background colour in the y,x positions of result
                for (int y = 0; y < textFields.length; y++)
                {
                    for (int x = 0; x < textFields[y].length; x++)
                    {
                        if (Objects.equals(textFields[y][x].getText().toLowerCase(Locale.ROOT), txtSearch.getText().toLowerCase(Locale.ROOT)) && !Objects.equals(txtSearch.getText(), ""))
                        {
                            textFields[y][x].setBackground(Color.GREEN);
                        }
                    }
                }
            }
        }

        // Runs method - see method for explanation
        if(e.getSource() == btnOpen)
        {
            SelectFileToLoad();
        }

    }

    // Window events (not reliable on Windows 10)
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}

    // Completely clears the screen of all text and input
    private void ClearScreen()
    {
        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                textFields[y][x].setText("");
                textFields[y][x].setBackground(Color.white); // Resets background colours of all text fields to white
            }
        }
        // Clears the other text fields
        //txtSearch.setText("");  // * Comment this out to allow ReadSpecificRAFEntry functionality - It will not work with it on
        txtDate.setText("");
        txtClass.setText("");
        txtRoom.setText("");
        txtTeacher.setText("");
    }

    // Functionality to Save a file in RAF format
    private void SaveRAFFile()
    {
        FileDialog read = new FileDialog(this,"Pick File To Open", FileDialog.SAVE);  // Opens standard file dialogue for user interaction
        read.setDirectory("C://");
        read.setVisible(true);
        String filePath = read.getDirectory() + read.getFile();  // Gets file path of selected file

        if (read.getFile() == null)  // If condition to check that selected path actually exists ( error handling)
        {
            return;
        }
        if (!filePath.endsWith(".RAF"))  // Adds file extension if it doesn't exist in file path
        {
            filePath += ".RAF";
        }

        try
        {

            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");   // Initializes RAF in read and write mode
            int count = 0;  // Overall count for raf
            int pointer = count + 100;  // Pointer of which raf will go to when writing
            // Initially saves at the first 500 positions the Teacher, Class , Room and Date Data
            raf.seek(pointer);
            raf.writeUTF(txtTeacher.getText());
            raf.seek(pointer + 200);
            raf.writeUTF(txtClass.getText());
            raf.seek(pointer + 300);
            raf.writeUTF(txtRoom.getText());
            raf.seek(pointer + 400);
            raf.writeUTF(txtDate.getText());
            count += 500;

            // For loops to save the data located in text field grid
            for (int y = 0; y < textFields.length; y++)
            {
                for (int x = 0; x < textFields[y].length; x++)
                {
                    if (!textFields[y][x].getText().isEmpty())
                    {
                        pointer = count * 100;
                        raf.seek(pointer);
                        // If text field is a desk, saves it in the following format
                        if(Objects.equals(textFields[y][x].getText(), "desk"))
                        {
                            raf.writeUTF("BKGRND FILL");
                            raf.seek(pointer + 50);
                            raf.writeInt(x);
                            raf.seek(pointer + 75);
                            raf.writeInt(y);
                        }
                        // If text field isn't a desk, saves it in the following format
                        if(!Objects.equals(textFields[y][x].getText(), "desk"))
                        {
                            raf.writeUTF(textFields[y][x].getText());
                            raf.seek(pointer + 50);
                            raf.writeInt(x);
                            raf.seek(pointer + 75);
                            raf.writeInt(y);
                        }
                        count++;
                    }
                }
            }
            raf.close();
        }
        // Exception handling
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    // Functionality to Save a file in CSV format
    private void SaveCSVFile()
    {
        FileDialog write = new FileDialog(this,"Pick File Location", FileDialog.SAVE); // Opens standard file dialogue for user interaction
        write.setDirectory("C://");
        write.setVisible(true);
        String filePath = write.getDirectory() + write.getFile(); // Gets file path of selected file
        if(write.getFile() == null)  // If condition to check that filepath actually exists - error handling
        {
            return;
        }
        if (!filePath.endsWith(".csv")) // Adds csv. file extension to file if it doesn't have one already
        {
            filePath += ".csv";
        }

        try
        {

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)); // Initializes buffered writer at file path
            // Initially writes Teacher, Class, Room and Date Data from text fields
            bw.write("Teacher:" + "," + txtTeacher.getText());
            bw.newLine();
            bw.write("Class:" + "," + txtClass.getText());
            bw.newLine();
            bw.write("Room:" + "," + txtRoom.getText());
            bw.newLine();
            bw.write("Date:" + "," + txtDate.getText());
            bw.newLine();

            // For loop to write data contained in text field grid
            for (int y = 0; y < textFields.length; y++)
            {
                for (int x = 0; x < textFields[y].length; x++)
                {
                    // Writes data in the following format if data is a desk
                    if (!textFields[y][x].getText().isEmpty() && Objects.equals(textFields[y][x].getText(), "desk"))
                    {
                        bw.write(x + "," + y + "," + "BKGRND FILL" + "," + "blue");
                        bw.newLine();
                    }
                    // Writes data in the following format if the data isn't a desk
                    if(!textFields[y][x].getText().isEmpty() && !Objects.equals(textFields[y][x].getText(), "desk"))
                    {
                        bw.write(x + "," + y + "," + textFields[y][x].getText());
                        bw.newLine();
                    }
                }
            }
            bw.close();
            JOptionPane.showMessageDialog(null,"CSV File Saved"); // Tells user that file has been save if operation is successful
        }
        // Tells users that file hasn't been saved if operation is unsuccessful
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"CSV File Failed To Save");
        }
    }

    // Open file method if file is in CSV format
    private void OpenFile(String filePath)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath)); // Initializes buffered reader at file path
            // Reads data in file line by line and stores data in a temporary array, then retrieves that data to set it to a text field
            String line;
            line = br.readLine();
            String[] temp = line.split(",");
            txtTeacher.setText(temp[1]);
            line = br.readLine();
            temp = line.split(",");
            txtClass.setText(temp[1]);
            line = br.readLine();
            temp = line.split(",");
            txtRoom.setText(temp[1]);
            line = br.readLine();
            temp = line.split(",");
            txtDate.setText(temp[1]);

            // Reads data for text field grid
            while(!Objects.equals(line = br.readLine(), "")) {
                temp = line.split(",");
                int xPos = Integer.parseInt(temp[0]);
                int yPos = Integer.parseInt(temp[1]);
                // If data contains BKGRND FILL which represents a desk, assigns a desk to that textfield[y][x]
                if (Objects.equals(temp[2], "BKGRND FILL"))
                {
                    textFields[yPos][xPos].setBackground(Color.blue);
                    textFields[yPos][xPos].setText("desk");
                }
                // Else assigns student data to that text field
                else{
                    textFields[yPos][xPos].setText(temp[2]);
                }
            }
        }
        catch(Exception ignored)
        {

        }
    }

    // Method for Open button of which different methods run depending on file type
    private void SelectFileToLoad()
    {
        FileDialog read = new FileDialog(this,"Pick File To Open", FileDialog.LOAD);
        read.setDirectory("C://");
        read.setVisible(true);
        String filePath = read.getDirectory() + read.getFile();

        if (read.getFile() == null)  // Error handling - if file doesn't exist in file path
        {
            return;
        }

        ClearScreen(); // Clears screen for data
        // Runs following method if file extension of file in file path is .RAF
        if (read.getFile().endsWith(".RAF"))
        {
            //ReadRafFile(filePath);
            ReadSpecificRAFEntry(filePath);  // Turn this on to read specific RAF entry, remember to edit ClearScreen() as well!
            return;
        }
        // else runs standard open file method
        OpenFile(filePath);
    }

    // Method to read data in a RAF file
    private void ReadRafFile(String filepath)
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(filepath,"r"); // Initializes raf in read mode
            int count = 0; // Universal counter for raf
            long length = raf.length(); // Grabs length of raf file
            int pointer = count + 100; // pointer to set position of raf read
            raf.seek(pointer);
            // Reads raf at specified positions (that raf is saved in) and assigns data to text fields
            String text = raf.readUTF();
            txtTeacher.setText(text);
            raf.seek(pointer + 200);
            text = raf.readUTF();
            txtClass.setText(text);
            raf.seek(pointer + 300);
            text = raf.readUTF();
            txtRoom.setText(text);
            raf.seek(pointer + 400);
            text = raf.readUTF();
            txtDate.setText(text);
            count+= 500;

            while(count * 100 < length)  // To allow entire file to be read
            {
                pointer = count * 100;
                raf.seek(pointer);
                text = raf.readUTF();
                if (text.isEmpty()) // If data in specified pointer is empty, skips it
                {
                    count++;
                    continue;
                }
                if (text.equals("BKGRND FILL")) // If data in specified pointer is BKGRND FILL (desk), grabs relevant data and sets data to [y][x] text field
                {
                    raf.seek(pointer + 50);
                    int xPos = raf.readInt();
                    raf.seek(pointer + 75);
                    int yPos = raf.readInt();
                    textFields[yPos][xPos].setText("desk");
                    textFields[yPos][xPos].setBackground(Color.blue);
                }
                if  (!Objects.equals(text, "BKGRND FILL")) // If data in specified pointer isn't a desk, grabs relevant data to set to text field
                {
                    raf.seek(pointer + 50);
                    int xPos = raf.readInt();
                    raf.seek(pointer + 75);
                    int yPos = raf.readInt();
                    textFields[yPos][xPos].setText(text);
                }
                count++;
            }
            raf.close();
        }
        // Error handling to catch exceptions
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

    }

    // Method to gather all student details in text fields for the sort button and find button function
    private StudentInfo[] GatherStudentDetailsForSortForm()
    {
        LinkedList<StudentInfo> studentList = new LinkedList<>();
        for (int y = 0; y < textFields.length ; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                // Long if statement that checks if data isn't empty and isn't a desk
                if (!textFields[y][x].getText().isEmpty() && !(Objects.equals(textFields[y][x].getText(), "desk")) && !(Objects.equals(textFields[y][x].getText(), "Front Desk")))
                {
                    studentList.add(new StudentInfo(textFields[y][x].getText(),y,x));
                }
            }
        }
        return studentList.toArray(new StudentInfo[studentList.size()]);
    }

    // Handles closing the sort form
    public void SortFormClose()
    {
        sort = null;
    }

    // Handles closing the find form
    public void FindFormClose()
    {
        find = null; // resets find variable
        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                if (!Objects.equals(textFields[y][x].getText(), "desk"))
                {
                    textFields[y][x].setBackground(Color.white); // resets background colour of the student that was found
                }
            }
        }
        txtSearch.setText(""); // Empties search text field

    }

    // Unused method to read a specific RAF file entry
    private void ReadSpecificRAFEntry(String filepath)
    {

        try
        {

            RandomAccessFile raf = new RandomAccessFile(filepath, "r"); // Initializes RAF in read mode
            long length = raf.length();
            int count = 500; // Universal counter for Raf (starts at 500 to skip teacher, room, class and date data)
            int pointer;
            String text;

            /* The aim of this method is to grab the text from the search text field and then search for that data
            * in the RAF file. In this case, the user can search for any student name, it will present that data
            * to the user when that student is found. This method is simple but demonstrates how a specific RAF entry
            * can be found with user interaction. This can be further expanded upon with combo boxes, advanced search
            * queries etc. so that the search can be more efficient and functional.  */
            while(count * 100 < length)
            {
                pointer = count * 100;
                raf.seek(pointer);
                text = raf.readUTF();
                if(!text.equals(txtSearch.getText()))
                {
                    count++;
                }
                if(text.equals(txtSearch.getText()))
                {
                    JOptionPane.showMessageDialog(null,"Searched Object Found: " + text);
                    raf.seek(pointer + 50);
                    int xPos = raf.readInt();
                    raf.seek(pointer + 75);
                    int yPos = raf.readInt();
                    textFields[yPos][xPos].setText(text);
                    break;
                }
            }
            raf.close();

        } catch (Exception e){

            System.out.println(e.toString());
        }

    }


}

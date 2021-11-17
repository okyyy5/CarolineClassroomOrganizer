package CarolineClassroomICTPRG418;

public class StudentInfo implements Comparable
{
    private String studentName;
    private int xPos;
    private int yPos;

    public StudentInfo(String name, int y, int x)
    {
        studentName = name;
        xPos = x;
        yPos = y;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPos() {
        return xPos;
    }


    // Allows comparable to compare student name whilst ignoring case
    @Override
    public int compareTo(Object o)
    {
        if (o.getClass() == StudentInfo.class   )
        {
            StudentInfo student = (StudentInfo)o;
            return this.studentName.compareToIgnoreCase(student.studentName);
        }
        return this.studentName.compareToIgnoreCase(o.toString());
    }
}

package cass.myapp;

import java.sql.Date;

public class AttendanceReportTableView {

    private int id;
    private String fullName;
    private String studentId;
    private String student_Class;
    private int total_attendance;
    private int total_absent;
    private int total_present;


    public AttendanceReportTableView( String studentId, String fullName, String student_Class, int total_attendance, int total_absent, int total_present) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.student_Class = student_Class;
        this.total_attendance = total_attendance;
        this.total_absent = total_absent;
        this.total_present = total_present;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudent_Class() {
        return student_Class;
    }

    public void setStudent_Class(String student_Class) {
        this.student_Class = student_Class;
    }

    public int getTotal_attendance() {
        return total_attendance;
    }

    public void setTotal_attendance(int total_attendance) {
        this.total_attendance = total_attendance;
    }

    public int getTotal_absent() {
        return total_absent;
    }

    public void setTotal_absent(int total_absent) {
        this.total_absent = total_absent;
    }

    public int getTotal_present() {
        return total_present;
    }

    public void setTotal_present(int total_present) {
        this.total_present = total_present;
    }

}//end of class


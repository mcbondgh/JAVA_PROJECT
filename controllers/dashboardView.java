package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import cass.myapp.dbconnection.DbRowCounts;
import cass.myapp.models.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dashboardView {
public dashboardView() throws SQLException {}

    static PreparedStatement prepare = null;
    Statement stmt = null;
    static ResultSet result = null;
    static Database DB_OBJECT = new Database();


    @FXML
    private Pane dashboardPane;

    @FXML
    private Label studentRowCount, userRowCount, subjectRowCount, lecturerRowCount;

    /**=================================================================================================================
                            setters and getters field implementation for the LABEL OBJECTS
    =================================================================================================================**/
    public void setStudentRowCount(int number) throws SQLException {this.studentRowCount.setText(String.valueOf(number));}
    public int getStudentRowCount() {return Integer.parseInt(studentRowCount.getText());}

    public void setSubjectRowCount(int number) {this.subjectRowCount.setText(String.valueOf(number));}
    public int getSubjectRowCount() {return Integer.parseInt(subjectRowCount.getText());}

    public void setUserRowCount(int number) {this.userRowCount.setText(String.valueOf(number));}
    public int getUserRowCount() {return Integer.parseInt(userRowCount.getText());}

    public void setLecturerRowCount(int number) {this.lecturerRowCount.setText(String.valueOf(number));}
    public int getLecturerRowCount() {return Integer.parseInt(lecturerRowCount.getText());}


    /**=================================================================================================================
                            implementation of row counts and return result to the dashboard.
    =================================================================================================================**/


    public void updateDashboard() throws SQLException {
        DbRowCounts OBJECT = new DbRowCounts();
        int result1 = OBJECT.totalStudents();
        int result2 = OBJECT.totalUsers();
        int result3 = OBJECT.totalSubjects();
        int result4 = OBJECT.totalTutors();

        setStudentRowCount(result1);
        setLecturerRowCount(result4);
        setSubjectRowCount(result3);
        setUserRowCount(result2);
    }






















}//end of class.....

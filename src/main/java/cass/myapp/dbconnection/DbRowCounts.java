package cass.myapp.dbconnection;

import cass.myapp.controllers.dashboardView;
import cass.myapp.multistage.UniversalController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DbRowCounts {
    public DbRowCounts() {
    }
    PreparedStatement prepare = null;
    ResultSet result = null;
    Database DbObject = new Database();


    @FXML
    private static Label studentRowCount1;
    @FXML
    private static Label lecturerRowCount1;
    @FXML
    private static Label subjectRowCount1;
    @FXML
    private static Label userRowCount1;
    @FXML
    public static Pane controlPane1;


    int count = 0;
    public int totalStudents() throws SQLException {
        String selectQuery = "SELECT COUNT(id) FROM students;";
        prepare = DbObject.CONNECT().prepareStatement(selectQuery);
        result = prepare.executeQuery();
        if (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }
    public int totalUsers() throws SQLException {
        String selectQuery = "SELECT COUNT(id) FROM users;";
        prepare = DbObject.CONNECT().prepareStatement(selectQuery);
        result = prepare.executeQuery();
        if (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }
    public int totalTutors() throws SQLException {
        String selectQuery = "SELECT COUNT(id) FROM staff;";
        prepare = DbObject.CONNECT().prepareStatement(selectQuery);
        result = prepare.executeQuery();
        if (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }
    public int totalSubjects() throws SQLException {
        String selectQuery = "SELECT COUNT(id) FROM subjects;";
        prepare = DbObject.CONNECT().prepareStatement(selectQuery);
        result = prepare.executeQuery();
        if (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }

    public int totalAttendance(String studentId, String subject, LocalDate date_from, LocalDate date_to) {
        try {
            String selectQuery = "SELECT COUNT(status) AS total FROM stu_attendance WHERE studentId = ? AND subject = ? AND attendance_date BETWEEN ? AND ?";
            prepare = DbObject.CONNECT().prepareStatement(selectQuery);
            prepare.setString(1, studentId);
            prepare.setString(2, subject);
            prepare.setDate(3, Date.valueOf(date_from));
            prepare.setDate(4,Date.valueOf(date_to));
            result = prepare.executeQuery();
            while (result.next()) {
                count = result.getInt("total");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

     public int totalPresent(String studentId, String subject, LocalDate date_from, LocalDate date_to) {
        try {
            String selectQuery = "SELECT COUNT(status) AS `absent` FROM stu_attendance WHERE studentId =? AND status = 1 AND subject = ? AND attendance_date BETWEEN ? AND ?";
            prepare = DbObject.CONNECT().prepareStatement(selectQuery);
            prepare.setString(1, studentId);
            prepare.setString(2, subject);
            prepare.setDate(3, Date.valueOf(date_from));
            prepare.setDate(4, Date.valueOf(date_to));
            result = prepare.executeQuery();
            while(result.next()) {
                count = result.getInt("absent");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

     public int totalAbsent(String studentId, String subject, LocalDate date_from, LocalDate date_to) {
        try {
            String selectQuery = "SELECT COUNT(status) AS `absent` FROM stu_attendance WHERE studentId =? AND status = 0 AND subject = ? AND attendance_date BETWEEN ? AND ?";
            prepare = DbObject.CONNECT().prepareStatement(selectQuery);
            prepare.setString(1, studentId);
            prepare.setString(2, subject);
            prepare.setDate(3, Date.valueOf(date_from));
            prepare.setDate(4, Date.valueOf(date_to));
            result = prepare.executeQuery();
            while(result.next()) {
                count = result.getInt("absent");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }









}///end of class

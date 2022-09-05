package cass.myapp.dbconnection;

import cass.myapp.controllers.dashboardView;
import cass.myapp.multistage.UniversalController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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









}///end of class

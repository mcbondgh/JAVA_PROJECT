package cass.myapp.controllers;

import cass.myapp.Start;
import cass.myapp.dbconnection.Database;
import cass.myapp.dbconnection.DbRowCounts;
import cass.myapp.models.Dashboard;
import cass.myapp.multistage.AllStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserLogin {

    @FXML
    public Button closeButton, loginButton;
    @FXML
    public TextField userIdField, passwordField;
    private String username, userPassword;

    /**===================> implement setters and getter for the text fields <================*/
    public void setUsername(String username) { this.username = username;}
    public String getUsername() {return username;}

    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}
    public String getUserPassword() {return userPassword;}
    /*==========================END OF SETTERS AND GETTERS ==============================*/


    /**====================> Define an method to store username and password entered by the user*/
    public void getUserInputs() {
        setUsername(userIdField.getText());
        setUserPassword(passwordField.getText());
    }

    /**=================> Define a method to clear all text fields */
    public void clearFields() {
        userIdField.clear();
        passwordField.clear();
    }
    public void CloseOnAction() {
//        Stage obj = (Stage)this.closeButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please Confirm To Exit");
        alert.setTitle("EXIT APP");
        alert.setHeaderText("Are you sure you want to exit? ");
        Optional<ButtonType> choose = alert.showAndWait();
        if(choose.isPresent() && choose.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
    /*******************************************************************************************************************
                                         IMPLEMENTATION OF USER LOGIN
    ********************************************************************************************************************/
     public void  loginUserOnClick() throws IOException {
         Database DB_OBJECT = new Database();
         PreparedStatement prepare = null;
         ResultSet result = null;
         Statement stmt = null;

         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         try {
             getUserInputs();
             if(getUsername().isEmpty() || getUserPassword().isEmpty()) {
                 alert.setTitle("EMPTY FIELD");
                 alert.setHeaderText("USERNAME OR PASSWORD FIELD IS EMPTY");
                 alert.setContentText("Please fill out the with your login details to login");
                 alert.showAndWait();
             }
             else {
                String selectQuery = "SELECT userName, user_role FROM users WHERE userName=? AND password=?";
                prepare = DB_OBJECT.CONNECT().prepareStatement(selectQuery);
                prepare.setString(1, getUsername());
                prepare.setString(2, getUserPassword());
                result = prepare.executeQuery();
                if (!result.next()){
                    alert.setTitle("INVALID CREDENTIALS");
                    alert.setHeaderText("WRONG USERNAME OR PASSWORD");
                    alert.setContentText("Please provide the correct Username and Password to login to CASS");
                    alert.showAndWait();
                } else {
                    String nestedQuery = "INSERT INTO signin(userName,  user_role) SELECT userName, user_role FROM users WHERE userName='"+getUsername()+"' AND password='"+getUserPassword()+"'";
                    stmt = DB_OBJECT.CONNECT().createStatement();
                    stmt.execute(nestedQuery);
                    String AccessLevel = result.getString("user_role");
                    switch (AccessLevel.toLowerCase()) {
                        case "student" -> {
                            studentInterface();
                            clearFields();
                            closeButton.getScene().getWindow().hide();
                        }
                        case "staff" -> {
                            staffInterface();
                            clearFields();
                            closeButton.getScene().getWindow().hide();
                        }
                    }
                }
             }
//             Stage hide = (Stage)this.loginButton.getScene().getWindow();
//             hide.hide();
         } catch (NullPointerException | SQLException e) {
             e.printStackTrace();
         }
    }

    /*******************************************************************************************************************
                                    ACCESS LEVEL SECTION -> STAFF AND STUDENTS VIEW IMPLEMENTATION
    ********************************************************************************************************************/
    public void staffInterface() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("staffinterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1278, 672);
        StaffDashboard OBJECT = fxmlLoader.getController();
        OBJECT.activeUsername.setText(getUsername().toLowerCase());
        Stage stage = new Stage();
        stage.setTitle("STAFF DASHBOARD");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.isFullScreen();
        stage.show();

//          DbRowCounts count = new DbRowCounts();
//        int money = 0;
//        int result1 = count.totalStudents();
//        int result2 = count.totalUsers();
//        int result3 = count.totalSubjects();
//        int result4 = count.totalTutors();
//
//        view.setStudentRowCount(result1);
//        view.setUserRowCount(result2);
//        view.setSubjectCount(result3);
//        view.setLecturerRowCount(result4);
//
    }
    public void studentInterface() throws IOException, SQLException {
         FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("studentDashboard.fxml"));
        Parent root = loadFile.load();
        Scene scene = new Scene(root, 1278, 672);

        StudentDashboard view  = loadFile.getController();
        view.setActiveUsername(getUsername().toLowerCase());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("STUDENT DASHBOARD");
//        stage.setResizable(false);
        stage.isFullScreen();
        stage.show();
//        DbRowCounts count = new DbRowCounts();
//
//        int money = 0;
//        int result1 = count.totalStudents();
//        int result2 = count.totalUsers();
//        int result3 = count.totalSubjects();
//        int result4 = count.totalTutors();
//
//        view.setStudentRowCount(result1);
//        view.setUserRowCount(result2);
//        view.setSubjectCount(result3);
//        view.setLecturerRowCount(result4);


    }

}
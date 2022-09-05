package cass.myapp.controllers;

import cass.myapp.Start;
import cass.myapp.models.Dashboard;
import cass.myapp.multistage.AllStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;


public class StudentDashboard {


    public StudentDashboard() {}

    @FXML
    private Label activeUsername, studentRowCount, subjectRowCount, userRowCount, lecturerRowCount;
    @FXML
    public  BorderPane insertScene;

    @FXML
    public Button signoutButton, dashboardButton;
    /**================> setter and getter for labels */
    public void setActiveUsername(String username) { activeUsername.setText(username);}
    public String getActiveUsername() {return activeUsername.getText();}
//    public void setStudentRowCount(int studentCount) {studentRowCount.setText(String.valueOf(studentCount));}
//    public int getStudentRowCount() {return Integer.parseInt(studentRowCount.getText());}
//
//    public void setSubjectCount(int subjectCount) {subjectRowCount.setText(String.valueOf(subjectCount));}
//    public int getSubjectCount() {return Integer.parseInt(subjectRowCount.getText());}
//    public void setUserRowCount(int userCount) {this.userRowCount.setText(String.valueOf(userCount));}
//    public int getUserRowCount() {return Integer.parseInt(userRowCount.getText());}
//    public void setLecturerRowCount(int lecturerCount) {this.lecturerRowCount.setText(String.valueOf(lecturerCount));}
//    public int getLecturerRowCount() {return Integer.parseInt(lecturerRowCount.getText());}

    /**==================> end of setters and getters.*/



    /**
     * =================================================================================================================
     * METHOD TO FLIP THE VIEWS ON BUTTON CLICK
     * ===================================================================================================================
     */
      public void showInterface(String provideFxmlFileName) throws IOException {
        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource(provideFxmlFileName));
        insertScene.setCenter(loadFile.load());
      }

        /**========================================================================================================
                              DASHBOARD BUTTON IMPLEMENTATIONS
        * ========================================================================================================*/
    public void signoutOnClick() {
        try {
            Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, "Please Confirm Your Action ");
            prompt.setTitle("SIGNOUT USER");
            prompt.setHeaderText("ARE YOU SURE YOU WANT TO SIGN OUT?");
            Optional<ButtonType> choose = prompt.showAndWait();
            if(choose.isPresent() && choose.get() == ButtonType.OK) {
                Dashboard DB_OBJECT = new Dashboard();
                DB_OBJECT.signout(getActiveUsername());
                Stage stage = (Stage)this.signoutButton.getScene().getWindow();
                stage.close();

                AllStage OBJECT = new AllStage();
                OBJECT.UserLogin().show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStudentStage() throws IOException {
        showInterface("studentform.fxml");
    }
    public void showSubjectStage() throws IOException {
        showInterface("Subjects.fxml");
    }
    public void showStaffStage() throws IOException {
        showInterface("staffform.fxml");

        }
    public void showNewUserStage() throws IOException {
        showInterface("newuserform.fxml");
    }
    public void dashboard() throws SQLException, IOException {
    showInterface("dashboardView.fxml");
    }
    public void updateRecordOnAction() throws IOException {
        showInterface("updateData.fxml");
    }
    public void viewStudentRecord() throws IOException {
        showInterface("viewStudentRecord.fxml");
    }
    public void userLogs() throws IOException {
        showInterface("userlogs.fxml");
    }
    public void viewStaffRecord() throws IOException {
        showInterface("viewStaffRecord.fxml");
    }
    public void takeAttandance() throws IOException {
        showInterface("takeAttendance.fxml");
    }

    public void attendenceReportAction() throws IOException {
        showInterface("attendance-report.fxml");
    }















}

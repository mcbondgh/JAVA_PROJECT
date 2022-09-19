package cass.myapp.controllers;

import cass.myapp.Start;
import cass.myapp.models.Dashboard;
import cass.myapp.multistage.AllStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class StaffDashboard {

    @FXML
    public Label activeUsername, studentRowCount, staffRowCount;
@FXML
private Button signoutButton;
    public void signOutOnClick() throws SQLException, IOException {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, "Please Confirm Your Decision.");
        prompt.setTitle("SIGN OUT");
        prompt.setHeaderText("ARE YOU SURE YOU WANT TO SIGN OUT? ");
        Optional<ButtonType> choose = prompt.showAndWait();
        if(choose.isPresent() && choose.get() == ButtonType.OK) {
            Dashboard OBJECT = new Dashboard();
            UserLogin object2 = new UserLogin();
            OBJECT.signout(object2.getUsername());
            Stage stage = (Stage)this.signoutButton.getScene().getWindow();
            stage.hide();
            AllStage object3 = new AllStage();
            object3.UserLogin().show();
        }
    }
/***********************************************************************************************************************
                                 SETTERS AND GETTERS FIELD FOR LABEL OBJECTS
 ***********************************************************************************************************************/
    public void setActiveUsername(String username) {activeUsername.setText(username);}
    public String getActiveUsername() {return activeUsername.getText();}

    public void setStudentRowCount(int countValue) {studentRowCount.setText(String.valueOf(countValue));}
    public int getStudentRowCount() {return Integer.parseInt(studentRowCount.getText());}

    public void setStaffRowCount(int countValue) {staffRowCount.setText(String.valueOf(countValue));}
    public int getStaffRowCount() {return Integer.parseInt(staffRowCount.getText());}


    @FXML
    private BorderPane insertScene;

    public void FilpView(String provideFXML) throws IOException {
        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource(provideFXML));
        insertScene.setCenter(loadFile.load());
    }

    public void staffDashboard() throws IOException {
        FilpView("staffdashboardView.fxml");

    }
    public void studentRecords() throws IOException {
        FilpView("viewStudentRecord.fxml");
    }

     public void updateStaffDetails() throws IOException {
        FilpView("updateStaffDetails.fxml");
    }
    public void attendanceOnAction() throws IOException {
        FilpView("attendance-report.fxml");
    }




















}//end of class.......

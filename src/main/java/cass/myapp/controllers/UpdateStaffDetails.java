package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class UpdateStaffDetails implements Initializable {
    public UpdateStaffDetails() {}

    @FXML
    private TextField firstnameField, lastnameField, staffIdField, subjectField, genderField;
    @FXML
    private TextField numberField, titleField, departmentField;
    @FXML
    private Button staffUpdateButton;

    @FXML
    private Label reflection;


    /**=================================================================================================================
                                            Setter and Getter Field
     ==================================================================================================================*/
    public void setFirstnameField(String firstname) {this.firstnameField.setText(firstname);}
    public String getFieldNameField() {return firstnameField.getText();}

    public void setLastnameField(String lastname) {this.lastnameField.setText(lastname);}
    public String getLastNameField() {return lastnameField.getText();}

    public void setStaffIdField(String staffId) {this.staffIdField.setText(staffId);}
    public String getStaffIdField() {return staffIdField.getText();}

    public void setSubjectField(String subject) {this.subjectField.setText(subject);}
    public String getSubjectField() {return subjectField.getText();}

    public void setGenderField(String gender) {this.genderField.setText(gender);}
    public String getGenderField() {return genderField.getText();}

    public void setNumberField(Integer number) {this.numberField.setText(String.valueOf(number));}
    public Integer getNumberField() {return  Integer.parseInt(numberField.getText());}

    public void setTitleField(String title) {this.titleField.setText(title);}
    public String getTitleField() {return titleField.getText();}

    public void setDepartmentField(String department) {this.departmentField.setText(department);}
    public String getDepartmentField() {return departmentField.getText();}

    /**=================================================================================================================
                    Special methods to a.hold all user inputs b. clear all inputs after update.
    ==================================================================================================================*/

    public void collectAll() {
        setFirstnameField(getFieldNameField());
        setLastnameField(getLastNameField());
        setStaffIdField(getStaffIdField());
        setSubjectField(getSubjectField());
        setGenderField(getGenderField());
        setDepartmentField(getDepartmentField());
        setTitleField(getTitleField());
        setNumberField(getNumberField());
    }


    public void updateStaffOnAction() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
           Database DB_OBJECT = new Database();
           PreparedStatement prepre = null;
           ResultSet result = null;
           StaffDashboard ST_OBJECT = new StaffDashboard();
           String activerUser = String.valueOf(ST_OBJECT.activeUsername);
//           reflection.setText(activerUser);
        try {


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

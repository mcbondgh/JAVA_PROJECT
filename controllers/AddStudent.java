package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import cass.myapp.models.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

public class AddStudent {
    PreparedStatement prepare = null;
    ResultSet result = null;
    Database DB_OBJECT = new Database();
    Statement statement = null;

    public AddStudent() {}
    @FXML
    private TextField firstnameField, lastnameField, emailField, userIdField, levelField;
    @FXML
    private TextField programmeField, departmentField, classField;
    @FXML
    private ToggleGroup Gender, Personality;

    RadioButton status, stuGender;

    String getGender, getStatus;



    /**=================================================================================================================
                                 SETTERS AND GETTERS FIELD FOR text fields AND toogle groups;
    ==================================================================================================================*/

    public void setFirstnameField(String firstname) {this.firstnameField.setText(firstname);}
    public String getFirstnameField() { return firstnameField.getText();}

    public void setLastnameField(String lastname) {this.lastnameField.setText(lastname);}
    public String getLastnameField() {return this.lastnameField.getText();}

    public void setEmailField(String email) {this.emailField.setText(email);}
    public String getEmailField() {return emailField.getText();}

    public void setUserIdField(String userid) {this.userIdField.setText(userid);}
    public String getUserIdField() {return userIdField.getText();}

    public void setLevelField(int level) {this.levelField.setText(String.valueOf(level));}
    public int getLevelField() {return Integer.parseInt(levelField.getText());}

    public void setDepartmentField(String department) {departmentField.setText(department);}
    public String getDepartmentField() {return departmentField.getText();}

    public void setClassField(String userClass) {this.classField.setText(userClass);}
    public String getClassField() {return classField.getText();}

    public void setProgrammeField(String programme) {this.programmeField.setText(programme);}
    public String getProgrammeField() {return programmeField.getText();}

    /**=================================================================================================================
                create methods to get All user inputs from the text fields and another to clear all text fields
    ====================================================================================================================*/
    public void AcceptAllInputs() {
        status = (RadioButton) Personality.getSelectedToggle();
        getStatus = status.getText();
        stuGender = (RadioButton) Gender.getSelectedToggle();
        getGender = stuGender.getText();
        setFirstnameField(getFirstnameField());
        setLastnameField(getLastnameField());
        setEmailField(getEmailField());
        setDepartmentField(getDepartmentField());
        setLevelField(getLevelField());
        setClassField(getClassField());
        setUserIdField(getUserIdField());
        setProgrammeField(getProgrammeField());
    }
    public void clearFields() {
        firstnameField.clear();
        lastnameField.clear();
        emailField.clear();
        classField.clear();
        departmentField.clear();
        programmeField.clear();
        userIdField.clear();
        levelField.clear();
        status.setSelected(false);
        stuGender.setSelected(false);
    }
    /**=================================================================================================================
                Execution method set on ACTION when the submit button is fired or clicked.
    ====================================================================================================================*/
    public void setAddStudentBtn() {
        Alert prompt =  new Alert(Alert.AlertType.ERROR);
        try {
            AcceptAllInputs();
            if (getFirstnameField().isEmpty() || getLastnameField().isEmpty() || getDepartmentField().isEmpty() || getEmailField().isEmpty() ||
                    getProgrammeField().isEmpty() || getUserIdField().isEmpty() || getClassField().isEmpty() || !stuGender.isSelected() || !status.isSelected()) {
                prompt.setTitle("EMPTY FIELD");
                prompt.setHeaderText("ONE OR MORE INPUT FIELD IS EMPTY");
                prompt.setContentText("Please fill all text fields");
                prompt.showAndWait();
            } else if (!stuGender.isSelected()) {
                prompt.setTitle("EMPTY BUTTON");
                prompt.setHeaderText("PLEASE SELECT YOUR GENDER");
                prompt.showAndWait();
//            } else if (!status.isSelected()) {
//                prompt.setTitle("EMPTY BUTTON");
//                prompt.setHeaderText("PLEASE INDICATE YOU ARE A STUDENT");
//                prompt.setContentText("Select the radio button to confirm you're not a robot");
//                prompt.showAndWait();
            } else {
                Dashboard.RegisterStudent(getFirstnameField(), getLastnameField(), getUserIdField(), getEmailField(),
                        getGender, getLevelField(), getClassField(), getStatus, getProgrammeField(), getDepartmentField());
                clearFields();
            }
        }catch (NullPointerException e) {
            prompt.setTitle("NULL VALUES");
            prompt.setHeaderText("PLEASE SELECT YOUR GENDER AND YOUR PERSONALITY");
            prompt.setContentText("Please check and fill all fields");
            prompt.showAndWait();
        } catch(NumberFormatException e) {
            prompt.setTitle("NUMBER FIELD");
            prompt.setHeaderText("PLEASE INPUT ONLY DIGITS IN THE LEVEL FIELD");
            prompt.setContentText("Please check and fill all fields");
            prompt.showAndWait();
        } catch (SQLException e) {
            prompt.setTitle("ERROR TYPE 112");
            prompt.setHeaderText("SORRY, UNABLE TO ADD NEW STUDENT, TRY AGAIN");
            prompt.setContentText("Execution failed");
            throw new RuntimeException(e);
        }
    }









}//END OF CLASS

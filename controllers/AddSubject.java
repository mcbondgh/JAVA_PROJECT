package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class AddSubject {

    @FXML
    private TextField subjectNameField, subjectCodeField, semesterField, subjectDuration;
    @FXML
    private Button submitSubject;

    /**=================================================================================================================
                                                setters and getters field
     ==================================================================================================================*/
    public void setSubjectNameField(String subject) {this.subjectNameField.setText(subject);}
    public String getSubjectNameField() {return subjectNameField.getText();}

    public void setSubjectCodeField(String code) {this.subjectCodeField.setText(code);}
    public String getSubjectCodeField() {return subjectCodeField.getText();}

    public void setSemesterField(String semester) {this.semesterField.setText(semester);}
    public String getSemesterField() {return semesterField.getText();}

    public void setSubjectDuration(int duration) {this.subjectDuration.setText(String.valueOf(duration));}
    public String getSubjectDuration() {return subjectDuration.getText();}


    /**=================================================================================================================
                                 Special method to collect all user inputs from form
    ==================================================================================================================*/
    public void getAllInputs() {
        setSubjectDuration(Integer.parseInt(getSubjectDuration()));
        setSubjectNameField(getSubjectNameField());
        setSemesterField(getSemesterField());
        setSubjectCodeField(getSubjectCodeField());
    }

    /**=================================================================================================================
                                    Special method to clear all text fields after execution
     ==================================================================================================================*/
    public void clearFields() {
        subjectNameField.clear();
        subjectCodeField.clear();
        subjectDuration.clear();
        semesterField.clear();
    }

    /**=================================================================================================================
                        implementation of submit button on action, when the button is fired or clicked.
     ==================================================================================================================*/
    public void  submitSubjectOnClick() {
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        try {
            Database DB_OBJECT = new Database();
            Statement stmt = null;
            ResultSet result = null;
            PreparedStatement prepare = null;

            getAllInputs();
            if(getSubjectDuration().isEmpty() || getSemesterField().isBlank() || getSubjectNameField().isBlank() || getSubjectCodeField().isBlank()) {
                prompt.setHeaderText("ONE OR MORE EMPTY FIELDS AVAILABLE");
                prompt.setTitle("EMPTY FIELDS");
                prompt.setContentText("Please fill out all input fields to proceed.");
                prompt.showAndWait();
            } else  {
                String selectQuery = "SELECT subject FROM subjects WHERE subject= '"+getSubjectNameField()+"'";
                stmt = DB_OBJECT.CONNECT().createStatement();
                result = stmt.executeQuery(selectQuery);
                if (result.next()) {
                    String output = result.getString("subject");
                    if(Objects.equals(output.toUpperCase(), getSubjectNameField().toUpperCase())) {
                        prompt.setTitle("SUBJECT EXIST");
                        prompt.setHeaderText("SUBJECT ALREADY EXIST");
                        prompt.setContentText("Please provide a subject that isn't in the CASS");
                        prompt.showAndWait();
                    }
                }else {
                    prompt.setAlertType(Alert.AlertType.CONFIRMATION);
                    prompt.setTitle("ADD NEW SUBJECT");
                    prompt.setHeaderText("ARE YOU SURE YOU WANT TO ADD: " + getSubjectNameField());
                    prompt.setContentText("Please confirm your action to proceed, else abort");
                    Optional<ButtonType>choose = prompt.showAndWait();
                    if(choose.isPresent() && choose.get() == ButtonType.OK) {
                        String insertQuery = "INSERT INTO subjects(subject, subject_code, semester, duration) VALUES(?, ?, ?, ?)";
                        prepare = DB_OBJECT.CONNECT().prepareStatement(insertQuery);
                        prepare.setString(1, getSubjectNameField());
                        prepare.setString(2, getSubjectCodeField());
                        prepare.setString(3, getSemesterField());
                        prepare.setString(4, getSubjectDuration());
                        int output = prepare.executeUpdate();
                        if (output > 0) {
                            prompt.setTitle("SUCCESSFUL");
                            prompt.setHeaderText(getSubjectNameField() + " SUCCESSFULLY ADDED");
                            prompt.setContentText("Operation executed successfully");
                            prompt.showAndWait();
                            clearFields();
                        }
                    }
                }
            }
        }catch (NumberFormatException | NullPointerException e) {
            prompt.setTitle("NULL POINTER");
            prompt.setHeaderText("PLEASE INPUT ONLY DIGITS IN THE SUBJECT DURATION FIELD");
            prompt.setContentText("Please make sure to provide the right input");
            prompt.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

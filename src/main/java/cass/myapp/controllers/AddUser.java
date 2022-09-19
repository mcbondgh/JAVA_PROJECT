package cass.myapp.controllers;

import cass.myapp.models.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;

public class AddUser {

    public AddUser() {}

    @FXML
    private TextField usernameField, userIdField, passwordField, confirmPasswordField;
    @FXML
    private ToggleGroup userRoles;

    @FXML
    private Button submitButton;

    /**=================================================================================================================
                                setters and getters field for the TEXTFIELDS
     ==================================================================================================================*/
    public void setUsernameField(String username) {this.usernameField.setText(username);}
    public String getUsernameField() {return usernameField.getText();}

    public void setUserIdField(String userid) {this.userIdField.setText(userid);}
    public String getUserIdField() {return userIdField.getText();}

    public void setPasswordField(String password) {this.passwordField.setText(password);}
    public String getPasswordField() {return passwordField.getText();}

    public void setConfirmPasswordField(String confirmPassword) {this.confirmPasswordField.setText(confirmPassword);}
    public String getConfirmPasswordField() {return confirmPasswordField.getText();}

    private String storeRole;
    private RadioButton selectRole;

    /**=================================================================================================================
                    Declare special method to collect all user inputs and store for use.
    ==================================================================================================================*/
    public void acceptAllInputs() {
        selectRole = (RadioButton) userRoles.getSelectedToggle();
        storeRole = selectRole.getText();
        setUsernameField(getUsernameField());
        setUserIdField(getUserIdField());
        setPasswordField(getPasswordField());
        setConfirmPasswordField(getConfirmPasswordField());
    }

    /**=================================================================================================================
                            Special method to clear all input fields after use..
     ==================================================================================================================*/
    public void clearAllFields() {
//        selectRole.setSelected(false);
        usernameField.clear();
        userIdField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    /**=================================================================================================================
                                implementation of form when the submit button is fired or clicked
     ==================================================================================================================*/
    public void SubmitForm() {
        try {
            Alert prompt = new Alert(Alert.AlertType.INFORMATION);
            acceptAllInputs();
            if(getUsernameField().isEmpty() || getUserIdField().isEmpty() || getPasswordField().isEmpty() ||
                    getConfirmPasswordField().isEmpty() || !selectRole.isSelected()){
                        prompt.setTitle("EMPTY FORM");
                        prompt.setHeaderText("ONE OR MORE EMPTY FIELDS");
                        prompt.setContentText("Please fill out all fields on the form");
                        prompt.showAndWait();
            } else if (!Objects.equals(getPasswordField(), getConfirmPasswordField())){
                prompt.setAlertType(Alert.AlertType.ERROR);
                prompt.setTitle("PASSWORD MISMATCH");
                prompt.setHeaderText("PASSWORD FIELDS DO NOT MATCH!");
                prompt.setContentText("Please enter a matched password to proceed.");
                prompt.setResult(ButtonType.CLOSE);
                prompt.showAndWait();
                passwordField.clear();
                confirmPasswordField.clear();
            }else {
                Dashboard.registerNewUser(getUsernameField(), getUserIdField(), getPasswordField(), getConfirmPasswordField(), storeRole);
                clearAllFields();
            }
        }catch (NullPointerException getAll) {
            Alert empty = new Alert(Alert.AlertType.ERROR);
            empty.setTitle("RADIO BUTTON EMPTY");
            empty.setHeaderText("PLEASE SELECT A USER ROLE FOR THE USER");
            empty.setContentText("The user role must be selected to to proceed.");
            empty.showAndWait();
            getAll.printStackTrace();
        } catch (Exception get) {
            get.printStackTrace();
        }

    }



























}//end of class.....

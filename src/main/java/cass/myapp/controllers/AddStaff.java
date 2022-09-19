package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import cass.myapp.models.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
//import org.controlsfx.control.spreadsheet.SpreadsheetCell;
//import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class AddStaff {

    Database DB_OBJECT = new Database();
    PreparedStatement prepare = null;
    Statement stmt = null;
    ResultSet result = null;

    public AddStaff() {}

    @FXML
    public TextField firstNameField, lastNameField, numberField, departmentField, titleField, subjectField, staffIdField;
    @FXML
    public ToggleGroup chooseGender;
    RadioButton genderButton;
    String holdGender;

    /**=================================================================================================================
                                    SETTERS AND GETTERS FOR THE TEXT FIELDS.
     ===================================================================================================================*/
    public void setFirstNameField(String firstname) {this.firstNameField.setText(firstname);}
    public String getFirstNameField() {return firstNameField.getText();}

    public void setLastNameField(String lastname) {this.lastNameField.setText(lastname);}
    public String getLastNameField() {return this.lastNameField.getText();}

    public void setNumberField(Integer phoneNumber){this.numberField.setText(String.valueOf(phoneNumber));}
    public int getNumberField() {return Integer.parseInt(this.numberField.getText());}

    public void setDepartmentField(String department) {this.departmentField.setText(department);}
    public String getDepartmentField() {return this.departmentField.getText();}

    public void setTitleField(String title) {this.titleField.setText(title);}
    public String getTitleField() {return titleField.getText();}

    public void setStaffIdField(String staffId) {this.staffIdField.setText(staffId);}
    public String getStaffIdField() {return staffIdField.getText();}

    public void setSubjectField(String subject) {this.subjectField.setText(subject);}
    public String getSubjectField() {return subjectField.getText();}

    /**=================================================================================================================
                                    METHOD TO GET ALL USER INPUTS FROM THE FORM
     ===================================================================================================================*/
    public void getAllInputs() {
        genderButton = (RadioButton) chooseGender.getSelectedToggle();
        holdGender = genderButton.getText();
        setFirstNameField(firstNameField.getText());
        setLastNameField(getLastNameField());
        setStaffIdField(getStaffIdField());
        setDepartmentField(getDepartmentField());
        setNumberField(getNumberField());
        setSubjectField(getSubjectField());
        setTitleField(getTitleField());
    }
    /**=================================================================================================================
                                        METHOD TO CLEAR ALL TEXT FIELDS
     ===================================================================================================================*/
    public void clearAllFields() {
        firstNameField.clear();
        lastNameField.clear();
        genderButton.setSelected(false);
        departmentField.clear();
        numberField.clear();
        staffIdField.clear();
        titleField.clear();
        subjectField.clear();
    }
    /**=================================================================================================================
                              ACTION METHOD TO PROCESS DATA WHEN THEN submit button IS FIRED
    ===================================================================================================================*/
    public void submitForm(){
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        try {
            getAllInputs();
            if(getFirstNameField().isEmpty() || getLastNameField().isEmpty() || getDepartmentField().isEmpty() || numberField.getText().isEmpty() ||
            getTitleField().isEmpty() || getSubjectField().isEmpty() || getTitleField().isEmpty() || !genderButton.isSelected()) {
            prompt.setContentText("Please check and fill required fields");
            prompt.setTitle("EMPTY FIELD");
            prompt.setHeaderText("ONE OR MORE INPUT FIELD LEFT UNFILLED");
            prompt.showAndWait();
            }
//            else if (numberField.getLength() < 10 || numberField.getLength() > 11) {
//                prompt.setHeaderText("PHONE NUMBER SHOULD BE EXACTLY 10 NUMBERS");
//                prompt.setTitle("INVALID INPUT");
//                prompt.setContentText("Please make sure to input the right values.");
//                prompt.showAndWait();
//            }
            else {
                prompt.setAlertType(Alert.AlertType.CONFIRMATION);
                prompt.setTitle("CONFIRM");
                prompt.setHeaderText("ARE YOU SURE YOU WANT TO ADD THIS USER? ");
                prompt.setContentText("Confirm to add or Cancel to abort");
                Optional<ButtonType> choose = prompt.showAndWait();
                if(choose.isPresent() && choose.get() == ButtonType.OK) {
                    String insertQuery = "INSERT INTO staff(first_name, last_name, gender, staffid, phone_number, department, teaches, title) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                    prepare = DB_OBJECT.CONNECT().prepareStatement(insertQuery);
                    prepare.setString(1, getFirstNameField());
                    prepare.setString(2, getLastNameField());
                    prepare.setString(3, holdGender);
                    prepare.setString(4, getStaffIdField());
                    prepare.setInt(5, getNumberField());
                    prepare.setString(6, getDepartmentField());
                    prepare.setString(7, getSubjectField());
                    prepare.setString(8, getTitleField());
                    int count = 0;
                    count = prepare.executeUpdate();
                    if(count>0) {
                        prompt.setTitle("SUCCESSFUL");
                        prompt.setHeaderText("STAFF MEMBER WITH ID " + getStaffIdField() + " HAS BEEN ADDED");
                        prompt.setContentText("Your process ended successfully.");
                        prompt.showAndWait();
                        clearAllFields();

                    } else {
                        prompt.setHeaderText("SORRY, UNABLE TO EXECUTE TASK");
                        prompt.setTitle("FAILED");
                        prompt.setContentText("Your process failed, please contact administrator");
                        prompt.showAndWait();
                        clearAllFields();
                    }
                }
            }
        } catch(NumberFormatException e) {
            prompt.setContentText("Please input only Numbers in Phone Number Field");
            prompt.setTitle("EMPTY FIELD");
            prompt.setHeaderText("PHONE NUMBER MUST BE ONLY DIGITS");
            prompt.showAndWait();
            }
        catch(NullPointerException e) {
            e.printStackTrace();
            prompt.setContentText("Please make sure to fill all fields");
            prompt.setTitle("EMPTY FIELD");
            prompt.setHeaderText("SORRY, GENDER FIELD CANNOT BE EMPTY");
            prompt.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


























}//END OF CLASS

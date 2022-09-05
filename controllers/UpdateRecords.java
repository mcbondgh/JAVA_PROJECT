package cass.myapp.controllers;

import cass.myapp.dbconnection.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.TextBoundsType;

import java.security.spec.ECField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class UpdateRecords {
    public UpdateRecords() {}
    Database DB_OBJECT = new Database();
    PreparedStatement prepare = null;
    ResultSet result = null;
    Statement stmt = null;


    @FXML
    private TextField  userSearchBox, studentSearchBox;
    @FXML
    private TextField usernameField, userIdField;
    @FXML
    private TextField userRoleField, passwordField, confirmPasswordField;
    @FXML
    private TextField firstnameField, lastnameField, emailField, genderField, studentIdField;
    @FXML
    private TextField levelField, classField, statusField, departmentField, programmeField;


     /**=================================================================================================================
                   SETTERS AND GETTERS FIELD FOR updateUser Form on the database
    ********************************************************************************************************************/
     public void setStudentSearchBox(String searchBox) {this.studentSearchBox.setText(searchBox);}
    public String getStudentSearcBox() {return studentSearchBox.getText();}

    public void setFirstnameField(String firstname) {this.firstnameField.setText(firstname);}
    public String getFirstNameField() {return firstnameField.getText();}

    public void setLastnameField(String lastname) {this.lastnameField.setText(lastname);}
    public String getLastNameField() {return lastnameField.getText();}

    public void setEmailField(String email) {this.emailField.setText(email);}
    public String getEmailField() {return emailField.getText();}

    public void setGenderField(String gender) {this.genderField.setText(gender);}
    public String getGenderField() {return genderField.getText();}
    public void setStudentIdField(String studentId) {this.studentIdField.setText(studentId);}
    public String getStudentIdField() {return studentIdField.getText();}

    public void setLevelField(int level) {this.levelField.setText(String.valueOf(level));}
    public int getLevelField() {return Integer.parseInt(levelField.getText());}

    public void setClassField(String stuClass) {this.classField.setText(stuClass);}
    public String getClassField() {return classField.getText();}

    public void setStatusField(String status) {this.statusField.setText(status);}
    public String getStatusField() {return statusField.getText();}

    public void setProgrammeField(String programme) {this.programmeField.setText(programme);}
    public String getProgrammeField() {return programmeField.getText();}

    public void setDepartmentField(String department) {this.departmentField.setText(department);}
    public String getDepartmentField() {return departmentField.getText();}
    public void setUserSearchBox(String searchBox) {this.userSearchBox.setText(searchBox);}
    public String getUserSearchBox() {return userSearchBox.getText();}

    public void setUsernameField(String username) {this.usernameField.setText(username);}
    public String getUsernameField() {return usernameField.getText();}

    public void setUserIdField(String userId) {this.userIdField.setText(userId);}
    public String getUserIdField() {return userIdField.getText();}

    public void setUserRoleField(String userRole) {this.userRoleField.setText(userRole);}
    public String getUserRoleField() {return userRoleField.getText();}

    public void setPasswordField(String password) {this.passwordField.setText(password);}
    public String getPasswordField() {return passwordField.getText();}

    public void setConfirmPasswordField(String confirmPassword) {this.confirmPasswordField.setText(confirmPassword);}
    public String getConfirmPasswordField() {return confirmPasswordField.getText();}


    /**=================================================================================================================
                special methods to get all user inputs and clear after update button is fired and successful for users
    ********************************************************************************************************************/
    public void collectSearchInput() {
        setUserSearchBox(getUserSearchBox());
    }
    public void getAll() {
        setUsernameField(getUsernameField());
        setUserIdField(getUserIdField());
        setUserRoleField(getUserRoleField());
        setPasswordField(getPasswordField());
        setConfirmPasswordField(getConfirmPasswordField());
    }
    public void clearFields() {
        studentSearchBox.clear();
        usernameField.clear();
        userIdField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        userRoleField.clear();
    }

    /**=================================================================================================================
                   implementation of a search pattern when the search button is fired or clicked for a user.
    ********************************************************************************************************************/
    public void searchUserOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            collectSearchInput();
            if (getUserSearchBox().isEmpty()) {
            alert.setTitle("Empty Field");
            alert.setHeaderText("Please Input Username or User Id to perform search");
            alert.setContentText("Search field cannnot be empty.");
            alert.showAndWait();
        } else {
                String searchQuery = "SELECT * FROM users WHERE userName LIKE '"+getUserSearchBox()+"' OR user_id LIKE '"+getUserSearchBox()+"';";
                stmt = DB_OBJECT.CONNECT().createStatement();
                result = stmt.executeQuery(searchQuery);
                if(result.next()) {
                    String username = result.getString("userName");
                    String id = result.getString("user_id");
                    String role = result.getString("user_role");
                    String password = result.getString("password");
                    String confirmPass = result.getString("confirm_password");

                    //SET VALUES INTO TEXT APPROPRIATE TEXT FIELDS TO DISPLAY
                    setUsernameField(username);
                    setUserIdField(id);
                    setUserRoleField(role);
                    setPasswordField(password);
                    setConfirmPasswordField(confirmPass);
                } else {
                    alert.setTitle("NULL SEARCH");
                    alert.setHeaderText("NO RECORD MARCHES YOUR SEARCH PATTERN");
                    alert.setContentText("Please make sure you search for a user that is a registered user.");
                    alert.showAndWait();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
        /**=================================================================================================================
                  implementation of the delete method when the delete button is fired or is clicked for a user.
    ********************************************************************************************************************/
    public void deleteUserOnAction() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        try {
            if (getUsernameField().isEmpty() || getUserIdField().isEmpty() || getUserRoleField().isEmpty() || getPasswordField().isEmpty() || getConfirmPasswordField().isEmpty()) {
                alert.setTitle("EMPTY FIELDS");
                alert.setHeaderText("YOU CANNOT DELETE AN EMPTY FIELD");
                alert.setContentText("Please make a search before you delete a record");
                alert.showAndWait();
            } else if (getUserSearchBox().isEmpty()) {
                alert.setTitle("SEARCH FIELD EMPTY");
                alert.setHeaderText("PLEASE SEARCH BOX IS EMPTY, MAKE A SEARCH BEFORE DELETE ACTION");
                alert.setContentText("You cannot search and delete a null record.");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM ACTION");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO DELETE USER WITH USERNAME " + getUsernameField()+ "?");
                alert.setContentText("Please confirm action to execute or cancel to abort ");
                Optional<ButtonType> choose = alert.showAndWait();
            if(choose.isPresent() && choose.get() == ButtonType.OK){
                String deleteQuery = "DELETE FROM users WHERE userName='"+getUserSearchBox()+"' OR user_id='"+getUserSearchBox()+"';";
                stmt = DB_OBJECT.CONNECT().createStatement();
                int count = 0;
                count = stmt.executeUpdate(deleteQuery);
                if(count > 0) {
                    alert.setTitle("DELETE RECORD");
                    alert.setHeaderText("USER WITH NAME: " + getUsernameField() + " HAS BEEN DELETED");
                    alert.setContentText("Delete operation executed successfully");
                    alert.showAndWait();
                    clearFields();
                } else {
                    alert.setHeaderText("UNABLE TO EXECUTE ACTION");
                    alert.setContentText("Please contact administrator for assistance");
                    alert.showAndWait();
                }
            }
            }
        }catch (Exception failed) {
            failed.printStackTrace();
        }
    }
    /**=================================================================================================================
                  implementation of the update method when the update button is fired or is clicked for a user
    ********************************************************************************************************************/
    public void updateUserOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            getAll();
            if (getUsernameField().isEmpty() || getUsernameField().isEmpty() || getUserRoleField().isEmpty() ||
                    getPasswordField().isEmpty() || getConfirmPasswordField().isEmpty()) {
            alert.setTitle("EMPTY FIELDS");
            alert.setHeaderText("YOU CANNOT UPDATE AN EMPTY FIELD, PLEASE PERFORM A VALID SEARCH TO UPDATE RECORD");
            alert.setContentText("On or more text field seem to be empty.");
            alert.showAndWait();
            } else {
                if (!Objects.equals(getPasswordField(), getConfirmPasswordField())) {
                    alert.setTitle("MISMATCH");
                    alert.setHeaderText("SORRY, PASSWORD FIELDS DO NOT MATCH");
                    alert.setContentText("Please make sure your password fields match");
                    alert.showAndWait();
                    passwordField.clear();
                    confirmPasswordField.clear();
                } else {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE RECORDS FOR " + getUsernameField() +" ?");
                alert.setContentText("Please confirm decision to proceed else cancel");
                Optional<ButtonType> choose = alert.showAndWait();
                if (choose.isPresent() && choose.get() == ButtonType.OK) {
                    String updateQuery = "UPDATE users SET userName=?, user_id=?, user_role=?, password=?, confirm_password=? WHERE userName=? OR user_id=?";
                prepare = DB_OBJECT.CONNECT().prepareStatement(updateQuery);
                prepare.setString(1, getUsernameField());
                prepare.setInt(2,Integer.parseInt(getUserIdField()));
                prepare.setString(3, getUserRoleField());
                prepare.setString(4, getPasswordField());
                prepare.setString(5, getConfirmPasswordField());
                prepare.setString(6, getUsernameField());
                prepare.setString(7, getUserIdField());
                int count = prepare.executeUpdate();
                if(count>0) {
                    alert.setTitle("SUCCESSFUL");
                    alert.setHeaderText("RECORD SUCCESSFULLY UPDATED");
                    alert.setContentText("You operation has been successfully executed");
                    alert.showAndWait();
                    clearFields();
                    }
                }else {
                    alert.setTitle("CANCELLED");
                    alert.setHeaderText("YOUR UPDATE OPERATION HAS BEEN CANCELLED");
                    alert.setContentText("Thank you ");
                    alert.showAndWait();
                }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**=================================================================================================================
                special methods to get all user inputs and clear after update button is fired and successful
    ********************************************************************************************************************/
    public void collectAllInputs() {
        setFirstnameField(getFirstNameField());
        setLastnameField(getLastNameField());
        setStudentIdField(getStudentIdField());
        setGenderField(getGenderField());
        setEmailField(getEmailField());
        setLevelField(getLevelField());
        setClassField(getClassField());
        setStatusField(getStatusField());
        setProgrammeField(getProgrammeField());
        setDepartmentField(getDepartmentField());
    }
    public void clearStudentFields() {
        firstnameField.clear();
        lastnameField.clear();
        studentIdField.clear();
        emailField.clear();
        statusField.clear();
        levelField.clear();
        classField.clear();
        programmeField.clear();
        departmentField.clear();
        genderField.clear();
    }
    /**=================================================================================================================
                    IMPLEMENTATION FOR STUDENT SEARCH PATTERN WHEN THE SEARCH BUTTON IS CLICKED OR FIRED
    ********************************************************************************************************************/
    public void searchStudentOnAction(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            setStudentSearchBox(getStudentSearcBox());
            if (getStudentSearcBox().isEmpty()) {
                alert.setTitle("Empty Field");
                alert.setHeaderText("PLEASE PROVIDE LAST NAME OR STUDENT ID TO PERFORM SEARCH");
                alert.setContentText("Search field cannot be empty.");
                alert.showAndWait();
            } else {
                String searchQuery = "SELECT * FROM students WHERE last_name LIKE '"+getStudentSearcBox()+"' OR student_id LIKE '"+getStudentSearcBox()+"';";
                stmt = DB_OBJECT.CONNECT().createStatement();
                result = stmt.executeQuery(searchQuery);
                    if(result.next()) {
                        String firstname = result.getString("first_name");
                        String lastname = result.getString("last_name");
                        String stu_id = result.getString("student_id");
                        String mail = result.getString("email");
                        String gender = result.getString("gender");
                        int level = result.getInt("level");
                        String stu_class = result.getString("class");
                        String status = result.getString("status");
                        String programme = result.getString("programme");
                        String department = result.getString("department");

                //SET VALUES INTO TEXT APPROPRIATE TEXT FIELDS TO DISPLAY
                        setFirstnameField(firstname);
                        setLastnameField(lastname);
                        setStudentIdField(stu_id);
                        setEmailField(mail);
                        setGenderField(gender);
                        setLevelField(level);
                        setClassField(stu_class);
                        setStatusField(status);
                        setProgrammeField(programme);
                        setDepartmentField(department);
                    } else {
                        alert.setTitle("RESULT NOT FOUND");
                        alert.setHeaderText("SORRY, NO SEARCH RESULT MATCHES YOUR INPUT");
                        alert.setContentText("Please check carefully your search input.");
                        alert.showAndWait();
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**=================================================================================================================
                  implementation of the update method when the update button is fired or is clicked for a student.
    ********************************************************************************************************************/
    public void updateStudentOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            collectAllInputs();
            if (getStudentSearcBox().isEmpty()) {
                alert.setHeaderText("SEARCH FIELD ID EMPTY, PLEASE PROVIDE DATA TO SEARCH RECORD");
                alert.setTitle("EMPTY SEARCH");
                alert.setContentText("You must provide a search record to enable you perform this action");
                alert.showAndWait();
            } else if (getFirstNameField().isEmpty() || getLastNameField().isEmpty() || getGenderField().isEmpty() || getEmailField().isEmpty() ||
                        getClassField().isEmpty() || String.valueOf(getLevelField()).isEmpty() || getStatusField().isEmpty()) {
                alert.setTitle("EMPTY FIELDS");
                alert.setHeaderText("YOU CANNOT UPDATE AN EMPTY FIELD.");
                alert.setContentText("Please make a search before you try to update a record");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM ACTION");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE RECORD FOR STUDENT " + getStudentSearcBox() +"?");
                alert.setContentText("Please confirm action to execute update else cancel to abort");
                Optional<ButtonType> choose = alert.showAndWait();
                if(choose.isPresent() && choose.get() == ButtonType.OK) {
                    String updateQuery = "UPDATE students SET first_name=?, last_name=?, email=?, gender=?, level=?, class=?, programme=?, department=?" +
                            " WHERE student_id=? OR last_name=?";
                    prepare = DB_OBJECT.CONNECT().prepareStatement(updateQuery);
                    prepare.setString(1, getFirstNameField());
                    prepare.setString(2, getLastNameField());
                    prepare.setString(3, getEmailField());
                    prepare.setString(4, getGenderField());
                    prepare.setInt(5, getLevelField());
                    prepare.setString(6, getClassField());
                    prepare.setString(7, getProgrammeField());
                    prepare.setString(8, getDepartmentField());
                    prepare.setString(9, getStudentIdField());
                    prepare.setString(10, getLastNameField());
                    int count = prepare.executeUpdate();
                    if(count > 0) {
                        alert.setTitle("SUCCESSFUL");
                        alert.setHeaderText("STUDENT RECORD UPDATED SUCCESSFULLY.");
                        alert.setContentText("Your statement has executed successfully.");
                        alert.showAndWait();
                        clearStudentFields();
                    } else {
                        alert.setTitle("FAILED");
                        alert.setHeaderText("YOUR REQUEST FAILED TO EXECUTE");
                        alert.setContentText("Please contact system admin for assistance.");
                        alert.showAndWait();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**=================================================================================================================
                  implementation of the delete method when the delete button is fired or is clicked for a student.
    ********************************************************************************************************************/
    public void deleteStudentOnAction() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if(getStudentSearcBox().isEmpty()) {
                alert.setTitle("EMPTY SEARCH");
                alert.setHeaderText("YOU CANNOT DELETE AN EMPTY RECORD");
                alert.setContentText("Please provide a valid search record");
                alert.showAndWait();
            } else if (getFirstNameField().isEmpty() || getLastNameField().isEmpty() || getGenderField().isEmpty() || getClassField().isEmpty() ||
            getEmailField().isEmpty() || getDepartmentField().isEmpty() || getProgrammeField().isEmpty()) {
                alert.setTitle("EMPTY TEXT FIELDS");
                alert.setHeaderText("YOU CANNOT PERFORM DELETE OPERATION ON TEXT FIELDS.");
                alert.setContentText("Please make a valid search before you execute a delete opration");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO DELETE THIS USER WITH " + getStudentSearcBox()+ "?");
                alert.setContentText("Please confirm your action else cancel to abort.");
                Optional<ButtonType> choose = alert.showAndWait();
                if(choose.isPresent() && choose.get() == ButtonType.OK) {
                    String deleteQuery = "DELETE FROM students WHERE last_name='"+getStudentSearcBox()+"' OR student_id='"+getStudentSearcBox()+"'";
                    stmt = DB_OBJECT.CONNECT().createStatement();
                    int count = 0;
                    count = stmt.executeUpdate(deleteQuery);
                    if(count>0){
                        alert.setTitle("SUCCESSFUL");
                        alert.setHeaderText("USER DATA HAS SUCCESSFULLY BEEN DELETED");
                        alert.setContentText("Operation executed successfully.");
                        alert.showAndWait();
                        clearStudentFields();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }










































































}//end of class

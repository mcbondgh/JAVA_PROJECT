package cass.myapp.models;

import cass.myapp.controllers.AddStudent;
import cass.myapp.controllers.AddUser;
import cass.myapp.controllers.UserLogin;
import cass.myapp.dbconnection.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import javax.xml.stream.events.EndDocument;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class Dashboard {

    public Dashboard() {}

    private static PreparedStatement prepare = null;
     static ResultSet result = null;
     Statement statement = null;
     static Database DB_OBJECT = new Database();

    /** insert into signout Table using the active username when the action is set on signout*/
    public void signout(String username) throws SQLException {
        String insertQuery = "INSERT INTO signout(user_id) SELECT user_id FROM users WHERE userName ='"+username+"';";
        statement = DB_OBJECT.CONNECT().createStatement();
        statement.execute(insertQuery);
    }

    /*******************************************************************************************************************
                                insert statement to add register a new staff member to the system
     *******************************************************************************************************************/
        /***********************************************************************************************************
                                insert statement to register a new student into the system
    ********************************************************************************************************************/
    public static void RegisterStudent(String firstname, String lastname, String studentId, String email, String gender, int level, String stuClass,
                                       String status, String programme, String department) throws SQLException {
         Alert prompt = new Alert(Alert.AlertType.WARNING);
        try {
            String selectQuery = "SELECT student_id FROM students WHERE student_id = '"+studentId+"'";
            Statement stmt = DB_OBJECT.CONNECT().createStatement();
            result = stmt.executeQuery(selectQuery);
            boolean isPresent = false;
            if (result.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String output = result.getString("student_id");
                if (Objects.equals(output.toLowerCase(), studentId.toLowerCase())){
                    isPresent = true;
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("TAKEN");
                    alert.setContentText("Please enter a unique student id");
                    alert.setHeaderText("THE STUDENT ID PROVIDED ALREADY EXIST");
                    alert.showAndWait();
                }
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("ARE YOU SURE YOU WANT TO STUDENT WITH ID: " + studentId);
                confirm.setTitle("CONFIRM");
                confirm.setContentText("Please select your option to execute.");
                Optional<ButtonType> choose = confirm.showAndWait();
                if (choose.isPresent() && choose.get() == ButtonType.OK) {
                    String insertQuery = "INSERT INTO students(first_name, last_name, student_id, email, gender, level, class, status, programme, department) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    prepare = DB_OBJECT.CONNECT().prepareStatement(insertQuery);
                    prepare.setString(1, firstname);
                    prepare.setString(2, lastname);
                    prepare.setString(3, studentId);
                    prepare.setString(4,email);
                    prepare.setString(5, gender);
                    prepare.setInt(6, level);
                    prepare.setString(7, stuClass);
                    prepare.setString(8, status);
                    prepare.setString(9, programme);
                    prepare.setString(10, department);
                    prepare.execute();
                    confirm.setAlertType(Alert.AlertType.INFORMATION);
                    confirm.setHeaderText("STUDENT SUCCESSFULLY ADD");
                    confirm.showAndWait();
                }
            }
        }catch (NullPointerException e) {
            prompt.setTitle("NULL VALUES");
            prompt.setHeaderText("PLEASE SELECT YOUR GENDER AND YOUR PERSONALITY");
            prompt.setContentText("Please check and fill all fields");
            prompt.showAndWait();
            e.printStackTrace();
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

    /*******************************************************************************************************************
                                insert statement to register a new USER into the system
    ********************************************************************************************************************/
    public static void registerNewUser(String username, String userId, String password, String confirmPassword, String userRole) {
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        try {
            //CHECK FROM DATABASE IF THE USERNAME ALREADY EXIST OR THE USER ID IS ALREADY IN THE SYSTEM.
            String selectQuery = "SELECT username FROM users WHERE username= ?;";
            prepare = DB_OBJECT.CONNECT().prepareStatement(selectQuery);
            prepare.setString(1, username);
            result = prepare.executeQuery();
            if(result.next()) {
                String name = result.getString("userName");
                if(Objects.equals(username, name)) {
                    prompt.setAlertType(Alert.AlertType.INFORMATION);
                    prompt.setHeaderText("SORRY, USERNAME HAS ALREADY BEEN TAKEN");
                    prompt.setTitle("USERNAME TAKEN");
                    prompt.setContentText("Please choose a valid username to proceed.");
                    prompt.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO ADD THIS USER?");
                Optional<ButtonType> choose = alert.showAndWait();
                if(choose.isPresent() && choose.get() == ButtonType.OK) {
                    String insertQuery = "INSERT INTO users(userName, user_id, password, confirm_password, user_role)VALUES(?, ?, ?, ?, ?)";
                        prepare = DB_OBJECT.CONNECT().prepareStatement(insertQuery);
                        prepare.setString(1, username);
                        prepare.setString(2, userId);
                        prepare.setString(3, password);
                        prepare.setString(4, confirmPassword);
                        prepare.setString(5, userRole);
                        int count = prepare.executeUpdate();
                        if(count > 0) {
                            alert.setTitle("SUCCESS");
                            alert.setHeaderText("USER WITH USERNAME: " + username + " HAS BEEN ADDED");
                            alert.setContentText("Operation successfully executed.");
                            alert.showAndWait();
                            AddUser USER_OBJECT = new AddUser();
//                            USER_OBJECT.clearAllFields();
                        }
                }
            }
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ID EXIST");
            alert.setHeaderText("SORRY, YOU CANNOT ADD THIS USER.");
            alert.setContentText("User must be a registered student or staff member");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
                                         IMPLEMENTATION OF USER LOGIN
    ********************************************************************************************************************/
    public static void Userlogin(String usernmae, String password) throws SQLException, IOException {
        String selectQuery = "SELECT userName, password user_role FROM users WHERE username=? AND password= ?;";
        prepare = DB_OBJECT.CONNECT().prepareStatement(selectQuery);
        prepare.setString(1, usernmae);
        prepare.setString(2, password);
        result = prepare.executeQuery();
        if (!result.next()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID CREDENTIALS");
            alert.setHeaderText("WRONG USERNAME OR PASSWORD");
            alert.setContentText("Please provide the correct Username and Password to login to CASS");
            alert.showAndWait();
        } else {
            String nestedQuery = "INSERT INTO signin(userName, user_id, user_role) SELECT userName, user_id, user_role FROM users WHERE userName='"+usernmae+"' AND password='"+password+"'";
            Statement stmt = DB_OBJECT.CONNECT().createStatement();
            stmt.execute(nestedQuery);

            String access = result.getString("user_role");
            UserLogin LOGIN_OBJECT = new UserLogin();

        /**=======================================> SWITCH CASE APPROACH <=============================================*/
//            switch(access.toLowerCase()) {
//                case "student" -> {
//                    LOGIN_OBJECT.displayAll("studentDashboard.fxml");
//                    LOGIN_OBJECT.loginButton.getScene().getWindow().hide();
//                    break;
//                }
//                case "staff" -> {
//                    LOGIN_OBJECT.displayAll("staffinterface.fxml");
//                    LOGIN_OBJECT.loginButton.getScene().getWindow().hide();
//                    break;
//                }
////                default -> throw new IllegalStateException("Unexpected value: " + ACCESS_LEVEL.toLowerCase());
//            }
//            result.close();
//            DB_OBJECT.CONNECT().close();

        /**=======================================> SWITCH CASE APPROACH <=============================================*/
            String ACCESS_LEVE1 = "student";
            String ACCESS_LEVEL2 = "staff";
            if(Objects.equals(ACCESS_LEVE1, result.getString("user_role"))) {
                LOGIN_OBJECT.studentInterface();
                LOGIN_OBJECT.loginButton.getScene().getWindow().hide();
            } else if(Objects.equals(ACCESS_LEVEL2, result.getString("user_role"))) {
                LOGIN_OBJECT.staffInterface();
                LOGIN_OBJECT.loginButton.getScene().getWindow().hide();
            }
            prepare.close();
            result.close();
            DB_OBJECT.CONNECT().close();

        }
    }
















}//end of class....

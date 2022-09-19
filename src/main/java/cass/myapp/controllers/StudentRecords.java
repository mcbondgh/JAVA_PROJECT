package cass.myapp.controllers;

import cass.myapp.StudentTableView;
import cass.myapp.dbconnection.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentRecords implements Initializable {
    public StudentRecords() {}
    Database DB_OBJECT = new Database();
    Statement stmt = null;
    ResultSet result = null;

    @FXML
    private TableView<StudentTableView> viewStudentData;
    @FXML
    private TableColumn<StudentTableView, Integer> id, level;
    @FXML
    private TableColumn<StudentTableView, String> firstName, lastName, studentId, gender, email, stuClass;
    @FXML
    private  TableColumn<StudentTableView, Date> dateJoined;

     /**=================================================================================================================
                                                    CREATE OBSERVERBLE LIST
    ===================================================================================================================*/
        ObservableList<StudentTableView> studentRecords = FXCollections.observableArrayList();
        ObservableList<StudentTableView> studentRecordsSearch = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String selectQuery = "SELECT * FROM students";
            stmt = DB_OBJECT.CONNECT().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                Integer stu_id = result.getInt("id");
                String stu_firstname = result.getString("first_name");
                String stu_lastname = result.getString("last_name");
                String stu_student_id = result.getString("student_id");
                String stu_gender = result.getString("gender");
                String stu_email = result.getString("email");
                Integer stu_level = result.getInt("level");
                String stu_class = result.getString("class");
                Date stu_date = result.getDate("date_added");

                studentRecords.add(new StudentTableView(stu_id, stu_firstname, stu_lastname, stu_student_id, stu_gender, stu_email, stu_level, stu_class, stu_date));
            }
            setTableView();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setTableView () {
        id.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("stu_firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("stu_lastName"));
        studentId.setCellValueFactory(new PropertyValueFactory<>("stu_studentId"));
        gender.setCellValueFactory(new PropertyValueFactory<>("stu_gender"));
        email.setCellValueFactory(new PropertyValueFactory<>("stu_email"));
        level.setCellValueFactory(new PropertyValueFactory<>("stu_level"));
        stuClass.setCellValueFactory(new PropertyValueFactory<>("stu_class"));
        dateJoined.setCellValueFactory(new PropertyValueFactory<>("stu_date"));
        viewStudentData.setItems(studentRecords);
//        viewStudentData.setItems(studentRecordsSearch);

    }


public void searchStudentOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            String selectQuery = "SELECT * FROM students WHERE student_id REGEXP '"+studentId+"%' OR last_name REGEXP'"+lastName+"%'";
            stmt = DB_OBJECT.CONNECT().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                Integer stu_id = result.getInt("id");
                String stu_first_name = result.getString("first_name");
                String stu_last_name = result.getString("last_name");
                String stu_student_id = result.getString("student_id");
                String stu_gender = result.getString("gender");
                String stu_email = result.getString("email");
                Integer stu_level = result.getInt("level");
                String stu_class = result.getString("class");
                Date stu_date  = result.getDate("date_added");

                studentRecordsSearch.add(new StudentTableView(stu_id, stu_first_name, stu_last_name, stu_student_id, stu_gender, stu_email, stu_level, stu_class, stu_date));
            }
            alert.setHeaderText("sorry, username not found...");
            alert.showAndWait();
            setTableView();
        }catch (Exception e) {
            e.printStackTrace();
        }
}



































































































}//end of class

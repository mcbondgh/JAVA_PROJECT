package cass.myapp.controllers;

import cass.myapp.AttendanceTableView;
import cass.myapp.dbconnection.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class AttendanceSheet implements Initializable {
    Database DB_OBJECT = new Database();
    PreparedStatement prepare = null;
    Statement stmt = null;
    ResultSet result = null;


    public AttendanceSheet() {}

    @FXML
    private TableView<AttendanceTableView> AttendanceTable;
    @FXML
    private TableColumn<AttendanceTableView, String > studentId;
    @FXML
    private TableColumn<AttendanceTableView, String> studentName;
    @FXML
    private TableColumn<AttendanceTableView, String> studentClass;
    @FXML
    private TableColumn<AttendanceTableView, Integer> subjectDuration;
    @FXML
    private TableColumn<AttendanceTableView, String> studentStatus;

    @FXML
    private ComboBox<Object> classComboBox;
    @FXML
    private ComboBox<Object> subjectComboBox;
    @FXML
    private DatePicker attendanceDate;
    public String classValue;
    public String subjectValue;
    public LocalDate stu_attendanceDate;

    /**=================================================================================================================
                                            IMPLEMENTATION OF LOGICS
     ================================================================================================================**/
    public void getAllInputs() {
        classValue = String.valueOf(classComboBox.getValue());
        subjectValue = String.valueOf(subjectComboBox.getValue());
        stu_attendanceDate = attendanceDate.getValue();
    }

    public void clearAllFields() {
        AttendanceTable.getItems().clear();
    }

    /**=================================================================================================================
                                  OBSERVABLE LIST FOR Subject and Class Combo Boxes.
     ==================================================================================================================*/
    ObservableList<Object> allSubjects = FXCollections.observableArrayList();
    ObservableList<Object> allClasses = FXCollections.observableArrayList();
    ObservableList <Object> getStatus = FXCollections.observableArrayList();
    ObservableList<AttendanceTableView> attendanceData = FXCollections.observableArrayList();
    List<List<String>> getData = new ArrayList<>();

      @Override
    public void initialize(URL location, ResourceBundle resources) {
          try {
              int present = 1;
              int absent = 0;
              getStatus.setAll(present, absent);
              populateClasBox();
              populateSubjectBox();
          }catch (Exception e) {
              throw new RuntimeException(e);
          }
    }
    /**=================================================================================================================
                                implementation of Class ComboBox and Subject ComboBox
     ==================================================================================================================*/
    public void populateSubjectBox() throws SQLException {
          try {
               String selectQuery = "SELECT subject FROM subjects";
          stmt = DB_OBJECT.CONNECT().createStatement();
          result = stmt.executeQuery(selectQuery);
          while (result.next()) {
              String tb_subject = result.getString("subject");
              allSubjects.add(tb_subject);
          }
          }catch (Exception e) {
              throw new RuntimeException(e);
          }
          subjectComboBox.setItems(allSubjects);
    }
    public void populateClasBox() {
        try {
            String selectQuery = "SELECT DISTINCT(class) FROM students";
            stmt = DB_OBJECT.CONNECT().createStatement();
            result =  stmt.executeQuery(selectQuery);
            while (result.next()) {
                String tb_class = result.getString("class");
                allClasses.add(tb_class);
                classComboBox.setItems(allClasses);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*******************************************************************************************************************
                                        implementation of On Action methods.
     *******************************************************************************************************************/
    public void processAttendance() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        try {
            getAllInputs();
            if(classComboBox.getSelectionModel().isEmpty()){
                alert.setTitle("EMPTY CLASS");
                alert.setHeaderText("PLEASE SELECT YOUR CLASS");
                alert.setContentText("Class filed cannot be empty, please select a class");
                alert.showAndWait();
            } else if (subjectComboBox.getSelectionModel().isEmpty()) {
                alert.setTitle("EMPTY SUBJECT");
                alert.setHeaderText("PLEASE SELECT A SUBJECT TO TAKE ATTENDANCE");
                alert.setContentText("Subject field cannot be empty, please select a subject");
                alert.showAndWait();
            } else if (attendanceDate.getValue() == null) {
                alert.setTitle("EMPTY DATE");
                alert.setHeaderText("PLEASE PICK A DATE TO TAKE ATTENDANCE");
                alert.setContentText("Attendance Date field cannot be empty, please select a date");
                alert.showAndWait();
            } else if (attendanceDate.getValue() ==  null && subjectComboBox.getSelectionModel().isEmpty() && classComboBox.getSelectionModel().isEmpty()) {
                alert.setTitle("EMPTY FIELDS");
                alert.setHeaderText("PLEASE MAKE SURE SUBJECT, CLASS AND DATE ARE SELECTED");
                alert.setContentText("Please make sure too set all fiends before you proceed.");
                alert.showAndWait();
            } else {
                String selectQuery = "SELECT students.student_id, students.first_name, students.last_name, students.class, subjects.duration " +
                        "FROM students, subjects WHERE class='"+classValue+"' AND subject='"+subjectValue+"' ORDER BY last_name ASC";
                stmt = DB_OBJECT.CONNECT().createStatement();
                result = stmt.executeQuery(selectQuery);
                while(result.next()) {
                    String stu_id = result.getString("student_id");
                    String stu_firstnam = result.getString("first_name");
                    String stu_lastname = result.getString("last_name");
                    String stu_class = result.getString("class");
                    int stu_duration = result.getInt("duration");
                    String fullname = stu_lastname + " " + stu_firstnam;

                    attendanceData.add(new AttendanceTableView(stu_id, fullname, stu_class, stu_duration, getStatus));
                }
                populateAttendanceTable();
                classComboBox.getSelectionModel().clearSelection();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void populateAttendanceTable() {
        studentId.setCellValueFactory(new PropertyValueFactory<>("at_studentID"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("at_fullName"));
        studentClass.setCellValueFactory(new PropertyValueFactory<>("at_class"));
        subjectDuration.setCellValueFactory(new PropertyValueFactory<>("att_duration"));
        studentStatus.setCellValueFactory(new PropertyValueFactory<>("choose"));
        AttendanceTable.setItems(attendanceData);
    }
    public void saveAttendance() throws SQLException {
        AttendanceTableView tableItem;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        try {
                for (int x = 0; x<AttendanceTable.getItems().size(); x++) {
                    tableItem = AttendanceTable.getItems().get(x);
                    String tb_fullname = tableItem.getAt_fullName();
                    String tb_studentID = tableItem.getAt_studentID();
                    String tb_class = tableItem.getAt_class();
                    int tb_duration = tableItem.getAtt_duration();
                    Integer tb_status = (Integer) tableItem.getChoose().getValue();

                    String insertQuery = "INSERT INTO stu_attendance(fullName, studentId, status, class, subject, duration, attendance_date)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)";
                    prepare = DB_OBJECT.CONNECT().prepareStatement(insertQuery);
                    prepare.setString(1, tb_fullname);
                    prepare.setString(2, tb_studentID);
                    prepare.setInt(3, tb_status);
                    prepare.setString(4, tb_class);
                    prepare.setString(5, subjectValue);
                    prepare.setInt(6, tb_duration);
                    prepare.setDate(7, Date.valueOf(stu_attendanceDate));
                    prepare.execute();
                }
                alert.setTitle("SUCCESSFUL");
                alert.setHeaderText("ATTENDANCE FOR " + subjectValue + " SAVED SUCCESSFULLY");
                alert.showAndWait();
                clearAllFields();
        }catch (NullPointerException e) {
            alert.setTitle("EMPTY STATUS");
            alert.setHeaderText("ONE OR MORE STUDENT STATUS NOT SET");
            alert.setContentText("Please make sure to take attendance for all selected students");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setWidth(300);
            alert.setResult(ButtonType.FINISH);
            alert.showAndWait();
        }
    }

    
    



}//end of class

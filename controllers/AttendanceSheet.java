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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AttendanceSheet implements Initializable {
    Database DB_OBJECT = new Database();
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
    public LocalDate attendanceValue;

    /**=================================================================================================================
                                            IMPLEMENTATION OF LOGICS
     ================================================================================================================**/
    public void getAllInputs() {
        classValue = String.valueOf(classComboBox.getValue());
        subjectValue = String.valueOf(subjectComboBox.getValue());
        attendanceValue = attendanceDate.getValue();
    }

    /**=================================================================================================================
                                  OBSERVABLE LIST FOR Subject and Class Combo Boxes.
     ==================================================================================================================*/
    ObservableList<Object> allSubjects = FXCollections.observableArrayList();
    ObservableList<Object> allClasses = FXCollections.observableArrayList();
    ObservableList <Object> getStatus = FXCollections.observableArrayList();
    ObservableList<AttendanceTableView> attendanceData = FXCollections.observableArrayList();


      @Override
    public void initialize(URL location, ResourceBundle resources) {
          try {
              pupulateClasBox();
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
    public void pupulateClasBox() {
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
                        "FROM students, subjects WHERE class='"+classValue+"' AND subject='"+subjectValue+"'";
                stmt = DB_OBJECT.CONNECT().createStatement();
                result = stmt.executeQuery(selectQuery);
                while(result.next()) {
                    String stu_id = result.getString("student_id");
                    String stu_firstnam = result.getString("first_name");
                    String stu_lastname = result.getString("last_name");
                    String stu_class = result.getString("class");
                    int stu_duration = result.getInt("duration");
                    String fullname = stu_firstnam + " " + stu_lastname;
                    int present = 1;
                    int absent = 0;
                   getStatus.setAll(present, absent);
//                    getStatus.add(1, absent);

                    attendanceData.add(new AttendanceTableView(stu_id, fullname, stu_class, stu_duration, getStatus));
                }
                populateAttendanceTable();
                subjectComboBox.getSelectionModel().clearSelection();
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
    public void saveAttendance() {
        System.out.println(attendanceValue);
        System.out.println(classValue);
        System.out.println(subjectValue);
    }




}//end of class

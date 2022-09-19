package cass.myapp.controllers;

import cass.myapp.AttendanceReportTableView;
import cass.myapp.dbconnection.Database;
import cass.myapp.dbconnection.DbRowCounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ResourceBundle;

import static javafx.scene.web.WebEvent.ALERT;

public class AttendanceReport implements Initializable {
    public AttendanceReport() {}
    Database DB_OBJECT = new Database();
    DbRowCounts COUNT_OBJ = new DbRowCounts();
    PreparedStatement prepare = null;
    ResultSet result = null;
    Statement statement = null;
    @FXML
    private TableView<AttendanceReportTableView> table_view;
    @FXML
    private TableColumn<AttendanceReportTableView, String> studentIdColumn;
    @FXML
    private TableColumn<AttendanceReportTableView, String> fullnameColumn;
    @FXML
    private TableColumn<AttendanceReportTableView, String> classColumn;
    @FXML
    private TableColumn<AttendanceReportTableView, Integer> attendanceColumn;
    @FXML
    private TableColumn<AttendanceReportTableView, Integer> absentColumn;
    @FXML
    private TableColumn<AttendanceReportTableView, Integer> presentColumn;
    @FXML
    private DatePicker date_from, date_to;
    @FXML
    private ComboBox<String> subject_box;

    @FXML
    private Label month_label, month_label2, subject_label, tutor_label, year_label;

    /**OBSERVABLE LIST */
    ObservableList<String> setSubjects = FXCollections.observableArrayList();
    ObservableList<AttendanceReportTableView> reportTableData = FXCollections.observableArrayList();

    @FXML
    private void generateAttendance() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        try {
        populateTableView();
        /**RUN AN IF STATEMENT TO CHECK IF EITHER OF THE DATE PICKER FIELDS IS EMPTY or the subject box is empty.*/
        if (subject_box.getSelectionModel().isEmpty()) {
            alert.setTitle("EMPTY SUBJECT BOX");
            alert.setHeaderText("PLEASE MAKE SURE TO SELECT A SUBJECT");
            alert.setContentText("You need to select a subject to generate a report.");
            alert.showAndWait();
        } else if (date_from.getValue() == null) {
            alert.setTitle("EMPTY SUBJECT BOX");
            alert.setHeaderText("PLEASE MAKE SURE TO SELECT DATE TO BEGIN FROM");
            alert.setContentText("You need to select date from where to generate report.");
            alert.showAndWait();
        } else if(date_to.getValue() == null) {
            alert.setTitle("EMPTY SUBJECT BOX");
            alert.setHeaderText("PLEASE MAKE SURE TO SELECT DATE TO END FROM");
            alert.setContentText("You need to select date from where to end generated report.");
            alert.showAndWait();
        } else {
            /**SET THE TUTOR LABEL TO THE CORRESPONDING LECTURER'S NAME BASED ON THE USER'S SUBJECT SELECTED...*/
            String selectQuery = "SELECT first_name, last_name FROM staff WHERE teaches = '"+subject_box.getValue()+"'";
            statement = DB_OBJECT.CONNECT().createStatement();
            result = statement.executeQuery(selectQuery);
            while(result.next()) {
                String firstname = result.getString("first_name");
                String lastname = result.getString("last_name");
                String fullname = lastname + " " + firstname;
                tutor_label.setText(fullname);
            }

        /** GET ID, STUDENT-ID, FIRST, LAST NAMES, CLASS AND TOTAL ATTENDANCE TO FILL IN THE TABLEVIEW*/
        String selectQuery2 = "SELECT DISTINCT(studentId), fullName, class FROM stu_attendance " +
                "WHERE subject = '"+subject_box.getValue()+"' ORDER BY fullName ASC";

        statement = DB_OBJECT.CONNECT().createStatement();
        result = statement.executeQuery(selectQuery2);
            String stu_fullname;
            String stu_studentId;
            String stu_class;
        while(result.next()) {
            stu_fullname = result.getString("fullName");
            stu_studentId  = result.getString("studentId");
            stu_class  = result.getString("class");
            int total_attendance = COUNT_OBJ.totalAttendance(stu_studentId, subject_box.getValue() ,date_from.getValue(), date_to.getValue());
            int total_absent = COUNT_OBJ.totalAbsent(stu_studentId, subject_box.getValue(), date_from.getValue(), date_to.getValue());
            int total_present = COUNT_OBJ.totalPresent(stu_studentId, subject_box.getValue(), date_from.getValue(), date_to.getValue());
            reportTableData.addAll(new AttendanceReportTableView( stu_studentId, stu_fullname, stu_class, total_attendance, total_absent, total_present));

        } if (table_view.getItems().isEmpty())  {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("EMPTY TABLE");
            alert.setHeaderText("SORRY, PLEASE NO RECORDS FOUND FOR SUBJECT BEING QUERIED");
            alert.setContentText("Please make sure attendance for selected subject is set before generating report");
            alert.showAndWait();
        }
        /** SETTING TEXT LABELS TO DISPLAY THEIR APPROPRIATE VALUES DERIVED FROM THE subject combo box and the date pickers*/
        subject_label.setText(subject_box.getValue());
        Month selectedDate1 = date_from.getValue().getMonth();
        Month selectDate2 = date_to.getValue().getMonth();
        Year currentYear = Year.of(date_from.getValue().getYear());
        month_label.setText(String.valueOf(selectedDate1));
        month_label2.setText(String.valueOf(selectDate2));
        year_label.setText(String.valueOf(currentYear));
        }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**NOW INSERT THE VALUES INTO THE TABLE COLUMNS OF THE TABLE VIEW TO DISPLAY THE DATA FETCHED*/
    public void populateTableView() {
        studentIdColumn.setCellValueFactory( new PropertyValueFactory<>("studentId"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("student_Class"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("total_attendance"));
        absentColumn.setCellValueFactory(new PropertyValueFactory<>("total_absent"));
        presentColumn.setCellValueFactory(new PropertyValueFactory<>("total_present"));
        table_view.setItems(reportTableData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String selectQuery = "SELECT subject FROM subjects";
            prepare = DB_OBJECT.CONNECT().prepareStatement(selectQuery);
            result = prepare.executeQuery();
            while(result.next()) {
                String getAll = result.getString("subject");
                setSubjects.add(getAll);
                subject_box.setItems(setSubjects);
            }
            populateTableView();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



































































































































}//end of class;

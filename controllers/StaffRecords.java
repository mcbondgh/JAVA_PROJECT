package cass.myapp.controllers;

import cass.myapp.StaffTableView;
import cass.myapp.dbconnection.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StaffRecords  implements Initializable {

    Database DB_OBJECT = new Database();
    PreparedStatement prepare = null;
    ResultSet result = null;

    @FXML
    private TableView<StaffTableView> viewStaffData;
    @FXML
    private TableColumn<StaffTableView, Integer> id;
    @FXML
    private TableColumn<StaffTableView, String> firstName;
    @FXML
    private TableColumn<StaffTableView, String> lastName, staffId, gender;
    @FXML
    private TableColumn<StaffTableView, String> phoneNumber, subject;
    @FXML
    private TableColumn<StaffTableView, Date> dateJoined;

    /**=================================================================================================================
                                                CREATE OBSERVERBLE LIST
    ===================================================================================================================*/
    ObservableList<StaffTableView> staffRecords = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String selectQuery = "SELECT * FROM staff;";
            prepare = DB_OBJECT.CONNECT().prepareStatement(selectQuery);
            result = prepare.executeQuery();
            while(result.next()) {
                Integer sf_id = result.getInt("id");
                String sf_firstName = result.getString("first_name");
                String sf_lastName = result.getString("last_name");
                String sf_staffId  = result.getString("staffId");
                String sf_gender = result.getString("gender");
                String sf_phoneNumber = result.getString("phone_number");
                String sf_subject = result.getString("teaches");
                Date sf_joinedDate = result.getDate("added_date");

                staffRecords.add(new StaffTableView(sf_id, sf_firstName, sf_lastName, sf_gender, sf_staffId, sf_phoneNumber, sf_subject, sf_joinedDate));
            }
            setTableView();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setTableView () {
        id.setCellValueFactory(new PropertyValueFactory<>("st_id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("st_firstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("st_lastname"));
        staffId.setCellValueFactory(new PropertyValueFactory<>("st_staffid"));
        gender.setCellValueFactory(new PropertyValueFactory<>("st_gender"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("st_phone_number"));
        subject.setCellValueFactory(new PropertyValueFactory<>("st_teaches"));
        dateJoined.setCellValueFactory(new PropertyValueFactory<>("st_added_date"));

        viewStaffData.setItems(staffRecords);
    }



































}//end of class

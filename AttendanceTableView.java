package cass.myapp;

import cass.myapp.controllers.AttendanceSheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AttendanceTableView{

    public AttendanceTableView() {}

    private String at_studentID;
    private String at_fullName;
    private String at_class;
    private int att_duration;
    private ComboBox choose;
    public Integer present = 1;
    public Integer absent = 0;

    ObservableList<Object> insert = FXCollections.observableArrayList();




    public AttendanceTableView(String at_studentID, String at_fullName, String at_class, int att_duration, ObservableList value) {
        this.at_studentID = at_studentID;
        this.at_fullName = at_fullName;
        this.at_class = at_class;
        this.att_duration = att_duration;
        this.choose = new ComboBox(value);
        choose.setMaxWidth(100);
        }

//
    public String getAt_studentID() {
        return at_studentID;
    }

    public void setAt_studentID(String at_studentID) {
        this.at_studentID = at_studentID;
    }

    public ComboBox getChoose() {
        return choose;
    }

    public void setChoose(ComboBox choose) {
        this.choose = choose;
    }

    public String getAt_fullName() {
        return at_fullName;
    }

    public void setAt_fullName(String at_fullName) {
        this.at_fullName = at_fullName;
    }

    public String getAt_class() {
        return at_class;
    }

    public void setAt_class(String at_class) {
        this.at_class = at_class;
    }

    public int getAtt_duration() {
        return att_duration;
    }

    public void setAtt_duration(int att_duration) {
        this.att_duration = att_duration;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }


}//end of clas....

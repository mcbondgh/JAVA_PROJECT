package cass.myapp;

import java.sql.Date;

public class StaffTableView {
    private Integer st_id;
    private String st_firstname, st_lastname;
    private String st_gender, st_staffid, st_phone_number;
    private String st_teaches;
    private Date st_added_date;

    /**=================================================================================================================
                                    implement a constructor
     ==================================================================================================================*/
    public StaffTableView(Integer st_id, String st_firstname, String st_lastname, String st_gender, String st_staffid, String st_phone_number, String st_teaches, Date st_added_date) {
        this.st_id = st_id;
        this.st_firstname = st_firstname;
        this.st_lastname = st_lastname;
        this.st_gender = st_gender;
        this.st_staffid = st_staffid;
        this.st_phone_number = st_phone_number;
        this.st_teaches = st_teaches;
        this.st_added_date = st_added_date;
    }

    /**=================================================================================================================
                                            setters and getter field
     ===================================================================================================================*/
    public Integer getSt_id() {
        return st_id;
    }

    public void setSt_id(Integer st_id) {
        this.st_id = st_id;
    }

    public String getSt_firstname() {
        return st_firstname;
    }

    public void setSt_firstname(String st_firstname) {
        this.st_firstname = st_firstname;
    }

    public String getSt_lastname() {
        return st_lastname;
    }

    public void setSt_lastname(String st_lastname) {
        this.st_lastname = st_lastname;
    }

    public String getSt_gender() {
        return st_gender;
    }

    public void setSt_gender(String st_gender) {
        this.st_gender = st_gender;
    }

    public String getSt_staffid() {
        return st_staffid;
    }

    public void setSt_staffid(String st_staffid) {
        this.st_staffid = st_staffid;
    }

    public String getSt_phone_number() {
        return st_phone_number;
    }

    public void setSt_phone_number(String st_phone_number) {
        this.st_phone_number = st_phone_number;
    }

    public String getSt_teaches() {
        return st_teaches;
    }

    public void setSt_teaches(String st_teaches) {
        this.st_teaches = st_teaches;
    }

    public Date getSt_added_date() {
        return st_added_date;
    }

    public void setSt_added_date(Date st_added_date) {
        this.st_added_date = st_added_date;
    }
}//end of class;

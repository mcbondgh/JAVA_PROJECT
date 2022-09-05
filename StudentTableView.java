package cass.myapp;

import java.sql.Date;

public class StudentTableView {

    private Integer stu_id;
    private String stu_firstName, stu_lastName, stu_studentId, stu_gender, stu_email;
    private  Integer stu_level;
    private String stu_class;
    private Date stu_date;

    /**=================================================================================================================
                                                    constructor field
    ===================================================================================================================*/
    public StudentTableView(Integer stu_id, String stu_firstName, String stu_lastName, String stu_studentId, String stu_gender, String stu_email, Integer stu_level, String stu_class, Date stu_date) {
        this.stu_id = stu_id;
        this.stu_firstName = stu_firstName;
        this.stu_lastName = stu_lastName;
        this.stu_studentId = stu_studentId;
        this.stu_gender = stu_gender;
        this.stu_email = stu_email;
        this.stu_level = stu_level;
        this.stu_class = stu_class;
        this.stu_date = stu_date;
    }

     /**=================================================================================================================
                                                   setters and getters field
    ===================================================================================================================*/
    public Integer getStu_id() {
        return stu_id;
    }

    public void setStu_id(Integer stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_firstName() {
        return stu_firstName;
    }

    public void setStu_firstName(String stu_firstName) {
        this.stu_firstName = stu_firstName;
    }

    public String getStu_lastName() {
        return stu_lastName;
    }

    public void setStu_lastName(String stu_lastName) {
        this.stu_lastName = stu_lastName;
    }

    public String getStu_studentId() {
        return stu_studentId;
    }

    public void setStu_studentId(String stu_studentId) {
        this.stu_studentId = stu_studentId;
    }

    public String getStu_gender() {
        return stu_gender;
    }

    public void setStu_gender(String stu_gender) {
        this.stu_gender = stu_gender;
    }

    public String getStu_email() {
        return stu_email;
    }

    public void setStu_email(String stu_email) {
        this.stu_email = stu_email;
    }

    public Integer getStu_level() {
        return stu_level;
    }

    public void setStu_level(Integer stu_level) {
        this.stu_level = stu_level;
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }

    public Date getStu_date() {
        return stu_date;
    }

    public void setStu_date(Date stu_date) {
        this.stu_date = stu_date;
    }

















































}//end of class

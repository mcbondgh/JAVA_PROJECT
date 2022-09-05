package cass.myapp;

import java.sql.Date;
import java.sql.Timestamp;

public class UserLogTableView {

    Integer tb_user_id;
    String tb_userName;
    String tb_userRole;
    Timestamp tb_signin;
    Timestamp tb_signout;

    public UserLogTableView(Integer tb_user_id, String tb_userName, String tb_userRole, Timestamp tb_signin, Timestamp tb_signout) {
        this.tb_user_id = tb_user_id;
        this.tb_userName = tb_userName;
        this.tb_userRole = tb_userRole;
        this.tb_signin = tb_signin;
        this.tb_signout = tb_signout;
    }

    public Integer getTb_user_id() {
        return tb_user_id;
    }

    public void setTb_user_id(Integer tb_user_id) {
        this.tb_user_id = tb_user_id;
    }

    public String getTb_userName() {
        return tb_userName;
    }

    public void setTb_userName(String tb_userName) {
        this.tb_userName = tb_userName;
    }

    public String getTb_userRole() {
        return tb_userRole;
    }

    public void setTb_userRole(String tb_userRole) {
        this.tb_userRole = tb_userRole;
    }

    public Timestamp getTb_signin() {
        return tb_signin;
    }

    public void setTb_signin(Timestamp tb_signin) {
        this.tb_signin = tb_signin;
    }

    public Timestamp getTb_signout() {
        return tb_signout;
    }

    public void setTb_signout(Timestamp tb_signout) {
        this.tb_signout = tb_signout;
    }
}//end of class

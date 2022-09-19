package cass.myapp.controllers;

import cass.myapp.UserLogTableView;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UserLogs implements Initializable {
    @FXML
    private TableView<UserLogTableView> userLogsTable;
    @FXML
    private TableColumn<UserLogTableView, Integer> userId;
    @FXML
    private TableColumn<UserLogTableView, String> userName;
    @FXML
    private TableColumn<UserLogTableView, String> userRole;
    @FXML
    private TableColumn<UserLogTableView, Timestamp> signin;
    @FXML
    private TableColumn<UserLogTableView, Timestamp> signout;

    ObservableList<UserLogTableView> userLogs = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database DB_OBJECT = new Database();
        Statement stmt = null;
        ResultSet result = null;

        try {
            String tableJoin = "SELECT si.user_id, si.userName, si.user_role, si.signedin_at, so.signedout_at " +
                    "FROM signin si JOIN signout so ON so.user_id= si.user_id ORDER BY so.signedout_at DESC LIMIT 10;";
            stmt = DB_OBJECT.CONNECT().createStatement();
            result = stmt.executeQuery(tableJoin);

            while(result.next()) {
                Integer th_user_id = result.getInt("si.user_id");
                String th_userName = result.getString("si.userName");
                String th_user_role = result.getString("si.user_role");
                Timestamp th_signIn = result.getTimestamp("si.signedin_at");
                Timestamp th_signOut = result.getTimestamp("so.signedout_at");
                userLogs.add(new UserLogTableView(th_user_id, th_userName, th_user_role, th_signIn, th_signOut));
            }
            setUserLogsTable();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setUserLogsTable() {
        userId.setCellValueFactory(new PropertyValueFactory<>("tb_user_id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("tb_userName"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("tb_userRole"));
        signin.setCellValueFactory(new PropertyValueFactory<>("tb_signin"));
        signout.setCellValueFactory(new PropertyValueFactory<>("tb_signout"));
       userLogsTable.setItems(userLogs);
    }































}//end of class....

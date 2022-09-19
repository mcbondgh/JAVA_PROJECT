package cass.myapp.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;



public class Database {

    public Database(){}
    private static final String URL = "jdbc:mysql://127.0.0.1:3308/cass";
    private static final String USERNAME = "Druglord";
    private static final String PASSWORD = "1244656800";

    public Connection CONNECT() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }




}//END OF CLASS....



package main.java.com.ipsos.andreas.asignari.database;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import main.java.com.ipsos.andreas.asignari.ui.AlertMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Andreas on 6/15/16.
 */

public class ConnectDB {
    private static Connection myConnection;
    private String url="jdbc:mysql://localhost:3306/SCHEMA?useSSL=false";
    private String user="user";
    private String pass="password";

    public ConnectDB() {
        try {
            ConnectDB.myConnection= DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            new AlertMessage("Error","The server could not be contacted. Please verify your connection.", Alert.AlertType.ERROR);
            Platform.exit();
            System.exit(-1);
        }
    }

    public static Connection getConnection() {
        return myConnection;
    }
}
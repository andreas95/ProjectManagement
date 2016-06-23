package main.java.com.ipsos.andreas.asignari.database;

import main.java.com.ipsos.andreas.asignari.common.Shared;
import main.java.com.ipsos.andreas.asignari.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 6/15/16.
 */
public class EmployeesDB {

    public EmployeesDB() {}

    public static boolean addEmployee(Employee employee) {
        String SQL="INSERT INTO Employees (firstName,lastName,position,username,password) VALUES(?,?,?,?,MD5(?));";
        PreparedStatement myStatement= null;

        try {
            myStatement = ConnectDB.getConnection().prepareStatement(SQL);
            myStatement.setString(1, employee.getFirstName());
            myStatement.setString(2, employee.getLastName());
            myStatement.setString(3, employee.getPosition());
            myStatement.setString(4, employee.getUsername());
            myStatement.setString(5, employee.getPassword());
            if (myStatement.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean deleteEmployee(String username) throws SQLException {
        String SQL="DELETE FROM Employees WHERE username=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, username);
        if (myStatement.executeUpdate() != 0) {
            return true;
        }
        return false;
    }

    public static Employee getEmployee(String username) throws SQLException {
        String SQL="SELECT * FROM Employees WHERE username=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, username);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return new Employee(myResultSet.getString("firstName"),myResultSet.getString("lastName"),myResultSet.getString("position"),
                    myResultSet.getString("username"),myResultSet.getString("password"));
        }
        return new Employee();
    }

    public static List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list=new ArrayList<Employee>();
        String SQL="SELECT * FROM Employees WHERE position!='Manager';";
        Statement myStatement=ConnectDB.getConnection().createStatement();
        ResultSet myResultSet=myStatement.executeQuery(SQL);
        while (myResultSet.next()) {
            list.add(new Employee(myResultSet.getString("firstName"),myResultSet.getString("lastName"),myResultSet.getString("position"),
                    myResultSet.getString("username"),myResultSet.getString("password")));
        }
        return list;
    }

    public static boolean loginEmployee(String username, String password) throws SQLException {
        String SQL="SELECT * FROM Employees WHERE username=? and password=MD5(?);";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, username);
        myStatement.setString(2, password);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return true;
        }
        return false;
    }

    public static boolean changePassword(String oldPasswrod, String newPassword) throws SQLException {
        String SQL="UPDATE Employees SET password=MD5(?) WHERE username=? and password=MD5(?);";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, newPassword);
        myStatement.setString(2, Shared.username);
        myStatement.setString(3, oldPasswrod);
        if (myStatement.executeUpdate() != 0) {
            return true;
        }
        return false;
    }

    public static int getEmployeeID() throws SQLException {
        String SQL="SELECT * FROM Employees WHERE username=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, Shared.username);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return myResultSet.getInt("Employee_ID");
        }
        return -1;
    }

    public static int getEmployeeID(String username) throws SQLException {
        String SQL="SELECT * FROM Employees WHERE username=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, username);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return myResultSet.getInt("Employee_ID");
        }
        return -1;
    }

    public static String getEmployeePosition() throws SQLException {
        String SQL="SELECT * FROM Employees WHERE username=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, Shared.username);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return myResultSet.getString("position");
        }
        return "";
    }
}

package main.java.com.ipsos.andreas.asignari.database;

import main.java.com.ipsos.andreas.asignari.common.Shared;
import main.java.com.ipsos.andreas.asignari.model.Employee;
import main.java.com.ipsos.andreas.asignari.model.Project;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 6/15/16.
 */
public class ProjectsDB {

    public ProjectsDB() {}

    public static boolean addProject(Project project) throws SQLException {
        String SQL="INSERT INTO Projects (status,assignDate,DP_RO,DP_NA,jobNumber,jobTitle,jobType,client,CS,fieldStart," +
                "fieldEnd,checkIpsos,checkCS,finalCheckIpsos,finalCheckCS,analyticsToCS,otherDeliverables,vendorBudget," +
                "overseeBudget,reserveBudget,iquoteBudget,Employee_ID)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, project.getStatus());
        myStatement.setString(2, project.getAssignDate());
        myStatement.setString(3, project.getDP_RO());
        myStatement.setString(4, project.getDP_NA());
        myStatement.setString(5, project.getJobNumber());
        myStatement.setString(6, project.getJobTitle());
        myStatement.setString(7, project.getJobType());
        myStatement.setString(8, project.getClient());
        myStatement.setString(9, project.getCS());
        myStatement.setString(10, project.getFieldStart());
        myStatement.setString(11, project.getFieldEnd());
        myStatement.setString(12, project.getCheckIpsos());
        myStatement.setString(13, project.getCheckCS());
        myStatement.setString(14, project.getFinalCheckIpsos());
        myStatement.setString(15, project.getFinalCheckCS());
        myStatement.setString(16, project.getAnalyticsToCS());
        myStatement.setString(17, project.getOtherDeliverables());
        myStatement.setDouble(18, project.getVendorBudget());
        myStatement.setDouble(19, project.getOverseeBudget());
        myStatement.setDouble(20, project.getReserveBudget());
        myStatement.setDouble(21, project.getIquoteBudget());
        myStatement.setInt(22, EmployeesDB.getEmployeeID());
        if (myStatement.executeUpdate() != 0) {
            return true;
        }
        return false;
    }

    public static boolean editProject(Project project,String old_jobNumber) throws SQLException {
        String SQL="UPDATE Projects SET status=?, assignDate=?, DP_RO=?, DP_NA=?, jobNumber=?, jobTitle=?, jobType=?, client=?, CS=?, fieldStart=?, " +
                "fieldEnd=?, checkIpsos=?, checkCS=?, finalCheckIpsos=?, finalCheckCS=?, analyticsToCS=?, otherDeliverables=?, vendorBudget=?, " +
                "overseeBudget=?, reserveBudget=?, iquoteBudget=?" +
                " WHERE jobNumber=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, project.getStatus());
        myStatement.setString(2, project.getAssignDate());
        myStatement.setString(3, project.getDP_RO());
        myStatement.setString(4, project.getDP_NA());
        myStatement.setString(5, project.getJobNumber());
        myStatement.setString(6, project.getJobTitle());
        myStatement.setString(7, project.getJobType());
        myStatement.setString(8, project.getClient());
        myStatement.setString(9, project.getCS());
        myStatement.setString(10, project.getFieldStart());
        myStatement.setString(11, project.getFieldEnd());
        myStatement.setString(12, project.getCheckIpsos());
        myStatement.setString(13, project.getCheckCS());
        myStatement.setString(14, project.getFinalCheckIpsos());
        myStatement.setString(15, project.getFinalCheckCS());
        myStatement.setString(16, project.getAnalyticsToCS());
        myStatement.setString(17, project.getOtherDeliverables());
        myStatement.setDouble(18, project.getVendorBudget());
        myStatement.setDouble(19, project.getOverseeBudget());
        myStatement.setDouble(20, project.getReserveBudget());
        myStatement.setDouble(21, project.getIquoteBudget());
        myStatement.setString(22, old_jobNumber);
        myStatement.executeUpdate();
        myStatement.close();
        return false;
    }

    public static boolean deleteProject(String jobNumber) throws SQLException {
        String SQL="DELETE FROM Projects WHERE jobNumber=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setString(1, jobNumber);
        if (myStatement.executeUpdate() != 0 ){
            myStatement.close();
            return true;
        }
        myStatement.close();
        return false;
    }

    public static List<Project> getAllProjects() throws SQLException {
        List<Project> list=new ArrayList<Project>();
        String SQL="SELECT * FROM Projects;";
        Statement myStatement=ConnectDB.getConnection().createStatement();
        ResultSet myResultSet=myStatement.executeQuery(SQL);
        while (myResultSet.next()) {
            list.add(new Project(myResultSet.getString("status"),myResultSet.getString("assignDate"),myResultSet.getString("DP_RO"),
                    myResultSet.getString("DP_NA"),myResultSet.getString("jobNumber"),myResultSet.getString("jobTitle"),
                    myResultSet.getString("jobType"),myResultSet.getString("client"),myResultSet.getString("CS"),
                    myResultSet.getString("fieldStart"),myResultSet.getString("fieldEnd"),myResultSet.getString("checkIpsos"),
                    myResultSet.getString("checkCS"),myResultSet.getString("finalCheckIpsos"),myResultSet.getString("finalCheckCS"),
                    myResultSet.getString("analyticsToCS"),myResultSet.getString("otherDeliverables"),myResultSet.getDouble("vendorBudget"),
                    myResultSet.getDouble("overseeBudget"),myResultSet.getDouble("reserveBudget")));
        }
        return list;
    }

    public static List<Project> getMyProjects() throws SQLException {
        List<Project> list=new ArrayList<Project>();
        String SQL="SELECT * FROM Projects WHERE Employee_ID=?;";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setInt(1, EmployeesDB.getEmployeeID());
        ResultSet myResultSet=myStatement.executeQuery();
        while (myResultSet.next()) {
            list.add(new Project(myResultSet.getString("status"),myResultSet.getString("assignDate"),myResultSet.getString("DP_RO"),
                    myResultSet.getString("DP_NA"),myResultSet.getString("jobNumber"),myResultSet.getString("jobTitle"),
                    myResultSet.getString("jobType"),myResultSet.getString("client"),myResultSet.getString("CS"),
                    myResultSet.getString("fieldStart"),myResultSet.getString("fieldEnd"),myResultSet.getString("checkIpsos"),
                    myResultSet.getString("checkCS"),myResultSet.getString("finalCheckIpsos"),myResultSet.getString("finalCheckCS"),
                    myResultSet.getString("analyticsToCS"),myResultSet.getString("otherDeliverables"),myResultSet.getDouble("vendorBudget"),
                    myResultSet.getDouble("overseeBudget"),myResultSet.getDouble("reserveBudget")));
        }
        return list;
    }

    public static int getNumberOfProjects(int Employee_ID,String date) throws SQLException {
        String SQL="SELECT COUNT(*) FROM Projects WHERE Employee_ID=? and (checkIpsos=? or checkCS=? or finalCheckIpsos=? or finalCheckCS=?);";
        PreparedStatement myStatement=ConnectDB.getConnection().prepareStatement(SQL);
        myStatement.setInt(1, Employee_ID);
        myStatement.setString(2, date);
        myStatement.setString(3, date);
        myStatement.setString(4, date);
        myStatement.setString(5, date);
        ResultSet myResultSet=myStatement.executeQuery();
        if (myResultSet.next()) {
            return myResultSet.getInt(1);
        }
        return 0;
    }
}

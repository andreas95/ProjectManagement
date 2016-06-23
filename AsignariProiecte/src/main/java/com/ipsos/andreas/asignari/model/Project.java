package main.java.com.ipsos.andreas.asignari.model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;
import main.java.com.ipsos.andreas.asignari.common.Shared;
import main.java.com.ipsos.andreas.asignari.database.ProjectsDB;
import main.java.com.ipsos.andreas.asignari.ui.AlertMessage;
import java.io.IOException;
import java.sql.SQLException;
import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class Project {

    private String status;
    private String assignDate;
    private String DP_RO;
    private String DP_NA;
    private String jobNumber;
    private String jobTitle;
    private String jobType;
    private String client;
    private String CS;
    private String fieldStart;
    private String fieldEnd;
    private String checkIpsos;
    private String checkCS;
    private String finalCheckIpsos;
    private String finalCheckCS;
    private String analyticsToCS;
    private String otherDeliverables;
    private double vendorBudget;
    private double overseeBudget;
    private double reserveBudget;
    private double iquoteBudget;

    public Project(String status, String assignDate, String DP_RO, String DP_NA, String jobNumber, String jobTitle,
                   String jobType, String client, String CS, String fieldStart, String fieldEnd, String checkIpsos, String checkCS,
                   String finalCheckIpsos, String finalCheckCS, String analyticsToCS, String otherDeliverables, double vendorBudget,
                   double overseeBudget, double reserveBudget) {
        this.status=status;
        this.assignDate=assignDate;
        this.DP_RO=DP_RO;
        this.DP_NA=DP_NA;
        this.jobNumber=jobNumber;
        this.jobTitle=jobTitle;
        this.jobType=jobType;
        this.client=client;
        this.CS=CS;
        this.fieldStart=fieldStart;
        this.fieldEnd=fieldEnd;
        this.checkIpsos=checkIpsos;
        this.checkCS=checkCS;
        this.finalCheckIpsos=finalCheckIpsos;
        this.finalCheckCS=finalCheckCS;
        this.analyticsToCS=analyticsToCS;
        this.otherDeliverables=otherDeliverables;
        this.vendorBudget=vendorBudget;
        this.overseeBudget=overseeBudget;
        this.reserveBudget=reserveBudget;
        this.iquoteBudget=vendorBudget+overseeBudget+reserveBudget;
    }

    public HBox getAction() {
        HBox action=new HBox(10);
        action.setAlignment(Pos.CENTER);

        Label delete=new Label("Delete");
        delete.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        delete.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        delete.setTextFill(Color.web("#0099FF"));
        delete.setFont(Font.font("Segoe UI Semilight", FontWeight.BOLD, 12));
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (AlertMessage.AlertMessageConfirmation()) {
                        ProjectsDB.deleteProject(getJobNumber());
                        ScreenController.setScreen(Shared.screen);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Label edit=new Label("Edit");
        edit.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        edit.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        edit.setTextFill(Color.web("#0099FF"));
        edit.setFont(Font.font("Segoe UI Semilight", FontWeight.BOLD, 12));
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Shared.project=Project.this;
                try {
                    ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        action.getChildren().addAll(delete,edit);
        return action;
    }
    public String getStatus() {return status;}
    public String getAssignDate() {return assignDate;}
    public String getDP_RO() {return DP_RO;}
    public String getDP_NA() {return DP_NA;}
    public String getJobNumber() {return jobNumber;}
    public String getJobTitle() {return jobTitle;}
    public String getJobType() {return jobType;}
    public String getClient() {return client;}
    public String getCS() {return CS;}
    public String getFieldStart() {return fieldStart;}
    public String getFieldEnd() {return fieldEnd;}
    public String getCheckIpsos() {return checkIpsos;}
    public String getCheckCS() {return checkCS;}
    public String getFinalCheckIpsos() {return finalCheckIpsos;}
    public String getFinalCheckCS() {return finalCheckCS;}
    public String getAnalyticsToCS() {return analyticsToCS;}
    public String getOtherDeliverables() {return otherDeliverables;}
    public double getVendorBudget() {return vendorBudget;}
    public double getOverseeBudget() {return overseeBudget;}
    public double getReserveBudget() {return reserveBudget;}
    public double getIquoteBudget() {return iquoteBudget;}

}

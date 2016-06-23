package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.java.com.ipsos.andreas.asignari.animation.FadeInLeftTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInUpTransition;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;
import main.java.com.ipsos.andreas.asignari.database.EmployeesDB;
import main.java.com.ipsos.andreas.asignari.model.Employee;
import main.java.com.ipsos.andreas.asignari.ui.AlertMessage;
import main.java.com.ipsos.andreas.asignari.utils.Loading;
import main.java.com.ipsos.andreas.asignari.utils.SmallScreen;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class AdministrationFragment {

    @FXML private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private Button ButtonAddEmployee;
    @FXML private Button ButtonDeleteEmployee;
    @FXML private Button ButtonChangePassword;
    @FXML private GridPane PaneAddEmployee;
    @FXML private GridPane PaneDeleteEmployee;
    @FXML private GridPane PaneChangePassword;
    @FXML private AnchorPane PaneMain;
    @FXML private HBox PaneTop;
    @FXML private PasswordField FieldOldPassword;
    @FXML private PasswordField FieldNewPassword;
    @FXML private PasswordField FieldConfirmPassword;
    @FXML private Button ButtonSave;
    @FXML private TextField FieldFirstName;
    @FXML private TextField FieldLastName;
    @FXML private ComboBox ComboPosition;
    @FXML private PasswordField FieldPassword;
    @FXML private PasswordField FieldPasswordAgain;
    @FXML private TextField FieldUsername;
    @FXML private TextField FieldUserDelete;
    private Task task;


    public void initialize() {
        ComboPosition.getItems().setAll("Data Analyst", "Senior", "Manager");

        PaneMain.getChildren().removeAll(PaneDeleteEmployee,PaneAddEmployee,PaneChangePassword);

        ButtonAddEmployee.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonAddEmployee.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonDeleteEmployee.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonDeleteEmployee.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonChangePassword.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonChangePassword.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonSave.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonSave.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ImageLoading.setVisible(true);
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        task= Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ImageLoading.setVisible(false);
                try {
                    if (EmployeesDB.getEmployeePosition().equals("Manager")) {
                        PaneTop.setVisible(true);
                        new FadeInLeftTransition(PaneTop).play();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                PaneMain.getChildren().add(PaneChangePassword);
                new FadeInUpTransition(PaneChangePassword).play();
            }
        });
    }

    @FXML
    public void changePassword() {
        new FadeInUpTransition(PaneChangePassword).play();
        if (PaneMain.getChildren().contains(PaneAddEmployee)) {
            PaneMain.getChildren().remove(PaneAddEmployee);
        } else if (PaneMain.getChildren().contains(PaneDeleteEmployee)) {
            PaneMain.getChildren().remove(PaneDeleteEmployee);
        }
        if (!PaneMain.getChildren().contains(PaneChangePassword)) {
            PaneMain.getChildren().add(PaneChangePassword);
        }
    }

    @FXML
    public void addEmployee() {
        new FadeInUpTransition(PaneAddEmployee).play();
        if (PaneMain.getChildren().contains(PaneChangePassword)) {
            PaneMain.getChildren().remove(PaneChangePassword);
        } else if (PaneMain.getChildren().contains(PaneDeleteEmployee)) {
            PaneMain.getChildren().remove(PaneDeleteEmployee);
        }
        if (!PaneMain.getChildren().contains(PaneAddEmployee)) {
            PaneMain.getChildren().add(PaneAddEmployee);
        }
    }

    @FXML
    public void deleteEmployee() {
        new FadeInUpTransition(PaneDeleteEmployee).play();
        if (PaneMain.getChildren().contains(PaneAddEmployee)) {
            PaneMain.getChildren().remove(PaneAddEmployee);
        } else if (PaneMain.getChildren().contains(PaneChangePassword)) {
            PaneMain.getChildren().remove(PaneChangePassword);
        }
        if (!PaneMain.getChildren().contains(PaneDeleteEmployee)) {
            PaneMain.getChildren().add(PaneDeleteEmployee);
        }
    }

    @FXML
    public void setPassword() throws IOException, SQLException {
        if (!FieldConfirmPassword.getText().equals(FieldNewPassword.getText())) {
            new AlertMessage("Error","The passwords you entered did not match.", Alert.AlertType.ERROR);
        } else if (EmployeesDB.changePassword(FieldOldPassword.getText(),FieldConfirmPassword.getText())) {
            new AlertMessage("Success","Your password is changed!", Alert.AlertType.INFORMATION);
            SmallScreen.setSmallScreen();
            ScreenController.setScreen(ScreenController.Screen.SPLASH);
        } else {
            new AlertMessage("Error","The old password you did entered is incorect.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void setDelete() throws SQLException {
        if (FieldUserDelete.getText().equals("")) {
            new AlertMessage("Error","Don't leave field blank.", Alert.AlertType.ERROR);
        } else if (EmployeesDB.deleteEmployee(FieldUserDelete.getText())) {
            new AlertMessage("Success","Employee are deleted from database!", Alert.AlertType.INFORMATION);
        } else {
            new AlertMessage("Error","The username doesn't  exist in database.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void setAdd() throws SQLException {
        if (FieldUsername.getText().equals("") || FieldFirstName.getText().equals("") || FieldLastName.getText().equals("")
                || FieldPassword.getText().equals("") || FieldPasswordAgain.getText().equals("")) {
            new AlertMessage("Error","Don't leave any fields blank.", Alert.AlertType.ERROR);
        } else if (!FieldPasswordAgain.getText().equals(FieldPassword.getText())) {
            new AlertMessage("Error","The passwords you entered did not match.", Alert.AlertType.ERROR);
        } else if (EmployeesDB.addEmployee(new Employee(FieldFirstName.getText(), FieldLastName.getText(),
                ComboPosition.getValue().toString(), FieldUsername.getText(), FieldPassword.getText()))) {
            new AlertMessage("Success","Employee added in database!", Alert.AlertType.INFORMATION);
        } else {
            new AlertMessage("Error","The username already exist in database.", Alert.AlertType.ERROR);
        }
    }
}

package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.java.com.ipsos.andreas.asignari.animation.FadeInLeftTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInRightTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInTransition;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;
import main.java.com.ipsos.andreas.asignari.common.Shared;
import main.java.com.ipsos.andreas.asignari.database.EmployeesDB;
import main.java.com.ipsos.andreas.asignari.ui.AlertMessage;
import main.java.com.ipsos.andreas.asignari.utils.SmallScreen;


import java.io.IOException;
import java.sql.SQLException;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class LoginFragment {

    @FXML private Text TextWelcome;
    @FXML private Text TextCompany;
    @FXML private Text TextUsername;
    @FXML private Text TextPassword;
    @FXML private TextField FieldUsername;
    @FXML private PasswordField FieldPassword;
    @FXML private Text TextCopyright;
    @FXML private Button ButtonLogin;
    @FXML private Label LabelClose;

    public void initialize() {
        FieldUsername.setText(System.getProperty("user.name"));
        FieldUsername.setFocusTraversable(false);
        FieldUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    FieldPassword.requestFocus();
                }
            }
        });

        FieldPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    try {
                        validateLogin();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        LabelClose.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        LabelClose.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        LabelClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
                System.exit(-1);
            }
        });

        ButtonLogin.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonLogin.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        start();
    }
    private void start() {
        Platform.runLater(() -> {
            new FadeInLeftTransition(TextWelcome).play();
            new FadeInRightTransition(TextCompany).play();
            new FadeInTransition(TextCopyright).play();
            new FadeInRightTransition(TextUsername).play();
            new FadeInLeftTransition(TextPassword).play();
            new FadeInLeftTransition(FieldPassword).play();
            new FadeInLeftTransition(FieldUsername).play();
            new FadeInRightTransition(ButtonLogin).play();
        });
    }
    @FXML
    private void validateLogin() throws IOException, SQLException {
        if (FieldUsername.getText().equals(System.getProperty("user.name")) &&
                EmployeesDB.loginEmployee(FieldUsername.getText(),FieldPassword.getText())) {
                Shared.username=FieldUsername.getText();
                ScreenController.setScreen(ScreenController.Screen.MENU);
        } else {
            new AlertMessage("Error","Username/password is incorect!", Alert.AlertType.ERROR);
        }
    }
}

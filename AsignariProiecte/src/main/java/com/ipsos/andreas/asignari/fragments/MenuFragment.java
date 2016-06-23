package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;
import main.java.com.ipsos.andreas.asignari.common.StageManager;
import main.java.com.ipsos.andreas.asignari.utils.LargeScreen;
import main.java.com.ipsos.andreas.asignari.utils.SmallScreen;

import java.io.IOException;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class MenuFragment {

    @FXML private Button ButtonMinimize;
    @FXML private Button ButtonClose;
    @FXML private Button ButtonLogout;
    @FXML private Button ButtonResize;
    @FXML private Button ButtonMaximize;
    @FXML private ListView<String> ListMenu;
    @FXML private AnchorPane PaneFragment;
    private Rectangle2D rectangle2D;
    private double width;
    private double height;

    public void initialize() {
        StageManager.setPane(PaneFragment);

        LargeScreen.setLargeScreen();

        ButtonMinimize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMinimize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonClose.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonClose.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonLogout.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonLogout.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonResize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonResize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMaximize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        rectangle2D = Screen.getPrimary().getVisualBounds();
        width=0.1;
        height=0.1;

        ListMenu.getItems().addAll("Home","My Projects","Team Projects","Administration","About");
        ListMenu.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ListMenu.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize.getStyleClass().add("decoration-button-restore");
        ButtonResize.setVisible(false);
        ListMenu.getSelectionModel().select(0);
        ListMenu.requestFocus();
        try {
            modeListMenu(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modeListMenu(MouseEvent event) throws IOException {
        switch(ListMenu.getSelectionModel().getSelectedIndex()){
            case 0:
                ScreenController.setScreen(ScreenController.Screen.HOME);
                break;
            case 1:
                ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
                break;
            case 2:
                ScreenController.setScreen(ScreenController.Screen.TEAM_PROJECTS);
                break;
            case 3:
                ScreenController.setScreen(ScreenController.Screen.ADMINISTRATION);
                break;
            case 4:
                ScreenController.setScreen(ScreenController.Screen.ABOUT);
                break;
            default:
                break;
        }
    }

    @FXML
    public void modeMinimize() {
        if (StageManager.getStage().isMaximized()) {
            width = rectangle2D.getWidth();
            height = rectangle2D.getHeight();
            StageManager.getStage().setMaximized(false);
            StageManager.getStage().setHeight(height);
            StageManager.getStage().setWidth(width);
            StageManager.getStage().centerOnScreen();
            Platform.runLater(() -> {
                StageManager.getStage().setIconified(true);
            });
        } else {
            StageManager.getStage().setIconified(true);
        }
    }

    @FXML
    public void modeResize() {

    }

    @FXML
    public void modeClose() {
        Platform.exit();
        System.exit(-1);
    }

    @FXML
    public void modeMaximize() {
        if (StageManager.getStage().isMaximized()) {
            if (width == rectangle2D.getWidth() && height == rectangle2D.getHeight()) {
                StageManager.getStage().setMaximized(false);
                StageManager.getStage().setHeight(600);
                StageManager.getStage().setWidth(800);
                StageManager.getStage().centerOnScreen();
                ButtonMaximize.getStyleClass().remove("decoration-button-restore");
                ButtonResize.setVisible(true);
            } else {
                StageManager.getStage().setMaximized(false);
                ButtonMaximize.getStyleClass().remove("decoration-button-restore");
                ButtonResize.setVisible(true);
            }
        }
    }

    @FXML
    public void modeLogout() throws IOException {
        SmallScreen.setSmallScreen();
        ScreenController.setScreen(ScreenController.Screen.LOGIN);
    }
}

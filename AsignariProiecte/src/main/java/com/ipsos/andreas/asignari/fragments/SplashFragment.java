package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import main.java.com.ipsos.andreas.asignari.animation.FadeInLeftTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInRightTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;

import java.io.IOException;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class SplashFragment {

    @FXML private Text TextWelcome;
    @FXML private Text TextCompany;
    @FXML private VBox PaneBottom;
    @FXML private Label LabelClose;

    public void initialize() {
        LabelClose.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        LabelClose.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        LabelClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
                System.exit(-1);
            }
        });
        start();
    }
    private void start() {
        Platform.runLater(()->{
            new FadeInLeftTransition(TextWelcome).play();
            new FadeInRightTransition(TextCompany).play();
            new FadeInTransition(PaneBottom).play();
        });
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    ScreenController.setScreen(ScreenController.Screen.LOGIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(sleeper).start();
    }
}

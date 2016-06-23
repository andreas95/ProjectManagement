package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.java.com.ipsos.andreas.asignari.utils.Loading;

/**
 * Created by Andreas on 6/14/16.
 */
public class AboutFragment {

    @FXML private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private Text test;
    private Task task;


    public void initialize() {
        test.setVisible(false);
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
                test.setVisible(true);
            }
        });

    }
}

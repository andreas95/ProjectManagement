package main.java.com.ipsos.andreas.asignari.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

import static javafx.scene.control.Alert.*;

/**
 * Created by Andreas on 6/14/16.
 */
public class AlertMessage {

    public AlertMessage(String title,String content,AlertType type) {
        Alert alert = new Alert(type,content);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static boolean AlertMessageConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Delete project");
        alert.setContentText("Are you sure to delete this project?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}

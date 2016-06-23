package main.java.com.ipsos.andreas.asignari.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Andreas on 6/14/16.
 */
public class StageManager {
    private static Stage stage;
    private static AnchorPane pane;

    public StageManager(Stage stage) throws IOException {
        StageManager.stage=stage;
        stage.setTitle("Asignari Proiecte IIS");
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root= FXMLLoader.load(ScreenController.class.getResource("../layout/splash_fragment.fxml"));
        Scene s=new Scene(root);
        stage.setScene(s);
        stage.show();
    }

    public static Stage getStage() {return stage;}
    public static void setRoot(Parent root) {StageManager.stage.getScene().setRoot(root);}
    public static void setPane(AnchorPane pane) {StageManager.pane=pane;}
    public static void setPaneFragment(Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }
}

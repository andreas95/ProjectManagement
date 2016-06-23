package main.java.com.ipsos.andreas.asignari;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.ipsos.andreas.asignari.common.StageManager;
import main.java.com.ipsos.andreas.asignari.database.ConnectDB;

/**
 * Created by Andreas on 6/14/16.
 */
public class Main extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception{
            new ConnectDB();
            new StageManager(primaryStage);
        }


        public static void main(String[] args) {
            launch(args);
        }
}

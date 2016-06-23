package main.java.com.ipsos.andreas.asignari.utils;

import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class LargeScreen {

    public static void setLargeScreen() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        getStage().setX(bounds.getMinX());
        getStage().setY(bounds.getMinY());
        getStage().setWidth(bounds.getWidth());
        getStage().setHeight(bounds.getHeight());
    }
}

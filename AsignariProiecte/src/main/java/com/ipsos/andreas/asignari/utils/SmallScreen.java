package main.java.com.ipsos.andreas.asignari.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class SmallScreen {

    public static void setSmallScreen() {
        getStage().setWidth(493);
        getStage().setHeight(327);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        getStage().setX((primScreenBounds.getWidth() - getStage().getWidth()) / 2);
        getStage().setY((primScreenBounds.getHeight() - getStage().getHeight()) / 2);
    }

}

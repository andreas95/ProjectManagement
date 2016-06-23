package main.java.com.ipsos.andreas.asignari.common;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Created by Andreas on 6/14/16.
 */
public class ScreenController {

    public static enum Screen {
        SPLASH,
        LOGIN,
        MENU,
        HOME,
        MY_PROJECTS,
        TEAM_PROJECTS,
        ADMINISTRATION,
        ABOUT
    }

    public ScreenController() {
    }

    public static void setScreen(Screen screen) throws IOException {
        switch (screen) {
            case SPLASH:
                StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource("../layout/splash_fragment.fxml")));
                Shared.screen=Screen.SPLASH;
                break;
            case LOGIN:
                StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource("../layout/login_fragment.fxml")));
                Shared.screen=Screen.LOGIN;
                break;
            case MENU:
                StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource("../layout/menu_fragment.fxml")));
                Shared.screen=Screen.MENU;
                break;
            case HOME:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../layout/home_fragment.fxml")));
                Shared.screen=Screen.HOME;
                break;
            case MY_PROJECTS:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../layout/my_projects_fragment.fxml")));
                Shared.screen=Screen.MY_PROJECTS;
                break;
            case TEAM_PROJECTS:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../layout/team_projects_fragment.fxml")));
                Shared.screen=Screen.TEAM_PROJECTS;
                break;
            case ADMINISTRATION:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../layout/administration_fragment.fxml")));
                Shared.screen=Screen.ADMINISTRATION;
                break;
            case ABOUT:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../layout/about_fragment.fxml")));
                Shared.screen=Screen.ABOUT;
                break;
            default:
                break;
        }
    }
}

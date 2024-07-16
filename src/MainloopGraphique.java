package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.menu.MenuAuthenticationGraphique;

public class MainloopGraphique extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuAuthenticationGraphique menuAuthenticationGraphique = new MenuAuthenticationGraphique();
        menuAuthenticationGraphique.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

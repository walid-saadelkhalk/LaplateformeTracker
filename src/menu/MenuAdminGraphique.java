package src.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminGraphique extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Menu");

        VBox layout = new VBox(20);
        // Ajouter les éléments de l'interface admin

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
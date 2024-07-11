import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import src.Modele.Database;

public class Main extends Application {

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        // Établir la connexion à la base de données
        connection = Database.getConnection();

        // Vérifier si la connexion est réussie
        if (connection != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to connect to the database.");
            return; // Quitter l'application si la connexion échoue
        }

        // Création de l'interface utilisateur JavaFX
        Label label = new Label("Hello, JavaFX!");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Student Database Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Fermer la connexion à la base de données lorsque l'application se ferme
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

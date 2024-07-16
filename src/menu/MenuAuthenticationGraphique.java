package src.menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.model.Connecting;
import java.net.URL;

import java.util.Scanner;

public class MenuAuthenticationGraphique extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Authentication Menu");
        showInitialMenu();
    }

    private void showInitialMenu() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.getStyleClass().add("vbox");

        Label title = new Label("Select Authentication Type");
        title.setId("title-label");
        StackPane titlePane = new StackPane();
        titlePane.getChildren().add(title);
        titlePane.setPadding(new Insets(0, 0, 20, 0));

        Button adminButton = new Button("Admin");
        adminButton.setOnAction(e -> showLoginScreen("Admin"));

        Button studentButton = new Button("Student");
        studentButton.setOnAction(e -> showLoginScreen("Student"));

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(titlePane, adminButton, studentButton, quitButton);

        Scene scene = new Scene(layout, 1000, 800);
        // Chargement du fichier CSS
        URL url = getClass().getResource("/styles.css");

        if (url == null) {
            System.out.println("Impossible de trouver le fichier CSS.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        System.out.println("Chargement du CSS depuis : " + css);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginScreen(String userType) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.getStyleClass().add("vbox");

        Label title = new Label("Login as " + userType);
        title.setId("title-label");
        StackPane titlePane = new StackPane();
        titlePane.getChildren().add(title);
        titlePane.setPadding(new Insets(0, 0, 20, 0));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextField userInput = new TextField();
        userInput.setPromptText("Nom d'utilisateur");
        GridPane.setConstraints(userInput, 0, 0);

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Mot de passe");
        GridPane.setConstraints(passInput, 0, 1);

        grid.getChildren().addAll(userInput, passInput);

        Button loginButton = new Button("Connexion");
        loginButton.setOnAction(e -> {
            boolean authenticated;
            if ("Admin".equals(userType)) {
                authenticated = Connecting.AdminConnection(new Scanner(System.in), userInput.getText(), passInput.getText());
            } else {
                authenticated = Connecting.StudentConnection(new Scanner(System.in), userInput.getText(), passInput.getText());
            }

            if (authenticated) {
                showAlert(AlertType.INFORMATION, "Connection successful!");
                if ("Admin".equals(userType)) {
                    MenuAdminGraphique menuAdminGraphique = new MenuAdminGraphique();
                    menuAdminGraphique.start(primaryStage);
                } else {
                    MenuStudentGraphique menuStudentGraphique = new MenuStudentGraphique();
                    menuStudentGraphique.start(primaryStage);
                }
            } else {
                showAlert(AlertType.ERROR, "Invalid mail or password.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showInitialMenu());

        layout.getChildren().addAll(titlePane, grid, loginButton, backButton);

        

        Scene scene = new Scene(layout, 1000, 800);

        // Chargement du fichier CSS
        URL url = getClass().getResource("/styles.css");

        if (url == null) {
            System.out.println("Impossible de trouver le fichier CSS.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        System.out.println("Chargement du CSS depuis : " + css);
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

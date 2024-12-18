package com.example.dashborda;

import com.example.dashborda.Utils.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ensure the path to "ListeClient.fxml" is correct
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/dashborda/signup.fxml"));
            primaryStage.setTitle("Page de Connexion");
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();  // Handle loading errors
            System.out.println("Erreur lors du chargement du FXML.");
        }
    }

    public static void main(String[] args) {
        // Check the database connection before launching the app
        MyConnection myConnection = MyConnection.getInstance();
        if (myConnection.getCnx() != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("La connexion a échoué.");
        }

        // Launch the JavaFX application
        launch(args);
    }
}

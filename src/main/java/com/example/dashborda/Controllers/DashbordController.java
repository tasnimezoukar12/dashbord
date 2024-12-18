package com.example.dashborda.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashbordController {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private Button button_entrepreneurs;

    @FXML
    private Button button_dashbord;
    @FXML
    private Button button_profile;
    @FXML
    private Button button_clients;
    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        if (xAxis != null && yAxis != null && lineChart != null) {
            // Initialisation du graphique de ligne
            xAxis.setLabel("Mois");
            yAxis.setLabel("Nombre");
            lineChart.setTitle("Statistiques Clients et Entrepreneurs");

            // Ajout des données pour les clients
            XYChart.Series<Number, Number> clientsSeries = new XYChart.Series<>();
            clientsSeries.setName("Clients");
            clientsSeries.getData().add(new XYChart.Data<>(1, 100)); // Exemple : 100 clients au mois 1
            clientsSeries.getData().add(new XYChart.Data<>(2, 150)); // Exemple : 150 clients au mois 2
            // Ajouter plus de données pour les clients ici

            // Ajout des données pour les entrepreneurs
            XYChart.Series<Number, Number> entrepreneursSeries = new XYChart.Series<>();
            entrepreneursSeries.setName("Entrepreneurs");
            entrepreneursSeries.getData().add(new XYChart.Data<>(1, 50)); // Exemple : 50 entrepreneurs au mois 1
            entrepreneursSeries.getData().add(new XYChart.Data<>(2, 80)); // Exemple : 80 entrepreneurs au mois 2
            // Ajouter plus de données pour les entrepreneurs ici

            // Ajout des séries au graphique
            lineChart.getData().addAll(clientsSeries, entrepreneursSeries);
        } else {
            System.err.println("Erreur : Les composants FXML ne sont pas correctement initialisés.");
        }
    }

    @FXML
    public void go_to_entrepreneurs(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/ListeEntrepreneurs.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) button_entrepreneurs.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void go_to_clients(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/ListeClient.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) button_clients.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void go_to_dashboard(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/dashbord.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) button_dashbord.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void go_to_profile(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/profile.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) button_profile.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}



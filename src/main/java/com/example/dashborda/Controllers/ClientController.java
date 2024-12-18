package com.example.dashborda.Controllers;

import com.example.dashborda.Models.client;
import com.example.dashborda.Utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private TableView<client> clientTable;
    @FXML
    private Button button_entrepreneurs;
    @FXML
    private Button button_dashbord;
    @FXML
    private Button button_profile;
    @FXML
    private Button button_clients;
    @FXML
    private TableColumn<client, Integer> colid;
    @FXML
    private TableColumn<client, String> colnom;
    @FXML
    private TableColumn<client, String> colprenom;
    @FXML
    private TableColumn<client, String> colemail;
    @FXML
    private TableColumn<client, String> colgenre;

    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfgenre;

    @FXML
    private Button addClient;
    @FXML
    private Button updateClient;
    @FXML
    private Button deleteClient;

    private Connection connection;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        connection = MyConnection.getInstance().getCnx();

        loadClients();
        clientTable.setOnMouseClicked(event -> {
            client selectedClient = clientTable.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                tfid.setText(selectedClient.getid());
                tfnom.setText(selectedClient.getnom());
                tfprenom.setText(selectedClient.getprenom());
                tfemail.setText(selectedClient.getemail());
                tfgenre.setText(selectedClient.getgenre());
            }
        });

        // Using lambdas for button event handlers fi3odh mano93dou nktbou kol fonction mtaa redirection wahadhha lakhassnehom f fonction wahda
        addClient.setOnAction(event -> add());
        updateClient.setOnAction(event -> update());
        deleteClient.setOnAction(event -> delete());
    }

    private void loadClients() {
        // ki tzid fel tableau affiche  l'interface
        // awel haja tsir arraylist  telm les données

        // observablelist affiche fel l'interface
        ObservableList<client> clients = FXCollections.observableArrayList();
        String query = "SELECT * FROM client";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                clients.add(new client(
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("genre")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des clients : " + e.getMessage());
        }
        clientTable.setItems(clients);
    }

    private void add() {
        String id = tfid.getText();
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        String email = tfemail.getText();
        String genre = tfgenre.getText();

// insert tetsma doa  fiha les requete sql fiha doa kol taif hatta execute
        // controller fiha tnadi doa  fes controlle saisi ( champs far8a walla katba bel 8alit )  trequpere kol mahou majoud fi text file
        String query = "INSERT INTO client (id, nom, prenom, email, genre, imageData) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setString(4, email);
            pstmt.setString(5, genre);
            pstmt.executeUpdate();
            loadClients();
            clearFields();
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Client ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du client : " + e.getMessage());
        }
    }

    private void update() {
        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            String query = "UPDATE client SET nom = ?, prenom = ?, email = ?, genre = ?,  imageData = ?WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, tfnom.getText());
                pstmt.setString(2, tfprenom.getText());
                pstmt.setString(3, tfemail.getText());
                pstmt.setString(4, tfgenre.getText());
                pstmt.setString(6, selectedClient.getid());

                pstmt.executeUpdate();
                loadClients();
                clearFields();
                showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Client mis à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour du client : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Client sélectionné", "Veuillez sélectionner un client à mettre à jour.");
        }
    }

    private void delete() {
        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            String query = "DELETE FROM client WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, selectedClient.getid());
                pstmt.executeUpdate();
                loadClients();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Client supprimé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du client : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Client sélectionné", "Veuillez sélectionner un client à supprimer.");
        }
    }

    private void clearFields() {
        tfid.clear();
        tfnom.clear();
        tfprenom.clear();
        tfemail.clear();
        tfgenre.clear();

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void go_to_entrepreneurs(ActionEvent actionEvent) {
        loadScene("/com/example/dashborda/ListeEntrepreneurs.fxml", button_entrepreneurs);
    }

    @FXML
    public void go_to_clients(ActionEvent actionEvent) {
        loadScene("/com/example/dashborda/ListeClient.fxml", button_clients);
    }

    @FXML
    public void go_to_dashboard(ActionEvent actionEvent) {
        loadScene("/com/example/dashborda/dashbord.fxml", button_dashbord);
    }

    @FXML
    public void go_to_profile(ActionEvent actionEvent) {
        loadScene("/com/example/dashborda/profile.fxml", button_profile);
    }

    private void loadScene(String fxmlFile, Button button) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

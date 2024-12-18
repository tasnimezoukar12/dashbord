package com.example.dashborda.Controllers;

import com.example.dashborda.Models.profile;
import com.example.dashborda.Utils.MyConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileController {
    @FXML
    private Button button_entrepreneurs;

    @FXML
    private Button button_dashbord;
    @FXML
    private Button button_profile;
    @FXML
    private Button button_clients;
    @FXML
    private TextField nomProfile;
    @FXML
    private TextField prenomProfile;
    @FXML
    private TextField emailProfile;
    @FXML
    private TextField numTelProfile;
    @FXML
    private TextField nomProjetProfile;
    @FXML
    private TextField categorieProfile;
    @FXML
    private TextField gouvernoratProfile;
    @FXML
    private TextField villeProfile;
    @FXML
    private TextField adresseExacteProfile;
    @FXML
    private Button updateProfileBtn;

    private profile currentProfile;

    public void initData(profile profile) {
        this.currentProfile = profile;
        fillFields();
    }
    

    private void fillFields() {
        if (currentProfile != null) {
            nomProfile.setText(currentProfile.getNom());
            prenomProfile.setText(currentProfile.getPrenom());
            emailProfile.setText(currentProfile.getEmail());
            numTelProfile.setText(currentProfile.getNumTel());
            nomProjetProfile.setText(currentProfile.getNomProjet());
            categorieProfile.setText(currentProfile.getCategorie());
            gouvernoratProfile.setText(currentProfile.getGouvernorat());
            villeProfile.setText(currentProfile.getVille());
            adresseExacteProfile.setText(currentProfile.getAdresseExacte());
        }
    }


    @FXML
    private void handleUpdateProfile(ActionEvent event) {
        if (validateFields()) {
            profile updatedProfile = createProfileFromFields();
            if (updateProfileInDatabase(updatedProfile)) {
                currentProfile = updatedProfile;
                showAlert(AlertType.INFORMATION, "Succès", "Profil mis à jour avec succès !");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Échec de la mise à jour du profil.");
            }
        } else {
            showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs.");
        }
    }


    private boolean validateFields() {
        return !nomProfile.getText().isEmpty() &&
                !prenomProfile.getText().isEmpty() &&
                !emailProfile.getText().isEmpty() &&
                !numTelProfile.getText().isEmpty() &&
                !nomProjetProfile.getText().isEmpty() &&
                !categorieProfile.getText().isEmpty() &&
                !gouvernoratProfile.getText().isEmpty() &&
                !villeProfile.getText().isEmpty() &&
                !adresseExacteProfile.getText().isEmpty();
    }

    private profile createProfileFromFields() {
        return new profile(
                nomProfile.getText(),
                prenomProfile.getText(),
                emailProfile.getText(),
                numTelProfile.getText(),
                nomProjetProfile.getText(),
                categorieProfile.getText(),
                gouvernoratProfile.getText(),
                villeProfile.getText(),
                adresseExacteProfile.getText(),
                currentProfile != null ? currentProfile.getPassword() : null
        );
    }

    private boolean updateProfileInDatabase(profile updatedProfile) {
        String sql = "UPDATE user SET nom=?, prenom=?, email=?, numTel=?, nomProjet=?, categorie=?, gouvernorat=?, ville=?, adresseExacte=? WHERE email=?";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updatedProfile.getNom());
            preparedStatement.setString(2, updatedProfile.getPrenom());
            preparedStatement.setString(3, updatedProfile.getEmail());
            preparedStatement.setString(4, updatedProfile.getNumTel());
            preparedStatement.setString(5, updatedProfile.getNomProjet());
            preparedStatement.setString(6, updatedProfile.getCategorie());
            preparedStatement.setString(7, updatedProfile.getGouvernorat());
            preparedStatement.setString(8, updatedProfile.getVille());
            preparedStatement.setString(9, updatedProfile.getAdresseExacte());
            preparedStatement.setString(10, updatedProfile.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erreur SQL", "Erreur lors de la mise à jour du profil : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


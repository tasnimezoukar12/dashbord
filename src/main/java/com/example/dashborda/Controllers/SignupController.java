package com.example.dashborda.Controllers;

import com.example.dashborda.Models.signup;
import com.example.dashborda.Utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController {
    @FXML
    private TextField nomField;
@FXML
private Hyperlink linktologin;
    @FXML
    private TextField prenomField;

    @FXML
    private TextField numTelField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nomProjetField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField gouvernoratField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField adresseExacteField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signupBtn;

    private signup signupModel;

    public void initialize() {
        signupModel = new signup();
        bindFieldsToModel();

        // Bind the button click to the submit action
        signupBtn.setOnAction(this::submitButtonClicked);
    }

    private void bindFieldsToModel() {
        nomField.textProperty().bindBidirectional(signupModel.nomProperty());
        prenomField.textProperty().bindBidirectional(signupModel.prenomProperty());
        numTelField.textProperty().bindBidirectional(signupModel.numTelProperty());
        emailField.textProperty().bindBidirectional(signupModel.emailProperty());
        nomProjetField.textProperty().bindBidirectional(signupModel.nomProjetProperty());
        categorieField.textProperty().bindBidirectional(signupModel.categorieProperty());
        gouvernoratField.textProperty().bindBidirectional(signupModel.gouvernoratProperty());
        villeField.textProperty().bindBidirectional(signupModel.villeProperty());
        adresseExacteField.textProperty().bindBidirectional(signupModel.adresseExacteProperty());
        passwordField.textProperty().bindBidirectional(signupModel.passwordProperty());
    }

    @FXML
    private void submitButtonClicked(ActionEvent event) {
        System.out.println("Formulaire soumis !");
        System.out.println("Nom: " + signupModel.getNom());
        System.out.println("Prénom: " + signupModel.getPrenom());
        System.out.println("NumTel: " + signupModel.getNumTel());
        System.out.println("Email: " + signupModel.getEmail());
        System.out.println("NomProjet: " + signupModel.getNomProjet());
        System.out.println("Categorie: " + signupModel.getCategorie());
        System.out.println("Gouvernorat: " + signupModel.getGouvernorat());
        System.out.println("Ville: " + signupModel.getVille());
        System.out.println("AdresseExacte: " + signupModel.getAdresseExacte());
        System.out.println("Password: " + signupModel.getPassword());

        saveToDatabase();
    }

    private void saveToDatabase() {
        String sql = "INSERT INTO user (nom, prenom, numTel, email, nomProjet, categorie, gouvernorat, ville, adresseExacte, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, signupModel.getNom());
            preparedStatement.setString(2, signupModel.getPrenom());
            preparedStatement.setString(3, signupModel.getNumTel());
            preparedStatement.setString(4, signupModel.getEmail());
            preparedStatement.setString(5, signupModel.getNomProjet());
            preparedStatement.setString(6, signupModel.getCategorie());
            preparedStatement.setString(7, signupModel.getGouvernorat());
            preparedStatement.setString(8, signupModel.getVille());
            preparedStatement.setString(9, signupModel.getAdresseExacte());
            preparedStatement.setString(10, signupModel.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully.");

                // Load the login page
                loadLoginPage();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginPage() {
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("/com/example/dashborda/login.fxml"));
            Scene loginScene = new Scene(loginParent);
            Stage stage = (Stage) signupBtn.getScene().getWindow(); // Get the current stage
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@FXML
    public void go_to_login(ActionEvent actionEvent) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/login.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) linktologin.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


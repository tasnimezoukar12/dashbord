package com.example.dashborda.Controllers;

import com.example.dashborda.Utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;
    @FXML
    private Hyperlink linktosignup;

    @FXML
    void handleLogin(ActionEvent event) {
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in all fields.");
            return;
        }

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement prepare = connection.prepareStatement(sql)) {

            prepare.setString(1, emailField.getText());
            prepare.setString(2, passwordField.getText());

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    // Authentication successful
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dashborda/dashbord.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid email or password.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while logging in.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
@FXML
    public void go_to_signup(ActionEvent actionEvent) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/signup.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) linktosignup.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

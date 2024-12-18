package com.example.dashborda.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyConnection {

    private String url = "jdbc:mysql://localhost:3306/myevent_com?autoReconnect=true&useSSL=false";
    private String login = "root";
    private String pwd = "";
    private static MyConnection instance;
    private Connection cnx;

    // Constructeur privé
    private MyConnection() {
        connect();
    }

    // Méthode pour établir la connexion
    private void connect() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie...");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    // Méthode pour vérifier et obtenir la connexion
    public Connection getCnx() {
        try {
            if (cnx == null || cnx.isClosed()) {
                System.out.println("Connexion fermée. Tentative de reconnexion...");
                connect();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de la connexion", e);
        }
        return cnx;
    }

    // Singleton pour obtenir l'instance unique
    public static MyConnection getInstance() {
        if (instance == null) {
            synchronized (MyConnection.class) {
                if (instance == null) {
                    instance = new MyConnection();
                }
            }
        }
        return instance;
    }

    // Méthode pour exécuter une requête de comptage
    public int countRecords(String tableName) {
        String sql = "SELECT COUNT(*) AS count FROM " + tableName;
        try (PreparedStatement prepare = getCnx().prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {
            if (result.next()) {
                return result.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des enregistrements : " + e.getMessage());
        }
        return 0;
    }
}

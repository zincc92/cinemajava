import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import VUE.*;

public class Main {
    public static void main(String[] args) {
        // Informations de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/projetcinema";
        String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String motDePasse = ""; // Remplacez par votre mot de passe MySQL

        // Connexion à la base de données
        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion à la base de données réussie !");
            // Vous pouvez maintenant exécuter vos requêtes SQL à partir de la connexion
            // N'oubliez pas de fermer la connexion lorsque vous avez terminé :
            // connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Nouvelle instance");
                new connexion();
                new inscription();
                new accueil();
            }
        });
    }
}


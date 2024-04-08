import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import CONTROLLEUR.*;
import MODELE.*;
import VUE.inscription;
import VUE.connexion;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cinemaprojet";
        String utilisateur = "root";
        String motDePasse = "";

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion à la base de données réussie !");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    utilisateurControlleur utilisateurControlleur = new utilisateurControlleur(connexion);
                    inscription inscriptionView = new inscription(utilisateurControlleur);
                    connexion connexionView = new connexion(utilisateurControlleur, connexion);
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }
}

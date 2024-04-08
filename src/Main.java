import VUE.Accueil;
import VUE.barreDeTache;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Informations de connexion à la base de données
        String url = "jdbc:mysql://localhost:8889/cinemaprojet";
        String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String motDePasse = "root"; // Remplacez par votre mot de passe MySQL

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
                JFrame frame = new JFrame("CinemaAPP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1366, 768);
                frame.setResizable(false);

                barreDeTache menuBar = new barreDeTache(frame);
                frame.setJMenuBar(menuBar);

                //Accueil accueil = new Accueil();
                //frame.add(accueil);

                frame.setVisible(true);
            }
        });
    }
}

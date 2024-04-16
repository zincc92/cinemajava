package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class admin extends JPanel {
    private JPanel userPanel; // Panel pour afficher les utilisateurs

    public admin(Connection connexion) {
        initializeAdminView(connexion);
    }

    public void initializeAdminView(Connection connexion) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Vous êtes à présents sur la page Administrateur");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton voirUtilisateurButton = new JButton("Ensemble des utilisateurs");
        buttonPanel.add(voirUtilisateurButton);
        add(buttonPanel, BorderLayout.WEST);

        JButton voirFilmsButton = new JButton("Ensemble des films");
        buttonPanel.add(voirFilmsButton);

        voirFilmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherFilms(connexion);
            }
        });


        userPanel = new JPanel(new GridLayout(0, 1)); // Utilisation d'un GridLayout pour afficher les utilisateurs
        JScrollPane scrollPane = new JScrollPane(userPanel); // Ajout d'une barre de défilement si nécessaire
        add(scrollPane, BorderLayout.CENTER);

        // Ajouter un écouteur d'événements au bouton pour charger et afficher les utilisateurs
        voirUtilisateurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherUtilisateurs(connexion);
            }
        });
    }

    // Méthode pour charger et afficher les utilisateurs depuis la base de données
    private void afficherUtilisateurs(Connection connexion) {
        // Supprimer les utilisateurs précédemment affichés
        userPanel.removeAll();

        try {
            // Création d'une requête SQL pour récupérer les utilisateurs
            String query = "SELECT * FROM clients";
            // Exécution de la requête
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Parcourir les résultats et afficher chaque utilisateur et ses informations sur le panneau
            while (resultSet.next()) {
                String nomUtilisateur = resultSet.getString("nom");
                String email = resultSet.getString("email");
                int idUtilisateur = resultSet.getInt("id");
                int typeUtilisateur = resultSet.getInt("type");

                // Créer une chaîne de texte avec les informations de l'utilisateur
                String infoUtilisateur = nomUtilisateur + " | " + email + " | ID: " + idUtilisateur + " | Type: " + typeUtilisateur;
                JLabel label = new JLabel(infoUtilisateur);
                userPanel.add(label);
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panneau
        userPanel.revalidate();
        userPanel.repaint();
    }

    // Méthode pour charger et afficher les films depuis la base de données
    private void afficherFilms(Connection connexion) {
        // Supprimer les films précédemment affichés
        userPanel.removeAll();

        try {
            // Création d'une requête SQL pour récupérer les films
            String query = "SELECT * FROM films";
            // Exécution de la requête
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Parcourir les résultats et afficher chaque film sur le panneau
            while (resultSet.next()) {
                int idFilm = resultSet.getInt("id");
                String titreFilm = resultSet.getString("nom");
                Date dateFilm = resultSet.getDate("date");
                Time heureFilm = resultSet.getTime("horaire");
                String themeFilm = resultSet.getString("themes");
                String realFilm = resultSet.getString("realisateur");
                String synopsisFilm = resultSet.getString("synopsis");
                double prixFilm = resultSet.getDouble("prix");
                String infoFilm = "ID : "+idFilm+" | "+titreFilm + " | "+dateFilm+" "+heureFilm+" | "+themeFilm+" | "+realFilm+" | "+synopsisFilm+" | "+prixFilm+"€";
                JLabel label = new JLabel(infoFilm);
                userPanel.add(label);
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panneau
        userPanel.revalidate();
        userPanel.repaint();
    }
}

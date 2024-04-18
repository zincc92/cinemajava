package VUE;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import MODELE.*;
import MODELE.connexion;

public class compte extends JPanel {
    private JPanel infoPanel; // Panel pour afficher les informations utilisateur
    private JPanel reservationPanel; // Panel pour afficher les réservations de films

    public compte(Connection connexion, connexion session) {
        initializeMonCompteView(connexion, session);
    }

    public void initializeMonCompteView(Connection connexion, connexion session) {
        setLayout(new BorderLayout());
        System.out.println("Dedans1" + session.getUser());
        // Panel pour afficher les informations utilisateur
        infoPanel = new JPanel(new GridLayout(0, 1));
        add(infoPanel, BorderLayout.WEST);

        // Panel pour afficher les réservations de films
        reservationPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(reservationPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Charger et afficher les informations utilisateur
        afficherInformationsUtilisateur(connexion, session);

        // Charger et afficher les réservations de films
        afficherReservations(connexion, session);
    }

    // Méthode pour charger et afficher les informations utilisateur depuis la base de données
    private void afficherInformationsUtilisateur(Connection connexion, connexion session) {
        // Supprimer les informations utilisateur précédemment affichées
        infoPanel.removeAll();
        System.out.println("Dedans2" + session.getUser());
        try {
            // Récupérer l'ID de l'utilisateur à partir de l'email
            String email = session.getUser().getEmail();
            String queryId = "SELECT id FROM clients WHERE email = ?";
            PreparedStatement preparedStatementId = connexion.prepareStatement(queryId);
            preparedStatementId.setString(1, email);
            ResultSet resultSetId = preparedStatementId.executeQuery();

            int idUtilisateur = -1; // Initialiser à une valeur impossible

            // Vérifier si un résultat est retourné
            if (resultSetId.next()) {
                idUtilisateur = resultSetId.getInt("id");
            }

            // Fermer les ressources
            resultSetId.close();
            preparedStatementId.close();

            if (idUtilisateur != -1) {
                // Récupérer les autres informations de l'utilisateur à partir de son ID
                String queryInfo = "SELECT * FROM clients WHERE id = ?";
                PreparedStatement preparedStatementInfo = connexion.prepareStatement(queryInfo);
                preparedStatementInfo.setInt(1, idUtilisateur);
                ResultSet resultSetInfo = preparedStatementInfo.executeQuery();

                // Afficher les informations utilisateur sur le panneau
                if (resultSetInfo.next()) {
                    String nomUtilisateur = resultSetInfo.getString("nom");
                    String emailUtilisateur = resultSetInfo.getString("email");
                    // Ajouter d'autres informations utilisateur si nécessaire

                    // Afficher les informations utilisateur sur des labels
                    JLabel nomLabel = new JLabel("Nom: " + nomUtilisateur);
                    JLabel emailLabel = new JLabel("Email: " + emailUtilisateur);
                    // Ajouter d'autres labels pour les autres informations utilisateur si nécessaire

                    // Ajouter les labels au panel d'informations utilisateur
                    infoPanel.add(nomLabel);
                    infoPanel.add(emailLabel);
                    // Ajouter d'autres labels au panel d'informations utilisateur si nécessaire
                }

                // Fermer les ressources
                resultSetInfo.close();
                preparedStatementInfo.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panel d'informations utilisateur
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    // Méthode pour charger et afficher les réservations de films depuis la base de données
    private void afficherReservations(Connection connexion, connexion session) {
        // Supprimer les réservations de films précédemment affichées
        reservationPanel.removeAll();
        System.out.println("Dedans3" + session.getUser());
        try {
            // Récupérer l'ID de l'utilisateur à partir de l'email
            String email = session.getUser().getEmail();
            String queryId = "SELECT id FROM clients WHERE email = ?";
            PreparedStatement preparedStatementId = connexion.prepareStatement(queryId);
            preparedStatementId.setString(1, email);
            ResultSet resultSetId = preparedStatementId.executeQuery();

            int idUtilisateur = -1; // Initialiser à une valeur impossible

            // Vérifier si un résultat est retourné
            if (resultSetId.next()) {
                idUtilisateur = resultSetId.getInt("id");
            }

            // Fermer les ressources
            resultSetId.close();
            preparedStatementId.close();

            if (idUtilisateur != -1) {
                // Récupérer les réservations de films de l'utilisateur à partir de son ID
                String query = "SELECT * FROM reservations WHERE id_client = ?";
                PreparedStatement preparedStatement = connexion.prepareStatement(query);
                preparedStatement.setInt(1, idUtilisateur);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Parcourir les résultats et afficher chaque réservation de film sur le panneau
                while (resultSet.next()) {
                    int idFilm = resultSet.getInt("id_film");
                    int quantite = resultSet.getInt("quantite");
                    // Ajouter d'autres informations sur la réservation si nécessaire

                    // Créer une chaîne de texte avec les informations sur la réservation
                    String infoReservation = "ID du film: " + idFilm + " | Quantité: " + quantite;
                    JLabel label = new JLabel(infoReservation);

                    // Ajouter le label au panel de réservations de films
                    reservationPanel.add(label);
                }

                // Fermer les ressources
                resultSet.close();
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panel de réservations de films
        reservationPanel.revalidate();
        reservationPanel.repaint();
    }
}

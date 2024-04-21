package VUE;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import MODELE.connexion;
import CONTROLLEUR.utilisateurControlleur;

public class compte extends JPanel {
    private JPanel infoPanel; // Panel pour afficher les informations utilisateur
    private JPanel reservationPanel; // Panel pour afficher les réservations de films

    public compte(Connection connexion, connexion session, barreDeTache barreDeTache) {
        initializeMonCompteView(connexion, session, barreDeTache);
    }

    public void initializeMonCompteView(Connection connexion, connexion session, barreDeTache barreDeTache) {
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

        // Ajouter les rubriques des labels de réservations
        JLabel labelRubriques = new JLabel("Réservations de films:");
        reservationPanel.add(labelRubriques);

        // Charger et afficher les réservations de films
        List<JPanel> labels = utilisateurControlleur.afficherReservations(connexion, session, barreDeTache);
        for (JPanel label : labels) {
            reservationPanel.add(label);
        }
        reservationPanel.revalidate();
        reservationPanel.repaint();
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


        // Rafraîchir l'affichage du panel de réservations de films

}


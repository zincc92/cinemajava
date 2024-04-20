package CONTROLLEUR;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import MODELE.*;
import VUE.barreDeTache;

import javax.swing.*;

public class utilisateurControlleur {
    private static Connection connexion; // Modifier le type de connexion

    public utilisateurControlleur(Connection connexion) {
        this.connexion = connexion;
    }

    public void setConnexion(Connection connexion) {
        this.connexion = connexion;
    }

    public boolean checkConnexion() {
        if (connexion == null) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean inscrireUtilisateur(utilisateur User) {
        String query = "INSERT INTO clients (type, nom, email, mdp, age) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, User.getType());
            statement.setString(2, User.getNom());
            statement.setString(3, User.getEmail());
            statement.setString(4, User.getMotDePasse());
            statement.setInt(5, User.getAge()); // Insérer l'âge dans la base de données
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public connexion creerSession(String email, String motDePasse) {
        String query = "SELECT * FROM clients WHERE email = ? AND mdp = ?";
        try {
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, motDePasse);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Connexion réussie, créez une session
                utilisateur user = new utilisateur(
                        resultSet.getString("type"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("mdp"),
                        resultSet.getInt("age") // Ajoutez l'âge comme cinquième argument

                );
                String token = UUID.randomUUID().toString(); // Génération d'un token unique pour la session
                return new connexion(token, user);
            } else {
                // Échec de la connexion, retournez null
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getClientIdByEmail(String email) {
        String query = "SELECT id FROM clients WHERE email = ?";
        int clientId = -1; // Valeur par défaut si l'ID du client n'est pas trouvé
        try {
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                clientId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientId;
    }

    public static void insertReservation(connexion session, int idFilm, double prix, String emailClient, Date dateReservation, Time horaireReservation, String salle) {
        int idClient = utilisateurControlleur.getClientIdByEmail(emailClient);
        int quantite = 1;
        if (idClient != -1) {
            String query = "INSERT INTO reservations (id_film, id_client, prix, quantite,date_reservation, horaire_reservation, idSalle) VALUES (?, ?, ?, ?, ?, ?, ?)";
            // Récupérer la session actuelle
            try {
                PreparedStatement statement = connexion.prepareStatement(query);
                statement.setInt(1, idFilm);
                statement.setInt(2, idClient);
                statement.setDouble(3, prix);
                statement.setInt(4, quantite);
                statement.setDate(5, dateReservation);
                statement.setTime(6, horaireReservation);
                statement.setString(7, salle);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Client non trouvé avec l'email spécifié.");
        }
    }

    public static List<JPanel> afficherReservations(Connection connexion, connexion session, barreDeTache barreDeTache) {
        List<JPanel> panels = new ArrayList<>();
        try {
            // Récupérer l'ID de l'utilisateur à partir de l'email
            String email = session.getUser().getEmail();
            String queryId = "SELECT id FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatementId = connexion.prepareStatement(queryId)) {
                preparedStatementId.setString(1, email);
                try (ResultSet resultSetId = preparedStatementId.executeQuery()) {
                    int idUtilisateur = -1; // Initialiser à une valeur impossible

                    // Vérifier si un résultat est retourné
                    if (resultSetId.next()) {
                        idUtilisateur = resultSetId.getInt("id");
                    }

                    // Si l'utilisateur existe
                    if (idUtilisateur != -1) {
                        // Récupérer les réservations de films de l'utilisateur à partir de son ID
                        String query = "SELECT * FROM reservations WHERE id_client = ?";
                        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
                            preparedStatement.setInt(1, idUtilisateur);
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Parcourir les résultats et afficher chaque réservation de film sur le panneau
                                while (resultSet.next()) {
                                    int idReservation = resultSet.getInt("id"); // Pour identifier la réservation
                                    int idFilm = resultSet.getInt("id_film");
                                    int quantite = resultSet.getInt("quantite");
                                    BigDecimal prix = resultSet.getBigDecimal("prix");
                                    Date dateReservation = resultSet.getDate("date_reservation");
                                    Time horaireReservation = resultSet.getTime("horaire_reservation");
                                    int idSalle = resultSet.getInt("idSalle");
                                    // Créer une chaîne de texte avec les informations sur la réservation
                                    String infoReservation = "ID du film: " + idFilm + " | Quantité: " + quantite +
                                            " | Prix: " + prix + " | Date de réservation: " + dateReservation +
                                            " | Horaire de réservation: " + horaireReservation + " | ID de la salle: " + idSalle;

                                    // Créer un label pour afficher les informations de la réservation
                                    JLabel label = new JLabel(infoReservation);
                                    JPanel panelReservation = new JPanel(new GridLayout(0, 1));
                                    // Panel pour afficher les réservations de films
                                    JScrollPane scrollPane = new JScrollPane(panelReservation);

                                    // Créer un bouton "Supprimer" pour chaque réservation
                                    JButton boutonSupprimer = new JButton("Supprimer");
                                    boutonSupprimer.addActionListener(e -> {
                                        // Code pour supprimer la réservation de la base de données
                                        try {
                                            String queryDelete = "DELETE FROM reservations WHERE id = ?";
                                            try (PreparedStatement preparedStatementDelete = connexion.prepareStatement(queryDelete)) {
                                                preparedStatementDelete.setInt(1, idReservation);
                                                preparedStatementDelete.executeUpdate();

                                                // Afficher un pop-up de confirmation
                                                JOptionPane.showMessageDialog(null, "La réservation a été supprimée avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                            barreDeTache.showAccueil(session);
                                            // Actualiser l'affichage des réservations après la suppression
                                            // Vous pouvez rafraîchir l'interface utilisateur ou recharger les réservations
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }
                                    });
                                    panelReservation.add(label);
                                    panelReservation.add(boutonSupprimer);
                                    panelReservation.revalidate();
                                    panelReservation.repaint();


                                    // Ajouter le panneau au tableau de panneaux de réservations de films
                                    panels.add(panelReservation);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return panels;
    }

}
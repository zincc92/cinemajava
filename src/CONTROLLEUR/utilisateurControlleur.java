package CONTROLLEUR;

import java.sql.*;
import java.util.UUID;

import MODELE.*;

public class utilisateurControlleur {
    private static Connection connexion; // Modifier le type de connexion

    public utilisateurControlleur(Connection connexion) {
        this.connexion = connexion;
    }

    public void setConnexion(Connection connexion) {
        this.connexion = connexion;
    }

    public boolean checkConnexion(){
        if (connexion == null){
            return false;
        }
        else {
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

}

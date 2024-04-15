package CONTROLLEUR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public static boolean inscrireUtilisateur(utilisateur User) {
        String query = "INSERT INTO clients (type, nom, email, mdp) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1,User.getType());
            statement.setString(2, User.getNom());
            statement.setString(3, User.getEmail());
            statement.setString(4, User.getMotDePasse());
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
                        resultSet.getString("mdp")
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
    public boolean detruireSession(String token) {
        // Préparez une requête pour supprimer la session correspondant au token
        String query = "DELETE FROM sessions WHERE token = ?";
        try {
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, token);
            // Exécutez la requête de suppression
            int rowsAffected = statement.executeUpdate();
            // Vérifiez si la session a été supprimée avec succès
            if (rowsAffected > 0) {
                System.out.println("Déconnexion !");
                return true;
            } else {
                System.out.println("Aucune session trouvée avec le token spécifié.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la destruction de la session : " + e.getMessage());
            return false;
        }
    }


}

package MODELE;

import CONTROLLEUR.utilisateurControlleur;

import java.sql.PreparedStatement;

public class connexion {
    private String token; // Token unique pour la session
    private utilisateur user; // Utilisateur connecté

    public connexion() {
        this.token = null;
        this.user = null;
    }

    public connexion(String token, utilisateur user) {
        this.token = token;
        this.user = user;
    }

    public boolean isUserConnected() {
        if (token != null && user != null) {
            return true;
        }
        else {
            return false;
        }
    }


    public connexion(utilisateurControlleur utilisateurControlleur) {
    }

    public void destroySession() {
        // Vous pouvez simplement réinitialiser les attributs de la session à null ou une valeur par défaut
        this.token = null;
        this.user = null;
        // Vous pouvez également effectuer d'autres opérations nécessaires pour détruire la session, par exemple supprimer les données de session côté serveur
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public utilisateur getUser() {
        return user;
    }

    public void setUser(utilisateur user) {
        this.user = user;
    }

    public static PreparedStatement prepareStatement(String query) {
        return null;
    }
}



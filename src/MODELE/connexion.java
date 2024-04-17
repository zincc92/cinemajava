package MODELE;

import CONTROLLEUR.utilisateurControlleur;

import java.sql.PreparedStatement;

public class connexion {
    private String token; // Token unique pour la session
    private utilisateur user; // Utilisateur connecté

    public connexion(String token, utilisateur user) {
        this.token = token;
        this.user = user;
    }

    public connexion() {
        this.token = null;
        this.user = null;
    }

    public connexion(utilisateurControlleur utilisateurControlleur) {
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



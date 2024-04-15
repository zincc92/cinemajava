package MODELE;

public class utilisateur {
    private String type;
    private String nom;
    private String email;
    private String motDePasse;

    public utilisateur(String type, String nom, String email, String motDePasse) {
        this.type = "1";
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }
    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    // Getters et setters
    // Vous pouvez également ajouter d'autres méthodes selon vos besoins
}

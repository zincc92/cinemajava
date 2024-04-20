package MODELE;

public class utilisateur {
    private String type;
    private String nom;
    private String email;
    private String motDePasse;
    private int age; // Nouvel attribut pour l'âge

    public utilisateur(String type, String nom, String email, String motDePasse, int age) {
        this.type = type;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.age = age; // Initialisation de l'âge
    }

    // Getters et setters pour l'âge
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Autres getters et setters
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
}

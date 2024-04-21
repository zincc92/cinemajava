package MODELE;

public class payer {
    private double prix;
    private int nbrPlace;
    private int ageUtilisateur; // Ajouter l'âge de l'utilisateur comme attribut

    public payer(double prix, int nbrPlace, int ageUtilisateur) {
        this.prix = prix;
        this.nbrPlace = nbrPlace;
        this.ageUtilisateur = ageUtilisateur;
    }

    // Méthode pour calculer le prix à payer
    public double calculerPrix() {
        double prixTotal = prix * nbrPlace; // Calculer le prix total initial

        // Appliquer les réductions en fonction de l'âge de l'utilisateur
        if (ageUtilisateur < 25) {
            // Remise de 25% pour les moins de 25 ans
            prixTotal *= 0.75;
        } else if (ageUtilisateur > 60) {
            // Remise de 10% pour les seniors
            prixTotal *= 0.9;
        }

        return prixTotal;
    }
}
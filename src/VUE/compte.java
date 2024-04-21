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
    private connexion session;

    public compte(Connection connexion, connexion session, barreDeTache barreDeTache) {
        this.session = session;
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
        infoPanel.removeAll();
        // Ajouter les rubriques des labels de réservations
        JLabel labelRubriques = new JLabel("Réservations de films:");
        reservationPanel.add(labelRubriques);
        utilisateurControlleur.afficherInformationsUtilisateur(connexion, MODELE.connexion.getSession(), infoPanel);
        // Charger et afficher les réservations de films
        List<JPanel> labels = utilisateurControlleur.afficherReservations(connexion, MODELE.connexion.getSession(), barreDeTache);
        for (JPanel label : labels) {
            reservationPanel.add(label);
        }
        reservationPanel.revalidate();
        reservationPanel.repaint();
    }

}
    // Méthode pour charger et afficher les informations utilisateur depuis la base de données



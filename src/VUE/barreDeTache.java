package VUE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import CONTROLLEUR.*;

public class barreDeTache extends JMenuBar {
    private JFrame frame;

    public barreDeTache(JFrame frame, utilisateurControlleur utilisateurControlleur, Connection connexion) {
        this.frame = frame;

        //Création des différents boutons d'utilisation
        JButton accueilMenu = new JButton("Accueil");
        JButton diffusionsMenu = new JButton("Diffusions");
        JButton connexionMenu = new JButton("Connexion");
        JButton inscriptionMenu = new JButton("Inscription");
        // Si on est connecté :
        JButton monCompteMenu = new JButton("Mon Compte");
        JButton deconnexion = new JButton("Deconnexion");

        // Ajout des boutons à la barre de menu
        add(accueilMenu);
        add(diffusionsMenu);
        add(Box.createHorizontalGlue()); // Ajout d'un espace flexible
        // Si on est connecté, affiché les bons boutons, sinon les autres
        if (connexion != null) {
            add(monCompteMenu);
            add(deconnexion);
        }
        else {
            add(connexionMenu);
            add(inscriptionMenu);
        }

        //Lorsque le bouton accueil est sélectionné
        accueilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Accueil sélectionné");
                // Affichage de l'accueil
                showAccueil();
            }
        });

        //Lorsque le bouton diffusion est sélectionné
        diffusionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Diffusions sélectionné");
                //Affichage de la diffusion
                showDiffusions();
            }
        });

        //Lorsque le bouton connexion est sélectionné
        connexionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test Connexion");
                // Affichage de la connexion
                showConnexion(utilisateurControlleur, connexion, barreDeTache.this);
            }
        });

        //Lorsque le bouton inscription est sélectionné
        inscriptionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Inscription sélectionné");
                // Affichage de l'inscription
                showInscription(utilisateurControlleur, barreDeTache.this);
            }
        });

        accueilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Deconnexion sélectionné");
                // Affichage de l'accueil

            }
        });
        accueilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Mon Compte sélectionné");
                // Affichage de l'accueil
                showMonCompte();
            }
        });
    }

    public void showAccueil() {
        frame.getContentPane().removeAll();
        Accueil accueilPanel = new Accueil();
        accueilPanel.initializeAccueilView();
        frame.getContentPane().add(accueilPanel);
        frame.revalidate();
    }
    public void showMonCompte() {
        frame.getContentPane().removeAll();
        compte comptePanel = new compte();
        comptePanel.initializeCompteView();
        frame.getContentPane().add(comptePanel);
        frame.revalidate();
    }


    private void showConnexion(utilisateurControlleur utilisateurControlleur, Connection connexion, barreDeTache barreDeTache) {
        frame.getContentPane().removeAll();
        connexion connexionPanel = new connexion(utilisateurControlleur, connexion, barreDeTache);
        frame.getContentPane().add(connexionPanel.initializeConnexionView());
        frame.revalidate();
    }

    private void showInscription(utilisateurControlleur utilisateurControlleur, barreDeTache barreDeTache) {
        frame.getContentPane().removeAll();
        Inscription inscriptionPanel = new Inscription(utilisateurControlleur, barreDeTache);
        frame.getContentPane().add(inscriptionPanel.initializeInscriptionView());
        frame.revalidate();
    }

    private void showDiffusions(){
        frame.getContentPane().removeAll();
        diffusions diffusionsPanel = new diffusions();
        diffusionsPanel.initializediffusionsView();
        //frame.getContentPane().add(diffusionsPanel);
        frame.revalidate();
    }
}

package VUE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Objects;

import CONTROLLEUR.*;
import MODELE.*;

public class barreDeTache extends JMenuBar {
    private Connection connexion;
    private JFrame frame;
    private MODELE.connexion session;
    private JButton accueilMenu;
    private JButton diffusionsMenu;
    private JButton connexionMenu;
    private JButton inscriptionMenu;
    private JButton monCompteMenu;
    private JButton deconnexion;
    private JButton adminMenu;
    public connexion connexionPanel;

    public barreDeTache(JFrame frame, utilisateurControlleur utilisateurControlleur, Connection connexion, MODELE.connexion session) {
        this.frame = frame;
        this.connexion = connexion;
        this.session = session;

        //Création des différents boutons d'utilisation
        accueilMenu = new JButton("Accueil");
        diffusionsMenu = new JButton("Diffusions");
        connexionMenu = new JButton("Connexion");
        inscriptionMenu = new JButton("Inscription");
        // Si on est connecté :
        monCompteMenu = new JButton("Mon Compte");
        deconnexion = new JButton("Deconnexion");
        adminMenu = new JButton("Admin");

        updateButtons(session);

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
                showConnexion(utilisateurControlleur, connexion, barreDeTache.this, session);
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

        monCompteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Mon Compte sélectionné");
                // Affichage de l'accueil
                showMonCompte();
            }
        });

        deconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Déconnexion sélectionné");
                // Affichage de l'accueil
                showDeconnexion(utilisateurControlleur, connexion, barreDeTache.this, connexionPanel, session);
            }
        });
    }

    public void showAccueil() {
        frame.getContentPane().removeAll();
        Accueil accueilPanel = new Accueil();
        accueilPanel.initializeAccueilView();
        frame.getContentPane().add(accueilPanel);
        frame.revalidate();
        frame.repaint();
    }
    public void showMonCompte() {
        frame.getContentPane().removeAll();
        compte comptePanel = new compte();
        //comptePanel.initializeCompteView();
        frame.getContentPane().add(comptePanel);
        frame.revalidate();
    }


    private void showConnexion(utilisateurControlleur utilisateurControlleur, Connection connexion, barreDeTache barreDeTache, MODELE.connexion session) {
        frame.getContentPane().removeAll();
        connexionPanel = new connexion(utilisateurControlleur, connexion, barreDeTache, session);
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

    private void showDeconnexion(utilisateurControlleur utilisateurControlleur, Connection connexion, barreDeTache barreDeTache, connexion connexionInstance, MODELE.connexion session) {
        frame.getContentPane().removeAll();
        connexionInstance.deconnexion();
        //frame.getContentPane().add(connexionInstance.initializeConnexionView());
        frame.revalidate();
        frame.repaint();
    }

    public void updateButtons(MODELE.connexion session) {
        removeAll(); // Supprime tous les boutons actuels de la barre de tâches
        // Ajoutez les autres boutons (comme Accueil et Diffusions)
        add(accueilMenu);
        add(diffusionsMenu);
        add(Box.createHorizontalGlue()); // Ajout d'un espace flexible

        if (session.user == null) {
            // Ajoutez les boutons de connexion et d'inscription
            add(connexionMenu);
            add(inscriptionMenu);
            System.out.println("on est deco");

        } else {
            // Ajoutez les boutons du compte utilisateur et de déconnexion
            add(monCompteMenu);
            add(deconnexion);
            System.out.println("on est co");
            System.out.println(session.getUser().getType());
            System.out.println(session.getUser().getNom());
            if (Objects.equals(session.getUser().getType(), String.valueOf(2))) {
                add(adminMenu);
            }
        }
        revalidate(); // Rafraîchissez l'affichage de la barre de tâches
    }
}

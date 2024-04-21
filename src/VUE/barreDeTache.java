package VUE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
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
        showAccueil(session);
        updateButtons(session);
        System.out.println("UPDATE TOUR DE BOUCLE");

        //Lorsque le bouton accueil est sélectionné
        accueilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Accueil sélectionné");
                // Affichage de l'accueil
                showAccueil(session);
            }
        });

        //Lorsque le bouton diffusion est sélectionné


        //Lorsque le bouton admin est sélectionné
        adminMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test bouton Admin");
                // Appel de l'affichage admin
                showAdmin(connexion);
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
    }

    public void showAccueil(MODELE.connexion session) {
        frame.getContentPane().removeAll();
        Accueil accueilPanel = new Accueil();
        accueilPanel.initializeAccueilView();
        frame.getContentPane().add(accueilPanel);
        frame.revalidate();
        frame.repaint();
    }
    public void showMonCompte(Connection connexion, MODELE.connexion session, barreDeTache barreDeTache) {
        frame.getContentPane().removeAll();
        compte comptePanel = new compte(connexion, session, barreDeTache);
        frame.getContentPane().add(comptePanel);
        frame.revalidate();
        frame.repaint();
    }


    private void showConnexion(utilisateurControlleur utilisateurControlleur, Connection connexion, barreDeTache barreDeTache, MODELE.connexion session) {
        frame.getContentPane().removeAll();
        connexionPanel = new connexion(utilisateurControlleur, connexion, barreDeTache, session);
        frame.getContentPane().add(connexionPanel.initializeConnexionView());
        frame.revalidate();
        frame.repaint();
    }

    private void showInscription(utilisateurControlleur utilisateurControlleur, barreDeTache barreDeTache) {
        frame.getContentPane().removeAll();
        Inscription inscriptionPanel = new Inscription(utilisateurControlleur, barreDeTache);
        frame.getContentPane().add(inscriptionPanel.initializeInscriptionView());
        frame.revalidate();
        frame.repaint();
    }

    private void showDeconnexion(Connection connexion, barreDeTache barreDeTache, connexion connexionInstance, MODELE.connexion session) {
        frame.getContentPane().removeAll();
        connexionInstance.deconnexion();
        //frame.getContentPane().add(connexionInstance.initializeConnexionView());
        frame.revalidate();
        frame.repaint();
    }

    private void showAdmin(Connection connexion){
        frame.getContentPane().removeAll();
        admin adminPanel = new admin(connexion);
        adminPanel.initializeAdminView(connexion);
        frame.getContentPane().add(adminPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void updateButtons(MODELE.connexion session) {
        removeAll(); // Supprime tous les boutons actuels de la barre de tâches
        // Ajoutez les autres boutons (comme Accueil et Diffusions)
        add(accueilMenu);
        add(diffusionsMenu);

        diffusionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Diffusions sélectionné");
                // Affichage de la page de diffusions
                frame.getContentPane().removeAll();
                diffusions diffusionsPanel = new diffusions(session);
                frame.getContentPane().add(diffusionsPanel);
                frame.revalidate();
            }
        });
        add(Box.createHorizontalGlue()); // Ajout d'un espace flexible

        if (session == null) {
            // Ajoutez les boutons de connexion et d'inscription
            add(connexionMenu);
            add(inscriptionMenu);
            System.out.println("on est deco");

        } else {
            // Ajoutez les boutons du compte utilisateur et de déconnexion
            add(monCompteMenu);
            monCompteMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Menu Mon Compte sélectionné");
                    System.out.println(session.getUser());
                    showMonCompte(connexion, session, barreDeTache.this);
                }
            });
            add(deconnexion);
            deconnexion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Déconnexion sélectionné");
                    // Chaffinch de l'accueil
                    showDeconnexion(connexion, barreDeTache.this, connexionPanel, session);
                }
            });
            System.out.println("on est co");
            System.out.println(session);
            if (session != null & Objects.equals(session.getUser().getType(), String.valueOf(2))) {
                add(adminMenu);
            }
        }
        revalidate(); // Rafraîchissez l'affichage de la barre de tâches
    }
    public void updateSession(MODELE.connexion session) {
        this.session = session;
        System.out.println("Session updated: " + session.getUser());
    }
}

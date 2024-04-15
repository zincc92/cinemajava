package VUE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class barreDeTache extends JMenuBar {
    private JFrame frame;

    public barreDeTache(JFrame frame) {
        this.frame = frame;

        //Création des différents boutons d'utilisation
        JButton accueilMenu = new JButton("Accueil");
        JButton diffusionsMenu = new JButton("Diffusions");
        JButton connexionMenu = new JButton("Connexion");
        JButton inscriptionMenu = new JButton("Inscription");

        // Ajout des boutons à la barre de menu
        add(accueilMenu);
        add(diffusionsMenu);
        add(Box.createHorizontalGlue()); // Ajout d'un espace flexible
        add(connexionMenu);
        add(inscriptionMenu);

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
                showConnexion();
            }
        });

        //Lorsque le bouton inscription est sélectionné
        inscriptionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Inscription sélectionné");
                // Affichage de l'inscription
                showInscription();
            }
        });
    }

    private void showAccueil() {
        frame.getContentPane().removeAll();
        Accueil accueilPanel = new Accueil();
        accueilPanel.initializeAccueilView();
        frame.getContentPane().add(accueilPanel);
        frame.revalidate();
    }

    private void showConnexion() {
        frame.getContentPane().removeAll();
        Connexion connexionPanel = new Connexion();
        frame.getContentPane().add(connexionPanel.initializeConnexionView());
        frame.revalidate();
    }

    private void showInscription() {
        frame.getContentPane().removeAll();
        Inscription inscriptionPanel = new Inscription();
        frame.getContentPane().add(inscriptionPanel.initializeInscriptionView());
        frame.revalidate();
    }

    private void showDiffusions(){
        frame.getContentPane().removeAll();
        diffusions diffusionsPanel = new diffusions();
        diffusionsPanel.initializediffusionsView();
        frame.getContentPane().add(diffusionsPanel);
        frame.revalidate();
    }
}

package VUE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class barreDeTache extends JMenuBar {
    public barreDeTache(JFrame frame) {
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

        accueilMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Accueil sélectionné");
                // Affichage de l'accueil
                frame.getContentPane().removeAll();
                Accueil accueilPanel = new Accueil();
                accueilPanel.initializeAccueilView();
                frame.getContentPane().add(accueilPanel);
                frame.revalidate();
            }
        });

        diffusionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Diffusions sélectionné");
            }
        });

        connexionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test Connexion");
                // Affichage de la connexion
                frame.getContentPane().removeAll();
                frame.getContentPane().add(connexion.initializeConnexionView());
                frame.revalidate();
            }
        });

        inscriptionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu Inscription sélectionné");
                frame.getContentPane().removeAll();
                Accueil accueilPanel = new Accueil();
                accueilPanel.initializeAccueilView();
                frame.getContentPane().add(accueilPanel);
                frame.revalidate();
            }
        });
    }
}

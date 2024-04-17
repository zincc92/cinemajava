package VUE;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CONTROLLEUR.*;
import MODELE.*;

public class Inscription {

    private final VUE.barreDeTache barreDeTache;
    private JPanel panel;
    private JFrame frame;
    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton inscriptionButton;
    private utilisateurControlleur user;

    public Inscription(utilisateurControlleur user, barreDeTache barreDeTache) {
        this.user = user;
        this.barreDeTache = barreDeTache;
        initializeInscriptionView();
    }
    Component initializeInscriptionView() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Utilisation d'un GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marge entre les composants

        JLabel nomLabel = new JLabel("Nom:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomLabel, gbc);

        nomField = new JTextField(15); // Taille réduite pour le champ de texte nom
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nomField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(15); // Taille réduite pour le champ de texte email
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15); // Taille réduite pour le champ de texte mot de passe
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);

        inscriptionButton = new JButton("Inscription");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        panel.add(inscriptionButton, gbc);

        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String email = emailField.getText();
                String type = "1";
                String motDePasse = new String(passwordField.getPassword());
                utilisateur user= new utilisateur(type, nom, email, motDePasse);
                boolean inscriptionReussie = utilisateurControlleur.inscrireUtilisateur(user);
                if (inscriptionReussie) {
                    JOptionPane.showMessageDialog(frame, "Inscription réussie !");
                    barreDeTache.showAccueil();
                } else {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'inscription !");
                }

            }
        });

        return panel;
    }
}

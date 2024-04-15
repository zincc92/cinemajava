package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connexion {
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Connexion() {
        initializeConnexionView();
    }

    public JPanel initializeConnexionView() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Utilisation d'un GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marge entre les composants

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(15); // Taille réduite pour le champ de texte email
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15); // Taille réduite pour le champ de texte mot de passe
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Connexion2");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        panel.add(loginButton, gbc);

        return panel; // Retourne le panneau
    }
}

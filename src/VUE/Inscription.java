package VUE;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CONTROLLEUR.*;
import MODELE.*;
import MODELE.connexion;

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

    public class IntegerInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField textField = (JTextField) input;
            String text = textField.getText();
            try {
                Integer.parseInt(text); // Tentative de conversion de la saisie en entier
                return true; // Si la conversion réussit, la saisie est valide
            } catch (NumberFormatException e) {
                return false; // Si une exception est levée, la saisie n'est pas un entier
            }
        }
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

        JLabel ageLabel = new JLabel("Âge:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(ageLabel, gbc);

        JTextField ageField = new JTextField(15); // Champ de saisie pour l'âge
        ageField.setInputVerifier(new IntegerInputVerifier()); // Utilisation d'un vérificateur pour s'assurer que seul un entier est saisi
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ageField, gbc);



        inscriptionButton = new JButton("Inscription");
        gbc.gridx = 0;
        gbc.gridy = 4;
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
                int age = 0; // Valeur par défaut
                try {
                    age = Integer.parseInt(ageField.getText());
                } catch (NumberFormatException ex) {
                    // Gérer l'exception si la saisie d'âge n'est pas un entier valide
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un âge valide !");
                    return; // Sortir de la méthode actionPerformed si la saisie n'est pas valide
                }
                utilisateur user = new utilisateur(type, nom, email, motDePasse, age);
                boolean inscriptionReussie = utilisateurControlleur.inscrireUtilisateur(user);
                if (inscriptionReussie) {
                    JOptionPane.showMessageDialog(frame, "Inscription réussie !");
                    barreDeTache.showAccueil(connexion.getSession());
                } else {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'inscription !");
                }
            }
        });




        return panel;
    }
}

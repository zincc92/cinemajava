package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;

import CONTROLLEUR.*;
import MODELE.*;

public class connexion {
    private final utilisateurControlleur user;
    private final Connection connexion;
    private JFrame frame;
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private barreDeTache barreDeTache;
    private MODELE.connexion session;

    public connexion(utilisateurControlleur user, Connection connexion, barreDeTache barreDeTache, MODELE.connexion session) {
        this.user = user;
        this.connexion = connexion; // Initialisez la connexion
        this.barreDeTache = barreDeTache;
        this.session = session;
        initializeConnexionView();
    }

    Component initializeConnexionView() {
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


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                utilisateurControlleur controller = new utilisateurControlleur(connexion);
                session = controller.creerSession(email, password);
                if (session != null) {
                    // Passer la connexion au contrôleur utilisateur si la connexion réussit
                    controller.setConnexion(connexion);
                    JOptionPane.showMessageDialog(frame, "Connexion réussie!");
                    // Afficher le nom de l'utilisateur dans la console
                    System.out.println("Utilisateur connecté : " + session.getUser().getNom());
                    // Rediriger vers la page d'accueil
                    barreDeTache.updateButtons(session);
                    barreDeTache.showAccueil();


                } else {
                    JOptionPane.showMessageDialog(frame, "Email ou mot de passe incorrect!");
                }
            }
        });
        return panel;
    }

    // Méthode pour centrer la fenêtre sur l'écran
    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    public void deconnexion() {
        if (session != null) {
            session.user = null; // Réinitialiser la session à null
            session.token = null;
            System.out.println("Utilisateur déconnecté");
            barreDeTache.updateButtons(session);
            barreDeTache.showAccueil();
            // Supprimer les composants liés à la session précédente
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            // Autres actions après la déconnexion, comme afficher l'écran de connexion
        } else {
            System.out.println("Aucune session à déconnecter.");
        }
    }


}

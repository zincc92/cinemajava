package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inscription {
    private JFrame frame;
    private JPanel panel;
    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton inscriptionButton;

    public inscription() {
        initializeInscriptionView();
    }

    private void initializeInscriptionView() {
        frame = new JFrame("Inscription");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200); // Dimension personnalisée de la fenêtre
        frame.setResizable(false); // Verrouille la taille de la fenêtre
        centerFrameOnScreen(frame); // Centrer la fenêtre sur l'écran

        // Chargement de l'icône depuis le chemin relatif
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/logo.png"));
        frame.setIconImage(icon.getImage());

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

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Méthode pour centrer la fenêtre sur l'écran
    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}

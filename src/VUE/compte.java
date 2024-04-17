package VUE;

import javax.swing.*;
import java.awt.*;

public class compte extends JFrame {

    public compte() {
        setTitle("Page de compte client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Création d'un panneau pour afficher les informations du client
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField();
        nomField.setEditable(false); // Rendre le champ non éditable
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        emailField.setEditable(false); // Rendre le champ non éditable
        JLabel passwordLabel = new JLabel("Mot de passe:");
        JTextField passwordField = new JTextField();
        passwordField.setEditable(false); // Rendre le champ non éditable
        JLabel reservationsLabel = new JLabel("Réservations de films:");
        JTextField reservationsField = new JTextField();
        reservationsField.setEditable(false); // Rendre le champ non éditable

        infoPanel.add(nomLabel);
        infoPanel.add(nomField);
        infoPanel.add(emailLabel);
        infoPanel.add(emailField);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordField);
        infoPanel.add(reservationsLabel);
        infoPanel.add(reservationsField);

        panel.add(infoPanel, BorderLayout.CENTER);

        // Bouton pour ouvrir l'onglet de modification
        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> {
            // Ouvrir un nouvel onglet pour la modification
            modifierInfosClient(nomField.getText(), emailField.getText(), passwordField.getText(), reservationsField.getText());
        });

        // Ajouter le bouton de modification en bas de la fenêtre
        panel.add(modifierButton, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        // Rendre la fenêtre visible après avoir ajouté tous les composants
        setVisible(true);
    }

    // Méthode pour ouvrir l'onglet de modification
    private void modifierInfosClient(String nom, String email, String password, String reservations) {
        JFrame frame = new JFrame("Modifier les informations du client");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Ajouter les composants de modification ici (nom, email, mot de passe, réservations de films...)
        // Vous pouvez utiliser des JTextField, JPasswordField, JTextArea, etc., pour permettre la modification

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField(nom);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(email);
        JLabel passwordLabel = new JLabel("Nouveau mot de passe:");
        JPasswordField newPasswordField = new JPasswordField();
        JLabel reservationsLabel = new JLabel("Réservations de films:");
        JTextArea reservationsField = new JTextArea(reservations);

        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(newPasswordField);
        panel.add(reservationsLabel);
        panel.add(reservationsField);

        // Boutons "Revenir à mon compte" et "Valider"
        JButton retourButton = new JButton("Revenir à mon compte");
        retourButton.addActionListener(e -> frame.dispose()); // Ferme la fenêtre de modification

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            // Insérer ici la logique de validation des modifications dans la base de données
            // Une fois les modifications validées, vous pouvez fermer la fenêtre de modification
            frame.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(retourButton);
        buttonPanel.add(validerButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(compte::new);
    }

}

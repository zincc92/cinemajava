import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class admin {
    private JFrame frame;
    private JPanel panel;
    private JButton manageUsersButton;
    private JButton manageMoviesButton;
    private JButton manageTheatersButton;
    private JButton manageReservationsButton;

    public admin() {
        initializeAdminPanel();
    }

    private void initializeAdminPanel() {
        frame = new JFrame("Panneau d'administration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        centerFrameOnScreen(frame);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 116, 169)); // Couleur d'en-tête
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 100)); // Hauteur de l'en-tête

        // Chargement du logo depuis le chemin relatif
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/logo.png"));
        JLabel logoLabel = new JLabel(icon);
        headerPanel.add(logoLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu accueilMenu = new JMenu("Accueil");
        JMenu diffusionsMenu = new JMenu("Diffusions");
        JMenu monCompteMenu = new JMenu("Mon Compte");

        menuBar.add(accueilMenu);
        menuBar.add(diffusionsMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(monCompteMenu);
        menuBar.setBackground(new Color(245, 245, 245)); // Couleur de fond du menu
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 40)); // Hauteur du menu
        panel.add(menuBar, BorderLayout.NORTH);

        // Contenu
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1, 20, 20)); // Utilisation d'un GridLayout
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marge intérieure
        contentPanel.setBackground(new Color(240, 240, 240)); // Couleur de fond du contenu

        // Boutons de gestion
        manageUsersButton = createStyledButton("Gérer les utilisateurs");
        manageMoviesButton = createStyledButton("Gérer les films");
        manageTheatersButton = createStyledButton("Gérer les salles de cinéma");
        manageReservationsButton = createStyledButton("Gérer les réservations");

        // Ajout des boutons au contenu
        contentPanel.add(manageUsersButton);
        contentPanel.add(manageMoviesButton);
        contentPanel.add(manageTheatersButton);
        contentPanel.add(manageReservationsButton);

        panel.add(contentPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Méthode utilitaire pour créer un bouton stylisé avec effet de survol
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Police et taille du texte
        button.setBackground(new Color(37, 116, 169)); // Couleur de fond du bouton
        button.setForeground(Color.WHITE); // Couleur du texte
        button.setFocusPainted(false); // Supprime l'effet de focus
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Marge intérieure

        // Ajout de l'effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 150, 200)); // Changement de couleur au survol
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(37, 116, 169)); // Retour à la couleur d'origine
            }
        });

        return button;
    }

    // Méthode pour centrer la fenêtre sur l'écran
    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}

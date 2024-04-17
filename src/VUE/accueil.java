package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class accueil {
    private JFrame frame;
    private JPanel panel;
    private JLabel welcomeLabel;
    private JLabel footerLabel;

    public accueil() {
        initializeAccueilView();
    }

    public void initializeAccueilView() {
        frame = new JFrame("CinemaAPP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimension personnalisée de la fenêtre
        frame.setResizable(false); // Verrouille la taille de la fenêtre
        centerFrameOnScreen(frame); // Centrer la fenêtre sur l'écran
        // Chargement de l'icône depuis le chemin relatif
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/logo.png"));
        frame.setIconImage(icon.getImage());


        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        JMenu accueilMenu = new JMenu("Accueil");
        JMenu diffusionsMenu = new JMenu("Diffusions");
        JMenu connexionMenu = new JMenu("Connexion");
        menuBar.add(accueilMenu);
        menuBar.add(diffusionsMenu);
        menuBar.add(Box.createHorizontalGlue()); // Ajout d'un espace flexible
        menuBar.add(connexionMenu);
        frame.setJMenuBar(menuBar);

        // Chargement de l'image et redimensionnement
        ImageIcon originalImageIcon = new ImageIcon(getClass().getResource("/IMAGES/acceuiltop.png"));
        Image originalImage = originalImageIcon.getImage();
        Image resizedImage = resizeImage(originalImage, 585, 170);

        // Ajout de l'image horizontale au top
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        panel.add(imageLabel, BorderLayout.NORTH);

        // Ajout de la section de bienvenue
        welcomeLabel = new JLabel("Bienvenue sur notre application! Consultez les diffusions de films, réservez vos séances !");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);

        // Ajout du footer
        footerLabel = new JLabel("© 2024 CinemaAPP tous droits réservés");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(footerLabel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Méthode pour redimensionner une image
    private Image resizeImage(Image image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    // Méthode pour centrer la fenêtre sur l'écran
    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

}

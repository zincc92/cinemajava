package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class diffusions {
    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel;
    private JLabel film1Label;
    private JLabel film2Label;
    private JLabel film3Label;

    public diffusions() {
        initializeDiffusionsView();
    }

    private void initializeDiffusionsView() {
        frame = new JFrame("CinemaAPP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimension personnalisée de la fenêtre
        frame.setResizable(false); // Verrouille la taille de la fenêtre
        centerFrameOnScreen(frame); // Centrer la fenêtre sur l'écran

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Chargement de l'icône depuis le chemin relatif
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/logo.png"));
        frame.setIconImage(icon.getImage());


        // Ajout de l'image en tant qu'en-tête
        ImageIcon headerIcon = new ImageIcon(getClass().getResource("/IMAGES/diffusionstop.png"));
        Image headerImage = headerIcon.getImage();
        Image resizedHeaderImage = resizeImage(headerImage, 585, 100);
        headerLabel = new JLabel(new ImageIcon(resizedHeaderImage));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Ajout des films diffusés avec leurs horaires et salles
        film1Label = new JLabel("<html><b>Film 1:</b> Horaires: 12:00, 15:00, 18:00 - Salle 1</html>");
        film2Label = new JLabel("<html><b>Film 2:</b> Horaires: 13:00, 16:00, 19:00 - Salle 2</html>");
        film3Label = new JLabel("<html><b>Film 3:</b> Horaires: 14:00, 17:00, 20:00 - Salle 3</html>");
        panel.add(film1Label, BorderLayout.CENTER);
        panel.add(film2Label, BorderLayout.CENTER);
        panel.add(film3Label, BorderLayout.CENTER);

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

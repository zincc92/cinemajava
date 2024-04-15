package VUE;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class diffusions {
    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel;
    private JPanel filmPanel1;
    private JPanel filmPanel2;
    private JPanel filmPanel3;

    public diffusions() {
        initializeDiffusionsView();
    }

    private void initializeDiffusionsView() {
        frame = new JFrame("CinemaAPP - Diffusions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimension personnalisée de la fenêtre
        frame.setResizable(false); // Verrouille la taille de la fenêtre
        centerFrameOnScreen(frame); // Centrer la fenêtre sur l'écran

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Ajout de l'image en tant qu'en-tête
        ImageIcon headerIcon = new ImageIcon(getClass().getResource("/IMAGES/diffusionstop.png"));
        Image headerImage = headerIcon.getImage();
        Image resizedHeaderImage = resizeImage(headerImage, 585, 170);
        headerLabel = new JLabel(new ImageIcon(resizedHeaderImage));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Création des panneaux pour les films
        filmPanel1 = createFilmPanel("Film 1", "Horaires: 12:00, 15:00, 18:00 - Salle 1");
        filmPanel2 = createFilmPanel("Film 2", "Horaires: 13:00, 16:00, 19:00 - Salle 2");
        filmPanel3 = createFilmPanel("Film 3", "Horaires: 14:00, 17:00, 20:00 - Salle 3");

        // Ajout des panneaux de films au centre du panneau principal
        JPanel filmsContainerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        filmsContainerPanel.add(filmPanel1);
        filmsContainerPanel.add(filmPanel2);
        filmsContainerPanel.add(filmPanel3);
        panel.add(filmsContainerPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private JPanel createFilmPanel(String filmTitle, String filmInfo) {
        JPanel filmPanel = new JPanel();
        filmPanel.setLayout(new BorderLayout());
        filmPanel.setPreferredSize(new Dimension(300, 100));
        filmPanel.setBackground(Color.lightGray);
        Border border = BorderFactory.createLineBorder(Color.black);
        filmPanel.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel titleLabel = new JLabel("<html><b>" + filmTitle + "</b></html>");
        JLabel infoLabel = new JLabel("<html>" + filmInfo + "</html>");

        filmPanel.add(titleLabel, BorderLayout.NORTH);
        filmPanel.add(infoLabel, BorderLayout.CENTER);

        // Ajouter un effet de survol
        filmPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filmPanel.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filmPanel.setBackground(Color.lightGray);
            }
        });

        return filmPanel;
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

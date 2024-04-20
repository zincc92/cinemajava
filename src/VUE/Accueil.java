package VUE;

import javax.swing.*;
import java.awt.*;

public class Accueil extends JPanel {
    private JLabel imageLabel;

    public Accueil() {
        initializeAccueilView();
    }

    public void initializeAccueilView() {
        setLayout(new BorderLayout());

        // Créer un JLabel pour afficher l'image
        ImageIcon imageIcon = new ImageIcon("src/IMAGES/accueil.png");
        imageLabel = new JLabel(imageIcon);

        // Centrer l'image horizontalement
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Ajouter le JLabel contenant l'image au panneau
        add(imageLabel, BorderLayout.CENTER);
    }
}
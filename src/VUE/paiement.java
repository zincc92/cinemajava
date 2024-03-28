package VUE;

import javax.swing.*;
import java.awt.*;

public class paiement {
    private JFrame frame;
    private JPanel panel;
    private JLabel priceLabel;
    private JLabel secureTextLabel;
    private JLabel securePaymentLabel;
    private JLabel mastercardLabel;
    private JLabel visaLabel;
    private JPanel formPanel;

    public paiement() {
        initializePaiementView();
    }

    private void initializePaiementView() {
        frame = new JFrame("Paiement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); // Augmenté la hauteur pour accommoder les éléments
        frame.setResizable(false);
        centerFrameOnScreen(frame);

        // Chargement de l'icône depuis le chemin relatif
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/logo.png"));
        frame.setIconImage(icon.getImage());

        panel = new JPanel(new BorderLayout());

        // Logos Visa et Mastercard en haut au centre
        JPanel logosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon mastercardIcon = new ImageIcon(getClass().getResource("/IMAGES/mastercard.png"));
        mastercardLabel = new JLabel(mastercardIcon);
        logosPanel.add(mastercardLabel);

        ImageIcon visaIcon = new ImageIcon(getClass().getResource("/IMAGES/visa.png"));
        visaLabel = new JLabel(visaIcon);
        logosPanel.add(visaLabel);

        panel.add(logosPanel, BorderLayout.NORTH);

        // Formulaire au centre
        formPanel = new JPanel(new GridLayout(6, 2)); // 6 rangées pour les champs, 2 colonnes pour les étiquettes et les champs
        // Ajout des champs du formulaire
        formPanel.add(new JLabel("Numéro de carte :"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Nom sur la carte :"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Date de validité :"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("CVC :"));
        formPanel.add(new JTextField());
        // Ajout d'un espace vide pour l'alignement
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        // Bouton "Payer" au centre
        JButton payerButton = new JButton("Payer");
        formPanel.add(payerButton);

        panel.add(formPanel, BorderLayout.CENTER);

        // Panel pour le logo du cadenas et le label "Paiement sécurisé"
        JPanel securePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon secureIcon = new ImageIcon(getClass().getResource("/IMAGES/secure_lock.png"));
        securePaymentLabel = new JLabel(secureIcon);
        securePanel.add(securePaymentLabel);
        secureTextLabel = new JLabel("Paiement sécurisé");
        securePanel.add(secureTextLabel);
        panel.add(securePanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

}

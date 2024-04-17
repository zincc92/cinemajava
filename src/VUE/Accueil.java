package VUE;


import javax.swing.*;
import java.awt.*;
public class Accueil extends JPanel {
    private JLabel welcomeLabel;
    private JLabel footerLabel;

    public Accueil() {
        initializeAccueilView();
    }

    public void initializeAccueilView() {
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Bienvenue sur notre application! Consultez les diffusions de films, réservez vos séances !");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        footerLabel = new JLabel("© 2024 CinemaAPP tous droits réservés");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }
}
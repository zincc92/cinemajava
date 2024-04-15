package VUE;

import javax.swing.*;
import java.awt.*;

public class diffusions extends JPanel{

    private JPanel panel;
    private JLabel welcomeLabel;

    public diffusions(){
        initializediffusionsView();
    }

    public JPanel initializediffusionsView(){
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Acctuellement présent sur la page de diffusion");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);


        return panel;
    }
}

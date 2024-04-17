package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class admin extends JPanel {
    private JPanel userPanel; // Panel pour afficher les utilisateurs

    public admin(Connection connexion) {
        initializeAdminView(connexion);
    }

    public void initializeAdminView(Connection connexion) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Vous êtes à présents sur la page Administrateur");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton voirUtilisateurButton = new JButton("Ensemble des utilisateurs");
        buttonPanel.add(voirUtilisateurButton);
        add(buttonPanel, BorderLayout.WEST);

        JButton voirFilmsButton = new JButton("Ensemble des films");
        buttonPanel.add(voirFilmsButton);

        JButton ajouterFilmButton = new JButton("Ajouter un film");
        buttonPanel.add(ajouterFilmButton);

        ajouterFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterFilm(connexion);
            }
        });

        voirFilmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherFilms(connexion);
            }
        });

        userPanel = new JPanel(new GridLayout(0, 1)); // Utilisation d'un GridLayout pour afficher les utilisateurs
        JScrollPane scrollPane = new JScrollPane(userPanel); // Ajout d'une barre de défilement si nécessaire
        add(scrollPane, BorderLayout.CENTER);

        // Ajouter un écouteur d'événements au bouton pour charger et afficher les utilisateurs
        voirUtilisateurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherUtilisateurs(connexion);
            }
        });
    }

    // Méthode pour charger et afficher les utilisateurs depuis la base de données
    private void afficherUtilisateurs(Connection connexion) {
        // Supprimer les utilisateurs précédemment affichés
        userPanel.removeAll();

        try {
            // Création d'une requête SQL pour récupérer les utilisateurs
            String query = "SELECT * FROM clients";
            // Exécution de la requête
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Parcourir les résultats et afficher chaque utilisateur et ses informations sur le panneau
            while (resultSet.next()) {
                String nomUtilisateur = resultSet.getString("nom");
                String email = resultSet.getString("email");
                int idUtilisateur = resultSet.getInt("id");
                int typeUtilisateur = resultSet.getInt("type");

                // Créer une chaîne de texte avec les informations de l'utilisateur
                String infoUtilisateur = nomUtilisateur + " | " + email + " | ID: " + idUtilisateur + " | Type: " + typeUtilisateur;
                JLabel label = new JLabel(infoUtilisateur);
                userPanel.add(label);
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panneau
        userPanel.revalidate();
        userPanel.repaint();
    }

    // Méthode pour charger et afficher les films depuis la base de données
    private void afficherFilms(Connection connexion) {
        // Supprimer les films précédemment affichés
        userPanel.removeAll();

        try {
            // Création d'une requête SQL pour récupérer les films
            String query = "SELECT * FROM films";
            // Exécution de la requête
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Parcourir les résultats et afficher chaque film sur le panneau
            while (resultSet.next()) {
                int idFilm = resultSet.getInt("id");
                String titreFilm = resultSet.getString("nom");
                Date dateFilm = resultSet.getDate("date");
                Time heureFilm = resultSet.getTime("horaire");
                String themeFilm = resultSet.getString("themes");
                String realFilm = resultSet.getString("realisateur");
                String synopsisFilm = resultSet.getString("synopsis");
                double prixFilm = resultSet.getDouble("prix");
                String infoFilm = "ID : "+idFilm+" | "+titreFilm + " | "+dateFilm+" "+heureFilm+" | "+themeFilm+" | "+realFilm+" | "+synopsisFilm+" | "+prixFilm+"€";
                JLabel label = new JLabel(infoFilm);
                userPanel.add(label);
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir l'affichage du panneau
        userPanel.revalidate();
        userPanel.repaint();
    }

    private int getLastFilmId(Connection connexion) throws SQLException {
        int lastId = 0;
        String query = "SELECT MAX(id) AS last_id FROM films";
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            lastId = resultSet.getInt("last_id");
        }
        resultSet.close();
        statement.close();
        return lastId;
    }

    private int generateNewFilmId(Connection connexion) throws SQLException {
        int lastId = getLastFilmId(connexion);
        return lastId + 1;
    }

    private void ajouterFilm(Connection connexion) {
        // Boîte de dialogue pour saisir les informations sur le nouveau film
        JTextField titreField = new JTextField(20);
        JTextField realisateurField = new JTextField(20);
        JTextField dateField = new JTextField(10); // Format "yyyy-mm-dd"
        JTextField horaireField = new JTextField(5); // Format "hh:mm"
        JTextField themeField = new JTextField(20);
        JTextField synopsisField = new JTextField(50);
        JTextField prixField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Titre:"));
        panel.add(titreField);
        panel.add(new JLabel("Réalisateur:"));
        panel.add(realisateurField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Horaire (HH:MM):"));
        panel.add(horaireField);
        panel.add(new JLabel("Thème:"));
        panel.add(themeField);
        panel.add(new JLabel("Synopsis:"));
        panel.add(synopsisField);
        panel.add(new JLabel("Prix:"));
        panel.add(prixField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter un film",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Récupérer les valeurs saisies
            String titre = titreField.getText();
            String realisateur = realisateurField.getText();
            String date = dateField.getText();
            String horaire = horaireField.getText();
            String theme = themeField.getText();
            String synopsis = synopsisField.getText();
            double prix = Double.parseDouble(prixField.getText());

            // Générer un nouvel ID unique pour le film
            int newId;
            try {
                newId = generateNewFilmId(connexion);
                // Insérer le nouveau film dans la base de données avec l'ID généré
                insererFilm(connexion, newId, titre, realisateur, date, horaire, theme, synopsis, prix);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la génération du nouvel ID : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void insererFilm(Connection connexion, int id,String titre, String realisateur, String date, String horaire, String theme, String synopsis, double prix) {
        try {
            String insertQuery = "INSERT INTO films (id, nom, realisateur, date, horaire, themes, synopsis, prix) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, titre);
            preparedStatement.setString(3, realisateur);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, horaire);
            preparedStatement.setString(6, theme);
            preparedStatement.setString(7, synopsis);
            preparedStatement.setDouble(8, prix);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "Le film a été ajouté avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du film : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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

        JLabel welcomeLabel = new JLabel("Vous êtes à présent sur la page Administrateur");
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
                JButton modifierButton = new JButton("Modifier");
                JButton supprimerButton = new JButton("Supprimer");

                // Ajout d'un écouteur d'événements pour le bouton "Modifier"
                modifierButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Récupérer l'id de l'utilisateur
                        modifierUtilisateur(connexion, idUtilisateur);
                    }
                });

                // Ajout d'un écouteur d'événements pour le bouton "Supprimer"
                supprimerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Supprimer l'utilisateur de la base de données et de l'affichage
                        supprimerUtilisateur(connexion, idUtilisateur);
                    }
                });

                // Ajout des boutons au panel avec les informations de l'utilisateur
                JPanel userPanelWithButtons = new JPanel(new FlowLayout());
                userPanelWithButtons.add(label);
                userPanelWithButtons.add(modifierButton);
                userPanelWithButtons.add(supprimerButton);
                userPanel.add(userPanelWithButtons);
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

    private void modifierUtilisateur(Connection connexion, int idUtilisateur) {
        try {
            // Récupérer les informations de l'utilisateur à partir de la base de données
            String query = "SELECT * FROM clients WHERE id=?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setInt(1, idUtilisateur);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Récupérer les informations de l'utilisateur
            String nomUtilisateur = "";
            String email = "";
            int typeUtilisateur = 0;

            if (resultSet.next()) {
                nomUtilisateur = resultSet.getString("nom");
                email = resultSet.getString("email");
                typeUtilisateur = resultSet.getInt("type");
            }

            // Créer une boîte de dialogue pour modifier les informations de l'utilisateur
            JTextField nomField = new JTextField(nomUtilisateur, 20);
            JTextField emailField = new JTextField(email, 20);
            JTextField typeField = new JTextField(String.valueOf(typeUtilisateur), 5);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier l'utilisateur",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Récupérer les nouvelles valeurs
                String nouveauNom = nomField.getText();
                String nouveauEmail = emailField.getText();
                int nouveauType = Integer.parseInt(typeField.getText());

                // Mettre à jour les informations de l'utilisateur dans la base de données
                String updateQuery = "UPDATE clients SET nom=?, email=?, type=? WHERE id=?";
                PreparedStatement updateStatement = connexion.prepareStatement(updateQuery);
                updateStatement.setString(1, nouveauNom);
                updateStatement.setString(2, nouveauEmail);
                updateStatement.setInt(3, nouveauType);
                updateStatement.setInt(4, idUtilisateur);
                updateStatement.executeUpdate();
                updateStatement.close();

                // Rafraîchir l'affichage des utilisateurs
                afficherUtilisateurs(connexion);

                JOptionPane.showMessageDialog(null, "Les informations de l'utilisateur ont été mises à jour !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'utilisateur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerUtilisateur(Connection connexion, int idUtilisateur) {
        try {
            // Supprimer l'utilisateur de la base de données
            String deleteQuery = "DELETE FROM clients WHERE id = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idUtilisateur);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Rafraîchir l'affichage des utilisateurs
            afficherUtilisateurs(connexion);

            JOptionPane.showMessageDialog(null, "L'utilisateur a été supprimé avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'utilisateur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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
                String imageFilm = resultSet.getString("image");
                String realFilm = resultSet.getString("realisateur");
                String themeFilm = resultSet.getString("themes");
                double prixFilm = resultSet.getDouble("prix");
                String infoFilm = "ID : " + idFilm + " | " + titreFilm + " | " + realFilm + " | " + themeFilm + " | " + prixFilm + "€";
                JLabel label = new JLabel(infoFilm);

                // Chargement de l'image du film
                ImageIcon icon = new ImageIcon("images/" + imageFilm + ".png");
                JLabel imageLabel = new JLabel(icon);

                // Créer un panneau pour regrouper les informations et l'image du film
                JPanel filmPanel = new JPanel();
                filmPanel.setLayout(new BoxLayout(filmPanel, BoxLayout.Y_AXIS));
                filmPanel.add(imageLabel);
                filmPanel.add(label);

                // Créer un bouton pour ajouter une disponibilité
                JButton addButton = new JButton("Ajouter une disponibilité");
                addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Boîte de dialogue pour saisir les informations sur la disponibilité
                        JTextField dateField = new JTextField(10); // Format "yyyy-mm-dd"
                        JTextField horaireField = new JTextField(5); // Format "hh:mm"
                        JTextField salleField = new JTextField(10);
                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Date (YYYY-MM-DD):"));
                        panel.add(dateField);
                        panel.add(new JLabel("Horaire (HH:MM):"));
                        panel.add(horaireField);
                        panel.add(new JLabel("Salle:"));
                        panel.add(salleField);
                        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter une disponibilité pour " + titreFilm,
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            // Récupérer les valeurs saisies
                            String date = dateField.getText();
                            String horaire = horaireField.getText();
                            String salle = salleField.getText();
                            // Insérer la disponibilité dans la base de données
                            insererDisponibilite(connexion, idFilm, date, horaire, salle);
                        }
                    }
                });
                filmPanel.add(addButton);
                userPanel.add(filmPanel);
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

    // Méthode pour insérer une disponibilité dans la base de données
    private void insererDisponibilite(Connection connexion, int idFilm, String date, String horaire, String salle) {
        try {
            String insertQuery = "INSERT INTO disponibilites_films (id_film, date, horaire, idSalle) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(insertQuery);
            preparedStatement.setInt(1, idFilm);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, horaire);
            preparedStatement.setString(4, salle);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "Disponibilité ajoutée avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la disponibilité : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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
        JTextField imageField = new JTextField(20); // Champ pour le nom de l'image
        JTextField synopsisField = new JTextField(50);
        JTextField themesField = new JTextField(20);
        JTextField prixField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Titre:"));
        panel.add(titreField);
        panel.add(new JLabel("Réalisateur:"));
        panel.add(realisateurField);
        panel.add(new JLabel("Nom de l'image (sans extension):"));
        panel.add(imageField);
        panel.add(new JLabel("Synopsis:"));
        panel.add(synopsisField);
        panel.add(new JLabel("Thèmes:"));
        panel.add(themesField);
        panel.add(new JLabel("Prix:"));
        panel.add(prixField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter un film",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Récupérer les valeurs saisies
            String titre = titreField.getText();
            String realisateur = realisateurField.getText();
            String imageNom = imageField.getText();
            String synopsis = synopsisField.getText();
            String themes = themesField.getText();
            double prix = Double.parseDouble(prixField.getText());

            // Vérifier si l'utilisateur a saisi un nom d'image
            if (!imageNom.isEmpty()) {
                try {
                    // Construire le chemin de l'image
                    String imagePath = imageNom + ".jpg";

                    // Générer un nouvel ID unique pour le film
                    int newId = generateNewFilmId(connexion);

                    // Insérer le nouveau film dans la base de données avec l'ID généré
                    insererFilm(connexion, newId, titre, realisateur, imagePath, synopsis, themes, prix);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la génération du nouvel ID : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez saisir le nom de l'image du film.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void insererFilm(Connection connexion, int id, String titre, String realisateur, String image, String synopsis, String themes, double prix) {
        try {
            String insertQuery = "INSERT INTO films (id, nom, realisateur, image, synopsis, themes, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, titre);
            preparedStatement.setString(3, realisateur);
            preparedStatement.setString(4, image);
            preparedStatement.setString(5, synopsis);
            preparedStatement.setString(6, themes);
            preparedStatement.setDouble(7, prix);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "Le film a été ajouté avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du film : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
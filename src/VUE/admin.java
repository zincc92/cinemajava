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
                Date dateFilm = resultSet.getDate("date");
                Time heureFilm = resultSet.getTime("horaire");
                String themeFilm = resultSet.getString("themes");
                String realFilm = resultSet.getString("realisateur");
                String synopsisFilm = resultSet.getString("synopsis");
                double prixFilm = resultSet.getDouble("prix");
                String infoFilm = "ID : "+idFilm+" | "+titreFilm + " | "+dateFilm+" "+heureFilm+" | "+themeFilm+" | "+realFilm+" | "+synopsisFilm+" | "+prixFilm+"€";
                JLabel label = new JLabel(infoFilm);
                JButton modifierButtonFilm = new JButton("Modifier");
                JButton supprimerButtonFilm = new JButton("Supprimer");

                // Ajout d'un écouteur d'événements pour le bouton "Modifier"
                modifierButtonFilm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Modifier un film dans la base de données
                        System.out.println("Bouton Modifier Film cliqué");
                        modifierFilm(connexion, idFilm);
                    }
                });

                // Ajout d'un écouteur d'événements pour le bouton "Supprimer"
                supprimerButtonFilm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Supprimer le film de la base de données et de l'affichage
                        System.out.println("Bouton Supprimer Film cliqué");
                        supprimerFilm(connexion, idFilm);
                    }
                });

                // Ajout des boutons au panel avec les informations de l'utilisateur
                JPanel userPanelWithButtons = new JPanel(new FlowLayout());
                userPanelWithButtons.add(label);
                userPanelWithButtons.add(modifierButtonFilm);
                userPanelWithButtons.add(supprimerButtonFilm);
                userPanel.add(label);
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


    private void modifierFilm(Connection connexion, int idFilm){
        try {
            // Récupérer les informations du film à partir de la base de données
            String query = "SELECT * FROM films WHERE id=?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setInt(1, idFilm);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Récupérer les informations du film
            String nomFilm = "";
            String realisateur = "";
            String theme = "";
            String synopsis = "";
            Date dateFilm = null;
            Time horaireFilm = null;
            Double prixFilm = 0.0;


            if (resultSet.next()) {
                nomFilm = resultSet.getString("nom");
                realisateur = resultSet.getString("realisateur");
                theme = resultSet.getString("themes");
                synopsis = resultSet.getString("synopsis");
                dateFilm = resultSet.getDate("date");
                horaireFilm = resultSet.getTime("horaire");
                prixFilm = resultSet.getDouble("prix");
            }

            // Créer une boîte de dialogue pour modifier les informations de l'utilisateur
            JTextField nomFilmField = new JTextField(nomFilm, 20);
            JTextField dateField = new JTextField(dateFilm != null ? dateFilm.toString() : "", 10); // S'assurer que la date n'est pas null avant de la convertir en String
            JTextField horaireField = new JTextField(horaireFilm != null ? horaireFilm.toString() : "", 5); // S'assurer que l'heure n'est pas null avant de la convertir en String
            JTextField themeField = new JTextField(theme, 20);
            JTextField realisateurField = new JTextField(realisateur, 20);
            JTextField synopsisField = new JTextField(synopsis, 50);
            JTextField prixFilmField = new JTextField(String.valueOf(prixFilm), 10);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Titre:"));
            panel.add(nomFilmField);
            panel.add(new JLabel("Date:"));
            panel.add(dateField);
            panel.add(new JLabel("Horaire:"));
            panel.add(horaireField);
            panel.add(new JLabel("Theme:"));
            panel.add(themeField);
            panel.add(new JLabel("Realisateur:"));
            panel.add(realisateurField);
            panel.add(new JLabel("Synopsis:"));
            panel.add(synopsisField);
            panel.add(new JLabel("Prix:"));
            panel.add(prixFilmField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier information sur le film",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Récupérer les nouvelles valeurs
                String nouveauNomFilm = nomFilmField.getText().isEmpty() ? nomFilm : nomFilmField.getText();
                String nouveauRealisateur = realisateurField.getText().isEmpty() ? realisateur : realisateurField.getText();
                String nouveauTheme = themeField.getText().isEmpty() ? theme : themeField.getText();
                String nouveauSynopsis = synopsisField.getText().isEmpty() ? synopsis : synopsisField.getText();
                String nouvelleDateStr = dateField.getText();
                String nouvelHoraireStr = horaireField.getText();
                String nouveauPrixStr = prixFilmField.getText();

                // Si les champs de date et d'heure sont vides, on garde leur valeur initiale à null
                Date nouvelleDate = nouvelleDateStr.isEmpty() ? dateFilm : Date.valueOf(nouvelleDateStr);
                Time nouvelHoraire = nouvelHoraireStr.isEmpty() ? horaireFilm : Time.valueOf(nouvelHoraireStr);

                // Convertir la chaîne de prix en double, garder la valeur initiale si la chaîne est vide
                double nouveauPrix = nouveauPrixStr.isEmpty() ? prixFilm : Double.parseDouble(nouveauPrixStr);


                // Mettre à jour les informations de l'utilisateur dans la base de données
                String updateQuery = "UPDATE films SET nom=?, date=?, horaire=?, themes=?, realisateur=?, synopsis=?, prix=? WHERE id=?";
                PreparedStatement updateStatement = connexion.prepareStatement(updateQuery);
                updateStatement.setString(1, nouveauNomFilm);
                updateStatement.setDate(2, nouvelleDate);
                updateStatement.setTime(3, nouvelHoraire);
                updateStatement.setString(4, nouveauTheme);
                updateStatement.setString(5, nouveauRealisateur);
                updateStatement.setString(6, nouveauSynopsis);
                updateStatement.setDouble(7, nouveauPrix);
                updateStatement.setInt(8, idFilm);
                updateStatement.executeUpdate();
                updateStatement.close();

                // Rafraîchir l'affichage des utilisateurs
                afficherFilms(connexion);

                JOptionPane.showMessageDialog(null, "Les informations du film ont été mises à jour !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification des informations du film : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerFilm(Connection connexion, int idFilm){
        try {
            // Supprimer le film de la base de données
            String deleteQuery = "DELETE FROM films WHERE id = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idFilm);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Rafraîchir l'affichage des utilisateurs
            afficherFilms(connexion);

            JOptionPane.showMessageDialog(null, "Le film a été supprimé avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du film : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
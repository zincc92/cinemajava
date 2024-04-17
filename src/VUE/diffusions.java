//diffusion bien

package VUE;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class diffusions extends JPanel {
    public diffusions() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10)); // GridLayout avec 3 colonnes et un espacement de 10 pixels

        // Récupérer les films depuis la base de données
        ArrayList<Film> films = getFilmsFromDatabase();

        // Créer un panneau pour chaque film
        for (Film film : films) {
            FilmPanel filmPanel = new FilmPanel(film);
            panel.add(filmPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel); // Ajouter le panneau dans un JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Toujours afficher la barre de défilement verticale
        add(scrollPane, BorderLayout.CENTER); // Ajouter le JScrollPane à la disposition principale
    }

    // Méthode pour récupérer les films depuis la base de données (à implémenter)
    private ArrayList<Film> getFilmsFromDatabase() {
        ArrayList<Film> films = new ArrayList<>();
        // Connexion à la base de données et récupération des films
        String url = "jdbc:mysql://localhost:3306/cinemaprojet";
        String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String motDePasse = ""; // Remplacez par votre mot de passe MySQL

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films"); // "films" est le nom de votre table de films

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String image = resultSet.getString("image");
                String synopsis = resultSet.getString("synopsis");
                String realisateur = resultSet.getString("realisateur");
                String themes = resultSet.getString("themes");
                double prix = resultSet.getDouble("prix");
                String date = resultSet.getString("date");
                String horaire = resultSet.getString("horaire");

                films.add(new Film(nom, image, synopsis, realisateur, themes, prix, date, horaire));
            }

            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }

    // Classe interne pour représenter un film sous forme de panneau
    public class FilmPanel extends JPanel {
        private Film film;
        private JLabel imageLabel;
        private JPanel detailsPanel;
        private JButton detailsButton;
        private JButton hideDetailsButton;

        public FilmPanel(Film film) {
            this.film = film;
            setLayout(new BorderLayout());

            // Création d'un panneau pour l'image et les boutons
            JPanel contentPanel = new JPanel(new BorderLayout());
            add(contentPanel, BorderLayout.CENTER);

            // Afficher l'image du film
            imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
            contentPanel.add(imageLabel, BorderLayout.CENTER);

            // Création du bouton "Voir les détails"
            detailsButton = new JButton("Voir les détails");
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleDetails(); // Action à effectuer lors du clic sur le bouton pour afficher/masquer les détails
                }
            });
            contentPanel.add(detailsButton, BorderLayout.SOUTH);

            // Création d'un panneau pour afficher les détails
            detailsPanel = new JPanel();
            detailsPanel.setLayout(new GridLayout(0, 1));
            detailsPanel.setVisible(false); // Masquer le panneau des détails par défaut
            detailsPanel.add(new JLabel("Réalisateur: " + film.getRealisateur()));
            detailsPanel.add(new JLabel("Thèmes: " + film.getThemes()));
            detailsPanel.add(new JLabel("Synopsis: " + film.getSynopsis()));
            detailsPanel.add(new JLabel("Prix: " + film.getPrix()));
            detailsPanel.add(new JLabel("Date: " + film.getDate()));
            detailsPanel.add(new JLabel("Horaire: " + film.getHoraire()));
            add(detailsPanel, BorderLayout.SOUTH);

            // Création du bouton "Cacher les détails"
            hideDetailsButton = new JButton("Cacher les détails");
            hideDetailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hideDetails(); // Action à effectuer lors du clic sur le bouton pour cacher les détails
                }
            });
            hideDetailsButton.setVisible(false); // Masquer le bouton "Cacher les détails" par défaut
            detailsPanel.add(hideDetailsButton);
        }

        // Méthode pour afficher ou masquer les détails du film
        private void toggleDetails() {
            // Afficher ou masquer le panneau des détails
            detailsPanel.setVisible(!detailsPanel.isVisible());
            // Afficher ou masquer le bouton "Cacher les détails" en conséquence
            hideDetailsButton.setVisible(detailsPanel.isVisible());
        }

        // Méthode pour cacher les détails du film
        private void hideDetails() {
            // Cacher le panneau des détails
            detailsPanel.setVisible(false);
            // Masquer le bouton "Cacher les détails"
            hideDetailsButton.setVisible(false);
        }
    }

    // Classe pour représenter un film
    public class Film {
        private String nom;
        private String image;
        private String synopsis;
        private String realisateur;
        private String themes;
        private double prix;
        private String date;
        private String horaire;

        public Film(String nom, String image, String synopsis, String realisateur, String themes, double prix, String date, String horaire) {
            this.nom = nom;
            this.image = image;
            this.synopsis = synopsis;
            this.realisateur = realisateur;
            this.themes = themes;
            this.prix = prix;
            this.date = date;
            this.horaire = horaire;
        }

        public String getNom() {
            return nom;
        }

        public String getImage() {
            return image;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public String getRealisateur() {
            return realisateur;
        }

        public String getThemes() {
            return themes;
        }

        public double getPrix() {
            return prix;
        }

        public String getDate() {
            return date;
        }

        public String getHoraire() {
            return horaire;
        }
    }
}

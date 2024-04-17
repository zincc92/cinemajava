package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class diffusions extends JPanel {
    private JPanel mainPanel;
    private JPanel detailPanel;
    private JButton backButton;
    private JButton reserveButton;

    public diffusions() {
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // GridLayout avec 3 colonnes et un espacement de 10 pixels

        // Récupérer les films depuis la base de données
        ArrayList<Film> films = getFilmsFromDatabase();

        // Créer un panneau pour chaque film
        for (Film film : films) {
            FilmPanel filmPanel = new FilmPanel(film);
            mainPanel.add(filmPanel);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel); // Ajouter le panneau dans un JScrollPane
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

    // Méthode pour afficher les détails d'un film
    private void showDetails(Film film) {
        detailPanel = new JPanel(new BorderLayout());

        // Création d'un panneau pour l'image et les détails
        JPanel contentPanel = new JPanel(new BorderLayout());
        detailPanel.add(contentPanel, BorderLayout.CENTER);

        // Afficher l'image du film
        JLabel imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        // Création du panneau pour les détails
        JPanel filmDetailsPanel = new JPanel();
        filmDetailsPanel.setLayout(new GridLayout(0, 1));
        filmDetailsPanel.add(new JLabel("Réalisateur: " + film.getRealisateur()));
        filmDetailsPanel.add(new JLabel("Thèmes: " + film.getThemes()));
        filmDetailsPanel.add(new JLabel("Synopsis: " + film.getSynopsis()));
        filmDetailsPanel.add(new JLabel("Prix: " + film.getPrix()));
        filmDetailsPanel.add(new JLabel("Date: " + film.getDate()));
        filmDetailsPanel.add(new JLabel("Horaire: " + film.getHoraire()));
        contentPanel.add(filmDetailsPanel, BorderLayout.SOUTH);

        // Ajout du bouton "Réserver"
        reserveButton = new JButton("Réserver");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre ici l'action à effectuer lors de la réservation
            }
        });
        backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Revenir à la vue principale
                removeAll();
                add(new JScrollPane(mainPanel), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        // Ajout du bouton "Retour" et "Réserver"
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Espacement entre les boutons
        buttonPanel.add(reserveButton);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.setPreferredSize(new Dimension(250, 50)); // Largeur: 100 pixels, Hauteur: 30 pixels


        reserveButton.setPreferredSize(new Dimension(300, 50)); // Largeur: 100 pixels, Hauteur: 30 pixels


        // Afficher les détails du film
        removeAll();
        add(new JScrollPane(detailPanel), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Classe interne pour représenter un film sous forme de panneau
    public class FilmPanel extends JPanel {
        private Film film;

        public FilmPanel(Film film) {
            this.film = film;
            setLayout(new BorderLayout());

            // Création d'un panneau pour l'image
            JPanel contentPanel = new JPanel(new BorderLayout());
            add(contentPanel, BorderLayout.CENTER);

            // Afficher l'image du film
            JLabel imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
            contentPanel.add(imageLabel, BorderLayout.CENTER);

            // Création du bouton "Plus d'informations"
            JButton detailsButton = new JButton("Plus d'informations");
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDetails(film); // Afficher les détails du film lorsque le bouton est cliqué
                }
            });
            contentPanel.add(detailsButton, BorderLayout.SOUTH);
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

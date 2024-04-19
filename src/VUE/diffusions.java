package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import MODELE.connexion;
import CONTROLLEUR.utilisateurControlleur;

public class diffusions extends JPanel {
    private JPanel mainPanel;
    private JPanel detailPanel;
    private JButton backButton;
    private JButton reserveButton;
    private JPanel reservationPanel;

    public diffusions(connexion session) {
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        // Récupérer les films depuis la base de données
        ArrayList<Film> films = getFilmsFromDatabase();

        // Créer un panneau pour chaque film
        for (Film film : films) {
            FilmPanel filmPanel = new FilmPanel(film);
            mainPanel.add(filmPanel);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }



    // Méthode pour récupérer les films depuis la base de données
    private ArrayList<Film> getFilmsFromDatabase() {
        ArrayList<Film> films = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/cinemaprojet";
        String utilisateur = "root";
        String motDePasse = "";

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films");

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String image = resultSet.getString("image");
                String synopsis = resultSet.getString("synopsis");
                String realisateur = resultSet.getString("realisateur");
                String themes = resultSet.getString("themes");
                double prix = resultSet.getDouble("prix");
                int idSalle = resultSet.getInt("idSalle");
                int id = resultSet.getInt("id");

                synopsis = synopsis.replace("\\n", System.lineSeparator());


                films.add(new Film(nom, image, synopsis, realisateur, themes, prix, idSalle, id));
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

        JPanel contentPanel = new JPanel(new BorderLayout());
        detailPanel.add(contentPanel, BorderLayout.CENTER);

        JLabel imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
        contentPanel.add(imageLabel, BorderLayout.WEST);

        JPanel filmDetailsPanel = new JPanel();
        filmDetailsPanel.setLayout(new GridLayout(0, 1));
        filmDetailsPanel.add(new JLabel("Réalisateur: " + film.getRealisateur()));
        filmDetailsPanel.add(new JLabel("Thèmes: " + film.getThemes()));
        filmDetailsPanel.add(new JLabel("Synopsis: " + film.getSynopsis()));
        filmDetailsPanel.add(new JLabel("Prix: " + film.getPrix()));

        contentPanel.add(filmDetailsPanel, BorderLayout.SOUTH);

        reserveButton = new JButton("Réserver");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationPanel(film, connexion.getSession()); // Afficher le panneau de réservation
            }
        });
        backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                add(new JScrollPane(mainPanel), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(reserveButton);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);


        removeAll();
        add(new JScrollPane(detailPanel), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Méthode pour récupérer le nom de la salle à partir de son ID
    private String getSalleName(int idSalle) {
        String salleName = "";
        String url = "jdbc:mysql://localhost:3306/cinemaprojet";
        String utilisateur = "root";
        String motDePasse = "";

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement statement = connexion.prepareStatement("SELECT s.numero FROM salles s INNER JOIN disponibilites_films df ON s.id = df.idSalle WHERE df.id_film = ?");
            statement.setInt(1, idSalle);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                salleName = resultSet.getString("numero");
            }

            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salleName;
    }


    // Méthode pour récupérer les disponibilités d'un film depuis la base de données
    private ArrayList<DisponibiliteFilm> getDisponibilitesFromDatabase(int idFilm) {
        ArrayList<DisponibiliteFilm> disponibilites = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/cinemaprojet";
        String utilisateur = "root";
        String motDePasse = "";

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement statement = connexion.prepareStatement("SELECT df.date, df.horaire, s.numero FROM disponibilites_films df INNER JOIN salles s ON df.idSalle = s.id WHERE df.id_film = ?");
            statement.setInt(1, idFilm);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                Time horaire = resultSet.getTime("horaire");
                String salle = resultSet.getString("numero");
                disponibilites.add(new DisponibiliteFilm(date, horaire, salle));
            }

            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disponibilites;
    }



    // Méthode pour afficher le panneau de réservation
    // Méthode pour afficher le panneau de réservation
    // Modifier la méthode showReservationPanel pour ajuster l'affichage des sessions
    private void showReservationPanel(Film film, connexion session) {
        removeAll(); // Supprimer les composants existants
        reservationPanel = new JPanel(new BorderLayout());

        // Récupérer les disponibilités du film depuis la base de données
        ArrayList<DisponibiliteFilm> disponibilites = getDisponibilitesFromDatabase(film.getId());

        // Créer un panneau pour afficher les sessions
        JPanel sessionsPanel = new JPanel(new GridLayout(0, 3, 5, 5)); // Réduire l'espacement entre les composants à 5 pixels

        // Ajouter chaque session au panneau
        for (DisponibiliteFilm disponibilite : disponibilites) {
            JPanel sessionPanel = new JPanel(new BorderLayout());
            sessionPanel.setPreferredSize(new Dimension(100, 150)); // Définir une taille préférée pour chaque session

            /*JLabel imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
            sessionPanel.add(imageLabel, BorderLayout.CENTER);*/
            JLabel salleLabel = new JLabel("Salle: " + disponibilite.getSalle());
            JLabel dateLabel = new JLabel("Date: " + disponibilite.getDate());
            JLabel horaireLabel = new JLabel("Horaire: " + disponibilite.getHoraire());


            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 5)); // Utilisation de GridLayout avec un espacement vertical de 5 pixels
            //infoPanel.add(imageLabel);
            infoPanel.add(salleLabel);
            infoPanel.add(dateLabel);
            infoPanel.add(horaireLabel);

            sessionPanel.add(infoPanel, BorderLayout.CENTER);


            sessionPanel.add(infoPanel, BorderLayout.CENTER);

            JButton choisirButton = new JButton("Choisir cette session");
            choisirButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    utilisateurControlleur.insertReservation(session, film.getId(), film.getPrix(), session.getUser().getEmail(), disponibilite.getDate(), disponibilite.getHoraire(), disponibilite.getSalle());
                    JOptionPane.showMessageDialog(null, "Session choisie : " + disponibilite.getDate() + " " + disponibilite.getHoraire() + " (Salle : " + disponibilite.getSalle() + ")");
                }
            });

            sessionPanel.add(choisirButton, BorderLayout.SOUTH);

            sessionsPanel.add(sessionPanel);
        }

        reservationPanel.add(new JScrollPane(sessionsPanel), BorderLayout.CENTER);

        // Ajouter le panneau de réservation à la fenêtre principale
        add(reservationPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }






    public class FilmPanel extends JPanel {
        private Film film;

        public FilmPanel(Film film) {
            this.film = film;
            setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel(new BorderLayout());
            add(contentPanel, BorderLayout.CENTER);

            JLabel imageLabel = new JLabel(new ImageIcon("image/" + film.getImage()));
            contentPanel.add(imageLabel, BorderLayout.CENTER);

            JButton detailsButton = new JButton("Plus d'informations");
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDetails(film);
                }
            });
            contentPanel.add(detailsButton, BorderLayout.SOUTH);
        }
    }
    public class DisponibiliteFilm {
        private Date date;
        private Time horaire;
        private String salle;

        public DisponibiliteFilm(Date date, Time horaire, String salle) {
            this.date = date;
            this.horaire = horaire;
            this.salle = salle;
        }

        // Getters et setters
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Time getHoraire() {
            return horaire;
        }

        public void setHoraire(Time horaire) {
            this.horaire = horaire;
        }

        public String getSalle() {
            return salle;
        }

        public void setSalle(String salle) {
            this.salle = salle;
        }
    }


    public class Film {
        private String nom;
        private String image;
        private String synopsis;
        private String realisateur;
        private String themes;
        private double prix;
        private int idSalle;
        private int id; // Ajouter l'attribut id


        public Film(String nom, String image, String synopsis, String realisateur, String themes, double prix, int idSalle, int id) {
            this.nom = nom;
            this.id = id;
            this.image = image;
            this.synopsis = synopsis;
            this.realisateur = realisateur;
            this.themes = themes;
            this.prix = prix;
            this.idSalle = idSalle;
        }

        public int getId() {
            return id;
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

        public int getIdSalle() {
            return idSalle;
        }

        public void setIdSalle(int idSalle) {
            this.idSalle = idSalle;
        }
    }
}

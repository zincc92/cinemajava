package VUE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import MODELE.connexion;
import MODELE.payer;
import CONTROLLEUR.utilisateurControlleur;
import MODELE.utilisateur;

public class diffusions extends JPanel {
    private JPanel mainPanel;
    private JPanel detailPanel;
    private JButton backButton;
    private JButton reserveButton;
    private JPanel reservationPanel;
    private connexion session;

    public diffusions(connexion session) {

        this.session = session;
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        // Récupérer les films depuis la base de données
        ArrayList<Film> films = getFilmsFromDatabase();

        // Créer un panneau pour chaque film
        for (Film film : films) {
            FilmPanel filmPanel = new FilmPanel(film, session);
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
        String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String motDePasse = ""; // Remplacez par votre mot de passe MySQL

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
    private void showDetails(Film film, MODELE.connexion connexion) {
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
                showReservationPanel(film); // Afficher le panneau de réservation
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
        String utilisateur = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String motDePasse = ""; // Remplacez par votre mot de passe MySQL

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
    private void showReservationPanel(Film film) {
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

            JLabel salleLabel = new JLabel("Salle: " + disponibilite.getSalle());
            JLabel dateLabel = new JLabel("Date: " + disponibilite.getDate());
            JLabel horaireLabel = new JLabel("Horaire: " + disponibilite.getHoraire());

            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 5)); // Utilisation de GridLayout avec un espacement vertical de 5 pixels
            infoPanel.add(salleLabel);
            infoPanel.add(dateLabel);
            infoPanel.add(horaireLabel);

            sessionPanel.add(infoPanel, BorderLayout.CENTER);

            JButton choisirButton = new JButton("Choisir cette session");
            choisirButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (MODELE.connexion.getSession() == null) {
                        // Afficher une boîte de dialogue pour saisir l'adresse e-mail
                        JTextField emailField = new JTextField(20);
                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Veuillez saisir votre adresse e-mail pour continuer:"));
                        panel.add(emailField);
                        int result = JOptionPane.showConfirmDialog(null, panel, "Connexion requise", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            // Récupérer l'adresse e-mail saisie
                            String email = emailField.getText();
                            // Créer un nouvel utilisateur avec l'adresse e-mail saisie et un âge de 30 ans
                            utilisateur newUser = new utilisateur("0", "Invité", email, "motdepasse", 30);
                            // Insérer le nouvel utilisateur dans la base de données
                            utilisateurControlleur.inscrireUtilisateur(newUser);
                            // Mettre à jour la session avec le nouvel utilisateur
                            int nbrPlace = 1;
                            int prix = 1;
                            // Vérifier l'âge du client


                            JTextField nbrPlaceTextField = new JTextField(String.valueOf(nbrPlace), 5);
                            panel.add(new JLabel("Nombre de place:"));
                            panel.add(nbrPlaceTextField);

                            int resultat = JOptionPane.showConfirmDialog(null, panel, "Choix nombre de place",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if (resultat == JOptionPane.OK_OPTION) {
                                int nouveauNbrdePlace = Integer.parseInt(nbrPlaceTextField.getText());
                                // Insérer la réservation dans la base de données avec le prix ajusté
                                System.out.println("L'utilisateur à décider de réserver "+nouveauNbrdePlace+"place(s)");
                                System.out.println("Prix du film : "+film.prix);

                                MODELE.payer payer = new MODELE.payer(film.prix, nouveauNbrdePlace, 30);

                                // Calculer le prix à payer en fonction du film choisi, du nombre de places et de l'âge de l'utilisateur
                                double prixAPayer = payer.calculerPrix();
                                int idResa = -1;

                                // Afficher le prix à payer à l'utilisateur
                                System.out.println("Prix à payer : " + prixAPayer);
                                JOptionPane.showMessageDialog(null,"Prix d'une place : "+film.prix+"\nNombre de place : "+nouveauNbrdePlace+"\nPrix à payer : "+prixAPayer);
                                // Vérifier le résultat du paiement
                                if (prixAPayer != 0) {
                                    // Le paiement a été effectué avec succès
                                    paiement paiement=new paiement();
                                    idResa = utilisateurControlleur.insertReservation(session, film.getId(), prixAPayer, nouveauNbrdePlace, email, disponibilite.getDate(), disponibilite.getHoraire(), disponibilite.getSalle());
                                    if (idResa != -1) {
                                        JOptionPane.showMessageDialog(null, "Votre réservation a été effectuée avec succès. Notez votre numéro de réservation pour le récupérer le jour du film : " + idResa, "Réservation réussie", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        // Gérer le cas où l'insertion de la réservation a échoué
                                        JOptionPane.showMessageDialog(null, "Erreur lors de la réservation. Veuillez réessayer.", "Erreur de réservation", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    // Le paiement a échoué, affichez un message d'erreur ou prenez d'autres mesures appropriées
                                    JOptionPane.showMessageDialog(null, "Le paiement a échoué. Veuillez réessayer plus tard.", "Erreur de paiement", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                    else {
                        int nbrPlace = 1;
                        int prix = 1;
                        // Vérifier l'âge du client


                        JTextField nbrPlaceTextField = new JTextField(String.valueOf(nbrPlace), 5);
                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Nombre de place:"));
                        panel.add(nbrPlaceTextField);

                        int resultat = JOptionPane.showConfirmDialog(null, panel, "Choix nombre de place",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (resultat == JOptionPane.OK_OPTION) {
                            int nouveauNbrdePlace = Integer.parseInt(nbrPlaceTextField.getText());
                            // Insérer la réservation dans la base de données avec le prix ajusté
                            System.out.println("L'utilisateur à décider de réserver "+nouveauNbrdePlace+"place(s)");
                            System.out.println("Prix du film : "+film.prix);
                            System.out.println("Age de l'utilisateur : "+session.getUser().getAge());

                            MODELE.payer payer = new MODELE.payer(film.prix, nouveauNbrdePlace, session.getUser().getAge());

                            // Calculer le prix à payer en fonction du film choisi, du nombre de places et de l'âge de l'utilisateur
                            double prixAPayer = payer.calculerPrix();

                            // Afficher le prix à payer à l'utilisateur
                            System.out.println("Prix à payer : " + prixAPayer);
                            JOptionPane.showMessageDialog(null,"Prix d'une place : "+film.prix+"\nNombre de place : "+nouveauNbrdePlace+"\nPrix à payer : "+prixAPayer);
                            // Vérifier le résultat du paiement
                            if (prixAPayer != 0) {
                                // Le paiement a été effectué avec succès
                                paiement paiement=new paiement();
                                utilisateurControlleur.insertReservation(session, film.getId(), prixAPayer, nouveauNbrdePlace, session.getUser().getEmail(), disponibilite.getDate(), disponibilite.getHoraire(), disponibilite.getSalle());
                            } else {
                                // Le paiement a échoué, affichez un message d'erreur ou prenez d'autres mesures appropriées
                                JOptionPane.showMessageDialog(null, "Le paiement a échoué. Veuillez réessayer plus tard.", "Erreur de paiement", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
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

        public FilmPanel(Film film, connexion session) {
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
                    showDetails(film, session);
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
package com.example;

import java.time.LocalDate;

public class Livre extends Article {

    // attributs
    private final String isbn;
    private final int nbPages;

    // constructeur
    public Livre(String description, double prixInitial, int nbExemplaires, String isbn, int nbPages) {
        super(description, prixInitial, nbExemplaires); // super = appelle le constructeur d'article
        String propre = normaliser(isbn);
        if (!propre.matches("\\d{13}")) { // \\d{13} = exactement 13 chiffres
            throw new IllegalArgumentException("ISBN invalide (13 chiffres attendus) : " + isbn);
        }
        if (nbPages <= 0) {
            throw new IllegalArgumentException("Nombre de pages invalide : " + nbPages);
        }
        this.isbn = propre;
        this.nbPages = nbPages;
    }

    // methodes
    public String getIsbn() {
        return isbn;
    }

    public int getNbPages() {
        return nbPages;
    }

    @Override
    public String getNumero() {
        return isbn;
    }

    // -50 % en avril, vaut aussi pour les manuels par héritage
    @Override
    public double calculerPrix(LocalDate date) {
        if (date.getMonthValue() == 4) {
            return getPrixInitial() * 0.5;
        }
        return getPrixInitial();
    }

    @Override
    public String toString() {
        return super.toString() + " | ISBN : " + isbn + " | " + nbPages + " pages";
    }
}

package com.example;

import java.time.LocalDate;

// abstract = on ne peut pas faire "new Article", seulement un livre, un manuel ou un magazine
public abstract class Article {

    // attributs
    private final String description;
    private final double prixInitial;
    private int nbExemplaires;

    // constructeur
    public Article(String description, double prixInitial, int nbExemplaires) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description vide");
        }
        if (prixInitial < 0) {
            throw new IllegalArgumentException("Prix négatif : " + prixInitial);
        }
        if (nbExemplaires < 0) {
            throw new IllegalArgumentException("Nombre d'exemplaires négatif : " + nbExemplaires);
        }
        this.description = description.trim();
        this.prixInitial = prixInitial;
        this.nbExemplaires = nbExemplaires;
    }

    // methodes

    // enlève espaces et tirets : "978 2 7073 5674 1" -> "9782707356741"
    static String normaliser(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("Numéro absent");
        }
        return numero.replaceAll("[\\s-]", "");
    }

    public String getDescription() {
        return description;
    }

    public double getPrixInitial() {
        return prixInitial;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    // méthode abstraite = pas de code ici, chaque sous-classe donne le sien (isbn ou issn)
    public abstract String getNumero();

    // compareTo > 0 : mon numéro vient après le sien dans l'ordre alphabétique
    public boolean placerApres(Article autre) {
        return getNumero().compareTo(autre.getNumero()) > 0;
    }

    public void ajouter(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("Quantité à ajouter invalide : " + quantite);
        }
        nbExemplaires += quantite;
    }

    // false si pas assez de stock, et dans ce cas on ne retire rien
    public boolean retirer(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("Quantité à retirer invalide : " + quantite);
        }
        if (quantite > nbExemplaires) {
            return false;
        }
        nbExemplaires -= quantite;
        return true;
    }

    // version avec date pour tester sans attendre avril
    public abstract double calculerPrix(LocalDate date);

    public double calculerPrix() {
        return calculerPrix(LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format("%s : %.2f EUR : %d ex.", description, prixInitial, nbExemplaires);
    }
}

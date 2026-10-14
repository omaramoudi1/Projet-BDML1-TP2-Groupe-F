package com.example;

import java.time.LocalDate;

public class Etablissement {

    // attributs
    private static final int MAX_ARTICLES = 100;
    private static final int MAX_BONS = 100;

    private final String nom;
    private final Article[] articles; // toujours trié par numéro
    private int nbArticles;
    private final BonDepot[] bons;    // toujours trié par date, le plus récent en dernier
    private int nbBons;

    // constructeur
    public Etablissement(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Nom de boutique vide");
        }
        this.nom = nom.trim();
        this.articles = new Article[MAX_ARTICLES];
        this.nbArticles = 0;
        this.bons = new BonDepot[MAX_BONS];
        this.nbBons = 0;
    }

    // methodes
    public String getNom() {
        return nom;
    }

    public int getNbArticles() {
        return nbArticles;
    }

    public int getNbBons() {
        return nbBons;
    }

    // recherche dichotomique : on regarde le milieu, on jette la moitié inutile, on recommence
    public Article rechercher(String numero) {
        String cle = Article.normaliser(numero);
        int bas = 0;
        int haut = nbArticles - 1;
        while (bas <= haut) {
            int milieu = (bas + haut) / 2;
            int cmp = articles[milieu].getNumero().compareTo(cle);
            if (cmp == 0) {
                return articles[milieu];
            }
            if (cmp < 0) {
                bas = milieu + 1;
            } else {
                haut = milieu - 1;
            }
        }
        return null;
    }

    // insertion triée : on décale vers la droite tant que la case d'avant doit passer après
    private boolean inserer(Article article) {
        if (nbArticles == articles.length || rechercher(article.getNumero()) != null) {
            return false;
        }
        int i = nbArticles;
        while (i > 0 && articles[i - 1].placerApres(article)) {
            articles[i] = articles[i - 1];
            i--;
        }
        articles[i] = article;
        nbArticles++;
        return true;
    }

    // surcharge = même nom "ajouter", java choisit selon les paramètres donnés

    // livre
    public boolean ajouter(String description, double prixInitial, int nbExemplaires,
                           String isbn, int nbPages) {
        return inserer(new Livre(description, prixInitial, nbExemplaires, isbn, nbPages));
    }

    // manuel
    public boolean ajouter(String description, double prixInitial, int nbExemplaires,
                           String isbn, int nbPages, String matiere, String niveauScolaire) {
        return inserer(new Manuel(description, prixInitial, nbExemplaires,
                                  isbn, nbPages, matiere, niveauScolaire));
    }

    // magazine
    public boolean ajouter(String description, double prixInitial, int nbExemplaires,
                           String issn, Periodicite periodicite, LocalDate datePublication) {
        return inserer(new Magazine(description, prixInitial, nbExemplaires,
                                    issn, periodicite, datePublication));
    }

    // exemplaires reçus, false si l'article n'existe pas
    public boolean ajouter(String numero, int quantite) {
        Article a = rechercher(numero);
        if (a == null) {
            return false;
        }
        a.ajouter(quantite);
        return true;
    }

    // exemplaires vendus, false si article absent ou stock insuffisant
    public boolean retirer(String numero, int quantite) {
        Article a = rechercher(numero);
        return a != null && a.retirer(quantite);
    }

    // enregistre un bon, rangé par date ; ne touche pas au stock
    public boolean ajouter(BonDepot bon) {
        if (bon == null || bon.getNbLignes() == 0 || nbBons == bons.length) {
            return false;
        }
        for (int k = 0; k < nbBons; k++) {
            if (bons[k] == bon || (bon.estNumerote() && bons[k].getNumeroBon() == bon.getNumeroBon())) {
                return false;
            }
        }
        bon.numeroter();
        int i = nbBons;
        while (i > 0 && bons[i - 1].getDateEmission().isAfter(bon.getDateEmission())) {
            bons[i] = bons[i - 1];
            i--;
        }
        bons[i] = bon;
        nbBons++;
        return true;
    }

    @Override
    public String toString() {
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(nom).append(" ===").append(nl);
        sb.append("--- Articles (").append(nbArticles).append(") ---").append(nl);
        for (int i = 0; i < nbArticles; i++) {
            sb.append(articles[i]).append(nl);
        }
        sb.append("--- Bons de dépôt (").append(nbBons).append(") ---").append(nl);
        for (int i = 0; i < nbBons; i++) {
            sb.append(bons[i]).append(nl);
        }
        return sb.toString();
    }
}

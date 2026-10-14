package com.example;

public class LigneDepot {

    // attributs
    private final String numero; // isbn ou issn
    private int nbExemplaires;

    // constructeur
    public LigneDepot(String numero, int nbExemplaires) {
        String propre = Article.normaliser(numero);
        if (!propre.matches("\\d{8}|\\d{13}")) { // 8 ou 13 chiffres
            throw new IllegalArgumentException("Référence invalide (ISBN 13 ou ISSN 8 chiffres) : " + numero);
        }
        if (nbExemplaires <= 0) {
            throw new IllegalArgumentException("Quantité déposée invalide : " + nbExemplaires);
        }
        this.numero = propre;
        this.nbExemplaires = nbExemplaires;
    }

    // methodes
    public String getNumero() {
        return numero;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    // pas de "public" = visible seulement dans le package tp2
    void augmenter(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("Quantité invalide : " + quantite);
        }
        nbExemplaires += quantite;
    }

    @Override
    public String toString() {
        return numero + " x " + nbExemplaires;
    }
}

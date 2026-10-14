package com.example;

public class Manuel extends Livre {

    // attributs
    private final String matiere;
    private final String niveauScolaire;

    // constructeur
    public Manuel(String description, double prixInitial, int nbExemplaires,
                  String isbn, int nbPages, String matiere, String niveauScolaire) {
        super(description, prixInitial, nbExemplaires, isbn, nbPages);
        if (matiere == null || matiere.isBlank()) {
            throw new IllegalArgumentException("Matière vide");
        }
        if (niveauScolaire == null || niveauScolaire.isBlank()) {
            throw new IllegalArgumentException("Niveau scolaire vide");
        }
        this.matiere = matiere.trim();
        this.niveauScolaire = niveauScolaire.trim();
    }

    // methodes
    public String getMatiere() {
        return matiere;
    }

    public String getNiveauScolaire() {
        return niveauScolaire;
    }

    @Override
    public String toString() {
        return super.toString() + " | Matière : " + matiere + " | Niveau : " + niveauScolaire;
    }
}

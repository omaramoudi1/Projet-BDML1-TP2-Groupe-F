package com.example;

import java.time.LocalDate;

public class BonDepot {

    // attributs
    private static final int MAX_LIGNES = 20;
    private static final int NON_NUMEROTE = 0;
    private static int compteur = 1; // static = un seul compteur partagé par tous les bons

    private int numeroBon;
    private final String telephone;
    private final LocalDate dateEmission;
    private final LigneDepot[] lignes;
    private int nbLignes;

    // constructeurs

    // cas normal : daté du jour, numéroté quand l'établissement l'accepte
    public BonDepot(String telephone) {
        this(telephone, LocalDate.now()); // this(...) = appelle l'autre constructeur
    }

    // date imposée, pour les tests
    public BonDepot(String telephone, LocalDate dateEmission) {
        this(NON_NUMEROTE, telephone, dateEmission);
    }

    // numéro imposé, pour relire le fichier au tp4
    public BonDepot(int numeroBon, String telephone, LocalDate dateEmission) {
        if (numeroBon < 0) {
            throw new IllegalArgumentException("Numéro de bon invalide : " + numeroBon);
        }
        String tel = telephone == null ? "" : telephone.replaceAll("[\\s.-]", "");
        if (!tel.matches("\\+?\\d{6,15}")) {
            throw new IllegalArgumentException("Téléphone invalide : " + telephone);
        }
        if (dateEmission == null) {
            throw new IllegalArgumentException("Date d'émission obligatoire");
        }
        this.numeroBon = numeroBon;
        this.telephone = tel;
        this.dateEmission = dateEmission;
        this.lignes = new LigneDepot[MAX_LIGNES];
        this.nbLignes = 0;
        if (numeroBon >= compteur) { // le compteur repart après le plus grand numéro lu
            compteur = numeroBon + 1;
        }
    }

    // methodes
    public int getNumeroBon() {
        return numeroBon;
    }

    public boolean estNumerote() {
        return numeroBon != NON_NUMEROTE;
    }

    // numéro donné à l'enregistrement : un bon refusé ne grille aucun numéro
    void numeroter() {
        if (!estNumerote()) {
            numeroBon = compteur++;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public LigneDepot getLigne(int i) {
        if (i < 0 || i >= nbLignes) {
            throw new IndexOutOfBoundsException("Ligne " + i + " inexistante");
        }
        return lignes[i];
    }

    // même référence déjà sur le bon : on cumule au lieu de créer une ligne
    public boolean ajouterLigne(String numero, int nbExemplaires) {
        LigneDepot nouvelle = new LigneDepot(numero, nbExemplaires);
        for (int i = 0; i < nbLignes; i++) {
            if (lignes[i].getNumero().equals(nouvelle.getNumero())) {
                lignes[i].augmenter(nbExemplaires);
                return true;
            }
        }
        if (nbLignes == lignes.length) {
            return false;
        }
        lignes[nbLignes++] = nouvelle;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bon n°").append(numeroBon)
          .append(" du ").append(dateEmission)
          .append(" | tél. ").append(telephone)
          .append(" | ").append(nbLignes).append(" article(s)");
        for (int i = 0; i < nbLignes; i++) {
            sb.append(System.lineSeparator()).append("    ").append(lignes[i]);
        }
        return sb.toString();
    }
}

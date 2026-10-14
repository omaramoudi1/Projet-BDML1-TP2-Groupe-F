package com.example;

import java.time.LocalDate;

public class Magazine extends Article {

    // attributs
    private final String issn;
    private final Periodicite periodicite;
    private final LocalDate datePublication;

    // constructeur
    public Magazine(String description, double prixInitial, int nbExemplaires,
                    String issn, Periodicite periodicite, LocalDate datePublication) {
        super(description, prixInitial, nbExemplaires);
        String propre = normaliser(issn);
        if (!propre.matches("\\d{8}")) {
            throw new IllegalArgumentException("ISSN invalide (8 chiffres attendus) : " + issn);
        }
        if (periodicite == null || datePublication == null) {
            throw new IllegalArgumentException("Périodicité et date de publication obligatoires");
        }
        this.issn = propre;
        this.periodicite = periodicite;
        this.datePublication = datePublication;
    }

    // methodes
    public String getIssn() {
        return issn;
    }

    public Periodicite getPeriodicite() {
        return periodicite;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    @Override
    public String getNumero() {
        return issn;
    }

    // plus de 2 périodes : -50 %, plus de 4 : -75 %
    // isAfter = strictement après, donc pile 2 mois ne compte pas encore
    @Override
    public double calculerPrix(LocalDate date) {
        LocalDate seuil50;
        LocalDate seuil75;
        switch (periodicite) {
            case HEBDOMADAIRE:
                seuil50 = datePublication.plusWeeks(2);
                seuil75 = datePublication.plusWeeks(4);
                break;
            case MENSUEL:
                seuil50 = datePublication.plusMonths(2);
                seuil75 = datePublication.plusMonths(4);
                break;
            default: // trimestriel : 1 trimestre = 3 mois
                seuil50 = datePublication.plusMonths(6);
                seuil75 = datePublication.plusMonths(12);
                break;
        }
        if (date.isAfter(seuil75)) {
            return getPrixInitial() * 0.25;
        }
        if (date.isAfter(seuil50)) {
            return getPrixInitial() * 0.5;
        }
        return getPrixInitial();
    }

    @Override
    public String toString() {
        return super.toString() + " | ISSN : " + issn + " | " + periodicite + " | publié le " + datePublication;
    }
}

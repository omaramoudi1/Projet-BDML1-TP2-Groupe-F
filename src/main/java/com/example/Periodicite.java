package com.example;

// enum = liste fermée de valeurs, impossible de taper "mensuelle" par erreur
public enum Periodicite {
    HEBDOMADAIRE("hebdomadaire"),
    MENSUEL("mensuel"),
    TRIMESTRIEL("trimestriel");

    // attributs
    private final String libelle;

    // constructeur
    Periodicite(String libelle) {
        this.libelle = libelle;
    }

    // methodes

    // "mensuel" -> MENSUEL, servira pour lire le fichier au tp4
    public static Periodicite depuisLibelle(String texte) {
        for (Periodicite p : values()) {
            if (p.libelle.equalsIgnoreCase(texte.trim())) {
                return p;
            }
        }
        throw new IllegalArgumentException("Périodicité inconnue : " + texte);
    }

    @Override
    public String toString() {
        return libelle;
    }
}

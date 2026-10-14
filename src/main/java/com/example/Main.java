package com.example;

import java.time.LocalDate;

public class Main {

    // methodes
    public static void main(String[] args) {
        Etablissement boutique = new Etablissement("Bouquins & Cie");

        // 4b : ajout des articles
        afficherTest("ajout livre", boutique.ajouter("La maison vide", 25.0, 10, "978 2 7073 5674 1", 752));
        afficherTest("ajout manuel", boutique.ajouter("Nouvelle grammaire du collège", 38.0, 31,
                "978 2 2104 4669 4", 512, "Français", "Collège"));
        afficherTest("ajout magazine", boutique.ajouter("Geo", 6.50, 20, "0020 8245",
                Periodicite.MENSUEL, LocalDate.of(2025, 8, 1)));
        afficherTest("doublon ISBN refusé", !boutique.ajouter("Copie", 1.0, 1, "9782707356741", 10));

        // 4c : rechercher, ajouter, retirer
        afficherTest("rechercher existant", boutique.rechercher("9782210446694") != null);
        afficherTest("rechercher inconnu -> null", boutique.rechercher("9999999999999") == null);
        afficherTest("ajouter 5 ex. Geo", boutique.ajouter("00208245", 5));
        afficherTest("Geo passe à 25 ex.", boutique.rechercher("00208245").getNbExemplaires() == 25);
        afficherTest("retirer 3 ex. livre", boutique.retirer("9782707356741", 3));
        afficherTest("retirer trop -> false", !boutique.retirer("9782707356741", 1000));
        afficherTest("stock intact après échec", boutique.rechercher("9782707356741").getNbExemplaires() == 7);

        // 4a : prix à dates fixes
        Article geo = boutique.rechercher("00208245");
        Article livre = boutique.rechercher("9782707356741");
        afficherTest("Geo 1,5 mois : plein tarif", geo.calculerPrix(LocalDate.of(2025, 9, 15)) == 6.50);
        afficherTest("Geo 3 mois : -50 %", geo.calculerPrix(LocalDate.of(2025, 11, 1)) == 3.25);
        afficherTest("Geo pile 2 mois : pas encore", geo.calculerPrix(LocalDate.of(2025, 10, 1)) == 6.50);
        afficherTest("Geo 5 mois : -75 %", geo.calculerPrix(LocalDate.of(2026, 1, 1)) == 1.625);
        afficherTest("livre en avril : -50 %", livre.calculerPrix(LocalDate.of(2026, 4, 23)) == 12.5);
        afficherTest("livre en mai : plein tarif", livre.calculerPrix(LocalDate.of(2026, 5, 1)) == 25.0);

        // 5 : bons de dépôt, ajoutés dans le désordre pour tester le tri par date
        BonDepot b1 = new BonDepot("061236547890", LocalDate.of(2025, 10, 10));
        b1.ajouterLigne("9782210446694", 10);
        b1.ajouterLigne("9782707356741", 20);
        b1.ajouterLigne("9782707356741", 2);
        afficherTest("cumul de ligne", b1.getNbLignes() == 2 && b1.getLigne(1).getNbExemplaires() == 22);

        BonDepot b2 = new BonDepot("0612345678", LocalDate.of(2025, 9, 1));
        b2.ajouterLigne("00208245", 4);

        BonDepot vide = new BonDepot("0611111111");
        afficherTest("bon vide refusé", !boutique.ajouter(vide));
        afficherTest("bon 1 enregistré", boutique.ajouter(b1));
        afficherTest("bon 2 enregistré", boutique.ajouter(b2));
        afficherTest("numéros continus 1 puis 2", b1.getNumeroBon() == 1 && b2.getNumeroBon() == 2);
        afficherTest("même bon deux fois refusé", !boutique.ajouter(b1));

        // try/catch = on attrape l'erreur au lieu de planter le programme
        try {
            boutique.ajouter("Faux", 5.0, 1, "123", 100);
            afficherTest("ISBN court refusé", false);
        } catch (IllegalArgumentException e) {
            afficherTest("ISBN court refusé (" + e.getMessage() + ")", true);
        }

        System.out.println();
        System.out.println(boutique); // appelle tout seul boutique.toString()
    }

    private static void afficherTest(String nom, boolean ok) {
        System.out.println((ok ? "[OK] " : "[ECHEC] ") + nom);
    }
}

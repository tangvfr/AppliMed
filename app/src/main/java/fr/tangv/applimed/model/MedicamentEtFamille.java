package fr.tangv.applimed.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MedicamentEtFamille {

    @Embedded
    private Medicament medicament;
    @Relation(
            parentColumn = "depotLegal",
            entityColumn = "code"
    )
    private Famille famille;

    /**
     * Permet de crée la relation en tre un medicament et une famille
     * @param medicament un médicament
     * @param famille la famille lier au medicament
     */
    public MedicamentEtFamille(Medicament medicament, Famille famille) {
        this.medicament = medicament;
        this.famille = famille;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

    public Famille getFamille() {
        return this.famille;
    }

}

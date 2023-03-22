package fr.tangv.applimed.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MedicamentEtComposants {

    @Embedded
    private Medicament medicament;
    @Relation(
            parentColumn = "depotLegal",
            entityColumn = "code",
            associateBy = @Junction(Constituer.class)
    )
    private List<Composant> composants;

    /**
     * Permet de construire la relation pour un medicament et plusieur de ses composants
     * @param medicament un medicament
     * @param composants list de composant
     */
    public MedicamentEtComposants(Medicament medicament, List<Composant> composants) {
        this.medicament = medicament;
        this.composants = composants;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

    public List<Composant> getComposants() {
        return this.composants;
    }

}

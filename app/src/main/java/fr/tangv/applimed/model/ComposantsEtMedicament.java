package fr.tangv.applimed.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ComposantsEtMedicament {

    @Embedded
    private Composant composant;

    @Relation(
            parentColumn = "code",
            entityColumn = "depotLegal",
            associateBy = @Junction(Constituer.class)
    )
    private List<Medicament> medicaments;

    /**
     * Permet de construire la relation pour un composant et plusieur des medicament en etant constituer
     * @param composant un composant
     * @param medicaments list de medicament
     */
    public ComposantsEtMedicament(Composant composant, List<Medicament> medicaments) {
        this.composant = composant;
        this.medicaments = medicaments;
    }

    public Composant getComposant() {
        return this.composant;
    }

    public List<Medicament> getMedicaments() {
        return this.medicaments;
    }

}
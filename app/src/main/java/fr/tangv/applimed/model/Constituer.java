package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        indices = {@Index(value = {"code", "depotLegal"}, unique = true)},
        primaryKeys = {"code", "depotLegal"}
)
public class Constituer {

    @NonNull
    private String code;
    @NonNull
    private String depotLegal;

    /**
     * Permet de construire la relation entre Composant et Medicament
     * @param code un code de composant
     * @param depotLegal un depot legal d'un medicament
     */
    public Constituer(@NonNull String code, @NonNull String depotLegal) {
        this.code = code;
        this.depotLegal = depotLegal;
    }

    @NonNull
    public String getCode() {
        return this.code;
    }

    @NonNull
    public String getDepotLegal() {
        return this.depotLegal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Constituer that = (Constituer) o;

        if (!code.equals(that.code)) return false;
        return depotLegal.equals(that.depotLegal);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + depotLegal.hashCode();
        return result;
    }

}

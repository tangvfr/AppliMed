package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Composant {

    @PrimaryKey
    @NonNull
    private String code;

    @NonNull
    private String libelle;

    /**
     * Permet de cosntruire un composant de médicament
     * @param code le code du composant
     * @param libelle le libellé du composant
     */
    public Composant(@NonNull String code, @NonNull String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    @NonNull
    public String getCode() {
        return this.code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(@NonNull String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composant composant = (Composant) o;

        return code.equals(composant.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

}

package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Famille {

    @PrimaryKey
    @NonNull
    private String code;

    @NonNull
    private String libelle;

    /**
     * Permet de cosntruire une fammile de médicament
     * @param code le code de la fammile
     * @param libelle le libellé de la fammile
     */
    public Famille(@NonNull String code, @NonNull String libelle) {
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
}

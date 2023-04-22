package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(indices = {@Index(value = {"libelle"}, unique = true)})
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

}

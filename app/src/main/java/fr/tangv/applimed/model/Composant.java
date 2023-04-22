package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(indices = {@Index(value = {"libelle"}, unique = true)})
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

}

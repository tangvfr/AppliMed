package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * Model qui représente l'entité famille dans la base de données
 */
@Data
@Entity(indices = {@Index(value = {"libelle"}, unique = true)})
public class Famille {

    /**
     * Code de la famille
     */
    @PrimaryKey
    @NonNull
    private String code;

    /**
     * Libellé de la famille
     */
    @NonNull
    private String libelle;

    /**
     * Permet de construire une fammile de médicament
     * @param code le code de la fammile
     * @param libelle le libellé de la fammile
     */
    public Famille(@NonNull String code, @NonNull String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

}

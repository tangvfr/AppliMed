package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * Model qui représente l'entité "composant" dans la base de données
 */
@Data
@Entity(indices = {@Index(value = {"libelle"}, unique = true)})
public class Composant {

    /**
     * Code du composant
     */
    @PrimaryKey
    @NonNull
    private String code;

    /**
     * Libellé du composant
     */
    @NonNull
    private String libelle;

    /**
     * Permet de construire un composant de médicament
     * @param code le code du composant
     * @param libelle le libellé du composant
     */
    public Composant(@NonNull String code, @NonNull String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

}

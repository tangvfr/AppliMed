package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Model qui représente l'association constituer dans la base de données
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Entity(
        primaryKeys = {"code", "depotLegal"},
        foreignKeys = {
                @ForeignKey(entity = Composant.class, parentColumns = {"code"}, childColumns = {"code"}),
                @ForeignKey(entity = Medicament.class, parentColumns = {"depotLegal"}, childColumns = {"depotLegal"})
        }
)
public class Constituer {

    /**
     * Code du composant qui constitue le médicament
     */
    @NonNull
    @ColumnInfo(index = true)
    private String code;

    /**
     * Dépôt légal du médicament
     */
    @NonNull
    @ColumnInfo(index = true)
    private String depotLegal;

}

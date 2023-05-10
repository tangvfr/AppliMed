package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.Data;

/**
 * Model qui représente l'entité "médicament" et contient l'association "appartenir" dans la base de données
 */
@Data
@Entity(
        foreignKeys = {
                @ForeignKey(entity = Famille.class, parentColumns = {"code"}, childColumns = {"famCode"})
        },
        indices = {@Index(value = {"nomCommercial"}, unique = true)}
)
public class Medicament {

    /**
     * Dépôt légal du médicament
     */
    @PrimaryKey
    @NonNull
    private String depotLegal;

    /**
     * Nom commercial du médicament
     */
    @NonNull
    private String nomCommercial;

    /**
     * Les effets du médicament
     */
    @NonNull
    private String effets;

    /**
     * Les contre-indications du médicament
     */
    @NonNull
    private String contreIndic;

    /**
     * Prix d'un échantillon du médicament
     */
    private Double prixEchantillion;

    /**
     * Nombre de médicament en stock
     */
    private Integer stocks;

    /**
     * Code famille de la famille du médicament
     */
    @NonNull
    @ColumnInfo(index = true)
    private String famCode;

    /**
     * Permet de construire un médicament vide/nulle
     */
    @Ignore
    public Medicament() {}

    /**
     * Permet de construire un médicament
     * @param depotLegal un depot dégal
     * @param nomCommercial un nom commercial
     * @param effets les effets
     * @param contreIndic les contre indication
     * @param prixEchantillion prix de l'echantillion
     * @param stocks stock
     * @param famCode code de la famille
     */
    public Medicament(@NonNull String depotLegal, @NonNull String nomCommercial,
                      @NonNull String effets, @NonNull String contreIndic, Double prixEchantillion,
                      Integer stocks, @NonNull String famCode
    ) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.effets = effets;
        this.contreIndic = contreIndic;
        this.prixEchantillion = prixEchantillion;
        this.stocks = stocks;
        this.famCode = famCode;
    }

}

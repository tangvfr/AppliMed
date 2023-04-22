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

@Data
@Entity(
        foreignKeys = {
                @ForeignKey(entity = Famille.class, parentColumns = {"code"}, childColumns = {"famCode"})
        },
        indices = {@Index(value = {"nomCommercial"}, unique = true)}
)
public class Medicament {

    @PrimaryKey
    @NonNull
    private String depotLegal;
    @NonNull
    private String nomCommercial;
    @NonNull
    private String effets;
    @NonNull
    private String contreIndic;
    private Double prixEchantillion;
    private Integer stocks;
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

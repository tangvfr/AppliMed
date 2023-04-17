package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.Data;

@Data
@Entity(
        foreignKeys = {
                @ForeignKey(entity = Famille.class, parentColumns = {"code"}, childColumns = {"famCode"})
        }
)
public class Medicament {

    @PrimaryKey
    @NonNull
    private String depotLegal;
    @NonNull
    private String nomCommercial;
    @NonNull
    private String composittion;
    @NonNull
    private String effets;
    @NonNull
    private String contreIndic;
    @NonNull
    private Double prixEchantillion;
    @NonNull
    private Integer stocks;
    @NonNull
    @ColumnInfo(index = true)
    private String famCode;

    /**
     * Permet de construire un médicament
     * @param depotLegal un depot dégal
     * @param nomCommercial un nom commercial
     * @param composittion une composition
     * @param effets les effets
     * @param contreIndic les contre indication
     * @param prixEchantillion prix de l'echantillion
     * @param stocks stock
     * @param famCode code de la famille
     */
    public Medicament(@NonNull String depotLegal, @NonNull String nomCommercial, @NonNull String composittion,
                      @NonNull String effets, @NonNull String contreIndic, @NonNull Double prixEchantillion,
                      @NonNull Integer stocks, @NonNull String famCode
    ) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.composittion = composittion;
        this.effets = effets;
        this.contreIndic = contreIndic;
        this.prixEchantillion = prixEchantillion;
        this.stocks = stocks;
        this.famCode = famCode;
    }

}

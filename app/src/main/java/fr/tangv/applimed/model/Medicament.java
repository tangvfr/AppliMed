package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(
        indices = {@Index(value = {"famCode"})}
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

    @NonNull
    public String getDepotLegal() {
        return this.depotLegal;
    }

    public void setDepotLegal(@NonNull String depotLegal) {
        this.depotLegal = depotLegal;
    }

    @NonNull
    public String getNomCommercial() {
        return this.nomCommercial;
    }

    public void setNomCommercial(@NonNull String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    @NonNull
    public String getComposittion() {
        return this.composittion;
    }

    public void setComposittion(@NonNull String composittion) {
        this.composittion = composittion;
    }

    @NonNull
    public String getEffets() {
        return this.effets;
    }

    public void setEffets(@NonNull String effets) {
        this.effets = effets;
    }

    @NonNull
    public String getContreIndic() {
        return this.contreIndic;
    }

    public void setContreIndic(@NonNull String contreIndic) {
        this.contreIndic = contreIndic;
    }

    @NonNull
    public Double getPrixEchantillion() {
        return this.prixEchantillion;
    }

    public void setPrixEchantillion(@NonNull Double prixEchantillion) {
        this.prixEchantillion = prixEchantillion;
    }

    @NonNull
    public Integer getStocks() {
        return this.stocks;
    }

    public void setStocks(@NonNull Integer stocks) {
        this.stocks = stocks;
    }

    @NonNull
    public String getFamCode() {
        return this.famCode;
    }

    public void setFamCode(@NonNull String famCode) {
        this.famCode = famCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicament med = (Medicament) o;

        return depotLegal.equals(med.depotLegal);
    }

    @Override
    public int hashCode() {
        return depotLegal.hashCode();
    }
}

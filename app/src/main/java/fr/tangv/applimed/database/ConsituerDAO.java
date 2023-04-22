package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.RoomWarnings;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Constituer;

@Dao
public interface ConsituerDAO {

    @Insert
    public void insertConstituer(Constituer constituer);

    @Insert
    public void insertConstituers(Constituer... constituer);

    public default void insertConstituers(String depotLegal, Set<Composant> composants) {
        Constituer[] constitue = new Constituer[composants.size()];
        int i = 0;

        for (Composant comp : composants) {
            constitue[i] = new Constituer(comp.getCode(), depotLegal);
            i++;
        }

        this.insertConstituers(constitue);
    }

    @Query("UPDATE constituer SET code=:newCompCode WHERE code=:oldCompCode")
    public void updateCompCode(String oldCompCode, String newCompCode);

    @Query("SELECT * FROM constituer WHERE code=:compCode;")
    public List<Constituer> findAllConsituerByComposant(String compCode);

    @Query("DELETE FROM constituer WHERE depotLegal=:depotLegal;")
    public void deleteConsituerByDepotLegal(String depotLegal);

    @Query("SELECT DISTINCT cp.code, cp.libelle FROM composant cp, constituer ct WHERE cp.code=ct.code AND ct.depotLegal=:depotLegal;")
    public List<Composant> findComposantByDepot(String depotLegal);

}

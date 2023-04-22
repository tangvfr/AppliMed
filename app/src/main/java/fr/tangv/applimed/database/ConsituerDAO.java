package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.RoomWarnings;

import java.util.List;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Constituer;

@Dao
public interface ConsituerDAO {

    @Insert
    public void insertConstituer(Constituer constituer);

    @Insert
    public void insertConstituers(Constituer... constituer);

    @Query("UPDATE constituer SET code=:newCompCode WHERE code=:oldCompCode")
    public void updateCompCode(String oldCompCode, String newCompCode);

    @Query("SELECT * FROM constituer WHERE code=:compCode;")
    public List<Constituer> findAllConsituerByComposant(String compCode);

    @Query("DELETE FROM constituer WHERE depotLegal=:depotLegal;")
    public void deleteConsituerByDepotLegal(String depotLegal);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM composant cp, constituer ct WHERE cp.code=ct.code AND ct.depotLegal=:depotLegal;")
    public List<Composant> findComposantByDepot(String depotLegal);

}

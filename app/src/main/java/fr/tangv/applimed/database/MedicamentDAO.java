package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Medicament;

@Dao
public interface MedicamentDAO {

    @Insert
    public void insertMedicament(Medicament med);

    @Insert
    public void insertMedicaments(Medicament... meds);

    @Delete
    public void deleteMedicament(Medicament med);

    @Query("DELETE FROM medicament WHERE depotLegal=:depotLegal;")
    public void deleteMedicament(String depotLegal);

    @Query("SELECT * FROM medicament WHERE depotLegal=:depotLegal;")
    public Medicament findMedicament(String depotLegal);

    @Query("SELECT * FROM medicament WHERE nomCommercial=:name;")
    public Medicament findMedicamentByName(String name);

    @Query("SELECT * FROM medicament WHERE famCode=:famCode;")
    public List<Medicament> findAllMedicamentsByFamille(String famCode);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM medicament m, constituer c WHERE m.depotLegal=c.depotLegal AND c.code=:compCode")
    public List<Medicament> findAllMedicamentsByComposant(String compCode);

    @Query("UPDATE medicament SET famCode=:newFamCode WHERE famCode=:oldFamCode")
    public void updateFamCode(String oldFamCode, String newFamCode);

    @Query("SELECT * FROM medicament")
    public List<Medicament> findAllMedicaments();

    @Update
    public void updateMedicament(Medicament med);

    @Update
    public void updateMedicaments(Medicament... meds);

}

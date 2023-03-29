package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Famille;
import fr.tangv.applimed.model.Medicament;
import fr.tangv.applimed.model.MedicamentEtComposants;
import fr.tangv.applimed.model.MedicamentEtFamille;

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

    @Query("SELECT * FROM medicament WHERE famCode=:famCode;")
    public List<Medicament> findAllMedicamentsByFamille(String famCode);

    public default List<Medicament> findAllMedicamentsByFamille(Famille famille) {
        return this.findAllMedicamentsByFamille(famille.getCode());
    }

    @Query("SELECT * FROM medicament")
    public List<Medicament> findAllMedicaments();

    @Update
    public void updateMedicament(Medicament med);

    @Update
    public void updateMedicaments(Medicament... meds);

    @Transaction
    @Query("SELECT * FROM medicament WHERE depotLegal=:depotLegal;")
    public MedicamentEtComposants findMedicamentComp(String depotLegal);

    @Transaction
    @Query("SELECT * FROM medicament")
    public List<MedicamentEtComposants> findAllMedicamentComps();

    @Transaction
    @Query("SELECT * FROM medicament WHERE depotLegal=:depotLegal;")
    public MedicamentEtFamille findMedicamentEtFamille(String depotLegal);

    @Transaction
    @Query("SELECT * FROM medicament")
    public List<MedicamentEtFamille> findAllMedicamentEtFamilles();

}

package fr.tangv.applimed.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Famille;

@Dao
public interface FamilleDAO {

    @Insert
    public void insertFamille(Famille fam);

    @Insert
    public void insertFamilles(Famille... fams);

    @Delete
    public void deleteFamille(Famille fam);

    @Query("DELETE FROM famille WHERE code=:code;")
    public void deleteFamille(String code);

    @Query("SELECT * FROM famille WHERE code=:code;")
    public Famille findFamille(String code);

    @Query("SELECT * FROM famille WHERE libelle=:lib;")
    public Famille findFamilleByLib(String lib);

    @Query("SELECT * FROM famille")
    public List<Famille> findAllFamilles();

    @Query("SELECT libelle FROM famille")
    public String[] findAllLibFamilles();

    @Update
    public void updateFamille(Famille fam);

    @Update
    public void updateFamilles(Famille... fams);

}

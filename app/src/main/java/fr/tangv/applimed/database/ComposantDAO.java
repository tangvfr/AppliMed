package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Composant;

@Dao
public interface ComposantDAO {

    @Insert
    public void insertComposant(Composant comp);

    @Insert
    public void insertComposants(Composant... comps);

    @Delete
    public void deleteComposant(Composant comp);

    @Query("DELETE FROM composant WHERE code=:code;")
    public void deleteComposant(String code);

    @Query("SELECT * FROM composant WHERE code=:code;")
    public Composant findComposant(String code);

    @Query("SELECT * FROM composant WHERE libelle=:lib;")
    public Composant findComposantByLib(String lib);

    @Query("SELECT * FROM composant")
    public List<Composant> findAllComposants();

    @Query("SELECT libelle FROM composant")
    public String[] findAlllibComposants();

    @Update
    public void updateComposant(Composant comp);

    @Update
    public void updateComposants(Composant... comps);

   /*@Transaction
    @Query("SELECT * FROM composant WHERE code=:code;")
    public ComposantsEtMedicament findComposantMed(String code);

    @Transaction
    @Query("SELECT * FROM composant")
    public List<ComposantsEtMedicament> findAllComposantMeds();*/

}
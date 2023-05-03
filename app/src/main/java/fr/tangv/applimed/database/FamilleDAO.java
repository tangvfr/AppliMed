package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Famille;

/**
 * Permet de gérer la base de données avec les models de famille de médicament
 */
@Dao
public interface FamilleDAO {

    /**
     * Permet d'ajouter à la bd une famille
     * @param fam une famille de médicament
     */
    @Insert
    public void insertFamille(Famille fam);

    /**
     * Permet d'ajouter à la bd des familles
     * @param fams des familles de médicament
     */
    @Insert
    public void insertFamilles(Famille... fams);

    /**
     * Permet de retirer de la bd une famille
     * @param fam une famille à retirer
     */
    @Delete
    public void deleteFamille(Famille fam);

    /**
     * Permet de retirer de la bd une famille
     * @param code le code d'une famille à retirer
     */
    @Query("DELETE FROM famille WHERE code=:code;")
    public void deleteFamille(String code);

    /**
     * Permet de récupérer une famille par son code dans la db
     * @param code code de la famille à récupérer
     * @return la famille si trouvé sinon null
     */
    @Query("SELECT * FROM famille WHERE code=:code;")
    public Famille findFamille(String code);

    /**
     * Permet de récupérer une famille par son libellé dans la db
     * @param lib libellé de la famille à récupérer
     * @return la famille si trouvé sinon null
     */
    @Query("SELECT * FROM famille WHERE libelle=:lib;")
    public Famille findFamilleByLib(String lib);

    /**
     * Permet de récupérer toutes les familles de médicament
     * @return liste des familles de médicaments
     */
    @Query("SELECT * FROM famille")
    public List<Famille> findAllFamilles();

    /**
     * Permet de récupérer tous les libellés de familles
     * @return liste des libellés de familles de médicaments
     */
    @Query("SELECT libelle FROM famille")
    public String[] findAllLibFamilles();

    /**
     * Permet de modifier une famille dans la db
     * @param fam une famille à modifier
     */
    @Update
    public void updateFamille(Famille fam);

    /**
     * Permet de modifier des familles dans la db
     * @param fams les familles à modifier
     */
    @Update
    public void updateFamilles(Famille... fams);

}

package fr.tangv.applimed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.tangv.applimed.model.Composant;

/**
 * Permet de gérer la base de données avec les models de composant de médicaments
 */
@Dao
public interface ComposantDAO {

    /**
     * Permet d'ajouter à la bd un composant
     * @param comp composant à ajouter
     */
    @Insert
    public void insertComposant(Composant comp);

    /**
     * Permet d'ajouter à la bd des composants
     * @param comps composants à ajouter
     */
    @Insert
    public void insertComposants(Composant... comps);

    /**
     * Permet de retirer de la bd un composants
     * @param comp composant à retirer
     */
    @Delete
    public void deleteComposant(Composant comp);

    /**
     * Permet de retirer de la bd un composants
     * @param code le code du composant à retirer
     */
    @Query("DELETE FROM composant WHERE code=:code;")
    public void deleteComposant(String code);

    /**
     * Permet de récupérer dans la bd un composant par son code
     * @param code un code de composant
     * @return le composant si trouvé sinon null
     */
    @Query("SELECT * FROM composant WHERE code=:code;")
    public Composant findComposant(String code);

    /**
     * Permet de récupérer dans la bd un composant par son libellé
     * @param lib un libellé de composant
     * @return le composant si trouvé sinon null
     */
    @Query("SELECT * FROM composant WHERE libelle=:lib;")
    public Composant findComposantByLib(String lib);

    /**
     * Permet de récupérer tous les composants dans la db
     * @return liste de tous les composants
     */
    @Query("SELECT * FROM composant")
    public List<Composant> findAllComposants();

    /**
     * Permet de récupérer le libellé de tous les composants dans la db
     * @return liste de tous les libellés de composant
     */
    @Query("SELECT libelle FROM composant")
    public String[] findAlllibComposants();

    /**
     * Permet de modifier un composant
     * @param comp un composant à modifier
     */
    @Update
    public void updateComposant(Composant comp);

    /**
     * Permet de modifier des composants
     * @param comps des composants à modifier
     */
    @Update
    public void updateComposants(Composant... comps);

}
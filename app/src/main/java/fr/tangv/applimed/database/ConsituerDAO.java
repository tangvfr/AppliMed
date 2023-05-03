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

/**
 * Permet de gérer la base de données avec les models de la constitution des médicaments
 */
@Dao
public interface ConsituerDAO {

    /**
     * Permet d'ajouter un composant à un medicament
     * @param constituer une constitution
     */
    @Insert
    public void insertConstituer(Constituer constituer);

    /**
     * Permet d'ajouter plusieurs composants à des medicaments
     * @param constituers des constitutions
     */
    @Insert
    public void insertConstituers(Constituer... constituers);

    /**
     * Permet d'ajouter plusieurs composants à un medicament
     * @param depotLegal un depot légale de médicament
     * @param composants un set de composants
     */
    public default void insertConstituers(String depotLegal, Set<Composant> composants) {
        Constituer[] constitue = new Constituer[composants.size()];
        int i = 0;

        for (Composant comp : composants) {
            constitue[i] = new Constituer(comp.getCode(), depotLegal);
            i++;
        }

        this.insertConstituers(constitue);
    }

    /**
     * Permet de remplacer un code composant par un autre
     * @param oldCompCode code composant remplacer
     * @param newCompCode code composant remplacent
     */
    @Query("UPDATE constituer SET code=:newCompCode WHERE code=:oldCompCode")
    public void updateCompCode(String oldCompCode, String newCompCode);

    /**
     * Permet de récupérer toutes les compositions contenant un composant
     * @param compCode un code de composant
     * @return liste des constitutions contenant le composant
     */
    @Query("SELECT * FROM constituer WHERE code=:compCode;")
    public List<Constituer> findAllConsituerByComposant(String compCode);

    /**
     * Permet de supprimer des constitutions qui concerne un médicament
     * @param depotLegal depot legal d'un médicament
     */
    @Query("DELETE FROM constituer WHERE depotLegal=:depotLegal;")
    public void deleteConsituerByDepotLegal(String depotLegal);

    /**
     * Permet de récupérer tous les composants contenus dans un médicament
     * @param depotLegal le depot legal d'un medicament
     * @return liste des composants du médicament
     */
    @Query("SELECT DISTINCT cp.code, cp.libelle FROM composant cp, constituer ct WHERE cp.code=ct.code AND ct.depotLegal=:depotLegal;")
    public List<Composant> findComposantByDepot(String depotLegal);

}

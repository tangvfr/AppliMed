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

/**
 * Permet de gérer la base de données avec les models de médicament
 */
@Dao
public interface MedicamentDAO {

    /**
     * Permet d'ajouter dans la base de donnée un médicament
     * @param med un médicament à ajouter
     */
    @Insert
    public void insertMedicament(Medicament med);

    /**
     * Permet d'ajouter dans la base de donnée des médicaments
     * @param meds liste des médicaments à ajouter
     */
    @Insert
    public void insertMedicaments(Medicament... meds);

    /**
     * Permet de supprimer un médicament de la base de données
     * @param med le médicament à supprimer
     */
    @Delete
    public void deleteMedicament(Medicament med);

    /**
     * Permet de supprimer un médicament de la base de données par son dépôt légale
     * @param depotLegal le dépôt légal du médicament à supprimer
     */
    @Query("DELETE FROM medicament WHERE depotLegal=:depotLegal;")
    public void deleteMedicament(String depotLegal);

    /**
     * Permet de recherche un médicament dans la base de données par son dépôt légale
     * @param depotLegal dépôt légal du médicament rechercher
     * @return le médicament si trouvé, sinon nulle
     */
    @Query("SELECT * FROM medicament WHERE depotLegal=:depotLegal;")
    public Medicament findMedicament(String depotLegal);

    /**
     * Permet de recherche un médicament dans la base de données par son libellé
     * @param name libellé du médicament rechercher
     * @return le médicament si trouvé, sinon nulle
     */
    @Query("SELECT * FROM medicament WHERE nomCommercial=:name;")
    public Medicament findMedicamentByName(String name);

    /**
     * Permet de rechercher des médicaments dans la base de données grâce à un code famille
     * @param famCode un code de famille
     * @return liste des médicaments fessant partie de la famille correspondent au code famille
     */
    @Query("SELECT * FROM medicament WHERE famCode=:famCode;")
    public List<Medicament> findAllMedicamentsByFamille(String famCode);

    /**
     * Permet de rechercher des médicaments dans la base de données grâce à un code de composant
     * @param compCode un code de composant
     * @return liste des médicaments contenant le composant correspondant au code de composant
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM medicament m, constituer c WHERE m.depotLegal=c.depotLegal AND c.code=:compCode")
    public List<Medicament> findAllMedicamentsByComposant(String compCode);

    /**
     * Permet de rechercher tous les médicaments dans la base de données
     * @return liste de tous les médicaments
     */
    @Query("SELECT * FROM medicament")
    public List<Medicament> findAllMedicaments();

    /**
     * Permet de remplacer un code de famille par un autre pour tous les médicaments
     * @param oldFamCode code famille remplacer
     * @param newFamCode code famille remplaçant
     */
    @Query("UPDATE medicament SET famCode=:newFamCode WHERE famCode=:oldFamCode")
    public void updateFamCode(String oldFamCode, String newFamCode);

    /**
     * Permet de mettre à jour un médicament
     * @param med le médicament à mettre à jour
     */
    @Update
    public void updateMedicament(Medicament med);

    /**
     * Permet de mettre à jour des médicaments
     * @param meds liste des médicaments à mettre à jour
     */
    @Update
    public void updateMedicaments(Medicament... meds);

}

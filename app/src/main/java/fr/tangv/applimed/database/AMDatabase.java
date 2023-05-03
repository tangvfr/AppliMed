package fr.tangv.applimed.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Constituer;
import fr.tangv.applimed.model.Famille;
import fr.tangv.applimed.model.Medicament;

/**
 * Permet de créer la connexion avec la base de donné via Room et d'obtenir les DAO pour interagir avec les models
 */
@Database(version = 1,
        /*autoMigrations = {
            @AutoMigration(
                    from = 1,
                    to = 2,
                    spec = AMDatabase.AutoMigrationV1_TO_2.class
            )
        },*/
        entities = {
            Composant.class,
            Famille.class,
            Constituer.class,
            Medicament.class
        }
)
public abstract class AMDatabase extends RoomDatabase {

    /*@RenameColumn(fromColumnName = "a", toColumnName = "example", tableName = "atable")
    static class AutoMigrationV1_TO_2 implements AutoMigrationSpec {}*/

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            db.execSQL("SQL CODE");
        }
    };*/

    private static AMDatabase INSTANCE = null;

    /**
     * Permet d'obtenir une instance de la base de données, si elle n'existe pas elle sera créée.
     * @param context un context d'application
     * @return La connexion à la base de donnée
     */
    public static AMDatabase getInstance(Context context) {
        synchronized (AMDatabase.class) {
            if (AMDatabase.INSTANCE == null) {
                AMDatabase.INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AMDatabase.class,
                                "db_med_app"
                        )
                        //charger la base par defaut
                        .createFromAsset("prepackage1_v1.db")
                        //migrations
                        /*.addMigrations(
                            AMDatabase.MIGRATION_1_2
                        )*/
                        //autoriser requet sur la thread principal
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return AMDatabase.INSTANCE;
    }

    /**
     * Permet de récupérer le DAO pour les composants
     * @return le DAO des composants qui est connecté à la BD
     */
    public abstract ComposantDAO getComposantDAO();
    /**
     * Permet de récupérer le DAO pour les familles
     * @return le DAO des familles qui est connecté à la BD
     */
    public abstract FamilleDAO getFamilleDAO();
    /**
     * Permet de récupérer le DAO pour les médicaments
     * @return le DAO des médicaments qui est connecté à la BD
     */
    public abstract MedicamentDAO getMedicamentDAO();
    /**
     * Permet de récupérer le DAO pour la constitution des médicaments
     * @return le DAO des constitutions de médicament qui est connecté à la BD
     */
    public abstract ConsituerDAO getConsituerDAO();

}

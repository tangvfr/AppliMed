package fr.tangv.applimed.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RenameColumn;
import androidx.room.RenameTable;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Constituer;
import fr.tangv.applimed.model.Famille;
import fr.tangv.applimed.model.Medicament;

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

    public static AMDatabase getInstance(Context context) {
        synchronized (AMDatabase.class) {
            if (AMDatabase.INSTANCE == null) {
                AMDatabase.INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AMDatabase.class,
                                "db_med_app"
                        )
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

    public abstract ComposantDAO getComposantDAO();
    public abstract FamilleDAO getFamilleDAO();
    public abstract MedicamentDAO getMedicamentDAO();

}

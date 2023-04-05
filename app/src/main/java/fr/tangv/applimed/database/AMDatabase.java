package fr.tangv.applimed.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Constituer;
import fr.tangv.applimed.model.Famille;
import fr.tangv.applimed.model.Medicament;

@Database(version = 1, entities = {
        Composant.class,
        Famille.class,
        Constituer.class,
        Medicament.class
})
public abstract class AMDatabase extends RoomDatabase {

    private static AMDatabase INSTANCE = null;

    public static AMDatabase getInstance(Context context) {
        synchronized (AMDatabase.class) {
            if (AMDatabase.INSTANCE == null) {
                AMDatabase.INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AMDatabase.class,
                                "db_med_app"
                        )
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

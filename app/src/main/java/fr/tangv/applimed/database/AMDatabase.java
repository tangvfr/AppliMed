package fr.tangv.applimed.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {})
public abstract class AMDatabase extends RoomDatabase {

    private static AMDatabase INSTANCE = null;

    public static AMDatabase createInstance(Context context) {
        if (AMDatabase.INSTANCE == null) {
            synchronized (AMDatabase.class) {
                AMDatabase.INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AMDatabase.class,
                                "database-jpaarticle"
                        )
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return AMDatabase.INSTANCE;
    }
    
}

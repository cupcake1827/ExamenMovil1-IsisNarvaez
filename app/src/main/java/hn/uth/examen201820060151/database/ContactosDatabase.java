package hn.uth.examen201820060151.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {Contactos.class, Ubicaciones.class})
public abstract class ContactosDatabase extends RoomDatabase {

    public abstract ContactosDao contactosDao();
    public abstract UbicacionesDao ubicacionDao();

    private static volatile ContactosDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ContactosDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactosDatabase.class) {
                if (INSTANCE == null) {
                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                ContactosDao contactosDao = INSTANCE.contactosDao();
                                UbicacionesDao ubicacionDao = INSTANCE.ubicacionDao();


                                ubicacionDao.deleteAll();
                                contactosDao.deleteAll();

                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactosDatabase.class, "contactocompartido_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}

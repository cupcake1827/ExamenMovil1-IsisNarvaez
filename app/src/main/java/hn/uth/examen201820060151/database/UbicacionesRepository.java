package hn.uth.examen201820060151.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UbicacionesRepository {
    private UbicacionesDao ubicacionDao;
    private LiveData<List<Ubicaciones>> dataset;

    public UbicacionesRepository(Application application) {
        ContactosDatabase database = ContactosDatabase.getDatabase(application);
        ubicacionDao = database.ubicacionDao();
        dataset = ubicacionDao.getUbicaciones();
    }

    public void insert(Ubicaciones nuevo) {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.insert(nuevo);
        });
    }

    public void update(Ubicaciones actualizar) {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.update(actualizar);
        });
    }

    public void delete(Ubicaciones eliminar) {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.deleteAll();
        });
    }

    public LiveData<List<Ubicaciones>> getDataset() {
        return dataset;
    }

    public LiveData<List<Ubicaciones>> buscarUbicacion(String query) {
        return ubicacionDao.buscarUbicacion(query);
    }
}



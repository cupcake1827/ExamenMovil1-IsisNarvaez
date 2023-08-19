package hn.uth.examen201820060151.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactosRepository {

    private ContactosDao contactosDao;
    private LiveData<List<Contactos>> dataset;

    public ContactosRepository(Application application) {
        ContactosDatabase database = ContactosDatabase.getDatabase(application);
        contactosDao = database.contactosDao();
        dataset = contactosDao.getContactos();
    }

    public void insert(Contactos nuevo) {
        ContactosDatabase.databaseWriteExecutor.execute(()->{
            contactosDao.insert(nuevo);
        });
    }

    public void update(Contactos actualizar) {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.update(actualizar);
        });
    }

    public void delete(Contactos eliminar) {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        ContactosDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.deleteAll();
        });
    }


    public LiveData<List<Contactos>> getDataset() {
        return dataset;
    }

    public LiveData<List<Contactos>> buscarContacto(String query) {
        return contactosDao.buscarContacto(query);
    }

}



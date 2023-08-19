package hn.uth.examen201820060151.ui.crearContactos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201820060151.database.Contactos;
import hn.uth.examen201820060151.database.ContactosRepository;
import hn.uth.examen201820060151.database.Ubicaciones;
import hn.uth.examen201820060151.database.UbicacionesRepository;

public class CrearContactoViewModel extends AndroidViewModel {
    private ContactosRepository repository;
    private UbicacionesRepository repositoryUbicacion;
    private LiveData<List<Contactos>> allContactos;

    public CrearContactoViewModel(@NonNull Application app){
        super(app);
        this.repository = new ContactosRepository(app);
        this.repositoryUbicacion = new UbicacionesRepository(app);
    }

    public LiveData<List<Contactos>> getAllContactos() {
        return allContactos;
    }

    public void insert(Contactos contactos) {
        repository.insert(contactos);
    }

    public void insert(Ubicaciones ubicacion) {
        repositoryUbicacion.insert(ubicacion);
    }

    public void update(Contactos actualizar){
        repository.update(actualizar);
    }
}

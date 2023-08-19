package hn.uth.examen201820060151.ui.ubicacion;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import hn.uth.examen201820060151.database.Ubicaciones;
import hn.uth.examen201820060151.database.UbicacionesRepository;

public class UbicacionViewModel extends AndroidViewModel {


    private UbicacionesRepository repository;
    private final LiveData<List<Ubicaciones>> ubicacionDataset;


    public UbicacionViewModel(Application application) {
        super(application);
        this.repository = new UbicacionesRepository(application);
        this.ubicacionDataset = repository.getDataset();
    }

    public UbicacionesRepository getRepository() {
        return repository;
    }

    public LiveData<List<Ubicaciones>> getUbicacionDataset(){
        return ubicacionDataset;
    }


    public LiveData<List<Ubicaciones>> buscarUbicacion(String query) {
        return repository.buscarUbicacion("%" + query + "%");
    }

    public void insert(Ubicaciones ubicacion) {
        repository.insert(ubicacion);
    }

    public void update(Ubicaciones ubicacion) {
        repository.update(ubicacion);
    }

    public void delete(Ubicaciones ubicacion) {
        repository.delete(ubicacion);
    }
}

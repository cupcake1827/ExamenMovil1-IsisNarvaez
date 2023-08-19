package hn.uth.examen201820060151.ui.contactos;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201820060151.database.Contactos;
import hn.uth.examen201820060151.database.ContactosRepository;
import hn.uth.examen201820060151.databinding.ContactoItemBinding;

public class ContactosViewModel extends AndroidViewModel {
    private ContactosRepository repository;
    private final LiveData<List<Contactos>> contactosDataset;

    public ContactosViewModel(Application application) {
        super(application);
        this.repository = new ContactosRepository(application);
        this.contactosDataset = repository.getDataset();
    }

    public ContactosRepository getRepository() {
        return repository;
    }

    public LiveData<List<Contactos>> getContactosDataset(){
        return contactosDataset;
    }

    public LiveData<List<Contactos>> buscarContacto(String query) {
        return repository.buscarContacto("%" + query + "%");
    }

    public void insert(Contactos contacto) {
        repository.insert(contacto);
    }

    public void update(Contactos contacto) {
        repository.update(contacto);
    }

    public void delete(Contactos contacto) {
        repository.delete(contacto);
    }
}

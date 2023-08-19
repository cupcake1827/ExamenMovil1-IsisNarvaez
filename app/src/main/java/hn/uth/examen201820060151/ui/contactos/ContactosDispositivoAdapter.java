package hn.uth.examen201820060151.ui.contactos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.ui.entity.Contacto;

public class ContactosDispositivoAdapter extends RecyclerView.Adapter<ContactosDispositivoAdapter.ContactoDispositivoViewHolder> {
    private List<Contacto> listaContactos;

    public ContactosDispositivoAdapter(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoDispositivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto_dispositivo_item, parent, false);
        return new ContactoDispositivoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoDispositivoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);
        holder.tvContactoNombre.setText(contacto.getName());

        holder.itemView.setOnClickListener(view -> {
            String nombreContacto = contacto.getName();
            String telefonoContacto = contacto.getPhone();
            String correoContacto = contacto.getEmail();

            // Crear un Bundle para pasar los datos al fragmento de destino
            Bundle args = new Bundle();
            args.putString("nombre_contacto", nombreContacto);
            args.putString("telefono_contacto", telefonoContacto);
            args.putString("correo_contacto", correoContacto);

            // Obtener el NavController y navegar al fragmento de CrearContactoFragment
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.crearContactoFragment, args);
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    static class ContactoDispositivoViewHolder extends RecyclerView.ViewHolder {
        TextView tvContactoNombre;

        public ContactoDispositivoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContactoNombre = itemView.findViewById(R.id.tvContacto);
        }
    }
}


package hn.uth.examen201820060151.ui.contactos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.database.Contactos;
import hn.uth.examen201820060151.databinding.ContactoItemBinding;
import hn.uth.examen201820060151.ui.OnItemClickListener;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> {
    private List<Contactos> dataset;
    private OnItemClickListener<Contactos> manejadorContactoClick;
    private Context context;
    private ContactosViewModel viewModel;

    public ContactosAdapter(Context context, List<Contactos> dataset, OnItemClickListener<Contactos> manejadorContactoClick, ContactosViewModel viewModel) {
        this.context = context;
        this.dataset = dataset;
        this.manejadorContactoClick = manejadorContactoClick;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactoItemBinding binding = ContactoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contactos contactoItem = dataset.get(position);

        String nombreCompleto = contactoItem.getNombre().trim() + " " + contactoItem.getApellido().trim();
        holder.binding.tvContacto.setText(nombreCompleto);
        holder.binding.tvTelefono.setText(contactoItem.getTelefono());
        holder.binding.tvCorreo.setText(contactoItem.getCorreo());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(contactoItem);
                return true;
            }
        });

        // Establecer el evento de clic en el elemento de lista
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Bundle y agregar el contacto seleccionado
                Bundle bundle = new Bundle();
                bundle.putParcelable("contacto", contactoItem);

                // Obtener el NavController y navegar al fragmento DetalleContactoFragment
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_contactosFragment_to_detalleContactoFragment, bundle);
            }
        });

        // Configurar el evento de clic en la imagen de actualización
        holder.binding.imgActualizarContacto.setOnClickListener(v -> {
            // Pasar el contacto seleccionado al fragmento de actualización
            Bundle bundle = new Bundle();
            bundle.putParcelable("contacto", contactoItem);

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_contactosFragment_to_actualizarContactoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<Contactos> contactos) {
        this.dataset = contactos;
        notifyDataSetChanged();
    }

    private void showDialog(Contactos contacto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar contacto");
        builder.setMessage("¿Estás seguro de que deseas eliminar este contacto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteContacto(contacto);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void deleteContacto(Contactos contacto) {
        viewModel.delete(contacto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ContactoItemBinding binding;

        public ViewHolder(@NonNull ContactoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

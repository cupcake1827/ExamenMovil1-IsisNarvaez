package hn.uth.examen201820060151.ui.ubicacion;

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
import hn.uth.examen201820060151.database.Ubicaciones;
import hn.uth.examen201820060151.databinding.UbicacionItemBinding;
import hn.uth.examen201820060151.ui.OnItemClickListener;

public class UbicacionAdapter extends RecyclerView.Adapter<UbicacionAdapter.ViewHolder> {
    private List<Ubicaciones> dataset;
    private OnItemClickListener<Ubicaciones> manejadorEventoClick;
    private Context context;
    private UbicacionViewModel viewModel;

    public UbicacionAdapter(Context context, List<Ubicaciones> dataset, OnItemClickListener<Ubicaciones> manejadorEventoClick, UbicacionViewModel viewModel){
        this.context = context;
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UbicacionItemBinding binding = UbicacionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ubicaciones ubicacionItem = dataset.get(position);

        holder.binding.tvCategoria.setText(ubicacionItem.getCategoria());
        holder.binding.tvUsuario.setText(ubicacionItem.getPersona());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("ubicacion", ubicacionItem);

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_ubicacionesFragment_to_detalleUbicacionFragment, bundle);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(ubicacionItem);
                return true;
            }
        });
    }

    private void showDialog(Ubicaciones ubicacionItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar ubicacion");
        builder.setMessage("¿Estás seguro de que deseas eliminar esta ubicacion?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUbicacion(ubicacionItem);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void deleteUbicacion(Ubicaciones ubicacion) {
        viewModel.delete(ubicacion);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<Ubicaciones> ubicacion) {
        this.dataset = ubicacion;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UbicacionItemBinding binding;

        public ViewHolder(@NonNull UbicacionItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

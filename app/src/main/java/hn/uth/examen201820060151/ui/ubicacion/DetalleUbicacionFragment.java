package hn.uth.examen201820060151.ui.ubicacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.database.Ubicaciones;
import hn.uth.examen201820060151.databinding.FragmentDetalleUbicacionBinding;

public class DetalleUbicacionFragment extends Fragment {
    private FragmentDetalleUbicacionBinding binding;
    private TextView tvPersona;
    private TextView tvCategoria;
    private TextView tvUbicacion;
    private Ubicaciones ubicacion;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleUbicacionBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        tvPersona = rootView.findViewById(R.id.tvPersona);
        tvCategoria = rootView.findViewById(R.id.tvCategoria);
        tvUbicacion = rootView.findViewById(R.id.tvUbicacion);

        if (getArguments() != null) {
            ubicacion = getArguments().getParcelable("ubicacion");
            if (ubicacion != null) {
                tvPersona.setText(ubicacion.getPersona());
                tvCategoria.setText(ubicacion.getCategoria());

                String ubicacionCompleta = "Latitud: " + ubicacion.getLatitud() + " , Longitud: " +
                        ubicacion.getLongitud();
                tvUbicacion.setText(ubicacionCompleta);

                binding.btnAbrirUbicacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        abrirUbicacion();
                    }
                });

                binding.btnCompartirUbicacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        compartirUbicacion(ubicacion);
                    }
                });
            }
        }
        return rootView;
    }

    private void abrirUbicacion() {
        if (ubicacion != null && ubicacion.getLatitud() != 0 && ubicacion.getLongitud() != 0) {
            double latitud = ubicacion.getLatitud();
            double longitud = ubicacion.getLongitud();

            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        } else {
            Snackbar.make(requireView(), "No hay ubicación disponible", Snackbar.LENGTH_LONG).show();
        }
    }

    private void compartirUbicacion(Ubicaciones ubicacion) {
        if (ubicacion != null) {
            String textoCompartir = "Persona: " + ubicacion.getPersona() +
                    "\nCategoría: " + ubicacion.getCategoria() +
                    "\nLatitud: " + ubicacion.getLatitud() +
                    "\nLongitud: " + ubicacion.getLongitud();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Detalle de ubicación");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textoCompartir);

            startActivity(Intent.createChooser(shareIntent, "Compartir Detalle de Ubicación"));
        } else {
            Snackbar.make(requireView(), "No hay ubicación para compartir", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }
}

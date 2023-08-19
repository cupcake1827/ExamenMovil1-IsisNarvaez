package hn.uth.examen201820060151.ui.crearContactos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.database.Contactos;


public class DetalleContactoFragment extends Fragment {
    private DetalleContactoFragment binding;
    private TextView tvNombreContacto;
    private TextView tvApellidoContacto;
    private TextView tvTelefonoContacto;
    private TextView tvCumplContacto;
    private TextView tvCorreoContacto;
    private TextView tvUbicacionContacto;
    private TextView tvTrabajoContacto;
    private Contactos contacto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_contacto, container, false);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        tvNombreContacto = rootView.findViewById(R.id.tvNombreContacto);
        tvApellidoContacto = rootView.findViewById(R.id.tvApellidoContacto);
        tvTelefonoContacto = rootView.findViewById(R.id.tvTelefonoContacto);
        tvCumplContacto = rootView.findViewById(R.id.tvCumplContacto);
        tvCorreoContacto = rootView.findViewById(R.id.tvCorreoContacto);
        tvUbicacionContacto = rootView.findViewById(R.id.tvUbicacionHogar);
        tvTrabajoContacto = rootView.findViewById(R.id.tvUbicacionTrabajo);

        // Obtener el objeto Contactos de los argumentos del fragmento
        contacto = getArguments().getParcelable("contacto");

        // Asignar los valores del objeto Contactos a los elementos de vista
        if (contacto != null) {
            tvNombreContacto.setText(contacto.getNombre());
            tvApellidoContacto.setText(contacto.getApellido());
            tvTelefonoContacto.setText(contacto.getTelefono());
            tvCumplContacto.setText(contacto.getFechaCumple());
            tvCorreoContacto.setText(contacto.getCorreo());

            // Concatenar latitud y longitud del hogar en el mismo TextView
            String ubicacionHogar = "Latitud: " + contacto.getLatitudpreferencia() +
                    ", Longitud: " + contacto.getLongitudpreferencia();
            tvUbicacionContacto.setText(ubicacionHogar);

            String ubicacionTrabajo = "Latitud: " + contacto.getLatitudzonacompartida() +
                    ", Longitud: " + contacto.getLongitudzonacompartida();
            tvTrabajoContacto.setText(ubicacionTrabajo);
        }

        Button btnCompartir = rootView.findViewById(R.id.btnCompartirContacto);
        btnCompartir.setOnClickListener(v -> compartirContacto(contacto));

        Button btnAbrirMapa = rootView.findViewById(R.id.btnAbrirHogar);
        btnAbrirMapa.setOnClickListener(v -> abrirUbicacionHogar());

        Button btnMapaTrabajo = rootView.findViewById(R.id.btnAbrirTrabajo);
        btnMapaTrabajo.setOnClickListener(v -> abrirUbicacionTrabajo());

        return rootView;
    }

    public void abrirUbicacionHogar() {
        if (contacto != null && contacto.getLatitudpreferencia() != 0 && contacto.getLongitudpreferencia() != 0) {
            double latitud = contacto.getLatitudzonacompartida();
            double longitud = contacto.getLongitudzonacompartida();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }else{
            Snackbar.make(requireView(), "No hay ubicaciones", Snackbar.LENGTH_LONG).show();
        }
    }

    public void abrirUbicacionTrabajo() {
        if (contacto != null && contacto.getLatitudzonacompartida() != 0 && contacto.getLongitudzonacompartida() != 0) {
            double latitud = contacto.getLatitudpreferencia();
            double longitud = contacto.getLongitudpreferencia();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }else{
            Snackbar.make(requireView(), "No hay ubicaciones", Snackbar.LENGTH_LONG).show();
        }
    }

    private void compartirContacto(Contactos contacto) {
        if (contacto != null) {
            String textoCompartir = "Nombre: " + contacto.getNombre() +
                    "\nApellido: " + contacto.getApellido() +
                    "\nTeléfono: " + contacto.getTelefono() +
                    "\nCumpleaños: " + contacto.getFechaCumple() +
                    "\nCorreo: " + contacto.getCorreo();


            if (contacto.getLatitudpreferencia() != 0 && contacto.getLongitudpreferencia() != 0) {
                String ubicacionHogar = "Ubicación 1: " +
                        "\nLatitud: " + contacto.getLatitudpreferencia() +
                        "\nLongitud: " + contacto.getLongitudpreferencia();
                textoCompartir += "\n" + ubicacionHogar;
            }

            if (contacto.getLatitudzonacompartida() != 0 && contacto.getLongitudzonacompartida() != 0) {
                String ubicacionTrabajo = "Ubicación 2: " +
                        "\nLatitud: " + contacto.getLatitudzonacompartida() +
                        "\nLongitud: " + contacto.getLongitudzonacompartida();
                textoCompartir += "\n" + ubicacionTrabajo;
            }

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Detalle de contacto");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textoCompartir);

            startActivity(Intent.createChooser(shareIntent, "Compartir Detalle de Contacto"));
        }else{
            Snackbar.make(requireView(), "No hay valores que compartir", Snackbar.LENGTH_LONG).show();
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


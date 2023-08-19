package hn.uth.examen201820060151.ui.crearContactos;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.database.Contactos;
import hn.uth.examen201820060151.database.Ubicaciones;
import hn.uth.examen201820060151.databinding.FragmentCrearContactoBinding;


public class CrearContactoFragment extends Fragment implements LocationListener{
    private static final int REQUEST_CODE_GPS = 102;
    private FragmentCrearContactoBinding binding;
    private CrearContactoViewModel viewModel;
    private Calendar calendar;
    private LocationManager locationManager;
    private double latitudzonacompartida, longitudzonacompartida, latitudpreferencia, longitudpreferencia;


    private String tipoUbicacion;
    private boolean hogarObtenido = false;
    private boolean trabajoObtenido = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearContactoBinding.inflate(inflater, container, false);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        Bundle args = getArguments();
        if (args != null) {
            String nombreContacto = args.getString("nombre_contacto");
            String telefonoContacto = args.getString("telefono_contacto");
            String correoContacto = args.getString("correo_contacto");

            binding.tilNombreContacto.getEditText().setText(nombreContacto);
            binding.tilTelefonoContacto.getEditText().setText(telefonoContacto);
            binding.tilCorreoContacto.getEditText().setText(correoContacto);
        }

        binding.btnUbicacionHogar.setOnClickListener(v -> obtenerUbicacion("Hogar"));
        binding.btnUbicacionTrabajo.setOnClickListener(v -> obtenerUbicacion("Trabajo"));

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        binding.btnCumpleContacto.setOnClickListener(v -> showDatePickerDialog());

        binding.btnGuardarContacto.setOnClickListener(v -> saveContact());

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CrearContactoViewModel.class);
        calendar = Calendar.getInstance();

        binding.btnCumpleContacto.setOnClickListener(v -> showDatePickerDialog());

        binding.btnGuardarContacto.setOnClickListener(v -> saveContact());
    }

    private void obtenerUbicacion(String tipoUbicacion) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            this.tipoUbicacion = tipoUbicacion;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();
        Snackbar.make(binding.getRoot(), "Obteniendo ubicación...", Snackbar.LENGTH_SHORT).show();

        if ("Hogar".equals(tipoUbicacion)) {
            latitudpreferencia = latitud;
            longitudpreferencia = longitud;
            binding.tvLatitudHogar.setText(String.valueOf(latitudpreferencia));
            binding.tvLongitudHogar.setText(String.valueOf(longitudpreferencia));
        } else if ("Trabajo".equals(tipoUbicacion)) {
            latitudzonacompartida = latitud;
            longitudzonacompartida = longitud;
            binding.tvLatitudTrabajo.setText(String.valueOf(latitudzonacompartida));
            binding.tvLongitudTrabajo.setText(String.valueOf(longitudzonacompartida));
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTextView();
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDateTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        binding.tvCumpleContacto.setText(formattedDate);
    }

    private void saveContact() {
        // Obtener los valores de los campos
        String nombre = binding.tilNombreContacto.getEditText().getText().toString().trim();
        String apellido = binding.tilApellidoContacto.getEditText().getText().toString().trim();
        String correo = binding.tilCorreoContacto.getEditText().getText().toString().trim();
        String telefono = binding.tilTelefonoContacto.getEditText().getText().toString().trim();
        String fechaCumple = binding.tvCumpleContacto.getText().toString().trim();

        // Validar campos, crear contacto y guardar en la base de datos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaCumple.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que los campos no sean solo espacios en blanco
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty() || telefono.trim().isEmpty() || fechaCumple.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Los campos no pueden ser solo espacios en blanco", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar longitud mínima y máxima para nombre y apellido (por ejemplo, 2 a 30 caracteres)
        if (nombre.length() < 2 || nombre.length() > 30) {
            binding.tilNombreContacto.setError("El nombre debe tener entre 2 y 30 caracteres");
            binding.tilApellidoContacto.setError(null); // Eliminar error en apellido
            binding.tilTelefonoContacto.setError(null); // Eliminar error en teléfono
            binding.tilCorreoContacto.setError(null); // Eliminar error en correo
            return;
        }

        if (apellido.length() < 2 || apellido.length() > 30) {
            binding.tilApellidoContacto.setError("El apellido debe tener entre 2 y 30 caracteres");
            binding.tilNombreContacto.setError(null); // Eliminar error en nombre
            binding.tilTelefonoContacto.setError(null); // Eliminar error en teléfono
            binding.tilCorreoContacto.setError(null); // Eliminar error en correo
            return;
        }

        // Validar que el número de teléfono sea numérico y tenga una longitud válida (por ejemplo, 8 a 15 dígitos)
        if (!TextUtils.isDigitsOnly(telefono) || telefono.length() < 8 || telefono.length() > 15) {
            binding.tilTelefonoContacto.setError("Ingrese un número válido (8 a 15 dígitos)");
            binding.tilNombreContacto.setError(null); // Eliminar error en nombre
            binding.tilApellidoContacto.setError(null); // Eliminar error en apellido
            binding.tilCorreoContacto.setError(null); // Eliminar error en correo
            return;
        }

        // Validar que el correo tenga un formato válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreoContacto.setError("Ingrese un correo electrónico válido");
            binding.tilNombreContacto.setError(null); // Eliminar error en nombre
            binding.tilApellidoContacto.setError(null); // Eliminar error en apellido
            binding.tilTelefonoContacto.setError(null); // Eliminar error en teléfono
            return;
        }

        // Crear un objeto Contacto con los datos ingresados
        Contactos nuevoContacto = new Contactos(nombre, apellido, correo, telefono, fechaCumple, 0, latitudpreferencia, longitudpreferencia, latitudzonacompartida, longitudzonacompartida);
        // Crear objetos Ubicacion para ambas categorías
        String persona = nuevoContacto.getNombre() + " " + nuevoContacto.getApellido();

        Ubicaciones ubicacionHogar = new Ubicaciones(persona, "Hogar", latitudpreferencia, longitudpreferencia);
        Ubicaciones ubicacionTrabajo = new Ubicaciones(persona, "Trabajo", latitudzonacompartida, longitudzonacompartida);

        // Guardar las ubicaciones usando el ViewModel
        viewModel.insert(ubicacionHogar);
        viewModel.insert(ubicacionTrabajo);
        // Guardar el contacto usando el ViewModel
        viewModel.insert(nuevoContacto);

        finish();
    }

    private void finish() {
        // Mostrar un mensaje de éxito
        Toast.makeText(requireContext(), "Contacto y ubicaciones guardados correctamente", Toast.LENGTH_SHORT).show();

        // Limpiar los campos
        binding.tilNombreContacto.getEditText().setText("");
        binding.tilApellidoContacto.getEditText().setText("");
        binding.tilCorreoContacto.getEditText().setText("");
        binding.tilTelefonoContacto.getEditText().setText("");
        binding.tvCumpleContacto.setText("");

        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Mostrar el Bottom Navigation nuevamente al salir del fragmento
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }
}


package hn.uth.examen201820060151.ui.contactos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.databinding.FragmentContactosDispositivoBinding;
import hn.uth.examen201820060151.ui.entity.Contacto;

public class ContactosDispositivoFragment extends Fragment {
    private FragmentContactosDispositivoBinding binding;
    private static final int PERMISSION_REQUEST_READ_CONTACT = 101;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactosDispositivoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        // Solicitar permiso si aún no está concedido
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACT);
        } else {
            // Si el permiso ya está concedido, cargar los contactos directamente
            cargarContactos();
        }

        return root;
    }

    private void cargarContactos() {
        // Obtener la lista de contactos
        List<Contacto> listaContactos = obtenerListaDeContactos();
        // Configurar el RecyclerView y el Adapter
        RecyclerView recyclerView = binding.rvContactosDispositivo;
        ContactosDispositivoAdapter adapter = new ContactosDispositivoAdapter(listaContactos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private List<Contacto> obtenerListaDeContactos() {
        List<Contacto> listaContactos = new ArrayList<>();
        // Verificar si se tienen los permisos para acceder a los contactos
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tiene el permiso, retornar una lista vacía temporalmente
            return listaContactos;
        }
        ContentResolver contentResolver = requireContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );
        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);

            do {
                String nombreContacto = cursor.getString(nameColumnIndex);
                String contactoId = cursor.getString(idColumnIndex);

                Contacto contacto = new Contacto();
                contacto.setName(nombreContacto);

                // Obtener el número de teléfono asociado al contacto
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{contactoId},
                        null
                );
                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    int phoneColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String numeroTelefono = phoneCursor.getString(phoneColumnIndex);
                    contacto.setPhone(numeroTelefono);
                    phoneCursor.close();
                }
                // Obtener el correo electrónico asociado al contacto
                Cursor emailCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Email.DATA},
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{contactoId},
                        null
                );
                if (emailCursor != null && emailCursor.moveToFirst()) {
                    int emailColumnIndex = emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                    String correo = emailCursor.getString(emailColumnIndex);
                    contacto.setEmail(correo);
                    emailCursor.close();
                }
                listaContactos.add(contacto);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaContactos;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, cargar los contactos
                cargarContactos();
            } else {
                // Permiso denegado, mostrar mensaje o realizar acciones apropiadas
                Snackbar.make(requireView(), "No se pueden buscar contactos sin permiso", Snackbar.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


package hn.uth.examen201820060151.ui.contactos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hn.uth.examen201820060151.R;
import hn.uth.examen201820060151.database.Contactos;
import hn.uth.examen201820060151.databinding.FragmentContactosBinding;
import hn.uth.examen201820060151.ui.OnItemClickListener;

public class ContactosFragment extends Fragment implements OnItemClickListener<Contactos> {
        private FragmentContactosBinding binding;
        private ContactosAdapter adaptador;
        private ContactosViewModel viewModel;

        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                binding = FragmentContactosBinding.inflate(inflater, container, false);
                View root = binding.getRoot();

                viewModel = new ViewModelProvider(this).get(ContactosViewModel.class);
                adaptador = new ContactosAdapter(getContext(), new ArrayList<>(), this, viewModel);
                viewModel.getContactosDataset().observe(getViewLifecycleOwner(), contactos -> adaptador.setItems(contactos));

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setVisibility(View.VISIBLE);

                setupRecyclerView();
                setupSearchView();

                FloatingActionButton fabAgregarContacto = root.findViewById(R.id.fabAgregarContacto);
                fabAgregarContacto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.action_fragmentActual_to_crearContactoFragment);
                        }
                });
                return root;
        }

        private void setupRecyclerView() {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                binding.rvContactos.setLayoutManager(linearLayoutManager);
                binding.rvContactos.setAdapter(adaptador);
        }

        private void setupSearchView() {
                binding.buscarContacto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                                buscarContactosPorNombreApellido(query);
                                return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                                buscarContactosPorNombreApellido(newText);
                                return false;
                        }
                });
        }

        private void buscarContactosPorNombreApellido(String query) {
                viewModel.buscarContacto(query)
                        .observe(getViewLifecycleOwner(), contactos -> adaptador.setItems(contactos));
        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                binding = null;
        }

        @Override
        public void onItemClick(Contactos data) {
        }
}

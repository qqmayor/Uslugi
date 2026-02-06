package com.uslugi.app.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uslugi.app.R;
import com.uslugi.app.databinding.FragmentHomeBinding;
import com.uslugi.app.domain.model.Service;
import com.uslugi.app.ui.common.CategoryAdapter;
import com.uslugi.app.ui.common.ServiceListAdapter;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ServiceListAdapter serviceListAdapter;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        NavController navController = NavHostFragment.findNavController(this);

        serviceListAdapter = new ServiceListAdapter(service -> navigateToDetail(navController, service));
        binding.servicesRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.servicesRecycler.setAdapter(serviceListAdapter);

        categoryAdapter = new CategoryAdapter(viewModel::setSelectedCategory);
        binding.categoriesRecycler.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoriesRecycler.setAdapter(categoryAdapter);

        viewModel.getCategories().observe(getViewLifecycleOwner(), categoryAdapter::submitList);
        viewModel.getSearchResults().observe(getViewLifecycleOwner(), serviceListAdapter::submitList);

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setQuery(s.toString());
            }
        });

        binding.mapButton.setOnClickListener(v -> navController.navigate(R.id.action_home_to_map));
        binding.profileButton.setOnClickListener(v -> navController.navigate(R.id.action_home_to_profile));
    }

    private void navigateToDetail(NavController navController, Service service) {
        Bundle args = new Bundle();
        args.putString("serviceId", service.getId());
        navController.navigate(R.id.action_home_to_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

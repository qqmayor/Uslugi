package com.uslugi.app.ui.profile;

import android.os.Bundle;
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

import com.uslugi.app.databinding.FragmentProfileBinding;
import com.uslugi.app.ui.common.BookingAdapter;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private BookingAdapter upcomingAdapter;
    private BookingAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        NavController navController = NavHostFragment.findNavController(this);

        upcomingAdapter = new BookingAdapter();
        binding.upcomingRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.upcomingRecycler.setAdapter(upcomingAdapter);

        historyAdapter = new BookingAdapter();
        binding.historyRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecycler.setAdapter(historyAdapter);

        viewModel.getUpcomingBookings().observe(getViewLifecycleOwner(), upcomingAdapter::submitList);
        viewModel.getPastBookings().observe(getViewLifecycleOwner(), historyAdapter::submitList);

        binding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

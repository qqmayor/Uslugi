package com.uslugi.app.ui.booking;

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

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.uslugi.app.R;
import com.uslugi.app.databinding.FragmentBookingBinding;
import com.uslugi.app.domain.model.Booking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BookingFragment extends Fragment {
    private FragmentBookingBinding binding;
    private BookingViewModel viewModel;
    private String selectedDate = "";
    private String selectedTime = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        NavController navController = NavHostFragment.findNavController(this);

        binding.datePickerButton.setOnClickListener(v -> openDatePicker());
        binding.timePickerButton.setOnClickListener(v -> openTimePicker());

        binding.confirmBookingButton.setOnClickListener(v -> {
            String serviceId = getArguments() != null ? getArguments().getString("serviceId") : "";
            Booking booking = new Booking(
                    UUID.randomUUID().toString(),
                    serviceId,
                    getString(R.string.booking_service_placeholder),
                    selectedDate,
                    selectedTime,
                    getString(R.string.booking_status_pending)
            );
            viewModel.addBooking(booking);
            navController.navigate(R.id.action_booking_to_profile);
        });

        binding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private void openDatePicker() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .build();
        picker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM", Locale.getDefault());
            selectedDate = formatter.format(date);
            binding.selectedDate.setText(selectedDate);
        });
        picker.show(getChildFragmentManager(), "datePicker");
    }

    private void openTimePicker() {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(R.string.select_time)
                .build();
        picker.addOnPositiveButtonClickListener(v -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", picker.getHour(), picker.getMinute());
            binding.selectedTime.setText(selectedTime);
        });
        picker.show(getChildFragmentManager(), "timePicker");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

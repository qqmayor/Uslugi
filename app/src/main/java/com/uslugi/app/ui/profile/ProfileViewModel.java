package com.uslugi.app.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uslugi.app.data.FakeServiceRepository;
import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Booking;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private final ServiceRepository repository = FakeServiceRepository.getInstance();

    public LiveData<List<Booking>> getUpcomingBookings() {
        return repository.getUpcomingBookings();
    }

    public LiveData<List<Booking>> getPastBookings() {
        return repository.getPastBookings();
    }
}

package com.uslugi.app.ui.booking;

import androidx.lifecycle.ViewModel;

import com.uslugi.app.data.FakeServiceRepository;
import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Booking;

public class BookingViewModel extends ViewModel {
    private final ServiceRepository repository = FakeServiceRepository.getInstance();

    public void addBooking(Booking booking) {
        repository.addBooking(booking);
    }
}

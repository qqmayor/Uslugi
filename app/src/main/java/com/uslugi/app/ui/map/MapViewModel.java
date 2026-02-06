package com.uslugi.app.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uslugi.app.data.FakeServiceRepository;
import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Service;

import java.util.List;

public class MapViewModel extends ViewModel {
    private final ServiceRepository repository = FakeServiceRepository.getInstance();

    public LiveData<List<Service>> getServices() {
        return repository.getPopularServices();
    }
}

package com.uslugi.app.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uslugi.app.data.FakeServiceRepository;
import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Review;
import com.uslugi.app.domain.model.Service;

import java.util.List;

public class DetailViewModel extends ViewModel {
    private final ServiceRepository repository = FakeServiceRepository.getInstance();

    public LiveData<Service> getService(String serviceId) {
        return repository.getServiceById(serviceId);
    }

    public LiveData<List<Review>> getReviews(String serviceId) {
        return repository.getReviews(serviceId);
    }

    public void addReview(String serviceId, Review review) {
        repository.addReview(serviceId, review);
    }
}

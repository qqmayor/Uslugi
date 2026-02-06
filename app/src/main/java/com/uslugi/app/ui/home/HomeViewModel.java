package com.uslugi.app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uslugi.app.data.FakeServiceRepository;
import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Service;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final ServiceRepository repository = FakeServiceRepository.getInstance();
    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>("Все");
    private final MutableLiveData<String> query = new MutableLiveData<>("");
    private final MediatorLiveData<List<Service>> searchResults = new MediatorLiveData<>();
    private LiveData<List<Service>> searchSource;

    public HomeViewModel() {
        refreshSearch();
    }

    public LiveData<List<Service>> getPopularServices() {
        return repository.getPopularServices();
    }

    public LiveData<List<String>> getCategories() {
        return repository.getCategories();
    }

    public LiveData<List<Service>> getSearchResults() {
        return searchResults;
    }

    public void setSelectedCategory(String category) {
        selectedCategory.setValue(category);
        refreshSearch();
    }

    public void setQuery(String text) {
        query.setValue(text);
        refreshSearch();
    }

    public LiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    private void refreshSearch() {
        String queryValue = query.getValue() == null ? "" : query.getValue();
        String categoryValue = selectedCategory.getValue() == null ? "Все" : selectedCategory.getValue();
        if (searchSource != null) {
            searchResults.removeSource(searchSource);
        }
        searchSource = repository.searchServices(queryValue, categoryValue);
        searchResults.addSource(searchSource, searchResults::setValue);
    }
}

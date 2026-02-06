package com.uslugi.app.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uslugi.app.domain.ServiceRepository;
import com.uslugi.app.domain.model.Booking;
import com.uslugi.app.domain.model.Review;
import com.uslugi.app.domain.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FakeServiceRepository implements ServiceRepository {
    private static FakeServiceRepository instance;

    private final List<Service> services = new ArrayList<>();
    private final List<Review> reviews = new ArrayList<>();
    private final List<Booking> upcomingBookings = new ArrayList<>();
    private final List<Booking> pastBookings = new ArrayList<>();
    private final List<String> categories = Arrays.asList(
            "Маникюр",
            "Педикюр",
            "Парикмахерские услуги",
            "Брови и ресницы",
            "Услуги няни",
            "Магазины косметики",
            "Женские товары и сервисы"
    );

    private final MutableLiveData<List<Service>> popularServicesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Booking>> upcomingBookingsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Booking>> pastBookingsLiveData = new MutableLiveData<>();

    private FakeServiceRepository() {
        seedData();
    }

    public static FakeServiceRepository getInstance() {
        if (instance == null) {
            instance = new FakeServiceRepository();
        }
        return instance;
    }

    private void seedData() {
        services.add(new Service(
                "1",
                "Маникюр Studio L",
                "Гель-лак, дизайн, уход за руками",
                "ул. Тверская, 12",
                "Маникюр",
                55.7577,
                37.6156,
                4.8f
        ));
        services.add(new Service(
                "2",
                "Педикюр Harmony",
                "Комфортный педикюр и spa-уход",
                "ул. Арбат, 7",
                "Педикюр",
                55.7520,
                37.6044,
                4.6f
        ));
        services.add(new Service(
                "3",
                "Salon Air",
                "Стрижки, укладки, окрашивание",
                "пр. Мира, 25",
                "Парикмахерские услуги",
                55.7818,
                37.6332,
                4.9f
        ));
        services.add(new Service(
                "4",
                "Brow Bar",
                "Брови и ресницы, ламинирование",
                "ул. Петровка, 18",
                "Брови и ресницы",
                55.7670,
                37.6190,
                4.7f
        ));
        services.add(new Service(
                "5",
                "Няня рядом",
                "Подбор надежной няни по району",
                "ул. Садовая, 3",
                "Услуги няни",
                55.7651,
                37.6083,
                4.5f
        ));
        services.add(new Service(
                "6",
                "Beauty Store",
                "Магазин косметики и ухода",
                "ул. Никольская, 10",
                "Магазины косметики",
                55.7588,
                37.6246,
                4.4f
        ));
        services.add(new Service(
                "7",
                "Women Care",
                "Товары и сервисы для женщин",
                "ул. Большая Дмитровка, 5",
                "Женские товары и сервисы",
                55.7612,
                37.6170,
                4.6f
        ));

        reviews.add(new Review("r1", "1", "Алина", "Очень аккуратно и красиво!", 5f, "12.03"));
        reviews.add(new Review("r2", "1", "Дарья", "Быстрое обслуживание.", 4.5f, "13.03"));
        reviews.add(new Review("r3", "3", "Ольга", "Отличный мастер, рекомендую.", 5f, "14.03"));

        upcomingBookings.add(new Booking("b1", "1", "Маникюр Studio L", "20.03", "12:30", "Подтверждено"));
        upcomingBookings.add(new Booking("b2", "4", "Brow Bar", "22.03", "18:00", "Ожидает подтверждения"));
        pastBookings.add(new Booking("b3", "2", "Педикюр Harmony", "02.03", "10:00", "Завершено"));

        popularServicesLiveData.setValue(new ArrayList<>(services));
        categoriesLiveData.setValue(new ArrayList<>(categories));
        upcomingBookingsLiveData.setValue(new ArrayList<>(upcomingBookings));
        pastBookingsLiveData.setValue(new ArrayList<>(pastBookings));
    }

    @Override
    public LiveData<List<Service>> getPopularServices() {
        return popularServicesLiveData;
    }

    @Override
    public LiveData<List<String>> getCategories() {
        return categoriesLiveData;
    }

    @Override
    public LiveData<List<Service>> searchServices(String query, String category) {
        MutableLiveData<List<Service>> data = new MutableLiveData<>();
        String normalizedQuery = query == null ? "" : query.toLowerCase(Locale.getDefault());
        List<Service> results = new ArrayList<>();
        for (Service service : services) {
            boolean matchesCategory = category == null || category.isEmpty() || category.equals("Все")
                    || service.getCategory().equals(category);
            boolean matchesQuery = normalizedQuery.isEmpty() || service.getName().toLowerCase(Locale.getDefault())
                    .contains(normalizedQuery);
            if (matchesCategory && matchesQuery) {
                results.add(service);
            }
        }
        data.setValue(results);
        return data;
    }

    @Override
    public LiveData<Service> getServiceById(String serviceId) {
        MutableLiveData<Service> data = new MutableLiveData<>();
        for (Service service : services) {
            if (service.getId().equals(serviceId)) {
                data.setValue(service);
                break;
            }
        }
        return data;
    }

    @Override
    public LiveData<List<Review>> getReviews(String serviceId) {
        MutableLiveData<List<Review>> data = new MutableLiveData<>();
        List<Review> serviceReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getServiceId().equals(serviceId)) {
                serviceReviews.add(review);
            }
        }
        data.setValue(serviceReviews);
        return data;
    }

    @Override
    public void addReview(String serviceId, Review review) {
        reviews.add(review);
        float total = 0f;
        int count = 0;
        for (Review item : reviews) {
            if (item.getServiceId().equals(serviceId)) {
                total += item.getRating();
                count++;
            }
        }
        for (Service service : services) {
            if (service.getId().equals(serviceId)) {
                service.setRating(total / Math.max(count, 1));
            }
        }
        popularServicesLiveData.setValue(new ArrayList<>(services));
    }

    @Override
    public LiveData<List<Booking>> getUpcomingBookings() {
        return upcomingBookingsLiveData;
    }

    @Override
    public LiveData<List<Booking>> getPastBookings() {
        return pastBookingsLiveData;
    }

    @Override
    public void addBooking(Booking booking) {
        upcomingBookings.add(0, booking);
        upcomingBookingsLiveData.setValue(new ArrayList<>(upcomingBookings));
    }
}

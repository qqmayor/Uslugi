package com.uslugi.app.ui.detail;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uslugi.app.R;
import com.uslugi.app.databinding.FragmentDetailBinding;
import com.uslugi.app.domain.model.Review;
import com.uslugi.app.domain.model.Service;
import com.uslugi.app.ui.common.ReviewAdapter;

import java.util.UUID;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;
    private ReviewAdapter reviewAdapter;
    private String serviceId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        NavController navController = NavHostFragment.findNavController(this);

        serviceId = getArguments() != null ? getArguments().getString("serviceId") : null;

        reviewAdapter = new ReviewAdapter();
        binding.reviewsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.reviewsRecycler.setAdapter(reviewAdapter);

        if (serviceId != null) {
            viewModel.getService(serviceId).observe(getViewLifecycleOwner(), this::bindService);
            viewModel.getReviews(serviceId).observe(getViewLifecycleOwner(), reviewAdapter::submitList);
        }

        binding.bookingButton.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("serviceId", serviceId);
            navController.navigate(R.id.action_detail_to_booking, args);
        });

        binding.addReviewButton.setOnClickListener(v -> showReviewDialog());
        binding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private void bindService(Service service) {
        if (service == null) {
            return;
        }
        binding.serviceTitle.setText(service.getName());
        binding.serviceDescription.setText(service.getDescription());
        binding.serviceAddress.setText(service.getAddress());
        binding.serviceRatingBar.setRating(service.getRating());
        binding.serviceRatingValue.setText(String.format("%.1f", service.getRating()));
    }

    private void showReviewDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_review, null);
        EditText reviewInput = dialogView.findViewById(R.id.reviewInput);
        RatingBar ratingBar = dialogView.findViewById(R.id.reviewRatingInput);

        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.add_review_title)
                .setView(dialogView)
                .setPositiveButton(R.string.add_review_button, (dialog, which) -> {
                    float rating = ratingBar.getRating();
                    String text = reviewInput.getText().toString().trim();
                    if (serviceId != null && !text.isEmpty()) {
                        Review review = new Review(
                                UUID.randomUUID().toString(),
                                serviceId,
                                getString(R.string.review_default_author),
                                text,
                                rating,
                                getString(R.string.review_today)
                        );
                        viewModel.addReview(serviceId, review);
                        viewModel.getReviews(serviceId).observe(getViewLifecycleOwner(), reviewAdapter::submitList);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

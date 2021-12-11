package mz.co.muianga.quarkushop.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.quarkushop.model.Product;
import mz.co.muianga.quarkushop.model.Review;
import mz.co.muianga.quarkushop.repository.ProductRepository;
import mz.co.muianga.quarkushop.repository.ReviewRepository;
import mz.co.muianga.quarkushop.resources.dto.ReviewDto;

@Slf4j
@ApplicationScoped
@Transactional
public class ReviewService {

    @Inject
    ReviewRepository reviewRepository;

    @Inject
    ProductRepository productRepository;

    public List<ReviewDto> findReviewsByProductId(Long id) {
        log.debug("Request to get all Reviews");

        return this.reviewRepository.findReviewsByProductId(id)
                .stream()
                .map(ReviewService::mapToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto findById(Long id) {
        log.debug("Request to get Review : {} ", id);
        return reviewRepository.findById(id)
                .map(ReviewService::mapToDto)
                .orElse(null);
    }

    public ReviewDto create(ReviewDto reviewDto, Long productId) {
        log.debug("Request to create Review : {} for the Product {}", reviewDto, productId);
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + productId + " was not found!"));

        Review savedReview = this.reviewRepository.saveAndFlush(new Review(
                reviewDto.getTitle(),
                reviewDto.getDescription(),
                reviewDto.getRating()));
        product.getReviews().add(savedReview);
        this.productRepository.saveAndFlush(product);

        return mapToDto(savedReview);
    }

    public void delete(Long reviewId) {
        log.debug("Request to delete Review : {}", reviewId);
        Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalStateException("Review with ID " + reviewId + " was not found"));

        Product product = this.productRepository.findProductByReviewId(reviewId);
        product.getReviews().remove(review);
        this.productRepository.saveAndFlush(product);
        this.reviewRepository.delete(review);
    }

    public static ReviewDto mapToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getDescriprion(),
                review.getRating());
    }
}

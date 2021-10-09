package mz.co.muianga.repository;

import java.util.List;
import mz.co.muianga.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select p.reviews from Product p WHERE p.id = ?1")
    List<Review> findReviewsByProductId(Long id);
}

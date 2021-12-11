package mz.co.muianga.quarkushop.repository;

import java.util.List;
import mz.co.muianga.quarkushop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategoryId(Long categoryId);
    Long countAllByCategoryId(Long categoryId);

    @Query ("select p from Produto p JOIN p.reviews r WHERE r.id = ?")
    Product findProductByReviewId(Long reviewId);
    void deleteAllByCategoryId(Long categoryId);
    List<Product> findAllByCategoryId(Long categoryId);
}

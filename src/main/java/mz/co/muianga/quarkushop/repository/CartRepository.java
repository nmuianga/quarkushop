package mz.co.muianga.quarkushop.repository;

import java.util.List;
import mz.co.muianga.quarkushop.model.Cart;
import mz.co.muianga.quarkushop.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByStatus(CartStatus status);
    List<Cart> findByStatusAndCustomerId(CartStatus status, Long customerId);
}

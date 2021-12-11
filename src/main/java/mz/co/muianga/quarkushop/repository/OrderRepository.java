package mz.co.muianga.quarkushop.repository;

import java.util.List;
import java.util.Optional;
import mz.co.muianga.quarkushop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCartCustomerId(Long customerId);
    Optional<Order> findByPaymentId(Long id);
}

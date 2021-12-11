package mz.co.muianga.quarkushop.repository;

import java.util.List;
import mz.co.muianga.quarkushop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(Long id);
}

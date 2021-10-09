package mz.co.muianga.repository;

import java.math.BigDecimal;
import java.util.List;
import mz.co.muianga.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllAmountBetween(BigDecimal min, BigDecimal max);
}

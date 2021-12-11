package mz.co.muianga.quarkushop.repository;

import java.util.List;
import mz.co.muianga.quarkushop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByEnabled(Boolean enabled);
}

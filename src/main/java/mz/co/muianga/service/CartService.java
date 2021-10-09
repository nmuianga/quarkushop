package mz.co.muianga.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.repository.CartRepository;
import mz.co.muianga.repository.CustomerRepository;

@Slf4j
@ApplicationScoped
@Transactional
public class CartService {

    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;

    public List<CartDto> findAll() {
        log.debug("Request to get all Carts");
    }
}

package mz.co.muianga.quarkushop.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.quarkushop.model.Cart;
import mz.co.muianga.quarkushop.model.CartStatus;
import mz.co.muianga.quarkushop.repository.CartRepository;
import mz.co.muianga.quarkushop.repository.CustomerRepository;
import mz.co.muianga.quarkushop.resources.dto.CartDto;

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
        return cartRepository.findAll()
                .stream()
                .map(CartService::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CartDto> findAllActiveCarts() {
        return cartRepository
                .findByStatus(CartStatus.NEW)
                .stream()
                .map(CartService::mapToDto)
                .collect(Collectors.toList());
    }

    public Cart create(Long customerId) {
        if (this.getActiveCart(customerId) == null) {
            var customer = this.customerRepository.findById(customerId)
                    .orElseThrow(()
                            -> new IllegalStateException("This customer does not exist!")
                    );
            var cart = new Cart(customer, CartStatus.NEW);
            return this.cartRepository.save(cart);
        } else {
            throw new IllegalStateException("There is already an active cart!");
        }
    }

    public CartDto createDto(Long customerId) {
        return mapToDto(this.create(customerId));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public CartDto findById(Long id) {
        log.debug("Request to get Cart : {}", id);
        return this.cartRepository.findById(id)
                .map(CartService::mapToDto)
                .orElse(null);
    }

    public void delete(Long id) {
        log.debug("Request to delete Cart {}", id);

        Cart cart = this.cartRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Cart with id " + id));
        cart.setStatus(CartStatus.CANDELED);
        this.cartRepository.save(cart);
    }

    public CartDto getActiveCart(Long customerId) {
        List<Cart> carts = this.cartRepository.findByStatusAndCustomerId(CartStatus.NEW, customerId);

        if (carts != null) {
            if (carts.size() == 1) {
                return mapToDto(carts.get(0));
            }

            if (carts.size() > 1) {
                throw new IllegalStateException("Many active carts detected!!!");
            }
        }

        return null;
    }

    public static CartDto mapToDto(Cart cart) {
        return new CartDto(cart.getId(),
                CustomerService.mapToDto(cart.getCustomer()),
                cart.getStatus().name());
    }
}

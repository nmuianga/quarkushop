package mz.co.muianga.quarkushop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.quarkushop.model.Order;
import mz.co.muianga.quarkushop.model.OrderStatus;
import mz.co.muianga.quarkushop.model.Payment;
import mz.co.muianga.quarkushop.model.PaymentStatus;
import mz.co.muianga.quarkushop.resources.dto.PaymentDto;
import mz.co.muianga.quarkushop.repository.OrderRepository;
import mz.co.muianga.quarkushop.repository.PaymentRepository;

@Slf4j
@ApplicationScoped
@Transactional
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderRepository orderRepository;

    public List<PaymentDto> findByPriceRange(Double max) {
        return paymentRepository.findAllByAmountBetween(BigDecimal.ZERO, BigDecimal.valueOf(max))
                .stream()
                .map(payment -> mapToDto(payment,
                        findOrderByPaymentId(payment.getId()).getId()))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(payment -> findById(payment.getId()))
                .collect(Collectors.toList());
    }

    public PaymentDto findById(Long id) {
        log.debug("Request to get Payment : {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("The Order does not exist!"));

        return this.paymentRepository.findById(id)
                .map(payment -> mapToDto(payment, order.getId()))
                .orElse(null);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        log.debug("Request to create Payment {}", paymentDto);
        Order order = this.orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new IllegalStateException("The order does not exist!"));
        order.setStatus(OrderStatus.PAID);

        Payment payment = paymentRepository.saveAndFlush(new Payment(
                paymentDto.getPaypalPaymentId(),
                PaymentStatus.valueOf(paymentDto.getStatus()),
                order.getPrice()
        ));

        this.orderRepository.saveAndFlush(order);

        return mapToDto(payment, order.getId());
    }

    private Order findOrderByPaymentId(Long id) {
        return this.orderRepository.findByPaymentId(id)
                .orElseThrow(() -> new IllegalStateException("No Order exists for the payment ID " + id));
    }

    public void delete(Long id) {
        log.debug("Request to delete Payment", id);
        this.paymentRepository.deleteById(id);
    }

    public static PaymentDto mapToDto(Payment payment, Long orderId) {
        if (payment != null) {
            return new PaymentDto(
                    payment.getId(),
                    payment.getPaypalPaymentId(),
                    payment.getStatus().name(),
                    orderId);
        }

        return null;
    }
}

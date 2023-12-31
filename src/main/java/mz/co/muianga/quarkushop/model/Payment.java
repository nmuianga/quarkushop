package mz.co.muianga.quarkushop.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString (callSuper = true)
@Entity
@Table (name = "payments")
public class Payment extends AbstractEntity {

    @Column (name = "paypal_payment_id")
    private String paypalPaymentId;

    @NotNull
    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private PaymentStatus status;

    @NotNull
    @Column (name = "amount", nullable = false)
    private BigDecimal amount;

    public Payment(String paypalPaymentId, @NotNull PaymentStatus status,
                   @NotNull BigDecimal amount) {
        this.paypalPaymentId = paypalPaymentId;
        this.status = status;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paypalPaymentId, payment.paypalPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paypalPaymentId);
    }
}

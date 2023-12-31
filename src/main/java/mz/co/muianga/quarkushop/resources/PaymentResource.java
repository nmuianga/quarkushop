package mz.co.muianga.quarkushop.resources;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import mz.co.muianga.quarkushop.resources.dto.PaymentDto;
import mz.co.muianga.quarkushop.service.PaymentService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/payments")
@Tag(name = "payment", description = "All payment methods")
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @GET
    public List<PaymentDto> findAll() {
        return this.paymentService.findAll();
    }

    @GET
    @Path("/{id}")
    public PaymentDto findById(@PathParam("id") Long id) {
        return this.paymentService.findById(id);
    }

    @POST
    public PaymentDto create(PaymentDto paymentDto) {
        return this.paymentService.create(paymentDto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(Long id) {
        this.paymentService.delete(id);
    }

    @GET
    @Path("/price/{max}")
    public List<PaymentDto> findPaymentsByAmountRangeMax(@PathParam("max") double max) {
        return paymentService.findByPriceRange(max);
    }
}

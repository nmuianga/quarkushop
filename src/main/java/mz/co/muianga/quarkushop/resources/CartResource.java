package mz.co.muianga.quarkushop.resources;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mz.co.muianga.quarkushop.resources.dto.CartDto;
import mz.co.muianga.quarkushop.service.CartService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "cart", description = "All cart methods")
public class CartResource {

    @Inject
    CartService cartService;

    @GET
    public List<CartDto> findAll() {
        return cartService.findAll();
    }

    @GET
    @Path("/active")
    public List<CartDto> findActiveCarts() {
        return cartService.findAllActiveCarts();
    }

    @GET
    @Path("/customer/{id}")
    public CartDto getActiveCartForCustomer(@PathParam("id") Long customerId) {
        return this.cartService.getActiveCart(customerId);
    }

    @GET
    @Path("/{id}")
    public CartDto findById(@PathParam("id") Long id) {
        return this.cartService.findById(id);
    }

    @POST
    @Path("/customer/{id}")
    public CartDto create(@PathParam("id") Long customerId) {
        return this.cartService.createDto(customerId);
    }

    @DELETE
    @Path(("/{id}"))
    public void delete(@PathParam("id") Long id) {
        this.cartService.delete(id);
    }
}

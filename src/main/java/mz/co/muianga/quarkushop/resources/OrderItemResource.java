package mz.co.muianga.quarkushop.resources;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import mz.co.muianga.quarkushop.resources.dto.OrderItemDto;
import mz.co.muianga.quarkushop.service.OrderItemService;

@Path("/order-items")
public class OrderItemResource {

    @Inject
    OrderItemService orderItemService;

    @GET
    @Path("/order/{id}")
    public List<OrderItemDto> findByOrderId(@PathParam("id") Long id) {
        return this.orderItemService.findByOrderId(id);
    }

    @GET
    @Path("/{id}")
    public OrderItemDto findById(Long id) {
        return this.orderItemService.findById(id);
    }

    @POST
    public OrderItemDto create(OrderItemDto orderItemDto) {
        return this.orderItemService.create(orderItemDto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.orderItemService.delete(id);
    }
}
